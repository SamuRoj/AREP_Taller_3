package edu.escuelaing.arep.model;

public class Pair<M, B> {
    private final M first;
    private final B second;

    public Pair(M first, B second){
        this.first = first;
        this.second = second;
    }

    public M getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }
}
