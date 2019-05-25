// RAISAAT ATIFA RASHID
// Net ID: rar150430
// CS 2336.003 - Project #2

import Customer.*;
import java.io.*;
import java.util.*;
import java.math.*;

public class Main 
{   
    public static void main (String []args) throws IOException
    {
        // Create a file object for customers.dat file
        File inFile = new File("customer.dat");
         
        // Create a Scanner to read customers.dat file's data
        Scanner readFile = new Scanner(inFile);
        
        // Find the total number of lines in customers.dat file
        LineNumberReader reader = new LineNumberReader(new FileReader(inFile));
        while ((reader.readLine()) != null);
        
        // Create a Customer type array with a size equal to the number of lines in customers.dat file
        Customer[] customerArr = new Customer[reader.getLineNumber()];
        
        // Set each element of customerArr array to reference to a Customer type object
        for (int i = 0; i < customerArr.length; i++)
            customerArr[i] = new Customer();
        
        // Read data from customers.dat file to customerArr array, while the file has a next line
        for (int i = 0; readFile.hasNext(); i++)
        {
            customerArr[i].setGuestID(readFile.nextInt());      // Read the customer ID
            customerArr[i].setFirstName(readFile.next());       // Read the customer's first name
            customerArr[i].setLastName(readFile.next());        // Read the customer's last name
            customerArr[i].setAmount(readFile.nextDouble());    // Read the amount spent by the customer
        }
        // Close customers.dat
        readFile.close();
        
        // Create a reference for a PreferredCustomer type array and set it to null
        PreferredCustomer[] preferredArr = null;
        
        // Create a file object for preferred.dat file
        inFile = new File("preferred.dat");
        
        // If preferred.dat file exists
        if (inFile.exists())
        {
            // Create a Scanner to read preferred.dat file's data
            readFile = new Scanner(inFile);
            
            // Find the total number of lines in preferred.dat file
            reader = new LineNumberReader(new FileReader(inFile));
            while ((reader.readLine()) != null);
        
            // Set a variable equal to the total number of lines in preferred.dat
            int preferredSize = reader.getLineNumber();
            
            // If the total number of lines in preferred.dat is not equal to zero
            if (preferredSize != 0)
            {
                // Create a new PreferredCustomer type array and set preferredArr to point to it
                preferredArr = new PreferredCustomer[preferredSize];
        
                // Set each element of preferredArr array to reference to a new PreferredCustomer type object
                for (int i = 0; i < preferredSize; i++)
                    preferredArr[i] = new PreferredCustomer();
        
                // Read data from preferred.dat file to preferredArr array, while the file has a next line
                for (int i = 0; readFile.hasNext(); i++)
                {
                    preferredArr[i].setGuestID(readFile.nextInt()); // Read the customer ID
                    preferredArr[i].setFirstName(readFile.next());  // Read the customer's first name
                    preferredArr[i].setLastName(readFile.next());   // Read the customer's last name
                    preferredArr[i].setAmount(readFile.nextDouble());   // Read the amount spent by the customer
                    preferredArr[i].setDiscount(Integer.parseInt(readFile.next().replaceAll("%", ""))); // Read the discount
                }
            }
            // Close preferred.dat
            readFile.close();
        }
      
        // Create a Scanner to read orders.dat file's data
        readFile = new Scanner(new File("orders.dat"));
        
        // While orders.dat has a next line
        while (readFile.hasNext())
        {
            int ID = readFile.nextInt();                        // Read the customer's ID
            double radius = readFile.nextDouble();              // Read the radius of the container
            double height = readFile.nextDouble();              // Read the height of the container
            double ounces = readFile.nextDouble();              // Read the no. of ounces
            double ouncePrice = readFile.nextDouble();          // Read the price per ounce
            double squareInchPrice = readFile.nextDouble();     // Read the price per square inch
            int quantity = readFile.nextInt();                  // Read the quantity
            
            // Calculate the total price
            double totalPrice = ((2 * Math.PI * radius * height + 2 * Math.PI * radius * radius) * squareInchPrice + ounces * ouncePrice) * quantity;
            
            // Set found to false
            boolean found = false;
            
            // Look for the customer in customerArr array
            for (int i = 0; i < customerArr.length; i++)
            {
                // If the customer is found in customerArr array
                if(customerArr[i].getGuestID() == ID)
                {
                    // Increment the customer's total amount spent by the total price
                    customerArr[i].setAmount(customerArr[i].getAmount() + totalPrice);
                    
                    // If the total amount spent is greater than or equal to $150
                    if (customerArr[i].getAmount() >= 150)
                    {
                        // Update preferredArr array
                        preferredArr = updatePreferred(i, customerArr, preferredArr);
                        // Update customerArr array
                        customerArr = updateCustomer(i, customerArr);
                    }
                    // Set found to true
                    found = true;
                    
                    // Break from the for loop
                    break;
                }
            }
            
            // If the customer was not found in customerArr array and preferredArr does not point to null
            if (!found && preferredArr != null)
            {
                // Look for the customer in preferredArr array
                for (int i = 0; i < preferredArr.length; i++)
                {
                    // If the customer is found in preferredArr array
                    if (preferredArr[i].getGuestID() == ID)
                    {
                       // Increment the customer's total amount spent by the total price with the appropriate discount applied
                       preferredArr[i].setAmount(preferredArr[i].getAmount() + totalPrice * (1 - preferredArr[i].getDiscount() / 100.0));
                       
                       // If the total amount spent is greater than or equal to $350, set the customer's discount to 10
                       if (preferredArr[i].getAmount() >= 350)
                           preferredArr[i].setDiscount(10);
                       
                       // Else, set the discount to 7
                       else if (preferredArr[i].getAmount() >= 200)
                           preferredArr[i].setDiscount(7);
                       
                       // break from the for loop
                       break;
                    }
                }
            }
        }
        // Close orders.dat
        readFile.close();
        
        // Create a PrintWriter object to write data to customers.dat
        PrintWriter writeFile = new PrintWriter("customer.dat");
        
        // Write customerArr array's contents back to customers.dat
        for (int i = 0; i < customerArr.length; i++)
        {
            writeFile.print(customerArr[i].getGuestID() + " " + customerArr[i].getFirstName() + " " + customerArr[i].getLastName());
            writeFile.printf(" %.2f\n", customerArr[i].getAmount());
        }
        // Close customer.dat
        writeFile.close();
        
        // If preferredArr array exists
        if (preferredArr != null)
        {
            // Create a PrintWriter object to write data to preferred.dat
            writeFile = new PrintWriter("preferred.dat");
        
            // Write preferredArr array's contents back to preferred.dat
            for (int i = 0; i < preferredArr.length; i++)
            {
                writeFile.print (preferredArr[i].getGuestID() + " " + preferredArr[i].getFirstName() + " " + preferredArr[i].getLastName());
                writeFile.printf(" %.2f %d%%\n", preferredArr[i].getAmount(), preferredArr[i].getDiscount());
            }
            // Close preferred.dat
            writeFile.close();
        }
    }
    
