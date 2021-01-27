package xyz.chengzi.halma.strategy;
import xyz.chengzi.halma.model.ChessBoard;
import xyz.chengzi.halma.model.ChessBoardLocation;

import java.awt.*;
import java.io.Serializable;

public class PossibleMove implements Serializable {
    private static final long serialVersionUID=1L;
    private ChessBoard chessBoard;
    private int num;
    private boolean hasJumped=false;


    public PossibleMove(int num, ChessBoard chessBoard){
        this.chessBoard=chessBoard;
        this.num=num;
    }

    public void reSet(ChessBoard chessBoard,int num){
        this.chessBoard=chessBoard;
        this.num=num;
        this.hasJumped=false;
    }

    private boolean isInEnemyCamp(Color currentPlayer, ChessBoardLocation src){
        Winner winner=new Winner(currentPlayer,num);
        boolean flag=false;
        for (int i=0;i<16;i++){
            for (int j=0;j<16;j++){
                if (winner.getWinState()[i][j].getPiece()!=null){
                    try {
                        if (winner.getWinState()[i][j].getLocation().getRow()==src.getRow()&&winner.getWinState()[i][j].getLocation().getColumn()==src.getColumn()){
                            flag=true;
                        }
                    } catch (Exception e) {

                    }
                }
            }
        }
        return flag;
    }

