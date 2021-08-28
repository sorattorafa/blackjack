/**
 * Este código é responsavel pela parte do cliente e comunicação com o servidor, ele ira tratar o envio o dos dados
 * e o fluxo do jogo.
 *
 * Autores:
 *     @hmarcuzzo (Henrique Marcuzzo)
 *     @sorattorafa (Rafael Soratto)
 *
 * Data de Criação: 23 de Ago de 2021
 * Ultima alteração: 23 de Ago de 2021
*/

import java.rmi.registry.Registry;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;
import java.util.List;

public class Cliente {

    private static String getUsername() {
        // Este método irá coletar o CÓDIGO da disciplina.

        System.out.println("Username: ");
        return new Scanner(System.in).nextLine();
    }

    private static String getPassword() {
        // Este método irá coletar o CÓDIGO da disciplina.

        System.out.println("Password: ");
        return new Scanner(System.in).nextLine();
    }

    private static String showHand(List<Carta> cartas) {
        // Este método irá mostrar as cartas que o jogador possui.

        String message = "";
        for (int i = 0; i < cartas.size(); i++) {
            if (i < cartas.size() - 1) {
                message += cartas.get(i).get_name() + cartas.get(i).get_symbol();
            } else {
                message += cartas.get(i).get_name() + cartas.get(i).get_symbol() + ", ";
            }
        }

        return message;
    }

    private static String reloadScreen(BlackJackManagerRMI bjm, Jogador player, Mesa table) throws RemoteException {
        // Este método irá atualizar a tela do jogo mostrando todas as informações.
        
        String message = "";

        Integer player_cash = bjm.get_player_cash(player.get_nickname());
        message += "-------------------\n";
        message += "Suas informações:\n";
        message += "\tNome: " + player.get_nickname() + "\n";
        message += "\tSaldo: " + player_cash + "\n";
        message += "-------------------\n\n";

        Integer table_cash = bjm.get_table_cash(table.get_id());
        Jogador opponent = bjm.get_oponente(table.get_id(), player.get_nickname());
        message += "-------------------\n";
        message += "Informações da mesa:\n";
        message += "\tApostado: " + table_cash + "\n";
        message += "\tOponentes: " + player.get_nickname() + " vs " + opponent.get_nickname() + "\n";
        message += "-------------------\n\n";

        List<Carta> player_cards = bjm.get_player_cards(table.get_id(), player.get_nickname());
        Integer player_points = bjm.get_player_points(table.get_id(), player.get_nickname());
        String player_status = bjm.get_player_status(table.get_id(), player.get_nickname());
        message += "-------------------\n";
        message += "Suas cartas: " + showHand(player_cards) + " (" + player_points + " pontos)\n";
        message += "Status: " + player_status + "\n";
        message += "-------------------\n\n";

        List<Carta> opponent_cards = bjm.get_player_cards(table.get_id(), opponent.get_nickname());
        String opponent_status = bjm.get_player_status(table.get_id(), opponent.get_nickname());
        message += "-------------------\n";
        message += "Cartas de " + opponent.get_nickname() + ": " + showHand(opponent_cards) + " (" + opponent_status + ")\n";
        message += "Status: " + opponent_status + "\n";
        message += "-------------------\n\n";

        return message;
    }

    private static void play_game(BlackJackManagerRMI bjm, Jogador player, Mesa table) throws IOException, InterruptedException {
        // Este método irá controlar o fluxo do jogo.
        System.out.println("Jogadores emparelhados");
        Thread.sleep(5000);

        while (true) {
            String message = reloadScreen(bjm, player, table);
            Runtime.getRuntime().exec("clear");
            System.out.println(message);

            Integer status = bjm.get_player_table_status(table.get_id(), player.get_nickname());
            // TODO ação do estado
            Thread.sleep(1000);
            break;
        }

        System.out.println("Deseja Revanche (1) ou Sair para o Lobby (2): ");
        Integer decision = Integer.parseInt(new Scanner(System.in).nextLine());

        if (decision == 1) {
            bjm.keep_playing(table.get_id(), player.get_nickname());
            Thread.sleep(10000);
            try {
                Mesa new_table = bjm.keep_current_play(table.get_id());
                play_game(bjm, player, new_table);
            } catch (Exception e) {
                System.out.println("O oponente desistiu! Você está sendo redirecionado para o lobby...");
                Thread.sleep(1000);
                enter_game(bjm, player);
            }
        } else if (decision == 2) {
            enter_game(bjm, player);
        }
    }

    public static void enter_game(BlackJackManagerRMI bjm, Jogador player) throws IOException, InterruptedException {
        // Este método irá iniciar o jogo.

        Runtime.getRuntime().exec("clear");

        System.out.println("Bem-vindo ao cassino Black Jack!");
        System.out.println("Você deseja se juntar a uma mesa (1) ou sair (0): ");
        Integer requestType = Integer.parseInt(new Scanner(System.in).nextLine()); 
        while (requestType > 1|| requestType < 0) {
            Runtime.getRuntime().exec("clear");
            System.out.println("Não conheço este tipo de requisição.\n");
            System.out.println("Você deseja se juntar a uma mesa (1) ou sair (0): ");
            requestType = Integer.parseInt(new Scanner(System.in).nextLine());
        }

        if (requestType == 1) {
            try {
                Mesa table = bjm.join_table(player);
                System.out.println(table.players_list().size());
                while (table.players_list().size() < 2) {
                    Thread.sleep(1000);
                    table = bjm.get_estado_atual_mesa(table);
                }

                play_game(bjm, player, table);
            } catch (Exception e) {
                System.out.println("Não foi possível se juntar a mesa: " + e.getMessage());
                Thread.sleep(2000);
                enter_game(bjm, player);
            }
        } else if (requestType == 0) {
            System.out.println("Obrigado por jogar!");
            System.exit(0);
        }
    }

    public static void main(String args[]) {
        try {
            /* OPCIONAL: configurar e ativar o Security Manager */
            System.setProperty("java.security.policy", "policy.txt");
            System.setSecurityManager(new SecurityManager());

            /* obtem a referencia para o objeto remoto */
            Registry registry = LocateRegistry.getRegistry("localhost");
            BlackJackManagerRMI bjm = (BlackJackManagerRMI)registry.lookup("BlackJackService");
            
            Integer requestType;
            String username, password;

            username = getUsername();
            password = getPassword();

            try {
                Jogador player = bjm.login(username, password);
                enter_game(bjm, player);
            } catch (Exception e) {
                System.out.println("Falha no login: " + e.getMessage());
                return;
            }
        } catch (Exception e) {
            System.out.println("Falha na conexão: " + e.getMessage());
        }
    }
}
