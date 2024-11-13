package com.dhyan.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Taxi
{
    private int taxiId;
    private String taxiDriverName;
    private int taxiDriverPhone;
    private String available;
    private String nextAvailableTime;
    private String location;
    private int tripCount;

    public Taxi(int taxiId, String taxiDiverName, int taxiDiverPhone, String available, String nextAvailableTime, String location)
    {
        this.taxiId = taxiId;
        this.taxiDriverName = taxiDiverName;
        this.taxiDriverPhone = taxiDiverPhone;
        this.available = available;
        this.nextAvailableTime = nextAvailableTime;
        this.location = location;

    }

    public Taxi()
    {

    }

    @XmlElement
    public int getTaxiId()
    {
        return taxiId;
    }

    public void setTaxiId(int taxiId)
    {
        this.taxiId = taxiId;
    }

    @XmlElement
    public String getTaxiDriverName()
    {
        return taxiDriverName;
    }

    public void setTaxiDiverName(String taxiDiverName)
    {
        this.taxiDriverName = taxiDiverName;
    }

    @XmlElement
    public int getTaxiDriverPhone()
    {
        return taxiDriverPhone;
    }

    public void setTaxiDriverPhone(int tdPhone)
    {
        this.taxiDriverPhone = tdPhone;
    }

    @XmlElement
    public String getAvailable()
    {
        return available;
    }

    public void setAvailable(String string)
    {
        this.available = string;
    }

    @XmlElement
    public String getNextAvailableTime()
    {
        return nextAvailableTime;
    }

    public void setNextAvailableTime(String nextAvailableTime)
    {
        this.nextAvailableTime = nextAvailableTime;
    }

    @XmlElement
    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    /**
     * @return the tripCount
     */
    @XmlElement
    public int getTripCount()
    {
        return tripCount;
    }

    /**
     * @param tripCount the tripCount to set
     */
    public void setTripCount(int tripCount)
    {
        this.tripCount = tripCount;
    }

}
