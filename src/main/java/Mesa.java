import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;


public class Mesa implements Serializable {
  private Integer id;
  private Integer players_list_id;
  private List<MaoJogador> players_hand;
  private Integer baralho_id;
  private Integer total_cash;
  

  public Mesa(){
  
  }

  public void set_id(Integer id) {
    this.id = id;
  }

  public Integer get_id() {
    return this.id;
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