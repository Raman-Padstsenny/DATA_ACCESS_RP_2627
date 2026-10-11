import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Manager extends Employee {
    private String category;
    private final Set<Employee> subordinates = new HashSet<>();

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<Employee> getSubordinates() {
        return Collections.unmodifiableSet(subordinates);
    }

    public int getSubordinateCount() {
        return subordinates.size();
    }

    public void addSubordinate(Employee employee) {
        subordinates.add(employee);
    }

    public void removeSubordinate(Employee employee) {
        subordinates.remove(employee);
    }
}
