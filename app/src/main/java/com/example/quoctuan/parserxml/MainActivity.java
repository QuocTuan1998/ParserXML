package com.example.quoctuan.parserxml;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.quoctuan.parserxml.model.CD;
import com.example.quoctuan.parserxml.model.ListCD;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;

public class MainActivity extends AppCompatActivity {
    private Button btnGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addControls() {
        btnGet = (Button) findViewById(R.id.btnGet);
    }

    private void addEvents() {
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                get da data from url
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        super.run();
//                        get data from url
                        String result = handleGetDataFromURL("https://www.w3schools.com/xml/cd_catalog.xml");
                        Message msg = new Message();
                        msg.obj = result;

                        handler.sendMessage(msg);
                    }
                };
                thread.start();
            }
        });
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.obj.toString();
//            create a Serializer
            Serializer serializer = new Persister();

            try {
                ListCD listCD = serializer.read(ListCD.class,result);
//                get title of all CD
                String listTitle = "";
                for (CD cd:listCD.arrCD){
                    listTitle += cd.title;
                    listTitle += "\n";
                }
                Toast.makeText(MainActivity.this,
                        listTitle,
                        Toast.LENGTH_LONG).
                        show();

            } catch (Exception e) {
                e.printStackTrace();
            }

//            Toast.makeText(MainActivity.this,
//                    result,
//                    Toast.LENGTH_LONG).
//                    show();

        }
    };

// get data from url
    private String handleGetDataFromURL(String url) {
        String result = null;
        int CONECT_TIMEOUT = 3000;
        int SOCKET_TIMEOUT = 5000;

        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams,CONECT_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParams,SOCKET_TIMEOUT);

        HttpClient httpClient = new DefaultHttpClient(httpParams);
        HttpGet httpGet = new HttpGet(url);

        try {

            HttpResponse httpResponse = httpClient.execute(httpGet);

            if (httpResponse != null){

                InputStream inputStream = httpResponse.getEntity().getContent();
                result = convertStreamToString(inputStream);
            }

        }catch (ConnectTimeoutException cx){
            result = "CONNECTION TIMEOUT(KẾT NỐI CHẬM)";

        }catch (SocketTimeoutException sx){
            result = "SOCKET TIMEOUT (TRẢ DỮ LIỆU VỀ CHẬM)";
        }catch (Exception ex){
            result = "EXCEPTION TIMEOUT";
        }
        return result;
    }

    private String convertStreamToString(InputStream inputStream) {

        String line = "";
        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            while ((line = bufferedReader.readLine()) != null){
                builder.append(line);
            }
        }catch (Exception ex){

        }

        return builder.toString();
    }
}
