package xyz.chengzi.halma.view;

import xyz.chengzi.halma.audio.WavePlayer;
import xyz.chengzi.halma.controller.GameController;
import xyz.chengzi.halma.model.ChessBoard;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public static String ip="127.0.0.1";
    public JTextArea chatText=new JTextArea();

    public static int userId;
    public static String userName;
    public static int points;

    public static String red=null;
    public static String green=null;
    public static String yellow=null;
    public static String blue=null;

//    public static int difficultyAi;


    public GameFrame(int playerNum,Color startPlayer,int mode) {
        if (mode==0){
            setTitle("跳棋-单机模式");
        }
        if (mode==1){
            setTitle("跳棋-人机模式");
        }
        if (mode==2){
            setTitle("跳棋-局域网对战模式");
        }
        if (mode==3){
            setTitle("跳棋-网络模式"+"用户名："+userName);
        }
        setSize(645, 780);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);


        ChessBoardComponent chessBoardComponent = new ChessBoardComponent(640, 16);
        ChessBoard chessBoard = new ChessBoard(16,playerNum);

        if (mode==2){
            GameController.LocalIp=ip;
        }

        GameController controller = new GameController(chessBoardComponent, chessBoard,mode);
        controller.gameFrame=this;
        controller.userId=GameFrame.userId;
        controller.userName=GameFrame.userName;
        controller.points=GameFrame.points;
//        controller.AiDifficulty=this.difficultyAi;

        controller.setCurrentPlayer(startPlayer);
        chessBoard.currentPlayer=startPlayer;
        add(chessBoardComponent,0);
        //显示当前玩家的标签
        JLabel playerHint=new JLabel("当前玩家：");
        playerHint.setBounds(20,650,200,50);
        playerHint.setFont(new Font("黑体",Font.BOLD,18));
        add(playerHint);
        JLabel player = new JLabel("");
        player.setLocation(115, 650);
        player.setSize(100, 50);
        player.setFont(new Font("黑体",Font.BOLD,18));
        add(player);
        controller.setCurrentPlayerView(player);
        controller.updateLabel();

        //显示游戏人数的标签
        JLabel numLabel=new JLabel("");
        numLabel.setLocation(20,680);
        numLabel.setSize(200,50);
        numLabel.setFont(new Font("黑体",Font.BOLD,18));
        add(numLabel);
        if (playerNum==2){
            numLabel.setText("游戏玩家：2人");
        }else if (playerNum==4){
            numLabel.setText("游戏玩家：4人");
        }


        //重置棋盘
        JButton restartButton = new JButton("重置棋盘");
        restartButton.addActionListener((e) -> controller.restart());
        restartButton.setLocation(185, 655);
        restartButton.setSize(100, 40);
        restartButton.setFont(new Font("黑体",Font.BOLD,15));
        add(restartButton);

        //保存期盘
        JButton saveBoard=new JButton("保存棋盘");
        saveBoard.setFont(new Font("黑体",Font.BOLD,15));
        saveBoard.setLocation(302,655);
        saveBoard.setSize(100,40);
        add(saveBoard);
        saveBoard.addActionListener((e)->controller.saveBoard());

        //加载棋盘
        JButton loadBoard=new JButton("加载棋盘");
        loadBoard.setFont(new Font("黑体",Font.BOLD,15));
        loadBoard.setLocation(302,700);
        loadBoard.setSize(100,40);
        add(loadBoard);
        loadBoard.addActionListener(e -> controller.loadBoard());

        //打开聊天室按钮
        JButton openChatButton=new JButton("聊天室");
        openChatButton.setFont(new Font("黑体",Font.BOLD,15));
        openChatButton.setBounds(534,655,100,40);
        add(openChatButton);
        openChatButton.addActionListener(e->{
            if (mode==0||mode==1){
                JOptionPane.showMessageDialog(this,"请在局域网模式或网络模式打开聊天室功能");
            }
            if (mode==2||mode==3){
                if (this.getHeight()==780){
                    this.setSize(640,950);
                }else {
                    this.setSize(640,780);
                }
            }
        });

        //聊天室本体：

        JScrollPane scrollPane=new JScrollPane(chatText);
        chatText.setFont(new Font("黑体",Font.BOLD,18));
        chatText.setEditable(false);
//        scrollPane.setFont(new Font("黑体",Font.BOLD,20));
        scrollPane.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(20,750,600,100);
        add(scrollPane);

        JTextField sendText=new JTextField();
        sendText.setBounds(20,860,530,30);
        add(sendText);

        JButton sendButton=new JButton("发送");
        sendButton.setFont(new Font("黑体",Font.BOLD,15));
        sendButton.setBounds(560,860,70,30);
        add(sendButton);
        sendButton.addActionListener(e -> {
            controller.send(sendText.getText());
            sendText.setText("");
        });


        JButton controlBGM=new JButton("暂停BGM");
        controlBGM.setFont(new Font("黑体",Font.BOLD,15));
        controlBGM.setBounds(185,700,100,40);
        add(controlBGM);

        controlBGM.addActionListener(e->{
            if (controller.bgm){
                controller.stopBGM();
                controller.bgm=false;
                controlBGM.setText("播放BGM");
            }else {
                controller.continueBGM();
                controller.bgm=true;
                controlBGM.setText("暂停BGM");
            }

        });

        JButton done=new JButton("认输");
        done.setFont(new Font("黑体",Font.BOLD,15));
        done.setBounds(422,655,100,40);
        add(done);

        done.addActionListener(e->controller.done());

        JButton withDraw=new JButton("悔棋");
        withDraw.setFont(new Font("黑体",Font.BOLD,15));
        withDraw.setBounds(422,700,100,40);
        add(withDraw);

        withDraw.addActionListener(e->controller.withDraw());

        JButton quit=new JButton("退出");
        quit.setFont(new Font("黑体",Font.BOLD,15));
        quit.setBounds(534,700,100,40);
        add(quit);

        quit.addActionListener(e->System.exit(0));
    }


}
