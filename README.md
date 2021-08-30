<h1 align="center">Trabalho final de Sistemas Distribuídos<br>Black Jack</h1>
<p href="#descricao" align="center">Trabalho final da disciplina de Sistemas Distribuídos, implementação do jogo de cartas Black Jack utilizando clientes (jogadores) conectados em um servidor. A comunicacao cliente-servidor ocorre utilizando a tecnologia RMI.</p>

<div align="center">
  <img alt="Java" src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white"/>
</div>

[comment]: <> (<h4 align="center"> )

[comment]: <> (  ✅  Projeto finalizado ✅)

[comment]: <> (</h4>)

Tabela de conteúdos
=================
<!--ts-->
   * [Executando o projeto](#executando-o-projeto)
   * [Descrição de implementação](#descrição-de-implementação)
   * [Bibliotecas Utilizadas](#bibliotecas-utilizadas)
   * [Exemplos de Uso](#exemplos-de-uso)
   * [Autores](#autores)
<!--te-->


Executando o projeto
====================

## 🎲 Servidor
### Compilando
```bash
# Na pasta do src/main/java execute:
$ javac -cp ":lib/*": *.java
```
### Executando
```bash
# Na pasta do src/main/java execute:
$ rmiregistry
# Em outro terminal execute:
$ java -cp ":lib/*" Servidor
```
## 🎲 Cliente (partida com 2 clientes)

### Executando um jogo para 2 clientes
```bash
# Na pasta do src/main/java execute:
$ java -cp ":lib/*" Cliente
$ java -cp ":lib/*" Cliente
```


Descrição de implementação

As interfaces implementadas sao utilizadas para parear, iniciar e finalizar uma partida de blackjack (21) entre dois jogadores.
São elas:

```java

public interface BlackJackManagerRMI extends Remote {
    public Jogador login (String nickname, String password) throws RemoteException;
    public Mesa join_table (Jogador jogador) throws RemoteException;
    public Mesa get_table_status(Mesa mesa) throws RemoteException;
    public Jogador update_player_cash(Jogador jogador) throws RemoteException;
    public Object[] submit_bet(Mesa mesa, Jogador jogador, int valor) throws RemoteException;
    public void player_decision(Jogador jogador, Mesa mesa, Integer requestType) throws RemoteException;
    public void finish_table(Mesa mesa) throws RemoteException;
}

```


==============

## Cliente

* Cliente deve logar com suas credenciais
* Cliente deve selecionar se quer jogar ou sair do jogo
* Cliente deve ter crédito para poder jogar (100)
* O Cliente tem seu saldo atualizado após uma partida
* A partida é uma mesa de uma rodada com outro jogador pareado

## Carta

A carta é uma classe com as seguintes propriedades:

```java
import java.io.Serializable;

public class Carta implements Serializable {
  private String name;
  private String symbol;
  private Integer value;
```
## Baralho

O baralho trata-se de um monte de carta usadas e um monte de cartas disponíveis:


```java
import java.io.Serializable;

public class Baralho implements Serializable {
  private List<Carta> unused_cards = new ArrayList<Carta>();
  private List<Carta> used_cards = new ArrayList<Carta>();
```

## Servidor

## Baralho

Pré-requisitos
==============

Antes de começar, vai precisar ter instalado na sua máquina as seguintes ferramentas:
- [Java](https://www.oracle.com/br/java/technologies/javase-jdk11-downloads.html)

Bibliotecas Utilizadas
==============

As seguintes bibliotecas foram usadas na construção do projeto:
#### Java
- [Scanner]()
- [Registry]()
- [LocateRegistry]()
- [SQLITE-JDBC]()
- [Remote]()

Exemplos de Uso
==============

Nesta versão o cliente e servidor são capazes de executar a Inserção, Remoção e Consulta de dados na tabela de Matrícula e consultas em na tabela de Alunos.

Para isso basta selecionar uma opção no cliente e inserir todos os dados solicitados. 

* Lembre-se sempre de inserir dados pré existentes no banco para os campos de ***RA*** e ***CÓDIGO***!

<!-- Ver exemplo abaixo:

![Exemplo do Cliente](img/exemplo_cliente.png) -->

Autores
=======

<table>
  <tr>
    <td align="center"><a href="https://www.linkedin.com/in/hmarcuzzo/"><img style="border-radius: 50%;" src="https://avatars2.githubusercontent.com/u/42159311?v=4" width="100px;" alt=""/></a><br /><a href="https://www.linkedin.com/in/hmarcuzzo/" title="Henrique Marcuzzo"><img href="https://www.linkedin.com/in/hmarcuzzo/" src="https://img.shields.io/badge/-HenriqueMarcuzzo-0077B5?style=flat&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/hmarcuzzo/"></a></td>
    <td align="center"><a href="https://www.linkedin.com/in/rafael-rampim-soratto-a42793190/"><img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/38047989?v=4" width="100px;" alt=""/></a><br /><a href="https://www.linkedin.com/in/rafael-rampim-soratto-a42793190/" title="Rafael Soratto"><img href="https://www.linkedin.com/in/rafael-rampim-soratto-a42793190/" src="https://img.shields.io/badge/-RafaelSoratto-0077B5?style=flat&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/rafael-rampim-soratto-a42793190/"></a></td>
  </tr>
</table>
