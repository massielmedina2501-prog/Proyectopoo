package Vista;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import Utilidades.*;
import Modelo.*;
import Controlador.*;
import Excepciones.*;

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
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opcion invalida");
                }

            } catch (NumberFormatException e) {
                opcion = 0;
                System.out.println("Debe ingresar un numero valido");
            }

        } while (opcion != 14);
    }

    private void createEmpresa() {
        try {
            System.out.print("Ingrese rut empresa: ");
            Rut rut = Rut.of(sc.nextLine());

            System.out.print("Ingrese nombre empresa: ");
            String nombre = sc.nextLine();

            System.out.print("Ingrese URL empresa: ");
            String url = sc.nextLine();

            ControladorEmpresas.getInstance().createEmpresa(rut, nombre, url);

            System.out.println("Empresa creada exitosamente");

        } catch (SistemaVentaPasajesException e) {
            System.out.println(e.getMessage());
        }
    }

    private void contratarTripulante() {

        try {

            System.out.print("Rut empresa: ");
            Rut rutEmpresa = Rut.of(sc.nextLine());

            System.out.print("Tipo tripulante [1 Auxiliar / 2 Conductor]: ");
            int tipo = Integer.parseInt(sc.nextLine());

            System.out.print("Documento [1 Rut / 2 Pasaporte]: ");
            int tipoDoc = Integer.parseInt(sc.nextLine());

            IdPersona id;

            if (tipoDoc == 1) {
                System.out.print("Rut: ");
                id = Rut.of(sc.nextLine());
            } else {
                System.out.print("Numero pasaporte: ");
                String numero = sc.nextLine();

                System.out.print("Nacionalidad: ");
                String nacionalidad = sc.nextLine();

                id = Pasaporte.of(numero, nacionalidad);
            }

            System.out.print("Tratamiento [1 SR / 2 SRA]: ");
            int tr = Integer.parseInt(sc.nextLine());

            Tratamiento tratamiento = (tr == 1) ? Tratamiento.SR : Tratamiento.SRA;

            System.out.print("Nombres: ");
            String nombres = sc.nextLine();

            System.out.print("Apellido paterno: ");
            String apPaterno = sc.nextLine();

            System.out.print("Apellido materno: ");
            String apMaterno = sc.nextLine();

            Nombre nombre = new Nombre(tratamiento, nombres, apPaterno, apMaterno);
            System.out.print("Calle: ");
            String calle = sc.nextLine();

            System.out.print("Numero: ");
            int numero = Integer.parseInt(sc.nextLine());

            System.out.print("Comuna: ");
            String comuna = sc.nextLine();

            Direccion direccion = new Direccion(calle, numero, comuna);

            if (tipo == 1) {
                ControladorEmpresas.getInstance().hireAuxiliarForEmpresa(rutEmpresa, id, nombre, direccion);
            } else {
                ControladorEmpresas.getInstance().hireConductorForEmpresa(rutEmpresa, id, nombre, direccion);
            }

            System.out.println("Tripulante contratado exitosamente");

        } catch (SistemaVentaPasajesException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al contratar tripulante");
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

            Direccion direccion = new Direccion(calle, numero, comuna);

            ControladorEmpresas.getInstance().createTerminal(nombre, direccion);

            System.out.println("Terminal creada exitosamente");

        } catch (SistemaVentaPasajesException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al crear terminal");
        }
    }

    private void createCliente() {

        try {

            System.out.print("Documento [1 Rut / 2 Pasaporte]: ");
            int tipoDoc = Integer.parseInt(sc.nextLine());

            IdPersona id;

            if (tipoDoc == 1) {

                System.out.print("Rut: ");
                id = Rut.of(sc.nextLine());

            } else {

                System.out.print("Numero pasaporte: ");
                String numero = sc.nextLine();

                System.out.print("Nacionalidad: ");
                String nacionalidad = sc.nextLine();

                id = Pasaporte.of(numero, nacionalidad);
            }

            System.out.print("Tratamiento [1 SR / 2 SRA]: ");
            int tr = Integer.parseInt(sc.nextLine());

            Tratamiento tratamiento = (tr == 1) ? Tratamiento.SR : Tratamiento.SRA;

            System.out.print("Nombres: ");
            String nombres = sc.nextLine();

            System.out.print("Apellido paterno: ");
            String apPaterno = sc.nextLine();

            System.out.print("Apellido materno: ");
            String apMaterno = sc.nextLine();

            Nombre nombre = new Nombre(tratamiento, nombres, apPaterno, apMaterno);

            System.out.print("Calle: ");
            String calle = sc.nextLine();

            System.out.print("Numero: ");
            int numero = Integer.parseInt(sc.nextLine());

            System.out.print("Comuna: ");
            String comuna = sc.nextLine();

            Direccion direccion = new Direccion(calle, numero, comuna);

            SistemaVentaPasajes.getInstance().createCliente(id, nombre, direccion);

            System.out.println("Cliente creado exitosamente");

        } catch (SistemaVentaPasajesException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al crear cliente");
        }
    }

    private void createBus() {

        try {

            System.out.print("Patente: ");
            String patente = sc.nextLine();

            System.out.print("Marca: ");
            String marca = sc.nextLine();

            System.out.print("Modelo: ");
            String modelo = sc.nextLine();

            System.out.print("Cantidad asientos: ");
            int cantidad = Integer.parseInt(sc.nextLine());

            System.out.print("Rut empresa: ");
            Rut rut = Rut.of(sc.nextLine());

            ControladorEmpresas.getInstance().createBus(patente, marca, modelo, cantidad, rut);

            System.out.println("Bus creado exitosamente");

        } catch (SistemaVentaPasajesException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al crear bus");
        }
    }

    private void createViaje() {

        try {

            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

            System.out.print("Fecha [dd/MM/yyyy]: ");
            LocalDate fecha = LocalDate.parse(sc.nextLine(), formatoFecha);

            System.out.print("Hora [HH:mm]: ");
            LocalTime hora = LocalTime.parse(sc.nextLine(), formatoHora);

            System.out.print("Precio: ");
            int precio = Integer.parseInt(sc.nextLine());

            System.out.print("Duracion: ");
            int duracion = Integer.parseInt(sc.nextLine());

            System.out.print("Patente bus: ");
            String patente = sc.nextLine();

            System.out.print("Cantidad conductores [1 o 2]: ");
            int cantidadConductores = Integer.parseInt(sc.nextLine());

            IdPersona[] tripulantes = new IdPersona[cantidadConductores + 1];

            for (int i = 0; i < tripulantes.length; i++) {

                System.out.print("Documento [1 Rut / 2 Pasaporte]: ");
                int tipoDoc = Integer.parseInt(sc.nextLine());

                if (tipoDoc == 1) {

                    System.out.print("Rut: ");
                    tripulantes[i] = Rut.of(sc.nextLine());

                } else {

                    System.out.print("Numero pasaporte: ");
                    String numero = sc.nextLine();

                    System.out.print("Nacionalidad: ");
                    String nacionalidad = sc.nextLine();

                    tripulantes[i] = Pasaporte.of(numero, nacionalidad);
                }
            }

            System.out.print("Comuna salida: ");
            String salida = sc.nextLine();

            System.out.print("Comuna llegada: ");
            String llegada = sc.nextLine();

            String[] comunas = {salida, llegada};

            SistemaVentaPasajes.getInstance().createViaje(fecha, hora, precio, duracion, patente, tripulantes, comunas);

            System.out.println("Viaje creado exitosamente");

        } catch (SistemaVentaPasajesException e) {
            System.out.println(e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Formato fecha/hora invalido");
        } catch (Exception e) {
            System.out.println("Error al crear viaje");
        }
    }

    private void venderPasaje() {

        try {

            System.out.print("ID documento: ");
            String idDocumento = sc.nextLine();

            System.out.print("Tipo documento [1 Boleta / 2 Factura]: ");
            int td = Integer.parseInt(sc.nextLine());

            TipoDocumento tipoDocumento = (td == 1)
                    ? TipoDocumento.BOLETA
                    : TipoDocumento.FACTURA;

            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            System.out.print("Fecha viaje: ");
            LocalDate fecha = LocalDate.parse(sc.nextLine(), formatoFecha);

            System.out.print("Comuna salida: ");
            String salida = sc.nextLine();

            System.out.print("Comuna llegada: ");
            String llegada = sc.nextLine();

            System.out.print("Cliente [1 Rut / 2 Pasaporte]: ");
            int tipoDoc = Integer.parseInt(sc.nextLine());

            IdPersona cliente;

            if (tipoDoc == 1) {

                System.out.print("Rut cliente: ");
                cliente = Rut.of(sc.nextLine());

            } else {

                System.out.print("Numero pasaporte: ");
                String numero = sc.nextLine();

                System.out.print("Nacionalidad: ");
                String nacionalidad = sc.nextLine();

                cliente = Pasaporte.of(numero, nacionalidad);
            }

            System.out.print("Cantidad pasajes: ");
            int cantidad = Integer.parseInt(sc.nextLine());

            SistemaVentaPasajes controlador = SistemaVentaPasajes.getInstance();

            controlador.iniciaVenta(
                    idDocumento,
                    tipoDocumento,
                    fecha,
                    salida,
                    llegada,
                    cliente,
                    cantidad
            );

            String[][] horarios = controlador.getHorariosDisponibles(
                    fecha,
                    salida,
                    llegada,
                    cantidad
            );

            if (horarios.length == 0) {
                System.out.println("No existen viajes disponibles");
                return;
            }

            for (int i = 0; i < horarios.length; i++) {
                System.out.println(
                        (i + 1) + ") " +
                                horarios[i][0] + " " +
                                horarios[i][1] + " " +
                                horarios[i][2]
                );
            }

            System.out.print("Seleccione viaje: ");
            int opcion = Integer.parseInt(sc.nextLine());

            String patente = horarios[opcion - 1][0];

            LocalTime hora = LocalTime.parse(
                    horarios[opcion - 1][1],
                    DateTimeFormatter.ofPattern("HH:mm")
            );

            String[] asientos = controlador.listAsientosDeViaje(
                    fecha,
                    hora,
                    patente
            );

            for (String asiento : asientos) {
                System.out.print(asiento + " ");
            }

            System.out.println();

            for (int i = 0; i < cantidad; i++) {

                System.out.print("Numero asiento: ");
                int asiento = Integer.parseInt(sc.nextLine());

                System.out.print("Pasajero [1 Rut / 2 Pasaporte]: ");
                int tipoPas = Integer.parseInt(sc.nextLine());

                IdPersona pasajero;

                if (tipoPas == 1) {

                    System.out.print("Rut pasajero: ");
                    pasajero = Rut.of(sc.nextLine());

                } else {

                    System.out.print("Numero pasaporte: ");
                    String numero = sc.nextLine();

                    System.out.print("Nacionalidad: ");
                    String nacionalidad = sc.nextLine();

                    pasajero = Pasaporte.of(numero, nacionalidad);
                }

                controlador.vendePasaje(idDocumento, tipoDocumento, pasajero, asiento, fecha, hora, patente);
            }

            System.out.print("Pago [1 Efectivo / 2 Tarjeta]: ");
            int pago = Integer.parseInt(sc.nextLine());

            if (pago == 1) {

                controlador.pagaVenta(idDocumento, tipoDocumento);

            } else {

                System.out.print("Numero tarjeta: ");
                long tarjeta = Long.parseLong(sc.nextLine());

                controlador.pagaVenta(idDocumento, tipoDocumento, tarjeta);
            }

            System.out.println("Venta realizada exitosamente");

        } catch (SistemaVentaPasajesException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al vender pasaje");
        }
    }

    private void listVentas() {

        try {

            String[][] ventas = SistemaVentaPasajes.getInstance().getVentas();

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

            String[][] viajes = SistemaVentaPasajes.getInstance().getViajes();

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

        } catch (SistemaVentaPasajesException e) {
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

            String[][] datos = SistemaVentaPasajes.getInstance()
                    .listLlegadasSalidasTerminal(terminal, fecha);

            for (String[] fila : datos) {

                for (String dato : fila) {
                    System.out.print(dato + "\t");
                }

                System.out.println();
            }

        } catch (SistemaVentaPasajesException e) {
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
}