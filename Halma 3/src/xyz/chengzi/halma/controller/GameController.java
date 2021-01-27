package xyz.chengzi.halma.controller;

import jdk.nashorn.internal.scripts.JO;
import xyz.chengzi.halma.Internet.ChatOnline;
import xyz.chengzi.halma.Internet.ClientIn;
import xyz.chengzi.halma.Internet.ClientOut;
import xyz.chengzi.halma.Internet.LocalServer;
import xyz.chengzi.halma.audio.Loop;
import xyz.chengzi.halma.audio.WavePlayer;
import xyz.chengzi.halma.listener.InputListener;
import xyz.chengzi.halma.model.ChessBoard;
import xyz.chengzi.halma.model.ChessBoardLocation;
import xyz.chengzi.halma.model.ChessPiece;

import xyz.chengzi.halma.model.Square;
import xyz.chengzi.halma.strategy.*;
import xyz.chengzi.halma.view.ChessBoardComponent;
import xyz.chengzi.halma.view.ChessComponent;
import xyz.chengzi.halma.view.GameFrame;
import xyz.chengzi.halma.view.SquareComponent;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

public class GameController implements InputListener ,Serializable{
    private static final long serialVersionUID=1L;
    private ChessBoardComponent view;
    private ChessBoard model;

    public GameFrame gameFrame;

    private ChessBoardLocation selectedLocation;
    public static Color currentPlayer;
    public static Color currentPlayerFromServer=Color.RED;

    private JLabel currentPlayerView;

    public static PossibleMove possibleMove;
    private ArrayList<Winner> winners;
    private Winner finalWinner;
    private boolean isFinished=false;
    WavePlayer audio=new WavePlayer();
    Loop loop=null;

    public boolean bgm=true;


    //模式 0-单机 1-人机 2-局域网 3-网络
    private int mode;
//    public int AiDifficulty;

    public static String LocalIp="127.0.0.1";

    ClientIn clientIn;
    ClientOut clientOut;

    public int userId;
    public String userName;
    public int points;

    public static int topic;

    private ArrayList<Move> moveLogs=new ArrayList<>();

