package xyz.chengzi.halma.strategy;

import xyz.chengzi.halma.model.ChessBoardLocation;

import java.io.Serializable;

public class Move implements Serializable {
    private static final long serialVersionUID=1L;
    public ChessBoardLocation src;
    public ChessBoardLocation des;

    public Move(ChessBoardLocation src, ChessBoardLocation des) {
        this.src = src;
        this.des = des;
    }
}
