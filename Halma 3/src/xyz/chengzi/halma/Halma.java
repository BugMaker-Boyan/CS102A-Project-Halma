package xyz.chengzi.halma;

import xyz.chengzi.halma.view.ChooseThemeFrame;
import xyz.chengzi.halma.view.LoginFrame;

import javax.swing.*;

public class Halma {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
//            LoginFrame loginFrame=new LoginFrame();
//            loginFrame.setVisible(true);
            ChooseThemeFrame chooseThemeFrame=new ChooseThemeFrame();
            chooseThemeFrame.setVisible(true);
        });
    }
}
