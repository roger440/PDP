package ir.ac.aut.ceit.alg;
import ir.ac.aut.ceit.alg.graph.Coloring;

import java.time.Duration;
import java.time.Instant;

public class Main {

    public static void main(String[] args) {
        Instant starts = Instant.now();
        Coloring coloring = new Coloring();
        coloring.doColoring(System.getProperty("user.dir") + "/inputs/test2.txt");
        Instant ends = Instant.now();
        System.out.println(Duration.between(starts, ends));
    }
}
