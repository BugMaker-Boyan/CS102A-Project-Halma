package xyz.chengzi.halma.strategy;

import com.sun.org.apache.bcel.internal.generic.CHECKCAST;
import com.sun.org.apache.bcel.internal.generic.GETFIELD;
import xyz.chengzi.halma.model.ChessBoard;
import xyz.chengzi.halma.model.ChessBoardLocation;
import xyz.chengzi.halma.model.Square;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class AI implements Serializable {

    public static Color AiColor=null;
    private static final long serialVersionUID=1L;
//    public static MinMaxResult maxResult(ChessBoard chessBoard, int depth, int alpha, int beta, Color player,int num,int difficulty){
//        if (depth==0){
//            return new MinMaxResult(evaluate(chessBoard,player,difficulty),null);
//        }
//        ArrayList<Move> legalMoves = getLegalMoves(chessBoard, player, num);
//        if (legalMoves.size()==0){
//            if (getLegalMoves(chessBoard,nextPlayer(player,num),num).size()==0){
//                return new MinMaxResult(evaluate(chessBoard,player,difficulty),null);
//            }
//            return minResult(chessBoard,depth,alpha,beta,nextPlayer(player,num),num,difficulty);
//        }
//
//        ChessBoard temp = copyBoard(chessBoard, num);
//        int best=Integer.MIN_VALUE;
//        Move move=null;
//
//        for (int i=0;i<legalMoves.size();i++){
//            alpha=Math.max(best,alpha);
//            if (alpha>=beta){
//                break;
//            }
//            temp.moveChessPiece(legalMoves.get(i).src,legalMoves.get(i).des);
//            int value=minResult(temp,depth-1,Math.max(best,alpha),beta,nextPlayer(player,num),num,difficulty).score;
//            if (value>best){
//                best=value;
//                move=legalMoves.get(i);
//            }
//            temp.moveChessPiece(legalMoves.get(i).des,legalMoves.get(i).src);
//        }
//        return new MinMaxResult(best,move);
//    }
//
//    public static MinMaxResult minResult(ChessBoard chessBoard, int depth, int alpha, int beta, Color player,int num,int difficulty){
//        if (depth==0){
//            return new MinMaxResult(evaluate(chessBoard,player,difficulty),null);
//        }
//        ArrayList<Move> legalMoves = getLegalMoves(chessBoard, player, num);
////        if (legalMoves.size()==0){
////            if (getLegalMoves(chessBoard,nextPlayer(player,num),num).size()==0){
////                return new MinMaxResult(evaluate(chessBoard,player,difficulty),null);
////            }
////            return maxResult(chessBoard,depth,alpha,beta,nextPlayer(player,num),num,difficulty);
////        }
////        ChessBoard temp = copyBoard(chessBoard, num);
//        int best=Integer.MAX_VALUE;
//        Move move =null;
//
//        for (int i=0;i<legalMoves.size();i++){
//            beta=Math.min(best,best);
//            if (alpha>=beta){
//                break;
//            }
//            temp.moveChessPiece(legalMoves.get(i).src,legalMoves.get(i).des);
//            int value=maxResult(temp,depth-1,alpha,Math.min(best,beta),player,num,difficulty).score;
//            if (value<best){
//                best=value;
//                move=legalMoves.get(i);
//            }
//            temp.moveChessPiece(legalMoves.get(i).des,legalMoves.get(i).src);
//
//        }
//        return new MinMaxResult(best,move);
//    }

    private static ArrayList<Move> getLegalMoves(ChessBoard chessBoard, Color chessColor,int num){
        Square[][] grid = chessBoard.getGrid();
        ArrayList<Move> moves=new ArrayList<>();
        PossibleMove pm=new PossibleMove(num,chessBoard);
        for (int i=0;i<chessBoard.getDimension();i++){
            for (int j=0;j<chessBoard.getDimension();j++){
                ChessBoardLocation src =new ChessBoardLocation(i,j);
                if (grid[i][j].getPiece()!=null&&grid[i][j].getPiece().getColor().equals(chessColor)){
                    for (int m=0;m<chessBoard.getDimension();m++){
                        for (int n=0;n<chessBoard.getDimension();n++){
                            ChessBoardLocation dest=new ChessBoardLocation(m,n);
                            pm.setHasJumped(false);
                            if (pm.isValidMove(src,dest,chessColor)){
                                moves.add(new Move(src,dest));
                            }
                        }
                    }
                }
            }
        }
        return moves;
    }

//    private static int evaluate(ChessBoard chessBoard,Color player){
//        int[][] score=new int[16][16];
//        if (player.equals(Color.RED)){
//            for (int i=0;i<16;i++){
//                for (int j=0;j<16;j++){
//                    score[i][j]=(i+1)*(j+1);
//                }
//            }
//        }
//        if (player.equals(Color.GREEN)){
//            for (int i=15;i>=0;i--){
//                for (int j=15;j>=0;j--){
//                    score=new int[][]{
//                            {100,90,81,73,66,60,55,54,51,49,48,47,46,45,44,43},
//                            {90,81,73,66,60,55,54,53,52,51,50,49,48,47,46,45},
//                            {81,73,66,60,57,54,53,52,51,50,49,48,47,46,45,44},
//                            {73,66,60,59,58,57,56,55,54,53,52,51,50,49,48,47},
//                            {66,60,59,58,57,55,56,54,53,52,51,50,49,48,47,46},
//                            {40,39,38,37,36,35,34,33,32,31,30,29,28,27,26,25},
//                            {38,37,36,35,34,33,32,31,30,29,28,27,26,25,24,23},
//                            {36,35,34,32,31,30,29,28,27,26,25,24,23,22,21,20},
//                            {34,33,32,31,30,20,19,18,17,16,15,14,13,12,11,10},
//                            {32,31,30,29,28,27,26,25,24,23,22,21,20,19,18,17},
//                            {30,29,28,27,26,25,24,23,22,21,20,19,18,17,16,15},
//                            {28,27,26,25,24,23,22,21,20,19,18,17,16,15,14,13},
//                            {26,25,24,23,22,21,20,19,18,17,16,15,14,13,12,11},
//                            {24,23,22,21,20,19,18,17,16,15,14,13,12,11,10,9},
//                            {22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7},
//                            {20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5}
//                    };
//
//                }
//            }
//        }
//        if (player.equals(Color.BLUE)){
//            for (int i=0;i<16;i++){
//                for (int j=15;j>=0;j--){
//                    score[i][j]=(i+1)*(16-j);
//                }
//            }
//        }
//        if (player.equals(Color.YELLOW)){
//            for (int i=15;i>=0;i--){
//                for (int j=0;j<16;j++){
//                    score[i][j]=(16-i)*(j+1);
//                }
//            }
//        }
//        Square[][] grid = chessBoard.getGrid();
//        int total=0;
//        for (int i=0;i<chessBoard.getDimension();i++){
//            for (int j=0;j<chessBoard.getDimension();j++){
//                if (grid[i][j].getPiece()!=null){
//                    if (grid[i][j].getPiece().getColor().equals(player)){
//                        total+=score[i][j];
//                    }else {
//                        total-=score[i][j];
//                    }
//                }
//            }
//        }
//
//        return total;
//    }


    //红方为人机，计算红方得分：
    //以下皆为困难人机模式评估函数
    private static int evaluateRedFor2(ChessBoard chessBoard){
        int[][] score=null;
        score=new int[][]{
                {169,171,171,173,174,0,0,0,0,0,0,0,0,0,0,0},
                {171,170,172,176,178,0,0,0,0,0,0,0,0,0,0,0},
                {171,172,175,180,179,0,0,0,0,0,0,0,0,0,0,0},
                {173,176,180,185,190,0,0,0,0,0,0,0,0,0,0,0},
                {174,178,179,193,200,210,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,215,220,223,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,230,245,255,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,261,275,280,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,289,310,320,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,323,350,369,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,360,400,410,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,420,460,465,470,645,650},
                {0,0,0,0,0,0,0,0,0,0,0,466,530,570,675,680},
                {0,0,0,0,0,0,0,0,0,0,0,470,570,610,690,710},
                {0,0,0,0,0,0,0,0,0,0,0,645,675,690,700,740},
                {0,0,0,0,0,0,0,0,0,0,0,650,680,710,740,800}
        };
        Square[][] grid = chessBoard.getGrid();
        int total=0;
        for (int i=0;i<chessBoard.getDimension();i++){
            for (int j=0;j<chessBoard.getDimension();j++){
                if (grid[i][j].getPiece()!=null){
                    total+=score[i][j];
                }
            }
        }

        return total;
    }

    private static int evaluateGreenFor2(ChessBoard chessBoard){
        int[][] score=null;
        score=new int[][]{
                {800,740,710,680,650,0,0,0,0,0,0,0,0,0,0,0},
                {740,700,690,675,645,0,0,0,0,0,0,0,0,0,0,0},
                {710,690,610,570,470,0,0,0,0,0,0,0,0,0,0,0},
                {680,675,570,530,466,0,0,0,0,0,0,0,0,0,0,0},
                {650,645,470,465,460,420,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,410,400,360,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,369,350,323,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,320,310,289,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,280,275,261,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,255,245,230,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,223,220,215,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,210,200,193,179,178,174},
                {0,0,0,0,0,0,0,0,0,0,0,190,185,180,176,173},
                {0,0,0,0,0,0,0,0,0,0,0,179,180,175,172,171},
                {0,0,0,0,0,0,0,0,0,0,0,178,176,172,170,171},
                {0,0,0,0,0,0,0,0,0,0,0,174,173,171,171,169}
        };
        Square[][] grid = chessBoard.getGrid();
        int total=0;
        for (int i=0;i<chessBoard.getDimension();i++){
            for (int j=0;j<chessBoard.getDimension();j++){
                if (grid[i][j].getPiece()!=null){
                    total+=score[i][j];
                }
            }
        }

        return total;
    }

    private static int evaluateYellowFor2(ChessBoard chessBoard){
        int[][] score=null;
        score=new int[][]{
                {0,0,0,0,0,0,0,0,0,0,0,650,680,710,740,800},
                {0,0,0,0,0,0,0,0,0,0,0,645,675,690,700,740},
                {0,0,0,0,0,0,0,0,0,0,0,470,570,610,690,710},
                {0,0,0,0,0,0,0,0,0,0,0,466,530,570,675,680},
                {0,0,0,0,0,0,0,0,0,0,420,460,465,470,645,650},
                {0,0,0,0,0,0,0,0,0,360,400,410,0,0,0,0},
                {0,0,0,0,0,0,0,0,323,350,369,0,0,0,0,0},
                {0,0,0,0,0,0,0,289,310,320,0,0,0,0,0,0},
                {0,0,0,0,0,0,261,275,280,0,0,0,0,0,0,0},
                {0,0,0,0,0,230,245,255,0,0,0,0,0,0,0,0},
                {0,0,0,0,215,220,223,0,0,0,0,0,0,0,0,0},
                {174,178,179,193,200,210,0,0,0,0,0,0,0,0,0,0},
                {173,176,180,185,190,0,0,0,0,0,0,0,0,0,0,0},
                {171,172,175,180,179,0,0,0,0,0,0,0,0,0,0,0},
                {171,170,172,176,178,0,0,0,0,0,0,0,0,0,0,0},
                {169,171,171,173,174,0,0,0,0,0,0,0,0,0,0,0}
        };
        Square[][] grid = chessBoard.getGrid();
        int total=0;
        for (int i=0;i<chessBoard.getDimension();i++){
            for (int j=0;j<chessBoard.getDimension();j++){
                if (grid[i][j].getPiece()!=null){
                    total+=score[i][j];
                }
            }
        }

        return total;
    }

    private static int evaluateBlueFor2(ChessBoard chessBoard){
        int[][] score=null;
        score=new int[][]{
                {0,0,0,0,0,0,0,0,0,0,0,174,173,171,171,169},
                {0,0,0,0,0,0,0,0,0,0,0,178,176,172,170,171},
                {0,0,0,0,0,0,0,0,0,0,0,179,180,175,172,171},
                {0,0,0,0,0,0,0,0,0,0,0,190,185,180,176,173},
                {0,0,0,0,0,0,0,0,0,0,210,200,193,179,178,174},
                {0,0,0,0,0,0,0,0,0,223,220,215,0,0,0,0},
                {0,0,0,0,0,0,0,0,255,245,230,0,0,0,0,0},
                {0,0,0,0,0,0,0,280,275,261,0,0,0,0,0,0},
                {0,0,0,0,0,0,320,310,289,0,0,0,0,0,0,0},
                {0,0,0,0,0,369,350,323,0,0,0,0,0,0,0,0},
                {0,0,0,0,410,400,360,0,0,0,0,0,0,0,0,0},
                {650,645,470,465,460,420,0,0,0,0,0,0,0,0,0,0},
                {680,675,570,530,466,0,0,0,0,0,0,0,0,0,0,0},
                {710,690,610,570,470,0,0,0,0,0,0,0,0,0,0,0},
                {740,700,690,675,645,0,0,0,0,0,0,0,0,0,0,0},
                {800,740,710,680,650,0,0,0,0,0,0,0,0,0,0,0}
        };
        Square[][] grid = chessBoard.getGrid();
        int total=0;
        for (int i=0;i<chessBoard.getDimension();i++){
            for (int j=0;j<chessBoard.getDimension();j++){
                if (grid[i][j].getPiece()!=null){
                    total+=score[i][j];
                }
            }
        }

        return total;
    }

    private static int evaluateRedFor4(ChessBoard chessBoard){
        int[][] score=null;
        score=new int[][]{
                {169,171,171,173,0,0,0,0,0,0,0,0,0,0,0,0},
                {171,170,172,176,0,0,0,0,0,0,0,0,0,0,0,0},
                {171,172,175,180,0,0,0,0,0,0,0,0,0,0,0,0},
                {173,176,180,185,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,179,193,200,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,215,220,223,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,230,245,255,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,261,275,280,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,289,310,320,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,323,350,369,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,360,400,410,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,420,460,465,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,530,570,675,680},
                {0,0,0,0,0,0,0,0,0,0,0,0,540,610,690,710},
                {0,0,0,0,0,0,0,0,0,0,0,0,675,690,700,740},
                {0,0,0,0,0,0,0,0,0,0,0,0,680,710,740,800}
        };
        Square[][] grid = chessBoard.getGrid();
        int total=0;
        for (int i=0;i<chessBoard.getDimension();i++){
            for (int j=0;j<chessBoard.getDimension();j++){
                if (grid[i][j].getPiece()!=null){
                    total+=score[i][j];
                }
            }
        }

        return total;
    }

    private static int evaluateGreenFor4(ChessBoard chessBoard){
        int[][] score=null;
        score=new int[][]{
                {800,740,710,680,0,0,0,0,0,0,0,0,0,0,0,0},
                {740,700,690,675,0,0,0,0,0,0,0,0,0,0,0,0},
                {710,690,610,570,0,0,0,0,0,0,0,0,0,0,0,0},
                {680,675,570,530,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,465,460,420,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,410,400,360,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,369,350,323,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,320,310,289,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,280,275,261,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,255,245,230,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,223,220,215,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,200,193,179,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,185,180,176,173},
                {0,0,0,0,0,0,0,0,0,0,0,0,180,175,172,171},
                {0,0,0,0,0,0,0,0,0,0,0,0,176,172,170,171},
                {0,0,0,0,0,0,0,0,0,0,0,0,173,171,171,169}
        };
        Square[][] grid = chessBoard.getGrid();
        int total=0;
        for (int i=0;i<chessBoard.getDimension();i++){
            for (int j=0;j<chessBoard.getDimension();j++){
                if (grid[i][j].getPiece()!=null){
                    total+=score[i][j];
                }
            }
        }

        return total;
    }

    private static int evaluateYellowFor4(ChessBoard chessBoard){
        int[][] score=null;
        score=new int[][]{
                {0,0,0,0,0,0,0,0,0,0,0,0,680,710,740,800},
                {0,0,0,0,0,0,0,0,0,0,0,0,675,690,700,740},
                {0,0,0,0,0,0,0,0,0,0,0,0,570,610,690,710},
                {0,0,0,0,0,0,0,0,0,0,0,0,530,570,675,680},
                {0,0,0,0,0,0,0,0,0,0,420,460,465,0,0,0},
                {0,0,0,0,0,0,0,0,0,360,400,410,0,0,0,0},
                {0,0,0,0,0,0,0,0,323,350,369,0,0,0,0,0},
                {0,0,0,0,0,0,0,289,310,320,0,0,0,0,0,0},
                {0,0,0,0,0,0,261,275,280,0,0,0,0,0,0,0},
                {0,0,0,0,0,230,245,255,0,0,0,0,0,0,0,0},
                {0,0,0,0,215,220,223,0,0,0,0,0,0,0,0,0},
                {0,0,179,193,200,0,0,0,0,0,0,0,0,0,0,0},
                {173,176,180,185,0,0,0,0,0,0,0,0,0,0,0,0},
                {171,172,175,180,0,0,0,0,0,0,0,0,0,0,0,0},
                {171,170,172,176,0,0,0,0,0,0,0,0,0,0,0,0},
                {169,171,171,173,0,0,0,0,0,0,0,0,0,0,0,0}
        };
        Square[][] grid = chessBoard.getGrid();
        int total=0;
        for (int i=0;i<chessBoard.getDimension();i++){
            for (int j=0;j<chessBoard.getDimension();j++){
                if (grid[i][j].getPiece()!=null){
                    total+=score[i][j];
                }
            }
        }

        return total;
    }

    private static int evaluateBlueFor4(ChessBoard chessBoard){
        int[][] score=null;
        score=new int[][]{
                {0,0,0,0,0,0,0,0,0,0,0,0,173,171,171,169},
                {0,0,0,0,0,0,0,0,0,0,0,0,176,172,170,171},
                {0,0,0,0,0,0,0,0,0,0,0,0,180,175,172,171},
                {0,0,0,0,0,0,0,0,0,0,0,0,185,180,176,173},
                {0,0,0,0,0,0,0,0,0,0,0,200,193,179,0,0},
                {0,0,0,0,0,0,0,0,0,223,220,215,0,0,0,0},
                {0,0,0,0,0,0,0,0,255,245,230,0,0,0,0,0},
                {0,0,0,0,0,0,0,280,275,261,0,0,0,0,0,0},
                {0,0,0,0,0,0,320,310,289,0,0,0,0,0,0,0},
                {0,0,0,0,0,369,350,323,0,0,0,0,0,0,0,0},
                {0,0,0,0,410,400,360,0,0,0,0,0,0,0,0,0},
                {0,0,0,465,460,420,0,0,0,0,0,0,0,0,0,0},
                {680,675,570,530,0,0,0,0,0,0,0,0,0,0,0,0},
                {710,690,610,570,0,0,0,0,0,0,0,0,0,0,0,0},
                {740,700,690,675,0,0,0,0,0,0,0,0,0,0,0,0},
                {800,740,710,680,0,0,0,0,0,0,0,0,0,0,0,0}
        };
        Square[][] grid = chessBoard.getGrid();
        int total=0;
        for (int i=0;i<chessBoard.getDimension();i++){
            for (int j=0;j<chessBoard.getDimension();j++){
                if (grid[i][j].getPiece()!=null){
                    total+=score[i][j];
                }
            }
        }

        return total;
    }











    public static Color nextPlayer(Color color,int num) {
        if (num==2){
            return color == Color.RED ? Color.GREEN : Color.RED;
        }
        if (num==4){
            if (color==Color.RED){
                return Color.GREEN;
            }
            if (color==Color.YELLOW){
                return Color.BLUE;
            }
            if (color==Color.GREEN){
                return Color.RED;
            }
            if (color==Color.BLUE){
                return Color.YELLOW;
            }
        }
        return null;
    }

    public static ChessBoard copyBoard(ChessBoard chessBoard,int num){
        ChessBoard temp=new ChessBoard(16,num);
        for (int i=0;i<16;i++){
            for (int j=0;j<16;j++){
                temp.getGrid()[i][j].setPiece(chessBoard.getGrid()[i][j].getPiece());
            }
        }
        return temp;
    }

    public static MinMaxResult max(ChessBoard chessBoard,int depth,Color color,int num){
        if (depth==0){
            if (AiColor.equals(Color.RED)){
                if (num==2){
                    return new MinMaxResult(evaluateRedFor2(chessBoard),null);
                }else {
                    return new MinMaxResult(evaluateRedFor4(chessBoard),null);
                }
            }
            if (AiColor.equals(Color.GREEN)){
                if (num==2){
                    return new MinMaxResult(evaluateGreenFor2(chessBoard),null);
                }else {
                    return new MinMaxResult(evaluateGreenFor4(chessBoard),null);
                }
            }
            if (AiColor.equals(Color.YELLOW)){
                if (num==2){
                    return new MinMaxResult(evaluateYellowFor2(chessBoard),null);
                }else {
                    return new MinMaxResult(evaluateYellowFor4(chessBoard),null);
                }
            }
            if (AiColor.equals(Color.BLUE)){
                if (num==2){
                    return new MinMaxResult(evaluateBlueFor2(chessBoard),null);
                }else {
                    return new MinMaxResult(evaluateBlueFor4(chessBoard),null);
                }
            }

        }
        int best=Integer.MIN_VALUE;
        ArrayList<Move> legalMoves = getLegalMoves(chessBoard, color, num);

        Move move=null;
        for (int i=0;i<legalMoves.size();i++){
            ChessBoardLocation src = legalMoves.get(i).src;
            ChessBoardLocation des = legalMoves.get(i).des;
            chessBoard.moveChessPiece(src,des);
            int temp=min(chessBoard,depth-1,nextPlayer(color,num),num).score;
            if (temp>best){
                best=temp;
                move=legalMoves.get(i);
            }
            chessBoard.moveChessPiece(des,src);
        }
        return new MinMaxResult(best,move);
    }

    public static MinMaxResult min(ChessBoard chessBoard,int depth,Color color,int num){
        if (depth==0){
            if (AiColor.equals(Color.RED)){
                if (num==2){
                    return new MinMaxResult(evaluateRedFor2(chessBoard),null);
                }else {
                    return new MinMaxResult(evaluateRedFor4(chessBoard),null);
                }
            }
            if (AiColor.equals(Color.GREEN)){
                if (num==2){
                    return new MinMaxResult(evaluateGreenFor2(chessBoard),null);
                }else {
                    return new MinMaxResult(evaluateGreenFor4(chessBoard),null);
                }
            }
            if (AiColor.equals(Color.YELLOW)){
                if (num==2){
                    return new MinMaxResult(evaluateYellowFor2(chessBoard),null);
                }else {
                    return new MinMaxResult(evaluateYellowFor4(chessBoard),null);
                }
            }
            if (AiColor.equals(Color.BLUE)){
                if (num==2){
                    return new MinMaxResult(evaluateBlueFor2(chessBoard),null);
                }else {
                    return new MinMaxResult(evaluateBlueFor4(chessBoard),null);
                }
            }

        }
        int best=Integer.MAX_VALUE;
        ArrayList<Move> legalMoves = getLegalMoves(chessBoard, color, num);

        Move move=null;
        for (int i=0;i<legalMoves.size();i++){
            ChessBoardLocation src = legalMoves.get(i).src;
            ChessBoardLocation des = legalMoves.get(i).des;
            chessBoard.moveChessPiece(src,des);
            int temp=max(chessBoard,depth-1,nextPlayer(color,num),num).score;
            if (best>temp){
                best=temp;
                move=legalMoves.get(i);
            }
            chessBoard.moveChessPiece(des,src);
        }
        return new MinMaxResult(best,move);
    }


//    public static MinMaxResult maxFor4(ChessBoard chessBoard,Color color,int num){
//        int best=Integer.MIN_VALUE;
//        ArrayList<Move> legalMoves = getLegalMoves(chessBoard, color, num);
//
//        Move move=null;
//        for (int i=0;i<legalMoves.size();i++){
//            ChessBoardLocation src = legalMoves.get(i).src;
//            ChessBoardLocation des = legalMoves.get(i).des;
//            chessBoard.moveChessPiece(src,des);
//            if ()
//            if (temp>best){
//                best=temp;
//                move=legalMoves.get(i);
//            }
//            chessBoard.moveChessPiece(des,src);
//        }
//        return new MinMaxResult(best,move);
//    }
}
