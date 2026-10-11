import java.time.LocalDate;

class ErasmusStudent extends Student {
    private LocalDate internshipStartDate;
    private LocalDate internshipEndDate;
    private String originCountry;

    public ErasmusStudent() {
    }

    public ErasmusStudent(String name, int grade,
                          LocalDate internshipStartDate,
                          LocalDate internshipEndDate,
                          String originCountry) {
        setName(name);
        setGrade(grade);
        this.internshipStartDate = internshipStartDate;
        this.internshipEndDate = internshipEndDate;
        this.originCountry = originCountry;
    }

    public LocalDate getInternshipStartDate() {
        return internshipStartDate;
    }

    public void setInternshipStartDate(LocalDate internshipStartDate) {
        this.internshipStartDate = internshipStartDate;
    }

    public LocalDate getInternshipEndDate() {
        return internshipEndDate;
    }

    public void setInternshipEndDate(LocalDate internshipEndDate) {
        this.internshipEndDate = internshipEndDate;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }
}