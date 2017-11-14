package sample.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;




/*
https://translate.yandex.net/api/v1.5/tr/translate ?
key=<API-ключ>
 & text=<переводимый текст>
 & lang=<направление перевода>
 & [format=<формат текста>]
 & [options=<опции перевода>]
*/

public class Translater {

    private final String USER_AGENT = "Mozilla/5.0";
    private final String KEY = "trnsl.1.1.20170903T104814Z.3f337c1f825e0053.44b1227f43bfdd393e36103b56b9775bcbefe8c4";
    private final String URL = "https://translate.yandex.net/api/v1.5/tr/translate?key=";


    /*public static void main(String[] args) {
        Translater translate = new Translater();
        try {
            String textToTranslate = "The GNOME desktop environment comes installed with Fedora Workstation. GNOME hackers have continued to refine it over several years. However, not all third party software providers update their apps accordingly. Some software providers still make use of outdated status bar icons for..";

            System.out.println(translate.tanslate(textToTranslate));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public String tanslate(String text) throws Exception {
        return  sendPost(text);

    }

    private HttpsURLConnection getConnection(String urll) throws IOException {
        URL url  = new URL(urll);
        return (HttpsURLConnection) url.openConnection();
    }


    // HTTP POST request
    private String sendPost(String text) throws Exception {

        HttpsURLConnection con = getConnection(URL + KEY);

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");


        String urlParameters = String.format("&text=%s&lang=en-ru&format=plain&options=1",text);

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

//        int responseCode = con.getResponseCode();
//        System.out.println("\nSending 'POST' request to URL : " + URL + "<key>");
//        System.out.println("Post parameters : " + urlParameters);
//        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
//        System.out.println(response.toString());
        Document result = Jsoup.parse(response.toString());
        return result.getElementsByTag("text").text();

    }



}