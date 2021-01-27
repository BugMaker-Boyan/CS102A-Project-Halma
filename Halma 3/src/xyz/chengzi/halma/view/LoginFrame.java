package xyz.chengzi.halma.view;

import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import xyz.chengzi.halma.Internet.JDBC;
import xyz.chengzi.halma.Internet.LoginResult;
import xyz.chengzi.halma.audio.Loop;
import xyz.chengzi.halma.audio.WavePlayer;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Random;

public class LoginFrame extends JFrame{
    public int topic;

    JLabel backGround1=new JLabel();
    ImageIcon imageIconBack1=new ImageIcon("src\\xyz\\chengzi\\halma\\image\\Fighting-back.jpg");

    JLabel backGround2=new JLabel();
    ImageIcon imageIconBack2=new ImageIcon("src\\xyz\\chengzi\\halma\\image\\Honey.jpg");



    JLabel theme1=new JLabel("Fighting! 元素之战！");
    JLabel theme2=new JLabel("Honey! 拯救糖果王国！");

    JLabel user=new JLabel("登陆账号: ");
    JLabel password=new JLabel("登陆密码: ");
    JLabel playerNumText=new JLabel("玩家人数：");
    JLabel startPlayerText=new JLabel("选择开始方：");
    JLabel modeText=new JLabel("选择游戏模式：");
    JLabel ipLabel=new JLabel("请输入房主Ip：");
    JLabel hints1=new JLabel("");
    JLabel hints2=new JLabel("");
    JLabel hints3=new JLabel("");
    JLabel hints4=new JLabel("");
    JLabel hints=new JLabel("Hints:");

    JTextField userText=new JTextField(16);
    JPasswordField passwordText=new JPasswordField(16);
    JTextField ipText=new JTextField(16);

    JButton loginButton=new JButton("登陆");

    JButton registerFrameButton=new JButton("注册账户");
    JButton changePasswordFrameButton=new JButton("更改密码");

    JComboBox<Integer> playerNum=new JComboBox<>();
    JComboBox<String> startPlayer=new JComboBox<>();
    JComboBox<String> mode=new JComboBox<>();
    JComboBox<String> identity=new JComboBox<>();
//    JComboBox<String> AiDifficulty=new JComboBox<>();


    AudioStream as = null;


    public static String red=null;
    public static String green=null;
    public static String yellow=null;
    public static String blue=null;

    public static Color themeColor;
    public static Color commonColor;
    public static Color hintsColor;
    public LoginFrame(int topic){
        setTitle("跳棋游戏");
        setSize(1000,750);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);

        this.topic=topic;



        //Boy
        if (topic==1){
            theme1.setFont(new Font("楷体",Font.BOLD,60));
            theme1.setForeground(themeColor);
            theme1.setBounds(250,100,800,100);
            add(theme1,0);
            Image backImage = imageIconBack1.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);
            ImageIcon back=new ImageIcon(backImage);
            backGround1.setIcon(back);
            backGround1.setBounds(0,0,getWidth()-1,getHeight()-1);
            add(backGround1,-1);
        }

        //Girl
        if (topic==2){
            theme2.setFont(new Font("楷体",Font.BOLD,60));
            theme2.setForeground(themeColor);
            theme2.setBounds(250,100,800,100);
            add(theme2,0);
            Image backImage = imageIconBack2.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);
            ImageIcon back=new ImageIcon(backImage);
            backGround2.setIcon(back);
            backGround2.setBounds(0,0,getWidth()-1,getHeight()-1);
            add(backGround2,-1);
        }

        user.setLocation(220,230);
        user.setSize(150,50);
        user.setForeground(commonColor);
        user.setFont(new Font("楷体",Font.BOLD,25));
        password.setLocation(220,280);
        password.setSize(150,50);
        password.setForeground(commonColor);
        password.setFont(new Font("楷体",Font.BOLD,25));
        add(user,0);
        add(password,0);

        userText.setLocation(350,230);
        userText.setSize(300,40);
        userText.setFont(new Font("楷体",Font.BOLD,22));
        passwordText.setLocation(350,280);
        passwordText.setSize(300,40);
