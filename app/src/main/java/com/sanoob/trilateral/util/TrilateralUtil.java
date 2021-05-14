package com.sanoob.trilateral.util;

import android.annotation.SuppressLint;

public class TrilateralUtil {

    public static String cleanDoubleAndConvertToString(Double input){

        String converted;
        String number  = input.toString();
        String[] numberPart = number.split("\\.");

        if(numberPart[1].length()>4){
            converted = String.format("%.4f", input);
        }else {
            converted = number;
        }

        return converted;
    }

    public static boolean isStringEmpty(String value) {
        return ((value == null) || (value.isEmpty()) || (value.contains("Value")) || (value.trim().isEmpty()));
    }

}
