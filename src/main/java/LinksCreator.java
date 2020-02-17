import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
public class LinksCreator
{
    public static volatile Set<String> uniqueURL = new HashSet<>();
    private String link;



    public LinksCreator(String url){

        link = url;
    }


    public ArrayList<String> getLinks(String url) {
        ArrayList<String> urls = new ArrayList<>();

        try {
            Document doc = getDoc(url);
            Elements links = doc.select("a[href]");

            if (links.isEmpty()) {
                return null;
            }

            for (Element link : links) {
                String thisUrl = link.attr("abs:href").split("\\?")[0].split("#")[0];
                if (!uniqueURL.add(thisUrl) || !thisUrl.contains(Main.URL)){
                    continue;
                }
                urls.add(thisUrl);
                System.out.println(thisUrl);
            }


        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
        finally {
            return urls;
        }
    }
    public static synchronized Document getDoc(String url) throws IOException, InterruptedException {
        Random random = new Random();
        Thread.sleep((random.nextInt(10) + 1) * 200);
        return Jsoup.connect(url).maxBodySize(0).get();
    }

    public String getLink() {
        return link;
    }
}

