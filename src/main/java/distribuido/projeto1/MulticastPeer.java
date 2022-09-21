package distribuido.projeto1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

public class MulticastPeer {
	static String host = "239.252.10.10";

	static public void enviarIdCoord(int port) {
		MulticastSocket s = null;
		try {
			System.out.println("Enviando id coord");
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

	static public void enviarOla() {
		MulticastSocket s = null;
		try {
			InetAddress group = InetAddress.getByName(host);
			s = new MulticastSocket(6789);
			s.joinGroup(group);
			byte[] m = "Ola".getBytes();
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

	static public String receberMutiCast() throws Exception {
		MulticastSocket s = null;
		DatagramPacket messageIn = null;
		try {
//			System.out.println("Receber ola");
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
		String texto = new String( messageIn.getData(), "UTF-8");
		return new String(texto.trim());

	}

}
