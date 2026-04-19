package lab6.common.commands;

public enum CommandType {
    ADD("add", 0, true),
    ADD_IF_MIN("add_if_min", 0, true),
    CLEAR("clear", 0, false),
    COUNT_BY_IMPACT_SPEED("count_by_impact_speed", 1, false),
    EXIT("exit", 0, false),
    FILTER_GREATER_THAN_SOUNDTRACK_NAME("filter_greater_than_soundtrack_name", 1, false),
    GROUP_BY_REAL_HERO("group_by_real_hero", 0, false),
    HELP("help", 0, false),
    INFO("info", 0, false),
    REMOVE_BY_ID("remove_by_id", 1, false),
    REMOVE_GREATER("remove_greater", 1, false),
    REMOVE_LOWER("remove_lower", 1, false),
    SAVE("save", 1, false),
    SHOW("show", 0, false),
    UPDATE_BY_ID("update_by_id", 1, true),
    GET_COMMANDS("get_commands", 0, false);

    private final String name;
    private final int argsCount;
    private final boolean requiresObject;

     CommandType(String name, int argsCount, boolean requiresObject){
        this.name = name;
        this.argsCount = argsCount;
        this.requiresObject = requiresObject;
    }

    public String getName(){
         return name;
    }

    public int getArgsCount(){
         return argsCount;
    }

    public boolean getRequiresObject(){
         return requiresObject;
    }

    public static CommandType fromName(String name){
         for (CommandType c : values()){
             if(c.name.equals(name)){
                 return c;
             }
         }
         return null;
    }
}
