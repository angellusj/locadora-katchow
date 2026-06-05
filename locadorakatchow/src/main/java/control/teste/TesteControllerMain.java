package control.teste;

public class TesteControllerMain {
    public static void main(String[] args){
        TesteControllerComposite t;
        t = new TesteControllerComposite(new TesteControllerFuncionario());
        t.teste();
    }
}