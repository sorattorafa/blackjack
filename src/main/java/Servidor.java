/**
 * Inicializa o servidor com o RMI
 *
 * Autores:
 *     @hmarcuzzo (Henrique Marcuzzo)
 *     @sorattorafa (Rafael Soratto)
 *
 * Data de Criação: 27 de Ago de 2021
 * Ultima alteração: 29 de Ago de 2021
*/
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class Servidor {
    public static void main(String args[]) {
       try {
            /* inicializa um objeto remoto */
            BlackJackManager blackJackManager = new BlackJackManager();

            /* registra o objeto remoto no Binder */
            Registry registry = LocateRegistry.getRegistry("localhost");
            registry.rebind("BlackJackService", blackJackManager);

            /* aguardando invocacoes remotas */
	        System.out.println("Server ready ...");
	    } catch (Exception e) {
	        System.out.println(e);
        } //catch
    } //main
} //Servidor