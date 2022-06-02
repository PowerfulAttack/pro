package model;

import controller.ClickController;
import view.ChessGameFrame;
import view.Chessboard;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * 这个类表示国际象棋里面的马
 */
public class PawnChessComponent extends ChessComponent{

    private static Image PAWN_WHITE;
    private static Image PAWN_BLACK;


    private Image pawnImage;


    public void loadResource() throws IOException {
        if (PAWN_WHITE == null) {
            PAWN_WHITE = ImageIO.read(new File("./newimages/White_Pawn.png"));
        }

        if (PAWN_BLACK == null) {
            PAWN_BLACK = ImageIO.read(new File("./newimages/Black_Pawn.png"));
        }
    }


    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = PAWN_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage = PAWN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiatePawnImage(color);
        if (color==ChessColor.BLACK){
            this.name='P';
        }
        else this.name='p';
    }


    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        ChessComponent a = chessComponents[source.getX()][source.getY()];

        if (a.getChessColor()==ChessColor.BLACK){

            if ((chessComponents[source.getX()+1][source.getY()] instanceof EmptySlotComponent)){  //前面是空的
                if (source.getX()==1){
                    if (source.getY()==destination.getY() && destination.getX()-source.getX()==1){
                        return true;
                    }
                    else if (source.getY()==destination.getY()
                            && destination.getX()-source.getX()==2
                            && (chessComponents[2][source.getY()] instanceof EmptySlotComponent)){
                        return true;
                    }
                    else if (((source.getY()+1<8)
                            &&!(chessComponents[source.getX()+1][source.getY()+1] instanceof EmptySlotComponent)
                            && destination.getX()-source.getX()==1 && destination.getY()-source.getY()==1)
                            ||((source.getY()-1>=0)
                            &&!(chessComponents[source.getX()+1][source.getY()-1] instanceof EmptySlotComponent)
                            && destination.getX()-source.getX()==1 && destination.getY()-source.getY()==-1)){
                        return true;
                    }
                }
//                else if (source.getX()==4){   //吃过路兵
//                    if (source.getY()==destination.getY() && destination.getX()-source.getX()==1){
//
//                        return true;
//                    }
//                    else if (((source.getY()+1<8)
//                            &&!(chessComponents[source.getX()+1][source.getY()+1] instanceof EmptySlotComponent)
//                            && (destination.getX()-source.getX()==1 && destination.getY()-source.getY()==1))
//                            ||((source.getY()+1<8)
//                            &&(chessComponents[source.getX()][source.getY()+1] instanceof PawnChessComponent)
//                            && (destination.getX()-source.getX()==1 && destination.getY()-source.getY()==1))
//                            ||((source.getY()-1>=0)
//                            &&!(chessComponents[source.getX()+1][source.getY()-1] instanceof EmptySlotComponent)
//                            && (destination.getX()-source.getX()==1 && destination.getY()-source.getY()==-1))
//                            ||((source.getY()-1>=0)
//                            &&(chessComponents[source.getX()][source.getY()-1] instanceof PawnChessComponent && chessComponents[source.getX()][source.getY()-1].getChessColor()==ChessColor.WHITE)
//                            && (destination.getX()-source.getX()==1 && destination.getY()-source.getY()==-1))){
//                        return true;
//                    }
//                }
                else {
                    if (source.getY()==destination.getY() && destination.getX()-source.getX()==1){
                        return true;
                    }
                    else if (((source.getY()+1<8)
                            &&!(chessComponents[source.getX()+1][source.getY()+1] instanceof EmptySlotComponent)
                            && destination.getX()-source.getX()==1 && destination.getY()-source.getY()==1)
                            ||((source.getY()-1>=0)
                            &&!(chessComponents[source.getX()+1][source.getY()-1] instanceof EmptySlotComponent)
                            && destination.getX()-source.getX()==1 && destination.getY()-source.getY()==-1)){
                        return true;
                    }
                }   //不在初始格不在第五格
            }
            else if (!(chessComponents[source.getX()+1][source.getY()] instanceof EmptySlotComponent)){  //前面有东西
                if (((source.getY()+1<8)
                        &&!(chessComponents[source.getX()+1][source.getY()+1] instanceof EmptySlotComponent)
                        && destination.getX()-source.getX()==1 && destination.getY()-source.getY()==1)
                        ||((source.getY()-1>=0)
                        &&!(chessComponents[source.getX()+1][source.getY()-1] instanceof EmptySlotComponent)
                        && destination.getX()-source.getX()==1 && destination.getY()-source.getY()==-1)){
                    return true;
                }
            }
            else {
                return false;
            }


        }

        else {  //白
            if ((chessComponents[source.getX()-1][source.getY()] instanceof EmptySlotComponent)){
                if (source.getX()==6){
                    if (source.getY()==destination.getY() && destination.getX()-source.getX()==-1){
                        return true;
                    }
                    else if (source.getY()==destination.getY()
                            && destination.getX()-source.getX()==-2
                            && (chessComponents[5][source.getY()] instanceof EmptySlotComponent)){
                        return true;
                    }
                    else if (((source.getY()+1<8)
                            &&!(chessComponents[source.getX()-1][source.getY()+1] instanceof EmptySlotComponent)
                            && destination.getX()-source.getX()==-1 && destination.getY()-source.getY()==1)
                            ||((source.getY()-1>=0)
                            &&!(chessComponents[source.getX()-1][source.getY()-1] instanceof EmptySlotComponent)
                            && destination.getX()-source.getX()==-1 && destination.getY()-source.getY()==-1)){
                        return true;
                    }
                }
                else if (source.getX()!=6){
                    if (source.getY()==destination.getY() && destination.getX()-source.getX()==-1){
                        return true;
                    }
                    else if (((source.getY()+1<8)
                            &&!(chessComponents[source.getX()-1][source.getY()+1] instanceof EmptySlotComponent)
                            && destination.getX()-source.getX()==-1 && destination.getY()-source.getY()==1)
                            ||((source.getY()-1>=0)
                            &&!(chessComponents[source.getX()-1][source.getY()-1] instanceof EmptySlotComponent)
                            && destination.getX()-source.getX()==-1 && destination.getY()-source.getY()==-1)){
                        return true;
                    }
                }
            }
            else if (!(chessComponents[source.getX()-1][source.getY()] instanceof EmptySlotComponent)){
                if (((source.getY()+1<8)
                        &&!(chessComponents[source.getX()-1][source.getY()+1] instanceof EmptySlotComponent)
                        && destination.getX()-source.getX()==-1 && destination.getY()-source.getY()==1)
                        ||((source.getY()-1>=0)
                        &&!(chessComponents[source.getX()-1][source.getY()-1] instanceof EmptySlotComponent)
                        && destination.getX()-source.getX()==-1 && destination.getY()-source.getY()==-1)){
                    return true;
                }
            }
            else {
                return false;
            }

        }

        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(pawnImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
