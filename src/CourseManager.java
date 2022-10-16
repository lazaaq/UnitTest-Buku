import java.util.HashMap;
import java.util.List;

public class CourseManager {

    CourseRepository courseRepository;
    public CourseManager(CourseRepository repo) {
        this.courseRepository = repo;
    }

    //Method ini berfungsi untuk mencari tahu apakah seorang student berhak untk mengikuti course tertentu
    //Seorang murid berhak mengikuti kursus apabila kuota masih ada dan umurnya memenuhi ketentuan
    public boolean isAbleToRegisterCourse(Student student, int courseID) {
        Course course = courseRepository.findCourse(courseID);
        return  (student.getAge() >= course.getMinimumAge() && student.getAge() <= course.getMaximumAge() && course.getQuota()> 0);
    }

    // Method ini berfungsi untuk mensimulasikan jumlah total biaya yang diperlukan jika ingin mengikuti beberapa course sekaligus
    // Parameter berupa array dari course id yang ingin diikuti
    public double simulateFees(int[] ids) {
        int totalFee = 0;
        List<Course> courses = courseRepository.getCourseList();
        for (int i = 0; i< ids.length; i++) {
            for (int j = 0; j < courses.size(); j++) {
                Course currentCourse = courses.get(j);
                if (ids[i]==currentCourse.getId()) {
                    totalFee += currentCourse.getFee();
                }
            }
        }
        return totalFee;
    }

    //Method ini berfungsi untuk menghitung cicilan yang harus dibayarkan tiap bulan tergantung dari durasi/ berapa kali cicilan akan dilakukan
    public double installment(Course course, int duration){
        double fee = course.getFee();
        return fee/duration;
    }

    // Method ini berfungsi untuk mengurangi kuota dari sebuah kursus yang terdaftar apabila ada peserta individual mendaftar
// Parameter berupa student id dan course id yang dipilih oleh student
// Apabila kuota pada course masih cukup dan student tidak punya tagihan course, maka kuota course akan dikurangi dan student akan diberikan informasi pemberitahuan
    public void registerStudentOnCourse(int studentID, int courseID) {
        Student student = courseRepository.findStudent(studentID);
        Course course = courseRepository.findCourse(courseID);
        int quota = course.getQuota();

        if (!student.getHasDebt() && quota > 0) {
            courseRepository.notifyRegisteredStudent(studentID,courseID);
            courseRepository.setCourseQuota(course.getId(), quota-1);
        }
    }

    // Method ini berfungsi untuk mengurangi kuota dari sebuah course yang terdaftar apabila ada peserta dalam bentuk grup yang mendaftar
// Parameter berupa hashmap dengan key course id dan value berupa jumlah pendaftar dari course tersebut
// Apabila kuota pada sebuah course masih cukup untuk menampung jumlah pendaftar, maka kuota course akan dikurangi sesuai jumlah pendaftar
    public void registerGroupStudents(HashMap<Integer, Integer> groups) {
        for (int courseID : groups.keySet()) {
            Course course = courseRepository.findCourse(courseID);
            int quota = course.getQuota();
            if (course.getQuota() > groups.get(courseID)) {
                int totalStudentPerCourse = groups.get(courseID);
                courseRepository.setCourseQuota(course.getId(), quota-totalStudentPerCourse);
            }
        }
    }
}
