
import java.util.*;


public class SudokuSolver {

    private static final int SIZE = 9; 
    private static final double START_TEMPERATURE = Double.MAX_VALUE;
    private static final double COOLING_RATE = 0.99;
   // private static final int MAX_ITERATIONS = 100000;

    //fill Sudoku
    private static boolean Sudokugraph(int[][] board) {
        for (int row = 0; row < SIZE; row += 3) {
            for (int col = 0; col < SIZE; col += 3) {
                if (!blockChange(board, row, col)) {
                    return false; 
                }
            }
        }
        return true; 
    }

    private static boolean blockChange(int[][] board, int startRow, int startCol) {
        List<Integer> availableNumbers = new ArrayList<>();
        for (int i = 1; i <= SIZE; i++) {
            availableNumbers.add(i);
        }
        Collections.shuffle(availableNumbers); // get numbers randomly

        for (int row = startRow; row < startRow + 3; row++) {
            for (int col = startCol; col < startCol + 3; col++) {
                if (board[row][col] == 0) {
                    for (int numIndex = 0; numIndex < availableNumbers.size(); numIndex++) {
                        int number = availableNumbers.get(numIndex);
                        if (!isNumberInBlock(board, startRow, startCol, number)) {
                            board[row][col] = number;
                            availableNumbers.remove(numIndex);
                            break;
                        }
                    }
                }
            }
        }
        return true; 
    }

    private static boolean isNumberInBlock(int[][] board, int startRow, int startCol, int number) {
        for (int row = startRow; row < startRow + 3; row++) {
            for (int col = startCol; col < startCol + 3; col++) {
                if (board[row][col] == number) {
                    return true;
                }
            }
        }
        return false;
    }


    private static int calculateFaultScore(int[][] board) {
        int faultScore = 0;
        Set<Integer> checkedNum;

        // Check rows for duplicates using while loop
        int row = 0;
        while (row < SIZE) {
            checkedNum = new HashSet<>();
            int col = 0;
            while (col < SIZE) {
                int number = board[row][col];
                if (number != 0 && !checkedNum.add(number)) {
                    faultScore++;
                }
                col++;
            }
            row++;
        }

        // Check columns for duplicates using while loop
        int col = 0;
        while (col < SIZE) {
            checkedNum = new HashSet<>();
            row = 0;
            while (row < SIZE) {
                int number = board[row][col];
                if (number != 0 && !checkedNum.add(number)) {
                    faultScore++;
                }
                row++;
            }
            col++;
        }

        // Check 3x3 blocks for duplicates using while loop
        int blockRow = 0;
        while (blockRow < SIZE) {
            int blockCol = 0;
            while (blockCol < SIZE) {
                checkedNum = new HashSet<>();
                row = blockRow;
                while (row < blockRow + 3) {
                    col = blockCol;
                    while (col < blockCol + 3) {
                        int number = board[row][col];
                        if (number != 0 && !checkedNum.add(number)) {
                            faultScore++;
                        }
                        col++;
                    }
                    row++;
                }
                blockCol += 3;
            }
            blockRow += 3;
        }

        return faultScore;
    }


