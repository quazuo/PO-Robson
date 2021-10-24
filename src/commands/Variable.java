package commands;

import util.InvalidProgramException;
import util.RobsonExecutionException;

import java.util.HashMap;
import java.util.HashSet;

public class Variable extends Command {
    private final String nazwa;

    public Variable(String nazwa) {
        super("Zmienna");
        this.nazwa = nazwa;
    }

    public String getNazwa() {
        return nazwa;
    }

    @Override
    public double execute(HashMap<String, Double> variables) throws RobsonExecutionException {
        if (!variables.containsKey(nazwa))
            throw new RobsonExecutionException("Odwołanie do niezainicjalizowanej zmiennej");
        return variables.get(nazwa);
    }

    @Override
    public String toJavaString(int indent, boolean isProgram, HashSet<String> usedVars,
                               Command program) throws InvalidProgramException {
        return nazwa;
    }
}
