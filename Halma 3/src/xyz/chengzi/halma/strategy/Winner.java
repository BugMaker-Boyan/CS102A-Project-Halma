package xyz.chengzi.halma.strategy;

import xyz.chengzi.halma.model.ChessBoard;
import xyz.chengzi.halma.model.ChessBoardLocation;
import xyz.chengzi.halma.model.ChessPiece;
import xyz.chengzi.halma.model.Square;
import xyz.chengzi.halma.view.GameFrame;

import java.awt.*;
import java.io.Serializable;

public class Winner implements Serializable {
    private static final long serialVersionUID=1L;
    Color player;
    int numPlayer;
    Square[][] winState=new Square[16][16];
    public Winner(Color player,int numPlayer){
        this.player=player;
        this.numPlayer=numPlayer;
        initGrid();
        placeInitialPieces();
    }
    
    private void initGrid() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                winState[i][j] = new Square(new ChessBoardLocation(i, j));
            }
        }
    }
    private void placeInitialPieces() {
        if (this.numPlayer==2){
            if (this.player.equals(Color.RED)){
            winState[16 - 1][16 - 1].setPiece(new ChessPiece(Color.RED));
            winState[16 - 1][16 - 2].setPiece(new ChessPiece(Color.RED));
            winState[16 - 1][16 - 3].setPiece(new ChessPiece(Color.RED));
            winState[16 - 1][16 - 4].setPiece(new ChessPiece(Color.RED));
            winState[16 - 1][16 - 5].setPiece(new ChessPiece(Color.RED));
            winState[16 - 2][16 - 1].setPiece(new ChessPiece(Color.RED));
            winState[16 - 2][16 - 2].setPiece(new ChessPiece(Color.RED));
            winState[16 - 2][16 - 3].setPiece(new ChessPiece(Color.RED));
            winState[16 - 2][16 - 4].setPiece(new ChessPiece(Color.RED));
            winState[16 - 2][16 - 5].setPiece(new ChessPiece(Color.RED));
            winState[16 - 3][16 - 1].setPiece(new ChessPiece(Color.RED));
            winState[16 - 3][16 - 2].setPiece(new ChessPiece(Color.RED));
            winState[16 - 3][16 - 3].setPiece(new ChessPiece(Color.RED));
            winState[16 - 3][16 - 4].setPiece(new ChessPiece(Color.RED));
            winState[16 - 4][16 - 1].setPiece(new ChessPiece(Color.RED));
            winState[16 - 4][16 - 2].setPiece(new ChessPiece(Color.RED));
            winState[16 - 4][16 - 3].setPiece(new ChessPiece(Color.RED));
            winState[16 - 5][16 - 1].setPiece(new ChessPiece(Color.RED));
            winState[16 - 5][16 - 2].setPiece(new ChessPiece(Color.RED));
            }
            if (this.player.equals(Color.GREEN)){
                winState[0][0].setPiece(new ChessPiece(Color.GREEN));
                winState[0][1].setPiece(new ChessPiece(Color.GREEN));
                winState[0][2].setPiece(new ChessPiece(Color.GREEN));
                winState[0][3].setPiece(new ChessPiece(Color.GREEN));
                winState[0][4].setPiece(new ChessPiece(Color.GREEN));
                winState[1][0].setPiece(new ChessPiece(Color.GREEN));
                winState[1][1].setPiece(new ChessPiece(Color.GREEN));
                winState[1][2].setPiece(new ChessPiece(Color.GREEN));
                winState[1][3].setPiece(new ChessPiece(Color.GREEN));
                winState[1][4].setPiece(new ChessPiece(Color.GREEN));
                winState[2][0].setPiece(new ChessPiece(Color.GREEN));
                winState[2][1].setPiece(new ChessPiece(Color.GREEN));
                winState[2][2].setPiece(new ChessPiece(Color.GREEN));
                winState[2][3].setPiece(new ChessPiece(Color.GREEN));
                winState[3][0].setPiece(new ChessPiece(Color.GREEN));
                winState[3][1].setPiece(new ChessPiece(Color.GREEN));
                winState[3][2].setPiece(new ChessPiece(Color.GREEN));
                winState[4][0].setPiece(new ChessPiece(Color.GREEN));
                winState[4][1].setPiece(new ChessPiece(Color.GREEN));
            }
        }
        if (this.numPlayer==4){
            if (this.player.equals(Color.RED)) {
                //右下
                winState[16 - 1][16 - 1].setPiece(new ChessPiece(Color.RED));
                winState[16 - 1][16 - 2].setPiece(new ChessPiece(Color.RED));
                winState[16 - 1][16 - 3].setPiece(new ChessPiece(Color.RED));
                winState[16 - 1][16 - 4].setPiece(new ChessPiece(Color.RED));
                winState[16 - 2][16 - 1].setPiece(new ChessPiece(Color.RED));
                winState[16 - 2][16 - 2].setPiece(new ChessPiece(Color.RED));
                winState[16 - 2][16 - 3].setPiece(new ChessPiece(Color.RED));
                winState[16 - 2][16 - 4].setPiece(new ChessPiece(Color.RED));
                winState[16 - 3][16 - 1].setPiece(new ChessPiece(Color.RED));
                winState[16 - 3][16 - 2].setPiece(new ChessPiece(Color.RED));
                winState[16 - 3][16 - 3].setPiece(new ChessPiece(Color.RED));
                winState[16 - 4][16 - 1].setPiece(new ChessPiece(Color.RED));
                winState[16 - 4][16 - 2].setPiece(new ChessPiece(Color.RED));

            }
            if (this.player.equals(Color.GREEN)){
                //左上
                winState[0][0].setPiece(new ChessPiece(Color.GREEN));
                winState[0][1].setPiece(new ChessPiece(Color.GREEN));
                winState[0][2].setPiece(new ChessPiece(Color.GREEN));
                winState[0][3].setPiece(new ChessPiece(Color.GREEN));
                winState[1][0].setPiece(new ChessPiece(Color.GREEN));
                winState[1][1].setPiece(new ChessPiece(Color.GREEN));
                winState[1][2].setPiece(new ChessPiece(Color.GREEN));
                winState[1][3].setPiece(new ChessPiece(Color.GREEN));
                winState[2][0].setPiece(new ChessPiece(Color.GREEN));
                winState[2][1].setPiece(new ChessPiece(Color.GREEN));
                winState[2][2].setPiece(new ChessPiece(Color.GREEN));
                winState[3][0].setPiece(new ChessPiece(Color.GREEN));
                winState[3][1].setPiece(new ChessPiece(Color.GREEN));
            }
            if (this.player.equals(Color.BLUE)){
                //左下
                winState[16-1][0].setPiece(new ChessPiece(Color.BLUE));
                winState[16-1][1].setPiece(new ChessPiece(Color.BLUE));
                winState[16-1][2].setPiece(new ChessPiece(Color.BLUE));
                winState[16-1][3].setPiece(new ChessPiece(Color.BLUE));
                winState[16-2][0].setPiece(new ChessPiece(Color.BLUE));
                winState[16-2][1].setPiece(new ChessPiece(Color.BLUE));
                winState[16-2][2].setPiece(new ChessPiece(Color.BLUE));
                winState[16-2][3].setPiece(new ChessPiece(Color.BLUE));
                winState[16-3][0].setPiece(new ChessPiece(Color.BLUE));
                winState[16-3][1].setPiece(new ChessPiece(Color.BLUE));
                winState[16-3][2].setPiece(new ChessPiece(Color.BLUE));
                winState[16-4][0].setPiece(new ChessPiece(Color.BLUE));
                winState[16-4][1].setPiece(new ChessPiece(Color.BLUE));
            }
            if (this.player.equals(Color.YELLOW)){
                //右上
                winState[0][16-1].setPiece(new ChessPiece(Color.YELLOW));
                winState[0][16-2].setPiece(new ChessPiece(Color.YELLOW));
                winState[0][16-3].setPiece(new ChessPiece(Color.YELLOW));
                winState[0][16-4].setPiece(new ChessPiece(Color.YELLOW));
                winState[1][16-1].setPiece(new ChessPiece(Color.YELLOW));
                winState[1][16-2].setPiece(new ChessPiece(Color.YELLOW));
                winState[1][16-3].setPiece(new ChessPiece(Color.YELLOW));
                winState[1][16-4].setPiece(new ChessPiece(Color.YELLOW));
                winState[2][16-1].setPiece(new ChessPiece(Color.YELLOW));
                winState[2][16-2].setPiece(new ChessPiece(Color.YELLOW));
                winState[2][16-3].setPiece(new ChessPiece(Color.YELLOW));
                winState[3][16-1].setPiece(new ChessPiece(Color.YELLOW));
                winState[3][16-2].setPiece(new ChessPiece(Color.YELLOW));
            }
        }
    }

    public static boolean isWin(Winner winner, ChessBoard chessBoard){
        Square[][] currentGrid = chessBoard.getGrid();
        for(int i=0;i<16;i++){
            for (int j=0;j<16;j++){
                if (winner.winState[i][j].getPiece()!=null){
                    if (chessBoard.getGrid()[i][j].getPiece()==null){
                        return false;
                    }else {
                        if (chessBoard.getGrid()[i][j].getPiece().getColor().equals(winner.winState[i][j].getPiece().getColor())==false){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        if (player.equals(Color.RED)){
            return GameFrame.red;
        }
        if (player.equals(Color.GREEN)){
            return GameFrame.green;
        }
        if (player.equals(Color.BLUE)){
            return GameFrame.blue;
        }
        if (player.equals(Color.YELLOW)){
            return GameFrame.yellow;
        }
        return null;
    }

    public Square[][] getWinState() {
        return winState;
    }
}
