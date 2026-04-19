package lab6.common.dto;

import java.io.Serializable;

import lab6.common.commands.CommandType;

public class CommandInfo implements Serializable {
    private static final long serialVersionUID = 5L;

    private String name;
    private int argCount;
    private boolean requiresObject;

    public CommandInfo(CommandType type){
        this.name = type.getName();
        this.argCount = type.getArgsCount();
        this.requiresObject = type.getRequiresObject();
    }

    public String getName(){
        return name;
    }

    public int getArgCount() {
        return argCount;
    }

    public boolean isRequiresObject() {
        return requiresObject;
    }
}
