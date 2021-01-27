package xyz.chengzi.halma.model;

import java.io.Serializable;

public class Square implements Serializable {
    private static final long serialVersionUID=1L;
    private ChessBoardLocation location;
    private ChessPiece piece;

    public Square(ChessBoardLocation location) {
        this.location = location;
    }

    public ChessBoardLocation getLocation() {
        return location;
    }

    public ChessPiece getPiece() {
        return piece;
    }

    public void setPiece(ChessPiece piece) {
        this.piece = piece;
    }
}
