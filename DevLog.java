import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DevLog
{

    private Map<Project, List<String>> contributions;
    private List<String> lastUpdateAt;

    public DevLog(Project newProject)
    {
        contributions = new HashMap<>();
        lastUpdateAt = new LinkedList<>();
        lastUpdateAt.add(getCurrentDate());
        contributions.put(newProject, lastUpdateAt);
    }

    public void updateProject(String projectName, String developerName)
    {
        // Add or update an entry in the map for a project
    }

    public void clear()
    {
        // Clears all entries
    }

    public void populate()
    {
        // Clears the map then creates different example entries suitable for testing.
    }

    public boolean removeProject()
    {
        // Removes a project from the contributors. Returns True or False if the project was found or not.
        return false;
    }

    public void completeProject()
    {
        // updates the isComplete field of the project class to True.
    }

    public List<Project> getLastUpdatedProjectOfContributor(String date, String name)
    {
        // Return a list of all the projects that a specific developer contributed on a specific date.
        List<Project> projects = new ArrayList<>();
        return projects;
    }

    public int getTotalNumberOfContributors()
    {
        // Returns the total number of contributors for a project.
        return -1;
    }

    public void show()
    {
        for(Map.Entry<Project, List<String>> entry : contributions.entrySet())
        {
            Project key = entry.getKey();
            System.out.println(key + "\tLast Updated On:");
            for(String value : entry.getValue())
            {
                System.out.println(value);
            }
        }
    }

    private String getCurrentDate()
    {
        String timestamp = "dd-MM-yyyy, HH:mm:ss";
        DateTimeFormatter currentTimestamp = DateTimeFormatter.ofPattern(timestamp);
        return currentTimestamp.format(LocalDateTime.now());
    }
}
