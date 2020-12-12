package com.bc.bit;

import org.junit.Test;

import java.text.DecimalFormat;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);

    }

    @Test
    public void test(){
        double d = 3.1415926;
        DecimalFormat df = new DecimalFormat("#.##");
        Double get_double = Double.parseDouble(df.format(d));
        System.out.println(get_double);
    }
}