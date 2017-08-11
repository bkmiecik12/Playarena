package com.example.bkmiecik.playarena.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.bkmiecik.playarena.Models.MyTeam;
import com.example.bkmiecik.playarena.Network.BackgroundWorker;
import com.example.bkmiecik.playarena.Network.DataDownloader;
import com.example.bkmiecik.playarena.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.ExecutionException;

public class SetTeamActivity extends AppCompatActivity {

    EditText etCode;
    Button bRegister;
    MyTeam myTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_team);

        etCode = (EditText) findViewById(R.id.et_register);
        bRegister = (Button) findViewById(R.id.b_accept);
        myTeam = null;

        etCode.performClick();

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackgroundWorker bw = new BackgroundWorker(v.getContext());
                bw.execute("register",etCode.getText().toString());
                try {
                    myTeam = parse(bw.get());
                    writeMyTeam();
                    startActivity(new Intent(SetTeamActivity.this,MainActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                final DataDownloader dd = new DataDownloader(myTeam.getTeamId(), myTeam.getSeasonId(), DataDownloader.Type.TEAM_SQUAD);
//                Thread thread = new Thread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        try  {
//                            myTeam.setPlayers(dd.downloadPlayers());
//                            myTeam.setTeams(dd.downloadTeams());
//                            myTeam.printPlayers();
//                            myTeam.printTeams();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//                thread.start();
            }
        });
    }

    private MyTeam parse (String data) throws JSONException {
        JSONArray ja = new JSONArray(data);
        JSONObject jo = ja.getJSONObject(0);

        return new MyTeam(jo.getInt("0"),jo.getString("1"),jo.getString("2"),jo.getInt("3"));
    }

    private void writeMyTeam() throws IOException {
        FileOutputStream fileOutputStream = openFileOutput("myTeam.lnp",MODE_PRIVATE);
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
        outputStream.writeObject(myTeam);
        outputStream.close();
    }
}
