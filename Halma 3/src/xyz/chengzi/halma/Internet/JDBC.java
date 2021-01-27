package xyz.chengzi.halma.Internet;

import javax.swing.*;
import java.io.Serializable;
import java.sql.*;

public class JDBC implements Serializable {
    private static final long serialVersionUID=1L;
    public static Connection connection = null;

    public static boolean connect() {
        String username = "liboyan";
        String passoword = "lby201318";
        String connectionUrl = "jdbc:mysql://rm-wz99gw07tizrm406kbo.mysql.rds.aliyuncs.com:3306/project?useUnicode=true&characterEncoding=UTF-8";
        try {
            connection = DriverManager.getConnection(connectionUrl, username, passoword);
            System.out.println("MySQL连接成功");
            return true;
        } catch (SQLException e) {
            System.out.println("MySQL连接失败");
            return false;
        }

    }

    public static LoginResult login(int userId, String password) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM login");

        boolean userExists = false;
        boolean passwordRight = false;
        String userName=null;
        int userPoints=0;
        while (resultSet.next()) {
            int user = resultSet.getInt("userId");
            String userPassword = resultSet.getString("passWord");
            String name=resultSet.getString("userName");
            int point=resultSet.getInt("points");
            if (userId == user) {
                userExists = true;
                if (userPassword.equals(password)) {
                    passwordRight = true;
                    userName=name;
                    userPoints=point;
                }
                break;
            }

        }
//        connection.close();
//        System.out.println("MySQL连接断开");

        return new LoginResult(userExists, passwordRight,userName,userPoints);
    }

    public static boolean register(int userId, String password, int security,String userName) {
        LoginResult result = null;
        try {
            result = login(userId, password);
        } catch (SQLException e) {
            System.out.println("查询失败");
        }

        if (result.userExist) {
            JOptionPane.showMessageDialog(null, "该用户已存在");
            return false;
        } else {
            String sql = String.format("INSERT INTO `project`.`login` (`userId`, `passWord`, `security`, `userName`) VALUES ('%d', '%s', '%d', '%s'); ", userId, password, security,userName);
            System.out.println("SQL" + sql);

            try {
                Statement statement = connection.createStatement();
                statement.execute(sql);
            } catch (SQLException e) {
                System.out.println("注册失败");
                return false;
            }

            JOptionPane.showMessageDialog(null, "注册成功");
            return true;
        }

    }


    public static boolean changePassword(int userId, String oldPassword, int security, String newPassword) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM login");
        boolean userExists = false;
        boolean passwordRight = false;
        boolean securityRight = false;
        while (resultSet.next()) {
            int user = resultSet.getInt("userId");
            String userPassword = resultSet.getString("passWord");
            int userSecurity = resultSet.getInt("security");
            if (userId == user) {
                userExists = true;
                if (userPassword.equals(oldPassword)) {
                    passwordRight = true;
                    if (userSecurity == security) {
                        securityRight = true;
                    }
                }
                break;
            }
        }

        if (userExists == false) {
            JOptionPane.showMessageDialog(null, "更改密码失败：该用户不存在");
            return false;
        } else {
            if (passwordRight == false) {
                JOptionPane.showMessageDialog(null, "更改密码失败：旧密码错误");
                return false;
            } else {
                if (securityRight == false) {
                    JOptionPane.showMessageDialog(null, "更改密码失败：安全码错误");
                    return false;
                } else {

                    String sql = String.format("UPDATE `project`.`login` SET `passWord` = '%s' WHERE `userId` = '%d';", newPassword, userId);
                    System.out.println("SQL" + sql);

                    try {
                        statement.execute(sql);
                    } catch (SQLException e) {
                        System.out.println("更改密码失败");
                        return false;
                    }
                    JOptionPane.showMessageDialog(null, "更改密码成功");
                    return true;
                }
            }
        }
    }




}