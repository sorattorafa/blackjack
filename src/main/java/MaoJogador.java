import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;


public class MaoJogador implements Serializable {
  private Integer player_id;
  private List<Carta> player_hand;

  public MaoJogador(){
  
  }

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