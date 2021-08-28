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

    public BlackJackManager() throws RemoteException {
        super();
        System.out.println("Instance of Object created with success");
    }

    @Override
    public Jogador login(String nickname, String password) throws RemoteException {
        Jogador jogador = new Jogador();
        String search_player_query = "SELECT J.nickname, J.id, J.cash FROM jogador AS J WHERE J.nickname IS \"" + String.valueOf(nickname) + "\" AND J.password IS \"" + String.valueOf(password) + "\" ;";
        Connection db_connection = SQLiteConnection.connect(); 
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
                        MaoJogador maojogador = new MaoJogador();
                        maojogador.set_player_id(jogador.get_id());
                        mesa_i.add_mao_jogador(maojogador);
                        mesa = mesa_i;
                    }
            }  
        } else {
            try {
                int total_mesas = mesas.size();
                mesa.set_id(total_mesas);
                mesa.set_total_cash(0);

                mesa.set_baralho(new Baralho());
                mesa.add_player(jogador);
                MaoJogador maojogador = new MaoJogador();
                maojogador.set_player_id(jogador.get_id());
                mesa.add_mao_jogador(maojogador);       

                mesas.add(mesa);
            } catch (Exception e) {
                System.out.println(e);
            }

        }

        return mesa;
    }

    @Override
    public Integer get_player_cash(Jogador jogador) throws RemoteException {
        return jogador.get_cash();
    }

    @Override
    public Integer get_table_cash(Integer id_table) throws RemoteException {
        for (Mesa mesa_i : mesas) {
            if (mesa_i.get_id().equals(id_table)) {
                return mesa_i.get_total_cash();
            }
        }
        return null;
    }

    @Override
    public Jogador get_oponente(Integer id_table, String player_nickname) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Carta> get_player_cards(Integer id_table, String player_nickname) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String get_player_status(Integer id_table, String player_nickname) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer get_player_points(Integer id_table, String player_nickname) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
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


}
