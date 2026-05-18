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

    public void menu(){
        int opcion;
        do {
            System.out.println("==========================");
            System.out.println("...:::Menu Principal:::...");
            System.out.println("1) Crear empresa");
            System.out.println("2) Contratar tripulante");
            System.out.println("3) Crear terminal");
            System.out.println("4) Crear cliente");
            System.out.println("5) Crear bus");
            System.out.println("6) Crear Viaje");
            System.out.println("7) Vender pasajes");
            System.out.println("8) Listar ventas");
            System.out.println("9) Listar viajes");
            System.out.println("10) Listar pasajeros de viaje");
            System.out.println("11) Listar empresas");
            System.out.println("12) Listar llegadas/salidas de terminal");
            System.out.println("13) Listar ventas de empresa");
            System.out.println("14) Salir");
            System.out.println("---------------------------");

            opcion = Integer.parseInt(sc.nextLine());
            switch (opcion) {
                case 1:
                    createEmpresa();
                    break;
                case 2:
                    contratarTripulante();
                    break;
                case 3:
                    createTerminal();
                    break;
                case 4:
                    createCliente();
                    break;
                case 5:
                    createBus();
                    break;
                case 6:
                    createViaje();
                    break;
                case 7:
                    venderPasaje();
                    break;
                case 8:
                    listVentas();
                    break;
                case 9:
                    listViajes();
                    break;
                case 10:
                    listPasajerosViajes();
                    break;
                case 11:
                    listEmpresas();
                    break;
                case 12:
                    listLlegadasSalidasTerminal();
                    break;
                case 13:
                    listVentasEmpresas();
                    break;
                case 14:
                    System.out.println("-------------------------");
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }

        }while (opcion != 14);

    }
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
