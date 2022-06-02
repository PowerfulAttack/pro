import view.ChessGameFrame;
import view.LoadFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoadFrame mainFrame = new LoadFrame(1000,760);
            mainFrame.setVisible(true);
        });
    }
}
