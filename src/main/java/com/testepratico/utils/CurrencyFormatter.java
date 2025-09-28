package com.testepratico.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormatter {

    private static final Locale BRAZIL_LOCALE = new Locale("pt", "BR");
    private static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance(BRAZIL_LOCALE);

    public static String format(BigDecimal value) {
        if (value == null) {
            return "R$ 0,00";
        }
        return CURRENCY_FORMATTER.format(value);
    }

    public static String format(Double value) {
        if (value == null) {
            return "R$ 0,00";
        }
        return CURRENCY_FORMATTER.format(value);
    }
}
