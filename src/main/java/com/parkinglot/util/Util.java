
package com.parkinglot.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Util {
    public static BigDecimal roundDecimalToHalfEvenWithScale(BigDecimal value, int scale) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(scale);
        df.setMinimumFractionDigits(scale);
        value = value.setScale(scale, RoundingMode.HALF_EVEN);
        String format = df.format(value);
        format = format.replaceAll(",", "");
        return new BigDecimal(format);
    }

}
