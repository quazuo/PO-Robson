import commands.*;
import util.InvalidProgramException;

import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class RobsonTest {

    public static String testBinom() throws IOException, InvalidProgramException {
        Robson robson = new Robson();

        System.out.println("binom.json:");
        robson.fromJSON("./TESTY/binom.json");
        if(robson.execute() != 252.0)
            return "Źle";
        robson.toJSON("./TESTY/binomTOJSON.txt");
        robson.fromJSON("./TESTY/binomTOJSON.txt");
        if(robson.execute() != 252.0)
            return "Źle";
        robson.toJava("./TESTY/binomJAVA.txt");

        return "Dobrze";
    }

    public static String testExample1() throws IOException, InvalidProgramException {
        Robson robson = new Robson();

        System.out.println("example1.json:");
        robson.fromJSON("./TESTY/example1.json");
        if(robson.execute() != 15.0)
            return "Źle";
        robson.toJSON("./TESTY/example1TOJSON.txt");
        robson.fromJSON("./TESTY/example1TOJSON.txt");
        if(robson.execute() != 15.0)
            return "Źle";
        robson.toJava("./TESTY/example1JAVA.txt");

        return "Dobrze";
    }

    public static String testExample2() throws IOException, InvalidProgramException {
        Robson robson = new Robson();

        System.out.println("example2.json:");
        robson.fromJSON("./TESTY/example2.json");
        if(robson.execute() != 55.0)
            return "Źle";
        robson.toJSON("./TESTY/example2TOJSON.txt");
        robson.fromJSON("./TESTY/example2TOJSON.txt");
        if(robson.execute() != 55.0)
            return "Źle";
        robson.toJava("./TESTY/example2JAVA.txt");

        return "Dobrze";
    }
}
