/**
 * Implementacao do objeto remoto
 * autor: Rodrigo Campiolo
 * data: 22/11/2006
 */

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.net.*;
import java.io.*;

public class BlackJackManager extends UnicastRemoteObject implements BlackJackManagerRMI {

    public BlackJackManager() throws RemoteException {
        super();
        System.out.println("Instance of Object created with success");
    } //Calc

    public Response login(String nickname, String password) throws RemoteException {

        Connection db_connection = SQLiteConnection.connect(); 
        try {
   
            Statement statement = db_connection.createStatement();

        } catch (SQLException e) {
         //   Response resp = new Response(0);
       //     return resp;
        }
    } //add_grade


    public Mesa join_table(Jogador jogador) throws RemoteException {  

        Connection db_connection = SQLiteConnection.connect(); 
        try {
   
            Statement statement = db_connection.createStatement();

        } catch (SQLException e) {
         //   Response resp = new Response(0);
       //     return resp;
        }
    } //add_grade


} //list_notas
