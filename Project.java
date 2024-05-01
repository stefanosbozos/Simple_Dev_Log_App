import java.util.Objects;
import java.util.ArrayList;

public class Project implements Comparable<Project>
{
    /*
     * Question 2 b(i)
     */
    private String projectName;
    private ArrayList<String> contributingDevelopers;       // A list that holds the names of all the developers that have contributed to the project.
    private int totalContributions = 0;                     // The number of updates/contributions for the project.
    private boolean isCompleted;                     // Represents the status of a Project. true if the Project is completed and false if not.

    /*
        Constructor - Receives the project name and the developer's name as String arguments and
        initializes the contributingDevelopers array, adds the developer's name to the array, sets the
        number of total contributions to the size of the contributingDeveloper's array and initializes
        the status of the project to false.
     */
    public Project(String projectName, String developersName)
    {
        this.projectName = projectName.toUpperCase();
        this.contributingDevelopers = new ArrayList<>();
        contributingDevelopers.add(developersName);
        this.totalContributions = contributingDevelopers.size();
        this.isCompleted = false;
    }

    /**
     * @return a String representing the name of the project.
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @return an integer representing the number of total updates/contributions in the project.
     */
    public int getTotalContributions() {
        return totalContributions;
    }

    /**
     * Returns a list with the names of all the developers that contributed in the project.
     * @return an ArrayList of String elements.
     */
    public ArrayList<String> getContributingDevelopers() {
        return contributingDevelopers;
    }

    /**
     * Returns the name of the last developer that contributed to the project.
     * @return a String with the name of the developer.
     */
    public String lastUpdatedBy()
    {
        return contributingDevelopers.getLast();
    }

    /**
     * Adds the name of the developer to the list that stores the names of the contributing developers, and
     * sets the number of totalContributions to the size of the contributingDevelopers list.
     * @param developersName a String representing the name of the developer that contributes in the project.
     */
    public void addContribution(String developersName)
    {
        contributingDevelopers.add(developersName);
        totalContributions = contributingDevelopers.size();
    }

    /**
     * Check if the project is complete or not.
     * @return a boolean true if the project is complete, and false if not.
     */
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Change the status of the project depending on if the project is complete or not.
     */
    public void changeCompletionStatus()
    {
        this.isCompleted = !this.isCompleted;
    }


    /*
     * Question 2 b (ii)
     *
     */
    @Override
    public boolean equals(Object object)
    {
        if(object instanceof Project)
        {
            Project project = (Project) object;

            return getProjectName().equals(project.getProjectName()) &&
                    getTotalContributions() == project.getTotalContributions() &&
                    getContributingDevelopers().equals(project.getContributingDevelopers());
        }
        return false;
    }

    @Override
    public int compareTo(Project projectToCompare)
    {
        return (int)(this.totalContributions - projectToCompare.totalContributions);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getProjectName(), getTotalContributions(), getContributingDevelopers(), isCompleted());
    }

    @Override
    public String toString() {
        return "Project Name:\t" + projectName +
                "\nLast updated by:\t" + lastUpdatedBy() +
                "\nNo. of contributions:\t" + totalContributions +
                "\n" + (isCompleted() ? "[Completed]" : "[In progress]");
    }
}
