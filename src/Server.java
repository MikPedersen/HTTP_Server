import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {

    public static void main(String[] args) {

        try {

            System.out.println("Serveren er startet og lytter på port 80");

            ServerSocket serverSocket = new ServerSocket(80); // Serverobjektet instansieres
            Socket socket = serverSocket.accept();                  // Serveren åbner port 80 for forbindelser

            // Vi læser en stream med bogstaver fra browserens request in igennem socketen
            BufferedReader request = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Vi skriver headers som respons vha. dette PrintWriter objekt
            PrintWriter responsHeaders = new PrintWriter(socket.getOutputStream());

            // Vi skriver HTML som respons vha. dette DataOutputStream objekt
            DataOutputStream responseStream = new DataOutputStream(socket.getOutputStream());

            // Vi indlæser alle linjer i den indkomne requests
            while (true) {

                String input = request.readLine();
                System.out.println("" + input);

                // En blank linje i HTTP er det, der slutter requesten
                if (input.isEmpty()) {

                    System.out.println("Request modtaget, svar sendes nu.");

                    // Vi konstruerer og sender en HTTP response linje for linje
                    responsHeaders.println("HTTP/1.1 200 OK"); // HTTP Status kode
                    responsHeaders.println("Content-Type: text/html; charset=utf-8"); // Typen af svaret er text eller html
                    responsHeaders.println(); // Blank line between headers and content, very important !
                    responsHeaders.flush();

                    responseStream.writeBytes("<h1>Velkommen til miks side<br></h1>"); // Indholdet i responsen
                    responseStream.writeBytes("<h2>Tjek den her flotte fyr.<br></h2>");
                    responseStream.writeBytes("<img src=\"https://scontent-cph2-1.xx.fbcdn.net/v/t1.0-9/1512630_189905787881908_1314959272_n.jpg?_nc_cat=102&_nc_sid=85a577&_nc_ohc=mA-9j-u_OJAAX9zStYW&_nc_ht=scontent-cph2-1.xx&oh=f4701bf2f6c2f900da9a82ebade010ab&oe=5F793961\" alt=\"W3Schools.com\">\n");
                    responseStream.writeBytes("<br><br><br><a href=\"https://github.com/MikPedersen\">Bes&oslash;g min github via denne tekst!<br></a>");
                    responseStream.flush();

                    // Vi lukker begge streams
                    responsHeaders.close();
                    responseStream.close();
                    System.out.println("Svar er sendt til browseren.");

                }
            }
        }
        catch (SocketException e) {
            if ( e.getMessage().equals("Socket closed"))
                System.out.println("Forbindelsen afsluttet efter en succesfuld response-request forløb.");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}