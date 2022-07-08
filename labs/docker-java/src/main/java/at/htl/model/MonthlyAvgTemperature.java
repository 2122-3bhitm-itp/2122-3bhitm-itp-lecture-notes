package at.htl.model;

import java.util.Arrays;

public class MonthlyAvgTemperature {
    @Override
    public String toString() {
        return "MonthlyAvgTemperature{" +
                "year=" + year +
                ", temperatures=" + Arrays.toString(temperatures) +
                '}';
    }

    public int year;
    public double[] temperatures;
}
