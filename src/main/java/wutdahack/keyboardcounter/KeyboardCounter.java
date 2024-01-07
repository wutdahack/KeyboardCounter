package wutdahack.keyboardcounter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class KeyboardCounter {

    private static int counter = 0;
    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        System.out.println("Counter Name: ");
        String counterName = input.nextLine().toLowerCase();
        String homeDir = System.getProperty("user.home");
        String counterDir = homeDir + "/KeyboardCounter";
        new File(counterDir).mkdirs();
        File counterFile = new File(counterDir + "/" + counterName + ".txt");
        boolean hasCreatedFile = counterFile.createNewFile();
        BufferedReader fileReader = new BufferedReader(new FileReader(counterFile));

        if (!hasCreatedFile) {
            counter = Integer.parseInt(fileReader.readLine());
        } else {
            BufferedWriter tmpWriter = new BufferedWriter(new FileWriter(counterFile));
            tmpWriter.write(Integer.toString(counter));
            tmpWriter.flush(); // i'm not sure if flushing before closing is needed
            tmpWriter.close();
        }

        fileReader.close();
        inputChoice(counterFile);
    }

    private static void inputChoice(File file) throws IOException {
        System.out.println(counter);
        System.out.println("Type + to increment, type - to decrement, or type esc to close the program.");
        String choice = input.nextLine().toLowerCase();
        BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardOpenOption.TRUNCATE_EXISTING);
        switch (choice) {
            case "+" -> {
                counter++;
                writer.write(Integer.toString(counter));
                writer.flush();
                writer.close();
            }
            case "-" -> {
                counter--;
                writer.write(Integer.toString(counter));
                writer.flush();
                writer.close();
            }
            case "esc" -> {
                writer.write(Integer.toString(counter));
                writer.flush();
                input.close();
                writer.close();
                System.exit(0);
            }
            default -> System.out.println("Invalid option, try again");
        }
        inputChoice(file);
    }

}
