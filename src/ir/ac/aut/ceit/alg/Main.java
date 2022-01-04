package ir.ac.aut.ceit.alg;
import ir.ac.aut.ceit.alg.graph.Coloring;

import java.time.Duration;
import java.time.Instant;

public class Main {

    public static void main(String[] args) {
        // instantiate a Coloring class and do coloring
        //document the time taken
        Instant starts = Instant.now();
        Coloring coloring = new Coloring();
        coloring.doColoring(System.getProperty("user.dir") + "/inputs/test3.txt");
        Instant ends = Instant.now();
        System.out.println(Duration.between(starts, ends));
    }
}
