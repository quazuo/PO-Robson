package commands;

import util.InvalidProgramException;
import util.RobsonExecutionException;

import java.util.HashMap;
import java.util.HashSet;

public class LogicValue extends Command {
    private final boolean wartosc;

    public LogicValue(String type, boolean wartosc) {
        super(type);
        this.wartosc = wartosc;
    }

    public boolean isWartosc() {
        return wartosc;
    }

    @Override
    public double execute(HashMap<String, Double> variables) throws RobsonExecutionException {
        return wartosc ? 1.0 : 0.0;
    }

    @Override
    public String toJavaString(int indent, boolean isProgram, HashSet<String> usedVars,
                               Command program) throws InvalidProgramException {
        return this.typ.toLowerCase();
    }
}
