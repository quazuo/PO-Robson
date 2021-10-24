package commands;

import util.InvalidProgramException;
import util.RobsonExecutionException;

import java.util.HashMap;
import java.util.HashSet;

public class LogicOperation extends Command {
    private final Command argument1;
    private final Command argument2;

    public LogicOperation(String type, Command argument1, Command argument2) {
        super(type);
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
        return switch (typ) {
            case "<" -> argument1.execute(variables) < argument2.execute(variables) ? 1.0 : 0.0;
            case ">" -> argument1.execute(variables) > argument2.execute(variables) ? 1.0 : 0.0;
            case "<=" -> argument1.execute(variables) <= argument2.execute(variables) ? 1.0 : 0.0;
            case ">=" -> argument1.execute(variables) >= argument2.execute(variables) ? 1.0 : 0.0;
            case "==" -> argument1.execute(variables) == argument2.execute(variables) ? 1.0 : 0.0;
            default -> throw new RobsonExecutionException("");
        };
    }

    @Override
    public String toJavaString(int indent, boolean isProgram, HashSet<String> usedVars,
                               Command program) throws InvalidProgramException {
        StringBuilder code = new StringBuilder();

        code.append(argument1.toJavaString(indent, false, usedVars, program))
            .append(" ")
            .append(this.typ)
            .append(" ")
            .append(argument2.toJavaString(indent, false, usedVars, program));

        return code.toString();
    }
}
