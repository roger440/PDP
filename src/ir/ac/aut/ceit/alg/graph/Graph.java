package ir.ac.aut.ceit.alg.graph;


import ir.ac.aut.ceit.alg.io.FileUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class Graph {
    private ListNode[] adjList;
    private int[] colors;
    private int vertexNumber;

    public Graph(String filePath){
        createAdjList(filePath);
        System.out.println("Graph created. Max Edge: " + vertexNumber);
    }

    private void createAdjList(String path) {
        ArrayList<int[]> csv = FileUtils.readCSV(path);
        int maxEdge = 0;

        for (int[] edgeEndPoints : csv) {
            if(edgeEndPoints[0] > maxEdge){
                maxEdge = Math.max(edgeEndPoints[1], edgeEndPoints[0]);
            }
        }

        vertexNumber = maxEdge + 1;
        adjList = new ListNode[vertexNumber];
        colors = new int[vertexNumber];
        Arrays.fill(colors, -1);

        for (int[] edgeEndPoints : csv) {
            insertEdge(edgeEndPoints[0],edgeEndPoints[1]);
        }
    }

    public void insertEdge(int a, int b){
        adjList[a] = new ListNode(b, adjList[a]);
    }


    public ListNode[] getAdjList() {
        return adjList;
    }

    public int[] getColors() {
        return colors;
    }

    public int getVertexNumber() {
        return vertexNumber;
    }

    public void doColoring(int vertex, int color){
        colors[vertex] = color;
    }

    public void printColoring(){
        for (int i = 0; i < colors.length; i++) {
            System.out.println(i + "=>" + colors[i]);
        }
    }
}
