import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TestDevLog
{
    DevLog devLog;

    public TestDevLog()
    {
        devLog = new DevLog();
        runTest();
    }

    private void runTest()
    {
        testUpdateProject();
        testRemoveProject();
        testCompleteProject();
        testTodayContributions();
        testProjectsByDate();
    }

    private void testRemoveProject()
    {
        System.out.println("=========================== TEST REMOVE PROJECT ==================================");

        devLog.updateProject("DOOM", "Carmack");    // New Project
        devLog.updateProject("Killer FPS", "Carmack");  // Existing Project
        devLog.updateProject("", "");   // Empty Name Project
        devLog.updateProject("DA", "da");   // Less than accepted characters project
        devLog.updateProject("RNA", "DNA"); // The least possible name size for a project
        devLog.updateProject("This is a really looooooooooooooooooooooooooooooooooooooooooooooooooooooooong name", "Nobody");   // More than accepted characters

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
        System.out.println("=============================== END TEST ================================\n");
    }

    private void testUpdateProject()
    {
        System.out.printf("=========================== TEST UPDATE PROJECT ==========================");
        System.out.println("TEST 1: Update project Killer FPS by Carmack");
        devLog.updateProject("Killer FPS", "Carmack");
        System.out.println("TEST 2: Create project DOOM by Romero");
        devLog.updateProject("DOOM", "Romero");
        System.out.println("TEST 3: Project name too short.");
        devLog.updateProject("", "American McGee");
        System.out.println("TEST 4: Project name too long.");
        devLog.updateProject("This is a really looooooooooooooooooooooooooooooooooooooooooooooooooooooooong name", "Cliffy B");

        System.out.println("=============================== END TEST ================================\n");
    }

    private void testCompleteProject()
    {
        System.out.println("================ TEST COMPLETE A PROJECT ==================");
        reinitialize();

        boolean[] testCompleteCase = new boolean[4];
        testCompleteCase[0] = devLog.completeProject("DOOM");
        testCompleteCase[1] = devLog.completeProject("Killer FPS");
        testCompleteCase[2] = devLog.completeProject("Calculator");

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
        System.out.println("=============================== END TEST ================================\n");
    }

    private void testTodayContributions()
    {
        System.out.println("================ TEST TODAY CONTRIBUTIONS ==================");
        reinitialize();
        int todayContributions = devLog.getTotalNumberOfTodayContributions();
        System.out.println("Test number of today's contributions: " +
                (todayContributions == 8 ? "PASS" : "FAIL - Expected " + 8 + " got " + todayContributions));

        System.out.println("=============================== END TEST ================================\n");
    }

    private void testProjectsByDate()
    {
        System.out.println("================ TEST PROJECTS BY DATE ==================");

        reinitialize();

        String today = applyTimestamp().substring(0,10);
        ArrayList<String> expectedProjects = new ArrayList<>();
        expectedProjects.add("CALCULATOR");
        expectedProjects.add("AI ASSISTANT");
        expectedProjects.add("KILLER FPS");
        ArrayList<String> todayProjectsTest = devLog.getProjectsByDate(today);

        System.out.println(
                expectedProjects.equals(todayProjectsTest) ? "PASS" : "FAIL"
        );

        System.out.println("=============================== END TEST ================================\n");
    }

    private String applyTimestamp()
    {
        String timestamp = "dd-MM-yyyy, HH:mm:ss";
        DateTimeFormatter currentTimestamp = DateTimeFormatter.ofPattern(timestamp);
        return currentTimestamp.format(LocalDateTime.now());
    }

    private void reinitialize()
    {
        System.out.println("[CLASS REINITIALIZATION]");
        devLog.populate();
        System.out.println("[REINITIALIZATION END]");
    }
}
