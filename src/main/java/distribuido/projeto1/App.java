package distribuido.projeto1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
	static Estado estado = Estado.RELEASED;
	static byte[] buffer = new byte[1000];
	static String host = "239.252.10.10";
	static int i=0;
	static Scanner ler = new Scanner(System.in);
	static List<String> portList = new ArrayList<String>();

	public static void main(String[] args) {
		new Thread() {
			@Override
			public void run() {
				System.out.println("Digite uma porta para unicast Exemplo:[8081,8082,8083] ");
				String port = ler.nextLine();
				while (true) {
					estado = Interface.printInterface(estado);
					if(estado==Estado.WANTED) {
						MulticastPeer.pedirRecursoMultcast(Integer.valueOf(port));
						System.out.println("\nEstado: "+estado.getDescricao());
						MulticastPeer.receberUnicast(Integer.valueOf(port));
						estado=Estado.HELD;
					}
				}
			}
		}.start();
		
		new Thread() {
			@Override
			public void run() {
				while (true) {
					portList.add(MulticastPeer.escutarMultcast().trim());
				}
			}
		}.start();

		new Thread() {
			@Override
			public void run() {
				while (true) {
					if(estado==Estado.RELEASED && !portList.isEmpty()) {
						MulticastPeer.enviarUnicast(Integer.valueOf(portList.get(0)));
						portList.remove(0);
					}
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}
		}.start();
	}
}
