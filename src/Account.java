public class Account {

    private double balance;
    private String description;
    private final int number;
    private String type;

    // Constructor

    public Account(int number) {
        this.number = number;
        balance = 0.00;
        description = "";
        type = "";
    }
    
    public void setDescription(String des){
        description = des;
    }
    
    public String getDescription(){
        return description;
    }
    public void setType(String accType){
        type = accType;
    }
    public String getType(){
        return type;
    }

    // Method to add to balance
    public void deposit(double amount) {
        balance += amount;
    }

    // Method to subtract from balance
    public void withdraw(double amount) {
        balance -= amount;
    }
    public int getNumber(){
        return number;
    }

    // Getter for balance
    public double getBalance() {
        return balance;
    }

    // Setter for balance
    public void setBalance(double amount) {
        balance += amount;
    }

}