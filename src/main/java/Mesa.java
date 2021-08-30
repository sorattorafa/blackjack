/**
 * Esta classe é o que representa uma Mesa no jogo Blackjack. 
 * Classe é responsável por guardar todas as informações de um jogo, tais como cartas dos baralho,
 * lista de player, mão dos jogadores, controlar o fluxo do jogo e total apostado.
 * 
 * Autores:
 *     @hmarcuzzo (Henrique Marcuzzo)
 *     @sorattorafa (Rafael Soratto)
 *
 * Data de Criação: 27 de Ago de 2021
 * Ultima alteração: 29 de Ago de 2021
*/

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;


public class Mesa implements Serializable {
  // Atributos
  private Integer id;
  private boolean is_finished = false;

  private Integer total_cash;
  private Baralho baralho;

  private List<Jogador> players_list = new ArrayList<Jogador>();
  private List<MaoJogador> players_hand = new ArrayList<MaoJogador>();
  private List<StatusJogador> players_status = new ArrayList<StatusJogador>();
  

  public Mesa(){
  
  }


  // Getters and Setters
  public void set_id(Integer id) {
    this.id = id;
  }
  
  public Integer get_id() {
    return this.id;
  }

  public void set_is_finished(boolean is_finished) {
    this.is_finished = is_finished;
  }
  public boolean get_is_finished() {
    return this.is_finished;
  }

  public void set_total_cash(Integer total_cash) {
    this.total_cash = total_cash;
  }

  public Integer get_total_cash() {
    return this.total_cash;
  }

  public void set_baralho(Baralho baralho) {
    this.baralho = baralho;
  }

  public Baralho get_baralho() {
    return this.baralho;
  }

  public void add_player(Jogador jogador) {
    this.players_list.add(jogador);
  }

  public List<Jogador> players_list() {
    return this.players_list;
  }

  public void add_mao_jogador(MaoJogador maojogador) {
    this.players_hand.add(maojogador);
  }

  public List<MaoJogador> players_hand() {
    return this.players_hand;
  }

  public void add_player_status(StatusJogador jogador) {
    this.players_status.add(jogador);
  }
  
  public List<StatusJogador> players_status() {
    return this.players_status;
  }


  // Métodos
  public Jogador get_opponent(String player_nickname) {
    // Método responsável por retorna o Jogador oponente de um jogador.
    for (Jogador jogador : this.players_list) {
      if (!jogador.get_nickname().equals(player_nickname)) {
        return jogador;
      }
    }
    return null;
  }

  public MaoJogador get_opponent_hand(String player_nickname){
    // Método responsável por retorna a mão do Jogador oponente de um jogador.
    Jogador opponent = this.get_opponent(player_nickname);

    for (MaoJogador maojogador : this.players_hand) {
      if (maojogador.get_player_id().equals(opponent.get_id())) {
        return maojogador;
      }
    }
    return null;
  }

  public MaoJogador get_mao_jogador(Integer player_id){
    // Método responsável por retorna a mão de um jogador.
    for (MaoJogador maojogador : this.players_hand) {
      if (maojogador.get_player_id().equals(player_id)) {
        return maojogador;
      }
    }
    return null;
}

  public List<Carta> get_player_cards(Integer player_id) {   
    // Método responsável por retorna as cartas de um jogador.    
    for (MaoJogador maojogador : this.players_hand) {
      if (maojogador.get_player_id().equals(player_id)) {
        return maojogador.get_player_hand();
      }
    }
    return null;
  }

  public Integer get_player_points(List<Carta> player_hand) {
    // Método responsável por retorna o total de pontos da mão de um jogador.
    Integer points = 0;
    for (Carta carta : player_hand) {
      Integer flag = 0;
      if (carta.get_name().equals("A")) {
        for (Carta carta2 : player_hand) {
          if (carta2.get_name().equals("J") || carta2.get_name().equals("Q") || carta2.get_name().equals("K")) {
            points += 11;
            flag = 1;
            break;
          }
        }
      }
      if (flag == 0) {
        points += carta.get_value();
      }
    }
    return points;
  }

  public Integer get_player_statusCode(Integer player_id) {
    // Método responsável por retorna o status do StatusJogador de um jogador.
    for (StatusJogador statusjogador : this.players_status) {
      if (statusjogador.get_player_id().equals(player_id)) {
        return statusjogador.get_player_status();
      }
    }
    return null;
  }

  public StatusJogador get_players_status(Integer player_id) {
    // Método responsável por retorna o StatusJogador de um jogador.
    for (StatusJogador statusjogador : this.players_status) {
      if (statusjogador.get_player_id().equals(player_id)) {
        return statusjogador;
      }
    }
    return null;
  }

}