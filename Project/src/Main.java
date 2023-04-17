import java.util.List;
import java.util.Scanner;
//2

/**
 * Cargar el siguiente mapa:
 *
 */

public class Main {
    public static void main(String[] args) {
        GraphReader reader = new GraphReader();
        Graph graph = reader.readGraphFromFile("src/Files/CaminoSustentación.txt");
        graph.newList(graph);


// Prompt the user to select the algorithms to run
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleccione los algoritmos que desea correr (separado con comas):");
        System.out.println("  1. Breadth-first search (BFS)");
        System.out.println("  2. Depth-first search (DFS)");
        System.out.println("  3. Uniform-cost search (UCS)");
        System.out.println("  4. A* search (Manhattan distance)");
        System.out.println("  5. A* search (Euclidean distance)");
        System.out.println("  6. Beam search (Manhattan distance)");
        System.out.println("  7. Beam search (Euclidean distance)");
        System.out.println("  8. Hill climbing (Manhattan distance)");
        System.out.println("  9. Hill climbing (Euclidean distance)");
        System.out.println("  10. Recursive Depth-first search (DFS))");



        String[] selectedAlgorithms = scanner.nextLine().trim().split(",");

        // Corre los algoritmos seleccionados
        for (String algorithm : selectedAlgorithms) {
            switch (algorithm.trim()) {
                case "1":
                    System.out.println("\nEjecutando Breadth-first search (BFS)...");
                    List<Node> bfsPath = GraphSearch.search(graph, true);
                    printPathAndVisitedNodes(bfsPath);
                    break;
                case "2":
                    System.out.println("\nEjecutando Depth-first search (DFS)...");
                    List<Node> dfsPath = GraphSearch.search(graph, false);
                    printPathAndVisitedNodes(dfsPath);
                    break;
                case "3":
                    System.out.println("\nEjecutando Uniform-cost search (UCS)...");
                    List<Node> ucsPath = GraphSearch.ucs(graph);
                    printPathAndVisitedNodes(ucsPath);
                    break;
                case "4":
                    System.out.println("\nEjecutando A* search with Manhattan distance...");
                    List<Node> aStarManhattanPath = GraphSearch.aStar(graph, HeuristicType.MANHATTAN);
                    printPathAndVisitedNodes(aStarManhattanPath);
                    break;
                case "5":
                    System.out.println("\nEjecutando A* search with Euclidean distance...");
                    List<Node> aStarEuclideanPath = GraphSearch.aStar(graph, HeuristicType.EUCLIDEAN);
                    printPathAndVisitedNodes(aStarEuclideanPath);
                    break;
                case "6":
                    System.out.println("\nEjecutando Beam search with Manhattan distance...");
                    List<Node> beamSearch = GraphSearch.beamSearch(graph, 2, HeuristicType.MANHATTAN);
                    printPathAndVisitedNodes(beamSearch);
                    break;
                case "7":
                    System.out.println("\nEjecutando Beam search with Euclidean distance...");
                    List<Node> beamSearchEuclidean = GraphSearch.beamSearch(graph, 2, HeuristicType.EUCLIDEAN);
                    printPathAndVisitedNodes(beamSearchEuclidean);
                    break;
                case "8":
                    System.out.println("\nEjecutando Hill climbing with Manhattan distance...");
                    List<Node> hillClimbingManhattanPath = GraphSearch.hillClimbing(graph, HeuristicType.MANHATTAN );
                    printPathAndVisitedNodes(hillClimbingManhattanPath);
                    break;
                case "9":
                    System.out.println("\nEjecutando Hill climbing with Euclidean distance...");
                    List<Node> hillClimbingPath = GraphSearch.hillClimbing(graph, HeuristicType.EUCLIDEAN );
                    printPathAndVisitedNodes(hillClimbingPath);
                    break;
                case "10":
                    System.out.println("\nEjecutando Depth-first search Recursive (DFS)...");
                    List<Node> dfsPathRecursive = GraphSearch.dfsRecursive(graph);
                    printPathAndVisitedNodes(dfsPathRecursive);
                    break;
                default:
                    System.out.println("\nSelección de algoritmo no permitido.");
            }
        }


    }



    private static void printPathAndVisitedNodes(List<Node> path) {
        System.out.println("camino: ");
        if (path == null) {
            System.out.println("Camino no encontrado.");
            return;
        }
        System.out.print("[");
        for (Node node : path) {

            System.out.print(node.getName() + ",");

        }
        System.out.print("]");
    }
}
