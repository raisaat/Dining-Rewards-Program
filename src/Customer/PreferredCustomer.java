// RAISAAT ATIFA RASHID
// Net ID: rar150430
// CS 2336.003 - Project #2

package Customer;

public class PreferredCustomer extends Customer
{
    // Data field
    private int discount;
    
    // default constructor
    public PreferredCustomer(){}
    
    // Overloaded constructor
    public PreferredCustomer (String firstName, String lastName, int guestID, double amount, int discount)
    {
        super(firstName, lastName, guestID, amount);
        this.discount = discount;
    }
    
    // Accessor
    public int getDiscount()
    {
        return discount;
    }
    
    // Mutator
    public void setDiscount(int discount)
    {
        this.discount = discount;
    }
}