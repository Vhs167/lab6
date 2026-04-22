package lab6.server.commands;

import lab6.common.commands.CommandType;
import lab6.common.dto.Response;
import lab6.common.dto.Request;

/**
 * Интерфейс Command является базовым контрактом поведения для всех команд
 */

public interface Command {
    String getName();

    String getDescription();

    CommandType getCommandType();

    Response execute(Request request);
}
