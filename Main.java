public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando a plataforma AcademiaDev...");

        // 1. Cria a instância do nosso "banco de dados" em memória.
        PlatformData platformData = new PlatformData();

        // 2. Chama a classe utilitária para popular a plataforma com dados iniciais.
        InitialData.load(platformData);
        
        System.out.println("Dados iniciais carregados com sucesso!");
        System.out.println("---------------------------------------");

        // 3. A partir daqui, a aplicação iniciaria a interface de linha de comando (CLI),
        //    passando o 'platformData' já populado para que a interface possa operar sobre ele.
        //
        // Exemplo:
        // CommandLineInterface cli = new CommandLineInterface(platformData);
        // cli.start();
    }
}