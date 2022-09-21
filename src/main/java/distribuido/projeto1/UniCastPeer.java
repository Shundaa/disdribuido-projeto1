package distribuido.projeto1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;

public class UniCastPeer {
	static String host = "239.252.10.11";

	static DatagramSocket server;

	static public void pedirCoord(int portDestino,int port) {
		System.out.println("Pedir coord");
		byte[] msg = new String(String.valueOf(port)).getBytes();

		DatagramSocket client;
		try {
			client = new DatagramSocket();
			InetAddress inetAddr = InetAddress.getLocalHost();
			SocketAddress socketAddr = new InetSocketAddress(inetAddr, portDestino);
			DatagramPacket sendPacket = new DatagramPacket(msg, msg.length, socketAddr);
			client.send(sendPacket);
			client.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static public String receberUniCast(int port) {
		try {
			System.out.println("recebepedidocoord");
			server = new DatagramSocket(port);
			server.setSoTimeout(0);
			DatagramPacket recvPacket = new DatagramPacket(new byte[MAXREV], MAXREV);
			server.receive(recvPacket);
			server.close();
			String texto = new String( recvPacket.getData(), "UTF-8");
			System.out.println("Porta recebida: "+ texto.trim());
			return texto.trim();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		return null;
	}

	static public void responderPedidoCoord(int port) {
		System.out.println("Responder Bully");
		byte[] msg = new String("Bully").getBytes();

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

}
