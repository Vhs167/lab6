package lab6.client.network;

import lab6.common.dto.Request;
import lab6.common.dto.Response;

import java.io.*;
import java.net.*;
import java.util.Collections;

public class UDPClient {
    private final InetAddress inetAddress;
    private final int port;
    private final DatagramSocket socket;

    public UDPClient(String host, int port) throws UnknownHostException, SocketException {
        this.inetAddress = InetAddress.getByName(host);
        this.port = port;
        this.socket = new DatagramSocket();
        this.socket.setSoTimeout(2000);
    }

    public Response sendRequest(Request request) {
        try {
            byte[] requestBytes;
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(baos)) {

                oos.writeObject(request);
                oos.flush();
                requestBytes = baos.toByteArray();
            }

            DatagramPacket packet = new DatagramPacket(requestBytes, requestBytes.length, inetAddress, port);
            socket.send(packet);

            byte[] buffer = new byte[65535];
            DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(responsePacket);

            try (ByteArrayInputStream bais = new ByteArrayInputStream(responsePacket.getData(), 0, responsePacket.getLength());
                 ObjectInputStream ois = new ObjectInputStream(bais)) {
                return (Response) ois.readObject();
            }

        } catch (SocketTimeoutException e) {
            return new Response(Collections.emptyList(), "Сервер не отвечает таймаут");
        } catch (IOException e) {
            return new Response(Collections.emptyList(), "Ошибка соединения с сервером");
        } catch (ClassNotFoundException e) {
            return new Response(Collections.emptyList(), "Ошибка при работе сервера");
        }
    }
}
