/**
 * Implementacao do objeto remoto
 * autor: Rodrigo Campiolo
 * data: 22/11/2006
 */

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.List;
import java.net.*;
import java.io.*;
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
        Jogador jogador = new Jogador();
        String search_player_query = "SELECT J.nickname, J.id, J.cash FROM jogador AS J WHERE J.nickname IS \"" + String.valueOf(nickname) + "\" AND J.password IS \"" + String.valueOf(password) + "\";";
        System.out.println(search_player_query);
        try {
            Statement statement = db_connection.createStatement();
             /* search for JOGADOR */
            ResultSet resultSet = statement.executeQuery(search_player_query);
            if (!resultSet.isBeforeFirst()) {
                /* NOT FOUND jogador*/
                
                String create_jogador = "INSERT INTO jogador (nickname, password) VALUES ( '" + nickname + "', '" + password + "');"; 
                statement.execute(create_jogador);
                /* search for disiplina */
                ResultSet resultSet2 = statement.executeQuery(search_player_query);
                if (!resultSet2.isBeforeFirst()) {
                    throw new RemoteException(" Falha ao cadastrar jogador");
                } else {
                     String nickname_user = resultSet2.getString("nickname");
                    int id = resultSet2.getInt("id");
                    int cash = resultSet2.getInt("cash");
                    jogador.set_nickname(nickname_user);
                    jogador.set_cash(cash);
                    jogador.set_id(id);
                }
                 
            } else{
                String nickname_user = resultSet.getString("nickname");
                int id = resultSet.getInt("id");
                int cash = resultSet.getInt("cash");
                jogador.set_nickname(nickname_user);
                jogador.set_cash(cash);
                jogador.set_id(id);
            }
            
            
        } catch (SQLException e) {
            System.out.println(e);
            try {
                Statement statement = db_connection.createStatement();
                String create_jogador = "INSERT INTO jogador (nickname, password) VALUES (" + nickname + ", " + password + ");"; 
                statement.execute(create_jogador);
                /* search for disiplina */
                ResultSet resultSet2 = statement.executeQuery(search_player_query);
                if (!resultSet2.isBeforeFirst()) {
                    throw new RemoteException(" Falha ao cadastrar jogador ");
                } else {
                     String nickname_user = resultSet2.getString("nickname");
                    int id = resultSet2.getInt("id");
                    int cash = resultSet2.getInt("cash");
                    jogador.set_nickname(nickname_user);
                    jogador.set_cash(cash);
                    jogador.set_id(id);
                }
            } catch(SQLException e2) {
                throw new RemoteException(" Falha ao cadastrar jogador");
            }
        }
        return jogador;
    }

    @Override
    public Mesa join_table(Jogador jogador) throws RemoteException {  
        Mesa mesa = new Mesa();
        if (mesas.size() > 0) {
             for (Mesa mesa_i : mesas) {
                    if (mesa_i.players_list().size() == 1) {
                        mesa_i.add_player(jogador);
                        Integer player_id = jogador.get_id();
                        MaoJogador maojogador = new MaoJogador();
                        StatusJogador statusjogador = new StatusJogador();

                        statusjogador.set_player_status(3);
                        statusjogador.set_player_id(player_id);

                        maojogador.set_player_id(player_id);
                        
                        /* 1a carta do player que entrou na mesa */  
                        Baralho baralho = mesa_i.get_baralho();
                        Carta player_card = baralho.draw_card();
                        maojogador.add_player_hand_carta(player_card);

                        /* 2a carta do player que iniciou a mesa */
                        MaoJogador maooponente = mesa_i.get_opponent_hand(jogador.get_nickname());
                        player_card = baralho.draw_card();
                        maooponente.add_player_hand_carta(player_card);

                        /* 2a carta do player que entrou na mesa */ 
                        player_card = baralho.draw_card();
                        maojogador.add_player_hand_carta(player_card);
                        
                        mesa_i.add_mao_jogador(maojogador);
                        mesa_i.add_player_status(statusjogador);
                        mesa = mesa_i;
                    }
            }  
        } else {
            try {
         
                Integer player_id = jogador.get_id();
                MaoJogador maojogador = new MaoJogador();
                StatusJogador statusjogador = new StatusJogador();
                int total_mesas = mesas.size();

               /* Mao do statusjogador setters*/ 
                statusjogador.set_player_status(5);
                statusjogador.set_player_id(player_id);

               /* Mao do jogador setters*/ 
                maojogador.set_player_id(player_id);

                /* Mesa setters*/
                mesa.set_id(total_mesas);
                mesa.set_total_cash(0);
                mesa.set_baralho(new Baralho());
                mesa.add_player(jogador);
                
                /* 1a carta do player que iniciou a mesa */ 
                Baralho baralho = mesa.get_baralho();
                Carta player_card = baralho.draw_card();
                maojogador.add_player_hand_carta(player_card);

                mesa.add_mao_jogador(maojogador);
                mesa.add_player_status(statusjogador); 
                
                                


                mesas.add(mesa);
            } catch (Exception e) {
                System.out.println(e);
            }

        }

        return mesa;
    }

    @Override
    public Mesa get_estado_atual_mesa(Mesa mesa) throws RemoteException {
        for (Mesa mesa_i : mesas) {
            if (mesa_i.get_id().equals(mesa.get_id())) {
                return mesa_i;
            }
        }
        
        return null;
    }

    @Override
    public Integer get_player_table_status(Integer id_table, String player_nickname) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void keep_playing(Integer id_table, String player_nickname) throws RemoteException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Mesa keep_current_play(Integer id_table) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object[] submit_bet(Mesa mesa, Jogador jogador, int valor) throws RemoteException {
        for (Mesa mesa_i : mesas) {
            if (mesa_i.get_id().equals(mesa.get_id())) {
                jogador.set_cash(jogador.get_cash() - valor);
                mesa_i.set_total_cash(mesa_i.get_total_cash() + valor);
                return new Object[] {mesa_i, jogador};
            }
        }


        return null;
    }

    @Override
    public void player_decision(Jogador jogador, Mesa mesa, Integer requestType) throws RemoteException {
        
        
    }


}
