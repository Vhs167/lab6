package lab6.client;

import lab6.client.managers.IOManager;
import lab6.client.factory.RequestBuilder;
import lab6.client.network.UDPClient;

import lab6.common.dto.Response;
import lab6.common.dto.Request;
import lab6.common.models.HumanBeing;
import lab6.common.utils.Validator;


import java.io.FileNotFoundException;
import java.net.SocketException;
import java.net.UnknownHostException;


public final class Client {

    private final IOManager ioManager;

    private Client(IOManager ioManager) {
        this.ioManager = ioManager;
    }

    public void start() {
        UDPClient udpClient;

        try {
            udpClient = new UDPClient("localhost", 32493);
        } catch (UnknownHostException e) {
            ioManager.println("Неизвестный хост: " + e.getMessage());
            return;
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        RequestBuilder requestBuilder = new RequestBuilder(ioManager);

        while (true) {
            Request request = requestBuilder.buildRequest();

            if (request == null) {
                continue;
            }

            Validator.validate(request);

            String command = request.getCommandName();

            if (command.equals("exit")) {
                ioManager.println("Завершение работы");
                break;
            }

            if (command.equals("execute_script")) {
                String fileName = request.getArgs()[0];

                if (ioManager.getScripts().contains(fileName)) {
                    ioManager.printError("Ошибка: попытка повторного запуска скрипта " + fileName);
                    continue;
                }

                try {
                    ioManager.setFileInput(fileName);
                    ioManager.setCurrentScript(fileName);
                    ioManager.println("Выполение скрипта: " + fileName);
                } catch (FileNotFoundException e) {
                    ioManager.println("Файл не найден: " + fileName);
                }

                continue;
            }

            Response response = udpClient.sendRequest(request);

            if (response != null) {
                ioManager.println(response.getMessage());

                if (response.getCollection() != null) {
                    for (HumanBeing human : response.getCollection()) {
                        ioManager.println(human);
                    }
                }
            } else {
                ioManager.println("Нет ответа от сервера");
            }
        }
    }


    public static void main(String[] args) {
        IOManager ioManager = new IOManager();
        Client client = new Client(ioManager);
        client.start();
    }
}
