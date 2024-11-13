package com.dhyan.applicationmenu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.dhyan.data.access.object.CustomerDAO;
import com.dhyan.data.access.object.TaxiDAO;
import com.dhyan.data.access.object.TripDetailsDAO;


public class AdminConsole
{
    static Scanner sc = new Scanner(System.in);

    public AdminConsole()
    {
        // TODO Auto-generated constructor stub
    }

    public void displayAdminConsoleOption() throws SQLException
    {
        boolean adminExit = true;
        TaxiDAO taxi = new TaxiDAO();
        TripDetailsDAO trip = new TripDetailsDAO();
        CustomerDAO customer = new CustomerDAO();
        try
        {
            while (adminExit)
            {

                System.out.println('\n' + "What do u want to see.");
                System.out.println("******************************************");
                System.out.println("1.\"Taxi status update\"" + '\n' + "2.\"View All Taxi Details\" " + '\n' + "3.\"View and Update All Taxi Status\" " + '\n'
                        + "4.\"View TripDetails\" " + '\n' + "5.\"View Customer details\" " + '\n' + "6.\"Exit\" ");
                System.out.println("******************************************");
                try
                {
                    int option = sc.nextInt();
                    switch (option)
                    {
                        case 1:
                            System.out.println("Enter the taxiId and Time");
                            int taxiId = sc.nextInt();
                            taxi.updateTaxiDetails(taxiId, trip);
                            System.out.println("SUCCESSFULLY UPDATE TAXI DETAILS");
                            break;
                        case 2:
                            ResultSet result = taxi.getAllData();
                            taxi.printTaxiDetails(result);
                            break;
                        case 3:
                            taxi.updateAllTaxiDetails(trip);
                            System.out.println("SUCCESSFULLY UPDATE ALL TAXI DETAILS");
                            break;
                        case 4:
                            trip.getAllData();
                            break;
                        case 5:
                            customer.getAllData();
                            System.out.println("Enter the customer id to get the customer trip details");
                            int customerId = sc.nextInt();
                            trip.getCustomerDetail(customerId);
                            break;
                        case 6:
                            adminExit = false;
                            break;
                        default:
                            System.out.println("Enter correct option");
                    }
                }
                catch (Exception e)
                {
                    System.out.println("Enter a correct data");
                    sc.nextLine();
                    displayAdminConsoleOption();
                }
            }

        }
        catch (NullPointerException e)
        {
            System.out.println("Enter a correct data");
            sc.nextLine();
            displayAdminConsoleOption();
        }
        catch (SQLException e)
        {
            System.out.println("Enter a correct data");
            sc.nextLine();
            displayAdminConsoleOption();
        }

    }

    public boolean checkPassWord(String password) throws SQLException
    {
        final String passWord = "Admin@987";
        if (password.equals(passWord))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
