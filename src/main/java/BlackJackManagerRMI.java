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

// 1 login
// Mesa entrar_na_mesa(jogador)
public interface BlackJackManagerRMI extends Remote {
    public Jogador login (String nickname, String password) throws RemoteException;
    public Mesa join_table (Jogador jogador) throws RemoteException;
    public List<Jogador> start_table(Jogador jogador, Mesa mesa) throws RemoteException;
    public List<Carta> get_cartas_by_jogador(Jogador jogador, Mesa mesa) throws RemoteException;
    public Jogador finish_table(Mesa mesa, Jogador jogador) throws RemoteException;
    public Mesa get_stado_atual_mesa(Mesa mesa) throws RemoteException;

}
