import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Project
{
    private String projectName;
    private String projectLog;
    private int totalContributions;
    private boolean isCompleted;

    public Project(String projectName)
    {
        this.projectName = projectName.toUpperCase();
        this.projectLog = "[" + getCurrentDate() + "] - INFO: Project "
                                    + projectName + " created.\n";
        this.totalContributions = 1;
        setCompleted(false);
    }

    public String getProjectName() {
        return projectName;
    }

    public int getTotalContributions() {
        return totalContributions;
    }

    public void addContribution(String message)
    {
        this.projectLog += "[" + getCurrentDate() + "] - INFO: Project "
                            + projectName + " updated. " + message + "\n";
        totalContributions++;

        if (totalContributions == 100)
        {
            setCompleted(true);
        }

    }

    public boolean isCompleted() {
        return isCompleted;
    }

    private void setCompleted(boolean completed)
    {
        this.isCompleted = completed;
    }

    private String getCurrentDate()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return dtf.format(LocalDate.now());
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectName='" + projectName + '\'' +
                ", log entries='" + projectLog + '\'' +
                ", totalContributions=" + totalContributions +
                ", isCompleted=" + isCompleted +
                '}';
    }
}
