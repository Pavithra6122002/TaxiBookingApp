package com.dhyan.data.access.object;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.HashMap;

import com.dhyan.model.Taxi;
import com.dhyan.model.TripDetails;

public class TaxiService
{

    static int duration;
    LocalTime timing;

    /**
     * @return the duration
     */
    public int getDuration()
    {
        return duration;
    }

    /**
     * @return the timing
     */
    public LocalTime getTiming()
    {
        return timing;
    }

    public Taxi findTaxi(String bookingTime, TripDetails trip, TaxiService taxiservice) throws SQLException
    {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        findPlace(map);
        int source = map.get(trip.getSource().toUpperCase());
        int destination = map.get(trip.getDestination().toUpperCase());
        LocalTime time = LocalTime.of(00, 00);
        LocalTime minimum = LocalTime.of(23, 59);
        Taxi taxi = new Taxi();
        TaxiDAO taxidao = new TaxiDAO();
        int working = 0;
        ResultSet resultSet = taxidao.getAllData();
        while (resultSet.next())
        {
            int tabledata = map.get(resultSet.getString(4).toUpperCase());
            String nextAvailable = resultSet.getString(3);
            int val = (LocalTime.parse(nextAvailable)).compareTo(LocalTime.parse(bookingTime));

            LocalTime nextTime = LocalTime.parse(nextAvailable);
            LocalTime booking = LocalTime.parse(bookingTime);
            if (resultSet.getString(2).equals("true"))
            {
                if (val < 0 || resultSet.getInt(5) != 2)

                {
                    working++;
                    if (resultSet.getString(4) == trip.getSource())
                    {
                        timing = LocalTime.of(00, 00);
                        resultSet.next();
                        setValuesToTaxi(resultSet, taxi);
                        return taxi;
                    }
                    if (val > 0)
                    {
                        LocalTime timeToReachSource = nextTime.minusHours(booking.getHour());
                        timeToReachSource = nextTime.minusMinutes(booking.getMinute());
                        time = timeToReachSource.plusMinutes(taxiservice.findNearestTaxi(tabledata, source));
                    }
                    time = time.plusMinutes(taxiservice.findNearestTaxi(tabledata, source));
                }
            }
            else
            {
                if (resultSet.getInt(5) != 2)
                {
                    working++;
                    if (resultSet.getString(4) == trip.getSource())
                    {
                        time = nextTime.minusHours(booking.getHour());
                        time = nextTime.minusMinutes(booking.getMinute());
                    }
                    else if (val > 0)
                    {
                        LocalTime timeToReachSource = nextTime.minusHours(booking.getHour());
                        timeToReachSource = nextTime.minusMinutes(booking.getMinute());
                        time = timeToReachSource.plusMinutes(taxiservice.findNearestTaxi(tabledata, source));
                    }
                    else
                    {
                        time = time.plusMinutes(taxiservice.findNearestTaxi(tabledata, source));
                    }
                }
            }
            if (working != 0)
            {
                int minTimeCompare = (minimum).compareTo(time);
                if (minTimeCompare > 0)
                {
                    minimum = time;
                    setValuesToTaxi(resultSet, taxi);
                }
            }

        }
        timing = minimum;
        duration = taxiservice.findNearestTaxi(source, destination);
        if (working != 0)
        {
            return taxi;
        }
        else
        {
            return null;
        }
    }

    public Object findPlace(HashMap<String, Integer> hash_map)
    {
        hash_map.put("A", 0);
        hash_map.put("B", 1);
        hash_map.put("C", 2);
        hash_map.put("D", 3);
        hash_map.put("E", 4);
        return hash_map;

    }

    public int minimumDistance(int timeToReach[], Boolean shortestPathSet[])
    {
        int minValue = Integer.MAX_VALUE, minIndex = -1;
        for (int vertex = 0; vertex < 5; vertex++)
        {
            if (shortestPathSet[vertex] == false && timeToReach[vertex] <= minValue)
            {
                minValue = timeToReach[vertex];
                minIndex = vertex;
            }
        }
        return minIndex;
    }

    public int findNearestTaxi(int source, int destination)
    {
        int graph[][] = new int[][]
        {
                {-1, 15, -1, -1, 25 },
                {15, -1, 10, -1, 20 },
                {-1, 10, -1, 23, -1 },
                {-1, -1, 23, -1, 16 },
                {25, 20, -1, 16, -1 } };
        int timeToReach[] = new int[5];
        Boolean shortestPathSet[] = new Boolean[5];
        for (int j = 0; j < 5; j++)
        {
            timeToReach[j] = Integer.MAX_VALUE;
            shortestPathSet[j] = false;
        }

        timeToReach[source] = 0;

        for (int i = 0; i < 5 - 1; i++)
        {
            int minIndex = 0;
            try
            {
                minIndex = minimumDistance(timeToReach, shortestPathSet);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            shortestPathSet[minIndex] = true;
            for (int vertex = 0; vertex < 5; vertex++)
            {
                if (!shortestPathSet[vertex] && graph[minIndex][vertex] != -1 && timeToReach[minIndex] != Integer.MAX_VALUE
                        && timeToReach[minIndex] + graph[minIndex][vertex] < timeToReach[vertex])
                {
                    timeToReach[vertex] = timeToReach[minIndex] + graph[minIndex][vertex];
                }
            }
        }

        return timeToReach[destination];
    }

    public void setValuesToTaxi(ResultSet resultSet, Taxi taxi) throws SQLException
    {
        taxi.setTaxiId(resultSet.getInt(1));
        taxi.setAvailable(resultSet.getString(2));
        taxi.setNextAvailableTime(resultSet.getString(3));
        taxi.setLocation(resultSet.getString(4));
    }
}