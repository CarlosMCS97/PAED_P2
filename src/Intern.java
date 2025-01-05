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

    public String getInternDetails() {
        return String.format("%s;%s;%.1f;%b", getName(), getSubject(), getAverage(), isJunior());
    }

    public double calculateSkillPoints(Task task) {
        double skillPoints = average * 10;

        if (average >= 9) {
            skillPoints *= 1.5;
        }
        else if (average >= 7 ) {
            skillPoints *= 1.2;
        }

        if (!junior) {
            skillPoints += 10;
        }

        if (subject.equals(task.getSubject())) {
            skillPoints += 20;
        }

        return skillPoints;
    }

    public double calculateTime(Task task) {
        double skillPoints = calculateSkillPoints(task);
        return (task.getTime() / skillPoints) * 80;
    }


}
