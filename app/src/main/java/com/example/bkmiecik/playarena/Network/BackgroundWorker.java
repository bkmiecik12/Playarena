package com.example.bkmiecik.playarena.Network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by bkmiecik on 08.08.17.
 */
public class BackgroundWorker extends AsyncTask<String,Void,String> {

    Context context;

    public BackgroundWorker(Context context){
        this.context=context;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String addMatchUrl = "http://playarena.kmiecik.tk/match.php";
        String addEventUrl = "http://playarena.kmiecik.tk/goal.php";
        String updateUrl = "http://playarena.kmiecik.tk/update.php";
        String registerUrl = "http://playarena.kmiecik.tk/register.php";


        if(type.equals("match")){
            try{
                String matchId = params[1];
                String home = params[2];
                String away = params[3];
                String date = params[4];
                String matchResult = params[5];

                Log.d("weszlo","1");

                URL url = new URL(addMatchUrl);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String postData = URLEncoder.encode("matchId","UTF-8")+"="+URLEncoder.encode(matchId,"UTF-8")+"&"+
                                    URLEncoder.encode("home","UTF-8")+"="+URLEncoder.encode(home,"UTF-8")+"&"+
                                    URLEncoder.encode("away","UTF-8")+"="+URLEncoder.encode(away,"UTF-8")+"&"+
                                    URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode(date,"UTF-8")+"&"+
                                    URLEncoder.encode("matchResult","UTF-8")+"="+URLEncoder.encode(matchResult,"UTF-8");

                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                Log.d("weszlo","2");

                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine())!=null){
                    result+=line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                Log.d("weszlo","3");
                return result;

            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }

        else if (type.equals("event")){
            try{
                String matchId = params[1];
                String player = params[2];
                String time = params[3];
                String team = params[4];

                URL url = new URL(addEventUrl);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();


                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String postData = URLEncoder.encode("matchId","UTF-8")+"="+URLEncoder.encode(matchId,"UTF-8")+"&"+
                        URLEncoder.encode("player","UTF-8")+"="+URLEncoder.encode(player,"UTF-8")+"&"+
                        URLEncoder.encode("time","UTF-8")+"="+URLEncoder.encode(time,"UTF-8")+"&"+
                        URLEncoder.encode("team","UTF-8")+"="+URLEncoder.encode(team,"UTF-8");

                Log.d("data",postData);

                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                String result = "";
                String line = "";

                Log.d("wbijam:","xx");

                while ((line = bufferedReader.readLine())!=null){
                    result+=line;
                }

                Log.d("wbijam:","xxx");
                Log.d("doInBackground: ",result);
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (java.io.IOException e) {
                e.printStackTrace();
            }

        }
        else if(type.equals("update")) {

            try{
                String matchId = params[1];
                String matchResult = params[2];

                Log.d("weszlo","1");

                URL url = new URL(updateUrl);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String postData = URLEncoder.encode("matchId","UTF-8")+"="+URLEncoder.encode(matchId,"UTF-8")+"&"+
                        URLEncoder.encode("matchResult","UTF-8")+"="+URLEncoder.encode(matchResult,"UTF-8");

                Log.d("PD:",postData);

                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine())!=null){
                    result+=line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                Log.d("result",result);
                return result;

            } catch (java.io.IOException e) {
                e.printStackTrace();
            }

        }
        else if(type.equals("register")) {

            try{
                String teamId = params[1];

                Log.d("weszlo","1");

                URL url = new URL(registerUrl);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String postData = URLEncoder.encode("teamId","UTF-8")+"="+URLEncoder.encode(teamId,"UTF-8");

                Log.d("PD:",postData);

                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine())!=null){
                    result+=line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                Log.d("result",result);
                return result;

            } catch (java.io.IOException e) {
                e.printStackTrace();
            }

        }
        Log.d("wyszlo","0");
        return null;
    }
}
