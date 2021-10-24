import com.google.gson.*;
import commands.*;
import commands.Number;
import util.InvalidProgramException;
import java.lang.reflect.Type;

public class CommandAdapter implements JsonDeserializer<Command> {

    @Override
    public Command deserialize(JsonElement jsonElement, Type type,
                               JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String commandType = jsonObject.get("typ").getAsString();

        try {
            return switch (commandType) {
                case "Blok" -> jsonDeserializationContext.deserialize(jsonElement, Block.class);
                case "If" -> jsonDeserializationContext.deserialize(jsonElement, If.class);
                case "While" -> jsonDeserializationContext.deserialize(jsonElement, While.class);
                case "Przypisanie" -> jsonDeserializationContext.deserialize(jsonElement, Assignment.class);
                case "Plus", "Minus", "Razy", "Dzielenie" -> jsonDeserializationContext.deserialize(jsonElement, NumericalOperation.class);
                case "And" -> jsonDeserializationContext.deserialize(jsonElement, And.class);
                case "Or" -> jsonDeserializationContext.deserialize(jsonElement, Or.class);
                case "<", ">", "<=", ">=", "==" -> jsonDeserializationContext.deserialize(jsonElement, LogicOperation.class);
                case "Not" -> jsonDeserializationContext.deserialize(jsonElement, Not.class);
                case "Liczba" -> jsonDeserializationContext.deserialize(jsonElement, Number.class);
                case "True", "False" -> jsonDeserializationContext.deserialize(jsonElement, LogicValue.class);
                case "Zmienna" -> jsonDeserializationContext.deserialize(jsonElement, Variable.class);
                default -> throw new IllegalStateException("Unexpected value: " + commandType);
            };
        }
        catch (IllegalStateException e) {
            try {
                throw new InvalidProgramException("Niepoprawny typ");
            } catch (InvalidProgramException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }
}
