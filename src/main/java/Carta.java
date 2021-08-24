import java.io.Serializable;

public class Carta implements Serializable {
  private String name;
  private Integer value;

  public Carta(){
  }

  public void set_name(String name) {
    this.name = name;
  }

  public void set_value(Integer value) {
    this.value = value;
  }

  public String get_name() {
    return this.name;
  }

  public Integer get_value() {
    return this.value;
  }
}