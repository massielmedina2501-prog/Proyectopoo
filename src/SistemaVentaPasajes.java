import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

//Denisse Manzor Tapia
public class SistemaVentaPasajes {
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private ArrayList<Pasajero> pasajeros = new ArrayList<>();
    private ArrayList<Bus> buses = new ArrayList<>();
    private ArrayList<Viaje> viajes = new ArrayList<>();
    private ArrayList<Venta> ventas = new ArrayList<>();

    public boolean createCliente(IdPersona id, Nombre nom, String fono, String email){
        if (id == null || nom == null || email == null) return false;

        if (findCliente(id) != null) return false;
        Cliente c = new Cliente(id, nom, email);
        c.setTelefono(fono);
        clientes.add(c);
        return true;
    }

    public boolean createPasajero(IdPersona id, Nombre nom, String fono, Nombre nomContacto, String fonoContacto){
        if (id == null || nom == null || nomContacto == null || fonoContacto == null) return false;

        if (findPasajero(id) != null) return false;
        Pasajero p = new Pasajero(id, nom);
        p.setTelefono(fono);
        p.setNomContacto(nomContacto);
        p.setFonoContacto(fonoContacto);
        pasajeros.add(p);
        return true;
    }

    public boolean createBus(String patente, String marca, String modelo, int nroAsientos){
        if (patente == null || marca == null || modelo == null || nroAsientos <= 0) return false;

        if (findBus(patente) != null) return false;
        Bus b = new Bus(patente, nroAsientos);
        b.setMarca(marca);
        b.setModelo(modelo);
        buses.add(b);
        return true;
    }

    public boolean createViaje(LocalDate fecha, LocalTime hora, int precio, String patBus){
        if (fecha == null || hora == null || patBus == null || precio <= 0) return false;
        Bus bus = findBus(patBus);
        if (bus == null) return false;

        for (Viaje v : viajes) {
            if (v.getBus().getPatente().equals(patBus) &&
                    v.getFecha().equals(fecha) &&
                    v.getHora().equals(hora)) {
                return false;
            }
        }
        Viaje v = new Viaje(fecha, hora, precio, bus);
        viajes.add(v);
        bus.addViaje(v);
        return true;
    }

    public boolean iniciaVenta(String idDoc, TipoDocumento tipo, LocalDate fechaVenta, IdPersona idCliente){
        if (idDoc == null || tipo == null || fechaVenta == null || idCliente == null) return false;
        Cliente c = findCliente(idCliente);
        if (c == null) return false;

        if (findVenta(idDoc, tipo) != null) return false;
        Venta v = new Venta(idDoc, tipo, fechaVenta, c);
        ventas.add(v);
        return true;
    }

    public boolean vendePasaje(String idDoc, LocalDate fecha, LocalTime hora, String patBus, int asiento, IdPersona idPasajero){
        if(idDoc == null || fecha == null || hora == null || patBus == null || idPasajero == null || asiento <= 0){
            return false;
        }

        Venta venta = findVenta(idDoc, TipoDocumento.BOLETA);
        Viaje viaje = findViaje(fecha, hora, patBus);
        Pasajero pasajero = findPasajero(idPasajero);

        if (venta == null || viaje == null || pasajero == null) return false;
        venta.createPasaje(asiento, viaje, pasajero);
        return true;
    }

    public int getMontoVenta(String idDocumento, TipoDocumento tipo){
        Venta v = findVenta(idDocumento, tipo);
        return (v == null) ? 0 : v.getMonto();
    }

    public String[][] listAsientosDeViaje(LocalDate fecha, LocalTime hora, String patBus){
        Viaje v = findViaje(fecha, hora, patBus);
        return (v == null) ? new String[0][0] : v.getAsientos();
    }

    public String getNombrePasajero(IdPersona idPasajero){
        Pasajero p = findPasajero(idPasajero);
        return (p == null) ? "" : p.getNombreCompleto().toString();
    }

    public String[][] listViajes(){
        String[][] matriz = new String[viajes.size()][5];
        for (int i = 0; i < viajes.size(); i++){
            Viaje v = viajes.get(i);
            matriz[i][0] = v.getFecha().toString();
            matriz[i][1] = v.getHora().toString();
            matriz[i][2] = String.valueOf(v.getPrecio());
            matriz[i][3] = String.valueOf(v.getNroAsientosDisponibles());
            matriz[i][4] = v.getBus().getPatente();
        }
        return matriz;
    }

    public String[][] getHorariosDisponibles(LocalDate fecha){
        if (fecha == null) return new String[0][0];
        ArrayList<String[]> lista = new ArrayList<>();

        for (Viaje v : viajes){
            if (v.getFecha().equals(fecha) && v.getNroAsientosDisponibles() > 0){
                String[] fila = new String[2];
                fila[0] = v.getHora().toString();     // hora
                fila[1] = v.getBus().getPatente();    // patente
                lista.add(fila);
            }
        }
        String[][] matriz = new String[lista.size()][2];
        for (int i = 0; i < lista.size(); i++){
            matriz[i] = lista.get(i);
        }
        return matriz;
    }

    public String[][] listPasajeros(LocalDate fecha, LocalTime hora, String patBus){
        Viaje viaje = findViaje(fecha, hora, patBus);
        if (viaje == null) return new String[0][0];

        List<Pasaje> lista = viaje.getPasajes();
        String[][] matriz = new String[lista.size()][5];

        for (int i = 0; i < lista.size(); i++){
            Pasaje p = lista.get(i);
            matriz[i][0] = String.valueOf(p.getAsiento());
            matriz[i][1] = p.getPasajero().getIdPersona().toString();
            matriz[i][2] = p.getPasajero().getNombreCompleto().toString();
            matriz[i][3] = p.getPasajero().getNomContacto().toString();
            matriz[i][4] = p.getPasajero().getFonoContacto();
        }
        return matriz;
    }

    public String[][] listVentas(){
        if (ventas.size() == 0) return new String[0][0];
        String[][] matriz = new String[ventas.size()][7];

        for (int i = 0; i < ventas.size(); i++){
            Venta v = ventas.get(i);
            matriz[i][0] = v.getIdDocumento();
            matriz[i][1] = v.getTipo().toString();
            matriz[i][2] = v.getFecha().toString();
            matriz[i][3] = v.getCliente().getIdPersona().toString();
            matriz[i][4] = v.getCliente().getNombreCompleto().toString();
            matriz[i][5] = String.valueOf(v.getPasajes().length);
            matriz[i][6] = String.valueOf(v.getMonto());
        }
        return matriz;
    }

    private Cliente findCliente(IdPersona id){
        for (Cliente c : clientes){
            if(c.getIdPersona().equals(id)) return c;
        }
        return null;
    }

    private Venta findVenta(String idDocumento, TipoDocumento tipo){
        for (Venta v : ventas){
            if(v.getIdDocumento().equals(idDocumento) && v.getTipo() == tipo){
                return v;
            }
        }
        return null;
    }

    private Bus findBus(String patente){
        for (Bus b : buses){
            if(b.getPatente().equals(patente)) return b;
        }
        return null;
    }

    private Viaje findViaje(LocalDate fecha, LocalTime hora, String patente){
        for (Viaje v : viajes){
            if(v.getFecha().equals(fecha) &&
                    v.getHora().equals(hora) &&
                    v.getBus().getPatente().equals(patente)){
                return v;
            }
        }
        return null;
    }

    private Pasajero findPasajero(IdPersona id){
        for (Pasajero p : pasajeros){
            if(p.getIdPersona().equals(id)) return p;
        }
        return null;
    }
}