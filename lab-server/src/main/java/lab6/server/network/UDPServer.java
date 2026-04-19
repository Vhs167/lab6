package lab6.server.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UDPServer {

    private final DatagramChannel channel;
    private final ByteBuffer buffer;

    public UDPServer(int port, int bufferSize) throws IOException {
        this.channel = DatagramChannel.open();
        this.channel.bind(new InetSocketAddress(port));
        this.channel.configureBlocking(false);
        this.buffer = ByteBuffer.allocate(bufferSize);
    }

    public Packet recieve() throws IOException {
        buffer.clear();

        SocketAddress clientAddress = channel.receive(buffer);

        if (clientAddress == null) {
            return null;
        }

        buffer.flip();

        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);

        return new Packet(clientAddress, data);
    }

    public void send(byte[] data, SocketAddress address) throws IOException {
        channel.send(ByteBuffer.wrap(data), address);
    }

    public void close() throws IOException {
        channel.close();
    }

    public static class Packet {
        public final SocketAddress address;
        public final byte[] data;

        public Packet(SocketAddress address, byte[] data) {
            this.address = address;
            this.data = data;
        }
    }
}
