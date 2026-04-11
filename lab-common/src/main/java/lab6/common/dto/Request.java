package lab6.common.dto;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String commandName;
    private final HumanBeingRequest humanBeingRequest;
    private final String[] args;

    public Request(String commandName, HumanBeingRequest humanBeingRequest, String[] args) {
        this.commandName = commandName;
        this.humanBeingRequest = humanBeingRequest;
        this.args = args;
    }

    public String getCommandName() {
        return commandName;
    }

    public HumanBeingRequest getHumanBeingRequest() {
        return humanBeingRequest;
    }

    public String[] getArgs() {
        return args;
    }
}
