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

    public BlackJackManager() throws RemoteException {
        super();
        System.out.println("Instance of Object created with success");
    }

    @Override
    public Jogador login(String nickname, String password) throws RemoteException {

        Connection db_connection = SQLiteConnection.connect(); 
        try {

            Statement statement = db_connection.createStatement();
            
        } catch (SQLException e) {
            // return null;
        }
        Jogador jogador = new Jogador();
        jogador.set_nickname(nickname);
        jogador.set_password(password);
        jogador.set_cash(0);
        return jogador;

    }

    @Override
    public Mesa join_table(Jogador jogador) throws RemoteException {  
        jogadores_disponiveis.add(jogador);
        
        Mesa mesa;
        if (jogador.get_table_id() != -1) {
            mesa =

        } else if (jogadores_disponiveis.size() >= 2) {
            
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
        // TODO Auto-generated method stub
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
