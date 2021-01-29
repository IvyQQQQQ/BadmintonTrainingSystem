package com.example.badmintontrainingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MainPage extends AppCompatActivity {
    GlobalVariable gv;
    public TextView txvUser1,txvUser2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        nameit();
        readUser();
    }


    public  void nameit(){
        gv = (GlobalVariable)getApplicationContext();
        txvUser1 = findViewById(R.id.txvUser1);
        txvUser2 = findViewById(R.id.txvUser2);
    }

    private void readUser() {
        String team,name,height,weight,age,gender;
        String path = MainActivity.CSV_FILE_PATH;
        //User.csv取得個資
        File inFile = new File(path+"User.csv");
        if(inFile.exists()){
            Log.d("check","User file exist");
        }else{
            Log.d("check","User file doesn't exist");
        }
        final StringBuilder sb = new StringBuilder();
        String readString;
        String[] arr;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inFile));
            int a =0;
            while ((readString = reader.readLine()) != null) {
                if(a == 0){
                    a++;
                    continue;
                }
                sb.append(readString);
                arr = sb.toString().split(",");
                team = arr[0];
                name = arr[1];
                height = arr[2];
                weight = arr[3];
                age = arr[4];
                gender = arr[5];
                txvUser1.setText("Team: "+team+"\n"+"Name: "+name);
                txvUser2.setText("Height:"+height+"\n"+"Weight: "+weight+"\n");
                gv.setUTeam(team);
                gv.setUName(name);

            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
