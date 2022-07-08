package at.htl;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@QuarkusMain

public class Main {

    public static double parseTemperature(String temperature){
        var number = temperature.replace(",",".");
        return Double.parseDouble(number);
    }

    public static void main(String[] args) {

        var filename = args[0];
        System.out.println(filename);

        var reader = new MontlyTemperatureFileReader();
        reader.parseLines(filename);

        //Quarkus.run(args);
    }

}
