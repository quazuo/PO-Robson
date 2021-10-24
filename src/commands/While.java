package commands;

import util.InvalidProgramException;
import util.RobsonExecutionException;

import java.util.HashMap;
import java.util.HashSet;

public class While extends Command {
    private final Command warunek;
    private final Block blok;

    public While(Command warunek, Block blok) {
        super("While");
        this.warunek = warunek;
        this.blok = blok;
    }

    public Command getWarunek() {
        return warunek;
    }
    public Block getBlok() {
        return blok;
    }

    @Override
    public double execute(HashMap<String, Double> variables) throws RobsonExecutionException {
        while (warunek.execute(variables) != 0.0)
            blok.execute(variables);
        return 0.0;
    }

    @Override
    public String toJavaString(int indent, boolean isProgram, HashSet<String> usedVars,
                               Command program) throws InvalidProgramException {
        StringBuilder code = new StringBuilder();

        code.append("\t".repeat(Math.max(0, indent)))
                .append("while (")
                .append(warunek.toJavaString(0, false, usedVars, program))
                .append(") {\r\n")
                .append(blok.toJavaString(indent + 1, false, usedVars, program))
                .append("\t".repeat(Math.max(0, indent)))
                .append("}\r\n");

        return code.toString();
    }
}
