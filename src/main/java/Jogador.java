import java.io.Serializable;

public class Jogador implements Serializable {
  private String nickname;
  private String password;
  private Integer cash;


  public Jogador(){
  }

 public void set_cash(Integer cash) {
    this.cash = cash;
}

  public int get_cash() {
    return this.cash;
  }

  public void set_nickname(String nickname) {
    this.nickname = nickname;
  }

  public void set_password(String password) {
    this.password = password;
  }

  public String get_nickname() {
    return this.nickname;
  }

  public String get_password() {
    return this.password;
  }
}