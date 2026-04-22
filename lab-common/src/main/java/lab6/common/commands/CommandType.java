package lab6.common.commands;

public enum CommandType {
    ONE_ARG(1, false),
    NO_ARG(0, false),
    WITH_OBJECT(0, true),
    ONE_ARG_WITH_OBJECT(1, true);


    private final int argsCount;
    private final boolean requiresObject;

    CommandType(int argsCount, boolean requiresObject) {
        this.argsCount = argsCount;
        this.requiresObject = requiresObject;
    }


    public int getArgsCount() {
        return argsCount;
    }

    public boolean getRequiresObject() {
        return requiresObject;
    }

}
