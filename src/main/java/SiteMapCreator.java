import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class SiteMapCreator extends RecursiveAction
{

    private String link;

    public SiteMapCreator(String link)
    {
        this.link = link;
    }

    @Override
    protected void compute()
    {
        List<SiteMapCreator> taskList = new ArrayList<>();
        try {
            Document doc = getDoc(link);
            Elements links = doc.select("a[href]");

            if (!links.isEmpty()) {
                for (Element link : links) {
                    String thisUrl = link.absUrl("href");
                    if (checkURL(thisUrl) && addNewURL(thisUrl)){
                        System.out.println(thisUrl);
                        SiteMapCreator task = new SiteMapCreator(thisUrl);
                        task.fork();
                        taskList.add(task);
                    }
                }
            }

        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }

        taskList.forEach(ForkJoinTask::join);
    }

    public static Document getDoc(String url) throws IOException, InterruptedException {
        Random random = new Random();
        Thread.sleep((random.nextInt(10) + 1) * 200);
        return Jsoup.connect(url).maxBodySize(0).get();
    }

    private synchronized boolean addNewURL(String url){

        return Main.uniqueURL.add(url);
    }
    private static boolean checkURL(String url){
        return url.startsWith(Main.URL) && url.endsWith("/");
    }
}
