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



### Descrição de implementação

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
  - Sera gerado uma instancia do tipo Jogador com saldo 10000.
* Cliente deve selecionar se quer jogar ou sair do jogo
  - Se ele jogar será retornada uma Mesa;
* Cliente deve ter crédito para poder jogar (100)
  - submit bet recebe um valor;
* O Cliente tem seu saldo atualizado após uma partida
  - Salva a partida em um histórico e atualiza o saldo do jogador no database.
* A partida é uma mesa de uma rodada com outro jogador pareado
  - O controle de acesso ao meio (Mesa) é realizado pelo Cliente e Servidor por meio da interface `get_table_status`


### Jogador

O jogador é salvo no banco de dados para que o seu saldo esteja sempre atualizado.
Seu nickname é único e pode ser utilizado como identificador.
Todo jogador inicia com 10000 de saldo, um nickname e uma senha.


## Servidor
O servido é o responsável por executar uma instancia do BlackJackManager para atender multíplos jogadores.

### Mesa
A mesa é a estrutura central do projeto, ela contém um baralho, dois jogadores sendo pareados,
e um valor total para se apostar. Após receber um vencedor, um perdedor ou um empate, a mesa é finalizada
e o cliente inicia o processo de construção de outra mesa.

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

### Exemplos

![Empate](empate.png)
![Vitória](vitoria.png)


Autores
=======

<table>
  <tr>
    <td align="center"><a href="https://www.linkedin.com/in/hmarcuzzo/"><img style="border-radius: 50%;" src="https://avatars2.githubusercontent.com/u/42159311?v=4" width="100px;" alt=""/></a><br /><a href="https://www.linkedin.com/in/hmarcuzzo/" title="Henrique Marcuzzo"><img href="https://www.linkedin.com/in/hmarcuzzo/" src="https://img.shields.io/badge/-HenriqueMarcuzzo-0077B5?style=flat&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/hmarcuzzo/"></a></td>
    <td align="center"><a href="https://www.linkedin.com/in/rafael-rampim-soratto-a42793190/"><img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/38047989?v=4" width="100px;" alt=""/></a><br /><a href="https://www.linkedin.com/in/rafael-rampim-soratto-a42793190/" title="Rafael Soratto"><img href="https://www.linkedin.com/in/rafael-rampim-soratto-a42793190/" src="https://img.shields.io/badge/-RafaelSoratto-0077B5?style=flat&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/rafael-rampim-soratto-a42793190/"></a></td>
  </tr>
</table>
