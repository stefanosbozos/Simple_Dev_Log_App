import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;

public class DevLog
{

    private Map<Project, List<String>> contributors;
    private List<String> lastContributor;

    public DevLog(Project newProject, String firstContributor)
    {
        contributors = new HashMap<>();
        lastContributor = new LinkedList<>();
        lastContributor.add(firstContributor);
        contributors.put(newProject,lastContributor);
    }

    public void updateLog(String projectName, String contributorName, String message)
    {
        // To be implemented
    }


    //DEBUG
    public void show()
    {
        for(Map.Entry<Project, List<String>> entry : contributors.entrySet())
        {
            Project key = entry.getKey();
            System.out.println(key + "\nContributors:");
            for(String value : entry.getValue())
            {
                System.out.println(value);
            }
        }
    }
}
