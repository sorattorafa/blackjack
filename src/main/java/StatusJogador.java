import java.io.Serializable;

public class StatusJogador implements Serializable {
  private Integer player_id;
  private Integer status;

  public StatusJogador() {}

  public void set_player_id(Integer player_id) {
    this.player_id = player_id;
  }

  public Integer get_player_id() {
    return this.player_id;
  }
  
  public void set_player_status(Integer status) {
    this.status = status;
  }

  public Integer get_player_status() {
    return this.status;
  }
  
}