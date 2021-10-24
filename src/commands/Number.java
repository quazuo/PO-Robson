package commands;

import util.InvalidProgramException;
import util.RobsonExecutionException;

import java.util.HashMap;
import java.util.HashSet;

public class Number extends Command {
    private final double wartosc;

    public Number(double wartosc) {
        super("Liczba");
        this.wartosc = wartosc;
    }

    public double getWartosc() {
        return wartosc;
    }

    @Override
    public double execute(HashMap<String, Double> variables) throws RobsonExecutionException {
        return wartosc;
    }

    @Override
    public String toJavaString(int indent, boolean isProgram, HashSet<String> usedVars,
                               Command program) throws InvalidProgramException {
        return Double.toString(wartosc);
    }
}
