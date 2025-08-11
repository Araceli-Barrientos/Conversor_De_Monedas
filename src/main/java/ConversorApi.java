import java.io.*;
import java.net.*;

public class ConversorApi {
    private static final String API_KEY = "d3ef0ff5fef5490afaea2443";  // Cambia por tu clave real
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/%s/latest/%s";

    public static double convertir(String desde, String hacia, double cantidad) throws IOException {
        String urlStr = String.format(API_URL, API_KEY, desde);
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder sb = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null) {
                sb.append(linea);
            }
            String respuesta = sb.toString();

            // Buscamos "conversion_rates" : { ... }
            String key = "\"conversion_rates\":";
            int pos = respuesta.indexOf(key);
            if (pos == -1) throw new IOException("No se encontró conversion_rates en la respuesta");

            // Buscamos la tasa para la moneda destino
            String searchRateKey = "\"" + hacia + "\":";
            int posRate = respuesta.indexOf(searchRateKey, pos);
            if (posRate == -1) throw new IOException("No se encontró la tasa para " + hacia);

            int startNum = posRate + searchRateKey.length();
            int endNum = startNum;

            // Leer el número (hasta coma o llave)
            while (endNum < respuesta.length() &&
                    (Character.isDigit(respuesta.charAt(endNum)) ||
                            respuesta.charAt(endNum) == '.' ||
                            respuesta.charAt(endNum) == 'e' ||
                            respuesta.charAt(endNum) == 'E' ||
                            respuesta.charAt(endNum) == '+' ||
                            respuesta.charAt(endNum) == '-')) {
                endNum++;
            }

            String tasaStr = respuesta.substring(startNum, endNum).trim();
            double tasa;
            try {
                tasa = Double.parseDouble(tasaStr);
            } catch (NumberFormatException e) {
                throw new IOException("Error parseando tasa: " + tasaStr);
            }

            // Calculamos la cantidad convertida
            return cantidad * tasa;
        }
    }
}

