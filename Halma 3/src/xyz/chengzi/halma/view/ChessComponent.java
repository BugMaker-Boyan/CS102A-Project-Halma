package xyz.chengzi.halma.view;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ChessComponent extends JComponent {
    private Color color;
    private boolean selected;

//    public static  Image Red;
    public static Image Red=null;
    public static Image Green=null;
    public static Image Yellow=null;
    public static Image Blue=null;

    public ChessComponent(Color color,boolean isInJumping) {
        this.color = color;
        this.selected=isInJumping;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            paintChess(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void paintChess(Graphics g) throws IOException {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g.setColor(color);

        int spacing = (int) (getWidth() * 0.05);
//        g.fillOval(spacing, spacing, getWidth() - 2 * spacing, getHeight() - 2 * spacing);

        if (color.equals(Color.RED)){
            g.drawImage(Red,spacing,spacing,getWidth()-2*spacing,getHeight() - 2 * spacing,null);
        }
        if (color.equals(Color.YELLOW)){
            g.drawImage(Yellow,spacing,spacing,getWidth()-2*spacing,getHeight() - 2 * spacing,null);
        }
        if (color.equals(Color.GREEN)){
            g.drawImage(Green,spacing,spacing,getWidth()-2*spacing,getHeight() - 2 * spacing,null);
        }
        if (color.equals(Color.BLUE)){
            g.drawImage(Blue,spacing,spacing,getWidth()-2*spacing,getHeight() - 2 * spacing,null);
        }
        if (selected) { // Draw a + sign in the center of the piece
            g.setColor(new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue()));
            g.drawLine(getWidth() / 2, getHeight() / 4, getWidth() / 2, getHeight() * 3 / 4);
            g.drawLine(getWidth() / 4, getHeight() / 2, getWidth() * 3 / 4, getHeight() / 2);
            g.setColor(Color.RED);
            ((Graphics2D) g).setStroke(new BasicStroke(3));
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }
}
