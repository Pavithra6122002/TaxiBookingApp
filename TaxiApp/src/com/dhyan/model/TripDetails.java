package com.dhyan.model;

public class TripDetails
{
    private int tripId;
    private int customerId;
    private String source;
    private String destination;
    private String startTime;
    private String endTime;
    private String status;
    private int taxiId;

    /**
     * @return the taxiId
     */
    public int getTaxiId()
    {
        return taxiId;
    }

    /**
     * @param taxiId the taxiId to set
     */
    public void setTaxiId(int taxiId)
    {
        this.taxiId = taxiId;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTIme(String endTIme)
    {
        this.endTime = endTIme;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public TripDetails()
    {
        // TODO Auto-generated constructor stub
    }

    public int getTripId()
    {
        return tripId;
    }

    public void setTripId(int tripId)
    {
        this.tripId = tripId;
    }

    public int getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(int userId)
    {
        this.customerId = userId;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public String getDestination()
    {
        return destination;
    }

    public void setDestination(String destination)
    {
        this.destination = destination;
    }

}
