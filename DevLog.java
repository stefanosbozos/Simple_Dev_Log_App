import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ArrayList;

public class DevLog
{
    /**
     *  Class DevLog simulates a small database that stores and updates coding projects
     *  that are being created. The class creates a new Map of Projects and stores for each
     *  project a list of timestamps for any new update that is being made on a specific project.
     *
     * @Author Stefanos Bozos
     * @Version 01/05/2024
     */

    private HashMap<Project, ArrayList<String>> contributions;              // Stores as a key a project and points each key to a list of timestamps.
    private ArrayList<String> lastUpdateAt;                                 // The list that holds the timestamps of every update for a project.

    /*
     * Constructor of DevLog. Initializes the map and populates the database
     * with sample entries.
     */
    public DevLog()
    {
        contributions = new HashMap<>();
        populate();
    }

    /**
     *  Question 3 a(i)
     *  Checks if a project exists based on its name and if it does and the project is not complete,
     *  updates the project by adding a new timestamp, passes the developer's name to the list of
     *  developers for the Project, and updates the number of contributions. Otherwise, checks if the argument
     *  that is passed as name of the project is between 2 and 20 characters long and adds a new project to the
     *  DevLog map.
     *
     * @param projectName a String representing the name of the project we want to update or create.
     * @param developerName a String representing the name of the developer that updates the projects or creates it.
     * @return a boolean true if the project was created/updated successfully or false if not.
     */
    public boolean contributeToProject(String projectName, String developerName)
    {
        // Check if the project exists. If not it will return null.
        Project projectToUpdate = fetchProjectByName(projectName);

        if (projectExists(projectToUpdate))
        {
            return updateExistingProject(projectName, developerName, projectToUpdate);
        }
        else
        {
            return createNewProject(projectName, developerName);
        }
    }

    /**
     *  Question 3 a(iv)
     *  Removes a project from the DevLog's contributions map, if it exists.
     * @param project a String representing the name of the project we want to remove.
     * @return a boolean true if the project was removed successfully, or false if not.
     */
    public boolean removeProject(String project)
    {
        // Find the project in the contributions map. If project not found returns null.
        Project projectToRemove = fetchProjectByName(project);

        // Check if the project exist. Otherwise, exit the method and return false.
        if (projectExists(projectToRemove))
        {
            contributions.remove(fetchProjectByName(project));
            return true;
        }
        return false;
    }

    /**
     *  Question 3 a(v)
     *  Changes the isComplete status of a project to true.
     * @param projectName a String representing the name of the project we want to set as complete.
     * @return a boolean true if the project status changed successfully, false if not.
     */
    public boolean setProjectComplete(String projectName)
    {

        Project projectToComplete = fetchProjectByName(projectName);

        // Check if the project exists and is not marked as complete.
        if (projectExists(projectToComplete) && !projectToComplete.isCompleted())
        {
            lastUpdateAt = contributions.get(projectToComplete);
            lastUpdateAt.add(applyTimestamp());
            removeProject(projectToComplete.getProjectName());
            projectToComplete.changeCompletionStatus();
            contributions.put(projectToComplete, lastUpdateAt);
            return true;
        }
        System.out.println("Project " + projectName + " was not updated. Project does not exist or does not accept any more updates.");
        return false;
    }

    /**
     * Question 3 a(vi)
     * Based on the date that the user passes as argument, the method returns
     * a list with the names of the projects that have been updated on that date.
     *
     * @param date a String the date of the projects that we want to get.
     * @return an ArrayList of Strings with all the projects on the specified date.
     */
    public ArrayList<String> getProjectsOnDate(String date)
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

    /**
     * Question 3 a(vii)
     * Returns the total number of Today's contributions for all the projects in the DevLog.
     *
     * @return an int representing the total number of updates for the day that the method runs.
     */
    public int getNumberOfTodaysUpdates()
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

    /**
     * Question 3 a(viii)
     * Prints in the terminal all the projects that are saved in the DevLog's contributions map.
     * If the map is empty displays a relevant message.
     */
    public void displayAllProjects()
    {
        for(Project project : contributions.keySet())
        {
            if(contributions.isEmpty())
            {
                System.out.println("It feels kind of empty here... Time for a new project!");
            }
            else
            {
                System.out.println(project + "\n\nLast Updated at:");
                for (String entry : contributions.get(project))
                {
                    System.out.println("- " + entry);
                }
            }
            System.out.println();
        }

    }

