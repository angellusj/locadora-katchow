package control.teste;

import java.util.Arrays;
import java.util.List;

public class TesteControllerComposite implements TesteControllerComponent {
    List<TesteControllerComponent> testes;

    public TesteControllerComposite(TesteControllerComponent... args){
        testes = Arrays.stream(args).toList();
    }

    @Override
    public boolean teste() {
        for (TesteControllerComponent t : testes)
            if(!t.teste())
                return false;
        return true;
    }
}
