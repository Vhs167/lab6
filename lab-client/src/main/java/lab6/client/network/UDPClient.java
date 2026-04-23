package lab6.client.network;

import lab6.common.dto.Chunk;
import lab6.common.dto.Request;
import lab6.common.dto.Response;
import lab6.common.network.ChunkBuffer;
import lab6.common.network.ChunkUtils;

import java.io.*;
import java.net.*;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static lab6.common.utils.Serializer.deserialize;
import static lab6.common.utils.Serializer.serialize;

public class UDPClient {
    private final InetAddress inetAddress;
    private final int port;
    private final DatagramSocket socket;
    private final ChunkBuffer buffer = new ChunkBuffer();

    public UDPClient(String host, int port) throws UnknownHostException, SocketException {
        this.inetAddress = InetAddress.getByName(host);
        this.port = port;
        this.socket = new DatagramSocket();
        this.socket.setSoTimeout(10000);
    }

    public Response sendRequest(Request request) {
        try {
            UUID id = UUID.randomUUID();

            byte[] data = serialize(request);

            List<Chunk> chunks = ChunkUtils.split(data, id);

            for (Chunk c : chunks) {
                byte[] bytes = serialize(c);

                DatagramPacket packet = new DatagramPacket(bytes, bytes.length, inetAddress, port);
                socket.send(packet);
            }

            while (true) {

                byte[] bufferArr = new byte[65535];
                DatagramPacket responcePacket = new DatagramPacket(bufferArr, bufferArr.length);

                socket.receive(responcePacket);

                Chunk chunk = deserialize(responcePacket.getData(), responcePacket.getLength());

                buffer.add(chunk);

                if (!buffer.isComplete(chunk.getId())) {
                    continue;
                }

                List<Chunk> full = buffer.take(chunk.getId());

                byte[] responseData = ChunkUtils.assemble(full);

                return deserialize(responseData);
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
