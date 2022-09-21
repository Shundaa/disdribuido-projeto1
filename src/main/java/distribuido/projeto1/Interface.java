package distribuido.projeto1;

import java.util.Scanner;

public class Interface {
	static Scanner ler = new Scanner(System.in);
	static public Estado printInterface(Estado estado) {
		{
			if (estado == Estado.NORMAL) {
				System.out.println("\nEstado: "+estado.getDescricao());
				System.out.println("Digite 1 para pedir o Recurso: ");
				String n = ler.nextLine();
				if (Integer.valueOf(n) == 1) {
					estado = Estado.NORMAL;
				}
			}
			else if (estado == Estado.NORMAL) {
				System.out.println("\nEstado: "+estado.getDescricao());
				System.out.println("Digite 2 para liberar o Recurso: ");
				String n = ler.nextLine();
				if (Integer.valueOf(n) == 2) {
					estado = Estado.NORMAL;
				}
			}
			return estado;
		}
	}
}
