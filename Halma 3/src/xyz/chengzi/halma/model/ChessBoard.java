package xyz.chengzi.halma.model;

import xyz.chengzi.halma.controller.GameController;
import xyz.chengzi.halma.listener.GameListener;
import xyz.chengzi.halma.listener.Listenable;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChessBoard implements Listenable<GameListener>, Serializable {
    private static final long serialVersionUID=1L;
    private List<GameListener> listenerList = new ArrayList<>();
    private Square[][] grid;
    private int dimension;
    private int numPlayer;
    public int mode=0;
    public Color currentPlayer;

    public ChessBoard(int dimension,int numPlayer) {
        this.grid = new Square[dimension][dimension];
        this.dimension = dimension;
        this.numPlayer=numPlayer;
        initGrid();
    }

    private void initGrid() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                grid[i][j] = new Square(new ChessBoardLocation(i, j));
            }
        }
    }

    public void placeInitialPieces() {
        for (int i=0;i<dimension;i++){
            for (int j=0;j<dimension;j++){
                grid[i][j].setPiece(null);
            }
        }
        if (numPlayer==2){
            grid[0][0].setPiece(new ChessPiece(Color.RED));
            grid[0][1].setPiece(new ChessPiece(Color.RED));
            grid[0][2].setPiece(new ChessPiece(Color.RED));
            grid[0][3].setPiece(new ChessPiece(Color.RED));
            grid[0][4].setPiece(new ChessPiece(Color.RED));
            grid[1][0].setPiece(new ChessPiece(Color.RED));
            grid[1][1].setPiece(new ChessPiece(Color.RED));
            grid[1][2].setPiece(new ChessPiece(Color.RED));
            grid[1][3].setPiece(new ChessPiece(Color.RED));
            grid[1][4].setPiece(new ChessPiece(Color.RED));
            grid[2][0].setPiece(new ChessPiece(Color.RED));
            grid[2][1].setPiece(new ChessPiece(Color.RED));
            grid[2][2].setPiece(new ChessPiece(Color.RED));
            grid[2][3].setPiece(new ChessPiece(Color.RED));
            grid[3][0].setPiece(new ChessPiece(Color.RED));
            grid[3][1].setPiece(new ChessPiece(Color.RED));
            grid[3][2].setPiece(new ChessPiece(Color.RED));
            grid[4][0].setPiece(new ChessPiece(Color.RED));
            grid[4][1].setPiece(new ChessPiece(Color.RED));

            grid[dimension - 1][dimension - 1].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 1][dimension - 2].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 1][dimension - 3].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 1][dimension - 4].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 1][dimension - 5].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 2][dimension - 1].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 2][dimension - 2].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 2][dimension - 3].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 2][dimension - 4].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 2][dimension - 5].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 3][dimension - 1].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 3][dimension - 2].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 3][dimension - 3].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 3][dimension - 4].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 4][dimension - 1].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 4][dimension - 2].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 4][dimension - 3].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 5][dimension - 1].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 5][dimension - 2].setPiece(new ChessPiece(Color.GREEN));


        }else if (numPlayer==4){
            //左上
            grid[0][0].setPiece(new ChessPiece(Color.RED));
            grid[0][1].setPiece(new ChessPiece(Color.RED));
            grid[0][2].setPiece(new ChessPiece(Color.RED));
            grid[0][3].setPiece(new ChessPiece(Color.RED));
            grid[1][0].setPiece(new ChessPiece(Color.RED));
            grid[1][1].setPiece(new ChessPiece(Color.RED));
            grid[1][2].setPiece(new ChessPiece(Color.RED));
            grid[1][3].setPiece(new ChessPiece(Color.RED));
            grid[2][0].setPiece(new ChessPiece(Color.RED));
            grid[2][1].setPiece(new ChessPiece(Color.RED));
            grid[2][2].setPiece(new ChessPiece(Color.RED));
            grid[3][0].setPiece(new ChessPiece(Color.RED));
            grid[3][1].setPiece(new ChessPiece(Color.RED));

            //右上
            grid[0][dimension-1].setPiece(new ChessPiece(Color.BLUE));
            grid[0][dimension-2].setPiece(new ChessPiece(Color.BLUE));
            grid[0][dimension-3].setPiece(new ChessPiece(Color.BLUE));
            grid[0][dimension-4].setPiece(new ChessPiece(Color.BLUE));
            grid[1][dimension-1].setPiece(new ChessPiece(Color.BLUE));
            grid[1][dimension-2].setPiece(new ChessPiece(Color.BLUE));
            grid[1][dimension-3].setPiece(new ChessPiece(Color.BLUE));
            grid[1][dimension-4].setPiece(new ChessPiece(Color.BLUE));
            grid[2][dimension-1].setPiece(new ChessPiece(Color.BLUE));
            grid[2][dimension-2].setPiece(new ChessPiece(Color.BLUE));
            grid[2][dimension-3].setPiece(new ChessPiece(Color.BLUE));
            grid[3][dimension-1].setPiece(new ChessPiece(Color.BLUE));
            grid[3][dimension-2].setPiece(new ChessPiece(Color.BLUE));

            //左下
            grid[dimension-1][0].setPiece(new ChessPiece(Color.YELLOW));
            grid[dimension-1][1].setPiece(new ChessPiece(Color.YELLOW));
            grid[dimension-1][2].setPiece(new ChessPiece(Color.YELLOW));
            grid[dimension-1][3].setPiece(new ChessPiece(Color.YELLOW));
            grid[dimension-2][0].setPiece(new ChessPiece(Color.YELLOW));
            grid[dimension-2][1].setPiece(new ChessPiece(Color.YELLOW));
            grid[dimension-2][2].setPiece(new ChessPiece(Color.YELLOW));
            grid[dimension-2][3].setPiece(new ChessPiece(Color.YELLOW));
            grid[dimension-3][0].setPiece(new ChessPiece(Color.YELLOW));
            grid[dimension-3][1].setPiece(new ChessPiece(Color.YELLOW));
            grid[dimension-3][2].setPiece(new ChessPiece(Color.YELLOW));
            grid[dimension-4][0].setPiece(new ChessPiece(Color.YELLOW));
            grid[dimension-4][1].setPiece(new ChessPiece(Color.YELLOW));

            //右下
            grid[dimension - 1][dimension - 1].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 1][dimension - 2].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 1][dimension - 3].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 1][dimension - 4].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 2][dimension - 1].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 2][dimension - 2].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 2][dimension - 3].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 2][dimension - 4].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 3][dimension - 1].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 3][dimension - 2].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 3][dimension - 3].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 4][dimension - 1].setPiece(new ChessPiece(Color.GREEN));
            grid[dimension - 4][dimension - 2].setPiece(new ChessPiece(Color.GREEN));
        }
        listenerList.forEach(listener -> listener.onChessBoardReload(this));
    }

    public Square getGridAt(ChessBoardLocation location) {
        return grid[location.getRow()][location.getColumn()];
    }

    public ChessPiece getChessPieceAt(ChessBoardLocation location) {
        return getGridAt(location).getPiece();
    }

    public void setChessPieceAt(ChessBoardLocation location, ChessPiece piece) {
        getGridAt(location).setPiece(piece);
        listenerList.forEach(listener -> listener.onChessPiecePlace(location, piece));
    }

    public ChessPiece removeChessPieceAt(ChessBoardLocation location) {
        ChessPiece piece = getGridAt(location).getPiece();
        getGridAt(location).setPiece(null);
        listenerList.forEach(listener -> listener.onChessPieceRemove(location));
        return piece;
    }

    public void moveChessPiece(ChessBoardLocation src, ChessBoardLocation dest) {
        if (!isValidMove(src, dest)) {
//            System.out.printf("%d %d,%d %d",src.getRow(),src.getColumn(),dest.getRow(),dest.getColumn());
//            throw new IllegalArgumentException("Illegal halma move");
        }
        setChessPieceAt(dest, removeChessPieceAt(src));
    }

    public int getDimension() {
        return dimension;
    }

    public boolean isValidMove(ChessBoardLocation src, ChessBoardLocation dest) {
        if (mode==0||mode==1){
            return GameController.possibleMove.isValidMove(src,dest,GameController.currentPlayer);
        }else {
            return GameController.possibleMove.isValidMove(src,dest,GameController.currentPlayer)&&GameController.currentPlayer.equals(GameController.currentPlayerFromServer);
        }
    }

    @Override
    public void registerListener(GameListener listener) {
        listenerList.add(listener);
    }

    @Override
    public void unregisterListener(GameListener listener) {
        listenerList.remove(listener);
    }

    public int getNumPlayer() {
        return numPlayer;
    }

    public void setNumPlayer(int numPlayer) {
        this.numPlayer = numPlayer;
    }

    public Square[][] getGrid() {
        return grid;
    }

    public void setGrid(Square[][] grid) {
        this.grid = grid;
    }
}
