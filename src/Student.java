import java.util.List;

public class Student {
    public Student() {

    }
    public Student(int id, String name, int age, Boolean hasDebt) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hasDebt = hasDebt;
    }
    private int id;
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private int age;

    public Boolean getHasDebt() {
        return hasDebt;
    }

    public void setHasDebt(Boolean hasDebt) {
        this.hasDebt = hasDebt;
    }

    private Boolean hasDebt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
