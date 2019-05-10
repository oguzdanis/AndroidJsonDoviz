package com.example.doviz;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView cadText;
    TextView tryText;
    TextView jpyText;
    TextView usdText;
    TextView chfText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cadText = (TextView)findViewById(R.id.cadText);
        tryText = (TextView)findViewById(R.id.tryText);
        jpyText = (TextView)findViewById(R.id.jpyText);
        usdText = (TextView)findViewById(R.id.usdText);
        chfText = (TextView)findViewById(R.id.chfText);

    }

    public void getRates(View view){
        DownloadData downloadData = new DownloadData();

        try {
            String url = "http://data.fixer.io/api/latest?access_key=93d9bafd3f045a3c5cadefec8512fafd&format=1";
            downloadData.execute(url);
        }catch (Exception e){

        }

    }

    private class DownloadData extends AsyncTask<String,Void,String>{



        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try{
                url = new URL(strings[0]);
                httpURLConnection= (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();
                while (data>0){
                    char character = (char) data;
                    result +=character;

                    data=inputStreamReader.read();
                }

                return result;
            }catch (Exception e){
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            //System.out.println("Alinan data:"+s);
            try{

                JSONObject jsonObject = new JSONObject(s);
                String base = jsonObject.getString("base");
                System.out.println("Base"+base);

                String rates = jsonObject.getString("rates");
                System.out.println("rates"+rates);



                JSONObject jsonObject1= new JSONObject(rates);

                String Cad = jsonObject1.getString("CAD");
                cadText.setText("Cad :"+Cad);

                String TurkishLira = jsonObject1.getString("TRY");
                System.out.println("TRY :"+TurkishLira);

                String Jpy = jsonObject1.getString("JPY");
                jpyText.setText("Jpy :"+Jpy);

                String Usd = jsonObject1.getString("USD");
                usdText.setText("Use :"+Usd);

                String Chf = jsonObject1.getString("CHF");
                chfText.setText("Chf :"+Chf);


                tryText.setText("try :"+TurkishLira);

            }catch (Exception e){

                System.out.println(e);
            }

            super.onPostExecute(s);
        }

    }

}