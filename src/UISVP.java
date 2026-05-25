import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
    private void contratarTripulante(){
        System.out.println("\n..:::: Contratando un nuevo Tripulante ::::....");

        Rut rutEmpresa = null;
        while (rutEmpresa == null) {
            System.out.print("R.U.T de la empresa (ej: 12.345.678-9): ");
            String rutEmpresaStr = sc.nextLine().trim();
            if (rutEmpresaStr.isEmpty()) {
                System.out.println("Error: El RUT de la empresa no puede estar vacío.");
            } else {
                try {
                    rutEmpresa = Rut.of(rutEmpresaStr);
                } catch (Exception e) {
                    System.out.println("Error: Formato de RUT de empresa inválido. Intente nuevamente.");
                }
            }
        }

        int tipoTripulante = 0;
        while (tipoTripulante != 1 && tipoTripulante != 2) {
            System.out.print("Seleccione el Rol (1: Auxiliar, 2: Conductor): ");
            try {
                tipoTripulante = Integer.parseInt(sc.nextLine().trim());
                if (tipoTripulante != 1 && tipoTripulante != 2) {
                    System.out.println("Error: Opción inválida. Ingrese 1 o 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número entero (1 o 2).");
            }
        }

        IdPersona idTripulante = null;
        while (idTripulante == null) {
            System.out.print("¿Qué tipo de documento tiene el tripulante? (1: RUT, 2: Pasaporte): ");
            String tipoId = sc.nextLine().trim();

            if (tipoId.equals("1")) {
                System.out.print("R.U.T del Tripulante: ");
                String rutTripulanteStr = sc.nextLine().trim();
                if (rutTripulanteStr.isEmpty()) {
                    System.out.println("Error: El RUT no puede estar vacío.");
                } else {
                    try {
                        idTripulante = Rut.of(rutTripulanteStr);
                    } catch (Exception e) {
                        System.out.println("Error: Formato de RUT inválido. Intente de nuevo.");
                    }
                }
            } else if (tipoId.equals("2")) {
                System.out.print("Número de Pasaporte: ");
                String numPasaporte = sc.nextLine().trim();
                System.out.print("Nacionalidad del Pasaporte: ");
                String nacionalidad = sc.nextLine().trim();

                if (numPasaporte.isEmpty() || nacionalidad.isEmpty()) {
                    System.out.println("Error: El número de pasaporte y la nacionalidad son obligatorios.");
                } else {
                    try {
                        idTripulante = Pasaporte.of(numPasaporte, nacionalidad);
                    } catch (Exception e) {
                        System.out.println("Error al construir el Pasaporte. Intente de nuevo.");
                    }
                }
            } else {
                System.out.println("Error: Opción no válida. Ingrese 1 para RUT o 2 para Pasaporte.");
            }
        }

        int tipoTratamiento = 0;
        while (tipoTratamiento != 1 && tipoTratamiento != 2) {
            System.out.print("Tratamiento (1: Sr., 2: Sra.): ");
            try {
                tipoTratamiento = Integer.parseInt(sc.nextLine().trim());
                if (tipoTratamiento != 1 && tipoTratamiento != 2) {
                    System.out.println("Error: Ingrese 1 para Sr. o 2 para Sra.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número entero.");
            }
        }
        Tratamiento tratamiento = (tipoTratamiento == 1) ? Tratamiento.SR : Tratamiento.SRA;

        String nombres = "";
        while (nombres.isEmpty()) {
            System.out.print("Nombres: ");
            nombres = sc.nextLine().trim();
            if (nombres.isEmpty()) System.out.println("Error: El nombre es obligatorio.");
        }

        String apPaterno = "";
        while (apPaterno.isEmpty()) {
            System.out.print("Apellido Paterno: ");
            apPaterno = sc.nextLine().trim();
            if (apPaterno.isEmpty()) System.out.println("Error: El apellido paterno es obligatorio.");
        }

        String apMaterno = "";
        while (apMaterno.isEmpty()) {
            System.out.print("Apellido Materno: ");
            apMaterno = sc.nextLine().trim();
            if (apMaterno.isEmpty()) System.out.println("Error: El apellido materno es obligatorio.");
        }

        Nombre nombreTripulante = new Nombre();
        nombreTripulante.setTratamiento(tratamiento);
        nombreTripulante.setNombres(nombres);
        nombreTripulante.setApellido_paterno(apPaterno);
        nombreTripulante.setApellido_materno(apMaterno);

        String calle = "";
        while (calle.isEmpty()) {
            System.out.print("Calle de residencia: ");
            calle = sc.nextLine().trim();
            if (calle.isEmpty()) System.out.println("Error: La calle es obligatoria.");
        }

        int numeroCalle = -1;
        while (numeroCalle < 0) {
            System.out.print("Número de la calle: ");
            try {
                numeroCalle = Integer.parseInt(sc.nextLine().trim());
                if (numeroCalle < 0) System.out.println("Error: El número no puede ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número entero válido.");
            }
        }

        String comuna = "";
        while (comuna.isEmpty()) {
            System.out.print("Comuna: ");
            comuna = sc.nextLine().trim();
            if (comuna.isEmpty()) System.out.println("Error: La comuna es obligatoria.");
        }

        Direccion direccion = new Direccion(calle, numeroCalle, comuna);

        try {
            ControladorEmpresas controlador = ControladorEmpresas.getInstance();

            if (tipoTripulante == 1) {
                controlador.hireAuxiliarForEmpresa(rutEmpresa, idTripulante, nombreTripulante, direccion);
                System.out.println("\n...:::: Auxiliar contratado de forma exitosa ::::....");
            } else {
                controlador.hireConductorForEmpresa(rutEmpresa, idTripulante, nombreTripulante, direccion);
                System.out.println("\n...:::: Conductor contratado de forma exitosa ::::....");
            }

        } catch (SistemaVentaPasajesException e) {
            System.out.println("\nError de negocio: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nError inesperado en el sistema: " + e.getMessage());
        }
    }
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
    private void createCliente(){
        System.out.println("\n..:::: Creando un nuevo Cliente ::::....");


        IdPersona idCliente = null;
        while (idCliente == null) {
            System.out.print("¿Qué tipo de documento tiene el cliente? (1: RUT, 2: Pasaporte): ");
            String tipoId = sc.nextLine().trim();

            if (tipoId.equals("1")) {
                System.out.print("R.U.T del Cliente: ");
                String rutClienteStr = sc.nextLine().trim();
                if (rutClienteStr.isEmpty()) {
                    System.out.println("Error: El RUT no puede estar vacío.");
                } else {
                    try {
                        idCliente = Rut.of(rutClienteStr);
                    } catch (Exception e) {
                        System.out.println("Error: Formato de RUT inválido. Intente de nuevo.");
                    }
                }
            } else if (tipoId.equals("2")) {
                System.out.print("Número de Pasaporte: ");
                String numPasaporte = sc.nextLine().trim();
                System.out.print("Nacionalidad del Pasaporte: ");
                String nacionalidad = sc.nextLine().trim();

                if (numPasaporte.isEmpty() || nacionalidad.isEmpty()) {
                    System.out.println("Error: El número de pasaporte y la nacionalidad son obligatorios.");
                } else {
                    try {
                        idCliente = Pasaporte.of(numPasaporte, nacionalidad);
                    } catch (Exception e) {
                        System.out.println("Error al construir el Pasaporte. Intente de nuevo.");
                    }
                }
            } else {
                System.out.println("Error: Opción no válida. Ingrese 1 para RUT o 2 para Pasaporte.");
            }
        }

        int tipoTratamiento = 0;
        while (tipoTratamiento != 1 && tipoTratamiento != 2) {
            System.out.print("Tratamiento (1: Sr., 2: Sra.): ");
            try {
                tipoTratamiento = Integer.parseInt(sc.nextLine().trim());
                if (tipoTratamiento != 1 && tipoTratamiento != 2) {
                    System.out.println("Error: Ingrese 1 para Sr. o 2 para Sra.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número entero.");
            }
        }
        Tratamiento tratamiento = (tipoTratamiento == 1) ? Tratamiento.SR : Tratamiento.SRA;

        String nombres = "";
        while (nombres.isEmpty()) {
            System.out.print("Nombres: ");
            nombres = sc.nextLine().trim();
            if (nombres.isEmpty()) System.out.println("Error: El nombre es obligatorio.");
        }

        String apPaterno = "";
        while (apPaterno.isEmpty()) {
            System.out.print("Apellido Paterno: ");
            apPaterno = sc.nextLine().trim();
            if (apPaterno.isEmpty()) System.out.println("Error: El apellido paterno es obligatorio.");
        }

        String apMaterno = "";
        while (apMaterno.isEmpty()) {
            System.out.print("Apellido Materno: ");
            apMaterno = sc.nextLine().trim();
            if (apMaterno.isEmpty()) System.out.println("Error: El apellido materno es obligatorio.");
        }

        Nombre nombreCliente = new Nombre();
        nombreCliente.setTratamiento(tratamiento);
        nombreCliente.setNombres(nombres);
        nombreCliente.setApellido_paterno(apPaterno);
        nombreCliente.setApellido_materno(apMaterno);

        String calle = "";
        while (calle.isEmpty()) {
            System.out.print("Calle de residencia: ");
            calle = sc.nextLine().trim();
            if (calle.isEmpty()) System.out.println("Error: La calle es obligatoria.");
        }

        int numeroCalle = -1;
        while (numeroCalle < 0) {
            System.out.print("Número de la calle: ");
            try {
                numeroCalle = Integer.parseInt(sc.nextLine().trim());
                if (numeroCalle < 0) System.out.println("Error: El número no puede ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número entero válido.");
            }
        }

        String comuna = "";
        while (comuna.isEmpty()) {
            System.out.print("Comuna: ");
            comuna = sc.nextLine().trim();
            if (comuna.isEmpty()) System.out.println("Error: La comuna es obligatoria.");
        }

        Direccion direccion = new Direccion(calle, numeroCalle, comuna);

        try {
            SistemaVentaPasajes.getInstance().createCliente(idCliente, nombreCliente, direccion);
            System.out.println("\n...:::: Cliente creado de forma exitosa ::::....");
        } catch (SistemaVentaPasajesException e) {
            System.out.println("\nError de negocio: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nError inesperado en el sistema: " + e.getMessage());
        }
    }
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
    private void createViaje(){
        System.out.println("...:::: Creando un nuevo Viaje::::....");

        LocalDate fecha = null;
        while(fecha == null) {
            System.out.println("Ingrese Fecha en formato [dd/mm/yyyy]:");
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                fecha = LocalDate.parse(sc.nextLine().trim(), formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Error: formalto de fecha incorrecto. Intente nuevamente");
            }
        }
        LocalDate hora = null;
        while(hora == null) {
            System.out.println("Ingrese Hora en formato [HH:MM]: ");
            try{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                hora = LocalDate.parse(sc.nextLine().trim(), formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Error: formato de hora incorrectos. Intente denuevo");
            }
        }
        int precio = 0;
        while (precio <= 0){
            System.out.println("Ingrese el Precio del viaje: ");
        }try {
            precio = Integer.parseInt(sc.nextLine().trim());
            if(precio <= 0) System.out.println("Error el precio no puede ser menor que 0");
        }catch (NumberFormatException e){
            System.out.println("Error ingrese un valor valido");
        }

        int duracion = 0;
        while (duracion <= 0){
            System.out.println("Ingrese duracion del viaje: ");
            }try {
                duracion = Integer.parseInt(sc.nextLine().trim());
                if(duracion <= 0) System.out.println("Error el duracion no puede ser menor que 0");
            }catch (NumberFormatException e){
                System.out.println("Error ingrese un valor valido");
            }

        String patente = "";
        while(patente.isEmpty()){
            System.out.println("Ingrese la patente del bus: ");
            patente = sc.nextLine().trim();
            if(patente.isEmpty()) System.out.println("Error la patente no puede estar en blanco");
        }
        int nroDeConductores = 0;
        while(nroDeConductores != 1 && nroDeConductores != 2 ){
            System.out.println("Ingres el numero de conductores 1 o 2: ");
        }try {
            nroDeConductores = Integer.parseInt(sc.nextLine().trim());
            if(nroDeConductores != 1 && nroDeConductores != 2 ) System.out.println("Error el nunmero de conductores debe ser 1 o 2");
        }catch (NumberFormatException e){
            System.out.println("Error ingrese una cantida valida de conductores");
        }


        IdPersona[] idTripulantes = new IdPersona[1 + nroDeConductores];

        for (int i = 0; i < idTripulantes.length; i++) {
            if (i == 0) {
                System.out.println(":: Datos del Auxiliar ::");
            } else {
                System.out.println(":: Datos del Conductor " + i + " ::");
            }

            boolean idValido = false;
            while (!idValido) {
                System.out.print("Rut[1] o Pasaporte[2]: ");
                String tipoId = sc.nextLine().trim();

                if (tipoId.equals("1")) {
                    System.out.print("R.U.T (ej: 12.345.678-9): ");
                    String rutString = sc.nextLine().trim();
                    if (rutString.isEmpty()) {
                        System.out.println("Error: El RUT no puede estar vacío.");
                    } else {
                        try {
                            idTripulantes[i] = Rut.of(rutString);
                            idValido = true;
                        } catch (Exception e) {
                            System.out.println("Error: Formato de RUT inválido.");
                        }
                    }
                } else if (tipoId.equals("2")) {
                    System.out.print("Número Pasaporte: ");
                    String numPasaporte = sc.nextLine().trim();
                    System.out.print("Nacionalidad Pasaporte: ");
                    String nacionalidad = sc.nextLine().trim();

                    if (numPasaporte.isEmpty() || nacionalidad.isEmpty()) {
                        System.out.println("Error: El número de pasaporte y la nacionalidad son obligatorios.");
                    } else {
                        try {
                            idTripulantes[i] = Pasaporte.of(numPasaporte, nacionalidad);
                            idValido = true;
                        } catch (Exception e) {
                            System.out.println("Error al construir el Pasaporte.");
                        }
                    }
                } else {
                    System.out.println("Error: Opción inválida. Ingrese 1 para RUT o 2 para Pasaporte.");
                }
            }
        }

        String comunaSalida = "";
        while(comunaSalida.isEmpty()){
            System.out.println("Ingrese la comuna de salida");
            comunaSalida = sc.nextLine().trim();
        }
        String comunaLlegada = "";
        while(comunaLlegada.isEmpty()){
            System.out.println("Ingrese la comuna de llegada");
            comunaLlegada = sc.nextLine().trim();
        }
        String[] nomComunas = {comunaSalida, comunaLlegada};
        try{
            SistemaVentaPasajes.getInstance().createViaje(fecha,hora,precio,duracion,patente,idTripulantes,nomComunas);
            System.out.println("....:::: Viaje guardado exitosamente ::::....");
        }catch (SistemaVentaPasajesException e){
            System.out.println("Error al crear el viaje" + e.getMessage());
        }catch (Exception e){
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }
    private void venderPasaje(){
        System.out.println("..:::: Venta de pasajes::::..");

        String idDocumento = "";
        while (idDocumento.isEmpty()) {
            System.out.print("ID Documento: ");
            idDocumento = sc.nextLine().trim();
            if (idDocumento.isEmpty()) System.out.println("Error: El ID no puede estar vacío.");
        }

        int tipoDocOpcion = 0;
        while (tipoDocOpcion != 1 && tipoDocOpcion != 2) {
            System.out.print("Tipo documento: [1] Boleta [2] Factura: ");
            try {
                tipoDocOpcion = Integer.parseInt(sc.nextLine().trim());
                if (tipoDocOpcion != 1 && tipoDocOpcion != 2) {
                    System.out.println("Error: Ingrese 1 o 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número.");
            }
        }
        TipoDocumento tipoDocumento = (tipoDocOpcion == 1) ? TipoDocumento.BOLETA : TipoDocumento.FACTURA;

        java.time.LocalDate fechaViaje = null;
        while (fechaViaje == null) {
            System.out.print("Fecha de viaje [dd/mm/yyyy]: ");
            try {
                java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
                fechaViaje = java.time.LocalDate.parse(sc.nextLine().trim(), formatter);
            } catch (java.time.format.DateTimeParseException e) {
                System.out.println("Error: Formato de fecha incorrecto.");
            }
        }

        String comunaSalida = "";
        while (comunaSalida.isEmpty()) {
            System.out.print("Origen (comuna): ");
            comunaSalida = sc.nextLine().trim();
            if (comunaSalida.isEmpty()) System.out.println("Error: La comuna de origen es obligatoria.");
        }

        String comunaLlegada = "";
        while (comunaLlegada.isEmpty()) {
            System.out.print("Destino (comuna): ");
            comunaLlegada = sc.nextLine().trim();
            if (comunaLlegada.isEmpty()) System.out.println("Error: La comuna de destino es obligatoria.");
        }

        System.out.println(":::: Datos del cliente");
        IdPersona idCliente = null;
        while (idCliente == null) {
            System.out.print("Rut [1] o Pasaporte [2]: ");
            String tipoId = sc.nextLine().trim();
            if (tipoId.equals("1")) {
                System.out.print("R.U.T: ");
                String rutStr = sc.nextLine().trim();
                if (!rutStr.isEmpty()) {
                    try {
                        idCliente = Rut.of(rutStr);
                    } catch (Exception e) {
                        System.out.println("Error: RUT inválido.");
                    }
                }
            } else if (tipoId.equals("2")) {
                System.out.print("Número de Pasaporte: ");
                String numPasaporte = sc.nextLine().trim();
                System.out.print("Nacionalidad del Pasaporte: ");
                String nacionalidad = sc.nextLine().trim();
                if (!numPasaporte.isEmpty() && !nacionalidad.isEmpty()) {
                    try {
                        idCliente = Pasaporte.of(numPasaporte, nacionalidad);
                    } catch (Exception e) {
                        System.out.println("Error al construir Pasaporte.");
                    }
                }
            } else {
                System.out.println("Error: Opción no válida.");
            }
        }

        int nroPasajes = 0;
        while (nroPasajes <= 0) {
            System.out.print("Cantidad de pasajes: ");
            try {
                nroPasajes = Integer.parseInt(sc.nextLine().trim());
                if (nroPasajes <= 0) System.out.println("Error: La cantidad debe ser mayor a 0.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un valor numérico válido.");
            }
        }

        try {
            SistemaVentaPasajes controlador = SistemaVentaPasajes.getInstance();
            controlador.iniciaVenta(idDocumento, tipoDocumento, fechaViaje, comunaSalida, comunaLlegada, idCliente, nroPasajes);

            String[][] horarios = controlador.getHorariosDisponibles(fechaViaje, comunaSalida, comunaLlegada, nroPasajes);
            if (horarios == null || horarios.length == 0) {
                System.out.println("No existen viajes disponibles para la fecha y origen/destino indicados.");
                return;
            }

            System.out.println("\n:::: Listado de horarios disponibles");
            System.out.println("   | BUS | SALIDA | VALOR | ASIENTOS |");
            for (int i = 0; i < horarios.length; i++) {
                System.out.println((i + 1) + " | " + horarios[i][0] + " | " + horarios[i][1] + " | " + horarios[i][2] + " | " + horarios[i][3] + " |");
            }

            int viajeSeleccionado = 0;
            while (viajeSeleccionado < 1 || viajeSeleccionado > horarios.length) {
                System.out.print("Seleccione viaje [1.." + horarios.length + "]: ");
                try {
                    viajeSeleccionado = Integer.parseInt(sc.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un número válido.");
                }
            }

            String patenteBus = horarios[viajeSeleccionado - 1][0];
            java.time.LocalTime horaViaje = null;
            try {
                java.time.format.DateTimeFormatter timeFormatter = java.time.format.DateTimeFormatter.ofPattern("HH:mm");
                horaViaje = java.time.LocalTime.parse(horarios[viajeSeleccionado - 1][1], timeFormatter);
            } catch (Exception e) {
                System.out.println("Error al obtener la hora del viaje.");
                return;
            }

            System.out.println("\n:::: Asientos disponibles para el viaje seleccionado");
            String[] asientosStr = controlador.listAsientosDeViaje(fechaViaje, horaViaje, patenteBus);
            for (int i = 0; i < asientosStr.length; i++) {
                System.out.print(asientosStr[i] + "\t");
                if ((i + 1) % 4 == 0) System.out.println();
            }
            System.out.println();

            String seleccionAsientos = "";
            String[] asientosArray = null;
            boolean asientosValidos = false;
            while (!asientosValidos) {
                System.out.print("Seleccione sus asientos separe por , : ");
                seleccionAsientos = sc.nextLine().trim();
                asientosArray = seleccionAsientos.split(",");
                if (asientosArray.length == nroPasajes) {
                    asientosValidos = true;
                } else {
                    System.out.println("Error: Debe seleccionar exactamente " + nroPasajes + " asientos.");
                }
            }

            for (int i = 0; i < asientosArray.length; i++) {
                int numeroAsiento = Integer.parseInt(asientosArray[i].trim());
                System.out.println("\n:::: Datos pasajeros " + (i + 1));

                IdPersona idPasajero = null;
                while (idPasajero == null) {
                    System.out.print("Rut [1] o Pasaporte [2]: ");
                    String tipoIdP = sc.nextLine().trim();
                    if (tipoIdP.equals("1")) {
                        System.out.print("R.U.T: ");
                        String rutStr = sc.nextLine().trim();
                        try {
                            idPasajero = Rut.of(rutStr);
                        } catch (Exception e) {
                            System.out.println("Error: RUT inválido.");
                        }
                    } else if (tipoIdP.equals("2")) {
                        System.out.print("Número de Pasaporte: ");
                        String numPasaporte = sc.nextLine().trim();
                        System.out.print("Nacionalidad del Pasaporte: ");
                        String nacionalidad = sc.nextLine().trim();
                        try {
                            idPasajero = Pasaporte.of(numPasaporte, nacionalidad);
                        } catch (Exception e) {
                            System.out.println("Error al construir Pasaporte.");
                        }
                    } else {
                        System.out.println("Error: Opción no válida.");
                    }
                }

                try {
                    controlador.vendePasaje(idDocumento, tipoDocumento, idPasajero, numeroAsiento, fechaViaje, horaViaje, patenteBus);
                    System.out.println(":::: Pasaje agregado exitosamente");
                } catch (SistemaVentaPasajesException e) {
                    if (e.getMessage().toLowerCase().contains("no existe pasajero")) {
                        System.out.println("El pasajero no existe en el sistema. Ingrese los datos para crearlo.");

                        int tipoTratamiento = 0;
                        while (tipoTratamiento != 1 && tipoTratamiento != 2) {
                            System.out.print("Tratamiento (1: Sr., 2: Sra.): ");
                            try {
                                tipoTratamiento = Integer.parseInt(sc.nextLine().trim());
                            } catch (NumberFormatException ex) {}
                        }
                        Tratamiento tratamiento = (tipoTratamiento == 1) ? Tratamiento.SR : Tratamiento.SRA;

                        System.out.print("Nombres: ");
                        String nombres = sc.nextLine().trim();
                        System.out.print("Apellido Paterno: ");
                        String apPaterno = sc.nextLine().trim();
                        System.out.print("Apellido Materno: ");
                        String apMaterno = sc.nextLine().trim();

                        Nombre nombrePasajero = new Nombre();
                        nombrePasajero.setTratamiento(tratamiento);
                        nombrePasajero.setNombres(nombres);
                        nombrePasajero.setApellido_paterno(apPaterno);
                        nombrePasajero.setApellido_materno(apMaterno);

                        System.out.print("Teléfono: ");
                        String telefono = sc.nextLine().trim();

                        System.out.print("Nombres Contacto Emergencia: ");
                        String nombresContacto = sc.nextLine().trim();
                        System.out.print("Apellido Paterno Contacto: ");
                        String apPaternoContacto = sc.nextLine().trim();
                        System.out.print("Apellido Materno Contacto: ");
                        String apMaternoContacto = sc.nextLine().trim();

                        Nombre nombreContacto = new Nombre();
                        nombreContacto.setTratamiento(Tratamiento.SR);
                        nombreContacto.setNombres(nombresContacto);
                        nombreContacto.setApellido_paterno(apPaternoContacto);
                        nombreContacto.setApellido_materno(apMaternoContacto);

                        System.out.print("Teléfono Contacto Emergencia: ");
                        String fonoContacto = sc.nextLine().trim();

                        controlador.createPasajero(idPasajero, nombrePasajero, telefono, nombreContacto, fonoContacto);
                        controlador.vendePasaje(idDocumento, tipoDocumento, idPasajero, numeroAsiento, fechaViaje, horaViaje, patenteBus);
                        System.out.println(":::: Pasaje agregado exitosamente");
                    } else {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
            }

            System.out.println("\n:::: Pago");
            int opcionPago = 0;
            while (opcionPago != 1 && opcionPago != 2) {
                System.out.print("Forma de pago [1] Efectivo [2] Tarjeta: ");
                try {
                    opcionPago = Integer.parseInt(sc.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un número válido.");
                }
            }

            if (opcionPago == 1) {
                controlador.pagaVenta(idDocumento, tipoDocumento);
                System.out.println(":::: Pago registrado exitosamente");
            } else {
                long numeroTarjeta = 0;
                while (numeroTarjeta <= 0) {
                    System.out.print("Número de Tarjeta: ");
                    try {
                        numeroTarjeta = Long.parseLong(sc.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un número de tarjeta válido.");
                    }
                }
                controlador.pagaVenta(idDocumento, tipoDocumento, numeroTarjeta);
                System.out.println(":::: Pago registrado exitosamente");
            }

        } catch (SistemaVentaPasajesException e) {
            System.out.println("\nError de negocio: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nError inesperado en el sistema: " + e.getMessage());
        }
    }
    private void pagaVentaPasaje(){
        System.out.println("\n..:::: Pagar Venta de Pasaje ::::....");

        String idDocumento = "";
        while (idDocumento.isEmpty()) {
            System.out.print("ID Documento: ");
            idDocumento = sc.nextLine().trim();
            if (idDocumento.isEmpty()) System.out.println("Error: El ID no puede estar vacío.");
        }

        int tipoDocOpcion = 0;
        while (tipoDocOpcion != 1 && tipoDocOpcion != 2) {
            System.out.print("Tipo documento: [1] Boleta [2] Factura: ");
            try {
                tipoDocOpcion = Integer.parseInt(sc.nextLine().trim());
                if (tipoDocOpcion != 1 && tipoDocOpcion != 2) {
                    System.out.println("Error: Ingrese 1 o 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número.");
            }
        }
        TipoDocumento tipoDocumento = (tipoDocOpcion == 1) ? TipoDocumento.BOLETA : TipoDocumento.FACTURA;

        int opcionPago = 0;
        while (opcionPago != 1 && opcionPago != 2) {
            System.out.print("Forma de pago [1] Efectivo [2] Tarjeta: ");
            try {
                opcionPago = Integer.parseInt(sc.nextLine().trim());
                if (opcionPago != 1 && opcionPago != 2) {
                    System.out.println("Error: Ingrese 1 o 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        }

        try {
            SistemaVentaPasajes controlador = SistemaVentaPasajes.getInstance();

            if (opcionPago == 1) {
                controlador.pagaVenta(idDocumento, tipoDocumento);
                System.out.println(":::: Pago registrado exitosamente");
            } else {
                long numeroTarjeta = 0;
                while (numeroTarjeta <= 0) {
                    System.out.print("Número de Tarjeta: ");
                    try {
                        numeroTarjeta = Long.parseLong(sc.nextLine().trim());
                        if (numeroTarjeta <= 0) System.out.println("Error: El número de tarjeta debe ser mayor a 0.");
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un número de tarjeta válido.");
                    }
                }
                controlador.pagaVenta(idDocumento, tipoDocumento, numeroTarjeta);
                System.out.println(":::: Pago registrado exitosamente");
            }

        } catch (SistemaVentaPasajesException e) {
            System.out.println("\nError de negocio: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nError inesperado en el sistema: " + e.getMessage());
        }
    }
    private void listVentas(){
        System.out.println("\n..:::: Listado de Ventas ::::....");

        try {
            String[][] ventas = SistemaVentaPasajes.getInstance().getVentas();

            if (ventas == null || ventas.length == 0) {
                System.out.println("No existen ventas registradas en el sistema.");
                return;
            }

            for (int i = 0; i < ventas.length; i++) {
                for (int j = 0; j < ventas[i].length; j++) {
                    System.out.print(ventas[i][j] + "\t| ");
                }
                System.out.println();
            }

        } catch (Exception e) {
            System.out.println("\nError inesperado en el sistema: " + e.getMessage());
        }
    }
    private void listViajes(){
        System.out.println("\n..:::: Listado de Viajes ::::....");

        try {
            String[][] viajes = SistemaVentaPasajes.getInstance().getViajes();

            if (viajes == null || viajes.length == 0) {
                System.out.println("No existen viajes registrados en el sistema.");
                return;
            }

            for (int i = 0; i < viajes.length; i++) {
                for (int j = 0; j < viajes[i].length; j++) {
                    System.out.print(viajes[i][j] + "\t| ");
                }
                System.out.println();
            }

        } catch (Exception e) {
            System.out.println("\nError inesperado en el sistema: " + e.getMessage());
        }
    }
    private void listPasajerosViajes(){
        System.out.println("\n..:::: Listado de Pasajeros de Viaje ::::....");

        java.time.LocalDate fecha = null;
        while (fecha == null) {
            System.out.print("Fecha del viaje [dd/mm/yyyy]: ");
            try {
                java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
                fecha = java.time.LocalDate.parse(sc.nextLine().trim(), formatter);
            } catch (java.time.format.DateTimeParseException e) {
                System.out.println("Error: Formato de fecha incorrecto.");
            }
        }

        java.time.LocalTime hora = null;
        while (hora == null) {
            System.out.print("Hora del viaje [hh:mm]: ");
            try {
                java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("HH:mm");
                hora = java.time.LocalTime.parse(sc.nextLine().trim(), formatter);
            } catch (java.time.format.DateTimeParseException e) {
                System.out.println("Error: Formato de hora incorrecto.");
            }
        }

        String patenteBus = "";
        while (patenteBus.isEmpty()) {
            System.out.print("Patente del Bus: ");
            patenteBus = sc.nextLine().trim();
            if (patenteBus.isEmpty()) System.out.println("Error: La patente no puede estar vacía.");
        }

        try {
            String[][] pasajeros = SistemaVentaPasajes.getInstance().listPasajerosViaje(fecha, hora, patenteBus);

            if (pasajeros == null || pasajeros.length == 0) {
                System.out.println("No existen pasajeros registrados para el viaje indicado.");
                return;
            }

            for (int i = 0; i < pasajeros.length; i++) {
                for (int j = 0; j < pasajeros[i].length; j++) {
                    System.out.print(pasajeros[i][j] + "\t| ");
                }
                System.out.println();
            }

        } catch (SistemaVentaPasajesException e) {
            System.out.println("\nError de negocio: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nError inesperado en el sistema: " + e.getMessage());
        }
    }
    private void listEmpresas(){
        System.out.println("\n..:::: Listado de Empresas ::::....");

        try {
            String[][] empresas = ControladorEmpresas.getInstance().listEmpresas();

            if (empresas == null || empresas.length == 0) {
                System.out.println("No existen empresas registradas en el sistema.");
                return;
            }

            for (int i = 0; i < empresas.length; i++) {
                for (int j = 0; j < empresas[i].length; j++) {
                    System.out.print(empresas[i][j] + "\t| ");
                }
                System.out.println();
            }

        } catch (Exception e) {
            System.out.println("\nError inesperado en el sistema: " + e.getMessage());
        }
    }
    private void listLlegadasSalidasTerminal(){
        System.out.println("\n..:::: Listado de Llegadas y Salidas por Terminal ::::....");

        String nombreTerminal = "";
        while (nombreTerminal.isEmpty()) {
            System.out.print("Nombre del Terminal: ");
            nombreTerminal = sc.nextLine().trim();
            if (nombreTerminal.isEmpty()) System.out.println("Error: El nombre del terminal no puede estar vacío.");
        }

        java.time.LocalDate fecha = null;
        while (fecha == null) {
            System.out.print("Fecha [dd/mm/yyyy]: ");
            try {
                java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
                fecha = java.time.LocalDate.parse(sc.nextLine().trim(), formatter);
            } catch (java.time.format.DateTimeParseException e) {
                System.out.println("Error: Formato de fecha incorrecto.");
            }
        }

        try {
            String[][] llegadasSalidas = SistemaVentaPasajes.getInstance().listLlegadasSalidasTerminal(nombreTerminal, fecha);

            if (llegadasSalidas == null || llegadasSalidas.length == 0) {
                System.out.println("No existen llegadas o salidas para el terminal en la fecha indicada.");
                return;
            }

            for (int i = 0; i < llegadasSalidas.length; i++) {
                for (int j = 0; j < llegadasSalidas[i].length; j++) {
                    System.out.print(llegadasSalidas[i][j] + "\t| ");
                }
                System.out.println();
            }

        } catch (SistemaVentaPasajesException e) {
            System.out.println("\nError de negocio: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nError inesperado en el sistema: " + e.getMessage());
        }
    }
    private void listVentasEmpresas(){
        System.out.println("\n..:::: Listado de Ventas por Empresa ::::....");

        try {
            String[][] ventasEmpresas = ControladorEmpresas.getInstance().listVentasEmpresas();

            if (ventasEmpresas == null || ventasEmpresas.length == 0) {
                System.out.println("No existen ventas registradas para las empresas en el sistema.");
                return;
            }

            for (int i = 0; i < ventasEmpresas.length; i++) {
                for (int j = 0; j < ventasEmpresas[i].length; j++) {
                    System.out.print(ventasEmpresas[i][j] + "\t| ");
                }
                System.out.println();
            }

        } catch (Exception e) {
            System.out.println("\nError inesperado en el sistema: " + e.getMessage());
        }
    }

}
