package view;
import com.sun.org.apache.xerces.internal.xs.StringList;
import controller.GameController;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LoadFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGTH;
    private GameController gameController;

    public LoadFrame(int width, int heigth) {

        setTitle("Load"); //设置标题

        WIDTH = width;
        HEIGTH = heigth;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        ImageIcon pic = new ImageIcon("");
        JLabel label = new JLabel(pic);
        label.setLocation(150, 100);
        label.setSize(500, 500);
        add(label);

        addLabel();
        addNewGameButton();
        addLoadGameButton();
        addQuitGameButton();
        ImageIcon imageIcon = new ImageIcon("R-C (1).jfif");
        JLabel alabel=new JLabel( imageIcon) ;
        alabel.setSize(WIDTH,HEIGTH);
        add(alabel);


    }

    private void addLabel() {
        JLabel statusLabel = new JLabel("Chess");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 50));
        add(statusLabel);
    }

    private void addNewGameButton(){
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
                while (input.hasNext()){
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
            String path = JOptionPane.showInputDialog(this,"Input Path here");
            if (testformat(path)) {
                stringBuilder.append("104");
                String wrongMessage =stringBuilder.toString();
                JOptionPane.showMessageDialog(null,wrongMessage);
                return;
            }
            File file = new File(path);


            try {
                Scanner input = new Scanner(file);
                List<String> chessboard1 = new ArrayList<>();
                while (input.hasNext()){
                    chessboard1.add(input.nextLine());

                }
                    if (testchessboard((StringList) chessboard1)) {
                        stringBuilder.append(",101");
                        String wrongMessage =stringBuilder.toString();
                        JOptionPane.showMessageDialog(null,wrongMessage);
                        return;
                    }
                    else if (testchess((StringList) chessboard1)) {
                        stringBuilder.append(",102");
                        String wrongMessage =stringBuilder.toString();
                        JOptionPane.showMessageDialog(null,wrongMessage);
                        return;
                    }
                    else if (testplayer((StringList) chessboard1)) {
                        stringBuilder.append(",103");
                        String wrongMessage =stringBuilder.toString();
                        JOptionPane.showMessageDialog(null,wrongMessage);
                        return;
                    }



                ChessGameFrame mainFrame = new ChessGameFrame(1000, 760, (ArrayList<String>) chessboard1);
                mainFrame.setVisible(true);

                System.out.println(chessboard1);


                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                this.dispose();

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

        });

    }
    public boolean testformat(String path){
        //格式检查//
        if (!path.endsWith(".txt")){
            return true;
        }
        else return false;
    }
    public boolean testchessboard(StringList chessboard1) {
        //棋盘长度8*8检查//
        boolean check=true;
        for (int i = 0; i < chessboard1.size() - 1; i++) {
            if (chessboard1.size() - 1 != 8 || chessboard1.get(i).length() != 8) {
                check=true;
            } else
                check=false;
        }
        return check;
    }
    public boolean testchess(StringList chessboard1){
        //棋子检查//
        boolean check = true;
        List<Character> chess= Arrays.asList('R','r','K','k','Q','q','B','b','P','p','_','n','N','w');
        for (int j=0;j<chessboard1.size();j++){
            if (!chess.contains(chessboard1.get(j))) {
                check=true;
            }
            else
                check=false;
        }
        return check;
    }
    public boolean testplayer(StringList chessboard1){
        //行棋方检查//
        if (!chessboard1.get(chessboard1.size()-1).equals('w')&&!chessboard1.get(chessboard1.size()-1).equals('b')){
            return true;
        }
        else return false;

    }


    private void addQuitGameButton(){
        JButton button = new JButton("Quit Game");
        button.addActionListener(e -> System.exit(0));
        button.setLocation(HEIGTH, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }


}
