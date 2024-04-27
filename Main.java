import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException {
        DevLog d1 = new DevLog();
        d1.completeProject("Test");
        d1.show();
        System.out.println("Total Contributions today: " + d1.getTotalNumberOfTodayContributions());
        System.out.println("Projects Updated on 23-04-2024: \n" + d1.getProjectsByDate("27-04-2024"));
        System.out.println(d1.writeCSVFile("test.csv"));
    }
}
