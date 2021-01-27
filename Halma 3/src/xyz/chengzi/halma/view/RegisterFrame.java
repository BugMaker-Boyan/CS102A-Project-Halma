package xyz.chengzi.halma.view;

import xyz.chengzi.halma.Internet.JDBC;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    JLabel backGround=new JLabel();

    public static int topic;

    ImageIcon imageIcon1=new ImageIcon("src\\xyz\\chengzi\\halma\\image\\back1.jpg");
    ImageIcon imageIcon2=new ImageIcon("src\\xyz\\chengzi\\halma\\image\\back.jpg");

    JLabel name=new JLabel("用户名：");
    JLabel user=new JLabel("账号：");
    JLabel password=new JLabel("密码：");
    JLabel security=new JLabel("安全码：");

    JTextField nameText=new JTextField(8);
    JTextField userText=new JTextField(16);
    JPasswordField passwordText=new JPasswordField(16);
    JTextField securityText=new JTextField(16);

    JButton registerButton=new JButton("注册账号");

    public RegisterFrame(){
        setTitle("注册账户");
        setSize(300,400);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(null);
        setResizable(false);

        Color textColor=null;
        if (topic==1){
            Image image = imageIcon1.getImage().getScaledInstance(getWidth(),getHeight(),Image.SCALE_DEFAULT);
            ImageIcon icon=new ImageIcon(image);
            backGround.setIcon(icon);
            textColor=new Color(203,163,26);
        }else {
            Image image = imageIcon2.getImage().getScaledInstance(getWidth(),getHeight(),Image.SCALE_DEFAULT);
            ImageIcon icon=new ImageIcon(image);
            backGround.setIcon(icon);
            textColor=new Color(223,137,140);
        }

        backGround.setBounds(0,0,getWidth()-1,getHeight()-1);
        add(backGround,-1);


        name.setBounds(20,50,50,30);
        name.setForeground(textColor);
        nameText.setBounds(80,50,180,30);
        add(name,0);
        add(nameText,0);


        user.setBounds(20,100,50,30);
        user.setForeground(textColor);
        userText.setBounds(80,100,180,30);
        add(user,0);
        add(userText,0);

        password.setBounds(20,150,50,30);
        password.setForeground(textColor);
        passwordText.setBounds(80,150,180,30);
        add(password,0);
        add(passwordText,0);

        security.setBounds(20,200,50,30);
        security.setForeground(textColor);
        securityText.setBounds(80,200,180,30);
        add(security,0);
        add(securityText,0);


        registerButton.setBounds(100,250,100,60);
        registerButton.setForeground(textColor);
        registerButton.addActionListener(e->{
            try{
                JDBC.register(Integer.parseInt(userText.getText()),String.valueOf(passwordText.getPassword()),Integer.parseInt(securityText.getText()),nameText.getText());
            }catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(this,"格式错误，请检查：\n账户名为16位以下纯数字，密码为16位以下数字和字母的组合，安全码为16位以下纯数字！");
            }


        });
        add(registerButton,0);

        JOptionPane.showMessageDialog(this,"提示：账户名为16位以下纯数字，密码为16位以下数字和字母的组合，安全码为16位以下纯数字！");

    }
}
