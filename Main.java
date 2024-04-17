public class Main
{
    public static void main(String[] args)
    {

        Project p1 = new Project("Test", "Steve");
        Project p2 = new Project("Bread maker", "John");
        DevLog d1 = new DevLog(p1);
        DevLog d2 = new DevLog(p2);
        d1.show();
        d2.show();

    }
}
