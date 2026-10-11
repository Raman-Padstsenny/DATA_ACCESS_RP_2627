import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Company {
    private String name;
    private final Set<Employee> employees = new HashSet<>();
    private final Set<Customer> customers = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getEmployees() {
        return Collections.unmodifiableSet(employees);
    }

    public Set<Customer> getCustomers() {
        return Collections.unmodifiableSet(customers);
    }

    public int getTotalCustomers() {
        return customers.size();
    }

    public int getTotalEmployees() {
        return employees.size();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
        customer.addCompany(this);
    }

    public void removeCustomer(Customer customer) {
        customers.remove(customer);
        customer.removeCompany(this);
    }
}
