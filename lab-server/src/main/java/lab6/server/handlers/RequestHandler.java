package lab6.server.handlers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

import lab6.common.dto.Response;
import lab6.common.dto.Request;
import lab6.server.managers.CommandManager;
import lab6.server.utils.ServerLogger;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.logging.Level;

public class RequestHandler {

    private final DatagramChannel channel;
    private final CommandManager commandmanager;

    public RequestHandler(DatagramChannel channel, CommandManager commandmanager) {
        this.channel = channel;
        this.commandmanager = commandmanager;
    }

    public void handle(ByteBuffer buffer, SocketAddress clientAddress) {
        try {
            buffer.flip();
            byte[] data = new byte[buffer.limit()];
            buffer.get(data);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            Request request = (Request) ois.readObject();
            Response response = commandmanager.executeCommand(request);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(response);
            oos.flush();
            byte[] responseBytes = baos.toByteArray();

            ByteBuffer sendBuffer = ByteBuffer.wrap(responseBytes);
            channel.send(sendBuffer, clientAddress);
        } catch (Exception e) {
            ServerLogger.logger.log(Level.WARNING, "Ошибка обработки UDP-запроса: ", e);
        }
    }
}
