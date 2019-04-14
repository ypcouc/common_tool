package com.ypc.common.utils;

import java.text.DecimalFormat;

public class DoubleUtil {

    public static double getTwoDecimal(double num) {
        DecimalFormat dFormat = new DecimalFormat("#.00");
        String yearString = dFormat.format(num);
        Double temp = Double.valueOf(yearString);
        return temp;
    }
}
