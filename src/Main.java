import javax.swing.*;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends JPanel {
    Cell[][] board;
    File file;

    public Main(int width, int height) {
        setSize(width, height);
        setup();
        printBoard();
        for (int i = 0; i < board.length; i++) {
            removePossibleFromRow(i);
//            for (int j = 0; j < board[0].length; j++) {
//                removePossibleFromCol(j);
//                removePossibleFromGroup(i, j);
//            }
        }
        setBoardActualVals();

        System.out.println();
        printBoard();
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g);

        for (int i = 1; i < 9; i++) {
            if (i % 3 == 0)
                g2.setStroke(new BasicStroke(3));
            else
                g2.setStroke(new BasicStroke(1));
            g2.drawLine((getWidth() * i) / 9, 0, (getWidth() * i) / 9, getHeight());
            g2.drawLine(0, (getHeight() * i) / 9, getWidth(), (getHeight() * i) / 9);
        }

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                g.setFont(new Font("default", Font.ROMAN_BASELINE, 12));
                g2.drawString(Integer.toString(board[row][col].getActualVal()), col*67 + 30, row*67 + 35);
            }
        }
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
        String[] sRow = new String[9];
        int[] intRow = new int[9];
        board = new Cell[9][9];

        file = new File("res/s01a.txt");

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                board[row][col] = new Cell();
            }
        }

        try {
            Scanner scanner = new Scanner(file);
            int r = 0;

            while (r < 9) {
                string = scanner.nextLine();

                sRow = string.trim().split("\\s+");

                for (int i = 0; i < sRow.length; i++) {
                    intRow[i] = Integer.parseInt(sRow[i]);
                }


                for (int c = 0; c < sRow.length; c++) {
                    board[r][c].setActualVal(intRow[c]);
                    if (board[r][c].getActualVal() > 0) {
                        for (int i = 0; i < 9; i++) {
                            board[r][c].removePossibleVal(i);

                        }
                    }
                }
                r++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void removePossibleFromRow(int row) {
        ArrayList<Integer> rowVals = new ArrayList();

        for (int col = 0; col < board[0].length; col++) {
            if (board[row][col].getActualVal() > 0) {
                rowVals.add(board[row][col].getActualVal());
            }
        }

        for (int col = 0; col < board[0].length; col++) {
            for (int i = 0; i < rowVals.size(); i++) {
                board[row][col].removePossibleVal(rowVals.get(i) - 1);
            }
        }
    }

    public void removePossibleFromCol(int col) {
        ArrayList<Integer> colVals = new ArrayList();

        for (int row = 0; row < board.length; row++) {
            if (board[row][col].getActualVal() > 0) {
                colVals.add(board[row][col].getActualVal());
            }
        }

        for (int row = 0; row < board.length; row++) {
            for (int i = 0; i < colVals.size(); i++) {
                board[row][col].removePossibleVal(colVals.get(i) - 1);
            }
        }
    }

    public void removePossibleFromGroup(int row, int col) {
        ArrayList<Integer> groupVals = new ArrayList();

        int groupRow = row/3;
        int groupCol = col/3;

        for (int r = groupRow * 3; r < groupRow*3 + 3; r++) {
            for (int c = groupCol * 3; c < groupCol*3 + 3; c++) {
                if (board[row][col].getActualVal() > 0) {
                    groupVals.add(board[row][col].getActualVal());
                }
            }
        }

        for (int r = groupRow * 3; r < 9; r++) {
            for (int c = groupCol * 3; c < 9; c++) {
                for (int i = 0; i < groupVals.size(); i++) {
                    board[row][col].removePossibleVal(groupVals.get(i) - 1);
                }
            }
        }

        int woo = 0;

        int yeet = 0;

        for (int r = groupRow * 3; r < groupRow * 3 + 3; r++) {

            for (int c = groupCol * 3; c < groupCol * 3 + 3; c++) {

                for (int i = 0; i < board[row][col].numPossibleValues(); i++) {

                    woo = board[row][col].getPossibleVal(i);

                    for (int r1 = r + 1; r1 < groupRow * 3 + 3; r1++) {

                        for (int c1 = c + 1; c1 < groupCol * 3 + 3; c1++) {

                            for (int j = 0; j < board[row][col].numPossibleValues(); j++) {

                                if (woo == board[r1][c1].getPossibleVal(j)) {

                                    yeet = 1;

                                }

                            }

                        }


                    }

                    if (yeet == 0) {

                        board[row][col].setActualVal(woo);

                    }

                }

            }

        }

    }

    public void setBoardActualVals() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col].numPossibleValues() == 1) {
                    for (int i = 0; i < 9; i++) {
                        if (board[row][col].isPossibleVal(i)) {
                            board[row][col].setActualVal(i + 1);
                        }
                    }
                }
            }
        }
    }

    public void tryEverything() {

        Cell[][] fakeBoard;

        fakeBoard = board;

        for (int i = 0; i < fakeBoard.length; i++) {

            for (int j = 0; j < fakeBoard[0].length; j++) {

                if (fakeBoard[i][j].getActualVal() == 0) {

                    for (int k = 0; k < fakeBoard[i][j].numPossibleValues(); k++) {

                        fakeBoard[i][j].setActualVal(fakeBoard[i][j].getPossibleVal(k));

                        while (i < fakeBoard.length && j < fakeBoard[0].length) {


                        }

                    }

                }

            }

        }

    }

    public boolean isSolved(){
        int rowSum = 0;
        int colSum = 0;
        int groupSum = 0;

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                rowSum += board[r][c].getActualVal();
            }
            if(rowSum != 45){
                return false;
            }
            rowSum = 0;
        }

        for (int c = 0; c < board.length; c++) {
            for (int r = 0; r < board[0].length; r++) {
                colSum += board[r][c].getActualVal();
            }
            if(colSum != 45){
                return false;
            }
            colSum = 0;
        }

        for(int groupRow = 0; groupRow < 3; groupRow++){
            for (int groupCol = 0; groupCol < 3; groupCol++) {
                for (int smallRow = 0; smallRow < 3; smallRow++) {
                    for (int smallCol = 0; smallCol < 3; smallCol++) {
                        groupSum += board[groupRow * 3 + smallRow][groupCol * 3 + smallCol].getActualVal();
                    }
                }
                if(groupSum != 45){
                    return false;
                }
                groupSum = 0;
            }
        }

        return true;
    }

    public void printBoard(){
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                System.out.print(board[r][c].getActualVal());
            }
            System.out.println();
        }
    }
}
