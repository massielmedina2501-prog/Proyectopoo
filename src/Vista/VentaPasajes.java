//import javax.swing.*;
//import javax.swing.border.TitledBorder;
//import java.awt.*;
//
//public VentaPasajes () {
//    super("Venta de Pasajes");
//    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//    // Pantalla completa
//    setExtendedState(JFrame.MAXIMIZED_BOTH);
//
//    // Fondo blanco
//    getContentPane().setBackground(Color.WHITE);
//    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
//
//    // Fuentes
//    Font fuenteTexto = new Font("Times New Roman", Font.PLAIN, 12);
//    Font fuenteRespuesta = new Font("Berlin Sans FB", Font.PLAIN, 12);
//    Font fuenteTitulo = new Font("Lucida Sans Typewriter", Font.BOLD, 14);
//
//    // Color botones
//    Color verde = Color.decode("#5CFF38");
//
//    // ================= PANEL BUSQUEDA =================
//    JPanel panelBusqueda = new JPanel(new GridLayout(4, 2, 5, 5));
//    panelBusqueda.setBackground(Color.WHITE);
//
//    TitledBorder bordeBusqueda = BorderFactory.createTitledBorder("1. Búsqueda de Viajes");
//    bordeBusqueda.setTitleFont(fuenteTitulo);
//    bordeBusqueda.setTitleColor(Color.BLACK);
//    panelBusqueda.setBorder(bordeBusqueda);
//
//    JLabel lblFecha = new JLabel("Fecha del Viaje (dd/mm/yyyy):");
//    lblFecha.setFont(fuenteTexto);
//    lblFecha.setForeground(Color.BLACK);
//    panelBusqueda.add(lblFecha);
//
//    JTextField txtFecha = new JTextField();
//    txtFecha.setFont(fuenteRespuesta);
//    txtFecha.setPreferredSize(new Dimension(400,20));
//    panelBusqueda.add(txtFecha);
//
//    JLabel lblOrigen = new JLabel("Origen (Comuna):");
//    lblOrigen.setFont(fuenteTexto);
//    lblOrigen.setForeground(Color.BLACK);
//    panelBusqueda.add(lblOrigen);
//
//    JTextField txtOrigen = new JTextField();
//    txtOrigen.setFont(fuenteRespuesta);
//    txtOrigen.setPreferredSize(new Dimension(400,20));
//    panelBusqueda.add(txtOrigen);
//
//    JLabel lblDestino = new JLabel("Destino (Comuna):");
//    lblDestino.setFont(fuenteTexto);
//    lblDestino.setForeground(Color.BLACK);
//    panelBusqueda.add(lblDestino);
//
//    JTextField txtDestino = new JTextField();
//    txtDestino.setFont(fuenteRespuesta);
//    txtDestino.setPreferredSize(new Dimension(400,20));
//    panelBusqueda.add(txtDestino);
//
//    panelBusqueda.add(new JLabel(""));
//
//    JButton btnBuscar = new JButton("Buscar Viajes");
//    btnBuscar.setFont(fuenteRespuesta);
//    btnBuscar.setBackground(verde);
//    btnBuscar.setForeground(Color.BLACK);
//    btnBuscar.setFocusPainted(false);
//    panelBusqueda.add(btnBuscar);
//
//    // ================= PANEL VENTA =================
//    JPanel panelVenta = new JPanel(new GridLayout(5, 2, 5, 5));
//    panelVenta.setBackground(Color.WHITE);
//
//    TitledBorder bordeVenta = BorderFactory.createTitledBorder("2. Datos de Venta");
//    bordeVenta.setTitleFont(fuenteTitulo);
//    bordeVenta.setTitleColor(Color.BLACK);
//    panelVenta.setBorder(bordeVenta);
//
//    JLabel lblViaje = new JLabel("Seleccione Viaje:");
//    lblViaje.setFont(fuenteTexto);
//    lblViaje.setForeground(Color.BLACK);
//    panelVenta.add(lblViaje);
//
//    JComboBox<String> comboViajes = new JComboBox<>();
//    comboViajes.setFont(fuenteRespuesta);
//    comboViajes.setPreferredSize(new Dimension(400,20));
//    panelVenta.add(comboViajes);
//
//    JLabel lblId = new JLabel("ID documento:");
//    lblId.setFont(fuenteTexto);
//    lblId.setForeground(Color.BLACK);
//    panelVenta.add(lblId);
//
//    JTextField txtIdCliente = new JTextField();
//    txtIdCliente.setFont(fuenteRespuesta);
//    txtIdCliente.setPreferredSize(new Dimension(400,20));
//    panelVenta.add(txtIdCliente);
//
//    JLabel lblCant = new JLabel("Cantidad de Pasajes:");
//    lblCant.setFont(fuenteTexto);
//    lblCant.setForeground(Color.BLACK);
//    panelVenta.add(lblCant);
//
//    JSpinner spinPasajes = new JSpinner(new SpinnerNumberModel(1,1,60,1));
//    spinPasajes.setFont(fuenteRespuesta);
//    spinPasajes.setPreferredSize(new Dimension(400,20));
//    panelVenta.add(spinPasajes);
//
//    JLabel lblDoc = new JLabel("Tipo de Documento:");
//    lblDoc.setFont(fuenteTexto);
//    lblDoc.setForeground(Color.BLACK);
//    panelVenta.add(lblDoc);
//
//    JComboBox<TipoDocumento> comboDocumento = new JComboBox<>(TipoDocumento.values());
//    comboDocumento.setFont(fuenteRespuesta);
//    comboDocumento.setPreferredSize(new Dimension(400,20));
//    panelVenta.add(comboDocumento);
//
//    panelVenta.add(new JLabel(""));
//
//    JButton btnIniciarVenta = new JButton("Iniciar Venta");
//    btnIniciarVenta.setFont(fuenteRespuesta);
//    btnIniciarVenta.setBackground(verde);
//    btnIniciarVenta.setForeground(Color.BLACK);
//    btnIniciarVenta.setFocusPainted(false);
//    panelVenta.add(btnIniciarVenta);
//
//    // ================= PANEL PAGO =================
//    JPanel panelPago = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//    panelPago.setBackground(Color.WHITE);
//
//    TitledBorder bordePago = BorderFactory.createTitledBorder("3. Finalizar");
//    bordePago.setTitleFont(fuenteTitulo);
//    bordePago.setTitleColor(Color.BLACK);
//    panelPago.setBorder(bordePago);
//
//    JButton btnPagar = new JButton("Pagar Venta");
//    btnPagar.setFont(fuenteRespuesta);
//    btnPagar.setBackground(verde);
//    btnPagar.setForeground(Color.BLACK);
//    btnPagar.setFocusPainted(false);
//    btnPagar.setEnabled(false);
//
//    panelPago.add(btnPagar);
//
//    add(panelBusqueda);
//    add(panelVenta);
//    add(panelPago);
//
//    getContentPane().setBackground(Color.WHITE);
//}