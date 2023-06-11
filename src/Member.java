
import java.util.ArrayList;

/**
 *
 * @author bcelikar
 */
public class Member {

    private final ArrayList<Account> accounts;
    private final String ID;
    private final String name;
    private final String pinNumber;

    public Member(String ID,String pin, String name) {
        this.ID = ID;
        this.pinNumber = pin;
        this.name = name;
        accounts = new ArrayList<>();
    }
        // Getter for ID
    public String getID() {
        return ID;
    }
    public String getPin(){
        return pinNumber;
    }
    public void addAccounts(Account acc){
        accounts.add(acc);
    }
    public ArrayList getAccounts(){
        return accounts;
    }

    // Getter for Name
    public String getName() {
        return name;
    }

    public double getBalance(Account account){
        return account.getBalance();
    }
    public void setBalance(Account account,double amount){
        account.setBalance(amount);
    }

}
