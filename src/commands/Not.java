package commands;

import util.InvalidProgramException;
import util.RobsonExecutionException;

import java.util.HashMap;
import java.util.HashSet;

public class Not extends Command {
    private final Command argument;

    public Not(Command argument) {
        super("Not");
        this.argument = argument;
    }

    public Command getArgument() {
        return argument;
    }

    @Override
    public double execute(HashMap<String, Double> variables) throws RobsonExecutionException {
        return argument.execute(variables) != 0.0 ? 0.0 : 1.0;
    }

    @Override
    public String toJavaString(int indent, boolean isProgram, HashSet<String> usedVars,
                               Command program) throws InvalidProgramException {
        StringBuilder code = new StringBuilder();

        code.append("!(")
                .append(argument.toJavaString(indent, false, usedVars, program))
                .append(")");

        return code.toString();
    }
}
