package com.dhyan.data.access.object;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dhyan.model.Data;
import com.dhyan.model.TripDetails;
import com.dhyan.util.DatabaseConnection;

public class TripDetailsDAO implements Data
{
    static Connection connection;
    static Statement statement;

    public TripDetailsDAO()
    {
        TripDetailsDAO.connection = DatabaseConnection.getConnection();
    }

    @Override
    public void addData(Object object)
    {
        try
        {
            TripDetails trip = (TripDetails) object;
            PreparedStatement preparedStatement;
            String query = "INSERT INTO tripdetails VALUES(?,?,?,?,?,?,?,?);";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, trip.getTripId());
            preparedStatement.setInt(2, trip.getCustomerId());
            preparedStatement.setString(3, trip.getSource());
            preparedStatement.setString(4, trip.getDestination());
            preparedStatement.setString(5, trip.getStartTime());
            preparedStatement.setString(6, trip.getEndTime());
            preparedStatement.setString(7, "Running");
            preparedStatement.setInt(8, trip.getTaxiId());
            preparedStatement.execute();
            preparedStatement.close();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public ResultSet getAllData() throws SQLException
    {
        Statement statement;
        ResultSet resultSet;
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM tripdetails");
        printTripDetails(resultSet);
        statement.close();
        resultSet.close();
        return resultSet;

    }

    public void updateTripStatus(int taxiId) throws SQLException
    {
        PreparedStatement preparedStatement;
        String updateQuery = "UPDATE tripdetails SET status = ? where taxiid =? and status='Running';";
        preparedStatement = connection.prepareStatement(updateQuery);
        preparedStatement.setString(1, "Complete");
        preparedStatement.setInt(2, taxiId);
        preparedStatement.execute();
        preparedStatement.close();

    }

    public void updateTripStatus() throws SQLException
    {
        PreparedStatement preparedStatement;
        String query = "UPDATE tripdetails SET status = ?;";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, "Complete");
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void updateTripStatusCancel(int tripId) throws SQLException
    {
        PreparedStatement preparedStatement;
        String query = "UPDATE tripdetails SET status = ?; WHERE tripid = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, "Cancel");
        preparedStatement.setInt(2, tripId);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void getCustomerDetail(int customerId) throws SQLException
    {
        Statement statement;
        ResultSet resultSet;
        String query = "SELECT * FROM tripdetails WHERE customerId=" + customerId + ";";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        printTripDetails(resultSet);
        statement.close();
        resultSet.close();

    }

    public void printTripDetails(ResultSet resultSet) throws SQLException
    {
        System.out.println("**** Trip Details ****");
        System.out.printf("%10s %10s %10s %10s %10s %10s %10s %10s", "Trip ID", "Customer ID", "Taxi Id", "Source", "Destination", "Start Time", "End Time",
                "Status");
        System.out.println();
        while (resultSet.next())
        {
            System.out.format("%10s %10s %10s %10s %10s %10s %10s %10s ", resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(8), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7));
            System.out.println();

        }
    }

}
