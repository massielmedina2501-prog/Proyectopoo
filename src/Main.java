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
        int opcion = 0;

        System.out.println(".:|.:|.:|.:| Bienvenido al sistema de venta |:.|:.|:.|:.");
        do {
            System.out.println("\n====== MENU PRINCIPAL ======");
            System.out.println("1. Crear Cliente");
            System.out.println("2. Crear Bus");
            System.out.println("3. Crear Viaje");
            System.out.println("4. Vender Pasajes");
            System.out.println("5. Listar Pasajeros de un Viaje");
            System.out.println("6. Listar Ventas");
            System.out.println("7. Listar Viajes");
            System.out.println("8. Consultar Viajes por Fecha");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {
                    case 1:
                        createCliente();
                        break;
                    case 2:
                        createBus();
                        break;
                    case 3:
                        createViaje();
                        break;
                    case 4:
                        vendePasajes();
                        break;
                    case 5:
                        listPasajerosViaje();
                        break;
                    case 6:
                        listVentas();
                        break;
                    case 7:
                        listViajes();
                        break;
                    case 8:
                        consultarViajesPorFecha();
                        break;
                    case 9:
                        System.out.println("\n.::| Cerrando sistema |::.");
                        break;
                    default:
                        System.out.println("\nOpción inválida.");
                }

            } catch (Exception e) {
                System.out.println("\n.:| Error en el ingreso de datos. Intente nuevamente |:...");
                opcion = 0;
            }

        } while (opcion != 9);
    }


    private static void createCliente() {
        System.out.println("\n.:| Crear un nuevo Cliente | : . ");

        System.out.print("\nRut[1] o Pasaporte[2] : ");
        int tipoId = Integer.parseInt(sc.nextLine());
        IdPersona id = null;

        if (tipoId == 1) {
            System.out.print("               R.U.T : ");
            String rutString = sc.nextLine();
            id = Rut.of(rutString);
        } else {
            System.out.print("           Pasaporte : ");
            String numPas = sc.nextLine();
            System.out.print("        Nacionalidad : ");
            String nac = sc.nextLine();
            id = new Pasaporte(numPas, nac);
        }

        System.out.print("      Sr.[1] o Sra. [2] : ");
        int tipoTra = Integer.parseInt(sc.nextLine());
        Tratamiento tratamiento = (tipoTra == 1) ? Tratamiento.SR : Tratamiento.SRA;

        System.out.print("               Nombres : ");
        String nom = sc.nextLine();
        System.out.print("       Apellido Paterno : ");
        String apeP = sc.nextLine();
        System.out.print("       Apellido Materno : ");
        String apeM = sc.nextLine();

        Nombre nombreCompleto = new Nombre(tratamiento, nom, apeP, apeM);


        System.out.print("        Telefono movil : ");
        String telefono = sc.nextLine();
        System.out.print("                 Email : ");
        String email = sc.nextLine();

        if (sistema.createCliente(id, nombreCompleto, email)) {
            System.out.println("\n.:| Cliente guardado exitosamente |:.");
        }
    }

    private static void createBus() {
        System.out.println("\n.:| Crear Bus | : . ");
        System.out.print("Patente: ");
        String pat = sc.nextLine();
        System.out.print("Marca: ");
        String marca = sc.nextLine();
        System.out.print("Modelo: ");
        String mod = sc.nextLine();
        System.out.print("Numero de asientos: ");
        int asientos = Integer.parseInt(sc.nextLine());

        if (sistema.createBus(pat, marca, mod, asientos)) {
            System.out.println(".:| Bus creado |:.");
        }
    }

    private static void createViaje() {
        System.out.print("Fecha (dd/mm/yyyy): ");
        LocalDate fec = LocalDate.parse(sc.nextLine(), fmtFecha);
        System.out.print("Hora (HH:mm): ");
        LocalTime hor = LocalTime.parse(sc.nextLine());
        System.out.print("Precio: ");
        int pre = Integer.parseInt(sc.nextLine());
        System.out.print("Patente Bus: ");
        String pat = sc.nextLine();

        if (sistema.createViaje(fec, hor, pre, pat)) {
            System.out.println(".:| Viaje programado |:.");
        }
    }

    private static void vendePasajes() {
        System.out.println("\n.:| Venta de pasajes |:.\n");

        System.out.println(".:| Datos de la Venta |:.");

        System.out.print("ID Documento : ");
        String idVenta = sc.nextLine();

        System.out.print("Tipo documento: [1] Boleta [2] Factura : ");
        int tipoDocInput = Integer.parseInt(sc.nextLine());
        TipoDocumento tipoDoc = (tipoDocInput == 1) ? TipoDocumento.BOLETA : TipoDocumento.FACTURA;

        System.out.print("Fecha de venta[dd/mm/yyyy] : ");
        LocalDate fechaVenta = LocalDate.parse(sc.nextLine(), fmtFecha);

        System.out.println("\n.:| Datos del cliente |:.");

        System.out.print("Rut[1] o Pasaporte[2] : ");
        int tipoCliente = Integer.parseInt(sc.nextLine());

        String idCliente;

        if (tipoCliente == 1) {
            System.out.print("R.U.T : ");
            idCliente = sc.nextLine();
        } else {
            System.out.print("Pasaporte : ");
            idCliente = sc.nextLine();
        }


        if (!sistema.iniciaVenta(idVenta, tipoDoc, fechaVenta, idCliente)) {
            System.out.println("\n .:|| No se puede realizar la venta ||:.");
            return;
        }


        System.out.println("\n.:| Pasajes a vender |:.");

        System.out.print("Cantidad de pasajes : ");
        int cant = Integer.parseInt(sc.nextLine());

        System.out.print("Fecha de viaje[dd/mm/yyyy] : ");
        LocalDate fechaViaje = LocalDate.parse(sc.nextLine(), fmtFecha);


        String[][] horarios = sistema.getHorariosDisponibles(fechaViaje);

        if (horarios == null || horarios.length == 0) {
            System.out.println("\n No hay viajes disponibles.");
            return;
        }

        System.out.println("\n.:| Listado de horarios disponibles |:.");

        System.out.println("*---*----------*---------*---------*----------*");
        System.out.println("|   |   BUS    |  SALIDA |  VALOR  | ASIENTOS |");
        System.out.println("|---|----------|---------|---------|----------|");

        for (int i = 0; i < horarios.length; i++) {
            System.out.printf("%-3d | %-8s |  %-5s  | $%-6s |    %-4s  |\n",
                    (i + 1), horarios[i][0], horarios[i][1], horarios[i][2], horarios[i][3]);
            System.out.println("|---|----------|---------|---------|----------|");
        }
        System.out.println("*---*----------*---------*---------*----------*");

        System.out.print("Seleccione viaje en [1.." + horarios.length + "] : ");
        int sel = Integer.parseInt(sc.nextLine());

        String patBus = horarios[sel - 1][0];
        LocalTime horaBus = LocalTime.parse(horarios[sel - 1][1]);


        String[][] mapa = sistema.listAsientosDeViaje(fechaViaje, horaBus, patBus);

        System.out.println("\n.:| Asientos disponibles |:.");
        System.out.println("*---*---*---*---*");

        for (String[] fila : mapa) {
            System.out.printf("| %-2s | %-2s |   | %-2s | %-2s |\n",
                    fila[0], fila[1], fila[2], fila[3]);
            System.out.println("|---|---|---|---|");
        }

        System.out.println("*---*---*---*---*");

        System.out.print("Seleccione sus asientos [separe por ,] : ");
        String[] asientosInput = sc.nextLine().split(",");

        String[] ruts = new String[cant];
        String[] nombres = new String[cant];

        for (int i = 0; i < asientosInput.length; i++) {
            System.out.println("\n:| Datos pasajero " + (i + 1));

            System.out.print("Rut[1] o Pasaporte[2] : ");
            int tipo = Integer.parseInt(sc.nextLine());

            String rut = "";
            IdPersona id;

            if (tipo == 1) {
                System.out.print("R.U.T : ");
                rut = sc.nextLine();
                id = Rut.of(rut);
            } else {
                System.out.print("Pasaporte : ");
                String pas = sc.nextLine();
                System.out.print("Nacionalidad : ");
                String nac = sc.nextLine();
                rut = pas;
                id = new Pasaporte(pas, nac);
            }

            System.out.print("Nombre pasajero : ");
            String nombrePasajero = sc.nextLine();

            ruts[i] = rut;
            nombres[i] = nombrePasajero;

            int nroAsiento = Integer.parseInt(asientosInput[i].trim());

            sistema.vendePasaje(idVenta, fechaViaje.toString(), horaBus, patBus, nroAsiento, id);
        }


        int total = sistema.getMontoVenta(idVenta, tipoDoc);
        System.out.println("\n.:| Monto total de la venta: $" + total);

        System.out.println("\n.:|| Imprimiendo los pasajes ||:.\n");

        for (int i = 0; i < asientosInput.length; i++) {
            int nroAsiento = Integer.parseInt(asientosInput[i].trim());

            System.out.println(".:||.:||.:|| PASAJE ||:.||:.||:.");
            System.out.println("Numero de pasaje: " + (i + 1));
            System.out.println("Fecha de pasaje: " + fechaViaje.format(fmtFecha));
            System.out.println("Hora de viaje: " + horaBus);
            System.out.println("Patente bus: " + patBus);
            System.out.println("Asiento: " + nroAsiento);
            System.out.println("Rut/Pasaporte: " + ruts[i]);
            System.out.println("Nombre pasajero: " + nombres[i]);
            System.out.println("|.:.:.:.:.:.:.:.:.:.:.:.:.:.:.:.:.:.|\n");
        }

        System.out.println("\n.:|| Venta generada exitosamente ||:.");
    }


    private static void listPasajerosViaje() {
        System.out.println("\n.:| Listado de pasajeros de un viaje |:.\n");

        System.out.print("Fecha del viaje[dd/mm/yyyy] : ");
        LocalDate f = LocalDate.parse(sc.nextLine(), fmtFecha);

        System.out.print("Hora del viaje[hh:mm] : ");
        LocalTime h = LocalTime.parse(sc.nextLine());

        System.out.print("Patente bus : ");
        String p = sc.nextLine();

        String[][] lista = sistema.listPasajeros(f, h, p);


        if (lista == null || lista.length == 0) {
            System.out.println("\n.:| No existe el viaje |:.");
            return;
        }

        System.out.println("\nFecha del viaje[dd/mm/yyyy] : " + f.format(fmtFecha));
        System.out.println("Hora del viaje[hh:mm] : " + h);
        System.out.println("Patente bus : " + p);

        System.out.println("*----------*----------------*------------------------------*------------------------------*-------------------*");
        System.out.println("| ASIENTO  |   RUT/PASS     | PASAJERO                     | CONTACTO                     | TELEFONO CONTACTO |");
        System.out.println("|----------|----------------|------------------------------|------------------------------|-------------------|");

        for (String[] fila : lista) {
            System.out.printf("| %-8s | %-14s | %-28s | %-28s | %-17s |\n",
                    fila[0], fila[1], fila[2], fila[3], fila[4]);

            System.out.println("|----------|----------------|------------------------------|------------------------------|-------------------|");
        }

        System.out.println("*----------*----------------*------------------------------*------------------------------*-------------------*");
    }

    private static void listVentas() {
        System.out.println("\n.:| Listado de ventas |:.\n");

        System.out.print("Ingrese fecha [dd/mm/yyyy] : ");
        LocalDate fecha = LocalDate.parse(sc.nextLine(), fmtFecha);

        String[][] ventas = sistema.listVentas();

        if (ventas == null || ventas.length == 0) {
            System.out.println("\n.:| No existen ventas registradas |:.");
            return;
        }

        System.out.println("*------------*------------*------------*------------------*------------------------------*---------------*---------------*");
        System.out.println("| ID DOCUMENT| TIPO DOC   | FECHA      | RUT/PASAPORTE    | CLIENTE                      | CANT BOLETOS | TOTAL VENTA  |");
        System.out.println("|------------|------------|------------|------------------|------------------------------|---------------|---------------|");

        boolean hayVentas = false;

        for (String[] fila : ventas) {

            String idDoc = fila[0];
            String tipo = fila[1];
            String fec = fila[2];
            String rut = fila[3];
            String cliente = fila[4];
            String cant = fila[5];
            String total = fila[6];

            if (!fec.equals(fecha.format(fmtFecha))) continue;

            hayVentas = true;

            System.out.printf("| %-10s | %-10s | %-10s | %-16s | %-28s | %-13s | $%-12s |\n",
                    idDoc, tipo, fec, rut, cliente, cant, total);

            System.out.println("|------------|------------|------------|------------------|------------------------------|---------------|---------------|");
        }

        System.out.println("*------------*------------*------------*------------------*------------------------------*---------------*---------------*");

        if (!hayVentas) {
            System.out.println("\n.:| No existen ventas para la fecha ingresada |:.");
        }
    }
    private static void listViajes() {
        System.out.println("\n.:|  Listado de viajes |:.\n");

        String[][] viajes = sistema.listViajes();

        if (viajes == null || viajes.length == 0) {
            System.out.println(".:| No existen viajes registrados |:.");
            return;
        }


        System.out.println("*------------*--------*---------*-------------*------------*");
        System.out.println("| FECHA      | HORA   | PRECIO  | DISPONIBLES | PATENTE   |");
        System.out.println("|------------|--------|---------|-------------|------------|");

        for (String[] fila : viajes) {
            System.out.printf("| %-10s | %-6s | $%-7s | %-11s | %-10s |\n",
                    fila[0], fila[1], fila[2], fila[3], fila[4]);

            System.out.println("|------------|--------|---------|-------------|------------|");
        }

        System.out.println("*------------*--------*---------*-------------*------------*");
    }
    private static void consultarViajesPorFecha() {
        System.out.println("\n .:| Consulta viajes por fecha |:.\n");

        System.out.print("Ingrese fecha [dd/mm/yyyy] : ");
        LocalDate fecha = LocalDate.parse(sc.nextLine(), fmtFecha);

        String[][] viajes = sistema.getHorariosDisponibles(fecha);


        if (viajes == null || viajes.length == 0) {
            System.out.println("\n.:| No hay viajes disponibles para esa fecha |:.");
            return;
        }

        System.out.println("\n.:| Viajes disponibles: " + viajes.length);

        System.out.println("*------------*--------*---------*-------------*");
        System.out.println("| PATENTE   | HORA   | PRECIO  | DISPONIBLES |");
        System.out.println("|------------|--------|---------|-------------|");

        for (String[] fila : viajes) {
            System.out.printf("| %-10s | %-6s | $%-7s | %-11s |\n",
                    fila[0], fila[1], fila[2], fila[3]);

            System.out.println("|------------|--------|---------|-------------|");
        }

        System.out.println("*------------*--------*---------*-------------*");
    }
}
