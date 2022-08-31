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

public class UniCastPeer {
	static String host = "239.252.10.11";

	static DatagramSocket server;
	
	static public void pedirCoord(int port) {
		System.out.println("Pedir coord");
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

	static public String receberPedidoCoord(int port) {
		try {
			System.out.println("recebepedidocoord");
			if(server==null)
				server = new DatagramSocket(port);
			DatagramPacket recvPacket = new DatagramPacket(new byte[MAXREV], MAXREV);
			server.receive(recvPacket);
			server.close();
			return recvPacket.getData().toString().trim();
		}
		 catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	static public void responderPedidoCoord(int port) {
		System.out.println("Responder Coord");
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

	static public Boolean receberRespostaBully(int port) {
		try {
			System.out.println("BULLYYYYYYYY8");
			DatagramPacket recvPacket = new DatagramPacket(new byte[MAXREV], MAXREV);
			server.setSoTimeout(1000);
			server.receive(recvPacket);
			server.close();
		} catch (SocketTimeoutException e1) {
			return true;
		}
		 catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
