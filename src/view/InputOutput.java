package view;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

public interface InputOutput {
    String readString(String prompt);

    void writeObject(Object obj);

    //============ default methods ============//


    default void writeLn(Object obj) {
        writeObject(obj + "\n");
    }

    default <R> R readObject(String prompt, String errorPrompt, Function<String, R> mapper) {
        while (true) {
            String string = readString(prompt);

            try {
                R result = mapper.apply(string);
                return result;
            } catch (Exception e) {
                writeLn(errorPrompt);
            }

        }

    }


    default Integer readInteger(String prompt) {
        return readObject(prompt, "It's not a Integer number", Integer::parseInt);
    }

    default Integer readInteger(String prompt, int min, int max) {
        return readObject(prompt,
                String.format("It's not a number in the range : [%s - %s]", min, max),
                s -> {
                    int num = Integer.parseInt(s);
                    if (num < min || num > max) {
                        throw new NumberFormatException();
                    }
                    return num;
                });
    }

}
