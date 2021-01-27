package xyz.chengzi.halma.view;

import javax.swing.*;
import java.awt.*;

public class SquareComponent extends JPanel {
    private Color color;
    public boolean canMoveTo=false;
    private int i;
    private int j;
    private Image image=null;

    public static int topic;
    public SquareComponent(int size, Color color,int i,int j) {
        setLayout(new GridLayout(1, 1)); // Use 1x1 grid layout
        setSize(size, size);
        this.color = color;
        this.i=i;
        this.j=j;
        int num=0;
        boolean flag=false;
        for (int m=0;m<16;m++){
            for (int n=0;n<16;n++){
                num++;
                if (m==i&&n==j){
                    flag=true;
                    break;
                }
            }
            if (flag==true){
                break;
            }
        }
        if (topic==1){
            this.image=new ImageIcon("src\\xyz\\chengzi\\halma\\chessImage\\chessBoard_"+num+".jpg").getImage().getScaledInstance(getWidth(),getHeight(),Image.SCALE_DEFAULT);
        }
        if (topic==2){
            this.image=new ImageIcon("src\\xyz\\chengzi\\halma\\chessImage\\Honey_"+num+".jpg").getImage().getScaledInstance(getWidth(),getHeight(),Image.SCALE_DEFAULT);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintSquare(g);
    }

    private void paintSquare(Graphics g) {
        g.setColor(color);
//        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(image,0,0,getWidth(),getHeight(),this);



        if (canMoveTo){
            ((Graphics2D) g).setStroke(new BasicStroke(6));
            g.setColor(Color.ORANGE);
        }else {
            ((Graphics2D) g).setStroke(new BasicStroke(1));
            g.setColor(Color.BLACK);
        }
        g.drawRect(0, 0, getWidth(), getHeight());

    }

//    public void paintPossible(Graphics g){
//        super.paintComponent(g);
//        g.setColor(Color.GREEN);
//        ((Graphics2D) g).setStroke(new BasicStroke(3));
//        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
//
//    }
}
