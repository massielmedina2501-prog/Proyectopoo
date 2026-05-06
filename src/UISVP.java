import java.util.Scanner;

public class UISVP {
    private Scanner sc;
    private static UISVP instance = null;
    private UISVP() {
        sc = new Scanner(System.in);
    }
    public static UISVP getInstance() {
        if (instance == null) {
            instance = new UISVP();
        }
        return instance;
    }

    public void menu(){}
    private void createEmpresa(){}
    private void contratarTripulante(){}
    private void createTerminal(){}
    private void createCliente(){}
    private void createBus(){}
    private void createViaje(){}
    private void venderPasaje(){}
    private void pagaVentaPasaje(){}
    private void listVentas(){}
    private void listViajes(){}
    private void listPasajerosViajes(){}
    private void listEmpresas(){}
    private void listLlegadasSalidasTerminal(){}
    private void listVentasEmpresas(){}

}
