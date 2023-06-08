public class Account {

    private double balance;
    private String ID;
    private String name;

    // Constructor

    public Account(String ID, String name) {
        balance = 0.00;
        this.ID = ID;
        this.name = name;
    }

    // Method to add to balance
    public void deposit(double amount) {
        balance += amount;
    }

    // Method to subtract from balance
    public void withdraw(double amount) {
        balance -= amount;
    }

    // Getter for balance
    public double getBalance() {
        String.format("%.2f", balance);
        return balance;
    }

    // Setter for balance
    public void setBalance(double amount) {
        balance += amount;
    }

    // Getter for ID
    public String getID() {
        return ID;
    }

    // Getter for Name
    public String getName() {
        return name;
    }
}