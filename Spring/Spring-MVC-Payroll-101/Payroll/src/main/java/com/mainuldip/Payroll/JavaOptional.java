package com.mainuldip.Payroll;

import io.micrometer.observation.Observation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class JavaOptional {
    public static void main(String[] args)
    {

        // creating a string array
        String[] str = {};
        System.out.println(str.length);

        List<String> al1 = Arrays.asList("a","b","c");
        ArrayList<String> al2 = (ArrayList<String>) Arrays.asList("a","b","c"); // casting as ArrayList<String>
        ArrayList<String> al3 = new ArrayList<String>(Arrays.asList("a","b","c"));
        ArrayList<String> al3a = new ArrayList<>(Arrays.asList("a","b","c"));
        ArrayList<String> al4 = new ArrayList<String>();
        al4.add("a"); // returns boolean
        boolean al4b = al4.add("b");

        // Setting value for 2nd index
        str[2] = "Geeks Classes are coming soon";

        System.out.println(str.length);

        // It returns an empty instance of Optional class
        Optional<String> empty = Optional.empty();
        System.out.println(empty);

        // It returns a non-empty Optional
        Optional<String> value = Optional.of(str[2]);
        System.out.println(value);
    }
}
