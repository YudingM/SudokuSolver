import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends JPanel {
    public static Cell[][] board;
    public static File file;
    public Timer timer;

    public Main(int width, int height) {
        setSize(width, height);
        setup();
        printBoard();

        System.out.println();
        printBoard();

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for (int i = 0; i < board.length; i++) {
                    removePossibleFromRow(i);
                    for (int j = 0; j < board[0].length; j++) {
                        removePossibleFromCol(j);
                        removePossibleFromGroup(i, j);
                        setBoardActualVals();

                        if(board[i][j].numPossibleValues() == 0){
                            System.out.println("no value at "+ i + "and "+ j);
                        }
                    }
                }
                repaint();
                printBoard();

                if(isSolved()){
                    timer.stop();
                }
            }
        });
        timer.start();
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
        String[] sRow;
        int[] intRow = new int[9];
        board = new Cell[9][9];

        file = new File("res/s01a.txt");

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                board[row][col] = new Cell();

                if(board[row][col].numPossibleValues() == 0){
                    System.out.println("no value at "+ row + " and "+ col);
                }
                else{
                    System.out.println("we're good");
                }
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
                }
                r++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void removePossibleFromRow(int row) {
        ArrayList<Integer> rowVals = new ArrayList();
        int[] possibleVals = new int[9];

        for (int col = 0; col < board[0].length; col++) {
            if (board[row][col].getActualVal() > 0) {
                rowVals.add(board[row][col].getActualVal());
            }
        }

        for (int col = 0; col < board[0].length; col++) {
            for (int i: rowVals) {
                board[row][col].removePossibleVal(i);
            }

            for (int i = 0; i < possibleVals.length; i++) {
                if(board[row][col].getPossibleVal(i) > 0){
                    possibleVals[i]++;
                }
            }
        }

        for (int i = 0; i < possibleVals.length; i++) {
            if (possibleVals[i] == 1) {
                for (int col = 0; col < board.length; col++) {
                    if (board[row][col].getPossibleVal(i) > 0) {
                        board[row][col].setActualVal(i + 1);
                    }
                }
            }
        }
    }

    public void removePossibleFromCol(int col) {
        ArrayList<Integer> colVals = new ArrayList();
        int[] possibleVals = new int[9];

        for (int row = 0; row < board.length; row++) {
            if (board[row][col].getActualVal() > 0) {
                colVals.add(board[row][col].getActualVal());
            }
        }

        for (int row = 0; row < board.length; row++) {
            for (int i: colVals) {
                board[row][col].removePossibleVal(i);
            }

            for (int i = 0; i < possibleVals.length; i++) {
                if(board[row][col].getPossibleVal(i) > 0){
                    possibleVals[i]++;
                }
            }
        }

        for (int i = 0; i < possibleVals.length; i++) {
            if (possibleVals[i] == 1) {
                for (int row = 0; row < board.length; row++) {
                    if (board[row][col].getPossibleVal(i) > 0) {
                        board[row][col].setActualVal(i + 1);
                    }
                }
            }
        }
    }

    public void removePossibleFromGroup(int row, int col) {
        ArrayList<Integer> groupVals = new ArrayList();
        int[] possibleVals = new int[9];

        int groupRow = row/3;
        int groupCol = col/3;

        for (int r = groupRow * 3; r < groupRow*3 + 3; r++) {
            for (int c = groupCol * 3; c < groupCol*3 + 3; c++) {
                if (board[row][col].getActualVal() > 0) {
                    groupVals.add(board[row][col].getActualVal());
                }
            }
        }

        for (int r = groupRow * 3; r < groupRow*3 + 3; r++) {
            for (int c = groupCol * 3; c < groupCol*3 + 3; c++) {
                for (int i: groupVals) {
                    board[row][col].removePossibleVal(i);
                }

                for (int i = 0; i < possibleVals.length; i++) {
                    if(board[row][col].getPossibleVal(i) > 0){
                        possibleVals[i]++;
                    }
                }
            }
        }
        for (int i = 0; i < possibleVals.length; i++) {
            if (possibleVals[i] == 1) {
                for (int r = groupRow*3; r < groupRow*3 + 3; r++) {
                    for (int c = groupCol*3; c < groupCol*3 + 3; c++) {
                        if (board[r][c].getPossibleVal(i) > 0) {
                            board[r][c].setActualVal(i + 1);
                        }
                    }
                }
            }
        }
    }



    public void setBoardActualVals() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col].numPossibleValues() == 1) {
                    System.out.println("onePos at " + row + col);
                    board[row][col].setOnlyPossibleVal();
                }
            }
        }
    }

//    public void tryEverything() {
//
//        Cell[][] fakeBoard;
//
//        fakeBoard = board;
//
//        for (int i = 0; i < fakeBoard.length; i++) {
//
//            for (int j = 0; j < fakeBoard[0].length; j++) {
//
//                if (fakeBoard[i][j].getActualVal() == 0) {
//
//                    for (int k = 0; k < fakeBoard[i][j].numPossibleValues(); k++) {
//
//                        fakeBoard[i][j].setActualVal(fakeBoard[i][j].getPossibleVal(k));
//
//                        while (i < fakeBoard.length && j < fakeBoard[0].length) {
//
//
//                        }
//
//                    }
//
//                }
//
//            }
//
//        }
//
//    }

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
