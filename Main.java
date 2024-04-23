public class Main
{
    public static void main(String[] args)
    {
        DevLog d1 = new DevLog();
        d1.completeProject("Test");
        d1.show();
        System.out.println("Total Contributions today: " + d1.getTotalNumberOfTodayContributions());
        System.out.println("Projects Updated on 23-04-2024: \n" + d1.getProjectsByDate("23-04-2024"));
    }
}
