package com.dhyan.applicationmenu;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.dhyan.data.access.object.CustomerDAO;
import com.dhyan.data.access.object.TaxiDAO;
import com.dhyan.data.access.object.TaxiService;
import com.dhyan.data.access.object.TripDetailsDAO;
import com.dhyan.model.Customer;
import com.dhyan.model.Taxi;
import com.dhyan.model.TripDetails;
import com.dhyan.util.DatabaseConnection;

public class UserConsole
{

    static Scanner sc = new Scanner(System.in);

    public void bookTrip()
    {
        TaxiApplication taxiapp = new TaxiApplication();
        CustomerDAO customerDao = new CustomerDAO();
        TripDetailsDAO tripdetails = new TripDetailsDAO();
        TaxiService taxiservice = new TaxiService();
        DecimalFormat decimalformat = new DecimalFormat("0.00");
        try
        {

            int userOption = getUserLogin();
            Customer customer = getCustomerData(customerDao, userOption);
            TripDetails trip = getTripData();

            System.out.println("Booking Time :");
            String bookingTime = sc.nextLine();

            Taxi taxiObject = taxiservice.findTaxi(bookingTime, trip, taxiservice);
            if (taxiObject != null)
            {
                int taxiId = taxiObject.getTaxiId();
                LocalTime waitingTime = taxiservice.getTiming();

                LocalTime startTime = LocalTime.parse(bookingTime).plusMinutes(waitingTime.getMinute());
                long durationTime = taxiservice.getDuration();
                System.out.println("StartTime : " + startTime);
                System.out.println("Duration : " + durationTime + " minutes");
                LocalTime endTime = startTime.plusMinutes(durationTime);
                System.out.println("Waiting Time : " + waitingTime + " minutes");
                System.out.println("Taxi Id : " + taxiId);

                int tripId =
                        taxiapp.addTripdetails(customer.getcustomerId(), trip.getSource(), trip.getDestination(), taxiId, ("" + endTime), ("" + startTime));
                System.out.println("1.BOOK" + '\n' + "2.CANCEL");
                int bookOption = sc.nextInt();
                if (bookOption == 1)
                {
                    TaxiDAO taxi = new TaxiDAO();
                    if (userOption == 1)
                    {
                        CustomerDAO cust=new CustomerDAO();
                        cust.addData(customer);
                        
                    }
                    taxi.updateAfterBooking(taxiId, ("" + endTime), trip.getDestination());
                    System.out.println("BOOK SUCCESSFULLY");

                }
                else
                {
                    System.out.println("BOOKING CANCEL");
                    tripdetails.updateTripStatusCancel(tripId);
                }
            }
            else
            {
                System.out.println("BOOKING CANCEL");
            }

        }
        catch (SQLException e)
        {
            System.out.println(e);
            sc.nextLine();
            taxiapp.displayApplicationMenu();

        }
        catch (InputMismatchException e)
        {
            System.out.println("Enter a correct data");
            sc.next();

        }
    }

    public void displayRoute() throws SQLException
    {
        System.out.println("TAXI ROUTES");
        System.out.println("-----------");
        Statement statement;
        ResultSet resultSet;
        Connection connection = DatabaseConnection.getConnection();
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM routTable");
        while (resultSet.next())
        {
            System.out.println(resultSet.getString(1) + " -> " + resultSet.getString(2));
        }
        statement.close();
        resultSet.close();
    }

    public String validateRoute(String route)
    {
        String[] routes = new String[]
        {"A", "B", "C", "D", "E" };
        int checkEqual = 0;
        int check = 0;
        while (check == 0)
        {
            for (String iterator : routes)
            {
                if (iterator.equals(route.toUpperCase()))
                {
                    checkEqual++;
                    check++;
                }
            }
            if (checkEqual == 0)
            {
                System.out.println("Enter a correct route");
                System.out.println("Routes are \" A , B , C , D , E \"");
                route = sc.nextLine();
            }
        }
        return route;
    }

    public Customer getCustomerData(CustomerDAO customerDao, int userOption) throws SQLException
    {
        Customer customer = new Customer();
        System.out.println("Customer Id :");
        int customerId = sc.nextInt();
        if (userOption == 1)
        {
            customerId = customerDao.validateNewUser(customerId);
        }
        else
        {
            customerId = customerDao.validateOldUser(customerId);
        }
        sc.nextLine();
        customer.setUserId(customerId);
        System.out.println("Customer Name :");
        String name = sc.nextLine();
        customer.setName(name);
        return customer;
    }

    public TripDetails getTripData()
    {
        TripDetails trip = new TripDetails();
        System.out.println("Source :");
        String source = sc.nextLine();
        source = validateRoute(source);
        trip.setSource(source);
        System.out.println("Destintion :");
        String destination = sc.nextLine();
        destination = validateRoute(destination);
        trip.setDestination(destination);
        return trip;
    }

    public int getUserLogin() throws SQLException
    {
        System.out.println("1.New User" + '\n' + "2.Old User");
        int userOption = sc.nextInt();
        System.out.println("Did you want Route Details" + '\n' + "1.Yes" + '\n' + "2.No");
        int routeOption = sc.nextInt();
        if (routeOption == 1)
        {
            displayRoute();
        }
        return userOption;
    }

}
