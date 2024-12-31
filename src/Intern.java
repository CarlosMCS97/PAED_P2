public class Intern {

    private String name;
    private String subject;
    private float average;
    private boolean junior;

    public Intern(String name, String subject, float average, boolean junior) {
        this.name = name;
        this.subject = subject;
        this.average = average;
        this.junior = junior;
    }


    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public float getAverage() {
        return average;
    }

    public boolean isJunior() {
        return junior;
    }


}
