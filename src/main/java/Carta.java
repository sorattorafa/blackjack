/**
 * Esta classe é o que representa a carta de um Baralho no jogo Blackjack. Classe é responsável por guardar 
 * as informações de cada carta para que posssa ser manipulada pelo baralho durante uma rodada de BlackJack.
 *
 * Autores:
 *     @hmarcuzzo (Henrique Marcuzzo)
 *     @sorattorafa (Rafael Soratto)
 *
 * Data de Criação: 27 de Ago de 2021
 * Ultima alteração: 29 de Ago de 2021
*/

import java.io.Serializable;

public class Carta implements Serializable {
  // Atributos
  private String name;
  private String symbol;
  private Integer value;

  // Construtor
  public Carta(){
  
  }

  public Carta(String name, String symbol, Integer value){
    this.name = name;
    this.symbol = symbol;
    this.value = value;
  }

  // Getters e Setters
  public void set_name(String name) {
    this.name = name;
  }

  public String get_name() {
    return this.name;
  }

  public void set_symbol(String symbol) {
    this.symbol = symbol;
  }

  public String get_symbol() {
    return this.symbol;
  }

  public void set_value(Integer value) {
    this.value = value;
  }

  public Integer get_value() {
    return this.value;
  }
}