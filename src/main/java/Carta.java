import java.io.Serializable;

public class Carta implements Serializable {
  private String name;
  private String symbol;
  private Integer value;

  public Carta(){
  }

  public void set_name(String name) {
    this.name = name;
  }

  public void set_symbol(String symbol) {
    this.symbol = symbol;
  }

  public void set_value(Integer value) {
    this.value = value;
  }

  public String get_name() {
    return this.name;
  }

  public String get_symbol() {
    return this.symbol;
  }

  public Integer get_value() {
    return this.value;
  }
}