package view;

import java.util.*;

public class Menu implements Item {
    String name;
    ArrayList<Item> items;

    public Menu(String name, ArrayList<Item> items) {
        super();
        this.name = name;
        this.items = items;
    }


    @Override
    public String displayName() {
        return name;
    }

    @Override
    public void perform(InputOutput io) {
        while (true) {
            displayMenu(io);
            int selectedItem = io.readInteger("Enter item number ", 1, items.size());
            Item item = items.get(selectedItem - 1);
            try {
                item.perform(io);
                if(item.isExit()) {
                    break;
                }
            } catch (Exception e) {
                io.writeLn(e.getMessage());
            }
        }

    }

    private void displayMenu(InputOutput io) {
        io.writeLn("=".repeat(23));
        io.writeLn("\t" + name);
        io.writeLn("=".repeat(23));

        int nItems = items.size();

        for (int i = 1; i <= nItems; i++) {
            io.writeLn(i + "." + items.get(i-1).displayName());
        }
    }
}
