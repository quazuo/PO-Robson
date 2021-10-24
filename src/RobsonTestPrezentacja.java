import commands.*;
import util.InvalidProgramException;

import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class RobsonTestPrezentacja {

    public static String testBinom() throws IOException, InvalidProgramException {
        Robson robson = new Robson();

        System.out.println("binom.json:");
        robson.fromJSON("C:\\Users\\PCx\\Desktop\\Testy\\binom.json");
        if(robson.execute() != 252.0)
            return "Źle";
        robson.toJSON("C:\\Users\\PCx\\Desktop\\Testy\\binomTOJSON.txt");
        robson.fromJSON("C:\\Users\\PCx\\Desktop\\Testy\\binomTOJSON.txt");
        if(robson.execute() != 252.0)
            return "Źle";
        robson.toJava("C:\\Users\\PCx\\Desktop\\Testy\\binomJAVA.txt");

        return "Dobrze";
    }

    public static String testExample1() throws IOException, InvalidProgramException {
        Robson robson = new Robson();

        System.out.println("example1.json:");
        robson.fromJSON("C:\\Users\\PCx\\Desktop\\Testy\\example1.json");
        if(robson.execute() != 15.0)
            return "Źle";
        robson.toJSON("C:\\Users\\PCx\\Desktop\\Testy\\example1TOJSON.txt");
        robson.fromJSON("C:\\Users\\PCx\\Desktop\\Testy\\example1TOJSON.txt");
        if(robson.execute() != 15.0)
            return "Źle";
        robson.toJava("C:\\Users\\PCx\\Desktop\\Testy\\example1JAVA.txt");

        return "Dobrze";
    }

    public static String testExample2() throws IOException, InvalidProgramException {
        Robson robson = new Robson();

        System.out.println("example2.json:");
        robson.fromJSON("C:\\Users\\PCx\\Desktop\\Testy\\example2.json");
        if(robson.execute() != 55.0)
            return "Źle";
        robson.toJSON("C:\\Users\\PCx\\Desktop\\Testy\\example2TOJSON.txt");
        robson.fromJSON("C:\\Users\\PCx\\Desktop\\Testy\\example2TOJSON.txt");
        if(robson.execute() != 55.0)
            return "Źle";
        robson.toJava("C:\\Users\\PCx\\Desktop\\Testy\\example2JAVA.txt");

        return "Dobrze";
    }
}
