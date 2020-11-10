package view;

import java.util.*;

public class ConsoleInputOutput implements InputOutput {
    Scanner scanner = new Scanner(System.in);

    @Override
    public String readString(String prompt) {
        writeLn(prompt + ">");
        String result = scanner.nextLine();
        return result;
    }

    @Override
    public void writeObject(Object obj) {
        System.out.print(obj);
    }
}
