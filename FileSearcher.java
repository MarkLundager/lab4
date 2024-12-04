
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

public class FileSearcher {

    final static private String commands[] = {"search"};
    static private LinkedList<Integer> list;

    public static void main(String[] args) {
        list = new LinkedList<>();

        if (args.length < 3) {
            System.out.println(
                    "Not enough arguments provided, 3 required, i.e. \"search [expr] [path_to_file]\" (omitting quotation marks)");
            return;
        }
        //Check whether command exists
        String commandArgument = args[0].toLowerCase().trim();
        boolean commandFound = false;
        for (String s : commands) {
            if (s.equals(commandArgument)) {
                commandFound = true;
            }
        }
        if (!commandFound) {
            System.out.println("Command " + "\"" + commandArgument + "\"" + " not found.");
            return;
        }

        //Extract String
        String expr = args[1];
        if (expr.isEmpty()) {
            System.out.println("Please provide non-empty string as second argument");
            return;
        }

        String pathArg = args[2];
        Path path = Paths.get(pathArg);

        if (Files.exists(path)) {
            try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
                String line;
                int lineNbr = 0;
                while ((line = br.readLine()) != null) {
                    if (line.contains(expr)) {
                        list.add(lineNbr);
                    }
                    lineNbr++;
                }
            } catch (IOException e) {
            }
        } else {
            System.out.println("The file does not exist.");
        }
        if (list.isEmpty()) {
            System.out.println("No matches found");
        } else {
            for (int i : list) {
                System.out.println("Found " + "\"" + expr + "\"" + "on line : " + i);
            }
        }

    }

}
