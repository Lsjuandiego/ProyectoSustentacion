import java.util.*;

class GraphSearch {

    /**
     * Metodo para implementar BFS y/o DFS
     * @param graph
     * @param useQueue true: para implementar BFS, false: implementar DFS
     * @return
     */
    public static List<Node> search(Graph graph, boolean useQueue) {
        Node start = graph.getStartNode();
        Node goal = graph.getGoalNode();

        Collection<Node> container = useQueue ? new LinkedList<>() : new Stack<>();
        Set<Node> visited = new HashSet<>();
        Map<Node, Node> parents = new HashMap<>();

        // Agregar el nodo inicial a la estructura de datos apropiada
        container.add(start);

        while (!container.isEmpty()) {
            Node node = useQueue ? ((LinkedList<Node>) container).poll() : ((Stack<Node>) container).pop();

            if (node.equals(goal)) {
                System.out.print(goal.getName());
                return getPath(start, goal, parents);
            }

            if (!visited.contains(node) && !node.isWall()) {
                visited.add(node);
                System.out.print(node.getName() +" , ");


                for (Node neighbor : node.getNeighborsWithoutHeuristics()) {
                    if (!visited.contains(neighbor) && !neighbor.isWall()) {
                        parents.put(neighbor, node);
                        container.add(neighbor);
                    }
                }
            }
        }

        return null; // Camino no encontrado
    }
    public static List<Node> dfsRecursive(Graph graph) {
        Set<Node> visited = new HashSet<>();
        List<Node> path = new ArrayList<>();
        dfsRecursiveHelper(graph.getStartNode(), graph.getGoalNode(), visited, path);
        return path;
    }

    private static boolean dfsRecursiveHelper(Node current, Node goal, Set<Node> visited, List<Node> path) {
        visited.add(current);
        path.add(current);

        if (current.equals(goal)) {
            return true;
        }

        for (Node neighbor : current.getNeighborsWithoutHeuristics()) {
            if (!visited.contains(neighbor) && !neighbor.isWall()) {
                if (dfsRecursiveHelper(neighbor, goal, visited, path)) {
                    return true;
                }
            }
        }

        path.remove(current);
        return false;
    }

    /**
     * Metodo que se encarga de devolver el camino encontrado
     * @param start: nodo de inicio
     * @param goal: nodo destino
     * @param parents : mapa de nodos padre
     * @return : Returna una lista de nodos con el camino
     */
    private static List<Node> getPath(Node start, Node goal, Map<Node, Node> parents) {
        List<Node> path = new ArrayList<>();
        Node current = goal;

        while (!current.equals(start)) {
            path.add(0, current);
            current = parents.get(current);
        }

        path.add(0, start);
        return path;
    }

