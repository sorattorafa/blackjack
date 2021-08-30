/**
 * Esta classe é o que representa o jogador no jogo Blackjack. Classe é responsável por guardar as informações
 * de cada jogador para que posssa ser manipulado pelo controlador.
 *
 * Autores:
 *     @hmarcuzzo (Henrique Marcuzzo)
 *     @sorattorafa (Rafael Soratto)
 *
 * Data de Criação: 27 de Ago de 2021
 * Ultima alteração: 29 de Ago de 2021
*/

import java.io.Serializable;

public class Jogador implements Serializable {
  // Atributos
  private Integer id;
  private String nickname;
  private Integer cash;
  private Integer table_id = -1;

  // Construtor
  public Jogador(){
    
  }


  public Jogador(String nickname_user, Integer id, Integer cash){
    this.nickname = nickname_user;
    this.id = id;
    this.cash = cash;
  }

  public Jogador(Integer id, String nickname, Integer cash) {
    this.id = id;
    this.nickname = nickname;
    this.cash = cash;
  }

  // Getters e Setters
  public void set_id(Integer id) {
    this.id = id;
  }

  public Integer get_id() {
    return this.id;
  }

  public void set_nickname(String nickname) {
    this.nickname = nickname;
  }
  
  public String get_nickname() {
    return this.nickname;
  }

  public void set_cash(Integer cash) {
    this.cash = cash;
  }
  
  public int get_cash() {
    return this.cash;
  }

  public void set_table_id(Integer table_id) {
    this.table_id = table_id;
  }

  public int get_table_id() {
    return this.table_id;
  }
}