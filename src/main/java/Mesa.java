import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;


public class Mesa implements Serializable {
  private Integer id;
  private List<Jogador> players_list = new ArrayList<Jogador>();
  private List<MaoJogador> players_hand = new ArrayList<MaoJogador>();
  private Baralho baralho;
  private Integer total_cash;
  
  public Mesa(){
  
  }

  public void set_id(Integer id) {
    this.id = id;
  }
  public Integer get_id() {
    return this.id;
  }

   public void set_baralho(Baralho baralho) {
    this.baralho = baralho;
  }
  public Baralho get_baralho() {
    return this.baralho;
  }

  public void set_total_cash(Integer total_cash) {
    this.total_cash = total_cash;
  }
  public Integer get_total_cash() {
    return this.total_cash;
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

  public Jogador get_opponent(String player_nickname) {
    for (Jogador jogador : this.players_list) {
      if (!jogador.get_nickname().equals(player_nickname)) {
        return jogador;
      }
    }
    return null;
  }


  // public boolean checa_vez_jogador(Jogador jogador) {
  //     // retorna true se for a vez do jogador
  // }

  // public int get_acao_jogador(Jogador jogador, Integer aposta) {
  //     //
  // }

  // public int get_aposta_jogador(Jogador jogador, Integer aposta) {
  //     //
  // }

  // public int duplicador_aposta_jogador(Jogador jogador, Integer aposta) {
  //     //
  // }
  
}