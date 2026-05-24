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
    private void createEmpresa(){
        System.out.println("...:::: Creando una nueva Empresa ::::....");
        try {

            System.out.print("Ingrese el Rut de la empresa: ");
            String rutEmpresa = sc.nextLine();

            Rut rut = new Rut(rutEmpresa);

            System.out.println("Ingrese el nombre de la empresa: ");
            String nombre = sc.nextLine();

            System.out.println("Ingrese la URL de la empresa: ");
            String urlString = sc.nextLine();

            // Revisar esto
            ControladorEmpresas.getInstance().createEmpresa(rut, nombre,urlString);

            System.out.println("...::: Empresa Creada Exitosamente::::....");

        }catch (SistemaVentaPasajesException e){
            System.out.println("Error de creacion de la empresa: " + e.getMessage());
        }catch (Exception e){
            System.out.println("Error en la creacion de la empresa.");
        }
    }
    private void contratarTripulante(){}
    private void createTerminal(){
        System.out.println("...:::: Creando un nuevo terminal ::::....");
        try {
            System.out.print("Ingrese nombre del terminal: ");
            String terminal = sc.nextLine();

            System.out.println("Ingrese nombre de la calle: ");
            String calle = sc.nextLine();

            System.out.println("Ingrese numero de la calle: ");
            String numero = sc.nextLine();

            System.out.println("Ingrese la comuna: ");
            String comuna = sc.nextLine();


            // Revisar esto
            Direccion direccion = new Direccion(calle,numero,comuna);

            ControladorEmpresas.getInstance().createTerminal(terminal,direccion);
            System.out.println("...::: Terminal guardado exitosamente::::....");

        }catch (SistemaVentaPasajesException e){
            System.out.println("Error en la creacion de la terminal: " + e.getMessage());
        }catch (Exception e){
            System.out.println("Error en la creacion de la terminal.");
        }
    }
    private void createCliente(){}
    private void createBus(){
        System.out.println("...:::: Creando un nuevo bus ::::....");
        try {
            System.out.print("Ingrese la patente del bus: ");
            String patente = sc.nextLine();

            System.out.println("Ingrese la marca del bus: ");
            String marca = sc.nextLine();

            System.out.println("Ingrese el modelo del bus: ");
            String modelo = sc.nextLine();

            System.out.println("Ingrese el numero de asientos: ");
            int numero = sc.nextInt();

            System.out.println("::::Dato de la empresa");
            String rutEmpresa =  sc.nextLine();
            Rut rut = new Rut(rutEmpresa);


            ControladorEmpresas.getInstance().createBus(patente,marca,modelo,numero);

            System.out.println("...:::: Bus guardado exitosamente::::....");
        }catch (SistemaVentaPasajesException e){
            System.out.println("Error en la creacion del bus: " + e.getMessage());
        }catch (NumberFormatException e){
            System.out.println("Error la cantidad de asientos no es valida: ");
        }catch (Exception e){
            System.out.println("Error");
        }
    }
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
