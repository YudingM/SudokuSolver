import javax.swing.*;
import java.awt.*;
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

    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        super.paintComponent(g);

        for (int i = 1; i < 9; i++) {
            if( i%3 == 0)
                g2.setStroke(new BasicStroke(3));
            else
                g2.setStroke(new BasicStroke(1));
            g2.drawLine((getWidth()*i)/9, 0, (getWidth()*i)/9, getHeight());
            g2.drawLine(0, (getHeight()*i)/9, getWidth(), (getHeight()*i)/9);
        }

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                g2.drawString();
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

            while(r < 9) {
                string = scanner.nextLine();
                System.out.println(string);

               sRow = string.trim().split("\\s+");

                for (int i = 0; i < sRow.length; i++) {
                    intRow[i] = Integer.parseInt(sRow[i]);
                }


                for (int c = 0; c < sRow.length; c++) {
                    board[r][c].setActualVal(intRow[c]);
                    if(board[r][c].actualVal > 0){
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

    public void removeFromRow(int row) {
        ArrayList<Integer> rowVals = new ArrayList();

        for (int col = 0; col < board[0].length; col++) {
            if (board[row][col].actualVal > 0) {
                rowVals.add(board[row][col].actualVal);
            }
        }

        for (int col = 0; col < board[0].length; col++) {
            for (int i = 0; i < rowVals.size(); i++) {
                board[row][col].removePossibleVal(rowVals.get(i) - 1);
            }
        }
    }

    public void removeFromCol(int col) {
        ArrayList<Integer> colVals = new ArrayList();

        for (int row = 0; row < board.length; row++) {
            if (board[row][col].actualVal > 0) {
                colVals.add(board[row][col].actualVal);
            }
        }

        for (int row = 0; row < board.length; row++) {
            for (int i = 0; i < colVals.size(); i++) {
                board[row][col].removePossibleVal(colVals.get(i) - 1);
            }
        }
    }

    public void removeFromGroup(int groupRow, int groupCol) {
        ArrayList<Integer> groupVals = new ArrayList();

        for (int row = groupRow * 3; row < groupRow * 3 + 3; row++) {
            for (int col = groupCol * 3; col < groupCol * 3 + 3; col++) {
                if (board[row][col].actualVal > 0) {
                    groupVals.add(board[row][col].actualVal);
                }
            }
        }

        for (int row = groupRow * 3; row < groupRow * 3 + 3; row++) {
            for (int col = groupCol * 3; col < groupCol * 3 + 3; col++) {
                for (int i = 0; i < groupVals.size(); i++) {
                    board[row][col].removePossibleVal(groupVals.get(i) - 1);
                }
            }
        }

        int woo = 0;

        int yeet = 0;

        for (int row = groupRow * 3; row < groupRow * 3 + 3; row++) {

            for (int col = groupCol * 3; col < groupCol * 3 + 3; col++) {

                for (int i = 0; i < board[row][col].numPossibleValues(); i++) {

                    woo = board[row][col].getPossibleVal(i);

                        for (int row1 = row + 1; row < groupRow * 3 + 3; row++) {

                            for (int col1 = col + 1; col < groupCol * 3 + 3; col++) {

                                for (int j = 0; j < board[row][col].numPossibleValues(); j++) {

                                    if(woo==board[row1][col1].getPossibleVal(j)) {

                                        yeet = 1;

                                    }

                                }

                            }


                        }

                        if(yeet == 0) {

                            board[row][col].setActualVal(woo);

                        }

                    }

                }

            }

        }

    public void setBoardActualVals(){
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if(board[row][col].numPossibleValues() == 1){
                    for (int i = 0; i < 9; i++) {
                        if(board[row][col].isPossibleVal(i)){
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

                if(fakeBoard[i][j].getActualVal() == 0) {

                    for (int k = 0; k < fakeBoard[i][j].numPossibleValues(); k++) {

                        fakeBoard[i][j].setActualVal(fakeBoard[i][j].getPossibleVal(k));

                        while(i < fakeBoard.length && j< fakeBoard[0].length) {



                        }

                    }

                }

            }

        }

    }

    public void isSolved() {

        for (int row = groupRow * 3; row < groupRow * 3 + 3; row++) {
            for (int col = groupCol * 3; col < groupCol * 3 + 3; col++) {



            }

    }

}
//random comment