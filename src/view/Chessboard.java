package view;


import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {

    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;

    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    protected ChessGameFrame chessGameFrame;


    public Chessboard(int width, int height, ArrayList<String> chessboard) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

        initiateEmptyChessboard();

        // FIXME: Initialize chessboard for testing only.
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                switch (chessboard.get(i).charAt(j)) {
                    case 'R':
                        initRookOnBoard(i, j, ChessColor.BLACK);
                        break;
                    case 'r':
                        initRookOnBoard(i, j, ChessColor.WHITE);
                        break;

                    case 'N':
                        initKnightOnBoard(i, j, ChessColor.BLACK);
                        break;
                    case 'n':
                        initKnightOnBoard(i, j, ChessColor.WHITE);
                        break;

                    case 'B':
                        initBishopOnBoard(i, j, ChessColor.BLACK);
                        break;
                    case 'b':
                        initBishopOnBoard(i, j, ChessColor.WHITE);
                        break;

                    case 'Q':
                        initQueenOnBoard(i, j, ChessColor.BLACK);
                        break;
                    case 'q':
                        initQueenOnBoard(i, j, ChessColor.WHITE);
                        break;

                    case 'K':
                        initKingOnBoard(i, j, ChessColor.BLACK);
                        break;
                    case 'k':
                        initKingOnBoard(i, j, ChessColor.WHITE);
                        break;

                    case 'P':
                        initPawnOnBoard(i, j, ChessColor.BLACK);
                        break;
                    case 'p':
                        initPawnOnBoard(i, j, ChessColor.WHITE);
                        break;
                }
            }
        }  //读档初始化

        if (chessboard.get(8).charAt(0)=='w'){
            currentColor=ChessColor.WHITE;
        }
        else if (chessboard.get(8).charAt(0)=='b'){
            currentColor=ChessColor.BLACK;
        }

    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

//        if (chess1 instanceof PawnChessComponent && chess1.getChessboardPoint().getX()==5 && chessComponents[4][chess1.getChessboardPoint().getY()] instanceof PawnChessComponent){
//            putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(chess1.getChessboardPoint().getX()-1,chess1.getChessboardPoint().getY()), chess1.getLocation(), clickController, CHESS_SIZE));
//
//        }



        chess1.repaint();
        chess2.repaint();
        chessComponents[row1][col1].repaint();
        chessComponents[row2][col2].repaint();



        if (ifWhiteWin()){
            System.out.println("white win");
            JOptionPane.showMessageDialog(null, "White Win!", "WINNING", JOptionPane.INFORMATION_MESSAGE);
            LoadFrame mainFrame = new LoadFrame(1000,760);
            mainFrame.setVisible(true);
            this.chessGameFrame.dispose();
        }
        if (ifBlackWin()){
            System.out.println("black win");
            JOptionPane.showMessageDialog(null, "Black Win!", "WINNING", JOptionPane.INFORMATION_MESSAGE);
            LoadFrame mainFrame = new LoadFrame(1000,760);
            mainFrame.setVisible(true);
            this.chessGameFrame.dispose();
        }  //胜负判断

        if (chess1 instanceof PawnChessComponent && (chess1.getChessboardPoint().getX()==7 ||chess1.getChessboardPoint().getX()==0) && (!ifBlackWin() && !ifWhiteWin())){
            JFrame f = new JFrame("promotion");
            f.setSize(300, 300);
            f.setLayout(null);
            Dimension a = new Dimension(90,90);

            JButton queen = new JButton("queen");
            JButton bishop = new JButton("bishop");
            JButton knight = new JButton("knight");
            JButton root = new JButton("root");

            queen.setLocation(50,50);
            queen.setSize(a);
            queen.setFont(new Font("Rockwell", Font.BOLD, 20));
            bishop.setLocation(150,50);
            bishop.setSize(a);
            bishop.setFont(new Font("Rockwell", Font.BOLD, 20));
            knight.setLocation(50,150);
            knight.setSize(a);
            knight.setFont(new Font("Rockwell", Font.BOLD, 20));
            root.setLocation(150,150);
            root.setSize(a);
            root.setFont(new Font("Rockwell", Font.BOLD, 20));

            f.setVisible(true);

            queen.setPreferredSize(a);
            queen.setBorder(BorderFactory.createLineBorder(ChessColor.BLACK.getColor()));
            queen.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    putChessOnBoard(new QueenChessComponent(chess1.getChessboardPoint(), chess1.getLocation(),chess1.getChessColor(), clickController, CHESS_SIZE));
                    for (int i=0;i<8;i++){
                        for (int j=0;j<8;j++){
                            chessComponents[i][j].repaint();
                        }
                    }
                    f.dispose();
                }

            });
            bishop.setPreferredSize(a);
            bishop.setBorder(BorderFactory.createLineBorder(ChessColor.BLACK.getColor()));
            bishop.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    putChessOnBoard(new BishopChessComponent(chess1.getChessboardPoint(), chess1.getLocation(),chess1.getChessColor(), clickController, CHESS_SIZE));
                    for (int i=0;i<8;i++){
                        for (int j=0;j<8;j++){
                            chessComponents[i][j].repaint();
                        }
                    }
                    f.dispose();
                }

            });
            knight.setPreferredSize(a);
            knight.setBorder(BorderFactory.createLineBorder(ChessColor.BLACK.getColor()));
            knight.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    putChessOnBoard(new KnightChessComponent(chess1.getChessboardPoint(), chess1.getLocation(),chess1.getChessColor(), clickController, CHESS_SIZE));
                    for (int i=0;i<8;i++){
                        for (int j=0;j<8;j++){
                            chessComponents[i][j].repaint();
                        }
                    }
                    f.dispose();
                }

            });
            root.setPreferredSize(a);
            root.setBorder(BorderFactory.createLineBorder(ChessColor.BLACK.getColor()));
            root.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    putChessOnBoard(new RookChessComponent(chess1.getChessboardPoint(), chess1.getLocation(),chess1.getChessColor(), clickController, CHESS_SIZE));
                    for (int i=0;i<8;i++){
                        for (int j=0;j<8;j++){
                            chessComponents[i][j].repaint();
                        }
                    }
                    f.dispose();
                }

            });

            f.add(queen);
            f.add(bishop);
            f.add(knight);
            f.add(root);

        }  //底线升变

        System.out.println(getChessboardGraph());

    }


    public String getChessboardGraph() {
        StringBuilder temp = new StringBuilder();
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                temp.append(chessComponents[i][j].name);
            }
            temp.append("\n");
        }
        if (this.currentColor==ChessColor.BLACK){
            temp.append('b');
        }
        else temp.append('w');

        return String.valueOf(temp);
    }

    public boolean ifBlackWin(){
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                if (chessComponents[i][j].name=='k'){
                    return false;
                }
            }
        }
        return true;
    }
    public boolean ifWhiteWin(){
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                if (chessComponents[i][j].name=='K'){
                    return false;
                }
            }
        }
        return true;
    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
    }

    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> chessData) {
        chessData.forEach(System.out::println);
    }
}
