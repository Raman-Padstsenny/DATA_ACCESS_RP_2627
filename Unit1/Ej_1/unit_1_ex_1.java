import java.util.ArrayList;
import java.util.List;

class Student {
    private String name;
    private int grade;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        if (grade >= 0 && grade <= 10) {
            this.grade = grade;
        }
    }

    public boolean isPassed() {
        return grade >= 5;
    }
}

class Students {
    private List<Student> studentList = new ArrayList<>();

    // Adds a new student to the list
    public void add(Student student) {
        studentList.add(student);
    }

    // Returns the student at position num
    public Student get(int num) {
        if (num >= 0 && num < studentList.size()) {
            return studentList.get(num);
        }
        return null;
    }

    // Returns the average grade of the students
    public float getAverage() {
        if (studentList.isEmpty()) {
            return 0;
        }

        float average = 0;

        for (Student student : studentList) {
            average += student.getGrade();
        }

        return average / studentList.size();
    }
}
