/**
 * Este código é responsável pela parte de criar uma conexão com o banco SQLITE
 * 
 * Protocolo: RPC
 *
 * @author Henrique Marcuzzo (@hmarcuzzo)
 * @author Rafael Soratto (@sorattorafa)
 * 
 * Data de Criação: 01 de Agosto de 2021 
 * Ultima alteração: 01 de Agosto de 2021
 */

import java.sql.*;

public class SQLiteConnection{
  static Connection connection;

  public static Connection connect() {    
    try {
      connection = DriverManager.getConnection( "jdbc:sqlite:./database/gerenciamento_notas.db");
      // System.out.println("BD connection success!");

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return connection;
  }
}