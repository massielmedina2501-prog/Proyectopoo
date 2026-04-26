import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
//Massiel Medina
public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static SistemaVentaPasajes sistema = new SistemaVentaPasajes();
    private static DateTimeFormatter fmtFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        menu();
    }

    private static void menu() {
        int opcion;

        System.out.println(".:|.:|.:| Bienvenido al sistema de venta de pasajes |:.|:.|:.");

        do {
            System.out.println("\n====== MENÚ PRINCIPAL ======");
            System.out.println("1. Crear cliente");
            System.out.println("2. Crear bus");
            System.out.println("3. Crear viaje");
            System.out.println("4. Vender pasajes");
            System.out.println("5. Listar pasajeros de un viaje");
            System.out.println("6. Listar ventas");
            System.out.println("7. Listar viajes");
            System.out.println("8. Consultar viajes por fecha");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1 : createCliente();
                    break;
                case 2 : createBus();
                    break;
                case 3 : createViaje();
                    break;
                case 4 : vendePasajes();
                    break;
                case 5 : listPasajerosViaje();
                    break;
                case 6 : listVentas();
                    break;
                case 7 : listViajes();
                    break;
                case 8 : consultarViajesPorFecha();
                    break;
            }

        } while (opcion != 9);
    }

    private static void createCliente() {
        System.out.println("\n.:| Crear cliente |:.");

        System.out.print("Ingrese RUT (ej: 12.345.678-9): ");
        IdPersona id = Rut.of(sc.nextLine());

        System.out.print("Ingrese tratamiento (SR/SRA): ");
        String t = sc.nextLine().toUpperCase();
        Tratamiento trat = t.equals("SRA") ? Tratamiento.SRA : Tratamiento.SR;

        System.out.print("Ingrese nombres: ");
        String nombres = sc.nextLine();

        System.out.print("Ingrese apellido paterno: ");
        String apPat = sc.nextLine();

        System.out.print("Ingrese apellido materno: ");
        String apMat = sc.nextLine();

        Nombre nom = new Nombre(trat, nombres, apPat, apMat);

        System.out.print("Ingrese teléfono de contacto: ");
        String fono = sc.nextLine();

        System.out.print("Ingrese correo electrónico: ");
        String email = sc.nextLine();

        boolean creado = sistema.createCliente(id, nom, fono, email);

        if (creado) {
            System.out.println(".:| Cliente creado correctamente |:.");
        } else {
            System.out.println(".:| Cliente ya creado vuelva a intentarlo |:.");
        }
    }

    private static void createBus() {
        System.out.println("\n.:| Crear bus |:.");

        System.out.print("Ingrese patente del bus: ");
        String pat = sc.nextLine();

        System.out.print("Ingrese marca del bus: ");
        String marca = sc.nextLine();

        System.out.print("Ingrese modelo del bus: ");
        String mod = sc.nextLine();

        System.out.print("Ingrese cantidad de asientos: ");
        int asientos = Integer.parseInt(sc.nextLine());

        sistema.createBus(pat, marca, mod, asientos);
    }

    private static void createViaje() {
        System.out.println("\n.:| Crear viaje |:.");

        System.out.print("Ingrese fecha del viaje (dd/MM/yyyy): ");
        LocalDate f = LocalDate.parse(sc.nextLine(), fmtFecha);

        System.out.print("Ingrese hora de salida (HH:mm): ");
        LocalTime h = LocalTime.parse(sc.nextLine());

        System.out.print("Ingrese precio del pasaje: ");
        int p = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese patente del bus: ");
        String pat = sc.nextLine();

        sistema.createViaje(f, h, p, pat);
    }

    private static void vendePasajes() {
        System.out.println("\n.:| Venta de pasajes |:.");

        System.out.print("Ingrese ID de la venta: ");
        String idVenta = sc.nextLine();

        TipoDocumento tipo = TipoDocumento.BOLETA;

        System.out.print("Ingrese fecha de la venta (dd/MM/yyyy): ");
        LocalDate f = LocalDate.parse(sc.nextLine(), fmtFecha);

        System.out.print("Ingrese RUT del cliente: ");
        IdPersona idCliente = Rut.of(sc.nextLine());

        if (!sistema.iniciaVenta(idVenta, tipo, f, idCliente)) {
            System.out.println(".:| Error al iniciar la venta |:.");
            return;
        }

        System.out.print("Ingrese fecha del viaje (dd/MM/yyyy): ");
        LocalDate fv = LocalDate.parse(sc.nextLine(), fmtFecha);

        String[][] viajes = sistema.getHorariosDisponibles(fv);

        System.out.println("\nHorarios disponibles:");
        for (int i = 0; i < viajes.length; i++) {
            System.out.println((i+1) + ". Hora: " + viajes[i][0] + " | Bus: " + viajes[i][1]);
        }

        System.out.print("Seleccione una opción: ");
        int sel = Integer.parseInt(sc.nextLine());

        String pat = viajes[sel-1][1];
        LocalTime hora = LocalTime.parse(viajes[sel-1][0]);

        String[][] asientos = sistema.listAsientosDeViaje(fv, hora, pat);

        System.out.println("\nEstado de asientos:");
        for (String[] a : asientos) {
            System.out.println("Asiento " + a[0] + ": " + a[1]);
        }

        System.out.print("Ingrese número de asiento: ");
        int asiento = Integer.parseInt(sc.nextLine());

        System.out.print("Ingrese RUT del pasajero: ");
        IdPersona idPas = Rut.of(sc.nextLine());

        sistema.vendePasaje(idVenta, fv, hora, pat, asiento, idPas);

        System.out.println(".:| Venta realizada correctamente |:.");
    }

    private static void listPasajerosViaje() {
        System.out.println("\n.:| Listar pasajeros de un viaje |:.");

        System.out.print("Ingrese fecha del viaje (dd/MM/yyyy): ");
        LocalDate f = LocalDate.parse(sc.nextLine(), fmtFecha);

        System.out.print("Ingrese hora del viaje (HH:mm): ");
        LocalTime h = LocalTime.parse(sc.nextLine());

        System.out.print("Ingrese patente del bus: ");
        String p = sc.nextLine();

        String[][] lista = sistema.listPasajeros(f, h, p);

        System.out.println("\nPasajeros:");
        for (String[] fila : lista) {
            System.out.println(fila[0] + " - " + fila[2]);
        }
    }

    private static void listVentas() {
        System.out.println("\n.:| Listar ventas |:.");

        String[][] v = sistema.listVentas();

        for (String[] fila : v) {
            System.out.println("ID: " + fila[0] + " | Total: " + fila[6]);
        }
    }

    private static void listViajes() {
        System.out.println("\n.:| Listar viajes |:.");

        String[][] v = sistema.listViajes();

        for (String[] fila : v) {
            System.out.println("Fecha: " + fila[0] + " | Hora: " + fila[1] + " | Precio: $" + fila[2]);
        }
    }

    private static void consultarViajesPorFecha() {
        System.out.println("\n.:| Consultar viajes por fecha |:.");

        System.out.print("Ingrese fecha (dd/MM/yyyy): ");
        LocalDate f = LocalDate.parse(sc.nextLine(), fmtFecha);

        String[][] v = sistema.getHorariosDisponibles(f);

        System.out.println("\nViajes disponibles:");
        for (String[] fila : v) {
            System.out.println("Hora: " + fila[0] + " | Bus: " + fila[1]);
        }
    }
}
