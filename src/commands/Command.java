package commands;

import util.InvalidProgramException;
import util.RobsonExecutionException;

import java.util.HashMap;
import java.util.HashSet;

public abstract class Command {
    protected String typ;

    public Command(String typ) {
        this.typ = typ;
    }

    public String getTyp() {
        return typ;
    }

    public abstract double execute(HashMap<String, Double> variables) throws RobsonExecutionException;

    public abstract String toJavaString(int indent, boolean isProgram, HashSet<String> usedVars,
                                        Command program) throws InvalidProgramException;
}