    // This method updates the customer array
    public static Customer[] updateCustomer(int i, Customer[] arr)
    {
        // Create a new Customer type array with a size equal to the parameter
        // Customer type array's size - 1
        Customer[] newArr = new Customer[arr.length - 1];
        
        // Assign the size of the new Customer type array to a variable
        int size = newArr.length;
        
        // Set each element of the new Customer type array to reference to a new Customer type object
        for (int count = 0; count < size; count++)
            newArr[count] = new Customer();
        
        // Copy the parameter Customer type array's elements, except the element specified by i, to
        // the new Customer type array
        for(int j = 0, k = 0; j < size; j++, k++)
        {
            if(j == i)
                k++;
            newArr[j].setFirstName(arr[k].getFirstName());
            newArr[j].setLastName(arr[k].getLastName());
            newArr[j].setGuestID(arr[k].getGuestID());
            newArr[j].setAmount(arr[k].getAmount());
        }
        // Return the new Customer type array
        return newArr;
    }
    
    // This method updates the preferred customer array
    public static PreferredCustomer[] updatePreferred(int i, Customer[] customerArr, PreferredCustomer[] preferred)
    {
        // Create a new PreferredCustomer[] type reference variable and set it to point to null
        PreferredCustomer[] newPreferred = null;
        
        // If the parameter PreferedCustomer[] type variable points to null, set the new PreferredCustomer[]
        // type variable to point to a new PreferredCustomer type array with a size equal to 1
        if (preferred == null)
            newPreferred = new PreferredCustomer[1];
        
        // Else, set the new PreferredCustomer[] type variable to point to a new
        // PreferredCustomer type array with a size equal to parameter PreferredCustomer type array's size + 1
        else
            newPreferred = new PreferredCustomer[preferred.length + 1];
        
        // Assign the size of the new PreferredCustomer type array's size to a variable
        int size = newPreferred.length;
        
        // Set each element of the new PreferredCustomer type array to reference to a new PreferredCustomer type object
        for(int count = 0; count < size; count++)
            newPreferred[count] = new PreferredCustomer();
        
        // Copy the parameter PreferredCustomer type array's elements to the new one
        for(int j = 0; j < size - 1; j++)
        {
            newPreferred[j].setFirstName(preferred[j].getFirstName());
            newPreferred[j].setLastName(preferred[j].getLastName());
            newPreferred[j].setGuestID(preferred[j].getGuestID());
            newPreferred[j].setAmount(preferred[j].getAmount());
            newPreferred[j].setDiscount(preferred[j].getDiscount());
        }
        
        // Copy the contents of the element of the parameter Customer type array, specified
        // by i, to the last element of the new PreferredCustomer type array 
        newPreferred[size - 1].setFirstName(customerArr[i].getFirstName());
        newPreferred[size - 1].setLastName(customerArr[i].getLastName());
        newPreferred[size - 1].setGuestID(customerArr[i].getGuestID());
        newPreferred[size - 1].setAmount(customerArr[i].getAmount());
        
        // If the amount spent by the customer is greater than or equal
        // to $350, set the discount to 10
        if (customerArr[i].getAmount() >= 350)
            newPreferred[size - 1].setDiscount(10);
        
        // else if, the amount spent by the customer is greater than or equal
        // to $200, set the discount to 7
        else if (customerArr[i].getAmount() >= 200)
            newPreferred[size - 1].setDiscount(7);
        
        // Else set the discount to 5
        else
            newPreferred[size - 1].setDiscount(5);
        
        // Return the new PreferredCustomer type array
        return newPreferred;
    }
}