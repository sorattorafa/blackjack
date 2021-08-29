/**
 * Este código é responsavel pela parte do cliente e comunicação com o servidor, ele ira tratar o envio o dos dados
 * utilizando a ferramenta RMI e o recebimento da mensagem no mesmo formato.
 *
 * Autores:
 *     @hmarcuzzo (Henrique Marcuzzo)
 *     @sorattorafa (Rafael Soratto)
 *
 * Data de Criação: 10 de Ago de 2021
 * Ultima alteração: 10 de Ago de 2021
*/

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface BlackJackManagerRMI extends Remote {
    public Jogador login (String nickname, String password) throws RemoteException;
    public Mesa join_table (Jogador jogador) throws RemoteException;
    public Mesa get_estado_atual_mesa(Mesa mesa) throws RemoteException;
    public Jogador update_player_cash(Jogador jogador) throws RemoteException;
    public Object[] submit_bet(Mesa mesa, Jogador jogador, int valor) throws RemoteException;
    public void player_decision(Jogador jogador, Mesa mesa, Integer requestType) throws RemoteException;

    
    public Integer get_player_table_status(Integer id_table, String player_nickname) throws RemoteException;
    public void keep_playing(Integer id_table, String player_nickname) throws RemoteException;
    public Mesa keep_current_play(Integer id_table) throws RemoteException;

}
