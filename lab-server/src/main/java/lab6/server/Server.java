package lab6.server;


import lab6.common.dto.Chunk;
import lab6.common.dto.Request;
import lab6.common.dto.Response;
import lab6.common.network.ChunkBuffer;
import lab6.common.network.ChunkUtils;
import lab6.server.handlers.RequestHandler;
import lab6.server.managers.*;
import lab6.server.network.UDPServer;
import lab6.server.utils.*;

import java.io.IOException;

import java.util.List;
import java.util.UUID;
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
            ChunkBuffer buffer = new ChunkBuffer();

            ServerLogger.logger.log(Level.INFO, "Сервер запущен на порту: " + port);

            while (true) {
                UDPServer.Packet packet = udpServer.recieve();
                if (packet == null) continue;

                try {
                    Chunk chunk = lab6.common.utils.Serializer.deserialize(packet.data);

                    buffer.add(chunk);

                    if (!buffer.isComplete(chunk.getId())) {
                        continue;
                    }

                    List<Chunk> chunks = buffer.take(chunk.id);

                    byte[] fullData = ChunkUtils.assemble(chunks);

                    Request request = lab6.common.utils.Serializer.deserialize(fullData);

                    Response response = requestHandler.handle(request);

                    UUID responseId = UUID.randomUUID();

                    byte[] responseBytes = lab6.common.utils.Serializer.serialize(response);

                    List<Chunk> responseChunks = ChunkUtils.split(responseBytes, responseId);

                    for (Chunk c : responseChunks) {
                        byte[] data = lab6.common.utils.Serializer.serialize(c);
                        udpServer.send(data, packet.address);
                    }

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