package lab6.server;


import lab6.common.dto.Request;
import lab6.common.dto.Response;
import lab6.server.handlers.RequestHandler;
import lab6.server.managers.*;
import lab6.server.network.UDPServer;
import lab6.server.utils.*;

import java.io.IOException;

import java.util.logging.Level;


public class Server {

    private final int port;
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;


    public Server(int port) {
        this.port = port;

        this.collectionManager = new CollectionManager();
        CsvSaver csvSaver = new CsvSaver(collectionManager.getCollection());
        this.commandManager = new CommandManager(collectionManager, csvSaver);

        CsvLoader csvLoader = new CsvLoader(collectionManager);
        csvLoader.loadFromFile();
    }

    public void start() {
        try {
            UDPServer udpServer = new UDPServer(port, 65535);
            RequestHandler requestHandler = new RequestHandler(commandManager);

            ServerLogger.logger.log(Level.INFO, "Сервер запущен на порту: " + port);

            while (true) {
                UDPServer.Packet packet = udpServer.recieve();
                if (packet == null) continue;

                try {
                    Request request = Serializer.deserialize(packet.data);

                    Response response = requestHandler.handle(request);

                    byte[] responseBytes = Serializer.serialize(response);

                    udpServer.send(responseBytes, packet.address);

                } catch (Exception e) {
                    ServerLogger.logger.log(Level.SEVERE, "Ошибка работы сервера", e);
                }
            }
        } catch (IOException e) {
            ServerLogger.logger.log(Level.SEVERE, "Ошибка работы сервера, порт: " + port, e);
        }
    }

    public static void main(String[] args) {
        Server server = new Server(32493);
        server.start();
    }
}