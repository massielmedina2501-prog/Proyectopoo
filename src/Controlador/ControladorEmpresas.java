package Controlador;

import Modelo.*;
import Utilidades.*;
import Excepciones.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class ControladorEmpresas {

    private static ControladorEmpresas instance = null;

    private ArrayList<Empresa> empresas;
    private ArrayList<Terminal> terminales;

    private ControladorEmpresas() {

        this.empresas = new ArrayList<>();
        this.terminales = new ArrayList<>();
    }

    public static ControladorEmpresas getInstance() {

        if (instance == null) {
            instance = new ControladorEmpresas();
        }

        return instance;
    }

    public void createEmpresa(Rut rut, String nombre, String url)
            throws SistemaVentaPasajesException {

        if (findEmpresa(rut).isPresent()) {
            throw new SistemaVentaPasajesException("Ya existe empresa con el rut indicado");
        }

        Empresa e = new Empresa(rut, nombre);

        e.setUrl(url);

        empresas.add(e);
    }

    public void createBus(String pat, String marca, String modelo, int nroAsientos, Rut rutEmp)
            throws SistemaVentaPasajesException {

        Optional<Empresa> emp = findEmpresa(rutEmp);

        if (emp.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe empresa con el rut indicado");
        }

        if (findBus(pat).isPresent()) {
            throw new SistemaVentaPasajesException("Ya existe bus con la patente indicada");
        }

        Bus b = new Bus(pat, nroAsientos, emp.get());

        b.setMarca(marca);
        b.setModelo(modelo);

        emp.get().addBus(b);
    }

    public void createTerminal(String nombre, Direccion direccion)
            throws SistemaVentaPasajesException {

        if (findTerminal(nombre).isPresent()) {
            throw new SistemaVentaPasajesException("Ya existe terminal con el nombre indicado");
        }

        if (findTerminalPorComuna(direccion.getComuna()).isPresent()) {
            throw new SistemaVentaPasajesException("Ya existe terminal en la comuna indicada");
        }

        Terminal t = new Terminal(nombre, direccion);

        terminales.add(t);
    }

    public void hireConductorForEmpresa(Rut rutEmp, IdPersona id, Nombre nom, Direccion dir)
            throws SistemaVentaPasajesException {

        Optional<Empresa> emp = findEmpresa(rutEmp);

        if (emp.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe empresa con el rut indicado");
        }

        boolean agregado = emp.get().addConductor(id, nom, dir);

        if (!agregado) {
            throw new SistemaVentaPasajesException("Ya existe tripulante con el id indicado");
        }
    }

    public void hireAuxiliarForEmpresa(Rut rutEmp, IdPersona id, Nombre nom, Direccion dir)
            throws SistemaVentaPasajesException {

        Optional<Empresa> emp = findEmpresa(rutEmp);

        if (emp.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe empresa con el rut indicado");
        }

        boolean agregado = emp.get().addAuxiliar(id, nom, dir);

        if (!agregado) {
            throw new SistemaVentaPasajesException("Ya existe tripulante con el id indicado");
        }
    }

    public String[][] listEmpresas() {

        String[][] matriz = new String[empresas.size()][2];

        for (int i = 0; i < empresas.size(); i++) {

            Empresa e = empresas.get(i);

            matriz[i][0] = e.getRut().toString();
            matriz[i][1] = e.getNombre();
        }

        return matriz;
    }

    public String[][] listLlegadasSalidasTerminal(String nombre, Date fecha)
            throws SistemaVentaPasajesException {

        Optional<Terminal> term = findTerminal(nombre);

        if (term.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe terminal con el nombre indicado");
        }

        ArrayList<String[]> lista = new ArrayList<>();

        for (Viaje v : term.get().getSalidas()) {

            if (v.getFecha().equals(fecha)) {

                String[] fila = new String[5];

                fila[0] = "SALIDA";
                fila[1] = v.getHora().toString();
                fila[2] = v.getBus().getPatente();
                fila[3] = v.getBus().getEmpresa().getNombre();
                fila[4] = String.valueOf(v.getPasajes().length);

                lista.add(fila);
            }
        }

        for (Viaje v : term.get().getLlegadas()) {

            if (v.getFecha().equals(fecha)) {

                String[] fila = new String[5];

                fila[0] = "LLEGADA";
                fila[1] = v.getHora().toString();
                fila[2] = v.getBus().getPatente();
                fila[3] = v.getBus().getEmpresa().getNombre();
                fila[4] = String.valueOf(v.getPasajes().length);

                lista.add(fila);
            }
        }

        String[][] matriz = new String[lista.size()][5];

        for (int i = 0; i < lista.size(); i++) {
            matriz[i] = lista.get(i);
        }

        return matriz;
    }

    public String[][] listVentasEmpresa(Rut rut)
            throws SistemaVentaPasajesException {

        Optional<Empresa> emp = findEmpresa(rut);

        if (emp.isEmpty()) {
            throw new SistemaVentaPasajesException("No existe empresa con el rut indicado");
        }

        Venta[] ventas = emp.get().getVentas();

        String[][] matriz = new String[ventas.length][4];

        for (int i = 0; i < ventas.length; i++) {

            Venta v = ventas[i];

            matriz[i][0] = v.getFecha().toString();
            matriz[i][1] = v.getTipo().toString();
            matriz[i][2] = String.valueOf(v.getMonto());
            matriz[i][3] = v.getTipoPago();
        }

        return matriz;
    }

    protected Optional<Empresa> findEmpresa(Rut rut) {

        for (Empresa e : empresas) {

            if (e.getRut().equals(rut)) {
                return Optional.of(e);
            }
        }

        return Optional.empty();
    }

    protected Optional<Terminal> findTerminal(String nombre) {

        for (Terminal t : terminales) {

            if (t.getNombre().equalsIgnoreCase(nombre)) {
                return Optional.of(t);
            }
        }

        return Optional.empty();
    }

    protected Optional<Terminal> findTerminalPorComuna(String comuna) {

        for (Terminal t : terminales) {

            if (t.getDireccion().getComuna().equalsIgnoreCase(comuna)) {
                return Optional.of(t);
            }
        }

        return Optional.empty();
    }

    protected Optional<Bus> findBus(String patente) {

        for (Empresa e : empresas) {

            for (Bus b : e.getBuses()) {

                if (b.getPatente().equalsIgnoreCase(patente)) {
                    return Optional.of(b);
                }
            }
        }

        return Optional.empty();
    }

    protected Optional<Conductor> findConductor(IdPersona id, Rut rutEmpresa) {

        Optional<Empresa> emp = findEmpresa(rutEmpresa);

        if (emp.isEmpty()) {
            return Optional.empty();
        }

        for (Tripulante t : emp.get().getTripulantes()) {

            if (t instanceof Conductor && t.getIdPersona().equals(id)) {
                return Optional.of((Conductor) t);
            }
        }

        return Optional.empty();
    }

    protected Optional<Auxiliar> findAuxiliar(IdPersona id, Rut rutEmpresa) {

        Optional<Empresa> emp = findEmpresa(rutEmpresa);

        if (emp.isEmpty()) {
            return Optional.empty();
        }

        for (Tripulante t : emp.get().getTripulantes()) {

            if (t instanceof Auxiliar && t.getIdPersona().equals(id)) {
                return Optional.of((Auxiliar) t);
            }
        }

        return Optional.empty();
    }
}