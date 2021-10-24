package commands;

import util.InvalidProgramException;
import util.RobsonExecutionException;

import java.util.HashMap;
import java.util.HashSet;

public class If extends Command {
    private final Command warunek;
    private final Block blok_prawda;
    private final Block blok_falsz;

    public If(Command warunek, Block blok_prawda) {
        super("If");
        this.warunek = warunek;
        this.blok_prawda = blok_prawda;
        this.blok_falsz = null;
    }

    public If(Command warunek, Block blok_prawda, Block blok_falsz) {
        super("If");
        this.warunek = warunek;
        this.blok_prawda = blok_prawda;
        this.blok_falsz = blok_falsz;
    }

    public Command getWarunek() {
        return warunek;
    }
    public Block getBlok_prawda() {
        return blok_prawda;
    }
    public Block getBlok_falsz() {
        return blok_falsz;
    }

    @Override
    public double execute(HashMap<String, Double> variables) throws RobsonExecutionException {
        if (warunek.execute(variables) != 0.0)
            return blok_prawda.execute(variables);
        else if (blok_falsz != null)
            return blok_falsz.execute(variables);
        else
            return 0.0;
    }

    @Override
    public String toJavaString(int indent, boolean isProgram, HashSet<String> usedVars,
                               Command program) throws InvalidProgramException {
        StringBuilder code = new StringBuilder();

        if (!blok_prawda.getTyp().equals("Blok"))
            throw new InvalidProgramException("");

        code.append("\t".repeat(Math.max(0, indent)))
            .append("if (")
            .append(warunek.toJavaString(0, false, usedVars, program))
            .append(") {\r\n")
            .append(blok_prawda.toJavaString(indent + 1, isProgram, usedVars, program))
            .append("\r\n")
            .append("\t".repeat(Math.max(0, indent)))
            .append("}\r\n");

        if (blok_falsz != null) {
            if (!blok_falsz.getTyp().equals("Blok"))
                throw new InvalidProgramException("");
            code.append("\t".repeat(Math.max(0, indent)))
                    .append("else {\r\n")
                    .append(blok_falsz.toJavaString(indent + 1, isProgram, usedVars, program))
                    .append("\r\n")
                    .append("\t".repeat(Math.max(0, indent)))
                    .append("}\r\n");
        }

        return code.toString();
    }
}
