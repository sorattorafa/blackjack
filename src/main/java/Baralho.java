import java.io.Serializable;
import java.util.List;
import java.util.regex.Pattern;
import java.util.*;


public class Baralho implements Serializable {
  private List<Carta> unused_cards;
  private List<Carta> used_cards;

  public Baralho(){
    for (String card : "2 3 4 5 6 7 8 9 10 J Q K A".split(" ")) {
      for (String symbol : "♠ ♥ ♦ ♣".split(" ")) {
        Carta carta = new Carta();
        carta.set_name(card);
        carta.set_symbol(symbol);

        if (Pattern.matches("[2-9]", card)) {
          carta.set_value(Integer.parseInt(card));
        } else {
          if (card.equals("A")) {
            carta.set_value(1);
          } else {
            carta.set_value(10);
          }
        }
        this.unused_cards.add(carta);
      }
    }
    random_unused_cards();
  }

  public List<Carta> get_unused_cards() {
    return this.unused_cards;
  }

  public List<Carta> get_used_cards() {
    return this.used_cards;
  }

  public void set_used_cards(List<Carta> used_cards) {
    this.used_cards = used_cards;
  }

  public void set_unused_cards(List<Carta> unused_cards) {
    this.unused_cards = unused_cards;
  }

  public void random_unused_cards() {
    Collections.shuffle(this.unused_cards);
  }

  public Carta draw_card() {
    Carta carta = this.unused_cards.get(0);
    this.unused_cards.remove(0);
    // this.used_cards.add(carta);
    return carta;
  }

  public void remake_deck() {
    for (Carta carta : used_cards) {
      this.unused_cards.add(carta);
      this.used_cards.remove(carta);
    }
    random_unused_cards();
  }

}