    private boolean isValidTypeOne(ChessBoardLocation src, ChessBoardLocation dest){
        if (chessBoard.getChessPieceAt(src) == null || chessBoard.getChessPieceAt(dest) != null) {
            return false;
        }
        int srcRow = src.getRow(), srcCol = src.getColumn(), destRow = dest.getRow(), destCol = dest.getColumn();
        int rowDistance = destRow - srcRow, colDistance = destCol - srcCol;
        if (Math.abs(rowDistance)>2||Math.abs(colDistance)>2) {
            return false;
        }else {
            //相邻周围一圈的情况，不跳越
            if (Math.abs(rowDistance) <= 1 && Math.abs(colDistance) <= 1&&hasJumped==false){
                return true;
            }else {
                //此时一定为跳跃的情况，行距和列距至少有一个等于2
                //上下跳跃
                if (Math.abs(rowDistance)==2&&Math.abs(colDistance)==0){
                    //往上跳
                    if (destRow<srcRow){
                        ChessBoardLocation location=new ChessBoardLocation(destRow+1,destCol);
                        if (chessBoard.getChessPieceAt(location)!=null){
                            hasJumped=true;
                            return true;
                        }else {
                            return false;
                        }
                    }else {
                        //往下跳
                        ChessBoardLocation location=new ChessBoardLocation(destRow-1,destCol);
                        if (chessBoard.getChessPieceAt(location)!=null){
                            hasJumped=true;
                            return true;
                        }else {
                            return false;
                        }
                    }
                }
                //左右跳跃
                if (Math.abs(rowDistance)==0&&Math.abs(colDistance)==2){
                    //往左跳
                    if (destCol<srcCol){
                        ChessBoardLocation location=new ChessBoardLocation(srcRow,srcCol-1);
                        if (chessBoard.getChessPieceAt(location)!=null){
                            hasJumped=true;
                            return true;
                        }else {
                            return false;
                        }
                    }else {
                        //往右跳
                        ChessBoardLocation location=new ChessBoardLocation(srcRow,srcCol+1);
                        if (chessBoard.getChessPieceAt(location)!=null){
                            hasJumped=true;
                            return true;
                        }else {
                            return false;
                        }

                    }
                }
                //斜跳
                if (Math.abs(rowDistance)==2&&Math.abs(colDistance)==2){
                    //左上跳
                    if (destRow<srcRow&&destCol<srcCol){
                        ChessBoardLocation location=new ChessBoardLocation(destRow+1,destCol+1);
                        if (chessBoard.getChessPieceAt(location)!=null){
                            hasJumped=true;
                            return true;
                        }else {
                            return false;
                        }
                    }else if (destRow>srcRow&&destCol>srcCol){
                        //右下跳
                        ChessBoardLocation location=new ChessBoardLocation(destRow-1,destCol-1);
                        if (chessBoard.getChessPieceAt(location)!=null){
                            hasJumped=true;
                            return true;
                        }else {
                            return false;
                        }
                    }else if (destRow<srcRow&&destCol>srcCol){
                        //右上跳
                        ChessBoardLocation location=new ChessBoardLocation(destRow+1,destCol-1);
                        if(chessBoard.getChessPieceAt(location)!=null){
                            hasJumped=true;
                            return true;
                        }else {
                            return false;
                        }
                    }else {
                        //左下跳
                        ChessBoardLocation location=new ChessBoardLocation(destRow-1,destCol+1);
                        if(chessBoard.getChessPieceAt(location)!=null){
                            hasJumped=true;
                            return true;
                        }else {
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isValidTypeTwo(ChessBoardLocation src, ChessBoardLocation dest, Winner winner){

        if (chessBoard.getChessPieceAt(src) == null || chessBoard.getChessPieceAt(dest) != null) {
            return false;
        }
        int srcRow = src.getRow(), srcCol = src.getColumn(), destRow = dest.getRow(), destCol = dest.getColumn();
        int rowDistance = destRow - srcRow, colDistance = destCol - srcCol;
        if (Math.abs(rowDistance)>2||Math.abs(colDistance)>2) {
            return false;
        }else {
            //必须限制在边界内才能走棋
            if (winner.getWinState()[dest.getRow()][dest.getColumn()].getPiece()!=null){
                //相邻周围一圈的情况，不跳越
                if (Math.abs(rowDistance) <= 1 && Math.abs(colDistance) <= 1&&hasJumped==false){
                    return true;
                }else {

                    //此时一定为跳跃的情况，行距和列距至少有一个等于2
                    //上下跳跃
                    if (Math.abs(rowDistance)==2&&Math.abs(colDistance)==0){
                        //往上跳
                        if (destRow<srcRow){
                            ChessBoardLocation location=new ChessBoardLocation(destRow+1,destCol);
                            if (chessBoard.getChessPieceAt(location)!=null){
                                hasJumped=true;
                                return true;
                            }else {
                                return false;
                            }
                        }else {
                            //往下跳
                            ChessBoardLocation location=new ChessBoardLocation(destRow-1,destCol);
                            if (chessBoard.getChessPieceAt(location)!=null){
                                hasJumped=true;
                                return true;
                            }else {
                                return false;
                            }
                        }
                    }
                    //左右跳跃
                    if (Math.abs(rowDistance)==0&&Math.abs(colDistance)==2){
                        //往左跳
                        if (destCol<srcCol){
                            ChessBoardLocation location=new ChessBoardLocation(srcRow,srcCol-1);
                            if (chessBoard.getChessPieceAt(location)!=null){
                                hasJumped=true;
                                return true;
                            }else {
                                return false;
                            }
                        }else {
                            //往右跳
                            ChessBoardLocation location=new ChessBoardLocation(srcRow,srcCol+1);
                            if (chessBoard.getChessPieceAt(location)!=null){
                                hasJumped=true;
                                return true;
                            }else {
                                return false;
                            }

                        }
                    }
                    //斜跳
                    if (Math.abs(rowDistance)==2&&Math.abs(colDistance)==2){
                        //左上跳
                        if (destRow<srcRow&&destCol<srcCol){
                            ChessBoardLocation location=new ChessBoardLocation(destRow+1,destCol+1);
                            if (chessBoard.getChessPieceAt(location)!=null){
                                hasJumped=true;
                                return true;
                            }else {
                                return false;
                            }
                        }else if (destRow>srcRow&&destCol>srcCol){
                            //右下跳
                            ChessBoardLocation location=new ChessBoardLocation(destRow-1,destCol-1);
                            if (chessBoard.getChessPieceAt(location)!=null){
                                hasJumped=true;
                                return true;
                            }else {
                                return false;
                            }
                        }else if (destRow<srcRow&&destCol>srcCol){
                            //右上跳
                            ChessBoardLocation location=new ChessBoardLocation(destRow+1,destCol-1);
                            if(chessBoard.getChessPieceAt(location)!=null){
                                hasJumped=true;
                                return true;
                            }else {
                                return false;
                            }
                        }else {
                            //左下跳
                            ChessBoardLocation location=new ChessBoardLocation(destRow-1,destCol+1);
                            if(chessBoard.getChessPieceAt(location)!=null){
                                hasJumped=true;
                                return true;
                            }else {
                                return false;
                            }
                        }
                    }
                }
            }else {
                return false;
            }
        }
        return false;
    }

    public boolean isValidMove(ChessBoardLocation src, ChessBoardLocation dest, Color currentPlayer){
        if (isInEnemyCamp(currentPlayer,src)){
            Winner winner=new Winner(currentPlayer,num);
            return isValidTypeTwo(src,dest,winner);
        }else{
            return isValidTypeOne(src,dest);
        }
    }

    public void setHasJumped(boolean hasJumped) {
        this.hasJumped = hasJumped;
    }

    public boolean isHasJumped() {
        return hasJumped;
    }




}
