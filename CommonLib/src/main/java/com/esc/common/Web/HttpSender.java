package com.esc.common.Web;

import com.esc.common.TextUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by afirsov on 1/27/2016.
 */
public class HttpSender {
    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {

        HttpSender http = new HttpSender();

        String params = "location=-33.8670,151.1957&radius=500&types=food&name=cruise&key=AIzaSyBMpMIj38PG1F1_B3ynC0AeIq-rlKmWMk0";
        http.send("https://maps.googleapis.com/maps/api/place/nearbysearch/json?" + params,Method.GET, null);
    }

    public String send(String path, Method method, String params) throws Exception {

        String url = path;
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod(method.name());
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = params;

        // Send post request
        if(method == Method.POST) {
            if(TextUtils.isEmpty(urlParameters))
            {
                throw new NullPointerException("Parameters for POST request in HttpSender are null");
            }
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
        }

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println("Response Content : " + response.toString());

        return response.toString();

    }
}
