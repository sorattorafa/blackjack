import java.io.Serializable;
import java.util.List;


public class Baralho implements Serializable {
  private List<Carta> cartas;

  public Baralho(){
  
  }

  public void add_carta(Carta carta) {
    this.cartas.add(carta);
  }

  public List<Carta> get_baralho() {
    return this.cartas;
  }

}