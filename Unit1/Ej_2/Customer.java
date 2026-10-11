import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Customer extends Person {
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^((\\+|00)\\d{2,3})?\\d{9}$");

    private String phone;
    private final Set<Company> companies = new HashSet<>();

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone == null || !PHONE_PATTERN.matcher(phone).matches()) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        this.phone = phone;
    }

    public Set<Company> getCompanies() {
        return Collections.unmodifiableSet(companies);
    }

    void addCompany(Company company) {
        companies.add(company);
    }

    void removeCompany(Company company) {
        companies.remove(company);
    }
}
