import java.util.Vector;

public class Node
{
    private String link;
    private Vector<Node> children;
    private int generation;
    private String url;
    private String prefix;

    public Node(String link, int generation, String url, String prefix) {
        this.link = link;
        this.children = new Vector<>();
        this.generation = generation;
        this.url = url;
        this.prefix = prefix;
    }

    public Vector<Node> getChildren() {
        return children;
    }

    public void addChildren(Node child) {
        children.add(child);
    }

    public String getLink() {
        return link;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public String getUrl() {
        return url;
    }

    public String getPrefix() {
        return prefix;
    }
}
