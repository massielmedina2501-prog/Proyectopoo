import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
//Massiel Medina Vasquez
public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static SistemaVentaPasajes sistema = new SistemaVentaPasajes();
    private static DateTimeFormatter fmtFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        menu();
    }

    private static void menu() {
        int opcion;

        System.out.println(".:|.:|.:| Bienvenido al sistema de venta |:.|:.|:.");

        do {
            System.out.println("\n====== MENU ======");
            System.out.println("1. Crear Cliente");
            System.out.println("2. Crear Bus");
            System.out.println("3. Crear Viaje");
            System.out.println("4. Vender Pasajes");
            System.out.println("5. Listar Pasajeros de un Viaje");
            System.out.println("6. Listar Ventas");
            System.out.println("7. Listar Viajes");
            System.out.println("8. Consultar Viajes por Fecha");
            System.out.println("9. Salir");
            System.out.print("Opcion: ");

            opcion = Integer.parseInt(sc.nextLine());

            if (opcion < 1 || opcion > 9) {
                System.out.println("Error: opcion fuera de rango.");
                continue;
            }

            switch (opcion) {
                case 1: createCliente();
                break;
                case 2: createBus();
                break;
                case 3: createViaje();
                break;
                case 4: vendePasajes();
                break;
                case 5: listPasajerosViaje();
                break;
                case 6: listVentas();
                break;
                case 7: listViajes();
                break;
                case 8: consultarViajesPorFecha();
                break;
                case 9: System.out.println(".:||| Sistema finalizado |||:.");
            }

        } while (opcion != 9);
    }

    private static void createCliente() {
        System.out.println("\n.:| Crear Cliente |:.");

        System.out.println("Tipo identificador:");
        System.out.println(".:| 1. RUT");
        System.out.println(".:| 2. Pasaporte");
        System.out.print("...:|Seleccione: ");
        int tipoId = Integer.parseInt(sc.nextLine());

        IdPersona id;

        if (tipoId == 1) {
            System.out.print("Ingrese RUT (formato: 11.111.111-1): ");
            id = Rut.of(sc.nextLine());
        } else {
            System.out.print("Numero pasaporte: ");
            String numero = sc.nextLine();

            System.out.print("Nacionalidad: ");
            String nacionalidad = sc.nextLine();

            id = Pasaporte.of(numero, nacionalidad);
        }

        System.out.println("Tratamiento:");
        System.out.println(".:|1. Sr");
        System.out.println(".:|2. Sra");
        System.out.print("...:|Seleccione: ");
        int trat = Integer.parseInt(sc.nextLine());

        Tratamiento tratamiento = (trat == 1) ? Tratamiento.SR : Tratamiento.SRA;

        System.out.print(".:|Primer nombre: ");
        String nombre = sc.nextLine();

        System.out.print(".:|Apellido paterno: ");
        String apPat = sc.nextLine();

        System.out.print(".:|Apellido materno: ");
        String apMat = sc.nextLine();

        Nombre nom = new Nombre(tratamiento, nombre, apPat, apMat);

        System.out.print(".:|Telefono: ");
        String telefono = sc.nextLine();

        System.out.print(".:|Email: ");
        String email = sc.nextLine();

        if (sistema.createCliente(id, nom, telefono, email)) {
            System.out.println(".:|.:|Cliente creado correctamente|:.|:.");
        } else {
            System.out.println("||| No se pudo crear el cliente (o ya existe) ||||");
        }
    }

    private static void createBus() {
        System.out.println("\n.:| Crear Bus |:.");

        System.out.print(".:|Patente: ");
        String patente = sc.nextLine();

        System.out.print(".:|Marca: ");
        String marca = sc.nextLine();

        System.out.print(".:|Modelo: ");
        String modelo = sc.nextLine();

        System.out.print(".:|Numero de asientos: ");
        int asientos = Integer.parseInt(sc.nextLine());

        if (sistema.createBus(patente, marca, modelo, asientos)) {
            System.out.println(".:|.:| Bus creado correctamente |:.|:.");
        } else {
            System.out.println("||| No se pudo crear el bus (patente existente) |||");
        }
    }

    private static void createViaje() {
        System.out.println("\n.:| Crear Viaje |:.");

        System.out.print(".:|Fecha (dd/MM/yyyy): ");
        LocalDate fecha = LocalDate.parse(sc.nextLine(), fmtFecha);

        System.out.print(".:|Hora (hh:mm): ");
        LocalTime hora = LocalTime.parse(sc.nextLine());

        System.out.print(".:|Precio: ");
        int precio = Integer.parseInt(sc.nextLine());

        System.out.print(".:|Patente del bus: ");
        String patente = sc.nextLine();

        if (sistema.createViaje(fecha, hora, precio, patente)) {
            System.out.println(".:|.:| Viaje creado correctamente |:.|:.");
        } else {
            System.out.println("||| No se pudo crear el viaje |||");
        }
    }

    private static void vendePasajes() {
        System.out.println("\n.:| Venta de Pasajes |:.");

        System.out.print(".:| ID venta: ");
        String idVenta = sc.nextLine();

        System.out.println(".:|Tipo documento:");
        System.out.println(".:|1. Boleta");
        System.out.println(".:|2. Factura");
        System.out.print(".:|Seleccione: ");
        int tipoDoc = Integer.parseInt(sc.nextLine());

        TipoDocumento tipo = (tipoDoc == 1)
                ? TipoDocumento.BOLETA
                : TipoDocumento.FACTURA;

        System.out.print(".:| Fecha venta: ");
        LocalDate fechaVenta = LocalDate.parse(sc.nextLine(), fmtFecha);

        System.out.println(".:| Tipo ID cliente:");
        System.out.println(".:| 1. RUT");
        System.out.println(".:| 2. Pasaporte");
        System.out.print("...:| Seleccione: ");
        int tipoCliente = Integer.parseInt(sc.nextLine());

        IdPersona idCliente;

        if (tipoCliente == 1) {
            System.out.print(".:| RUT cliente: ");
            idCliente = Rut.of(sc.nextLine());
        } else {
            System.out.print(".:| Numero pasaporte cliente: ");
            String numero = sc.nextLine();

            System.out.print(".:| Nacionalidad cliente: ");
            String nacionalidad = sc.nextLine();

            idCliente = Pasaporte.of(numero, nacionalidad);
        }

        if (!sistema.iniciaVenta(idVenta, tipo, fechaVenta, idCliente)) {
            System.out.println("||| No se puede iniciar la venta |||");
            return;
        }

        System.out.print(".:| Cantidad de pasajes: ");
        int cantidad = Integer.parseInt(sc.nextLine());

        System.out.print(".:| Fecha viaje: ");
        LocalDate fechaViaje = LocalDate.parse(sc.nextLine(), fmtFecha);

        String[][] viajes = sistema.getHorariosDisponibles(fechaViaje);

        if (viajes.length == 0) {
            System.out.println("||| No existen viajes disponibles |||");
            return;
        }

        System.out.println("\n.:| Viajes disponibles:");

        for (int i = 0; i < viajes.length; i++) {
            System.out.println(
                    (i + 1) + ". Hora: " + viajes[i][0]
                            + " | Bus: " + viajes[i][1]
                            + " | Precio: " + viajes[i][2]
                            + " | Disponibles: " + viajes[i][3]
            );
        }

        System.out.print(".:| Seleccione viaje: ");
        int seleccion = Integer.parseInt(sc.nextLine());

        LocalTime hora = LocalTime.parse(viajes[seleccion - 1][0]);
        String patente = viajes[seleccion - 1][1];

        String[][] asientos = sistema.listAsientosDeViaje(fechaViaje, hora, patente);

        if (asientos.length == 0) {
            System.out.println("||| No existe viaje |||");
            return;
        }

        System.out.println("\n.:| Asientos:");

        for (String[] asiento : asientos) {
            System.out.println("Asiento " + asiento[0] + ": " + asiento[1]);
        }

        for (int i = 0; i < cantidad; i++) {
            System.out.println("\nPasaje " + (i + 1));

            System.out.print("Numero asiento: ");
            int asiento = Integer.parseInt(sc.nextLine());

            System.out.println(".:| Tipo ID pasajero:");
            System.out.println(".:| 1. RUT");
            System.out.println(".:| 2. Pasaporte");
            System.out.print(".:| Seleccione: ");
            int tipoPas = Integer.parseInt(sc.nextLine());

            IdPersona idPasajero;

            if (tipoPas == 1) {
                System.out.print(".:| RUT pasajero: ");
                idPasajero = Rut.of(sc.nextLine());
            } else {
                System.out.print(".:| Pasaporte pasajero: ");
                System.out.print(".:| Numero pasaporte pasajero: ");
                String numero = sc.nextLine();

                System.out.print(".:| Nacionalidad pasajero: ");
                String nacionalidad = sc.nextLine();

                idPasajero = Pasaporte.of(numero, nacionalidad);
            }

            if (sistema.getNombrePasajero(idPasajero).equals("")) {

                System.out.println("||| Pasajero no existe ||| Crear pasajero |||");

                System.out.print(".:|| Nombre pasajero: ");
                String nomPas = sc.nextLine();

                System.out.print(".:|| Apellido paterno: ");
                String apPat = sc.nextLine();

                System.out.print(".:|| Apellido materno: ");
                String apMat = sc.nextLine();

                Nombre nombrePas = new Nombre(
                        Tratamiento.SR,
                        nomPas,
                        apPat,
                        apMat
                );

                System.out.print(".:|| Telefono pasajero: ");
                String telefono = sc.nextLine();

                System.out.print(".:|| Nombre contacto: ");
                String nomContacto = sc.nextLine();

                Nombre contacto = new Nombre(
                        Tratamiento.SR,
                        nomContacto,
                        "",
                        ""
                );

                System.out.print(".:|| Telefono contacto: ");
                String telContacto = sc.nextLine();

                sistema.createPasajero(
                        idPasajero,
                        nombrePas,
                        telefono,
                        contacto,
                        telContacto
                );
            }

            if (sistema.vendePasaje(idVenta, fechaViaje, hora, patente, asiento, idPasajero)) {
                System.out.println(".:| Pasaje agregado |:.");
            } else {
                System.out.println(" ||| No se pudo vender pasaje |||");
            }
        }

        int total = sistema.getMontoVenta(idVenta, tipo);
        System.out.println(".:||| Monto total: $" + total);
    }

    private static void listPasajerosViaje() {
        System.out.println("\n.:| Listado pasajeros viaje |:.");

        System.out.print(".:| Fecha: ");
        LocalDate fecha = LocalDate.parse(sc.nextLine(), fmtFecha);

        System.out.print(".:| Hora: ");
        LocalTime hora = LocalTime.parse(sc.nextLine());

        System.out.print(".:| Patente: ");
        String patente = sc.nextLine();

        String[][] lista = sistema.listPasajeros(fecha, hora, patente);

        if (lista.length == 0) {
            System.out.println("||| No existe informacion para ese viaje |||");
            return;
        }

        for (String[] fila : lista) {
            System.out.println(
                    fila[0] + " | "
                            + fila[1] + " | "
                            + fila[2] + " | "
                            + fila[3]
            );
        }
    }

    private static void listVentas() {
        System.out.println("\n.:| Listado Ventas |:.");

        String[][] ventas = sistema.listVentas();

        if (ventas.length == 0) {
            System.out.println(" ||| No existen ventas registradas |||");
            return;
        }

        for (String[] fila : ventas) {
            System.out.println(
                    "Documento: " + fila[0]
                            + " | Tipo: " + fila[1]
                            + " | Fecha: " + fila[2]
                            + " | Cliente: " + fila[4]
                            + " | Cantidad Pasajes: " + fila[5]
                            + " | Monto: $" + fila[6]
            );
        }
    }

    private static void listViajes() {
        System.out.println("\n.:| Listado Viajes |:.");

        String[][] viajes = sistema.listViajes();

        if (viajes.length == 0) {
            System.out.println("||| No existen viajes registrados |||");
            return;
        }

        for (String[] fila : viajes) {
            for (String dato : fila) {
                System.out.print(dato + " | ");
            }
            System.out.println();
        }
    }

    private static void consultarViajesPorFecha() {
        System.out.println("\n.:| Viajes por Fecha |:.");

        System.out.print(".:| Fecha: ");
        LocalDate fecha = LocalDate.parse(sc.nextLine(), fmtFecha);

        String[][] viajes = sistema.getHorariosDisponibles(fecha);

        if (viajes.length == 0) {
            System.out.println("||| No existen viajes |||");
            return;
        }

        for (String[] fila : viajes) {
            System.out.println(
                    "Hora: " + fila[0]
                            + " | Bus: " + fila[1]
            );
        }
    }
}