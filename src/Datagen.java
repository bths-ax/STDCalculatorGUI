// Test class for generating an approximately
// normal distribution of data points

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class Datagen {
    public static int TEST_AMOUNT = 5000;
    public static int TEST_SIZE = 1000;
    public static double TEST_PROBABILITY = 0.5;

    public static void main(String[] args) {
        Calculator calc = new Calculator();

        for (int i = 0; i < TEST_AMOUNT; i++) {
            int successes = 0;
            for (int j = 0; j < TEST_SIZE; j++)
                if (Math.random() > (1 - TEST_PROBABILITY))
                    successes++;
            calc.add(successes);
        }

        try {
            Files.writeString(new File("data.std").toPath(), calc.serialize(), StandardOpenOption.CREATE);
        } catch (Exception ex) { }
    }
}
