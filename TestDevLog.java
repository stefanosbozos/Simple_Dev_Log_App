import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TestDevLog
{
    DevLog devLog;

    public TestDevLog() throws IOException
    {
        devLog = new DevLog();
        runTest();
    }

    private void runTest() throws IOException
    {
        testUpdateProject();
        testRemoveProject();
        testCompleteProject();
        testTodayContributions();
        testProjectsByDate();
        testWriteFile();
    }

    private void testRemoveProject()
    {
        System.out.printf("%n %35s %n%n", "[TEST REMOVE A PROJECT]");

        devLog.contributeToProject("DOOM", "Carmack");    // New Project
        devLog.contributeToProject("Killer FPS", "Carmack");  // Existing Project
        devLog.contributeToProject("", "");   // Empty Name Project
        devLog.contributeToProject("DA", "da");   // Less than accepted characters project
        devLog.contributeToProject("RNA", "DNA"); // The least possible name size for a project
        devLog.contributeToProject("This is a really looooooooooooooooooooooooooooooooooooooooooooooooooooooooong name", "Nobody");   // More than accepted characters

        boolean[] testRemoveCase = new boolean[7];
        testRemoveCase[0] = devLog.removeProject("DOOM");
        testRemoveCase[1] = devLog.removeProject("Killer FPS");
        testRemoveCase[2] = devLog.removeProject("");
        testRemoveCase[3] = devLog.removeProject("DA");
        testRemoveCase[4] = devLog.removeProject("RNA");
        testRemoveCase[5] = devLog.removeProject("I do not exist!");
        testRemoveCase[6] = devLog.removeProject("This is a really looooooooooooooooooooooooooooooooooooooooooooooooooooooooong name");

        for (int i = 0; i < testRemoveCase.length; i++)
        {
            switch(i)
            {
                case 0,1,4:
                    System.out.println("Test " + (i) + " : " + (testRemoveCase[i] ? "PASS" : "FAIL expected \'true\' got " + testRemoveCase[i]));
                    break;
                case 2,3,5,6:
                    System.out.println("Test " + (i) + " : " + (testRemoveCase[i] ? "FAIL expected \'false\' got " + testRemoveCase[i] : "PASS"));
                    break;
                default:
                    break;

            }
        }
        System.out.printf("%n %28s %n","[END OF TEST]");
    }

    private void testUpdateProject()
    {
        System.out.printf("%n %35s %n%n", "[TEST UPDATE A PROJECT]");

        System.out.println("TEST 1: Update project Killer FPS by Carmack");
        devLog.contributeToProject("Killer FPS", "Carmack");
        System.out.println("TEST 2: Create project DOOM by Romero");
        devLog.contributeToProject("DOOM", "Romero");
        System.out.println("TEST 3: Project name too short.");
        devLog.contributeToProject("", "American McGee");
        System.out.println("TEST 4: Project name too long.");
        devLog.contributeToProject("This is a really looooooooooooooooooooooooooooooooooooooooooooooooooooooooong name", "Cliffy B");

        System.out.printf("%n %28s %n","[END OF TEST]");
    }

    private void testCompleteProject()
    {
        System.out.printf("%n %35s %n%n", "[TEST SET A PROJECT AS COMPLETE]");
        reinitialize();

        boolean[] testCompleteCase = new boolean[4];
        testCompleteCase[0] = devLog.setProjectComplete("DOOM");
        testCompleteCase[1] = devLog.setProjectComplete("Killer FPS");
        testCompleteCase[2] = devLog.setProjectComplete("Calculator");

        for (int i = 0; i < testCompleteCase.length; i++)
        {
            switch(i)
            {
                case 1,2:
                    System.out.println("Test " + (i + 1) + " : " + (testCompleteCase[i] ? "PASS" : "FAIL expected \'true\' got " + testCompleteCase[i]));
                    break;
                case 0:
                    System.out.println("Test " + (i + 1) + " : " + (testCompleteCase[i] ? "FAIL expected \'false\' got " + testCompleteCase[i] : "PASS"));
                    break;
                default:
                    break;

            }
        }
        System.out.printf("%n %28s %n","[END OF TEST]");
    }

    private void testTodayContributions()
    {
        System.out.printf("%n %35s %n%n", "[TEST PROJECTS TODAY CONTRIBUTIONS]");
        reinitialize();
        int todayContributions = devLog.getNumberOfTodaysUpdates();
        System.out.println("Test number of today's contributions: " +
                (todayContributions == 8 ? "PASS" : "FAIL - Expected " + 8 + " got " + todayContributions));

        System.out.printf("%n %28s %n","[END OF TEST]");
    }

    private void testProjectsByDate()
    {
        System.out.printf("%n %35s %n%n", "[TEST GET PROJECTS BY CERTAIN DATE]");

        reinitialize();

        String today = applyTimestamp().substring(0,10);
        ArrayList<String> expectedProjects = new ArrayList<>();
        expectedProjects.add("CALCULATOR");
        expectedProjects.add("AI ASSISTANT");
        expectedProjects.add("KILLER FPS");
        ArrayList<String> todayProjectsTest = devLog.getProjectsOnDate(today);

        System.out.println(
                expectedProjects.equals(todayProjectsTest) ? "PASS" : "FAIL"
        );

        System.out.printf("%n %28s %n","[END OF TEST]");
    }

    private void testWriteFile() throws IOException
    {
        boolean test1 = devLog.saveToCSV("Test_1");
        boolean test2 = devLog.saveToCSV(null);

        System.out.printf("%n %35s %n%n", "[TEST EXTRACT DATA TO A CSV FILE]");

        System.out.println("Test Create a file named \"Test_1\": " + (test1 ? "PASS" : "FAIL expected true and got " + test1));
        System.out.println("Test Pass a null value as filename: " + (!test2 ? "PASS" : "FAIL expected false and got " + test1));

        System.out.printf("%n %28s %n","[END OF TEST]");

    }

    private String applyTimestamp()
    {
        String timestamp = "dd-MM-yyyy, HH:mm:ss";
        DateTimeFormatter currentTimestamp = DateTimeFormatter.ofPattern(timestamp);
        return currentTimestamp.format(LocalDateTime.now());
    }

    private void reinitialize()
    {
        System.out.printf("%20s %n","[CLASS REINITIALIZATION]");
        devLog.populate();
        System.out.printf("%20s %n","[REINITIALIZATION END]");
    }
}
