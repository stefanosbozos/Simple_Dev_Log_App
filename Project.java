import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Project
{
    private String projectName;
    private String lastUpdatedBy;
    private int totalContributions = 0;
    private boolean isCompleted;

    public Project(String projectName, String developersName)
    {
        this.projectName = projectName.toUpperCase();
        this.lastUpdatedBy = developersName;
        this.totalContributions++;
        setCompleted(false);
    }

    public String getProjectName() {
        return projectName;
    }

    public int getTotalContributions() {
        return totalContributions;
    }

    public String getLastUpdatedBy()
    {
        return lastUpdatedBy;
    }

    public void addContribution(String developersName)
    {
        this.lastUpdatedBy = developersName;
        totalContributions++;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed)
    {
        this.isCompleted = completed;
    }


    @Override
    public boolean equals(Object object)
    {
        if(object instanceof Project)
        {
            Project project = (Project) object;

            return getProjectName().equals(project.getProjectName()) &&
                    getTotalContributions() == project.getTotalContributions() &&
                    getLastUpdatedBy().equals(project.getLastUpdatedBy());
        }
        return false;
    }

    public int compareTo(Object object)
    {
        if(object instanceof Project)
        {
            Project project = (Project) object;

            return project.getProjectName().compareTo(this.projectName);
        }
        return -1;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getProjectName(), getTotalContributions(), getLastUpdatedBy(), isCompleted());
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectName='" + projectName + '\'' +
                ", lastUpdatedBy='" + lastUpdatedBy + '\'' +
                ", totalContributions=" + totalContributions +
                ", isCompleted=" + isCompleted +
                '}';
    }
}
