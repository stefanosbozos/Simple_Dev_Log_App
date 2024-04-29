public class TestDevLog
{
    DevLog devLog;

    public TestDevLog()
    {
        devLog = new DevLog();
    }

    public void testRemoveProject()
    {
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
                case 0,1,4,5:
                    System.out.println("Test " + (i + 1) + " : " + (testRemoveCase[i] ? "PASS" : "FAIL expected \'true\' got " + testRemoveCase[i]));
                    break;
                case 2,3,6:
                    System.out.println("Test " + (i + 1) + " : " + (testRemoveCase[i] ? "FAIL expected \'false\' got " + testRemoveCase[i] : "PASS"));
                    break;
                default:
                    break;

            }
        }
        devLog.populate();
    }

    public void testUpdateProject()
    {
        devLog.updateProject("Killer FPS", "Carmack");
        devLog.updateProject("DOOM", "Romero");
        devLog.updateProject("", "McGee");


    }
}
