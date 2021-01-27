package xyz.chengzi.halma.view;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import xyz.chengzi.halma.controller.GameController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

public class ChooseThemeFrame extends JFrame {
    JLabel background=new JLabel();
    ImageIcon imageIconBack=new ImageIcon("src\\xyz\\chengzi\\halma\\image\\chooseback.jpg");
    JLabel topic1=new JLabel("No Game,");
    JLabel topic2=new JLabel("No Life!");
    AudioStream as = null;
    public ChooseThemeFrame(){
        setTitle("");
        setSize(600,400);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);

        Image backImage = imageIconBack.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);
        ImageIcon back=new ImageIcon(backImage);
        background.setIcon(back);
        background.setBounds(0,0,getWidth()-1,getHeight()-1);
        add(background,-1);

        topic1.setFont(new Font("楷体",Font.BOLD,30));
        topic2.setFont(new Font("楷体",Font.BOLD,30));
        topic1.setBounds(420,0,400,30);
        topic2.setBounds(420,50,400,30);
        topic1.setForeground(Color.WHITE);
        topic2.setForeground(Color.WHITE);
        add(topic1,0);
        add(topic2,0);


        JButton choose1 =new JButton("Boy");
        choose1.setFont(new Font("楷体",Font.BOLD,20));
        choose1.setForeground(new Color(107,163,204));
        choose1.setBounds(30,270,100,50);
//        choose1.setMargin(new Insets(0,0,0,0));
//        choose1.setBorderPainted(false);
        Border boyBorder = BorderFactory.createLineBorder(new Color(107,163,204));
        choose1.setBorder(boyBorder);
        choose1.setFocusPainted(false);
//        choose1.setContentAreaFilled(false);
        add(choose1,0);

        JButton choose2=new JButton("Girl");
        choose2.setFont(new Font("楷体",Font.BOLD,20));
        choose2.setForeground(new Color(232,163,204));
        choose2.setBounds(460,270,100,50);
        Border girlBorder = BorderFactory.createLineBorder(new Color(232, 163, 204));
        choose2.setBorder(girlBorder);
        choose2.setFocusPainted(false);
//        choose2.setContentAreaFilled(false);
        add(choose2,0);


        choose1.addActionListener(e->{
            LoginFrame.red="青龙";
            LoginFrame.green="玄武";
            LoginFrame.yellow="朱雀";
            LoginFrame.blue="白虎";
            LoginFrame.themeColor=new Color(203,163,26);
            LoginFrame.commonColor=new Color(255,215,0);
            LoginFrame.hintsColor=new Color(106,194,242);

            ChessComponent.Red=new ImageIcon("src\\xyz\\chengzi\\halma\\image\\Fighting2.PNG").getImage();
            ChessComponent.Green=new ImageIcon("src\\xyz\\chengzi\\halma\\image\\Fighting4.PNG").getImage();
            ChessComponent.Yellow=new ImageIcon("src\\xyz\\chengzi\\halma\\image\\Fighting3.PNG").getImage();
            ChessComponent.Blue=new ImageIcon("src\\xyz\\chengzi\\halma\\image\\Fighting1.PNG").getImage();

            RegisterFrame.topic=1;
            ChangeFrame.topic=1;
            GameController.topic=1;
            SquareComponent.topic=1;

            LoginFrame loginFrame=new LoginFrame(1);
            loginFrame.setVisible(true);

            this.setVisible(false);
            AudioPlayer.player.stop(as);
        });
        choose2.addActionListener(e->{
            LoginFrame.red="水果糖";
            LoginFrame.green="牛奶糖";
            LoginFrame.yellow="杏仁糖";
            LoginFrame.blue="巧克力";

            LoginFrame.themeColor=new Color(223,137,140);
            LoginFrame.commonColor=new Color(226,173,132);
            LoginFrame.hintsColor=new Color(226,173,211);

            GameController.topic=2;
            RegisterFrame.topic=2;
            ChangeFrame.topic=2;

            ChessComponent.Red=new ImageIcon("src\\xyz\\chengzi\\halma\\image\\GirlRed.PNG").getImage();
            ChessComponent.Green=new ImageIcon("src\\xyz\\chengzi\\halma\\image\\GirlGreen.PNG").getImage();
            ChessComponent.Yellow=new ImageIcon("src\\xyz\\chengzi\\halma\\image\\GirlYellow.PNG").getImage();
            ChessComponent.Blue=new ImageIcon("src\\xyz\\chengzi\\halma\\image\\GirlBlue.PNG").getImage();

            SquareComponent.topic=2;

            LoginFrame loginFrame=new LoginFrame(2);
            loginFrame.setVisible(true);

            this.setVisible(false);
            AudioPlayer.player.stop(as);
        });

        try {
            FileInputStream fileInputStream=new FileInputStream("src\\xyz\\chengzi\\halma\\audio\\bgm0.wav");
            as = new AudioStream(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AudioPlayer.player.start(as);
    }
}
