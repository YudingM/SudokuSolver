import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends JPanel {
    Cell[][] board;
    int[][] fileValues;
    File file;

    public Main(int width, int height) {
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

                for (int c = 0; c < sRow.length; c++) {
                    board[r][c].setActualVal(Integer.parseInt(sRow[c]));
                }
                r++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void verifyRow(int row) {
        ArrayList<Integer> rowVals = new ArrayList();

        for (int col = 0; col < board[0].length; col++) {
            if (board[row][col].actualVal > 0) {
                rowVals.add(board[row][col].actualVal);
            }
        }

        for (int col = 0; col < board[0].length; col++) {
            for (int i = 0; i < rowVals.size(); i++) {
                board[row][col].removeValue(rowVals.get(i));
            }
        }
    }

    public void verifyCol(int col) {
        ArrayList<Integer> rowVals = new ArrayList();

        for (int row = 0; row < board.length; row++) {
            if (board[row][col].actualVal > 0) {
                rowVals.add(board[row][col].actualVal);
            }
        }

        for (int row = 0; row < board.length; row++) {
            for (int i = 0; i < rowVals.size(); i++) {
                board[row][col].removeValue(rowVals.get(i));
            }
        }
    }

    public void verifyGroup(int groupRow, int groupCol) {
        ArrayList<Integer> rowVals = new ArrayList();

        for (int row = groupRow * 3; row < groupRow * 3 + 3; row++) {
            for (int col = groupCol * 3; col < groupCol * 3 + 3; col++) {
                if (board[row][col].actualVal > 0) {
                    rowVals.add(board[row][col].actualVal);
                }
            }
        }

        for (int row = groupRow * 3; row < groupRow * 3 + 3; row++) {
            for (int col = groupCol * 3; col < groupCol * 3 + 3; col++) {
                for (int i = 0; i < rowVals.size(); i++) {
                    board[row][col].removeValue(rowVals.get(i));
                }
            }
        }
    }
}