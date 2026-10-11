import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Person {
    private String name;
    private LocalDate birthDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Invalid birth date");
        }
        this.birthDate = birthDate;
    }

    public int getAge() {
        return (int) (ChronoUnit.DAYS.between(birthDate, LocalDate.now()) / 365);
    }
}
