package controller;


import model.ChessComponent;
import view.Chessboard;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class ClickController {
    private final Chessboard chessboard;
    private ChessComponent first;

    File move = new File("./musics/move.wav");
    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }


    public void onClick(ChessComponent chessComponent) {
        if (first == null) {
            if (handleFirst(chessComponent)) {
                File fileone = new File("a.wav");
                play(fileone);
                chessComponent.setSelected(true);
                first = chessComponent;
                first.repaint();
            }
        } else {
            File filetwo = new File("a.wav");
            play(filetwo);
            if (first == chessComponent) { // 再次点击取消选取
                chessComponent.setSelected(false);
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
            } else if (handleSecond(chessComponent)) {
                //repaint in swap chess method.
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();

                first.setSelected(false);
                first = null;
            }
        }

        File file = new File("a.wav");
        play(file);
    }

    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(ChessComponent chessComponent) {
        return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
    }

    private void play(File music) {
        try {
            Clip Music = AudioSystem.getClip();
            AudioInputStream Input = AudioSystem.getAudioInputStream(music);
            Music.open(Input);
            Music.start();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }



}
