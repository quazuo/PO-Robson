package commands;

import util.InvalidProgramException;
import util.RobsonExecutionException;

import java.util.HashMap;
import java.util.HashSet;

public class Assignment extends Command {
    private final String nazwa;
    private final Command wartosc;
    private transient double executeValue;

    public Assignment(String nazwa, Number wartosc) {
        super("Przypisanie");
        this.nazwa = nazwa;
        this.wartosc = wartosc;
    }

    public String getNazwa() {
        return nazwa;
    }
    public Command getWartosc() {
        return wartosc;
    }
    public double getExecuteValue() {
        return executeValue;
    }

    @Override
    public double execute(HashMap<String, Double> variables) throws RobsonExecutionException {
        executeValue = wartosc.execute(variables);
        variables.put(nazwa, executeValue);
        return executeValue;
    }

    @Override
    public String toJavaString(int indent, boolean isProgram, HashSet<String> usedVars,
                               Command program) throws InvalidProgramException {
        StringBuilder code = new StringBuilder();

        code.append("\t".repeat(Math.max(0, indent)));
        if (!usedVars.contains(nazwa)) {
            usedVars.add(nazwa);
            code.append("double ");
        }
        code.append(nazwa)
                .append(" = ");

        if (wartosc.getTyp().equals("Liczba"))
            code.append(executeValue);
        else if (wartosc.getTyp().equals("Zmienna"))
            code.append(((Variable) wartosc).getNazwa());
        else
            code.append(wartosc.toJavaString(indent, false, usedVars, program));

        code.append(";\r\n");

        return code.toString();
    }
}
