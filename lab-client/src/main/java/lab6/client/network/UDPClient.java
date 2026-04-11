package lab6.client.network;

import lab6.common.dto.Request;
import lab6.common.dto.Response;

import java.io.*;
import java.net.*;
import java.util.Collections;

public class UDPClient {
    private final String host;
    private final int port;
    private final DatagramSocket socket;

    public UDPClient(String host, int port) throws SocketException {
        this.host = host;
        this.port = port;
        this.socket = new DatagramSocket();
        this.socket.setSoTimeout(10000);
    }

    public Response sendRequest(Request request) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(request);
            oos.flush();
            byte[] requestBytes = baos.toByteArray();

            InetAddress inetAddress = InetAddress.getByName(host);
            DatagramPacket packet = new DatagramPacket(requestBytes, requestBytes.length, inetAddress, port);
            socket.send(packet);

            byte[] buffer = new byte[65535];
            DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(responsePacket);

            ByteArrayInputStream bais = new ByteArrayInputStream(responsePacket.getData(), 0, responsePacket.getLength());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Response) ois.readObject();
        } catch (SocketTimeoutException e) {
            return new Response(Collections.emptyList(), "Сервер не отвечает таймаут");
        } catch (IOException e) {
            return new Response(Collections.emptyList(), "Ошибка соединения с сервером");
        } catch (ClassNotFoundException e) {
            return new Response(Collections.emptyList(), "Ошибка при работе сервера");
        }
    }

    public void close() {
        socket.close();
    }
}
