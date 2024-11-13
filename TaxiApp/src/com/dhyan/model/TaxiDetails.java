package com.dhyan.model;

import java.util.ArrayList;

import jakarta.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class TaxiDetails
{

    public ArrayList<Taxi> taxiList = new ArrayList<Taxi>();

    public TaxiDetails()
    {

    }

    /**
     * @return the taxiList
     */
    public ArrayList<Taxi> getTaxiList()
    {
        return taxiList;
    }

    /**
     * @param taxiList the taxiList to set
     */
    public void setTaxiList(ArrayList<Taxi> taxiList)
    {
        this.taxiList = taxiList;
    }

    @Override
    public String toString()
    {
        return "Taxis [taxiList=" + taxiList + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }
}
