package com.freitas.hero;

public class Node implements Comparable<Node> {
    int x;
    int y;
    int fScore;

    Node(int x, int y, int fScore) {
        this.x = x;
        this.y = y;
        this.fScore = fScore;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.fScore, other.fScore);
    }
}
