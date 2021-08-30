/**
 * Esta classe é o que representa a mão de cada jogador em uma mesa de Blackjack. Todas as mesas devem ter um
 * objeto de mão de jogador para cada jogador que estiver na mesa, para que o controlador possa manipular
 * as cartas para cada jogador.
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


public class MaoJogador implements Serializable {
  // Atributos
  private Integer player_id;
  private List<Carta> player_hand;

  // Construtor
  public MaoJogador(){
    this.player_hand = new ArrayList<Carta>();
  }

  public MaoJogador(Integer player_id){
    this.player_id = player_id;
    this.player_hand = new ArrayList<Carta>();
  }


  // Getters e Setters
  public void set_player_id(Integer player_id) {
    this.player_id = player_id;
  }

  public Integer get_player_id() {
    return this.player_id;
  }

  public void add_player_hand_carta(Carta carta) {
    this.player_hand.add(carta);
  }

  public List<Carta> get_player_hand() {
    return this.player_hand;
  }
}