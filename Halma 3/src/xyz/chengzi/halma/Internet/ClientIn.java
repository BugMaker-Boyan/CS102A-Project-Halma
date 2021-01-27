package xyz.chengzi.halma.Internet;

import xyz.chengzi.halma.controller.GameController;
import xyz.chengzi.halma.model.ChessBoard;
import xyz.chengzi.halma.model.ChessBoardLocation;
import xyz.chengzi.halma.model.ChessPiece;
import java.awt.*;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;

public class ClientIn extends Thread implements Serializable {
    private static final long serialVersionUID=1L;
    private Socket socket;
    private ChessBoard chessBoard;
    public GameController controller;
    public boolean canAccept=true;
    public int messageId=0;
//    private String lastContent="";
    public ClientIn(Socket socket,ChessBoard chessBoard,GameController controller){
        this.socket=socket;
        this.chessBoard=chessBoard;
        this.controller=controller;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        ObjectInputStream objectInputStream=null;
        while (true){
            try {
                inputStream = socket.getInputStream();
                objectInputStream=new ObjectInputStream(inputStream);

                OnlineCommunicate onlineCommunicate = (OnlineCommunicate)objectInputStream.readObject();
                ChessBoardLocation[] o = onlineCommunicate.location;
                ChatOnline chatOnline=onlineCommunicate.chatOnline;
                if (o[2]!=null&&canAccept==true){
                    controller.setCurrentPlayerFromServer(getColorFromLocation(o[2]));
                    controller.updateLabel();
                }
                if (o[0]!=null&&o[1]!=null&&o[2]!=null&&canAccept==true){
                    ChessPiece chessPiece = chessBoard.getChessPieceAt(o[0]);
                    if (chessPiece!=null){
                        chessBoard.setChessPieceAt(o[1], chessBoard.removeChessPieceAt(o[0]));
                        controller.isWin();
                    }
                }

//                if (chatOnline.content.equals(lastContent)==false){
//                    controller.updateChat(chatOnline);
//                    this.lastContent=chatOnline.content;
////                    System.out.println(lastContent);
//                }
//                System.out.println(lastContent);
//                System.out.println(chatOnline==null);

                try {
                    if (chatOnline!=null&&chatOnline.content!=null&&chatOnline.messageId!=messageId){
                         controller.updateChat(chatOnline);
                         this.messageId=chatOnline.messageId;
                    }
                }catch (NullPointerException e){

                }
                Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    public Color getColorFromLocation(ChessBoardLocation location){
        if (location.getRow()==0&&location.getColumn()==0){
            return Color.RED;
        }
        if (location.getRow()==0&&location.getColumn()==1){
            return Color.YELLOW;
        }if (location.getRow()==1&&location.getColumn()==0){
            return Color.BLUE;
        }if (location.getRow()==1&&location.getColumn()==1){
            return Color.GREEN;
        }
        return Color.RED;
    }
}
