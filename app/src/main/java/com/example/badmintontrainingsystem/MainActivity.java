package com.example.badmintontrainingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

//登入鍵，如果存在則登入，如果不存在則詢問是否登入或建立帳號。

public class MainActivity extends AppCompatActivity {

    public static String CSV_FILE_PATH;
    public static Context mContext;

    Button signin,change;
    int oper = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();
        nameit();




    }

    public void init(){
        mContext = MainActivity.this;

        FirebaseApp.initializeApp(this);

        CSV_FILE_PATH = mContext.getExternalFilesDir(null) + File.separator + "Download" + File.separator;
    }

    public void nameit(){

        signin = (Button)findViewById(R.id.signin);
        change = (Button)findViewById(R.id.change);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oper = 1;
                checkuser();
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oper = 2;
                checkuser();
            }
        });
    }

    public void checkuser(){
        //確認app儲存使用者資料夾的路徑是否存在
        Log.e("path","file path = "+CSV_FILE_PATH);
//        File f = new File(CSV_FILE_PATH  + "UserData");
//        if (!f.exists()) {
//            f.mkdir();
//            Log.e("Check User","Create UserData folder");
//        }
//        if(f.exists())
//        {
//            Log.e("Check User","UserData folder have existed");
//        }


        File file = new File(CSV_FILE_PATH, "User.csv");
        Intent intent;

        //確認使用者資料是否存在
        if(!file.exists()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("該手機未曾登入選手資料，是否登入或創建?");
            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, SigninUser.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("否，稍後", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this,"尚未登入選手資料",Toast.LENGTH_SHORT).show();
                }
            });
            builder.show();
        }else{
            switch(oper){
                case(1):
                    intent = new Intent();
                    intent.setClass(MainActivity.this, MainPage.class);
                    startActivity(intent);
                    break;

                case(2):
                    intent = new Intent();
                    intent.setClass(MainActivity.this, SigninUser.class);
                    startActivity(intent);
                    break;
            }
        }

    }

}
