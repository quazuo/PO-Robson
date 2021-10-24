import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import commands.*;
import commands.Number;
import util.*;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public class Robson {
    private Command program;

    public void fromJSON(String filename) throws FileNotFoundException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(Command.class, new CommandAdapter());
        Gson gson = gsonBuilder.create();

        JsonReader jsonReader = new JsonReader(new FileReader(filename));
        program = gson.fromJson(jsonReader, Command.class);
    }

    public void toJSON(String filename) throws IOException {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        FileWriter writer = new FileWriter(filename);
        writer.write(gson.toJson(program));
        writer.close();
    }

    /*
    private String getCommandAsJava(Command command, int indent, boolean isProgram,
                                           HashSet<String> usedVars) throws InvalidProgramException {
        StringBuilder code = new StringBuilder();

        switch (command.getTyp()) {
            case "Blok" -> {
                Block block = (Block) command;
                if (block.getInstrukcje() == null || block.getInstrukcje().size() == 0) {
                    code.append("\t".repeat(Math.max(0, indent)))
                        .append("return 0.0;\r\n");
                    break;
                }
                int i = 0;
                for (; i < block.getInstrukcje().size() - 1; i++) {
                    code.append(getCommandAsJava(block.getInstrukcje().get(i), indent, false, usedVars));
                }
                if (isProgram) {
                    Command lastCommand = block.getInstrukcje().get(i);
                    switch (lastCommand.getTyp()) {
                        case "Plus", "Minus", "Razy", "Dzielenie", "True", "False" -> {
                            code.append("\t".repeat(Math.max(0, indent)))
                                .append("return ")
                                .append(getCommandAsJava(lastCommand, indent, false, usedVars))
                                .append(";");
                        }
                        case "Przypisanie" -> {
                            code.append(getCommandAsJava(lastCommand, indent, false, usedVars))
                                .append("\t".repeat(Math.max(0, indent)))
                                .append("return ")
                                .append(((Assignment) lastCommand).getNazwa())
                                .append(";");
                        }
                        case "If" -> {
                            code.append(getCommandAsJava(block.getInstrukcje().get(i), indent, true, usedVars));
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
                    code.append(getCommandAsJava(block.getInstrukcje().get(i), indent, false, usedVars));
                }
            }
            case "If" -> {
                If ifCommand = (If) command;
                if (!ifCommand.getBlok_prawda().getTyp().equals("Blok"))
                    throw new InvalidProgramException("");

                code.append("\t".repeat(Math.max(0, indent)))
                    .append("if (")
                    .append(getCommandAsJava(ifCommand.getWarunek(), 0, false, usedVars))
                    .append(") {\r\n")
                    .append(getCommandAsJava(ifCommand.getBlok_prawda(), indent + 1, isProgram, usedVars))
                    .append("\r\n")
                    .append("\t".repeat(Math.max(0, indent)))
                    .append("}\r\n");

                if (ifCommand.getBlok_falsz() != null) {
                    if (!ifCommand.getBlok_falsz().getTyp().equals("Blok"))
                        throw new InvalidProgramException("");
                    code.append("\t".repeat(Math.max(0, indent)))
                        .append("else {\r\n")
                        .append(getCommandAsJava(ifCommand.getBlok_falsz(), indent + 1, isProgram, usedVars))
                        .append("\r\n")
                        .append("\t".repeat(Math.max(0, indent)))
                        .append("}\r\n");
                }
            }
            case "While" -> {
                While whileCommand = (While) command;
                code.append("\t".repeat(Math.max(0, indent)))
                        .append("while (")
                        .append(getCommandAsJava(whileCommand.getWarunek(), 0, false, usedVars))
                        .append(") {\r\n")
                        .append(getCommandAsJava(whileCommand.getBlok(), indent + 1, false, usedVars))
                        .append("\t".repeat(Math.max(0, indent)))
                        .append("}\r\n");
            }
            case "Przypisanie" -> {
                Assignment assignment = (Assignment) command;
                code.append("\t".repeat(Math.max(0, indent)));
                if (!usedVars.contains(assignment.getNazwa())) {
                    usedVars.add(assignment.getNazwa());
                    code.append("double ");
                }
                code.append(assignment.getNazwa())
                .append(" = ");

                Command wartosc = assignment.getWartosc();
                if (wartosc.getTyp().equals("Liczba"))
                    code.append(assignment.getExecuteValue());
                else if (wartosc.getTyp().equals("Zmienna"))
                    code.append(((Variable) wartosc).getNazwa());
                else
                    code.append(getCommandAsJava(wartosc, indent, false, usedVars));

                code.append(";\r\n");
            }
            case "Plus" -> {
                NumericalOperation numOp = (NumericalOperation) command;
                code.append(getCommandAsJava(numOp.getArgument1(), indent, false, usedVars))
                        .append(" + ")
                        .append(getCommandAsJava(numOp.getArgument2(), indent, false, usedVars));
            }
            case "Minus" -> {
                NumericalOperation numOp = (NumericalOperation) command;
                code.append(getCommandAsJava(numOp.getArgument1(), indent, false, usedVars))
                        .append(" - ")
                        .append(getCommandAsJava(numOp.getArgument2(), indent, false, usedVars));
            }
            case "Razy" -> {
                NumericalOperation numOp = (NumericalOperation) command;
                code.append(getCommandAsJava(numOp.getArgument1(), indent, false, usedVars))
                        .append(" * ")
                        .append(getCommandAsJava(numOp.getArgument2(), indent, false, usedVars));
            }
            case "Dzielenie" -> {
                NumericalOperation numOp = (NumericalOperation) command;
                code.append(getCommandAsJava(numOp.getArgument1(), indent, false, usedVars))
                        .append(" / ")
                        .append(getCommandAsJava(numOp.getArgument2(), indent, false, usedVars));
            }
            case "And" -> {
                And and = (And) command;
                code.append(getCommandAsJava(and.getArgument1(), indent, false, usedVars))
                        .append(" && ")
                        .append(getCommandAsJava(and.getArgument2(), indent, false, usedVars));
            }
            case "Or" -> {
                Or or = (Or) command;
                code.append(getCommandAsJava(or.getArgument1(), indent, false, usedVars))
                        .append(" || ")
                        .append(getCommandAsJava(or.getArgument2(), indent, false, usedVars));
            }
            case "<" -> {
                LogicOperation logOp = (LogicOperation) command;
                code.append(getCommandAsJava(logOp.getArgument1(), indent, false, usedVars))
                        .append(" < ")
                        .append(getCommandAsJava(logOp.getArgument2(), indent, false, usedVars));
            }
            case ">" -> {
                LogicOperation logOp = (LogicOperation) command;
                code.append(getCommandAsJava(logOp.getArgument1(), indent, false, usedVars))
                        .append(" > ")
                        .append(getCommandAsJava(logOp.getArgument2(), indent, false, usedVars));
            }
            case "<=" -> {
                LogicOperation logOp = (LogicOperation) command;
                code.append(getCommandAsJava(logOp.getArgument1(), indent, false, usedVars))
                        .append(" <= ")
                        .append(getCommandAsJava(logOp.getArgument2(), indent, false, usedVars));
            }
            case ">=" -> {
                LogicOperation logOp = (LogicOperation) command;
                code.append(getCommandAsJava(logOp.getArgument1(), indent, false, usedVars))
                        .append(" >= ")
                        .append(getCommandAsJava(logOp.getArgument2(), indent, false, usedVars));
            }
            case "==" -> {
                LogicOperation logOp = (LogicOperation) command;
                code.append(getCommandAsJava(logOp.getArgument1(), indent, false, usedVars))
                        .append(" == ")
                        .append(getCommandAsJava(logOp.getArgument2(), indent, false, usedVars));
            }
            case "Not" -> {
                Not not = (Not) command;
                code.append("!(")
                        .append(getCommandAsJava(not.getArgument(), indent, false, usedVars))
                        .append(")");
            }
            case "True" -> {
                code.append("true");
            }
            case "False" -> {
                code.append("false");
            }
            case "Zmienna" -> {
                Variable variable = (Variable) command;
                code.append(variable.getNazwa());
            }
            case "Liczba" -> {
                Number number = (Number) command;
                code.append(number.getWartosc());
            }
            default -> throw new InvalidProgramException("");
        }

        return code.toString();
    } */

    public void toJava(String filename) throws IOException, InvalidProgramException {
        HashMap<String, Double> variables = new HashMap<>();
        try {
            program.execute(variables);
        } catch (RobsonExecutionException e) {
            e.printStackTrace();
        }

        FileWriter writer = new FileWriter(filename);
        writer.write("public class program {\r\n\tprivate static double program() {\r\n");

        // writer.write(getCommandAsJava(program, 2, true, new HashSet<>()));
        writer.write(program.toJavaString(2, true, new HashSet<>(), program));

        writer.write("\r\n\t}\r\n\r\n\tpublic static void main(String[] args) " +
                        "{\r\n\t\tSystem.out.println(program());\r\n\t}\r\n}");
        writer.close();
    }

    public double execute() {
        try {
            return program.execute(new HashMap<>());
        } catch (RobsonExecutionException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
