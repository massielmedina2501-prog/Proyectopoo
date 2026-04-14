import java.util.Scanner;
//Massiel Medina Vasquez
public class Main {
    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);

        System.out.println("--- REGISTRO DE NOMBRE ---");
        System.out.print("Ingrese sus nombres: ");
        String nom = leer.nextLine();
        System.out.print("Ingrese su Apellido Paterno: ");
        String apePat = leer.nextLine();
        System.out.print("Ingrese su Apellido Materno: ");
        String apeMat = leer.nextLine();


        System.out.println("Seleccione su tratamiento:");
        System.out.println("1. SR");
        System.out.println("2. SRA");
        System.out.print("Opción: ");
        int opcion = leer.nextInt();

        Tratamiento tratoSeleccionado;
        if (opcion == 2) {
            tratoSeleccionado = Tratamiento.SRA;
        } else {
            tratoSeleccionado = Tratamiento.SR;
        }

        Nombre usuario = new Nombre(tratoSeleccionado, nom, apePat, apeMat);
        System.out.println("\n----------------------------------");
        System.out.println("Nombre:");
        System.out.println(usuario.toString());
        System.out.println("----------------------------------");
    }
}