public class Main
{
    public static void main(String[] args)
    {

        Project p1 = new Project("Test");
        Project p2 = new Project("Bread maker");
        DevLog d1 = new DevLog(p1, "Michael");
        DevLog d2 = new DevLog(p2, "Steve");
        d1.show();
        d2.show();

    }
}
