import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TestDevLog
{
    /**
     * This class performs unit testing for the functionality of class DevLog.
     *
     * @author Stefanos Bozos
     * @version 01/05/2024
     */
    public static boolean isTesting;                // Flag to disable the user messages in class DevLog
    private DevLog devLog;

    /**
     * Constructor - sets the isTesting flag to true, initializes an object of type DevLog.
     * @throws IOException
     */
    public TestDevLog() throws IOException
    {
        isTesting = true;
        devLog = new DevLog();
    }

    /**
     * Conducts all the test sequentially and when is finished sets the isTesting flag to false.
     * @throws IOException
     */
    public void runTest() throws IOException
    {
        System.out.printf("%28s %n%n","[START OF TEST]");
        testUpdateAProject();
        testRemoveProject();
        testCompleteProject();
        testTodayContributions();
        testProjectsByDate();
        testWriteFile();
        System.out.printf("%28s","[END OF TEST]");
        isTesting = false;
    }

    /**
     * Performs a unit testing for the method contributeToProject.
     */
    private void testUpdateAProject()
    {
        System.out.println("[TEST UPDATE A PROJECT]");
        devLog.populate();

        boolean test1 = devLog.contributeToProject("DOOM", "Carmack");                                                                                  // New Project
        boolean test2 = devLog.contributeToProject("Killer FPS", "Carmack");                                                                            // Existing Project
        boolean test3 = devLog.contributeToProject("", "");                                                                                             // Empty Name Project
        boolean test4 = devLog.contributeToProject("DA", "da");                                                                                         // Less than accepted characters project
        boolean test5 = devLog.contributeToProject("RNA", "DNA");                                                                                       // The least possible name size for a project
        boolean test6 = devLog.contributeToProject("This is a really looooooooooooooooooooooooooooooooooooooooooooooooooooooooong name", "Nobody");     // More than accepted characters
        devLog.setProjectComplete("DOOM");
        boolean test7 = devLog.contributeToProject("DOOM", "Romero");                                                                                   // Update an already completed project

        check("Test 1: Project creation", test1, true);
        check("Test 2: Project update", test2, true);
        check("Test 3: Empty name project", test3, false);
        check("Test 4: Less than 2 characters long name", test4, false);
        check("Test 5: Exactly 3 characters long name", test5, true);
        check("Test 6: Exceeding number of allowed characters", test6, false);
        check("Test 7: Update an already completed project", test7, false);
        System.out.println();
    }

    /**
     * Performs a unit testing for the method removeProject.
     */
    private void testRemoveProject()
    {
        System.out.println("[TEST REMOVE A PROJECT]");
        devLog.populate();

        boolean test1 = devLog.removeProject("Killer FPS");         // Remove an existing project
        boolean test2 = devLog.removeProject("DOOM");               // Remove a project that does not exist.

        check("Test 1: Remove an existing project", test1, true);
        check("Test 2: Remove an existing project", test2, false);
        System.out.println();
    }

    /**
     * Performs a unit testing for the method setProjectComplete.
     */
    private void testCompleteProject()
    {
        System.out.println("[TEST SET A PROJECT AS COMPLETE]");
        devLog.populate();

        boolean test1 = devLog.setProjectComplete("DOOM");          // Set a project that does not exist to complete
        boolean test2 = devLog.setProjectComplete("Killer FPS");    // Set an existing project to complete
        devLog.setProjectComplete("Calculator");
        boolean test3 = devLog.setProjectComplete("Calculator");    // Set an already completed project to complete

        check("Test 1: Set a non-existing project to complete", test1, false);
        check("Test 2: Set an existing project to complete", test2, true);
        check("Test 3: Set a completed project to complete", test3, false);
        System.out.println();
    }

    /**
     * Performs a unit testing for the method getNumberOfTodaysUpdates.The expected number
     * is 8, because there are 8 entries in the method populate of class DevLog.
     */
    private void testTodayContributions()
    {
        System.out.println("[TEST PROJECTS TODAY CONTRIBUTIONS]");
        devLog.populate();
        
        int test1 = devLog.getNumberOfTodaysUpdates();
        check("Test: Check today's total contributions", test1, 8);
        System.out.println();
    }

    /**
     * Performs a unit testing for the method getProjectsOnDate. Tests with the date
     * that the method is run and expects the 3 project that are created in the populate method of DevLog.
     */
    private void testProjectsByDate()
    {
        System.out.println("[TEST GET PROJECTS BY CERTAIN DATE]");
        devLog.populate();

        // Create an arraylist with the expected values.
        String today = applyTimestamp().substring(0,10);
        ArrayList<String> expectedProjects = new ArrayList<>();
        expectedProjects.add("CALCULATOR");
        expectedProjects.add("AI ASSISTANT");
        expectedProjects.add("KILLER FPS");

        ArrayList<String> todayProjectsTest = devLog.getProjectsOnDate(today);

        check("Test: Test today's updated projects: ", todayProjectsTest, expectedProjects);
        System.out.println();
    }

    /**
     * Performs a unit testing for the method saveToCSV. The created file of test1 is
     * created in the same directory as the class DevLog.
     */
    private void testWriteFile() throws IOException
    {
        devLog.populate();

        boolean test1 = devLog.saveToCSV("Test1.csv");
        boolean test2 = devLog.saveToCSV(null);

        System.out.println("[TEST EXTRACT DATA TO A CSV FILE]");
        check("Test 1: Write a file names Test1", test1, true);
        check("Test 2: Write a file with a null argument", test2, false);
        System.out.println();
    }

    /*
     * Used by the testProjectsByDate() method to get the timestamp of the time that the
     * method runs.
     */

    private String applyTimestamp()
    {
        String timestamp = "dd-MM-yyyy, HH:mm:ss";
        DateTimeFormatter currentTimestamp = DateTimeFormatter.ofPattern(timestamp);
        return currentTimestamp.format(LocalDateTime.now());
    }

    /**
     * Overloaded method that checks if the returned value of a test is the same as the expected.
     *
     * @param testName  the name that we have given for a test.
     * @param returnedValue the value that the test returned
     * @param expectedValue the value that is expected by the test.
     */
    private void check(String testName, boolean returnedValue, boolean expectedValue)
    {
        if (returnedValue == expectedValue)
        {
            System.out.println(testName + ": PASS");
        }
        else
        {
            System.out.println(testName + ": FAIL\nExpected value: " + expectedValue + " returned value: " + returnedValue);
        }
    }

    /**
     * Overloaded method that checks if the returned value of a test is the same as the expected.
     *
     * @param testName  the name that we have given for a test.
     * @param returnedValue the value that the test returned
     * @param expectedValue the value that is expected by the test.
     */
    private void check(String testName, int returnedValue, int expectedValue)
    {
        if (returnedValue == expectedValue)
        {
            System.out.println(testName + ": PASS");
        }
        else
        {
            System.out.println(testName + ": FAIL\nExpected value: " + expectedValue + " returned value: " + returnedValue);
        }
    }

    /**
     * Overloaded method that checks if the returned value of a test is the same as the expected.
     *
     * @param testName  the name that we have given for a test.
     * @param returnedValue the value that the test returned
     * @param expectedValue the value that is expected by the test.
     */
    private void check(String testName, ArrayList<String> returnedValue, ArrayList<String> expectedValue)
    {
        if (returnedValue.equals(expectedValue))
        {
            System.out.println(testName + ": PASS");
        }
        else
        {
            System.out.println(testName + ": FAIL\nExpected value: " + expectedValue + "\nReturned value: " + returnedValue);
        }
    }

}