    /**
     * Question 3 b
     * This method write in a .csv file the contents of the DevLog contributions map. The
     * file is being saved in the same directory as the class DevLog and the name is set by
     * the user. If the file name is null or not a permitted input (e.g. an integer) the method
     * throws an IOException. Returns either true if the file was written and saved successfully, or
     * false if an exception is being thrown.
     *
     * @param fileName a String, the name that we want to give to our created file.
     * @return  a boolean, true if the file was written successfully, false is there was an exception thrown.
     * @throws IOException in the case that the fileName argument is null or contains not permitted variable types.
     */
    public boolean saveToCSV(String fileName) throws IOException
    {
        try
        {
            fileName += fileName + ".csv";
            FileWriter writer = new FileWriter(fileName);
            String currentLine = "Project Name,Last updated by,Total contributions,Status,Updated at\n";
            writer.write(currentLine);

            for (Project key : contributions.keySet())
            {
                currentLine =   key.getProjectName() + "," +
                        key.lastUpdatedBy() + "," +
                        key.getTotalContributions() + "," +
                        (key.isCompleted() ? "Completed" : "In Progress");

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
            System.out.println("[ERROR] File was not saved successfully. \nReason: " + e);
            return false;
        }
        return true;
    }

    /**
     *  Question 3 a(ii)
     *  Clears all the entries from the contributions map.
     */
    public void clear()
    {
        // Clears all entries
        this.contributions.clear();
    }

    /**
     *  Question 3 a(iii)
     *  Populates the contributions map with sample entries for testing purposes.
     */
    public void populate()
    {
        // Clears the map then creates different example entries suitable for testing.
        clear();
        contributeToProject("Calculator", "Steve");
        contributeToProject("AI Assistant", "John");
        contributeToProject("Killer FPS", "Jason");
        contributeToProject("Calculator", "Aaron");
        contributeToProject("Killer FPS", "Baxter");
        contributeToProject("Killer FPS", "Steve");
        contributeToProject("Killer FPS", "Aaron");
        contributeToProject("AI Assistant", "Brandon");
    }

    // Private Methods
    /**
     * Creates a new object of type Project, and adds the project to the contributions map. The name of
     * the project should be more than 2 characters and less than 20 characters long.
     *
     * @param projectName a String, the name of the project we want to create.
     * @param developerName a String, the name of the developers that creates the project.
     * @return  a boolean true if the project was created successfully, or false if the given name does not
     * meet the naming requirements.
     */
    private boolean createNewProject(String projectName, String developerName) {
        Project projectToUpdate;
        // Check if the name for a new project applies to our constraints.
        if (projectName.length() > 2 && projectName.length() < 20)
        {
            projectToUpdate = new Project(projectName, developerName);
            addNewProject(projectToUpdate);
            System.out.println("Project " + projectName + " created by " + developerName + ".");
            return true;
        }
        else
        {
            System.out.println("Project name should be more than 2 and less that 20 characters long.");
            return false;
        }
    }

    /**
     * Checks if a project is complete and if not it updates the project, by adding a new
     * timestamp to the lastUpdateAt list, adds the name of the developer in the list of
     * contributing developers of the Project item, updates the number of contributions,
     * removes the current key and value, and re-adds the updated Project type item as key,
     * and the updated list of timestamps as value.
     *
     * @param projectName a String, the name of the project we want to update.
     * @param developerName a String, the name of the developer that updates the project.
     * @param projectToUpdate   an object of type Project, the project that we want to update.
     * @return a boolean, true if the project was updated successfully, or false is the project does
     * not accept any new updates, because is marked as Complete.
     */
    private boolean updateExistingProject(String projectName, String developerName, Project projectToUpdate) {
        // If the project is marked as complete do not update the project further.
        if(!projectToUpdate.isCompleted())
        {
            lastUpdateAt = contributions.get(projectToUpdate);
            lastUpdateAt.add(applyTimestamp());
            removeProject(projectToUpdate.getProjectName());
            projectToUpdate.addContribution(developerName);
            contributions.put(projectToUpdate, lastUpdateAt);
            System.out.println("Project " + projectName + " updated by " + developerName + ".");
            return true;
        }
        else
        {
            System.out.println("Project " + projectName + " cannot be updated as it is marked as Complete.");
            return false;
        }
    }

    /**
     * Adds a type of Project item as key to the contributions map and adds a new timestamp to the
     * lastUpdateAt list, which is added as value for the Project key.
     *
     * @param projectToAdd  a type of Project object that is set as a key for the contributions map.
     */
    private void addNewProject(Project projectToAdd) {
        lastUpdateAt = new ArrayList<>();
        lastUpdateAt.add(applyTimestamp());
        contributions.put(projectToAdd, lastUpdateAt);
    }

    /**
     * Creates a timestamp for the time that the method is called. It is used to be applied
     * to update the lastUpdateAt list everytime a project is updated.
     *
     * @return a String in the form of dd-MM-yyyy, HH:mm:ss
     */
    private String applyTimestamp()
    {
        String timestamp = "dd-MM-yyyy, HH:mm:ss";
        DateTimeFormatter currentTimestamp = DateTimeFormatter.ofPattern(timestamp);
        return currentTimestamp.format(LocalDateTime.now());
    }

    /**
     * Checks if a Project type item is not null and exists in the contributions map
     *
     * @param projectToUpdate a Project type of element that holds information about a created project.
     * @return  true if the project is found in the keys of the map,
     * or false if the Project is either null, or does not exist in the map's keys.
     */
    private boolean projectExists(Project projectToUpdate) {
        return contributions.containsKey(projectToUpdate) && projectToUpdate != null;
    }

    /**
     * Finds and returns a specific project based on the given name as an argument.
     *
     * @param projectName a String, the name of the project we are searching.
     * @return the Project type item that contains the given project name, or null if the project does not exist.
     */
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
