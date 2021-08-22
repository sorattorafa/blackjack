import java.io.Serializable;

public class Mesa implements Serializable {
  private List<Jogador> jogadores;
  private Baralho baralho;

  public Mesa(){
  }

  public void add_jogador(Jogador jogador) {
    this.jogadores.add(jogador);
  }

  public List<Jogador> get_jogadores(Mesa mesa) {
    return this.jogadores;
  }

  public Baralho get_baralho() {
    return this.baralho;
  }

  public Baralho set_baralho(Baralho baralho) {
    this.baralho = baralho;
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