import java.util.ArrayList;

class Graph {

    private ArrayList<Node> nodes;
    private Node startNode;
    private Node goalNode;

    private boolean zAsignada;

    public Graph() {
        nodes = new ArrayList<>();
        zAsignada=false;
    }

    /**
     * Metodo para agregar nodo
     * @param node
     */
    public void addNode(Node node) {
        nodes.add(node);
    }

    /**
     * Obtener los nodos
     * @return
     */
    public ArrayList<Node> getNodes() {
        return nodes;
    }

    /**
     * Lista de nodos en ascii
     */
    public ArrayList<Node> newList(Graph graph){
        int aux = 65;
        int ascii = 65;
        int aux2 = 65;
        int aux3 = 65;
        for (int i = 0;  i < graph.getNodes().size(); i++){
            graph.nodes.get(i).setName(toASCII(ascii));
            ascii++;
            if (ascii > 91){
                graph.nodes.get(i).setName(toASCII(65)+toASCII(aux));
                aux++;
                if (aux > 91){
                    graph.nodes.get(i).setName(toASCII(66)+toASCII(aux2));
                    aux2++;
                    if (aux2 > 91){
                        graph.nodes.get(i).setName(toASCII(66)+toASCII(aux3));
                        aux3++;
                    }
                }
            }
        }
        return nodes;
    }

    private static String toASCII(int value) {
        int length = 1;
        StringBuilder builder = new StringBuilder(length);


        for (int i = length - 1; i >= 0; i--) {
            builder.append((char) ((value >> (8 * i)) & 0xFF));
        }

        return builder.toString();
    }


    /**
     * Altura del mapa
     * @return
     */
    public int getHeight() {
        int numNodes = nodes.size();
        int numColumns = getColumnWidth();
        return (int) Math.floor(numNodes / numColumns);
    }

    /**
     * Ancho del mapa
     * @return
     */
    public int getColumnWidth() {
        if (getNodes().isEmpty()) {
            return 0;
        }
        int firstRow = getNodes().get(0).getRow();
        int count = 0;
        for (Node node : getNodes()) {
            if (node.getRow() == firstRow) {
                count++;
            }
        }
        return count;
    }

    /**
     * Obtiene el nodo inicio
     * @return
     */
    public Node getStartNode() {
        for (Node node : nodes) {
            if (node.isStart()) {
                startNode = node;
            }
        }
        return startNode;
    }

    /***
     * Obtiene el nodo final
     * @return
     */
    public Node getGoalNode() {
        for (Node node : nodes) {
            if (node.isGoal()) {
                goalNode = node;
            }
        }
        return goalNode;
    }

}