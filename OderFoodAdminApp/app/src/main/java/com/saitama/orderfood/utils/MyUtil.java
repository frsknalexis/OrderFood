package com.saitama.orderfood.utils;

import java.text.DecimalFormat;

public class MyUtil {
    public static String formatCurrency(double value) {
        DecimalFormat df = new DecimalFormat("###,###,###");
        return df.format(value);
    }
}
