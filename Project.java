import java.util.Objects;
import java.util.ArrayList;

public class Project implements Comparable<Project>
{
// Class Fields
    private String projectName;
    private ArrayList<String> contributingDevelopers;
    private int totalContributions = 0;
    private boolean isCompleted;

    public Project(String projectName, String developersName)
    {
        this.projectName = projectName.toUpperCase();
        this.contributingDevelopers = new ArrayList<>();
        contributingDevelopers.add(developersName);
        this.totalContributions = contributingDevelopers.size();
        setCompleted(false);
    }

    public String getProjectName() {
        return projectName;
    }

    public int getTotalContributions() {
        return totalContributions;
    }

    public ArrayList<String> getContributingDevelopers() {
        return contributingDevelopers;
    }

    public String lastUpdatedBy()
    {
        return contributingDevelopers.getLast();
    }

    public void addContribution(String developersName)
    {
        contributingDevelopers.add(developersName);
        totalContributions = contributingDevelopers.size();
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed)
    {
        this.isCompleted = completed;
    }

// Overridden Methods
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
        return "Project{" +
                "projectName='" + projectName + '\'' +
                ", lastUpdatedBy='" + contributingDevelopers.getLast() + '\'' +
                ", totalContributions=" + totalContributions +
                ", isCompleted=" + isCompleted +
                '}';
    }
}
