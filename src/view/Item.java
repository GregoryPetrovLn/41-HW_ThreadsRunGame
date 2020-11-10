package view;

import java.util.function.Consumer;

public interface Item {
    String displayName();

    void perform(InputOutput io);

    public static Item of(String name, Consumer<InputOutput> action, boolean isExit) {
        return new Item() {
            @Override
            public String displayName() {
                return name;
            }

            @Override
            public void perform(InputOutput io) {
                action.accept(io);
            }

            @Override
            public boolean isExit() {
                return isExit;
            }
        };
    }

    public static Item of(String name, Consumer<InputOutput> action) {
        return of(name, action, false);
    }

    public static Item exit() {
        return of("Exit", a -> {
        },true);
    }

    default boolean isExit() {
        return false;
    }

}
