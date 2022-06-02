package controller;

import view.Chessboard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameController {
    private Chessboard chessboard;

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public List<String> loadGameFromFile(String path) {
        File file = new File(path+".txt");
        try {
            Scanner input = new Scanner(file);
            List<String> chessboard1 = new ArrayList<>();
            while (input.hasNext()){
                chessboard1.add(input.nextLine());
            }
            System.out.println(chessboard1);
            return chessboard1;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        try {
//            List<String> chessData = Files.readAllLines(Paths.get(path));
//            chessboard.loadGame(chessData);
//            return chessData;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    @Override
    public String toString() {
        return chessboard.getChessboardGraph();
    }


    public String GetColour(){
        return String.valueOf(chessboard.getCurrentColor());
    }
}
