# Sudoku Solver in Java 🧩

This project is a simple **Sudoku Solver** written in Java. It solves a fixed 9x9 Sudoku board using a backtracking algorithm and prints the solution to the console.

## 🧠 Features

- Solves a 9x9 Sudoku puzzle  
- Uses the backtracking algorithm to find the solution  
- Prints the step-by-step solution to the console  
- Clean and simple structure, easy to understand  

## 📁 File Structure

```
.
├── Main.java           # Main class to run the Sudoku solver
├── SudokuSolver.java   # Class that implements the solving algorithm
```

## 🏃‍♂️ How to Run

1. Make sure you have Java installed:  
   ```bash
   java -version
   ```

2. Clone the project or place the `.java` files into a folder.

3. Compile and run using the terminal:
   ```bash
   javac SudokuSolver.java Main.java
   java Main
   ```

4. The solution will be printed to the console.

## 👨‍💻 Algorithm Used

This solver uses a **recursive backtracking** algorithm. It checks each empty cell and tries numbers from 1 to 9, backtracking when it finds an invalid placement, and continues until a valid solution is found.

## 📸 Example Output

```
Solution:
5 3 4 | 6 7 8 | 9 1 2 
6 7 2 | 1 9 5 | 3 4 8 
1 9 8 | 3 4 2 | 5 6 7 
8 5 9 | 7 6 1 | 4 2 3 
4 2 6 | 8 5 3 | 7 9 1 
7 1 3 | 9 2 4 | 8 5 6 
9 6 1 | 5 3 7 | 2 8 4 
2 8 7 | 4 1 9 | 6 3 5 
3 4 5 | 2 8 6 | 1 7 9 
```

## 🧱 Requirements

- Java 8 or higher

---

**Author:** [Your GitHub Username Here]
