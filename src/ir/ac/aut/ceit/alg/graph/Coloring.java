package ir.ac.aut.ceit.alg.graph;

import ir.ac.aut.ceit.alg.io.FileUtils;

import java.util.ArrayList;
import java.util.HashSet;

public class Coloring {
    private ArrayList<Thread> threadArray = new ArrayList<>();

    public void doColoring(String path){
        Graph graph = new Graph(path);
        int threadCount = 3;
        int eachThreadVertex = graph.getVertexNumber() / threadCount;

        doFFInParallel(graph, eachThreadVertex);

        System.out.println("coloring: ===============");
        graph.printColoring();

        HashSet<Integer> allVerticesErrors = findErrorsInParallel(graph, eachThreadVertex);

        System.out.println("errors: ===============");
        System.out.println("Vertices with invalid coloring: " + allVerticesErrors);

        for (Integer invalidVertex : allVerticesErrors) {
            ArrayList<Integer> impossibleList = buildImpossibleList(graph, graph.getAdjList()[invalidVertex]);
            graph.doColoring(invalidVertex, getLowestAvailableColor(graph, impossibleList));
            System.out.println("new color of " + invalidVertex + " is " + graph.getColors()[invalidVertex]);
        }

        System.out.println("final coloring: ===============");
        graph.printColoring();

        saveColoring(graph, path.substring(0,path.lastIndexOf(".")) + "-colored.txt");
    }

    private ArrayList<Integer> buildImpossibleList(Graph graph, ListNode firstNeigh) {
        ArrayList<Integer> imPossibleList = new ArrayList<>();
        ListNode neigh = firstNeigh;
        while (neigh != null){
            imPossibleList.add(graph.getColors()[neigh.getData()]);
            neigh = neigh.getNext();
        }
        return imPossibleList;
    }

    protected int getLowestAvailableColor(Graph graph, ArrayList<Integer> imPossibleList) {
        int color = -2;
        for (int j = 0; j < graph.getVertexNumber(); j++) {
            if(!imPossibleList.contains(j)){
                color = j;
                break;
            }
        }
        return color;
    }

    private HashSet<Integer> findErrorsInParallel(Graph graph, int eachThreadVertex) {
        threadArray = new ArrayList<>();
        ArrayList<ParallelFindColorError> errorClassArray = new ArrayList<>();
        HashSet<Integer> allVerticesErrors = new HashSet<>();

        int i = 0;
        int vertexNumbers = graph.getVertexNumber();
        while (i < vertexNumbers){
            int end = i + eachThreadVertex;
            if(end > graph.getVertexNumber()){
                end = graph.getVertexNumber();
            }
            System.out.println(i + " to " + end);
            ParallelFindColorError errorThread = new ParallelFindColorError(graph,i,end);
            errorClassArray.add(errorThread);
            threadArray.add(new Thread(errorThread));
            i += eachThreadVertex;
        }
        for (Thread thread : threadArray) {
            thread.start();
        }
        try {
            for (Thread thread : threadArray) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (ParallelFindColorError parallelFindColorError : errorClassArray) {
            allVerticesErrors.addAll(parallelFindColorError.getErrorVertices());
        }
        return allVerticesErrors;
    }

    private void doFFInParallel(Graph graph, int eachThreadVertex) {
        threadArray = new ArrayList<>();
        int i = 0;
        int vertexNumbers = graph.getVertexNumber();
        while (i < vertexNumbers){
            int end = i + eachThreadVertex;
            if(end > graph.getVertexNumber()){
                end = graph.getVertexNumber();
            }
            threadArray.add(new Thread(new ParallelFirstFit(graph,i,end)));
            i += eachThreadVertex;
        }
        for (Thread thread : threadArray) {
            thread.start();
        }
        try {
            for (Thread thread : threadArray) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void saveColoring(Graph graph, String path){
        StringBuilder result = new StringBuilder();
        int[] colorArr = graph.getColors();
        for (int i = 0; i < colorArr.length; i++) {
            result.append(i).append(" ").append(colorArr[i]).append("\n");
        }
        FileUtils.write(result.toString(), path);
    }
}
