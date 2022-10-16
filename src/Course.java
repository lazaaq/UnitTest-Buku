public class Course {
    private String name;
    private int id;
    private int fee;

    private int minimumAge;
    private int maximumAge;
    private int quota;

    public Course() {

    }
    public Course(int id, String name, int fee, int quota, int minimumAge, int maximumAge) {
        this.id = id;
        this.fee = fee;
        this.quota = quota;
        this.name = name;
        this.maximumAge = maximumAge;
        this.minimumAge = minimumAge;
    }
    public int getQuota() {
        return quota;
    }
    public void setQuota(int quota) {
        this.quota = quota;
    }
    public int getMinimumAge() {
        return minimumAge;
    }

    public void setMinimumAge(int minimumAge) {
        this.minimumAge = minimumAge;
    }
    public int getMaximumAge() {
        return maximumAge;
    }
    public void setMaximumAge(int maximumAge) {
        this.maximumAge = maximumAge;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
}
