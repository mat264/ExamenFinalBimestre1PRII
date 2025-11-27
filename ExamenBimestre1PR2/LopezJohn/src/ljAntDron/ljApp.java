package ljAntDron;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ljApp {

    // Datos constantes
    private static final String LJ_NOMBRE = "John López";
    private static final String LJ_CEDULA = "1751330158";

    public static void main(String[] args) {
        System.out.println("=== SISTEMA ANTDRON 2K25 ===");
        System.out.println("Nombre: " + LJ_NOMBRE);
        System.out.println("Cédula: " + LJ_CEDULA);
        System.out.println("----------------------------");

        // Instanciar objetos
        ljAntCiberDron ljDron = new ljAntCiberDron();

        // CORRECCIÓN: Instancia de Omnivoro según tu indicación
        ljAlimento ljComida = new ljOmnivoro();

        // Verificar vida
        if (ljDron.ljComer(ljComida)) {
            System.out.println("Alimentación: Omnívoro -> Hormiga VIVE");
        } else {
            System.out.println("Alimentación Incorrecta -> Hormiga MUERE");
            return;
        }

        System.out.println("\n[+] LEYENDO ARCHIVO UCRANIANO: LopezJohn.csv");
        ljProcesarArchivo(ljDron);
    }

    private static void ljProcesarArchivo(ljAntCiberDron ljDron) {
        String ljCsvFile = "ExamenFinal/LopezJohn.csv";
        String ljLine = "";
        String ljSplitBy = ";";
        java.util.List<String[]> ljTargets = new java.util.ArrayList<>();

        try (BufferedReader ljBr = new BufferedReader(new FileReader(ljCsvFile))) {

            // Leer y descartar cabecera
            ljBr.readLine();

            System.out.println("\n[+] COORDENADAS UCRANIANAS:");
            // Formato de tabla de salida
            System.out.println(String.format("%-10s | %-12s | %-8s | %-8s | %-10s | %-8s | %-8s | %-15s",
                    "Loading", "Geoposición", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Tipo Arsenal"));

            while ((ljLine = ljBr.readLine()) != null) {
                // Mostrar loading
                ljMostrarLoadingCustom();

                String[] ljDatos = ljLine.split(ljSplitBy);

                // Rellenar con cadenas vacías si faltan columnas
                String[] ljDatosCompletos = new String[7];
                for (int i = 0; i < 7; i++) {
                    if (i < ljDatos.length) {
                        ljDatosCompletos[i] = ljDatos[i];
                    } else {
                        ljDatosCompletos[i] = "";
                    }
                }

                // Borrar la línea de loading y mostrar la fila completa
                System.out.print("\r");
                System.out.println(String.format("%-10s | %-12s | %-8s | %-8s | %-10s | %-8s | %-8s | %-15s",
                        "100%", ljDatosCompletos[0], ljDatosCompletos[1], ljDatosCompletos[2], ljDatosCompletos[3],
                        ljDatosCompletos[4], ljDatosCompletos[5], ljDatosCompletos[6]));

                // Lógica de detección de targets
                if (ljDatos.length > 0) {
                    String ljCoord = ljDatos[0];
                    String ljArsenal = "";
                    for (int i = 1; i < ljDatos.length; i++) {
                        if (!ljDatos[i].isEmpty()) {
                            String datoLimpio = ljDatos[i].trim();
                            if (datoLimpio.startsWith("a")) {
                                ljArsenal = datoLimpio;
                                break;
                            }
                        }
                    }
                    ljDron.ljSetCoordenadaActual(ljCoord);
                    if (ljDron.ljBuscar(ljArsenal)) {
                        ljTargets.add(new String[] { ljCoord, ljArsenal, "true" });
                    }
                }

                // Retardo para visualizar mejor
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            // Mostrar tabla de destrucción
            System.out.println("\n[+] COORDENADAS UCRANIANAS A DESTRUIR:");
            System.out.println(
                    String.format("%-10s | %-12s | %-15s | %-10s", "Loading", "Geoposición", "Tipo Arsenal", "Acción"));
            for (String[] target : ljTargets) {
                // Mostrar loading
                ljMostrarLoadingCustom();

                // Borrar la línea de loading y mostrar la fila completa
                System.out.print("\r");
                System.out.println(
                        String.format("%-10s | %-12s | %-15s | %-10s", "100%", target[0], target[1], target[2]));

                // Retardo
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

        } catch (IOException e) {
            System.out.println("Error leyendo CSV: " + e.getMessage());
        }
    }

    private static void ljMostrarLoadingCustom() {
        try {
            // Animación de 0 a 100%
            for (int ljI = 0; ljI <= 100; ljI += 10) {
                // Formato: o0o0o 20%
                String barra = String.format("o0o0o %d%%", ljI);
                System.out.print("\r" + String.format("%-10s", barra));
                Thread.sleep(20); // Velocidad
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}