/**
 * Esta classe é o controlador do jogo, tudo que o cliente necessitar fazer que altere algum estado do jogo 
 * deve submeter ao servidor que invocará métodos desta classe.
 *
 * Autores:
 *     @hmarcuzzo (Henrique Marcuzzo)
 *     @sorattorafa (Rafael Soratto)
 *
 * Data de Criação: 27 de Ago de 2021
 * Ultima alteração: 29 de Ago de 2021
*/

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class BlackJackManager extends UnicastRemoteObject implements BlackJackManagerRMI {

    List<Mesa> mesas = new ArrayList<Mesa>();
    Connection db_connection = SQLiteConnection.connect(); 

    public BlackJackManager() throws RemoteException {
        super();
        System.out.println("Instance of Object created with success");
    }

    @Override
    public Jogador login(String nickname, String password) throws RemoteException {
        /* Método resposável pelo login do jogador se ele estiver cadastrado no banco de dados. 
        Caso contrário, criar um novo usuário. */
        Jogador jogador;
        String search_player_query = "SELECT J.nickname, J.id, J.cash FROM jogador AS J WHERE J.nickname IS \"" + String.valueOf(nickname) + "\" AND J.password IS \"" + String.valueOf(password) + "\";";
        
        try {
            Statement statement = db_connection.createStatement();
            /* Procura se o jogador já está cadastrado no banco de dados */
            ResultSet resultSet = statement.executeQuery(search_player_query);
            if (!resultSet.isBeforeFirst()) {
                /* Jogador não foi encontrado, criar novo usuário */
                
                String create_jogador = "INSERT INTO jogador (nickname, password) VALUES ( '" + nickname + "', '" + password + "');"; 
                statement.execute(create_jogador);
    
                ResultSet resultSet2 = statement.executeQuery(search_player_query);
                if (!resultSet2.isBeforeFirst()) {
                    throw new RemoteException(" Falha ao cadastrar jogador");
                } else {
                    String nickname_user = resultSet2.getString("nickname");
                    int id = resultSet2.getInt("id");
                    int cash = resultSet2.getInt("cash");
                    jogador = new Jogador(nickname_user, id, cash);
                }
                 
            } else{
                /* Jogador já está cadastrado no banco de dados, retornar usuário cadastrado */
                String nickname_user = resultSet.getString("nickname");
                int id = resultSet.getInt("id");
                int cash = resultSet.getInt("cash");
                jogador = new Jogador(nickname_user, id, cash);
            }
            
            
        } catch (SQLException e) {
            System.out.println(e);
            try {
                Statement statement = db_connection.createStatement();
                String create_jogador = "INSERT INTO jogador (nickname, password) VALUES (" + nickname + ", " + password + ");"; 
                statement.execute(create_jogador);
                ResultSet resultSet2 = statement.executeQuery(search_player_query);
                if (!resultSet2.isBeforeFirst()) {
                    throw new RemoteException(" Falha ao cadastrar jogador ");
                } else {
                     String nickname_user = resultSet2.getString("nickname");
                    int id = resultSet2.getInt("id");
                    int cash = resultSet2.getInt("cash");
                    jogador = new Jogador(nickname_user, id, cash);
                }
            } catch(SQLException e2) {
                throw new RemoteException(" Falha ao cadastrar jogador");
            }
        }
        return jogador;
    }

    @Override
    public Mesa join_table(Jogador jogador) throws RemoteException {  
        /* Método responsável por encontrar uma mesa para jogar ou criar uma se não tiver. */
        Mesa mesa = new Mesa();
        boolean found = false;

        /* Se ouver mesas na lista, verificar se alguma necessita de jogador */
        if (mesas.size() > 0) {
            for (Mesa mesa_i : mesas) {
                if (mesa_i.players_list().size() == 1) {
                    found = true;
                    mesa_i.add_player(jogador);
                    Integer player_id = jogador.get_id();
                    MaoJogador mao_jogador = new MaoJogador(player_id);
                    StatusJogador status_jogador = new StatusJogador(player_id, 3);

                    /* 1a carta do player que entrou na mesa */  
                    Baralho baralho = mesa_i.get_baralho();
                    Carta player_card = baralho.draw_card();
                    mao_jogador.add_player_hand_carta(player_card);

                    /* 2a carta do player que iniciou a mesa */
                    MaoJogador mao_oponente = mesa_i.get_opponent_hand(jogador.get_nickname());
                    player_card = baralho.draw_card();
                    mao_oponente.add_player_hand_carta(player_card);

                    /* 2a carta do player que entrou na mesa */ 
                    player_card = baralho.draw_card();
                    mao_jogador.add_player_hand_carta(player_card);
                    
                    mesa_i.add_mao_jogador(mao_jogador);
                    mesa_i.add_player_status(status_jogador);
                    mesa = mesa_i;
                    break;
                }
            }  
        } 

        /* Se não tiver mesas, criar uma nova mesa */
        if (!found) {
            System.out.println("Creating new table");
            try {
                Integer player_id = jogador.get_id();
                MaoJogador mao_jogador = new MaoJogador(player_id);
                StatusJogador status_jogador = new StatusJogador(player_id, 5);
                int total_mesas = mesas.size();

                /* Mesa setters*/
                mesa.set_id(total_mesas);
                mesa.set_total_cash(0);
                mesa.set_baralho(new Baralho());
                mesa.add_player(jogador);
                
                /* 1a carta do player que iniciou a mesa */ 
                Baralho baralho = mesa.get_baralho();
                Carta player_card = baralho.draw_card();
                mao_jogador.add_player_hand_carta(player_card);

                mesa.add_mao_jogador(mao_jogador);
                mesa.add_player_status(status_jogador); 

                mesas.add(mesa);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return mesa;
    }

    @Override
    public Mesa get_table_status(Mesa mesa) throws RemoteException {
        // Este método é resposável por retornar a mesa com as informações atualizadas para o cliente.
        for (Mesa mesa_i : mesas) {
            if (mesa_i.get_id().equals(mesa.get_id())) {
                return mesa_i;
            }
        }
        
        return null;
    }

    @Override
    public Object[] submit_bet(Mesa mesa, Jogador jogador, int valor) throws RemoteException {
        // Este método é resposável por submeter a aposta do jogador à mesa.
        for (Mesa mesa_i : mesas) {
            if (mesa_i.get_id().equals(mesa.get_id())) {
                // Retira o valor da aposta do jogador e atualiza o valor na mesa.
                jogador.set_cash(jogador.get_cash() - valor);
                mesa_i.set_total_cash(mesa_i.get_total_cash() + valor);
                String update_saldo = "UPDATE jogador SET cash = " + jogador.get_cash()  + " WHERE (id = " + jogador.get_id() +");";
                try {
                    Statement statement = db_connection.createStatement();
                    statement.execute(update_saldo);
                } catch(SQLException e) {
                    throw new RemoteException(" 404 - jogador not  found ");
                }
                
                return new Object[] {mesa_i, jogador};
            }
        }


        return null;
    }

    @Override
    public void player_decision(Jogador jogador, Mesa mesa, Integer requestType) throws RemoteException {
        /* Este método é responsável por lidar com a decisão tomada pelo jogador e suas implicações */

        /* Obter as informações mais recentes da mesa */
        mesa = get_table_status(mesa);

        Jogador oponent = update_player_cash(mesa.get_opponent(jogador.get_nickname()));
        StatusJogador status_jogador = mesa.get_players_status(jogador.get_id());
        StatusJogador status_jogador_oponente = mesa.get_players_status(oponent.get_id());

        List<Carta> player_cards = mesa.get_player_cards(jogador.get_id());
        List<Carta> oppenent_cards = mesa.get_player_cards(oponent.get_id());
        boolean empate = false;

        // Lógica para lidar com a decisão do jogador
        if (requestType == 1) {
            status_jogador.set_player_status(4);

            if (status_jogador_oponente.get_player_status() == 3) status_jogador_oponente.set_player_status(5);
            else if (status_jogador_oponente.get_player_status() == 4) {
                Integer player_points = mesa.get_player_points(player_cards);

                Integer oppenent_points = mesa.get_player_points(oppenent_cards);

                if (player_points == oppenent_points) {
                    status_jogador.set_player_status(6);
                    status_jogador_oponente.set_player_status(6);
                    empate = true;

                } else if (player_points > oppenent_points) {
                    status_jogador.set_player_status(1);
                    status_jogador_oponente.set_player_status(2);

                } else {
                    status_jogador.set_player_status(2);
                    status_jogador_oponente.set_player_status(1);

                }
            }

        } else if (requestType == 2) {
            MaoJogador mao_jogador = mesa.get_mao_jogador(jogador.get_id());

            Baralho baralho = mesa.get_baralho();
            Carta player_card = baralho.draw_card();
            mao_jogador.add_player_hand_carta(player_card);

            Integer player_points = mesa.get_player_points(player_cards);

            if (player_points > 21) {
                status_jogador.set_player_status(2);
                status_jogador_oponente.set_player_status(1);

            } else {
                if (status_jogador_oponente.get_player_status() == 3) {
                    status_jogador.set_player_status(3);
                    status_jogador_oponente.set_player_status(5);
                } else if (status_jogador_oponente.get_player_status() == 4) {
                    status_jogador.set_player_status(5);
                }
            }
        }
        
        Integer perdedor_id = null;
        Integer ganhador_id = null;
        
        // Verifica se há ganhador e perdedor
        if(status_jogador.get_player_status() == 1 && status_jogador_oponente.get_player_status() == 2){
            ganhador_id = jogador.get_id();
            perdedor_id =  oponent.get_id();

        } else if (status_jogador.get_player_status() == 2 && status_jogador_oponente.get_player_status() == 1) {
            ganhador_id = oponent.get_id();
            perdedor_id =  jogador.get_id();
        }

        /* Se houver ganhador, perdedor ou se houve empate atualiza o saldo dos jogadores, 
        salva a partida no banco e atualiza o status da mesa para finalizada. */
        if(perdedor_id != null && ganhador_id != null) {

            try {

                Statement statement = db_connection.createStatement();
                Integer saldo_ganhador;

                if ( perdedor_id == oponent.get_id() && ganhador_id == jogador.get_id()) {
                    saldo_ganhador = jogador.get_cash() + mesa.get_total_cash();
                } else {
                    saldo_ganhador = oponent.get_cash() + mesa.get_total_cash();
                }

                System.out.print(saldo_ganhador + " = saldo ganhador, " + jogador.get_cash() + " = jogador_get_cash, " + oponent.get_cash() + " oponent_cash ," + ganhador_id + " = ganhador_id" );

                String update_saldo_ganhador = "UPDATE jogador SET cash = " + saldo_ganhador + " WHERE (id = " + ganhador_id +");";
                String create_partida = "INSERT INTO partida (perdedor_id, ganhador_id, total_cash) VALUES ( '" + perdedor_id + "', '" + ganhador_id + "', '" +  mesa.get_total_cash() + "');";

                statement.execute(create_partida);
                statement.execute(update_saldo_ganhador);
                mesa.set_is_finished(true);
                
            } catch (SQLException e){
                System.out.print(e);
                throw new RemoteException(" Falha ao salvar os dados da partida no banco de dados");
            }
        } else if(empate){
         
            try {
                String create_partida = "INSERT INTO partida (perdedor_id, ganhador_id, total_cash) VALUES ( '" + jogador.get_id() + "', '" + jogador.get_id() + "', '" +  mesa.get_total_cash() + "');";
                Statement statement = db_connection.createStatement();

                String update_saldo_jogador  = "UPDATE jogador SET cash = " + (jogador.get_cash() + mesa.get_total_cash()/2)  + " WHERE (id = " + jogador.get_id() +");";
                String update_saldo_oponent  = "UPDATE jogador SET cash = " + (oponent.get_cash() + mesa.get_total_cash()/2)  + " WHERE (id = " + oponent.get_id() +");";
                
                statement.execute(update_saldo_jogador);
                statement.execute(update_saldo_oponent);
                statement.execute(create_partida);
                mesa.set_is_finished(true);
            } catch (SQLException e){
                System.out.print(e);
                throw new RemoteException(" Falha ao salvar os dados da partida no banco de dados");
            }
        }
    }

    @Override
    public Jogador update_player_cash(Jogador jogador) throws RemoteException {
        // Atualiza o saldo do jogador de acordo com a informação do banco de dados.
        String search_player_query = "SELECT cash FROM jogador WHERE nickname IS \"" + jogador.get_nickname() + "\";";
        try {
            Statement statement = db_connection.createStatement();
            statement.executeQuery(search_player_query);
            
            /* search for disiplina */
            ResultSet resultSet = statement.executeQuery(search_player_query);
            if (!resultSet.isBeforeFirst()) {
                throw new RemoteException(" 404 - jogador not  found ");
            } else {
                Integer cash = resultSet.getInt("cash");
                jogador.set_cash(cash);
            }

        } catch(SQLException e) {
            throw new RemoteException(" 404 - jogador not  found ");
        }
        return jogador;
    }

    @Override
    public void finish_table(Mesa mesa) throws RemoteException {
        // Finaliza uma mesa e libera o espaço na memória do servidor.
        if(mesas.isEmpty()) return;
        else if(!mesas.contains(mesa)) return;
        else if(mesa.get_is_finished()){
            mesas.remove(mesa);
        }

    }
}
