/**
 * Esta classe é o que representa um Baralho em uma Mesa no jogo Blackjack. Classe é responsável por guardar 
 * as cartas dos baralho ainda não utilizada e as cartas que já foram utilizadas.
 *    Esta classe também deve oferecer métodos para manipulação do baralho, como embaralhar e retirar uma carta.
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
import java.util.regex.Pattern;
import java.util.*;


public class Baralho implements Serializable {
  // Atributos
  private List<Carta> unused_cards;
  private List<Carta> used_cards;

  // Construtor
  public Baralho(){
    // Cria um baralho de 52 cartas
    this.unused_cards = new ArrayList<Carta>();
    this.used_cards = new ArrayList<Carta>();

    for (String card : "2 3 4 5 6 7 8 9 10 J Q K A".split(" ")) {
      for (String symbol : "♠ ♥ ♦ ♣".split(" ")) {
        Integer value;

        if (Pattern.matches("[2-9]", card)) {
          value = Integer.parseInt(card);
        } else {
          if (card.equals("A")) {
            value = 1;
          } else {
            value = 10;
          }
        }
        this.unused_cards.add(new Carta(card, symbol, value));
      }
    }
    random_unused_cards();
  }

  // Getters and Setters
  public void set_unused_cards(List<Carta> unused_cards) {
    this.unused_cards = unused_cards;
  }

  public List<Carta> get_unused_cards() {
    return this.unused_cards;
  }
  
  public void set_used_cards(List<Carta> used_cards) {
    this.used_cards = used_cards;
  }

  public List<Carta> get_used_cards() {
    return this.used_cards;
  }


  // Métodos
  public void random_unused_cards() {
    // Este método é responsável por embaralha as cartas que não foram utilizadas.
    Collections.shuffle(this.unused_cards);
  }

  public Carta draw_card() {
    // Este método é responsável por retirar uma carta do baralho que ainda não foi utilizado.
    Carta carta = this.unused_cards.get(0);
    this.unused_cards.remove(0);
    // this.used_cards.add(carta);
    return carta;
  }

  public void remake_deck() {
    /** Este método é responsável remontar o baralho com todas as suas cartas (usadas ou não) e ao fim
     * embaralhar. 
    */
    for (Carta carta : used_cards) {
      this.unused_cards.add(carta);
      this.used_cards.remove(carta);
    }
    random_unused_cards();
  }

}