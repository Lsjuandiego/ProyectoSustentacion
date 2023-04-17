import java.util.ArrayList;

class Node {
    int priority;
    private Node parent;
    private String value, name;
    private int row, col,cost;
    private ArrayList<Node> neighbors;
    private boolean isStart, isGoal, isWall;
    private static final int UNIT_COST = 1; // Costo uniforme para todas las aristas

    public Node(String value, int row, int col) {
        this.value = value;
        this.name = name;
        this.row = row;
        this.col = col;
        this.parent = null;
        neighbors = new ArrayList<>();
        isStart = value.equals("I");
        isGoal = value.equals("F");
        isWall = value.equals("R") || value.equals("M");
        this.cost = 0;
    }

    /**
     * Metodo para asignar la prioridad según la heuristica
     * @param priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Metodo para obtener la "letra" del nodo
     * @return
     */
    public String getValue() {
        return value;
    }
    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    /**
     * Obtener la fila
     * @return
     */
    public int getRow() {
        return row;
    }

    /**
     * Obtener la columna
     * @return
     */
    public int getCol() {
        return col;
    }

    /**
     * Metodo para agregar vecino
     * @param neighbor
     */
    public void addNeighbor(Node neighbor) {
        neighbors.add(neighbor);
    }

    /**
     * Obtiene los vecinos unicamente horizontales y verticales
     * @return lista de vecinos
     */
    public ArrayList<Node> getNeighborsWithoutHeuristics() {
        ArrayList<Node> validNeighbors = new ArrayList<>();
        for (Node neighbor : neighbors) {
            int dx = Math.abs(neighbor.getRow() - this.getRow());
            int dy = Math.abs(neighbor.getCol() - this.getCol());
            if ((dx == 1 && dy == 0) || (dx == 0 && dy == 1)) {
                validNeighbors.add(neighbor);
            }
        }
        return validNeighbors;
    }

    /**
     * Obtiene todos los vecinos (horizontales y verticales)
     * @param heuristic
     * @return
     */
    public ArrayList<Node> getNeighbors(HeuristicType heuristic) {
        ArrayList<Node> validNeighbors = new ArrayList<>();
        for (Node neighbor : neighbors) {
            int dx = Math.abs(neighbor.getRow() - this.getRow());
            int dy = Math.abs(neighbor.getCol() - this.getCol());
            if ((dx == 1 && dy == 0) || (dx == 0 && dy == 1)) {
                validNeighbors.add(neighbor);
            }
            if (heuristic == HeuristicType.EUCLIDEAN && dx == 1 && dy == 1) {
                validNeighbors.add(neighbor);
            }
        }
        return validNeighbors;
    }


    public boolean isStart() {
        return isStart;
    }

    public boolean isGoal() {
        return isGoal;
    }

    public boolean isWall() {
        return isWall;
    }


    /**
     * Metodo para obtener el costo del nodo
     * @return
     */
    public int getCost() {
        return UNIT_COST;
    }

    /**
     * Asignar el costo al nodo
     * @param cost
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setValue(String value){
        this.value = value;
    }



}