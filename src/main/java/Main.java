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
        Node root = getRoot(urls);
        Vector<Node> nodes = new Vector<>();
        nodes.add(root);
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
            list.add(node.getPrefix() + node.getUrl());
            if (!node.getChildren().isEmpty()) {
                printMap(node.getChildren());
            }
        }
    }
    public static Node getRoot(ArrayList<String> urls){
        Node root = new Node(URL, 0,"");
        ArrayList<Node> parents = new ArrayList<>();
        ArrayList<Node> children;
        parents.add(root);
        while (!parents.isEmpty()){
            children = new ArrayList<>();
            for (Node node : parents) {
                for (String url : urls){
                    if (url.split("/").length < node.getGeneration() + 5 && !url.equals(node.getUrl())
                            && url.contains(node.getUrl()) && uniqueURL2.add(url))
                    {
                        Node child = new Node(url, node.getGeneration() + 1,node.getPrefix() + "\t");
                        node.addChildren(child);
                        children.add(child);
                    }
                }
            }
            parents = new ArrayList<>(children);

        }
        return root;
    }
}
