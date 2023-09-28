package com.freitas.hero;

import com.freitas.hero.MazeGenerator;

import static com.freitas.hero.MazeGenerator.*;

public class Main {
    public static void main(String[] args) {
        // Initialize the maze with walls
        initializeMaze();

        // Generate the maze starting from (1, 1)
        generateMaze(1, 1);

        // Print the maze
        printMaze();
    }
}
