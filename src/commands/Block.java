package commands;

import util.InvalidProgramException;
import util.RobsonExecutionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Block extends Command {
    private final ArrayList<Command> instrukcje;

    public Block(ArrayList<Command> commands) {
        super("Blok");
        this.instrukcje = commands;
    }

    public ArrayList<Command> getInstrukcje() {
        return new ArrayList<>(instrukcje);
    }

    @Override
    public double execute(HashMap<String, Double> variables) throws RobsonExecutionException {
        if (instrukcje == null || instrukcje.size() == 0)
            return 0.0;

        for (int i = 0; i < instrukcje.size() - 1; i++)
            instrukcje.get(i).execute(variables);

        return instrukcje.get(instrukcje.size() - 1).execute(variables);
    }

    @Override
    public String toJavaString(int indent, boolean isProgram, HashSet<String> usedVars,
                               Command program) throws InvalidProgramException {
        StringBuilder code = new StringBuilder();

        if (this.getInstrukcje() == null || this.getInstrukcje().size() == 0) {
            code.append("\t".repeat(Math.max(0, indent)))
                    .append("return 0.0;\r\n");
        }

        int i = 0;
        for (; i < instrukcje.size() - 1; i++) {
            code.append(instrukcje.get(i).toJavaString(indent, false, usedVars, program));
        }
        if (isProgram) {
            Command lastCommand = instrukcje.get(i);
            switch (lastCommand.getTyp()) {
                case "Plus", "Minus", "Razy", "Dzielenie", "True", "False" -> {
                    code.append("\t".repeat(Math.max(0, indent)))
                            .append("return ")
                            .append(lastCommand.toJavaString(indent, false, usedVars, program))
                            .append(";");
                }
                case "Przypisanie" -> {
                    code.append(lastCommand.toJavaString(indent, false, usedVars, program))
                            .append("\t".repeat(Math.max(0, indent)))
                            .append("return ")
                            .append(((Assignment) lastCommand).getNazwa())
                            .append(";");
                }
                case "If" -> {
                    code.append(lastCommand.toJavaString(indent, true, usedVars, program));
                }
                case "Liczba" -> {
                    code.append("\t".repeat(Math.max(0, indent)))
                            .append("return ")
                            .append(((Number) lastCommand).getWartosc())
                            .append(";");
                }
                case "Zmienna" -> {
                    code.append("\t".repeat(Math.max(0, indent)))
                            .append("return ")
                            .append(((Variable) lastCommand).getNazwa())
                            .append(";");
                }
                default -> {
                    HashMap<String, Double> variables = new HashMap<>();
                    try {
                        code.append("\t".repeat(Math.max(0, indent)))
                                .append("return ")
                                .append(program.execute(variables))
                                .append(";");
                    } catch (RobsonExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else {
            code.append(instrukcje.get(i).toJavaString(indent, false, usedVars, program));
        }

        return code.toString();
    }
}
