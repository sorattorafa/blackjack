import java.io.Serializable;
import java.util.List;

public class Mesa implements Serializable {
  private Integer id;
  private List<Jogador> jogadores;
  private Baralho baralho;

  public Mesa(){
  
  }

  public void set_id(Integer id) {
    this.id = id;
  }

  public void add_jogador(Jogador jogador) {
    this.jogadores.add(jogador);
  }

  public void set_baralho(Baralho baralho) {
    this.baralho = baralho;
  }

  public Integer get_id() {
    return this.id;
  }

  public List<Jogador> get_jogadores(Mesa mesa) {
    return this.jogadores;
  }
  
  public Baralho get_baralho() {
    return this.baralho;
  }




  public boolean checa_vez_jogador(Jogador jogador) {
      // retorna true se for a vez do jogador
  }

  public int get_acao_jogador(Jogador jogador, Integer aposta) {
      //
  }

  public int get_aposta_jogador(Jogador jogador, Integer aposta) {
      //
  }

  public int duplicador_aposta_jogador(Jogador jogador, Integer aposta) {
      //
  }
  
}