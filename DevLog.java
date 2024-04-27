import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ArrayList;

public class DevLog
{

// Class Fields
    private HashMap<Project, ArrayList<String>> contributions;
    private ArrayList<String> lastUpdateAt;

    public DevLog()
    {
        contributions = new HashMap<>();
        populate();
    }

    /*
     * Question 3 a(i)
     */
    public void updateProject(String projectName, String developerName)
    {
        // Add or update an entry in the map for a project
        Project projectToUpdate = fetchProjectByName(projectName);
        if (projectExists(projectToUpdate))
        {
            lastUpdateAt = contributions.get(projectToUpdate);
            lastUpdateAt.add(applyTimestamp());
            removeProject(projectToUpdate.getProjectName());
            projectToUpdate.addContribution(developerName);
            contributions.put(projectToUpdate, lastUpdateAt);
        }
        else
        {
            projectToUpdate = new Project(projectName, developerName);
            addNewProject(projectToUpdate);
        }
    }

    /*
     * Question 3 a(ii)
     */
    public void clear()
    {
        // Clears all entries
        this.contributions.clear();
    }

    /*
     * Question 3 a(iii)
     */
    private void populate()
    {
        // Clears the map then creates different example entries suitable for testing.
        clear();
        updateProject("Test", "Steve");
        updateProject("Bread maker", "John");
        updateProject("Test 2", "Jason");
        updateProject("Bread maker", "Aaron");
        updateProject("Bread maker", "Baxter");
        updateProject("Bread maker", "Steve");
        updateProject("Bread maker", "Aaron");
        updateProject("Test", "Brandon");
    }

    /*
     * Question 3 a(iv)
     */
    public boolean removeProject(String project)
    {
        // Removes a project from the contributors. Returns True or False if the project was found or not.
        Project projectToRemove = fetchProjectByName(project);
        if (projectExists(projectToRemove))
        {
            contributions.remove(fetchProjectByName(project));
            return true;
        }
        return false;
    }

    /*
     * Question 3 a(v)
     */
    public void completeProject(String projectName)
    {
        // updates the isComplete field of the project class to True.
        Project projectToComplete = fetchProjectByName(projectName);
        if (projectExists(projectToComplete))
        {
            lastUpdateAt = contributions.get(projectToComplete);
            lastUpdateAt.add(applyTimestamp());
            removeProject(projectToComplete.getProjectName());
            projectToComplete.setCompleted(true);
            contributions.put(projectToComplete, lastUpdateAt);
        }
    }

    /*
     * Question 3 a(vi)
     */
    public ArrayList<String> getProjectsByDate(String date)
    {
        // Return a list of all the projects updated on a specific date.
        ArrayList<String> projects = new ArrayList<>();

        for (Project project : contributions.keySet())
        {
            for (String projectUpdateDate : contributions.get(project))
            {
                if (projectUpdateDate.substring(0,10).equalsIgnoreCase(date))
                {
                    projects.add(project.getProjectName());
                    break;
                }
            }

        }

        return projects;
    }

    /*
     * Question 3 a(vii)
     */
    public int getTotalNumberOfTodayContributions()
    {
        // Returns the total number of contributions for the current date for all projects.
        int totalNumberOfContributions = 0;

        DateTimeFormatter currentTimestamp = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String today = currentTimestamp.format(LocalDateTime.now());

        for (Project project : contributions.keySet())
        {
            for (String dateOnList: contributions.get(project))
            {
                String date = dateOnList.substring(0,10);
                if (date.equals(today))
                {
                    totalNumberOfContributions++;
                }
            }
        }
        return totalNumberOfContributions;
    }

    /*
     * Question 3 a(viii)
     */
    public void show()
    {
        for(Project project : contributions.keySet())
        {
            System.out.println(project + "\n\nLast Updated at:");
            for (String entry : contributions.get(project))
            {
                System.out.println("- " + entry);
            }
            System.out.println();
        }
    }

    public boolean writeCSVFile(String fileName) throws IOException
    {
        /*
         * Extract the Developer's Log Projects to a csv file. Returns
         * true if there was an exception, or false if not.
         */
        try
        {
            FileWriter writer = new FileWriter(fileName);
            String currentLine = "Project Name,Last updated by,Total contributions,Status,Updated at\n";
            writer.write(currentLine);

            for (Project key : contributions.keySet())
            {
                currentLine =   key.getProjectName() + "," +
                                key.lastUpdatedBy() + "," +
                                key.getTotalContributions() + "," +
                                (key.isCompleted() ? "Completed" : "In Progress");
                //writer.write(currentLine);

                for (String entry : contributions.get(key))
                {
                    currentLine += ",[" + entry + "]";
                }

                currentLine += "\n";
                writer.write(currentLine);
            }

            writer.close();

        }
        catch (Exception e)
        {
            System.err.println("[ERROR] File was not saved successfully. \nReason: " + e);
            return true;
        }

        return false;
    }

// Private Methods
    private void addNewProject(Project projectToAdd) {
        lastUpdateAt = new ArrayList<>();
        lastUpdateAt.add(applyTimestamp());
        contributions.put(projectToAdd, lastUpdateAt);
    }

    private String applyTimestamp()
    {
        String timestamp = "dd-MM-yyyy, HH:mm:ss";
        DateTimeFormatter currentTimestamp = DateTimeFormatter.ofPattern(timestamp);
        return currentTimestamp.format(LocalDateTime.now());
    }

    private boolean projectExists(Project projectToUpdate) {
        return contributions.containsKey(projectToUpdate) && projectToUpdate != null;
    }

    private Project fetchProjectByName(String projectName)
    {
        for (Project fetchProject : this.contributions.keySet())
        {
            if (fetchProject.getProjectName().equals(projectName.toUpperCase()))
            {
                return fetchProject;
            }
        }
        return null;
    }
}
