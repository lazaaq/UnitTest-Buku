import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CourseManagerTest {

    @Test
    public void isAbleToRegisterCourse1() {
        CourseRepository repository = mock(CourseRepository.class);
        CourseManager manager = new CourseManager(repository);

        Student student = new Student();
        student.setAge(19);
        Course course = new Course();
        course.setId(1);
        course.setQuota(1);
        course.setMinimumAge(18);
        course.setMaximumAge(20);

        when(repository.findCourse(1)).thenReturn(course);
        Assert.assertEquals(true, manager.isAbleToRegisterCourse(student, 1));
    }

    @Test
    public void isAbleToRegisterCourse2() {
        CourseRepository repository = mock(CourseRepository.class);
        CourseManager manager = new CourseManager(repository);

        Student student = new Student();
        student.setAge(19);
        Course course = new Course();
        course.setId(1);
        course.setQuota(0);
        course.setMinimumAge(18);
        course.setMaximumAge(20);

        when(repository.findCourse(1)).thenReturn(course);
        Assert.assertEquals(false, manager.isAbleToRegisterCourse(student, 1));
    }

    @Test
    public void isAbleToRegisterCourse3() {
        CourseRepository repository = mock(CourseRepository.class);
        CourseManager manager = new CourseManager(repository);

        Student student = new Student();
        student.setAge(19);
        Course course = new Course();
        course.setId(1);
        course.setQuota(1);
        course.setMinimumAge(20);
        course.setMaximumAge(25);

        when(repository.findCourse(1)).thenReturn(course);
        Assert.assertEquals(false, manager.isAbleToRegisterCourse(student, 1));
    }

    @Test
    public void isAbleToRegisterCourse4() {
        CourseRepository repository = mock(CourseRepository.class);
        CourseManager manager = new CourseManager(repository);

        Student student = new Student();
        student.setAge(19);
        Course course = new Course();
        course.setId(1);
        course.setQuota(0);
        course.setMinimumAge(20);
        course.setMaximumAge(25);

        when(repository.findCourse(1)).thenReturn(course);
        Assert.assertEquals(false, manager.isAbleToRegisterCourse(student, 1));
    }

    @Test
    public void simulateFees() {
        CourseRepository repository = mock(CourseRepository.class);
        CourseManager manager = new CourseManager(repository);

        Course course1 = new Course(1, "PPPL", 100000, 12, 18, 25);
        Course course2 = new Course(2, "Verval", 150000, 12, 18, 25);
        Course course3 = new Course(3, "Kapsel", 70000, 12, 18, 25);

        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);

        when(repository.getCourseList()).thenReturn(courses);
        int[] ids = {1,2,3};
        double expected = 320000;
        Assert.assertEquals(expected, manager.simulateFees(ids), 0);
    }

    @Test
    public void installment() {
        CourseRepository repository = mock(CourseRepository.class);
        CourseManager manager = new CourseManager(repository);

        Course course = new Course(1, "PPPL", 1000000, 12, 18, 25);
        Assert.assertEquals(100000, manager.installment(course, 10), 0);
    }

    @Test
    public void registerStudentOnCourse1() {
        CourseRepository repository = mock(CourseRepository.class);
        CourseManager manager = new CourseManager(repository);

        Student student = new Student(1, "Lana", 19, Boolean.FALSE);
        Course course = new Course(1, "PPPL", 100000, 12, 18, 25);

        when(repository.findStudent(1)).thenReturn(student);
        when(repository.findCourse(1)).thenReturn(course);
        manager.registerStudentOnCourse(1, 1);
        verify(repository).notifyRegisteredStudent(1, 1);
        verify(repository).setCourseQuota(1, 11);
    }

    @Test
    public void registerStudentOnCourse2() {
        CourseRepository repository = mock(CourseRepository.class);
        CourseManager manager = new CourseManager(repository);

        Student student = new Student(1, "Lana", 19, Boolean.FALSE);
        Course course = new Course(1, "PPPL", 100000, 0, 18, 25);

        when(repository.findStudent(1)).thenReturn(student);
        when(repository.findCourse(1)).thenReturn(course);
        manager.registerStudentOnCourse(1, 1);
        verify(repository, never()).notifyRegisteredStudent(1, 1);
        verify(repository, never()).setCourseQuota(1, 11);
    }

    @Test
    public void registerStudentOnCourse3() {
        CourseRepository repository = mock(CourseRepository.class);
        CourseManager manager = new CourseManager(repository);

        Student student = new Student(1, "Lana", 19, Boolean.TRUE);
        Course course = new Course(1, "PPPL", 100000, 12, 18, 25);

        when(repository.findStudent(1)).thenReturn(student);
        when(repository.findCourse(1)).thenReturn(course);
        manager.registerStudentOnCourse(1, 1);
        verify(repository, never()).notifyRegisteredStudent(1, 1);
        verify(repository, never()).setCourseQuota(1, 11);
    }

    @Test
    public void registerStudentOnCourse4() {
        CourseRepository repository = mock(CourseRepository.class);
        CourseManager manager = new CourseManager(repository);

        Student student = new Student(1, "Lana", 19, Boolean.TRUE);
        Course course = new Course(1, "PPPL", 100000, 0, 18, 25);

        when(repository.findStudent(1)).thenReturn(student);
        when(repository.findCourse(1)).thenReturn(course);
        manager.registerStudentOnCourse(1, 1);
        verify(repository, never()).notifyRegisteredStudent(1, 1);
        verify(repository, never()).setCourseQuota(1, 11);
    }

    @Test
    public void registerGroupStudents() {
        CourseRepository repository = mock(CourseRepository.class);
        CourseManager manager = new CourseManager(repository);

        Course course1 = new Course(1, "PPPL", 100000, 12, 18, 25);
        Course course2 = new Course(2, "Verval", 150000, 12, 18, 25);
        Course course3 = new Course(3, "Kapsel", 70000, 12, 18, 25);

        HashMap<Integer, Integer> groups = new HashMap<Integer, Integer>();
        groups.put(1, 5);
        groups.put(2, 10);
        groups.put(3, 15);

        when(repository.findCourse(1)).thenReturn(course1);
        when(repository.findCourse(2)).thenReturn(course2);
        when(repository.findCourse(3)).thenReturn(course3);
        manager.registerGroupStudents(groups);
        verify(repository).setCourseQuota(1, 7);
        verify(repository).setCourseQuota(2, 2);
        verify(repository, never()).setCourseQuota(3, 0);
    }
}