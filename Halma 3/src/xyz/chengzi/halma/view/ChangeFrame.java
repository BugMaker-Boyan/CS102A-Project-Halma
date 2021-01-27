package xyz.chengzi.halma.view;

import xyz.chengzi.halma.Internet.JDBC;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ChangeFrame extends JFrame {
    JLabel backGround=new JLabel();
    ImageIcon imageIcon1=new ImageIcon("src\\xyz\\chengzi\\halma\\image\\back1.jpg");
    ImageIcon imageIcon2=new ImageIcon("src\\xyz\\chengzi\\halma\\image\\back.jpg");


    JLabel user=new JLabel("登陆账号");
    JLabel oldPassword=new JLabel("旧密码");
    JLabel newPassword1=new JLabel("新密码");
    JLabel newPassword2=new JLabel("确认新密码");
    JLabel security=new JLabel("安全码");



    JTextField userText=new JTextField(16);
    JPasswordField oldPasswordText=new JPasswordField(16);
    JPasswordField newPasswordText1=new JPasswordField(16);
    JPasswordField newPasswordText2=new JPasswordField(16);
    JTextField securityText=new JTextField(16);

    JButton changeButton=new JButton("更改密码");

    public static int topic;
    public ChangeFrame(){
        setTitle("更改密码");
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





        user.setBounds(20,20,50,30);
        user.setForeground(textColor);
        userText.setBounds(90,20,180,30);
        add(user,0);
        add(userText,0);

        oldPassword.setBounds(20,70,50,30);
        oldPassword.setForeground(textColor);
        oldPasswordText.setBounds(90,70,180,30);
        add(oldPassword,0);
        add(oldPasswordText,0);

        newPassword1.setBounds(20,120,50,30);
        newPassword1.setForeground(textColor);
        newPasswordText1.setBounds(90,120,180,30);
        add(newPassword1,0);
        add(newPasswordText1,0);

        newPassword2.setBounds(20,170,70,30);
        newPassword2.setForeground(textColor);
        newPasswordText2.setBounds(90,170,180,30);
        add(newPassword2,0);
        add(newPasswordText2,0);

        security.setBounds(20,220,50,30);
        security.setForeground(textColor);
        securityText.setBounds(90,220,180,30);
        add(security,0);
        add(securityText,0);

        changeButton.setBounds(100,270,100,60);
        changeButton.setForeground(textColor);
        changeButton.addActionListener(e->{
            if (securityText.getText().length()==0){
                JOptionPane.showMessageDialog(null,"请输入安全码！");
            }

            if (String.valueOf(newPasswordText1.getPassword()).equals(String.valueOf(newPasswordText2.getPassword()))){
                try {
                    JDBC.changePassword(Integer.parseInt(userText.getText()),String.valueOf(oldPasswordText.getPassword()),Integer.parseInt(securityText.getText()),String.valueOf(newPasswordText1.getPassword()));
                } catch (SQLException ex) {
                    System.out.println("注册失败");
                }catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(this,"格式错误，请检查：\n账户名为16位以下纯数字，密码为16位以下数字和字母的组合，安全码为16位以下纯数字！");
                }
            }
        });
        add(changeButton,0);






    }
}
