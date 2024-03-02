package com.library.library.model;

import lombok.Data;

@Data
public class Address {
    private String address;
    private String city;
    private String state;
    private Long zipcode;

    public String getAddressString() {
        return address + ", " + city + ", " + state + " " + zipcode;
    }

}
