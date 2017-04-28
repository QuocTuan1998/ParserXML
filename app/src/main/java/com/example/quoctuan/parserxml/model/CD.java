package com.example.quoctuan.parserxml.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import static android.R.attr.name;

/**
 * Created by Admin on 4/27/2017.
 */
@Root
public class CD {
    public @Element (name = "TITLE")
    String title;

    public @Element (name = "ARTIST")
    String artist;

    public @Element (name = "COMPANY")
    String company;

    public @Element (name = "COUNTRY")
    String country;

    public @Element (name = "YEAR")
    int year;

    public @Element (name = "PRICE")
    float price;


}
