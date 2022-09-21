package distribuido.projeto1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {

	// static Estado estado = null;
	static Scanner ler = new Scanner(System.in);
	static List<String> portList = new ArrayList<String>();
	static String port;
	static String coordenador_eleito;
	static String mensagemRecebidaMultiCast;
	static String mensagemRecebidaUniCast;
	static Boolean eleicao = false;
	static Boolean bully = false;

	public static void main(String[] args) {
		new Thread() {
			@Override
			public void run() {
				portList.add("8081");
				portList.add("8082");
				portList.add("8083");
				portList.add("8084");
				System.out.println("Digite uma porta para unicast Exemplo:  " + portList.toString());
				port = ler.nextLine();
				coordenador_eleito = "9999";
			}
		}.start();

		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(50);
						if (port != null) {
							if (!port.contains(coordenador_eleito)) {
								try {
									mensagemRecebidaMultiCast = MulticastPeer.receberMutiCast().trim();
									if (!mensagemRecebidaMultiCast.contains("Ola")) {
										System.out.println("Novo Coordenador: " + mensagemRecebidaMultiCast);
										coordenador_eleito = mensagemRecebidaMultiCast;
										eleicao = false;
									}

								} catch (Exception e) {
									if (!eleicao){	
										eleicao = true;
										new Thread(inciarEleicao).start();
									}
								}
							} else {
								MulticastPeer.enviarOla();
							}
						}
					} catch (InterruptedException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}

			}
		}.start();

		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(10);
						if (port != null) {
							mensagemRecebidaUniCast = UniCastPeer.receberUniCast(Integer.valueOf(port));
							if (mensagemRecebidaUniCast.contains("Bully")) {
								// sair da eleicao//
								System.out.println("unicast recebido: "+mensagemRecebidaUniCast);
								bully = false;
								System.out.println("unicast eleicao: "+eleicao);
							} else {
								// if (Integer.valueOf(mensagemRecebidaUniCast) < Integer.valueOf(port)) {
								System.out.println(mensagemRecebidaUniCast);
								UniCastPeer.responderPedidoCoord(Integer.valueOf(mensagemRecebidaUniCast));
								// }
							}

						}
					} catch (InterruptedException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

				}
			}
		}.start();
	}

	private static Runnable inciarEleicao = new Runnable() {
		public void run() {
			System.out.println("Coordenador não respondeu");
			coordenador_eleito = "9999";
			System.out.println("Iniciar Eleicao? Para sim digite 1, para nao digite 0");
			// ler.nextLine();
			if (ler.nextInt() == 0) {
				eleicao = false;
				return;
			}
			if (port.contentEquals("8084")) {
				MulticastPeer.enviarIdCoord(Integer.valueOf(port));
				coordenador_eleito = port;
			} else {
				for (int i = 0; i < 4; i++) {
					bully=true;
					if (Integer.valueOf(port) < Integer.valueOf(portList.get(i))
							&& !portList.get(i).equals(coordenador_eleito))
						UniCastPeer.pedirCoord(Integer.valueOf(portList.get(i)), Integer.valueOf(port));
				}
				try {
					Thread.sleep(10000);
				
					System.out.println("sleep recebido: "+eleicao);
				if (eleicao&&bully) {
					MulticastPeer.enviarIdCoord(Integer.valueOf(port));
					coordenador_eleito = port;
				}
				eleicao = false;
			} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};
}
