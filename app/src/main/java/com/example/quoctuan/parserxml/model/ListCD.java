package com.example.quoctuan.parserxml.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Admin on 4/27/2017.
 */
@Root
public class ListCD {
    @ElementList (entry = "CD",inline = true)
    public List<CD> arrCD;
}
