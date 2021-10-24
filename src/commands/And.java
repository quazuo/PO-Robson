package commands;

import util.InvalidProgramException;
import util.RobsonExecutionException;

import java.util.HashMap;
import java.util.HashSet;

public class And extends Command {
    private final Command argument1;
    private final Command argument2;

    public And(Command argument1, Command argument2) {
        super("And");
        this.argument1 = argument1;
        this.argument2 = argument2;
    }

    public Command getArgument1() {
        return argument1;
    }
    public Command getArgument2() {
        return argument2;
    }

    @Override
    public double execute(HashMap<String, Double> variables) throws RobsonExecutionException {
        return argument1.execute(variables) != 0.0 && argument2.execute(variables) != 0.0 ? 1.0 : 0.0;
    }

    @Override
    public String toJavaString(int indent, boolean isProgram, HashSet<String> usedVars,
                               Command program) throws InvalidProgramException {
        StringBuilder code = new StringBuilder();

        code.append(argument1.toJavaString(indent, false, usedVars, program))
                .append(" && ")
                .append(argument2.toJavaString(indent, false, usedVars, program));

        return code.toString();
    }
}
