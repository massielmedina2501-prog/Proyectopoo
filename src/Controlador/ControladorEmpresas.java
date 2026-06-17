package Controlador;
//Massiel Medina y Luis Reyes
import Modelo.*;
import Utilidades.*;
import Excepciones.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.Collector;


public class ControladorEmpresas {

    private static ControladorEmpresas instance = null;
    private ArrayList<Empresa> empresas;
    private ArrayList<Terminal> terminales;

    private ControladorEmpresas() {
        this.empresas = new ArrayList<>();
        this.terminales = new ArrayList<>();
    }

    public static ControladorEmpresas getInstance() {
        if (instance == null) instance = new ControladorEmpresas();
        return instance;
    }


    protected void setInstanciaPersistente(ControladorEmpresas inst) {
        instance = inst;
    }

    protected void setDatosIniciales(Object[] objetos) {
        this.empresas = (ArrayList<Empresa>) objetos[0];
        this.terminales = (ArrayList<Terminal>) objetos[1];
    }

    public void createEmpresa(Rut rut, String nombre, String url) throws SVPException {
        if (findEmpresa(rut).isPresent()) throw new SVPException("Ya existe empresa con el rut indicado");
        Empresa e = new Empresa(rut, nombre);
        e.setUrl(url);
        empresas.add(e);
    }

    public void createBus(String pat, String marca, String modelo, int nroAsientos, Rut rutEmp) throws SVPException {
        Empresa emp = findEmpresa(rutEmp).orElseThrow(() -> new SVPException("No existe empresa con el rut indicado"));
        if (findBus(pat).isPresent()) throw new SVPException("Ya existe bus con la patente indicada");

        Bus b = new Bus(pat, nroAsientos, emp);
        b.setMarca(marca);
        b.setModelo(modelo);
        emp.addBus(b);
    }

    public void createTerminal(String nombre, Direccion direccion) throws SVPException {
        if (findTerminal(nombre).isPresent()) throw new SVPException("Ya existe terminal con el nombre indicado");
        if (findTerminalPorComuna(direccion.getComuna()).isPresent()) throw new SVPException("Ya existe terminal en la comuna indicada");
        terminales.add(new Terminal(nombre, direccion));
    }

    public void hireConductorForEmpresa(Rut rutEmp, IdPersona id, Nombre nom, Direccion dir) throws SVPException {
        Empresa c = findEmpresa(rutEmp).orElseThrow(() -> new SVPException("Empresa no encontrada"));
        c.addConductor(id, nom, dir);
    }

    public void hireAuxiliarForEmpresa(Rut rutEmp, IdPersona id, Nombre nom, Direccion dir) throws SVPException {
        Empresa e = findEmpresa(rutEmp).orElseThrow(() -> new SVPException("Empresa no encontrada"));
        e.addAuxiliar(id, nom, dir);
    }

    // --- LISTADOS ---
    public String[][] listEmpresas() {
        return empresas.stream().map(e -> new String[]{e.getRut().toString(), e.getNombre()}).toArray(String[][]::new);
    }

    public String[][] listLlegadasSalidasTerminal(String nombre, LocalDate fecha) throws SVPException {
        Terminal t = findTerminal(nombre).orElseThrow(() -> new SVPException("No existe terminal"));
        return java.util.stream.Stream.concat(
                t.getSalidas().stream().filter(v -> v.getFecha().equals(fecha)).map(v -> new String[]{"SALIDA", v.getHora().toString(), v.getBus().getPatente(), v.getBus().getEmpresa().getNombre(), String.valueOf(v.getPasajes().size())}),
                t.getLlegadas().stream().filter(v -> v.getFecha().equals(fecha)).map(v -> new String[]{"LLEGADA", v.getHora().toString(), v.getBus().getPatente(), v.getBus().getEmpresa().getNombre(), String.valueOf(v.getPasajes().size())})
        ).toArray(String[][]::new);
    }

    public String[][] listVentasEmpresa(Rut rut) throws SVPException {
        Empresa e = findEmpresa(rut).orElseThrow(() -> new SVPException("Empresa no encontrada"));

        return e.getBuses().stream().flatMap(b -> b.getViajes().stream()).flatMap(v -> v.getPasajes().stream()).map(p -> new String[]{p.getVenta().getIdDocumento(), p.getVenta().getTipo().toString(), String.valueOf(p.getVenta().getMonto())}).distinct().toArray(String[][]::new);
    }

    // --- BÚSQUEDAS
    protected Optional<Empresa> findEmpresa(Rut rut) { return empresas.stream().filter(e -> e.getRut().equals(rut)).findFirst(); }
    protected Optional<Terminal> findTerminal(String nombre) { return terminales.stream().filter(t -> t.getNombre().equalsIgnoreCase(nombre)).findFirst(); }
    protected Optional<Terminal> findTerminalPorComuna(String comuna) { return terminales.stream().filter(t -> t.getDireccion().getComuna().equalsIgnoreCase(comuna)).findFirst(); }
    protected Optional<Bus> findBus(String patente) { return empresas.stream().flatMap(e -> e.getBuses().stream()).filter(b -> b.getPatente().equalsIgnoreCase(patente)).findFirst(); }

    protected Optional<Conductor> findConductor(IdPersona id, Rut rutEmp) {
        return findEmpresa(rutEmp)
                .flatMap(e -> e.getTripulantes().stream()
                        .filter(t -> t instanceof Conductor)
                        .map(t -> (Conductor) t)
                        .filter(c -> c.getIdPersona().equals(id))
                        .findFirst());
    }
    protected Optional<Auxiliar> findAuxiliar(IdPersona id, Rut rutEmp) {
        return findEmpresa(rutEmp)
                .flatMap(e -> e.getTripulantes().stream()
                        .filter(t -> t instanceof Auxiliar)
                        .map(t -> (Auxiliar) t)
                        .filter(a -> a.getIdPersona().equals(id))
                        .findFirst());
    }
}
