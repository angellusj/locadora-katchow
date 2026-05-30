package model.dao.teste;

import java.util.List;

public class TesteDaoComposite implements TesteDaoComponent {
    List<TesteDaoComponent> testes;

    public TesteDaoComposite(TesteClienteDAO args){
        testes = List.of((TesteDaoComponent) args);
    }

    @Override
    public boolean teste() {
        for(model.dao.teste.TesteDaoComponent t : testes){
            if(!t.teste())
                return false;
        }
        return true;
    }
}