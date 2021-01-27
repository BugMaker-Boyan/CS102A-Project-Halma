package xyz.chengzi.halma.strategy;

import java.io.Serializable;

public class MinMaxResult implements Serializable {
    private static final long serialVersionUID=1L;
    public int score;
    public Move move;

    public MinMaxResult(int score, Move move) {
        this.score = score;
        this.move = move;
    }
}