//        passwordText.setEchoChar('*');
        add(userText,0);
        add(passwordText,0);

        playerNumText.setLocation(220,330);
        playerNumText.setSize(150,50);
        playerNumText.setFont(new Font("楷体",Font.BOLD,25));
        playerNumText.setForeground(commonColor);
        add(playerNumText,0);

        playerNum.setLocation(350,335);
        playerNum.setSize(50,40);
        playerNum.setFont(new Font("楷体",Font.BOLD,20));
        playerNum.addItem(2);
        playerNum.addItem(4);
        playerNum.setSelectedIndex(0);
        playerNum.addActionListener(e->updateStartPlayerBox());
        add(playerNum,0);

        startPlayerText.setLocation(410,330);
        startPlayerText.setSize(200,50);
        startPlayerText.setFont(new Font("楷体",Font.BOLD,25));
        startPlayerText.setForeground(commonColor);
        add(startPlayerText,0);

        startPlayer.setLocation(570,335);
        startPlayer.setSize(80,40);
//        startPlayer.addItem("红方");
        startPlayer.addItem(red);
        startPlayer.addItem(green);
        startPlayer.addItem("随机");
        startPlayer.setFont(new Font("楷体",Font.BOLD,20));
        startPlayer.setSelectedIndex(0);
        add(startPlayer,0);



        modeText.setLocation(220,370);
        modeText.setSize(200,50);
        modeText.setFont(new Font("楷体",Font.BOLD,25));
        modeText.setForeground(commonColor);
        add(modeText,0);

        mode.setLocation(410,375);
        mode.setSize(130,40);
        mode.addItem("单机模式");
        mode.addItem("人机模式");
        mode.addItem("局域网模式");
        mode.addItem("网络模式");
        mode.setFont(new Font("楷体",Font.BOLD,20));
        mode.addActionListener(e->updateIdentity());
        add(mode,0);

        identity.setLocation(570,375);
        identity.setSize(80,40);
        identity.addItem("房主");
        identity.addItem("成员");
        identity.setFont(new Font("楷体",Font.BOLD,20));
        identity.addActionListener(e->updateIpText());
        add(identity,0);
        identity.setVisible(false);

//        AiDifficulty.setLocation(570,375);
//        AiDifficulty.setSize(80,40);
//        AiDifficulty.addItem("简单");
//        AiDifficulty.addItem("困难");
//        AiDifficulty.setFont(new Font("楷体",Font.BOLD,20));
//        AiDifficulty.addActionListener(e -> updateIpText());
//        add(AiDifficulty,0);
//        AiDifficulty.setVisible(false);

        ipLabel.setLocation(220,410);
        ipLabel.setSize(200,50);
        ipLabel.setFont(new Font("楷体",Font.BOLD,25));
        ipLabel.setForeground(commonColor);
        add(ipLabel,0);
        ipLabel.setVisible(false);

        ipText.setLocation(400,420);
        ipText.setSize(253,30);
        ipText.setFont(new Font("楷体",Font.BOLD,18));
        add(ipText,0);
        ipText.setVisible(false);


        hints1.setText("感谢您试玩本游戏！Welcome to play this Halma！");
        hints2.setText("本游戏支持四种模式：单机、人机、局域网联机、网络联机");
        hints3.setText("单机模式、人机模式以及局域网不需要填写账户与密码");
        hints4.setText("网络模式需要您输入账号与密码以登陆游戏!");

        hints.setForeground(commonColor);
        hints.setFont(new Font("楷体",Font.BOLD,30));
        hints.setBounds(420,460,300,30);
        hints1.setBounds(240,490,500,30);
        hints1.setForeground(hintsColor);
        hints1.setFont(new Font("楷体",Font.BOLD,20));
        hints2.setBounds(230,520,600,30);
        hints2.setForeground(hintsColor);
        hints2.setFont(new Font("楷体",Font.BOLD,20));
        hints3.setBounds(250,550,600,30);
        hints3.setForeground(hintsColor);
        hints3.setFont(new Font("楷体",Font.BOLD,20));
        hints4.setBounds(260,580,500,30);
        hints4.setForeground(hintsColor);
        hints4.setFont(new Font("楷体",Font.BOLD,20));
        add(hints1,0);
        add(hints2,0);
        add(hints3,0);
        add(hints4,0);
        add(hints,0);

