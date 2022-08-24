package distribuido.projeto1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class MulticastPeer {
	static String host = "239.252.10.10";

	static public void enviarIdCoord(int port) {
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
	static public String enviarOla() {
		return null;
	}

	static public String receberOla() throws Exception{
		return null;
	}
	static public String receberCoord() throws Exception{
		MulticastSocket s = null;
		DatagramPacket messageIn=null;
		try {
			byte[] buffer = new byte[1000];
			InetAddress group = InetAddress.getByName(host);
			s = new MulticastSocket(6789);
			s.joinGroup(group);
			s.setSoTimeout(1000);
			messageIn = new DatagramPacket(buffer, buffer.length);
			s.receive(messageIn);
		} finally {
			if (s != null)
				s.close();
		}
		return new String(messageIn.getData());

	}
}
