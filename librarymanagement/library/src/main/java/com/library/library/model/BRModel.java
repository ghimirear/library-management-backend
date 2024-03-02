package com.library.library.model;

import lombok.Data;

import java.util.Date;

@Data
public class BRModel {
    private Long uid;
    private Long bid;
    private Date borrowDate;
    private int forDays;

}
