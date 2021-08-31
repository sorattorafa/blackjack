/**
 * Este código é responsavel pela parte do cliente e comunicação com o servidor, ele ira tratar o envio o dos dados
 * utilizando a ferramenta RMI e o recebimento da mensagem no mesmo formato.
 *
 * Autores:
 *     @hmarcuzzo (Henrique Marcuzzo)
 *     @sorattorafa (Rafael Soratto)
 *
 * Data de Criação: 23 de Ago de 2021
 * Ultima alteração: 29 de Ago de 2021
*/

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface BlackJackManagerRMI extends Remote {

    /* login method receives nickname and password and returns Jogador instance (player) logged in */
    public Jogador login (String nickname, String password) throws RemoteException;
    
    /* join_table method receives a logged in player and return a instance o Mesa to start a new game */
    public Mesa join_table (Jogador jogador) throws RemoteException;
    
    /*  
        get_table_status receive table and returns table updated table with cards and bets of opponent
        needs to be called periodically by player.
    */
    public Mesa get_table_status(Mesa mesa) throws RemoteException;

    /*  update_player_cash receive logged in player, save cash into DB and returns updated player */
    public Jogador update_player_cash(Jogador jogador) throws RemoteException;

    /* submit_bet receives started table, logged in player, and betting value */
    public Object[] submit_bet(Mesa mesa, Jogador jogador, int valor) throws RemoteException;
    

    /* player_decision receives started table, logged in player, and requestType
        if request type = 2 ->  means the player wants one more card
        if request type = 1 ->  means the player don't want more cards
    */
    public void player_decision(Jogador jogador, Mesa mesa, Integer requestType) throws RemoteException;
    
    /* finish_table save table into DB and delete from memory */
    public void finish_table(Mesa mesa) throws RemoteException;
}
