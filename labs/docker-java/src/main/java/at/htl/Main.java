package at.htl;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@QuarkusMain

public class Main {

    public static double parseTemperature(String temperature){
        var number = temperature.replace(",",".");
        return Double.parseDouble(number);
    }

    public static void main(String[] args) {

        var filename = args[0];
        System.out.println(filename);

        try {
            Files
                    .lines(Path.of(filename), StandardCharsets.UTF_8)
                    .skip(1)
                    .map(l -> l.split(";"))
                    .map(ar-> {
                        var temperature = new MonthlyAvgTemperature();
                        int index = 0;
                        temperature.year = Integer.parseInt(ar[index++]);
                        temperature.temperatures = new double[12];
                        temperature.temperatures[index-1] = Main.parseTemperature(ar[index++]);
                        return temperature;
                    })
                    .forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Quarkus.run(args);
    }
}

class MonthlyAvgTemperature {
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
