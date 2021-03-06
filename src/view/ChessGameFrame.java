package view;

import com.sun.org.apache.xerces.internal.xs.StringList;
import controller.GameController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    private Clip bugin;

    public ChessGameFrame(int width, int height, ArrayList<String> load) {
        setTitle("CHESS"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addChessboard(load);
        addLabel();
        addNewGameButton();
        addLoadGameButton();
        addSaveGameButton();
        addQuitGameButton();

        //游戏界面背景图片 //
        ImageIcon imageIcon = new ImageIcon("R-C (1).jfif");
        JLabel alabel=new JLabel( imageIcon) ;
        alabel.setSize(WIDTH,HEIGTH);
        add(alabel);


        //音乐//
        File file=new File("49bdv-x1hax (1).wav");
        Music(file);


    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard(ArrayList<String> load) {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE, load);
        chessboard.chessGameFrame = this;
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        add(chessboard);
    }


    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        JLabel statusLabel = new JLabel(this.gameController.GetColour());
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 50));
        add(statusLabel);
    }


    private void addNewGameButton() {
        JButton button = new JButton("New Game");
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click new");

            File file = new File("new.txt");
            try {
                Scanner input = new Scanner(file);
                List<String> chessboard = new ArrayList<>();
                while (input.hasNext()) {
                    chessboard.add(input.nextLine());
                }
                ChessGameFrame mainFrame = new ChessGameFrame(1000, 760, (ArrayList<String>) chessboard);
                mainFrame.setVisible(true);


                System.out.println(chessboard);
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                this.dispose();

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

        });
    }


    private void addLoadGameButton() {
        JButton button = new JButton("Load Game");
        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            StringBuilder stringBuilder = new StringBuilder("Errorcode:");

            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            if (testformat(path)) {
                stringBuilder.append("104");
                String wrongMessage = stringBuilder.toString();
                JOptionPane.showMessageDialog(null, wrongMessage);
                return;
            }
            File file = new File(path);
            try {
                Scanner input = new Scanner(file);
                List<String> chessboard1 = new ArrayList<>();
                while (input.hasNext()) {
                    chessboard1.add(input.nextLine());
                }
                ChessGameFrame mainFrame = new ChessGameFrame(1000, 760, (ArrayList<String>) chessboard1);
                mainFrame.setVisible(true);
                if (testchessboard((StringList) chessboard1))
                    stringBuilder.append(",101");
                if (testchess((StringList) chessboard1))
                    stringBuilder.append(",102");
                if (testplayer((StringList) chessboard1))
                    stringBuilder.append(",103");
                String wrongMessage = stringBuilder.toString();
                JOptionPane.showMessageDialog(null, wrongMessage);

                System.out.println(chessboard1);
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                this.dispose();

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

        });
    }

    public boolean testformat(String path) {
        //格式检查//
        if (!path.endsWith(".txt")) {
            return true;
        } else return false;
    }

    public boolean testchessboard(StringList chessboard1) {
        //棋盘长度8*8检查//
        boolean check = true;
        for (int i = 0; i < chessboard1.size() - 1; i++) {
            if (chessboard1.size() - 1 != 8 || chessboard1.get(i).length() != 8) {
                check = true;
            } else
                check = false;
        }
        return check;
    }

    public boolean testchess(StringList chessboard1) {
        //棋子检查//
        boolean check = true;
        List<Character> chess = Arrays.asList('R', 'r', 'K', 'k', 'Q', 'q', 'B', 'b', 'P', 'p', '_', 'n', 'N', 'w');
        for (int j = 0; j < chessboard1.size(); j++) {
            if (!chess.contains(chessboard1.get(j))) {
                check = true;
            } else
                check = false;
        }
        return check;
    }

    public boolean testplayer(StringList chessboard1) {
        //行棋方检查//
        if (!chessboard1.get(chessboard1.size() - 1).equals('w') && !chessboard1.get(chessboard1.size() - 1).equals('b')) {
            return true;
        } else return false;

    }


    private void addSaveGameButton() {
        JButton button = new JButton("Save Game");
        button.setLocation(HEIGTH, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click Save");
            try (PrintWriter output = new PrintWriter("save.txt");) {
                output.println(this.gameController.toString());
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

    }

    private void addQuitGameButton() {
        JButton button = new JButton("Quit Game");
        button.addActionListener(e -> {
            LoadFrame mainFrame = new LoadFrame(1000, 760);
            mainFrame.setVisible(true);
            this.dispose();
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 480);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);


    }
    //音乐//
    public void Music(File music) {
        try {Clip clip = AudioSystem.getClip();
            this.bugin = clip;
            AudioInputStream input = AudioSystem.getAudioInputStream(music);
            clip.open(input);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}