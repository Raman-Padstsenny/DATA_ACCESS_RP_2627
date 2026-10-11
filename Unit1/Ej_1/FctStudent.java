class FctStudent extends Student {
    private String company;
    private String tutor;
    private String instructor;

    public FctStudent() {
    }

    public FctStudent(String name, int grade, String company, String tutor, String instructor) {
        setName(name);
        setGrade(grade);
        this.company = company;
        this.tutor = tutor;
        this.instructor = instructor;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
}