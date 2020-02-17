import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class SiteMapCreator extends RecursiveAction
{

    private LinksCreator link;

    public SiteMapCreator(LinksCreator link)
    {
        this.link = link;
    }

    @Override
    protected void compute()
    {
        List<SiteMapCreator> taskList = new ArrayList<>();
        ArrayList<String> newLinks = link.getLinks(link.getLink());

        if (!newLinks.isEmpty()){
            newLinks.forEach(l -> {
                SiteMapCreator task = new SiteMapCreator(new LinksCreator(l));
                task.fork();
                taskList.add(task);
            });

        }

        taskList.forEach(ForkJoinTask::join);
    }
}
