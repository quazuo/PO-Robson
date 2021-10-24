import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import commands.*;
import util.*;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public class Robson {
    private Command program;

    public void fromJSON(String filename) throws FileNotFoundException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(Command.class, new CommandAdapter());
        Gson gson = gsonBuilder.create();

        JsonReader jsonReader = new JsonReader(new FileReader(filename));
        program = gson.fromJson(jsonReader, Command.class);
    }

    public void toJSON(String filename) throws IOException {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        FileWriter writer = new FileWriter(filename);
        writer.write(gson.toJson(program));
        writer.close();
    }

    public void toJava(String filename) throws IOException, InvalidProgramException {
        HashMap<String, Double> variables = new HashMap<>();
        try {
            program.execute(variables);
        } catch (RobsonExecutionException e) {
            e.printStackTrace();
        }

        FileWriter writer = new FileWriter(filename);
        writer.write("public class program {\r\n\tprivate static double program() {\r\n");

        writer.write(program.toJavaString(2, true, new HashSet<>(), program));

        writer.write("\r\n\t}\r\n\r\n\tpublic static void main(String[] args) " +
                        "{\r\n\t\tSystem.out.println(program());\r\n\t}\r\n}");
        writer.close();
    }

    public double execute() {
        try {
            return program.execute(new HashMap<>());
        } catch (RobsonExecutionException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