//        toolBar.setFloatable(false);
//        toolBar.add(registerFrameButton);
//        toolBar.add(changePasswordFrameButton);
//        toolBar.setBounds(0,0,1200,60);
//        add(toolBar,0);
        loginButton.setLocation(670,230);
        loginButton.setSize(90,180);
        loginButton.setFont(new Font("楷体",Font.BOLD,20));
        loginButton.addActionListener(e->Login());
        add(loginButton,0);

        registerFrameButton.setBounds(765,230,90,90);
        registerFrameButton.setFont(new Font("楷体",Font.BOLD,12));
        changePasswordFrameButton.setBounds(765,320,90,90);
        changePasswordFrameButton.setFont(new Font("楷体",Font.BOLD,12));
        add(registerFrameButton,0);
        add(changePasswordFrameButton,0);

        registerFrameButton.addActionListener(e->{
            RegisterFrame registerFrame=new RegisterFrame();
            registerFrame.setVisible(true);
        });
        changePasswordFrameButton.addActionListener(e->{
            ChangeFrame changeFrame=new ChangeFrame();
            changeFrame.setVisible(true);
        });




        try {
            FileInputStream fileInputStream=null;
            if (topic==1){
                fileInputStream=new FileInputStream("src\\xyz\\chengzi\\halma\\audio\\bgm1.wav");
            }
            if (topic==2){
                fileInputStream=new FileInputStream("src\\xyz\\chengzi\\halma\\audio\\bgm3.wav");
            }
            as = new AudioStream(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AudioPlayer.player.start(as);
        JDBC.connect();


    }

    private void updateStartPlayerBox(){
        if ((int)playerNum.getSelectedItem()==2){
            startPlayer.removeAllItems();
            startPlayer.addItem(red);
            startPlayer.addItem(green);
            startPlayer.addItem("随机");
            startPlayer.setSelectedIndex(0);
        }
        if ((int)playerNum.getSelectedItem()==4){
            startPlayer.removeAllItems();
            startPlayer.addItem(red);
            startPlayer.addItem(blue);
            startPlayer.addItem(yellow);
            startPlayer.addItem(green);
            startPlayer.addItem("随机");
            startPlayer.setSelectedIndex(0);

        }
    }

    private void Login()  {
        //本地模式：
        if (getMode()==0||getMode()==1){
            this.setVisible(false);
            GameFrame.red=LoginFrame.red;
            GameFrame.green=LoginFrame.green;
            GameFrame.yellow=LoginFrame.yellow;
            GameFrame.blue=LoginFrame.blue;

            GameFrame mainFrame = new GameFrame(getNumPlayer(),getStartColor(),getMode());
            mainFrame.setVisible(true);

            AudioPlayer.player.stop(as);
            return;
        }

        //局域网模式
        if (getMode()==2){
            this.setVisible(false);

            GameFrame.red=LoginFrame.red;
            GameFrame.green=LoginFrame.green;
            GameFrame.yellow=LoginFrame.yellow;
            GameFrame.blue=LoginFrame.blue;

            System.out.println(getMode());
            System.out.println(identity.getSelectedItem());
            if (getMode()==2&&identity.getSelectedItem().equals("成员")){
                GameFrame.ip=ipText.getText();
                System.out.println(GameFrame.ip);
            }
//            GameFrame.userName="李伯岩";
            GameFrame.userName="局域网玩家";
            GameFrame mainFrame = new GameFrame(getNumPlayer(),getStartColor(),getMode());
            mainFrame.setVisible(true);
            AudioPlayer.player.stop(as);
            return;
        }




        //网络模式：
        if (getMode()==3){
            if (userText.getText().equals("")){
                JOptionPane.showMessageDialog(this,"请输入用户名");
                return;
            }
            if (String.valueOf(passwordText.getPassword()).equals("")){
                JOptionPane.showMessageDialog(this,"请输入密码");
                return;
            }
            try {
                LoginResult result = JDBC.login(Integer.parseInt(userText.getText()), String.valueOf(passwordText.getPassword()));
                if (result.userExist==false){
                    JOptionPane.showMessageDialog(this,"用户名不存在");
                }else {
                    if (result.passwordRight==false){
                        JOptionPane.showMessageDialog(this,"密码错误");
                    }else {
                        this.setVisible(false);

                        GameFrame.red=LoginFrame.red;
                        GameFrame.green=LoginFrame.green;
                        GameFrame.yellow=LoginFrame.yellow;
                        GameFrame.blue=LoginFrame.blue;

                        System.out.println(getMode());
                        System.out.println(identity.getSelectedItem());
//                        if (getMode()==2&&identity.getSelectedItem().equals("成员")){
//                            GameFrame.ip=ipText.getText();
//                            System.out.println(GameFrame.ip);
//                        }
                        GameFrame.userId=Integer.parseInt(userText.getText());
                        GameFrame.userName=result.userName;
                        GameFrame.points=result.points;
                        GameFrame mainFrame = new GameFrame(getNumPlayer(),getStartColor(),getMode());
                        mainFrame.setVisible(true);
                        AudioPlayer.player.stop(as);
                        JDBC.connection.close();
                    }
                }
            } catch (SQLException e) {
                System.out.println("网络错误，请检查连接");
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(this,"格式错误，请检查：\n账户名为16位以下纯数字，密码为16位以下数字和字母的组合，安全码为16位以下纯数字！");
            }


        }
    }

    private Color getStartColor(){
        if (startPlayer.getSelectedItem().equals(red)){
            return Color.RED;
        }
        if (startPlayer.getSelectedItem().equals(blue)){
            return Color.BLUE;
        }
        if (startPlayer.getSelectedItem().equals(yellow)){
            return Color.YELLOW;
        }
        if (startPlayer.getSelectedItem().equals(green)){
            return Color.GREEN;
        }
        if (startPlayer.getSelectedItem().equals("随机")){
            Random random=new Random();
            if (getNumPlayer()==2){
                int i = random.nextInt(100);
                if (i%2==0){
                    JOptionPane.showMessageDialog(this,red+"先下");
                    return Color.RED;
                }else {
                    JOptionPane.showMessageDialog(this,green+"先下");
                    return Color.GREEN;
                }
            }
            if (getNumPlayer()==4){
                int i = random.nextInt(4);
                if (i%4==0){
                    JOptionPane.showMessageDialog(this,red+"先下");
                    return Color.RED;
                }
                if (i%4==1){
                    JOptionPane.showMessageDialog(this,yellow+"先下");
                    return Color.YELLOW;
                }
                if (i%4==2){
                    JOptionPane.showMessageDialog(this,green+"先下");
                    return Color.GREEN;
                }
                if (i%4==3){
                    JOptionPane.showMessageDialog(this,blue+"先下");
                    return Color.BLUE;
                }
            }
        }
        return Color.RED;
    }
    private int getNumPlayer(){
        return (int)playerNum.getSelectedItem();
    }

    private int getMode(){
        if (mode.getSelectedItem().equals("单机模式")){
            return 0;
        }
        if (mode.getSelectedItem().equals("人机模式")){
            return 1;
        }if (mode.getSelectedItem().equals("局域网模式")){
            return 2;
        }if (mode.getSelectedItem().equals("网络模式")){
            return 3;
        }
        return 0;
    }

    private void updateIdentity(){
        if (getMode()==2){
            identity.setVisible(true);
            updateIpText();
        }else{
            identity.setVisible(false);
            ipText.setVisible(false);
            ipLabel.setVisible(false);
        }
    }

    private void updateIpText(){
        if (identity.getSelectedItem().equals("房主")){
            ipLabel.setVisible(false);
            ipText.setVisible(false);
        }else {
            ipLabel.setVisible(true);
            ipText.setVisible(true);
        }
    }
}
