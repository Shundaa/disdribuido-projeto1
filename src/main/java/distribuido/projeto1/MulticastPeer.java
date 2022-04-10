package distribuido.projeto1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Arrays;

public class MulticastPeer {
	static String host = "239.252.10.10";

	static public void pedirRecursoMultcast(int port) {
		MulticastSocket s = null;
		try {
			InetAddress group = InetAddress.getByName(host);
			s = new MulticastSocket(6789);
			s.joinGroup(group);
			byte[] m = String.valueOf(port).getBytes();
			DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 6789);
			s.send(messageOut);
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (s != null)
				s.close();
		}
	}

	static public String escutarMultcast() {
		MulticastSocket s = null;
		DatagramPacket messageIn=null;
		try {
			byte[] buffer = new byte[1000];
			InetAddress group = InetAddress.getByName(host);
			s = new MulticastSocket(6789);
			s.joinGroup(group);
			messageIn = new DatagramPacket(buffer, buffer.length);
			s.receive(messageIn);
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (s != null)
				s.close();
		}
		return new String(messageIn.getData());

	}

	static public void enviarUnicast(int port) {
		byte[] msg = new String(String.valueOf(port)).getBytes();

		DatagramSocket client;
		try {
			client = new DatagramSocket();
			InetAddress inetAddr = InetAddress.getLocalHost();
			SocketAddress socketAddr = new InetSocketAddress(inetAddr, port);
			DatagramPacket sendPacket = new DatagramPacket(msg, msg.length, socketAddr);
			client.send(sendPacket);
			client.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static final int MAXREV = 255;

	static public void receberUnicast(int port) {
		try {
			int i=0;
			DatagramSocket server = new DatagramSocket(port);
			DatagramPacket recvPacket = new DatagramPacket(new byte[MAXREV], MAXREV);

			while (i<2) {
				server.receive(recvPacket);
				i++;
			}
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
