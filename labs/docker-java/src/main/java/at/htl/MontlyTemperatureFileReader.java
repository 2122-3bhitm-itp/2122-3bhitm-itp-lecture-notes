package at.htl;

import at.htl.model.MonthlyAvgTemperature;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class MontlyTemperatureFileReader {
    public void parseLines(String filename) {
        try {
            var monthlyAvgTemperatures = Files
                    .lines(Path.of(filename), StandardCharsets.UTF_8)
                    .skip(1)
                    .map(l -> l.split(";"))
                    .map(this::getMonthlyAvgTemperature)
                    .toList();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private MonthlyAvgTemperature getMonthlyAvgTemperature(String[] ar) {
        var temperature = new MonthlyAvgTemperature();
        int index = 0;
        temperature.year = Integer.parseInt(ar[index++]);
        temperature.temperatures = new double[12];
        for (int i = index; i < ar.length-1; i++) {
            temperature.temperatures[i-1] = Main.parseTemperature(ar[i]);
        }
        return temperature;
    }

}