    private static int[][] simulatedAnnealing(int[][] initialBoard) {
        int[][] currentBoard = deepCopyBoard(initialBoard);
        int currentScore = calculateFaultScore(currentBoard);
        double temperature = START_TEMPERATURE;
        int[][] bestSolution = deepCopyBoard(currentBoard);
        int bestScore = currentScore;

        int iteration = 0;
        while (temperature > Double.MIN_VALUE && bestScore > 0) {
            iteration++;
            int[][] newBoard = deepCopyBoard(currentBoard);
            successorFunc(newBoard); // 
            int newScore = calculateFaultScore(newBoard);

            if (newScore < currentScore || Math.random() < Math.exp((currentScore - newScore) / temperature)) {
                currentBoard = newBoard;
                currentScore = newScore;

                // Save new score if it is better
                if (newScore < bestScore) {
                    bestSolution = deepCopyBoard(newBoard);
                    bestScore = newScore;
                }
            }

            //  Print fault score every 100 iteration
            if (iteration<= 800 && iteration % 100 == 0) {
                System.out.println("Iteration: " + iteration + " - Fault Score: " + bestScore);
            }
            if(iteration >800 && iteration %25000 == 0){
                System.out.println("Iteration: " + iteration + " - Fault Score: " + bestScore);
            }


            temperature *= COOLING_RATE;
            // Print final fault score until 1000000 iteration
            if(bestScore== 0 || iteration ==1000000){
                System.out.println("Final Fault Score: " + bestScore);
                System.out.println("Found in " + iteration + ". iteration");
            }

            if(iteration==1000001){
            bestScore = -1;
            }
        }


        return bestSolution;
    }
    private static void successorFunc(int[][] board) {
        Random rand = new Random();

        int cell1Row, cell1Col;
        int cell2Row, cell2Col;

        do {
            // Pick first cell randomly
            cell1Row = rand.nextInt(board.length);
            cell1Col = rand.nextInt(board[0].length);

            // Pick second cell randomly
            cell2Row =  rand.nextInt(3) * 3 + rand.nextInt(3);
            cell2Col =  rand.nextInt(3) * 3 + rand.nextInt(3);
        } while ((cell1Row == cell2Row && cell1Col == cell2Col) || // They should be same cell
                !dontRand(cell1Row, cell1Col) || !dontRand(cell2Row, cell2Col)); // They should not default numbers

        // Swapping
        int temp = board[cell1Row][cell1Col];
        board[cell1Row][cell1Col] = board[cell2Row][cell2Col];
        board[cell2Row][cell2Col] = temp;
    }



    private static boolean dontRand(int row, int col) {
        switch (row) {
            case 0:
                return col != 2;
            case 1:
                switch (col) {
                    case 1:
                    case 4:
                    case 5:
                    case 6:
                        return false;
                }
                break;
            case 2:
                switch (col) {
                    case 1:
                    case 4:
                    case 7:
                        return false;
                }
                break;
            case 3:
                switch (col) {
                    case 2:
                    case 3:
                    case 6:
                        return false;
                }
                break;
            case 4:
                switch (col) {
                    case 4:
                    case 6:
                        return false;
                }
                break;
            case 5:
                return col != 0 && col != 6;
            case 6:
                switch (col) {
                    case 0:
                    case 2:
                    case 8:
                        return false;
                }
                break;
            case 7:
                return col == 3 || col == 7;
            case 8:
                return col == 2 || col == 3 || col == 6 || col == 8;
        }
        return true;
    }

    // Print grids
    public static void printBoard(int[][] board) {
        for (int row = 0; row < 9; row++) {
            if (row % 3 == 0 && row != 0) {
                System.out.println();
            }
            for (int col = 0; col < 9; col++) {
                if (col % 3 == 0 && col != 0) {
                    System.out.print("| ");
                }
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }

        System.out.println("Fault Score: " + calculateFaultScore(board) + "\n");
    }


    // copying board
    private static int[][] deepCopyBoard(int[][] original) {
        int[][] newBoard = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            newBoard[i] = original[i].clone();
        }
        return newBoard;
    }


    public static void main(String[] args) {
        int[][] board = {
                {0, 0, 6, 0, 0, 0, 0, 0, 0},
                {0, 8, 0, 0, 5, 4, 2, 0, 0},
                {0, 4, 0, 0, 9, 0, 0, 7, 0},
                {0, 0, 7, 9, 0, 0, 3, 0, 0},
                {0, 0, 0, 0, 8, 0, 4, 0, 0},
                {6, 0, 0, 0, 0, 0, 1, 0, 0},
                {2, 0, 3, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 5, 0, 0, 0, 4, 0},
                {0, 0, 8, 3, 0, 0, 5, 0, 2}
        };

        if (Sudokugraph(board)) {
            printBoard(board);
            board = simulatedAnnealing(board);
            printBoard(board);
            System.out.println("Final Fault Score: " + calculateFaultScore(board));
        } else {
            System.out.println("Cannot fill the Sudoku grid following the rules.");
        }



    }
}