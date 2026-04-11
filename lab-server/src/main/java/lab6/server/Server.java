package lab6.server;


import lab6.server.handlers.RequestHandler;
import lab6.server.managers.*;
import lab6.server.utils.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;
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

    public void start() throws IOException {
        try (DatagramChannel channel = DatagramChannel.open();
            Selector selector = Selector.open()){

            channel.bind(new InetSocketAddress(port));
            channel.configureBlocking(false);

            channel.register(selector, SelectionKey.OP_READ);

            ServerLogger.logger.info("Сервер запущен на порту " + port);

            RequestHandler handler = new RequestHandler(channel, commandManager);
            ByteBuffer buffer = ByteBuffer.allocate(65535);

            while(true) {
                selector.select();

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isReadable()) {
                        DatagramChannel dc = (DatagramChannel) key.channel();

                        buffer.clear();
                        SocketAddress clientAddress = dc.receive(buffer);

                        if (clientAddress != null) {
                            ServerLogger.logger.info("Получен запрос от клиента: " + clientAddress);
                            ServerLogger.logger.info("Пакет принят");

                            handler.handle(buffer, clientAddress);

                            ServerLogger.logger.info("Ответ отправлен клиенту: " + clientAddress);

                        }
                    }
                }
            }
        } catch(IOException e){
            ServerLogger.logger.log(Level.SEVERE, "Ошибка работы сервера, порт: " + port, e);
        }
    }

    public static void main(String[] args) throws IOException{
        Server server = new Server(32493);
        server.start();
    }
}