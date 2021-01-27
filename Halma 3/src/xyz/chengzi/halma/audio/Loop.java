package xyz.chengzi.halma.audio;

import java.io.Serializable;

public class Loop extends Thread implements Serializable {
    private static final long serialVersionUID=1L;
    public boolean flag=true;

    WavePlayer player;
    int x;


    public Loop(WavePlayer player,int x){
        this.player=player;
        this.x=x;
    }
    @Override
    public void run() {
        while (flag){
            player.Play(x);
            try {
                if (x==7){
                    Thread.sleep(210*1000);
                }
                if (x==9){
                    Thread.sleep(136*1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
