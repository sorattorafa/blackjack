import java.io.Serializable;

public class Jogador implements Serializable {
  private String nickname;
  private String password;
  private Integer cash;
  private Integer table_id = -1;
  private Integer id;


  public Jogador(){
  
  }

  public void set_id(Integer id) {
    this.id = id;
  }


  public Integer get_id(Integer id) {
    return this.id;
  }


  public void set_nickname(String nickname) {
    this.nickname = nickname;
  }
  
  public void set_password(String password) {
    this.password = password;
  }
  
  public void set_cash(Integer cash) {
    this.cash = cash;
  }

  public void set_table_id(Integer table_id) {
    this.table_id = table_id;
  }

  public String get_nickname() {
    return this.nickname;
  }
  
  public String get_password() {
    return this.password;
  }
  
  public int get_cash() {
    return this.cash;
  }

  public int get_table_id() {
    return this.table_id;
  }
}