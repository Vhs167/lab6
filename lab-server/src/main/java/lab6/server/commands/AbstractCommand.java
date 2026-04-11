package lab6.server.commands;

public abstract class AbstractCommand implements Command {
    private final String name;
    private final String description;

    /**
     * Класс абстрактной команды. Родительский класс для всех команд.
     */

    public AbstractCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
