import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("*****Bienvenidos al conversor de monedas*****");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Conversor de Monedas =====");
            System.out.println("1) Dólar → Peso Argentino");
            System.out.println("2) Peso Argentino → Dólar");
            System.out.println("3) Dólar → Real Brasileño");
            System.out.println("4) Real Brasileño → Dólar");
            System.out.println("5) Dólar → Peso Colombiano");
            System.out.println("6) Peso Colombiano → Dólar");
            System.out.println("7) Salir");
            System.out.print("Elige una opción (1–7): ");

            String opcionStr = scanner.nextLine();
            int opcion;
            try {
                opcion = Integer.parseInt(opcionStr);
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida. Por favor ingresa un número del 1 al 7.");
                continue;
            }

            if (opcion == 7) {
                System.out.println("Gracias por usar el conversor. ¡Hasta pronto!");
                break;
            }

            if (opcion < 1 || opcion > 7) {
                System.out.println("Opción inválida. Por favor ingresa un número del 1 al 7.");
                continue;
            }

            System.out.print("Ingresa el monto a convertir: ");
            String montoStr = scanner.nextLine().replace(",", ".");
            double monto;
            try {
                monto = Double.parseDouble(montoStr);
            } catch (NumberFormatException e) {
                System.out.println("Monto inválido, por favor ingresa un número válido.");
                continue;
            }

            String desde = "", hacia = "";
            switch (opcion) {
                case 1 -> { desde = "USD"; hacia = "ARS"; }
                case 2 -> { desde = "ARS"; hacia = "USD"; }
                case 3 -> { desde = "USD"; hacia = "BRL"; }
                case 4 -> { desde = "BRL"; hacia = "USD"; }
                case 5 -> { desde = "USD"; hacia = "COP"; }
                case 6 -> { desde = "COP"; hacia = "USD"; }
            }

            try {
                double resultado = ConversorApi.convertir(desde, hacia, monto);
                System.out.printf("%.2f %s equivalen a %.2f %s%n", monto, desde, resultado, hacia);
            } catch (IOException e) {
                System.out.println("Error al realizar la conversión: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
