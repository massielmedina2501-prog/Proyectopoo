package Persistencia;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import Controlador.*;
import Excepciones.SVPException;
import Modelo.*;
import Utilidades.*;

public class IOSVP {

    public static void cargarDatosIniciales() throws SVPException {
        File archivo = new File("SVPDatosIniciales.txt");
        if (!archivo.exists() || !archivo.canRead())
            throw new SVPException("No se puede acceder a SVPDatosIniciales.txt");

        ControladorEmpresas cEmp = ControladorEmpresas.getInstance();
        SistemaVentaPasajes cSVP = SistemaVentaPasajes.getInstance();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int seccion = 1;
            DateTimeFormatter dF = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter tF = DateTimeFormatter.ofPattern("HH:mm");

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                if (linea.equals("+")) { seccion++; continue; }

                String[] campos = linea.split(";");

                switch (seccion) {
                    case 1:
                        IdPersona id = campos[1].contains("-") ? Rut.of(campos[1].replace(".", "")) : Pasaporte.of(campos[1], "Chilena");
                        Nombre nom = new Nombre(Tratamiento.valueOf(campos[2]), campos[3], campos[4], campos[5]);
                        if (campos[0].contains("C")) cSVP.createCliente(id, nom, campos[7]);
                        if (campos[0].contains("P")) {

                            cSVP.createPasajero(id, nom, campos[6], new Nombre(Tratamiento.valueOf(campos[8]), campos[9], campos[10], campos[11]), campos[12]);
                        }
                        break;
                    case 2: // Empresas
                        cEmp.createEmpresa(Rut.of(campos[0]), campos[1], campos[2]);
                        break;
                    case 3: // Tripulantes
                        IdPersona idT = campos[1].contains("-") ? Rut.of(campos[1].replace(".", "")) : Pasaporte.of(campos[1], "Chilena");
                        Direccion dir = new Direccion(campos[6], Integer.parseInt(campos[7]), campos[8]);
                        if (campos[0].equals("C"))
                            cEmp.hireConductorForEmpresa(Rut.of(campos[9]), idT, new Nombre(Tratamiento.valueOf(campos[2]), campos[3], campos[4], campos[5]), dir);
                        else
                            cEmp.hireAuxiliarForEmpresa(Rut.of(campos[9]), idT, new Nombre(Tratamiento.valueOf(campos[2]), campos[3], campos[4], campos[5]), dir);
                        break;
                    case 4: // Terminales
                        cEmp.createTerminal(campos[0], new Direccion(campos[1], Integer.parseInt(campos[2]), campos[3]));
                        break;
                    case 5: // Buses
                        cEmp.createBus(campos[0], campos[1], campos[2], Integer.parseInt(campos[3]), Rut.of(campos[4]));
                        break;
                    case 6: // Viajes
                        cSVP.createViaje(
                                LocalDate.parse(campos[0], dF),
                                LocalTime.parse(campos[1], tF),
                                Integer.parseInt(campos[3]), // precio
                                Integer.parseInt(campos[2]), // duración
                                campos[4],
                                new IdPersona[]{
                                        Rut.of(campos[5]),
                                        Rut.of(campos[6])
                                },
                                new String[]{
                                        campos[7],
                                        campos[8]
                                });
                        break;
                }
            }
        } catch (Exception e) {
            throw new SVPException("Error en formato de datos: " + e.getMessage());
        }
    }

    // --- Métodos de Persistencia de Objetos (Serialización) ---
    public static void saveControladores(Object[] data) throws SVPException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("SVPObjetos.obj"))) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
            throw new SVPException("Error al guardar objetos: " + e.getMessage());
        }
    }

    public static Object[] readControladores() throws SVPException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("SVPObjetos.obj"))) {
            return (Object[]) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new SVPException("Error al leer objetos: " + e.getMessage());
        }
    }

    public static void savePasajesDeVenta(Pasaje[] pasajes, String nombreArchivo) throws SVPException {

        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))) {

            for (Pasaje p : pasajes) {
                pw.println(p.toString());
                pw.println();
            }

        } catch (IOException e) {
            throw new SVPException("Error al guardar pasajes");
        }
    }
    private static Optional<Empresa> findEmpresa(List<Empresa> empresas, Rut rut) {
        return empresas.stream()
                .filter(e -> e.getRut().equals(rut)).findFirst();
    }
    private static Optional<Bus> findBus(List<Bus> buses, String patente) {
        return buses.stream()
                .filter(b -> b.getPatente().equalsIgnoreCase(patente))
                .findFirst();
    }
    private static Optional<Terminal> findTerminal(List<Terminal> terminales, String nombre) {
        return terminales.stream()
                .filter(t -> t.getNombre().equalsIgnoreCase(nombre))
                .findFirst();
    }
    private static Optional<Tripulante> findTripulante(Empresa empresa, IdPersona id) {
        return empresa.getTripulantes().stream()
                .filter(t -> t.getIdPersona().equals(id))
                .findFirst();
    }
}