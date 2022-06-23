import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        InputStreamReader isR = null;
        BufferedReader bfR = null;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите запрос: ");

        URL url = new URL("https://ru.wikipedia.org/w/api.php?action=query&list=search&utf8=&format=json&srsearch=\"" + ScannerString(scanner) + "\"" );
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
            isR = new InputStreamReader(connection.getInputStream());
            bfR = new BufferedReader(isR);
            String line = bfR.readLine();
            String line2 = "";
            boolean lineB = false, read = false;
            for(int i=0;i<line.length()-13;i++){
                if((line.substring(i, i + 11)).equals("\"snippet\":\"")){
                    read = lineB = true;
                    i += 11;
                }
                if((line.substring(i, i + 13)).equals("\"timestamp\":\"")){
                    lineB = false;
                    i += 13;
                    line2 = line2.replace("<span class=\\\"searchmatch\\\">", "");
                    line2 = line2.replace("</span>", "");
                    System.out.println(line2.substring(0,line2.length()-2));
                    line2 = "";
                }
                if(lineB) line2 += line.charAt(i);
            }
            if(!read) System.out.print("Нечего не найдено по запросу");
        } else {
            System.out.print("Нечего не найдено по запросу или проблемы с соединением (попробуйте еще)");
        }
        if (isR != null) {isR.close();}
        if (bfR != null) {bfR.close();}
    }
    private static String ScannerString(Scanner scanner){
        String bool = scanner.next();
        if (!bool.equals("")) return bool;
        System.out.print("Вы не ввели запрос: ");
        return ScannerString(scanner);
    }

}