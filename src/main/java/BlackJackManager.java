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

    List<Jogador> jogadores_disponiveis = new ArrayList<Jogador>();
    List<Mesa> mesas = new ArrayList<Mesa>();
    int total_mesas = 0;
    int total_jogadores = 0;

    public BlackJackManager() throws RemoteException {
        super();
        System.out.println("Instance of Object created with success");
    }

    @Override
    public Jogador login(String nickname, String password) throws RemoteException {

        Connection db_connection = SQLiteConnection.connect(); 
        Jogador jogador = new Jogador();
        String search_player_query = "SELECT * FROM jogador WHERE (nickname = " + String.valueOf(nickname) + ");";
        Statement statement = db_connection.createStatement();

        try {
             /* search for JOGADOR */
            ResultSet resultSet = statement.executeQuery(search_player_query);
            if (!resultSet.isBeforeFirst()) {
                /* NOT FOUND */
                String create_jogador = "INSERT INTO jogador (nickname, password) VALUES (" + nickname + ", " + password + ");"; 
                statement.execute(create_matricula);
                /* search for disiplina */
                ResultSet resultSet2 = statement.executeQuery(search_player_query);
                if (!resultSet2.isBeforeFirst()) {
                }
                 
            }

            jogador.set_nickname(nickname);
            jogador.set_password(password);
            jogador.set_cash(0);
            total_jogadores += 1;
            jogador.set_id(total_jogadores);
            
            
        } catch (SQLException e) {
            return null;
        }
        return jogador;
    }

    @Override
    public Mesa join_table(Jogador jogador) throws RemoteException {  
        jogadores_disponiveis.add(jogador);
        
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
                total_mesas += 1;
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
    public Integer get_player_cash(String nickname) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer get_table_cash(Integer id_table) throws RemoteException {
        // TODO Auto-generated method stub
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
            System.out.println(mesa_i.get_id().equals(mesa.get_id()));
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


}
