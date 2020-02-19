import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ForkJoinPool;


public class Main
{
    public static final String URL = "https://skillbox.ru/";
    public static ArrayList<String> list = new ArrayList<>();
    public static volatile Set<String> uniqueURL = new HashSet<>();
    private static Set<String> uniqueURL2 = new HashSet<>();

    public static void main(String[] args) throws IOException {

        new ForkJoinPool().invoke(new SiteMapCreator(URL));

        ArrayList<String> urls = new ArrayList<>(uniqueURL);
        Vector<Node> nodes = getNodes(urls);
        printMap(nodes);

        PrintWriter writer = new PrintWriter("file.txt");
        for (String str : list){
            writer.write(str + "\n");
        }
        writer.flush();
        writer.close();
    }

    public static void printMap(Vector<Node> nodes){
        for (Node node : nodes) {
            list.add(node.getPrefix() + node.getLink());
            if (!node.getChildren().isEmpty()) {
                printMap(node.getChildren());
            }
        }
    }
    public static Vector<Node> getNodes(ArrayList<String> urls){
        Node root = new Node(URL, 0, URL,"");
        ArrayList<Node> parents = new ArrayList<>();
        ArrayList<Node> children;
        parents.add(root);
        while (!parents.isEmpty()){
            children = new ArrayList<>();
            for (Node node : parents) {
                for (String u : urls){
                    if (u.split("/").length < node.getGeneration() + 5 && !u.equals(node.getUrl())
                            && uniqueURL2.add(u)) {
                        Node child = new Node(u, node.getGeneration() + 1, u, node.getPrefix() + "\t");
                        node.addChildren(child);
                        children.add(child);
                        System.out.println(u);
                    }
                }
            }
            parents = new ArrayList<>(children);

        }
        Vector<Node> nodes = new Vector<>();
        nodes.add(root);
        return nodes;
    }
}
