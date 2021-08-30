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

    public static void clearScreen() {
        // Este método é responsável por limpar a tela
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    private static String getUsername() {
        // Este método irá coletar o CÓDIGO da disciplina.

        System.out.print("Username: ");
        return new Scanner(System.in).nextLine();
    }

    private static String getPassword() {
        // Este método irá coletar o CÓDIGO da disciplina.

        System.out.print("Password: ");
        return new Scanner(System.in).nextLine();
    }

    private static String showHand(List<Carta> cartas) {
        // Este método irá mostrar as cartas que o jogador possui.

        String message = "";
        for (int i = 0; i < cartas.size(); i++) {
            message += cartas.get(i).get_name() + cartas.get(i).get_symbol() + " ";
        }

        return message;
    }

    private static String statusCode_treated(Integer statusCode) {
        // Este método irá tratar o statusCode do servidor.
        
        String message = "";
        switch (statusCode) {
            case 1:
                message = "Venceu!";
                break;
            case 2:
                message = "Perdeu!";
                break;
            case 3:
                message = "Aguardando...";
                break;
            case 4:
                message = "Parou";
                break;
            case 5:
                message = "Sua vez";
                break;
            case 6:
                message = "Empate!";
                break;
        }

        return message;
    }
            
    private static String reloadScreen(BlackJackManagerRMI bjm, Jogador player, Mesa table) throws RemoteException {
        // Este método irá atualizar a tela do jogo mostrando todas as informações.
        
        String message = "";
        Jogador opponent = table.get_opponent(player.get_nickname());

        message += "-------------------\n";
        message += "Suas informações:\n";
        message += "\tNome: " + player.get_nickname() + "\n";
        message += "\tSaldo: " + player.get_cash() + "\n";
        message += "-------------------\n\n";

        message += "-------------------\n";
        message += "Informações da mesa:\n";
        message += "\tApostado: " + table.get_total_cash() + "\n";
        message += "\tOponentes: " + player.get_nickname() + " vs " + opponent.get_nickname() + "\n";
        message += "-------------------\n\n";

        List<Carta> player_cards = table.get_player_cards(player.get_id());
        Integer player_points = table.get_player_points(player_cards);
        Integer player_status = table.get_player_statusCode(player.get_id());
        message += "-------------------\n";
        message += "Suas cartas: " + showHand(player_cards) + " (" + player_points + " pontos)\n";
        message += "Status: " + statusCode_treated(player_status) + "\n";
        message += "-------------------\n\n";

        List<Carta> opponent_cards = table.get_player_cards(opponent.get_id());
        Integer opponent_points = table.get_player_points(opponent_cards);
        Integer opponent_status = table.get_player_statusCode(opponent.get_id());
        message += "-------------------\n";
        message += "Cartas de " + opponent.get_nickname() + ": " + showHand(opponent_cards) + " (" + opponent_points + " pontos)\n";
        message += "Status: " + statusCode_treated(opponent_status) + "\n";
        message += "-------------------\n\n";

        return message;
    }

    private static void play_game(BlackJackManagerRMI bjm, Jogador player, Mesa table) throws IOException, InterruptedException {
        /** Este método irá controlar o fluxo do jogo até que ele termine e ao final irá liberar a mesa no servidor 
         * e redirecionar o jogador o começo do jogo. 
         */
        System.out.println("Jogadores emparelhados");
        Thread.sleep(1500);
        System.out.println("Embaralhando as cartas, aguarde...");
        Object[]  response = bjm.submit_bet(table, player, 100);

        table = (Mesa) response[0];
        player = (Jogador) response[1];

        // Espera a aposta da meda mudar para garantir que os dois jogadores apostaram e estão na mesa.
        while (table.get_total_cash().equals(100)) {
            table = bjm.get_table_status(table);
        }

        Thread.sleep(2500);

        // Inicia o jogo.
        while (true) {
            try {
                String message = reloadScreen(bjm, player, table);
                clearScreen();
                System.out.println(message);
                
                // Pega as informações mais recentes da mesa e do jogador no servidor para tomadas de decisão.
                table = bjm.get_table_status(table);
                player = bjm.update_player_cash(player);

                // Verifica o estado do jogador e executa a ação correspondente.
                Integer status = table.get_player_statusCode(player.get_id());
                if (status.equals(1)) {
                    System.out.println("Parabéns, você venceu $ " + table.get_total_cash());
                    player = bjm.update_player_cash(player);
                    break;

                } else if (status.equals(2)) {
                    System.out.println("Que pena, você perdeu $ " + table.get_total_cash() / 2);
                    player = bjm.update_player_cash(player);
                    break;

                } else if (status.equals(3)) {
                    System.out.println("Aguardando seu oponente...");

                } else if (status.equals(4)) {
                    System.out.println("Aguardando seu oponente terminar...");

                } else if (status.equals(5)) {
                    System.out.print("Você deseja Parar (1) ou Continuar (2): ");
                    Integer requestType = Integer.parseInt(new Scanner(System.in).nextLine());
                    
                    // Informa ao servior a decisão do jogador.
                    bjm.player_decision(player, table, requestType);

                } else if (status.equals(6)) {
                    System.out.println("Ninguém ganhou, ninguém perdeu... Empate!");
                    player = bjm.update_player_cash(player);
                    break;
                }

                Thread.sleep(1000);

                // Pega as informações mais recentes da mesa e do jogador no servidor para mostrar na tela.
                table = bjm.get_table_status(table);
                player = bjm.update_player_cash(player);
                
            } catch (Exception e) {
                System.out.println("Erro: " + e);
                Thread.sleep(5000);
            }
        }

        System.out.print("Sair para o Lobby (1): ");
        Integer decision = Integer.parseInt(new Scanner(System.in).nextLine());

        // Informa ao servidor que pode liberar a mesa.
        bjm.finish_table(table);

        // Retorna para o lobby.
        enter_game(bjm, player);
    }

    public static void enter_game(BlackJackManagerRMI bjm, Jogador player) throws IOException, InterruptedException {
        // Este método trata de iniciar o jogo até a alocar o jogador em uma mesa.

        clearScreen();

        System.out.println("Bem-vindo ao cassino Black Jack!");
        System.out.print("Você deseja se juntar a uma mesa (1) ou sair (0): ");
        Integer requestType = Integer.parseInt(new Scanner(System.in).nextLine()); 
        while (requestType > 1|| requestType < 0) {
            clearScreen();
            System.out.println("Não conheço este tipo de requisição.");
            System.out.print("Você deseja se juntar a uma mesa (1) ou sair (0): ");
            requestType = Integer.parseInt(new Scanner(System.in).nextLine());
        }

        if (requestType == 1) {
            try {
                if(player.get_cash() < 100) {
                    throw new Exception("Você não possui saldo suficiente para jogar!");
                }

                Mesa table = bjm.join_table(player);
                System.out.println("Procurando por jogadores. Aguarde...");
                while (table.players_list().size() < 2) {
                    Thread.sleep(1000);
                    // Pega o estado atual da mesa para checar se houve alguma mudança.
                    table = bjm.get_table_status(table);
                }

                // Inicia o jogo com os jogadores emparelhados.
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
