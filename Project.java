import java.util.Objects;
import java.util.ArrayList;

public class Project implements Comparable<Project>
{
    /**
     *  This class is a POJO object representing a Project. It holds information
     *  such as the name of the project, who has contributed in the project, how
     *  many contributions have been made, and if the project is finished or not.
     *
     * @Author Stefanos Bozos
     * @Version 01/05/2024
     */

    /*
     * Question 2 b(i)
     * Added setters, getters, constructor and class methods
     */
    private String projectName;                                     // The name of the project.
    private ArrayList<String> contributingDevelopers;               // A list that holds the names of all the developers that have contributed to the project.
    private int totalContributions = 0;                             // The number of updates/contributions for the project.
    private boolean isCompleted;                                    // Represents the status of a Project. true if the Project is completed and false if not.


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
     *  Question 2 b (ii)
     *  Implement the overridden methods equals, hashCode and compareTo
     */

    /**
     *  Checks if an object is an instance of the Project class and if it shares the same name,
     *  number of contributions to the project, and the same contributing developers.
     *
     * @param object that is going to be checked for equality with the class Project
     * @return a boolean true or false, depending on the object's equality to Project name, number of contributions
     * and the list of contributing developers.
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

    /**
     * Compares two type of Project objects based on their total number of contributions. If current Project has more
     * contributions it returns a positive number, otherwise it returns a negative. The method returns 0 if equals.
     *
     * @param projectToCompare the object type of Project to be compared based on the number of total contributions.
     * @return an int representing which Project's total contributions are greater/lesser than the compared Project.
     */
    @Override
    public int compareTo(Project projectToCompare)
    {
        return (int)(this.totalContributions - projectToCompare.totalContributions);
    }

    /**
     * Creates a hash for the current project object based on its name, number of contributions, list of contributing
     * developers and its completions status.
     * @return a hash of Project.
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(getProjectName(), getTotalContributions(), getContributingDevelopers(), isCompleted());
    }

    /**
     * Creates and returns a formatted representations of the fields of the Project.
     *
     * @return a String showing the fields of the Project.
     */
    @Override
    public String toString() {
        return "Project Name:\t" + projectName +
                "\nLast updated by:\t" + lastUpdatedBy() +
                "\nNo. of contributions:\t" + totalContributions +
                "\n" + (isCompleted() ? "[Completed]" : "[In progress]");
    }
}
