import java.util.Vector;

public class Node
{
    private Vector<Node> children;
    private int generation;
    private String url;
    private String prefix;

    public Node(String url, int generation, String prefix) {
        this.url = url;
        this.children = new Vector<>();
        this.generation = generation;
        this.prefix = prefix;
    }

    public Vector<Node> getChildren() {
        return children;
    }

    public void addChildren(Node child) {
        children.add(child);
    }

    public int getGeneration() {
        return generation;
    }


    public String getUrl() {
        return url;
    }

    public String getPrefix() {
        return prefix;
    }
}
