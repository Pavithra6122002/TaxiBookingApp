package com.dhyan.data.access.object;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.dhyan.model.Customer;
import com.dhyan.model.Data;
import com.dhyan.util.DatabaseConnection;

public class CustomerDAO implements Data
{
    public static Connection connection;
    static Statement statement;
    static Scanner sc = new Scanner(System.in);

    public CustomerDAO()
    {
        CustomerDAO.connection = DatabaseConnection.getConnection();
    }

    @Override
    public void addData(Object object)
    {
        try
        {
            PreparedStatement preparedStatement;
            String query = "INSERT INTO customer VALUES(?,?);";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ((Customer) object).getcustomerId());
            preparedStatement.setString(2, ((Customer) object).getName());
            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException e)
        {
            System.out.println("Error in SQL Query");
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet getAllData() throws SQLException
    {
        Statement statement;
        ResultSet resultSet;
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM customer");
        System.out.println("**** User Details ****");
        while (resultSet.next())
        {
            System.out.println("Customer ID: " + resultSet.getInt(1) + "  " + "Customer Name : " + resultSet.getString(2));

        }
        statement.close();
        resultSet.close();
        return resultSet;

    }

    public int validateOldUser(int customerId) throws SQLException
    {
        int check = 0;
        while (check == 0)
        {
            int checkCustomerId = 0;
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            preparedStatement = connection.prepareStatement("SELECT  userid from customer ");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                if (resultSet.getInt(1) == customerId)
                {
                    checkCustomerId = 1;
                    break;
                }
            }
            if (checkCustomerId == 0)
            {
                System.out.println("Eneter a correct Id");
                customerId = sc.nextInt();
            }
            else
            {
                check++;
            }

        }
        return customerId;
    }

    public int validateNewUser(int customerId) throws SQLException
    {
        int check = 0;
        while (check == 0)
        {
            int checkCustomerId = 0;
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            preparedStatement = connection.prepareStatement("SELECT  userid from customer ");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                if (resultSet.getInt(1) == customerId)
                {
                    checkCustomerId = 1;
                    break;
                }
            }
            if (checkCustomerId == 0)
            {
                check++;
            }
            else
            {
                System.out.println("Enter a correct Id");
                customerId = sc.nextInt();
            }

        }
        return customerId;
    }

}
