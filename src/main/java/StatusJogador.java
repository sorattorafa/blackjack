/**
 * Esta classe é o que representa o estado do jogador em uma mesa de Blackjack. Todas as mesas devem ter um
 * objeto de estado de jogador para cada jogador que estiver na mesa, para que o controlador possa manipular
 * de quem é a vez de jogar, ganhadores e perdedores, etc.
 *
 * Autores:
 *     @hmarcuzzo (Henrique Marcuzzo)
 *     @sorattorafa (Rafael Soratto)
 *
 * Data de Criação: 27 de Ago de 2021
 * Ultima alteração: 29 de Ago de 2021
*/

import java.io.Serializable;

public class StatusJogador implements Serializable {
  // Atributos
  private Integer player_id;  // ID do jogador
  private Integer status;    // Status do jogador: 1 - ganhou, 2 - perdeu, 3 - Aguardando, 4 - Parou, 5 - Vez do jogador, 6 - Empatou

  // Construtor
  public StatusJogador() {

  }

  public StatusJogador(Integer player_id, Integer status) {
    this.player_id = player_id;
    this.status = status;
  }

  
  // Getters e Setters
  public void set_player_id(Integer player_id) {
    this.player_id = player_id;
  }

  public Integer get_player_id() {
    return this.player_id;
  }
  
  public void set_player_status(Integer status) {
    this.status = status;
  }

  public Integer get_player_status() {
    return this.status;
  }
  
}