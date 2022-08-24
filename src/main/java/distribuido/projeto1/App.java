package distribuido.projeto1;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {

	static Estado estado = Estado.NORMAL;
	static Scanner ler = new Scanner(System.in);
	static List<String> portList = new ArrayList<String>();
	static String port;
	static String coordenador_eleito;
	public static void main(String[] args) {
		new Thread() {
			@Override
			public void run() {
				portList.add("8081");
				portList.add("8082");
				portList.add("8083");
				portList.add("8084");
				System.out.println("Digite uma porta para unicast Exemplo:[8081,8082,8083,8084] ");
				port = ler.nextLine();
				while (true) {
					if(estado == Estado.ELEICAO){
						
					}
					estado = Interface.printInterface(estado);
					if (estado == Estado.WANTED) {
						MulticastPeer.pedirRecursoMultcast(Integer.valueOf(port));
						System.out.println("\nEstado: " + estado.getDescricao());
						MulticastPeer.receberUnicast(Integer.valueOf(port));
						estado = Estado.HELD;
					}
				}
			}
		}.start();

		new Thread() {
			@Override
			public void run() {
				while (true) {
					if(estado == Estado.NORMAL){
						try{
							MulticastPeer.receberOla();
						}catch(SocketTimeoutException e){
							estado = Estado.ELEICAO;
						}catch(Exception e1){
							System.out.println("Erro");
						}
					}
					if(estado == Estado.ELEICAO){
						for (int i=0;i<4;i++) {
							if(Integer.valueOf(port) < Integer.valueOf(portList.get(i)) && !portList.get(i).equals(coordenador_eleito))
								UniCastPeer.pedirCoord(Integer.valueOf(port));
								if(UniCastPeer.receberRespostaBully(Integer.valueOf(port))){
									if(port !=null && port.equals(coordenador_eleito) && estado != Estado.COORD){
										MulticastPeer.enviarIdCoord(Integer.valueOf(port));
										estado = Estado.COORD;
									}
								}
								else{
									estado = Estado.NORMAL;
								}
						}
					}
				}
			}
		}.start();

		new Thread() {
			@Override
			public void run() {
				while (true) {
					if(estado==Estado.COORD){
						MulticastPeer.enviarOla();
					}
				}
			}
		}.start();
	}
}
