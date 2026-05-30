package model.dao.teste;

public class TesteMain {
    public static void main(String[] args) {
        TesteDaoComposite testes;
        testes = new TesteDaoComposite(new TesteClienteDAO());

        testes.teste();
    }
}