package de.rechnungflow.cli;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class Formatters {
    private static final NumberFormat eurFormat = createEurFormat();


    private static NumberFormat createEurFormat(){
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        nf.setCurrency(Currency.getInstance("EUR"));
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf;
    }

    public  static String money(BigDecimal value){
        BigDecimal scaled = value.setScale(2, RoundingMode.HALF_EVEN);
        return eurFormat.format(scaled);
    }
}
