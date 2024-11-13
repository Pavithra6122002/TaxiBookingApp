package com.dhyan.applicationmenu;

import java.io.File;
import java.io.IOException;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import com.dhyan.data.access.object.TaxiDAO;
import com.dhyan.data.access.object.TripDetailsDAO;
import com.dhyan.model.Taxi;
import com.dhyan.model.TaxiDetails;
import com.dhyan.model.TripDetails;


import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

public class TaxiApplication
{
    private static Scanner sc = new Scanner(System.in);

    public static void main(String args[])
    {

        try
        {
            TaxiDAO taxiDao = new TaxiDAO();
            // populateData(taxidetails,taxiDao);
            taxiDao.updateInitialDetails();
            System.out.println("Welcome to Taxi Booking" + '\n' + "Press enter to book taxi");
            sc.nextLine();

            TaxiApplication taxiapp = new TaxiApplication();
            taxiapp.displayApplicationMenu();
        }
        catch (SQLException e)
        {
            System.out.println("Error in Sql query");
        }
        catch (IOException e)
        {
            System.out.println("Enter correct data");
        }

    }

    public int addTripdetails(int customerId, String source, String destination, int taxiId, String duration, String bookingTime)
            throws SQLException
    {
        TripDetailsDAO tripdetails=new TripDetailsDAO();
        Random rand = new Random();
        TripDetails trip = new TripDetails();
        int tripId = rand.nextInt(1000);
        System.out.println("Your OTP: " + tripId);
        trip.setTripId(tripId);
        trip.setCustomerId(customerId);
        trip.setSource(source);
        trip.setDestination(destination);
        trip.setStartTime(bookingTime);
        trip.setEndTime(duration);
        trip.setTaxiId(taxiId);
        tripdetails.addData(trip);
        return tripId;

    }

    public void displayApplicationMenu()
    {
        boolean appExit = true;
        UserConsole user = new UserConsole();
        while (appExit)
        {
            try
            {
                System.out.println("*****************");
                System.out.println("|User press \"1\" |" + '\n' + "|Admin press \"2\"|" + '\n' + "|Exit press \"3\" |");
                System.out.println("*****************");

                int option = sc.nextInt();
                sc.nextLine();
                switch (option)
                {
                    case 1:
                        System.out.println("Welcome to User Console");
                        System.out.println("1.Go Back" + '\n' + "2.Continue");
                        int backOption = sc.nextInt();
                        if (backOption == 1)
                        {
                            break;
                        }
                        user.bookTrip();

                        break;

                    case 2:
                        System.out.println("Welcome to Admin Console");
                        AdminConsole admin = new AdminConsole();
                        System.out.println("1.Go Back" + '\n' + "2.Continue");
                        int adminBackOption = sc.nextInt();
                        sc.nextLine();
                        boolean checkPassword = false;
                        while (!checkPassword)
                        {
                            if (adminBackOption == 1)
                            {

                                break;
                            }
                            else
                            {
                                System.out.println("Enter Admin PassWord");
                                String password = sc.nextLine();

                                checkPassword = admin.checkPassWord(password);
                            }
                            if (checkPassword == false)
                            {
                                System.out.println("1.Retry" + '\n' + "2.Exit");
                                int passwordExit = sc.nextInt();
                                if (passwordExit == 2)
                                    break;
                            }
                        }
                        if (checkPassword == true)
                        {
                            admin.displayAdminConsoleOption();
                        }

                        break;

                    case 3:
                        System.out.println("Did you want to Exit");
                        System.out.println("1.YES" + '\n' + "2.NO");
                        int exitBackOption = sc.nextInt();
                        sc.nextLine();
                        if (exitBackOption == 2)
                        {
                            break;
                        }
                        appExit = false;
                        TripDetailsDAO trip = new TripDetailsDAO();
                        trip.updateTripStatus();
                        break;
                    default:
                        System.out.println("Enter correct option");
                }
            }
            catch (InputMismatchException e)
            {
                System.out.println("Enter a correct data");
                sc.next();

            }
            catch (SQLException e)
            {
                System.out.println("Enter a correct data");
                sc.next();

            }

        }

    }

    public TaxiDetails convertXmlToObject(TaxiDetails taxi)
    {
        try
        {
            File file = new File("InputData.xml");
            JAXBContext contextObj = JAXBContext.newInstance(TaxiDetails.class);
            Unmarshaller unmarshallerObj = contextObj.createUnmarshaller();
            taxi = (TaxiDetails) unmarshallerObj.unmarshal(file);
        }
        catch (Exception e)
        {
            System.out.println(e);

        }
        return taxi;
    }

    public void populateData(TaxiDetails taxidetails, TaxiDAO taxiDao)
    {
        taxidetails = convertXmlToObject(taxidetails);
        for (Taxi taxi : taxidetails.taxiList)
        {
            taxiDao.addTaxi(taxi);
        }

    }

}
