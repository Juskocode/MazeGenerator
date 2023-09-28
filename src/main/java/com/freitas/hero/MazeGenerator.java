package com.freitas.hero;

import java.util.Random;
import java.util.Stack;

public class MazeGenerator {
    private static final int WIDTH = 21; // Maze width (odd number)
    private static final int HEIGHT = 21; // Maze height (odd number)
    private static final char WALL = '#';
    private static final char PATH = ' ';
    private static final char VISITED = '.';

    private static final int[] DX = {0, 0, 1, -1}; // Directions for movement (right, left, down, up)
    private static final int[] DY = {1, -1, 0, 0};

    private static char[][] maze = new char[HEIGHT][WIDTH];
    private static Random random = new Random();

    static void initializeMaze() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                maze[i][j] = WALL;
            }
        }
    }

    static void generateMaze(int x, int y) {
        Stack<Integer> stackX = new Stack<>();
        Stack<Integer> stackY = new Stack<>();
        maze[x][y] = PATH;
        stackX.push(x);
        stackY.push(y);

        while (!stackX.isEmpty()) {
            x = stackX.peek();
            y = stackY.peek();
            int[] directions = {0, 1, 2, 3};
            shuffleArray(directions);

            boolean found = false;
            for (int dir : directions) {
                int nx = x + DX[dir] * 2;
                int ny = y + DY[dir] * 2;

                if (isValid(nx, ny) && maze[nx][ny] == WALL) {
                    maze[x + DX[dir]][y + DY[dir]] = PATH;
                    maze[nx][ny] = PATH;
                    stackX.push(nx);
                    stackY.push(ny);
                    found = true;
                    break;
                }
            }

            if (!found) {
                stackX.pop();
                stackY.pop();
            }
        }
    }

    private static boolean isValid(int x, int y) {
        return x >= 0 && x < HEIGHT && y >= 0 && y < WIDTH;
    }

    private static void shuffleArray(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    static void printMaze() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }
}
