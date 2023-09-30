package com.freitas.hero;

import com.freitas.hero.MazeGenerator.*;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.PriorityQueue;

import static com.freitas.hero.MazeGenerator.*;

public class MazePathfinder {
    private static final int WIDTH = 21; // Maze width (odd number)
    private static final int HEIGHT = 21; // Maze height (odd number)
    private static final char WALL = '#';
    private static final char PATH = ' ';


    public static void main(String[] args) {
        initializeMaze();
        generateMaze(1, 1);
        printMaze();

        // Find and print the path from start to end
        int[] start = {1, 1}; // Start point
        int[] end = {HEIGHT - 2, WIDTH - 2}; // End point

        boolean[][] visited = new boolean[HEIGHT][WIDTH];
        int[][] parentX = new int[HEIGHT][WIDTH];
        int[][] parentY = new int[HEIGHT][WIDTH];

        boolean foundPath = findPathAstar(start, end, visited, parentX, parentY);

        if (foundPath) {
            printShortestPath(start, end, parentX, parentY);
        } else {
            System.out.println("No path found.");
        }
    }

    private static boolean findPathBFS(int[] start, int[] end, boolean[][] visited, int[][] parentX, int[][] parentY) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(start);
        visited[start[0]][start[1]] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];

            if (x == end[0] && y == end[1]) {
                return true; // Path found
            }

            // Check adjacent cells
            int[] dx = {-1, 0, 1, 0};
            int[] dy = {0, 1, 0, -1};

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (isValid(nx, ny) && MAZE[nx][ny] == PATH && !visited[nx][ny]) {
                    queue.add(new int[]{nx, ny});
                    visited[nx][ny] = true;
                    parentX[nx][ny] = x;
                    parentY[nx][ny] = y;
                }
            }
        }

        return false; // No path found
    }
    private static boolean findPathAstar(int[] start, int[] end, boolean[][] visited, int[][] parentX, int[][] parentY) {
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        openSet.add(new Node(start[0], start[1], 0));

        int[][] gScore = new int[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                gScore[i][j] = Integer.MAX_VALUE;
            }
        }
        gScore[start[0]][start[1]] = 0;

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            int x = current.x;
            int y = current.y;

            if (x == end[0] && y == end[1]) {
                return true; // Path found
            }

            visited[x][y] = true;

            // Check adjacent cells
            int[] dx = {-1, 0, 1, 0};
            int[] dy = {0, 1, 0, -1};

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (isValid(nx, ny) && MAZE[nx][ny] == PATH && !visited[nx][ny]) {
                    int tentativeGScore = gScore[x][y] + 1;

                    if (tentativeGScore < gScore[nx][ny]) {
                        parentX[nx][ny] = x;
                        parentY[nx][ny] = y;
                        gScore[nx][ny] = tentativeGScore;
                        int fScore = tentativeGScore + heuristic(nx, ny, end);
                        openSet.add(new Node(nx, ny, fScore));
                    }
                }
            }
        }

        return false; // No path found
    }

    private static boolean isValid(int x, int y) {
        return x >= 0 && x < HEIGHT && y >= 0 && y < WIDTH;
    }
    private static int heuristic(int x1, int y1, int[] end) {
        return Math.abs(x1 - end[0]) + Math.abs(y1 - end[1]); // Manhattan distance
    }

    private static void printShortestPath(int[] start, int[] end, int[][] parentX, int[][] parentY) {
        int x = end[0];
        int y = end[1];

        while (x != start[0] || y != start[1]) {
            MAZE[x][y] = '@'; // Mark the path with '*'
            int px = parentX[x][y];
            int py = parentY[x][y];
            x = px;
            y = py;
        }

        MAZE[start[0]][start[1]] = 'S'; // Mark the start
        MAZE[end[0]][end[1]] = 'E'; // Mark the end

        System.out.println();
        System.out.println("Path");
        System.out.println();

        // Print the MAZE with the path
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(MAZE[i][j]);
                System.out.print('.');
            }
            System.out.println();
        }
    }
}