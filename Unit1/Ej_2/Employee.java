public class Employee extends Person {
    private double grossSalary;

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        if (grossSalary < 0) {
            throw new IllegalArgumentException("Invalid salary");
        }
        this.grossSalary = grossSalary;
    }
}
