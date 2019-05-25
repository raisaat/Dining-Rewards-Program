// RAISAAT ATIFA RASHID
// Net ID: rar150430
// CS 2336.003 - Project #2

package Customer;

public class Customer 
{
    // Data fields
    protected String firstName, lastName;
    protected int guestID;
    protected double amount;
    
    // default constructor
    public Customer () {}
    
    // Overloaded constructor
    public Customer (String firstName, String lastName, int guestID, double amount)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.guestID = guestID;
        this.amount = amount;
    }
    
    // Accessors
    public String getFirstName ()
    {
        return firstName;
    }
    
    public String getLastName ()
    {
        return lastName;
    }
    
    public int getGuestID ()
    {
        return guestID;
    }
    
    public double getAmount ()
    {
        return amount;
    }
    
    // Mutators
    public void setFirstName (String firstName)
    {
        this.firstName = firstName;
    }
    
    public void setLastName (String lastName)
    {
        this.lastName = lastName;
    }
    
    public void setGuestID (int guestID)
    {
        this.guestID = guestID;
    }
    
    public void setAmount (double amount)
    {
        this.amount = amount;
    }
}