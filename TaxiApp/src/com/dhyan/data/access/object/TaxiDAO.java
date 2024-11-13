package com.dhyan.data.access.object;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dhyan.model.Taxi;
import com.dhyan.util.DatabaseConnection;

public class TaxiDAO
{
    private Connection connection;

    public TaxiDAO()
    {
        this.connection = DatabaseConnection.getConnection();
    }

    public void addTaxi(Taxi taxi)
    {
        try
        {
            PreparedStatement preparedStatement;
            String query = "INSERT INTO taxi VALUES(?,?,?,?,?,?,?);";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, taxi.getTaxiId());
            preparedStatement.setString(2, taxi.getTaxiDriverName());
            preparedStatement.setInt(3, taxi.getTaxiDriverPhone());
            preparedStatement.setString(4, taxi.getAvailable());
            preparedStatement.setString(5, taxi.getNextAvailableTime());
            preparedStatement.setString(6, taxi.getLocation());
            preparedStatement.setInt(7, taxi.getTripCount());
            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
    }

    public void updateInitialDetails() throws SQLException, IOException
    {
        PreparedStatement preparedStatement;
        String query = "UPDATE taxi SET availablilty = ?,nextavailabletime  = ? , location=? ,tripcount=?;";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, "true");
        preparedStatement.setString(2, "09:00");
        preparedStatement.setString(3, "A");
        preparedStatement.setInt(4, 0);
        preparedStatement.execute();
        preparedStatement.close();

    }

    public void updateTaxiDetails(int taxiId, TripDetailsDAO trip) throws SQLException
    {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("UPDATE taxi SET availablilty = ? , tripcount=? where taxiid = ?");
        preparedStatement.setBoolean(1, true);
        preparedStatement.setInt(2, 0);
        preparedStatement.setInt(3, taxiId);
        trip.updateTripStatus(taxiId);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void updateAllTaxiDetails(TripDetailsDAO trip) throws SQLException
    {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("UPDATE taxi SET availablilty = ? , tripcount = ?");
        preparedStatement.setBoolean(1, true);
        preparedStatement.setInt(2, 0);
        trip.updateTripStatus();
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void updateAfterBooking(int taxiId, String nextAvailableTime, String location) throws SQLException
    {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        preparedStatement = connection.prepareStatement("SELECT tripcount FROM taxi WHERE taxiid = ?");
        preparedStatement.setInt(1, taxiId);
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        String query = "UPDATE taxi SET availablilty = ?,nextavailabletime  = ? , location=? , tripcount = ? where taxiid = ?;";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, "false");
        preparedStatement.setString(2, nextAvailableTime);
        preparedStatement.setString(3, location);
        preparedStatement.setInt(4, (resultSet.getInt(1) + 1));
        preparedStatement.setInt(5, taxiId);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public ResultSet getAllData() throws SQLException
    {

        Statement statement;
        ResultSet resultSet;
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT taxiid,availablilty,nextavailabletime,location,tripcount FROM taxi");
        return resultSet;
    }

    public void printTaxiDetails(ResultSet resultSet) throws SQLException
    {
        System.out.println("Taxi Status:");
        System.out.printf("%10s %10s %10s %10s %10s ", "Taxi Id", "Availablity", "Next Available Time", "Location", "Trip Count");
        System.out.println();
        while (resultSet.next())
        {
            System.out.format("%8s %10s %10s %17s %10s", resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),
                    resultSet.getInt(5));
            System.out.println();

        }
    }

    

}
