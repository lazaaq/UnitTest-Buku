import java.util.List;

public class CourseRepository {
    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    private List<Course> courseList;


    public Course findCourse(int id) {
        return courseList.get(id);
    }

    public void setCourseQuota(int id, int total) {
        this.courseList.get(id).setQuota(total);
    }

    public Student findStudent(int studentID) {
        return new Student();
    }

    public void notifyRegisteredStudent(int studentID, int courseID) {
        System.out.println("Hey " + studentID + " you've been registered to course " + courseID);
    }
}
