import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GraphReader {
    private Graph graph;

    public GraphReader() {
        graph = new Graph();
    }

    public Graph readGraphFromFile(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            int row = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");

                for (int col = 0; col < values.length; col++) {
                    String value = values[col];
                    Node node = new Node(value, row, col);
                    graph.addNode(node);

                    if (col > 0) {
                        Node left = graph.getNodes().get(graph.getNodes().size() - 2);
                        node.addNeighbor(left);
                        left.addNeighbor(node);

                        if (row > 0) { // Diagonal superior izquierda
                            Node topLeft = graph.getNodes().get(graph.getNodes().size() - values.length - 2);
                            node.addNeighbor(topLeft);
                            topLeft.addNeighbor(node);
                        }

                        if (row < graph.getHeight() - 1) { // Diagonal inferior izquierda
                            System.out.println("altura "+graph.getHeight());
                            Node bottomLeft = graph.getNodes().get(graph.getNodes().size() + values.length - 2);
                            node.addNeighbor(bottomLeft);
                            bottomLeft.addNeighbor(node);
                        }
                    }

                    if (row > 0) {
                        Node top = graph.getNodes().get(graph.getNodes().size() - values.length - 1);
                        node.addNeighbor(top);
                        top.addNeighbor(node);

                        if (col < values.length - 1) { // Diagonal superior derecha
                            Node topRight = graph.getNodes().get(graph.getNodes().size() - values.length);
                            node.addNeighbor(topRight);
                            topRight.addNeighbor(node);
                        }
                    }

                    if (row < graph.getHeight() - 1) {
                        Node bottom = graph.getNodes().get(graph.getNodes().size() + values.length - 1);
                        node.addNeighbor(bottom);
                        bottom.addNeighbor(node);

                        if (col < values.length - 1) { // Diagonal inferior derecha
                            Node bottomRight = graph.getNodes().get(graph.getNodes().size() + values.length);
                            node.addNeighbor(bottomRight);
                            bottomRight.addNeighbor(node);
                        }
                    }
                }

                row++;
            }

            scanner.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return graph;
    }
}