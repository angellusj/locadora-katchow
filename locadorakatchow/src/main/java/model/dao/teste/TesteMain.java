package model.dao.teste;

import model.db.DatabaseConfig;

public class TesteMain {
    public static void main(String[] args) {
        System.out.println(DatabaseConfig.class.getProtectionDomain()
                .getCodeSource()
                .getLocation());
        System.out.println(
                new java.io.File("target/classes/db.properties").getAbsolutePath()
        );

        System.out.println(
                new java.io.File("target/classes/db.properties").exists()
        );
        System.out.println("user.dir = " + System.getProperty("user.dir"));
        System.out.println("classpath = " + System.getProperty("java.class.path"));

        TesteDaoComposite testes;
        testes = new TesteDaoComposite(new TesteClienteDAO(), new TesteFuncionarioDAO());

        testes.teste();
    }
}