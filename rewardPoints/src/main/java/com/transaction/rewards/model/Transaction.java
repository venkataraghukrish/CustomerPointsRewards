package com.transaction.rewards.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {

    private String custId;
    private String transId;
    private double transAmount;
    private Calendar dateTime;
    private int points;
}
