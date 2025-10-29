package com.ktb.community.global.processor;

public class NumberProcessor {

    public static String getFormatNumber(long number) {
        if(number >= 1000){
            number = number / 1000;
            return String.valueOf(number)+"K";
        }
        return String.valueOf(number);
    }
}