    public GameController(ChessBoardComponent chessBoardComponent, ChessBoard chessBoard,int mode) {
        this.view = chessBoardComponent;
        this.model = chessBoard;
        this.mode=mode;

        this.currentPlayer = Color.RED;
        view.registerListener(this);
        model.registerListener(view);
        model.placeInitialPieces();
        model.mode=mode;
        this.winners=new ArrayList<>();


        if (model.getNumPlayer()==2){
            this.winners.add(new Winner(Color.RED,2));
            this.winners.add(new Winner(Color.GREEN,2));
        }
        if (model.getNumPlayer()==4){
            this.winners.add(new Winner(Color.RED,4));
            this.winners.add(new Winner(Color.GREEN,4));
            this.winners.add(new Winner(Color.YELLOW,4));
            this.winners.add(new Winner(Color.BLUE,4));
        }
        possibleMove=new PossibleMove(model.getNumPlayer(),model);


        if (topic==1){
            this.loop=new Loop(audio,7);
            this.loop.start();
        }
        if (topic==2){
            this.loop=new Loop(audio,9);
            this.loop.start();
        }


        //人机模式
        if (mode==1){
//            this.currentPlayer=Color.GREEN;
        }

        //局域网联机模式
        if (mode==2){
            if (LocalIp.equals("127.0.0.1")){
                LocalServer localServer=new LocalServer();
                localServer.start();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Socket socket=new Socket();
            try {
                socket.connect(new InetSocketAddress(LocalIp,10086));
                System.out.println("socket连接成功");
            } catch (IOException e) {
                System.out.println("连接房主内网IP失败");
            }

            clientIn=new ClientIn(socket,model,this);
            clientOut=new ClientOut(socket);
            clientIn.start();
            System.out.println("in线程启动成功");

            model.mode=2;
            //默认从红方开始
            clientOut.location[2]=new ChessBoardLocation(0,0);
            clientOut.run();
        }
        //网络联机模式
        if (mode==3){
            Socket socket=new Socket();
            try {
                //120.26.184.239
                socket.connect(new InetSocketAddress("120.26.184.239",10086));
            } catch (IOException e) {
                System.out.println("连接服务器失败");
            }

            clientIn=new ClientIn(socket,model,this);
            clientOut=new ClientOut(socket);
            clientIn.start();
            System.out.println("in线程启动成功");

            model.mode=3;
            //默认从红方开始
            clientOut.location[2]=new ChessBoardLocation(0,0);
            clientOut.run();

        }
    }



    public void restart(){
        if (mode==2||mode==3){
            JOptionPane.showMessageDialog(view,"本模式不允许重置棋盘！");
            return;
        }




        this.currentPlayer=Color.RED;
        model.placeInitialPieces();
        if (mode==0){
            currentPlayer=Color.RED;
            currentPlayerView.setText(GameFrame.red);
            updateLabel();
        }
        if (mode==1){
            currentPlayer=Color.RED;
            currentPlayerView.setText(GameFrame.red);
            updateLabel();
        }
        possibleMove.reSet(model,model.getNumPlayer());
        this.finalWinner=null;
        this.isFinished=false;
        this.moveLogs.clear();

    }
    public ChessBoardLocation getSelectedLocation() {
        return selectedLocation;
    }

    public void setSelectedLocation(ChessBoardLocation location) {
        this.selectedLocation = location;
    }

    public void resetSelectedLocation() {
        setSelectedLocation(null);
    }

    public boolean hasSelectedLocation() {
        return selectedLocation != null;
    }

    public Color nextPlayer() {
        if (model.getNumPlayer()==2){
            currentPlayer = currentPlayer == Color.RED ? Color.GREEN : Color.RED;
            updateLabel();
            model.currentPlayer=currentPlayer;
//            this.moveLogs.clear();
            return currentPlayer;
        }
        if (model.getNumPlayer()==4){
            if (currentPlayer==Color.RED){
                currentPlayer=Color.YELLOW;
                updateLabel();
                model.currentPlayer=currentPlayer;
//                this.moveLogs.clear();
                return currentPlayer;
            }
            if (currentPlayer==Color.YELLOW){
                currentPlayer=Color.GREEN;
                updateLabel();
                model.currentPlayer=currentPlayer;
//                this.moveLogs.clear();
                return currentPlayer;
            }
            if (currentPlayer==Color.GREEN){
                currentPlayer=Color.BLUE;
                updateLabel();
                model.currentPlayer=currentPlayer;
//                this.moveLogs.clear();
                return currentPlayer;
            }
            if (currentPlayer==Color.BLUE){
                currentPlayer=Color.RED;
                updateLabel();
                model.currentPlayer=currentPlayer;
//                this.moveLogs.clear();
                return currentPlayer;
            }
        }
        return currentPlayer;
    }
    public Color nextPlayerInServer() {
        if (model.getNumPlayer()==2){
            return currentPlayer == Color.RED ? Color.GREEN : Color.RED;
        }
        if (model.getNumPlayer()==4){
            if (currentPlayer==Color.RED){
                return Color.YELLOW;
            }
            if (currentPlayer==Color.YELLOW){
                return Color.GREEN;
            }
            if (currentPlayer==Color.GREEN){
                return Color.BLUE;
            }
            if (currentPlayer==Color.BLUE){
                return Color.RED;
            }
        }
        return Color.RED;
    }
    @Override
    public void onPlayerClickSquare(ChessBoardLocation location, SquareComponent component) {
//        System.out.println(model.isValidMove(getSelectedLocation(), location));
//        System.out.println(currentPlayerFromServer);
        if (hasSelectedLocation() && model.isValidMove(getSelectedLocation(), location)) {
            if (possibleMove.isHasJumped()==false){
                view.setInJumpingState(false);
                if (mode==3||mode==2){
                    clientIn.canAccept=false;
                }
                model.moveChessPiece(selectedLocation, location);
                this.moveLogs.add(new Move(selectedLocation,location));
                if (mode==3||mode==2){
                    clientOut.location[0]=selectedLocation;
                    clientOut.location[1]=location;
                }
                if (mode==0){
                    nextPlayer();
                }

                if (mode==1){
                    if (model.getNumPlayer()==2){
                        nextPlayer();
                        possibleMove.setHasJumped(false);
                        view.setInJumpingState(false);
                        AI.AiColor=currentPlayer;
//                        System.out.println(AI.AiColor);
                        MinMaxResult minMaxResult = AI.max(model,1,currentPlayer,2);
                        model.moveChessPiece(minMaxResult.move.src,minMaxResult.move.des);
                        this.moveLogs.add(new Move(minMaxResult.move.src,minMaxResult.move.des));
                        possibleMove.setHasJumped(false);
                        view.setInJumpingState(false);
                        nextPlayer();
                    }
                    if (model.getNumPlayer()==4){
                        nextPlayer();
                        possibleMove.setHasJumped(false);
                        view.setInJumpingState(false);
                        AI.AiColor=currentPlayer;
                        MinMaxResult minMaxResult1 = AI.max(model,1,  currentPlayer, 4);
                        model.moveChessPiece(minMaxResult1.move.src,minMaxResult1.move.des);
                        this.moveLogs.add(new Move(minMaxResult1.move.src,minMaxResult1.move.des));
                        possibleMove.setHasJumped(false);
                        view.setInJumpingState(false);
                        nextPlayer();
                        possibleMove.setHasJumped(false);
                        view.setInJumpingState(false);
                        AI.AiColor=currentPlayer;
                        MinMaxResult minMaxResult2 = AI.max(model,1,  currentPlayer, 4);
                        model.moveChessPiece(minMaxResult2.move.src,minMaxResult2.move.des);
                        this.moveLogs.add(new Move(minMaxResult2.move.src,minMaxResult2.move.des));
                        possibleMove.setHasJumped(false);
                        view.setInJumpingState(false);
                        nextPlayer();
                        possibleMove.setHasJumped(false);
                        view.setInJumpingState(false);
                        AI.AiColor=currentPlayer;
                        MinMaxResult minMaxResult3 = AI.max(model,1,  currentPlayer, 4);
                        model.moveChessPiece(minMaxResult3.move.src,minMaxResult3.move.des);
                        this.moveLogs.add(new Move(minMaxResult3.move.src,minMaxResult3.move.des));
                        possibleMove.setHasJumped(false);
                        view.setInJumpingState(false);
                        nextPlayer();
                    }
                }

                if (mode==3||mode==2){
                    currentPlayerFromServer=nextPlayerInServer();
                    clientOut.location[2]=getCurrent();
                    clientOut.run();
                    System.out.println("out线程发送成功");
//                    System.out.println(currentPlayerFromServer);
                }
                try {
                    Thread.currentThread().sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (mode==3||mode==2){
                    clientIn.canAccept=true;
                }
                swapGrids();
                resetSelectedLocation();
            }else {
                view.setInJumpingState(true);
                if (mode==3||mode==2){
                    clientIn.canAccept=false;
                }
                model.moveChessPiece(selectedLocation, location);
                this.moveLogs.add(new Move(selectedLocation,location));
                if (mode==3||mode==2){
                    clientOut.location[0]=selectedLocation;
                    clientOut.location[1]=location;
                    clientOut.location[2]=getCurrent();
                    clientOut.run();
                    System.out.println("out线程启动成功");
                }

                try {
                    Thread.currentThread().sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (mode==3||mode==2){
                    clientIn.canAccept=true;
                }
                resetSelectedLocation();
                setSelectedLocation(location);
                swapGrids();
                paintPossibleGrids(location);
                possibleMove.setHasJumped(true);
//                System.out.println(currentPlayerFromServer);
            }
            audio.Play(3);
            isWin();
        }
    }

    @Override
    public void onPlayerClickChessPiece(ChessBoardLocation location, ChessComponent component) {
        ChessPiece piece = model.getChessPieceAt(location);
        if (piece.getColor().equals(currentPlayer)  && (!hasSelectedLocation() || location.equals(getSelectedLocation()))&&this.isFinished==false) {
            if (!hasSelectedLocation()) {
                setSelectedLocation(location);
                paintPossibleGrids(location);
                audio.Play(1);
            } else {
                if (possibleMove.isHasJumped()==false){
                    resetSelectedLocation();
                }else {
                    resetSelectedLocation();
                    if (mode==0){
                        nextPlayer();
                    }
                    if (mode==1){
                        if (model.getNumPlayer()==2){
                            nextPlayer();
                            possibleMove.setHasJumped(false);
                            view.setInJumpingState(false);
                            AI.AiColor=currentPlayer;
                            MinMaxResult minMaxResult = AI.max(model,1,currentPlayer,2);
                            model.moveChessPiece(minMaxResult.move.src,minMaxResult.move.des);
                            this.moveLogs.add(new Move(minMaxResult.move.src,minMaxResult.move.des));
                            possibleMove.setHasJumped(false);
                            view.setInJumpingState(false);
                            nextPlayer();
                        }
                        if (model.getNumPlayer()==4){
                            nextPlayer();
                            possibleMove.setHasJumped(false);
                            view.setInJumpingState(false);
                            AI.AiColor=currentPlayer;
                            MinMaxResult minMaxResult1 = AI.max(model,1,  currentPlayer, 4);
                            model.moveChessPiece(minMaxResult1.move.src,minMaxResult1.move.des);
                            this.moveLogs.add(new Move(minMaxResult1.move.src,minMaxResult1.move.des));
                            possibleMove.setHasJumped(false);
                            view.setInJumpingState(false);
                            nextPlayer();
                            possibleMove.setHasJumped(false);
                            view.setInJumpingState(false);
                            AI.AiColor=currentPlayer;
                            MinMaxResult minMaxResult2 = AI.max(model,1,  currentPlayer, 4);
                            model.moveChessPiece(minMaxResult2.move.src,minMaxResult2.move.des);
                            this.moveLogs.add(new Move(minMaxResult2.move.src,minMaxResult2.move.des));
                            possibleMove.setHasJumped(false);
                            view.setInJumpingState(false);
                            nextPlayer();
                            possibleMove.setHasJumped(false);
                            view.setInJumpingState(false);
                            AI.AiColor=currentPlayer;
                            MinMaxResult minMaxResult3 = AI.max(model,1,  currentPlayer, 4);
                            model.moveChessPiece(minMaxResult3.move.src,minMaxResult3.move.des);
                            this.moveLogs.add(new Move(minMaxResult3.move.src,minMaxResult3.move.des));
                            possibleMove.setHasJumped(false);
                            view.setInJumpingState(false);
                            nextPlayer();
                        }
                    }
                    if (mode==3||mode==2){
                        clientIn.canAccept=false;
                        currentPlayerFromServer=nextPlayerInServer();
                        clientOut.location[2]=getCurrent();
                        clientOut.run();
                        System.out.println("out线程启动成功");
                    }
                    if (mode==2||mode==3){
                        try {
                            Thread.currentThread().sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (mode==3||mode==2){
                        clientIn.canAccept=true;
                    }
                    view.setInJumpingState(false);
                    possibleMove.setHasJumped(false);
                }
                audio.Play(2);
                swapGrids();
            }

            component.setSelected(!component.isSelected());
            component.repaint();
        }
    }

    public void setCurrentPlayerView(JLabel label){
        this.currentPlayerView=label;
    }

    public void updateLabel(){
        if (mode==0||mode==1){
            if (currentPlayer.equals(Color.RED)){
                currentPlayerView.setText(GameFrame.red);
                if (topic==1){
                    currentPlayerView.setForeground(Color.GREEN);
                }
                if (topic==2){
                    currentPlayerView.setForeground(Color.RED);
                }
            }
            if (currentPlayer.equals(Color.GREEN)){
                currentPlayerView.setText(GameFrame.green);
                if (topic==1){
                    currentPlayerView.setForeground(Color.BLUE);
                }
                if (topic==2){
                    currentPlayerView.setForeground(Color.PINK);
                }
            }
            if (currentPlayer.equals(Color.YELLOW)){
                currentPlayerView.setText(GameFrame.yellow);
                if (topic==1){
                    currentPlayerView.setForeground(Color.RED);
                }
                if (topic==2){
                    currentPlayerView.setForeground(Color.YELLOW);
                }
            }
            if (currentPlayer.equals(Color.BLUE)){
                currentPlayerView.setText(GameFrame.blue);
                if (topic==1){
                    currentPlayerView.setForeground(new Color(255,215,0));
                }
                if (topic==2){
                    currentPlayerView.setForeground(Color.BLUE);
                }
            }
        }else if (mode==2||mode==3){
            try {
                if (currentPlayerFromServer.equals(Color.RED)){
                    currentPlayerView.setText(GameFrame.red);
                    if (topic==1){
                        currentPlayerView.setForeground(Color.GREEN);
                    }
                    if (topic==2){
                        currentPlayerView.setForeground(Color.RED);
                    }
                }
                if (currentPlayerFromServer.equals(Color.GREEN)){
                    currentPlayerView.setText(GameFrame.green);
                    if (topic==1){
                        currentPlayerView.setForeground(Color.BLUE);
                    }
                    if (topic==2){
                        currentPlayerView.setForeground(Color.PINK);
                    }
                }
                if (currentPlayerFromServer.equals(Color.YELLOW)){
                    currentPlayerView.setText(GameFrame.yellow);
                    if (topic==1){
                        currentPlayerView.setForeground(Color.RED);
                    }
                    if (topic==2){
                        currentPlayerView.setForeground(Color.YELLOW);
                    }
                }
                if (currentPlayerFromServer.equals(Color.BLUE)){
                    currentPlayerView.setText(GameFrame.blue);
                    if (topic==1){
                        currentPlayerView.setForeground(new Color(255,215,0));
                    }
                    if (topic==2){
                        currentPlayerView.setForeground(Color.BLUE);
                    }
                }
            }catch (Exception e){

            }
        }
    }

    public void updateLabelByColor(Color color){
        if (color.equals(Color.RED)){
            currentPlayerView.setText(GameFrame.red);
            currentPlayerView.setForeground(Color.RED);
        }
        if (color.equals(Color.GREEN)){
            currentPlayerView.setText(GameFrame.green);
            currentPlayerView.setForeground(Color.GREEN);
        }
        if (color.equals(Color.YELLOW)){
            currentPlayerView.setText(GameFrame.yellow);
            currentPlayerView.setForeground(Color.YELLOW);
        }
        if (color.equals(Color.BLUE)){
            currentPlayerView.setText(GameFrame.blue);
            currentPlayerView.setForeground(Color.BLUE);
        }
    }

    public void saveBoard(){
        File file=null;
        JFileChooser chooser=new JFileChooser();
        FileNameExtensionFilter filter=new FileNameExtensionFilter("存档文件(dat文件)","dat");
        chooser.setFileFilter(filter);
        int ret=chooser.showOpenDialog(null);
        if (ret== JFileChooser.APPROVE_OPTION){
            file=chooser.getSelectedFile();
        }
        FileOutputStream out=null;
        try {
            out=new FileOutputStream(file);
            ObjectOutputStream objOut=new ObjectOutputStream(out);
            ChessBoard temp=new ChessBoard(model.getDimension(),model.getNumPlayer());
            temp.setGrid(model.getGrid());
//            temp.currentPlayer=this.currentPlayer;
            temp.mode=model.mode;
            objOut.writeObject(temp);
            objOut.flush();
            objOut.close();
            JOptionPane.showMessageDialog(null,"Save successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadBoard(){
        if (mode==2||mode==3){
            JOptionPane.showMessageDialog(view,"本模式不允许加载本地棋盘！");
            return;
        }




        File file = null;
        FileNameExtensionFilter filter=new FileNameExtensionFilter("存档文件","dat");
        JFileChooser chooser=new JFileChooser();
        chooser.setFileFilter(filter);
        int ret=chooser.showOpenDialog(view);
        if (ret==JFileChooser.APPROVE_OPTION){
            file=chooser.getSelectedFile();
        }
        FileInputStream in;
        try {
            in=new FileInputStream(file);
            ObjectInputStream objIn=new ObjectInputStream(in);
            ChessBoard localBoard=(ChessBoard)objIn.readObject();

            //检验存档是否合法：
            if (checkLoadBoardValid(localBoard)==false){
//                JOptionPane.showMessageDialog(null,"存档非法！");
                return;
            }

            JOptionPane.showMessageDialog(view,"加载成功");
            model=localBoard;
            model.registerListener(view);
            model.mode=this.mode;
            view.onChessBoardReload(model);
//            currentPlayer=model.currentPlayer;

//            System.out.println(currentPlayer);

            updateLabel();

            possibleMove.reSet(model,model.getNumPlayer());
            this.winners=new ArrayList<>();
            if (model.getNumPlayer()==2){
                this.winners.add(new Winner(Color.RED,2));
                this.winners.add(new Winner(Color.GREEN,2));
            }
            if (model.getNumPlayer()==4){
                this.winners.add(new Winner(Color.RED,4));
                this.winners.add(new Winner(Color.GREEN,4));
                this.winners.add(new Winner(Color.YELLOW,4));
                this.winners.add(new Winner(Color.BLUE,4));
            }
            this.finalWinner=null;
            this.isFinished=false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkLoadBoardValid(ChessBoard chessBoard){
        int numPlayer = chessBoard.getNumPlayer();
        Square[][] grid = chessBoard.getGrid();
        //检查棋子数量对不对
        int totalChessNum=0;
        for (int i=0;i<grid.length;i++){
            for (int j=0;j<grid[0].length;j++){
                if (grid[i][j].getPiece()!=null){
                    totalChessNum++;
                }
            }
        }

        if (numPlayer==2&&totalChessNum!=38){
            JOptionPane.showMessageDialog(view,"存档非法：棋子数量不正确！");
            return false;
        }
        if (numPlayer==4&&totalChessNum!=52){
            JOptionPane.showMessageDialog(view,"存档非法：棋子数量不正确！");
            return false;
        }

        //是否出现胜利方
        ArrayList<Winner> test=new ArrayList<>();
        if (chessBoard.getNumPlayer()==2){
            test.add(new Winner(Color.RED,2));
            test.add(new Winner(Color.GREEN,2));
        }
        if (chessBoard.getNumPlayer()==4){
            test.add(new Winner(Color.RED,4));
            test.add(new Winner(Color.GREEN,4));
            test.add(new Winner(Color.YELLOW,4));
            test.add(new Winner(Color.BLUE,4));
        }

        for (Winner winner:test){
            if (Winner.isWin(winner,chessBoard)){
                JOptionPane.showMessageDialog(view,"存档非法：已出现胜利方！");
                return false;
            }
        }

        //检查人数对不对：
        if (chessBoard.getNumPlayer()!=model.getNumPlayer()){
            JOptionPane.showMessageDialog(view,String.format("存档非法：人数与当前棋局不对应！当前棋局：%d人，存档：%d人",model.getNumPlayer(),chessBoard.getNumPlayer()));
            return false;
        }



        return true;




    }

    public void setCurrentPlayer(Color currentPlayer) {
        GameController.currentPlayer = currentPlayer;
    }

    public static void setCurrentPlayerFromServer(Color currentPlayerFromServer) {
        GameController.currentPlayerFromServer = currentPlayerFromServer;
    }

    public ChessBoardLocation getCurrent(){
        if (currentPlayerFromServer.equals(Color.RED)){
            return new ChessBoardLocation(0,0);
        }
        if (currentPlayerFromServer.equals(Color.YELLOW)){
            return new ChessBoardLocation(0,1);
        }
        if (currentPlayerFromServer.equals(Color.BLUE)){
            return new ChessBoardLocation(1,0);
        }
        if (currentPlayerFromServer.equals(Color.GREEN)){
            return new ChessBoardLocation(1,1);
        }
        return new ChessBoardLocation(0,0);
    }

    public Color nextPlayerLable() {
        if (model.getNumPlayer()==2){
            return currentPlayer == Color.RED ? Color.GREEN : Color.RED;
        }
        if (model.getNumPlayer()==4){
            if (currentPlayer==Color.RED){
                return Color.YELLOW;
            }
            if (currentPlayer==Color.YELLOW){
                return Color.GREEN;
            }
            if (currentPlayer==Color.GREEN){
                return Color.BLUE;
            }
            if (currentPlayer==Color.BLUE){
                return Color.RED;
            }
        }
        return currentPlayer;
    }

    public void isWin(){
        for (Winner winner:winners){
            if (Winner.isWin(winner,model)){
                finalWinner=winner;
                break;
            }
        }
        if (finalWinner!=null){
            audio.Play(4);
//            JOptionPane.showMessageDialog(view,String.format("恭喜"+"\""+finalWinner.toString()+"\""+"获得最终的胜利！"));
            if (topic==1){
                JOptionPane.showMessageDialog(view,String.format("恭喜"+"\""+finalWinner.toString()+"\""+"成功占领敌营!"));
            }
            if (topic==2){
                JOptionPane.showMessageDialog(view,String.format("恭喜"+"\""+finalWinner.toString()+"\""+"拯救了糖果王国！"));
            }
            this.isFinished=true;
        }
    }

    private void paintPossibleGrids(ChessBoardLocation src){
        Square[][] grid = model.getGrid();
        for (int i=0;i<16;i++){
            for (int j=0;j<16;j++){
                if (possibleMove.isHasJumped()){
                    if (model.isValidMove(src,grid[i][j].getLocation())){
                        view.getGridComponents()[i][j].canMoveTo=true;
                        view.getGridComponents()[i][j].repaint();
                    }
                }else {
                    if (model.isValidMove(src,grid[i][j].getLocation())){
                        possibleMove.setHasJumped(false);
                        view.getGridComponents()[i][j].canMoveTo=true;
                        view.getGridComponents()[i][j].repaint();
                    }
                }
            }
        }
    }

    private void swapGrids(){
        for (int i=0;i<16;i++){
            for (int j=0;j<16;j++){
                view.getGridComponents()[i][j].canMoveTo=false;
                view.getGridComponents()[i][j].repaint();
            }
        }
    }

    public void send(String string){
        clientOut.chatOnline.content=string;
        clientOut.chatOnline.userName=this.userName;
        clientOut.chatOnline.userId=userId;
        clientOut.chatOnline.player=getColorString(currentPlayer);
        clientOut.chatOnline.messageId=clientIn.messageId+1;
        clientOut.location[2]=getCurrent();
        clientOut.run();
        clientOut.chatOnline.content=null;
//        System.out.println(clientOut.chatOnline.content);
    }

    public void updateChat(ChatOnline chatOnline){
        gameFrame.chatText.append(chatOnline.toString()+"\n");
    }

    public String getColorString(Color color){
        if (color.equals(Color.RED)){
            return GameFrame.red;
        }
        if (color.equals(Color.YELLOW)){
            return GameFrame.yellow;
        }
        if (color.equals(Color.GREEN)){
            return GameFrame.green;
        }
        if (color.equals(Color.BLUE)){
            return GameFrame.blue;
        }
        return GameFrame.red;
    }

    public void stopBGM(){
        audio.stopPlay();
        this.loop.flag=false;
    }

    public void continueBGM(){
        audio.continuePlay();
        this.loop.flag=true;
        if (loop==null){
            if (topic==1){
                this.loop=new Loop(audio,7);
                this.loop.start();
            }
            if (topic==2){
                this.loop=new Loop(audio,9);
                this.loop.start();
            }
        }
    }

    public void done(){
        this.isFinished=true;
        JOptionPane.showMessageDialog(view,getColorString(currentPlayer)+"认输啦！");
    }

    public void withDraw(){
        if (mode==2||mode==3){
            JOptionPane.showMessageDialog(view,"局域网与网络模式不支持悔棋哦！");
            return;
        }
        if (moveLogs.size()==0){
            JOptionPane.showMessageDialog(view,"请先下棋！");
            return;
        }
        if (mode==0){
            if (model.getNumPlayer()==2){
                Move move = moveLogs.get(moveLogs.size()-1);
                model.moveChessPiece(move.des,move.src);
                moveLogs.remove(moveLogs.size()-1);
                JOptionPane.showMessageDialog(view,"悔棋成功！");
            }
            if (model.getNumPlayer()==4){
                Move move = moveLogs.get(moveLogs.size()-1);
                model.moveChessPiece(move.des,move.src);
                moveLogs.remove(moveLogs.size()-1);
                JOptionPane.showMessageDialog(view,"悔棋成功！");
            }
        }
        if (mode==1){
            if (model.getNumPlayer()==2){
                Move move = moveLogs.get(moveLogs.size()-2);
                model.moveChessPiece(move.des,move.src);
                moveLogs.remove(moveLogs.size()-1);
                JOptionPane.showMessageDialog(view,"悔棋成功！");
            }
            if (model.getNumPlayer()==4){
                Move move = moveLogs.get(moveLogs.size()-4);
                model.moveChessPiece(move.des,move.src);
                moveLogs.remove(moveLogs.size()-1);
                JOptionPane.showMessageDialog(view,"悔棋成功！");
            }
        }
    }

}
