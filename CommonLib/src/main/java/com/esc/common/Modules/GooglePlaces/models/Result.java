package com.esc.common.Modules.GooglePlaces.models;

import java.util.List;

public class Result implements java.io.Serializable
{
    public String formatted_address;
    public Geometry geometry;
    public String icon;
    public String id;
    public String name;
    public List<Photo> photos;
    public String place_id;
    public double rating;
    public String reference;
    public List<String> types;
}
