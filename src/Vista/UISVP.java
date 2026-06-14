package Vista;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import Utilidades.*;
import Modelo.*;
import Controlador.*;
import Excepciones.SVPException;

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

    public void menu() {
        int opcion;
        do {
            System.out.println("==========================");
            System.out.println("...:::Menu Principal:::...");
            System.out.println("1) Crear empresa");
            System.out.println("2) Contratar tripulante");
            System.out.println("3) Crear terminal");
            System.out.println("4) Crear cliente");
            System.out.println("5) Crear bus");
            System.out.println("6) Crear Viaje ");
            System.out.println("7) Vender pasajes");
            System.out.println("8) Listar ventas");
            System.out.println("9) Listar viajes");
            System.out.println("10) Listar pasajeros");
            System.out.println("11) Listar empresas");
            System.out.println("12) Listar terminal");
            System.out.println("13) Listar ventas empresa.");
            System.out.println("14) Generar pasajes venta");
            System.out.println("15) Salir");

            try {
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
                        generatePasajesVenta();
                        break;
                    case 15:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opcion invalida");
                }
            } catch (NumberFormatException e) {
                opcion = 0;
                System.out.println("Debe ingresar un numero valido");
            }
        } while (opcion != 15);
    }

    private void createEmpresa() {
        try {
            System.out.print("Rut empresa: ");
            Rut rut = Rut.of(sc.nextLine());
            System.out.print("Nombre empresa: ");
            String nombre = sc.nextLine();
            System.out.print("URL empresa: ");
            String url = sc.nextLine();
            ControladorEmpresas.getInstance().createEmpresa(rut, nombre, url);
            System.out.println("Empresa creada exitosamente");
        } catch (SVPException e) {
            System.out.println(e.getMessage());
        }
    }

    private void contratarTripulante() {
        try {
            System.out.print("Rut empresa: ");
            Rut rutEmpresa = Rut.of(sc.nextLine());

            System.out.print("Tipo [1 Auxiliar / 2 Conductor]: ");
            int tipo = Integer.parseInt(sc.nextLine());

            System.out.print("Documento [1 Rut / 2 Pasaporte]: ");
            int tipoDoc = Integer.parseInt(sc.nextLine());
            IdPersona id = (tipoDoc == 1) ? Rut.of(sc.nextLine()) : Pasaporte.of(sc.nextLine(), sc.nextLine());

            System.out.print("Tratamiento [1 SR / 2 SRA]: ");
            Tratamiento tr = (Integer.parseInt(sc.nextLine()) == 1) ? Tratamiento.SR : Tratamiento.SRA;
            System.out.print("Nombres: ");
            String nombres = sc.nextLine();
            System.out.print("Apellido Paterno: ");
            String apPaterno = sc.nextLine();
            System.out.print("Apellido Materno: ");
            String apMaterno = sc.nextLine();
            Nombre nombre = new Nombre(tr, nombres, apPaterno, apMaterno);
            System.out.print("Calle: ");
            String calle = sc.nextLine();
            System.out.print("Numero: ");
            int numero = Integer.parseInt(sc.nextLine());
            System.out.print("Comuna: ");
            String comuna = sc.nextLine();
            Direccion dir = new Direccion(calle, numero, comuna);
            if (tipo == 1) {
                ControladorEmpresas.getInstance().hireAuxiliarForEmpresa(rutEmpresa, id, nombre, dir);
            } else {
                ControladorEmpresas.getInstance().hireConductorForEmpresa(rutEmpresa, id, nombre, dir);
            }
            System.out.println("Tripulante contratado exitosamente");
        } catch (SVPException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al contratar");
        }
    }

    private void createTerminal() {
        try {
            System.out.print("Nombre terminal: ");
            String nombre = sc.nextLine();
            System.out.print("Calle: ");
            String calle = sc.nextLine();
            System.out.print("Numero: ");
            int numero = Integer.parseInt(sc.nextLine());
            System.out.print("Comuna: ");
            String comuna = sc.nextLine();
            ControladorEmpresas.getInstance().createTerminal(nombre, new Direccion(calle, numero, comuna));
            System.out.println("Terminal creada exitosamente");
        } catch (SVPException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al crear terminal");
        }
    }

    private void createCliente() {
        try {
            System.out.print("Doc [1 Rut / 2 Pasaporte]: ");
            int tipoDoc = Integer.parseInt(sc.nextLine());
            IdPersona id = (tipoDoc == 1) ? Rut.of(sc.nextLine()) : Pasaporte.of(sc.nextLine(), sc.nextLine());

            System.out.print("Tratamiento [1 SR / 2 SRA]: ");
            Tratamiento tr = (Integer.parseInt(sc.nextLine()) == 1) ? Tratamiento.SR : Tratamiento.SRA;

            System.out.print("Nombres: ");
            String nombres = sc.nextLine();
            System.out.print("Apellido Paterno: ");
            String apPaterno = sc.nextLine();
            System.out.print("Apellido Materno: ");
            String apMaterno = sc.nextLine();
            Nombre nombre = new Nombre(tr, nombres, apPaterno, apMaterno);
            System.out.print("Email: ");
            String email = sc.nextLine();
            SistemaVentaPasajes.getInstance().createCliente(id, nombre, email);
            System.out.println("Cliente creado exitosamente");
        } catch (SVPException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al crear cliente");
        }
    }

    private void createBus() {
        try {
            System.out.print("Patente: ");
            String pat = sc.nextLine();
            System.out.print("Marca: ");
            String mar = sc.nextLine();
            System.out.print("Modelo: ");
            String mod = sc.nextLine();
            System.out.print("Asientos: ");
            int cant = Integer.parseInt(sc.nextLine());
            System.out.print("Rut empresa: ");
            Rut rut = Rut.of(sc.nextLine());
            ControladorEmpresas.getInstance().createBus(pat, mar, mod, cant, rut);
            System.out.println("Bus creado exitosamente");
        } catch (SVPException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al crear bus");
        }
    }

    private void createViaje() {
        try {
            DateTimeFormatter fF = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter fH = DateTimeFormatter.ofPattern("HH:mm");

            System.out.print("Fecha [dd/MM/yyyy]: ");
            LocalDate fecha = LocalDate.parse(sc.nextLine(), fF);

            System.out.print("Hora [HH:mm]: ");
            LocalTime hora = LocalTime.parse(sc.nextLine(), fH);

            System.out.print("Precio: ");
            int precio = Integer.parseInt(sc.nextLine());

            System.out.print("Duracion (minutos): ");
            int dur = Integer.parseInt(sc.nextLine());

            System.out.print("Patente del bus: ");
            String pat = sc.nextLine();


            System.out.print("Comuna salida: ");
            String salida = sc.nextLine();

            System.out.print("Comuna llegada: ");
            String llegada = sc.nextLine();
            String[] comunas = {salida, llegada};

            System.out.print("Cantidad tripulantes [1 o 2]: ");
            int cT = Integer.parseInt(sc.nextLine());


            IdPersona[] trip = new IdPersona[cT + 1];
            for (int i = 0; i < trip.length; i++) {
                System.out.println("--- Tripulante " + (i + 1) + " ---");
                System.out.print("Doc [1 Rut / 2 Pasaporte]: ");
                int tD = Integer.parseInt(sc.nextLine());

                if (tD == 1) {
                    System.out.print("Rut: ");
                    trip[i] = Rut.of(sc.nextLine());
                } else {
                    System.out.print("Numero pasaporte: ");
                    String num = sc.nextLine();
                    System.out.print("Nacionalidad: ");
                    String nac = sc.nextLine();
                    trip[i] = Pasaporte.of(num, nac);
                }
            }

            SistemaVentaPasajes.getInstance().createViaje(fecha, hora, precio, dur, pat, trip, comunas);
            System.out.println("Viaje creado exitosamente");

        } catch (SVPException e) {
            System.out.println(e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha u hora invalido");
        } catch (Exception e) {
            System.out.println("Error al crear viaje");
        }
    }

    private void venderPasaje() {
        try {
            System.out.print("ID documento: ");
            String idDoc = sc.nextLine();
            System.out.print("Tipo documento [1 Boleta / 2 Factura]: ");
            int td = Integer.parseInt(sc.nextLine());
            TipoDocumento tipoDoc = (td == 1) ? TipoDocumento.BOLETA : TipoDocumento.FACTURA;


            System.out.print("Fecha viaje [dd/MM/yyyy]: ");
            LocalDate fecha = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            System.out.print("Comuna salida: ");
            String sal = sc.nextLine();
            System.out.print("Comuna llegada: ");
            String lleg = sc.nextLine();

            System.out.print("Cliente [1 Rut / 2 Pasaporte]: ");
            int tD = Integer.parseInt(sc.nextLine());
            IdPersona cliente = (tD == 1) ? Rut.of(sc.nextLine()) : Pasaporte.of(sc.nextLine(), sc.nextLine());

            System.out.print("Cantidad pasajes: ");
            int cant = Integer.parseInt(sc.nextLine());

            SistemaVentaPasajes c = SistemaVentaPasajes.getInstance();
            c.iniciaVenta(idDoc, tipoDoc, fecha, sal, lleg, cliente, cant);


            String[][] horarios = c.getHorariosDisponibles(fecha, sal, lleg, cant);
            if (horarios.length == 0) {
                System.out.println("No existen viajes disponibles para esos datos.");
                return;
            }

            System.out.println("\nViajes Disponibles:");
            for (int i = 0; i < horarios.length; i++) {

                System.out.println((i + 1) + ") " + horarios[i][0] + " " + horarios[i][1] + " - " + horarios[i][2]);
            }

            System.out.print("Seleccione viaje: ");
            int op = Integer.parseInt(sc.nextLine());
            String pat = horarios[op - 1][0];
            LocalTime hora = LocalTime.parse(horarios[op - 1][1], DateTimeFormatter.ofPattern("HH:mm"));


            String[] asientos = c.listAsientosDeViaje(fecha, hora, pat);
            System.out.println("Asientos disponibles: ");
            for (String a : asientos) System.out.print(a + " ");
            System.out.println();

            for (int i = 0; i < cant; i++) {
                System.out.print("Numero asiento para pasajero " + (i + 1) + ": ");
                int as = Integer.parseInt(sc.nextLine());
                System.out.print("Pasajero [1 Rut / 2 Pasaporte]: ");
                int tP = Integer.parseInt(sc.nextLine());
                IdPersona pas = (tP == 1) ? Rut.of(sc.nextLine()) : Pasaporte.of(sc.nextLine(), sc.nextLine());
                c.vendePasaje(idDoc, tipoDoc, fecha, hora, pat, as, pas);
            }

            System.out.print("Pago [1 Efectivo / 2 Tarjeta]: ");
            int p = Integer.parseInt(sc.nextLine());
            if (p == 1) c.pagaVenta(idDoc, tipoDoc);
            else {
                System.out.print("Numero tarjeta: ");
                c.pagaVenta(idDoc, tipoDoc, Long.parseLong(sc.nextLine()));
            }

            System.out.println("Venta realizada exitosamente");

        } catch (SVPException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al vender pasaje: revise los datos ingresados.");
        }
    }

    private void listVentas() {

        try {

            String[][] ventas = SistemaVentaPasajes.getInstance().listVentas();

            for (String[] venta : ventas) {

                for (String dato : venta) {
                    System.out.print(dato + "\t");
                }

                System.out.println();
            }

        } catch (Exception e) {
            System.out.println("Error al listar ventas");
        }
    }

    private void listViajes() {

        try {

            String[][] viajes = SistemaVentaPasajes.getInstance().listViajes();

            for (String[] viaje : viajes) {

                for (String dato : viaje) {
                    System.out.print(dato + "\t");
                }

                System.out.println();
            }

        } catch (Exception e) {
            System.out.println("Error al listar viajes");
        }
    }

    private void listPasajerosViajes() {

        try {

            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

            System.out.print("Fecha viaje: ");
            LocalDate fecha = LocalDate.parse(sc.nextLine(), formatoFecha);

            System.out.print("Hora viaje: ");
            LocalTime hora = LocalTime.parse(sc.nextLine(), formatoHora);

            System.out.print("Patente bus: ");
            String patente = sc.nextLine();

            String[][] pasajeros = SistemaVentaPasajes.getInstance()
                    .listPasajerosViaje(fecha, hora, patente);

            for (String[] pasajero : pasajeros) {

                for (String dato : pasajero) {
                    System.out.print(dato + "\t");
                }

                System.out.println();
            }

        } catch (SVPException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al listar pasajeros");
        }
    }

    private void listEmpresas() {

        try {

            String[][] empresas = ControladorEmpresas.getInstance().listEmpresas();

            for (String[] empresa : empresas) {

                for (String dato : empresa) {
                    System.out.print(dato + "\t");
                }

                System.out.println();
            }

        } catch (Exception e) {
            System.out.println("Error al listar empresas");
        }
    }

    private void listLlegadasSalidasTerminal() {

        try {

            System.out.print("Nombre terminal: ");
            String terminal = sc.nextLine();

            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            System.out.print("Fecha: ");
            LocalDate fecha = LocalDate.parse(sc.nextLine(), formatoFecha);

            String[][] datos = ControladorEmpresas.getInstance().listLlegadasSalidasTerminal(terminal, fecha);

            for (String[] fila : datos) {

                for (String dato : fila) {
                    System.out.print(dato + "\t");
                }

                System.out.println();
            }

        } catch (SVPException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al listar llegadas y salidas");
        }
    }

    private void listVentasEmpresas() {

        try {

            System.out.print("Ingrese rut empresa: ");
            Rut rut = Rut.of(sc.nextLine());

            String[][] ventas = ControladorEmpresas.getInstance()
                    .listVentasEmpresa(rut);

            for (String[] venta : ventas) {

                for (String dato : venta) {
                    System.out.print(dato + "\t");
                }

                System.out.println();
            }

        } catch (Exception e) {
            System.out.println("Error al listar ventas empresas");
        }
    }

    private void generatePasajesVenta() {

        try {

            System.out.print("ID documento: ");
            String idDoc = sc.nextLine();

            System.out.print("Tipo documento [1 Boleta / 2 Factura]: ");
            int op = Integer.parseInt(sc.nextLine());

            TipoDocumento tipo =
                    (op == 1) ? TipoDocumento.BOLETA
                            : TipoDocumento.FACTURA;

            SistemaVentaPasajes.getInstance()
                    .generatePasajesVenta(idDoc, tipo);

            System.out.println("Pasajes generados correctamente.");

        } catch (SVPException e) {

            System.out.println(e.getMessage());

        } catch (Exception e) {

            System.out.println("Error al generar pasajes.");
        }
    }
}