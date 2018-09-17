import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main extends JPanel {
    Cell[][] board;
    int[][] fileValues;
    File file;

    public Main(int width, int height){
        setSize(width, height);
        setup();

    }

    public static void main(String[] args) {
        JFrame window = new JFrame("Sudoku Solver");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setBounds(0, 0, 600, 600 + 22); //(x, y, w, h) 22 due to title bar.

        Main panel = new Main(600, 600);

        panel.setFocusable(true);
        panel.requestFocusInWindow();
        panel.grabFocus();

        window.add(panel);
        window.setVisible(true);
        window.setResizable(false);
    }
    public void setup() {
        String string;
        String[] sRow;
        board = new Cell[9][9];
        int r;

        file = new File("res/s01a.txt");

        try {
            Scanner scanner = new Scanner(file);
            r = 0;

            while (scanner.hasNextLine()) {
                string = scanner.nextLine();

                sRow = string.split("\\s+");

                for (int i = 0; i < sRow.length; i++) {
                    fileValues[r][i] = Integer.parseInt(sRow[i]);
                }
                r++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
