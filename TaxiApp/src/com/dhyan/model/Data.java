package com.dhyan.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Data
{
    public void addData(Object object);

    public ResultSet getAllData() throws SQLException;
}