    /** Costo uniforme (UCS)
     *  Este algoritmo, en este caso se comportaría como BFS ya que
     *  no cuenta con valores en las aristas. en este caso se toma un costo
     *  constante de 1
     * @param graph
     * @return
     */
    public static List<Node> ucs(Graph graph) {
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::getCost));
        Map<Node, Node> parentMap = new HashMap<>();
        Map<Node, Integer> costMap = new HashMap<>();

        Node start = graph.getStartNode();
        Node goal = graph.getGoalNode();
        start.setCost(0);
        queue.offer(start);
        parentMap.put(start, null);
        costMap.put(start, 0);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            System.out.print(current.getName() +" , ");
            if (current.equals(goal)) {
                // Nodo objetivo encontrado, retorna el camino
                return getPath(start, current, parentMap);
            }

            for (Node neighbor : current.getNeighborsWithoutHeuristics()) {
                if (neighbor.isWall()) {
                    continue; // Ignorar nodos que son muros
                }
                int newCost = costMap.get(current) + current.getCost();
                if (!costMap.containsKey(neighbor) || newCost < costMap.get(neighbor)) {
                    neighbor.setCost(newCost);
                    queue.add(neighbor);
                    parentMap.put(neighbor, current);
                    costMap.put(neighbor, newCost);
                }
            }
        }

        // No se encontró camino
        return null;
    }


    /**
     *
     * @param graph
     * @param heuristic
     * @return
     */
    public static List<Node> aStar(Graph graph, HeuristicType heuristic) {
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::getCost));
        Map<Node, Node> parentMap = new HashMap<>();
        Map<Node, Integer> costMap = new HashMap<>();

        Node start = graph.getStartNode();
        Node goal = graph.getGoalNode();
        start.setCost(0);
        queue.add(start);
        parentMap.put(start, null);
        costMap.put(start, 0);

        boolean diagonal = heuristic == HeuristicType.EUCLIDEAN;

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            System.out.print(current.getName() +" , ");
            //System.out.println("Visitado: " + current.getName() + " en (" + current.getRow() + ", " + current.getCol() + ")");
            if (current.equals(goal)) {
                // Nodo final encontrado
                return getPath(start, current, parentMap);
            }

            for (Node neighbor : current.getNeighbors(heuristic)) {
                if (neighbor.isWall()) {
                    continue; // Ignorar nodos que son muros
                }
                int newCost = costMap.get(current) + current.getCost();
                if (!costMap.containsKey(neighbor) || newCost < costMap.get(neighbor)) {
                    neighbor.setCost(newCost);
                    parentMap.put(neighbor, current);
                    costMap.put(neighbor, newCost);

                    int heuristicValue;
                    if (diagonal) {
                        heuristicValue = getEuclideanDistance(neighbor, goal);
                    } else {
                        heuristicValue = getManhattanDistance(neighbor, goal);
                    }

                    int priority = newCost + heuristicValue;
                    neighbor.setPriority(priority);
                    queue.add(neighbor);
                }
            }
        }

        // No se encontró el nodo objetivo
        return null;
    }

    public static List<Node> hillClimbing(Graph graph, HeuristicType heuristic) {
        boolean diagonal = heuristic == HeuristicType.EUCLIDEAN;
        List<Node> visitedNodes = new ArrayList<>();

        Node current = graph.getStartNode();
        Node goal = graph.getGoalNode();

        while (!current.equals(goal)) {
            visitedNodes.add(current);
            System.out.print(current.getName() +" , ");
           // System.out.println("Visitado: " + current.getValue() + " en (" + current.getRow() + ", " + current.getCol() + ")");
            List<Node> neighbors = current.getNeighbors(heuristic);
            Node bestNeighbor = null;
            int bestHeuristic = Integer.MAX_VALUE;

            for (Node neighbor : neighbors) {
                if (!visitedNodes.contains(neighbor)&& !neighbor.isWall()) {
                    int heuristicValue;
                    if (diagonal) {
                        heuristicValue = getEuclideanDistance(neighbor, goal);
                    } else {
                        heuristicValue = getManhattanDistance(neighbor, goal);
                    }
                    if (heuristicValue< bestHeuristic) {
                        bestNeighbor = neighbor;
                        bestHeuristic = heuristicValue;
                    }
                }
            }

            if (bestNeighbor == null) {
                // Stuck, return null
                return null;
            }

            current = bestNeighbor;
        }
        System.out.print(goal.getName());

        visitedNodes.add(current);
        return visitedNodes;
    }
    public static List<Node> beamSearch(Graph graph, int k, HeuristicType heuristic) {
        boolean diagonal = heuristic == HeuristicType.EUCLIDEAN;
        List<Node> visitedNodes = new ArrayList<>();

        Node current = graph.getStartNode();
        Node goal = graph.getGoalNode();

        while (!current.equals(goal)) {
            visitedNodes.add(current);
            System.out.print(current.getName() +" , ");
            //System.out.println("Visitado: " + current.getValue() + " en (" + current.getRow() + ", " + current.getCol() + ")");
            List<Node> neighbors = current.getNeighbors(heuristic);

            PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::getCost));

            for (Node neighbor : neighbors) {
                if (!visitedNodes.contains(neighbor) && !neighbor.isWall()) {
                    int heuristicValue;
                    if (diagonal) {
                        heuristicValue = getEuclideanDistance(neighbor, goal);
                    } else {
                        heuristicValue = getManhattanDistance(neighbor, goal);
                    }

                    int newCost = current.getCost() + neighbor.getCost();
                    int priority = newCost + heuristicValue;

                    neighbor.setCost(newCost);
                    neighbor.setPriority(priority);

                    pq.add(neighbor);
                }
            }

            if (pq.isEmpty()) {
                // Stuck, return null
                return null;
            }

            current = pq.poll();
            if (k > 1) {
                List<Node> candidates = new ArrayList<>();
                while (!pq.isEmpty() && candidates.size() < k - 1) {
                    candidates.add(pq.poll());
                }
                pq.clear();
                pq.add(current);
                pq.addAll(candidates);
            }
        }
        System.out.print(goal.getName());

        visitedNodes.add(current);
        return visitedNodes;
    }

    private static int getManhattanDistance(Node node1, Node node2) {
        int dx = Math.abs(node1.getCol() - node2.getCol());
        int dy = Math.abs(node1.getRow() - node2.getRow());
        return dx + dy;
    }
    private static int getEuclideanDistance(Node node1, Node node2) {
        int dx = node1.getRow() - node2.getRow();
        int dy = node1.getCol() - node2.getCol();
        return (int) Math.sqrt(dx * dx + dy * dy);
    }
}