package com.dhyan.util;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnection
{

    private final static String propertyFilePath = "/home/pavithra/workspace/TaxiApp/src/DatabaseInput.properties";

    public static Connection getConnection()
    {

        Connection connection = null;
        try
        {
            Properties prop = new Properties();
            FileReader reader = new FileReader(propertyFilePath);
            prop.load(reader);
            String url = prop.getProperty("url");
            String username = prop.getProperty("userName");
            String password = prop.getProperty("password");
            connection = DriverManager.getConnection(url, username, password);
            return connection;
        }
        catch (Exception e)
        {
            return null;

        }
    }
}
