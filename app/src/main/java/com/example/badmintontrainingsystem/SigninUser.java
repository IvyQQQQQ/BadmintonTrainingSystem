package com.example.badmintontrainingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SigninUser extends AppCompatActivity {

    //layout
    EditText etTeam,etName,etHeight,etWeight,etAge;
    Button btnSave,etTeamcheck,etNamecheck;
    RadioGroup Gender;
    //variable
    public  String TAG ="SigninUser";
    FirebaseFirestore db;
    public String team,name,gender;
    public int height,weight,age;
    BufferedWriter bw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_user);

        nameit();
        init();
        getFirestore();




    }

    public void init(){
        db = FirebaseFirestore.getInstance();
    }

    public void nameit(){
        etTeam = (EditText)findViewById(R.id.etTeam);
        etName = (EditText)findViewById(R.id.etName);
        etHeight = (EditText)findViewById(R.id.etHeight);
        etWeight = (EditText)findViewById(R.id.etWeight);
        etAge = (EditText)findViewById(R.id.etAge);
        Gender = (RadioGroup)findViewById(R.id.Gender);
        etTeamcheck = (Button)findViewById(R.id.etTeamcheck);
        etNamecheck = (Button)findViewById(R.id.etNamecheck);
        btnSave = (Button)findViewById(R.id.btnSave);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserdata();
            }

        });
        etTeamcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }

        });
        etNamecheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }

        });



    }

    public boolean isInteger(String s){
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        if(Integer.valueOf(s) > 0) {
            return true;
        }else{
            return false;
        }
    }

    public void saveUserdata(){
        //若未輸入完整資訊儲存未填寫的項目以在警告時顯示
        String warning = "";
        if(etTeam.getText().toString().matches("")){
            warning += " Team";
        }
        if(etName.getText().toString().matches("")){
            warning += " Name";
        }
        if(isInteger(etHeight.getText().toString()) == false){
            warning += " Height";
        }
        if(isInteger(etWeight.getText().toString()) == false){
            warning += " Weight";
        }
        if(isInteger(etAge.getText().toString()) == false){
            warning += " age";
        }
        if(Gender.getCheckedRadioButtonId() == -1){
            warning += " gender";
        }

        if(!(warning.equals(""))){
            //資料填寫不完整
            Toast.makeText(SigninUser.this, warning+"資料未填寫或填寫錯誤!", Toast.LENGTH_LONG).show();
        }else{
            team = etTeam.getText().toString();
            name = etName.getText().toString();
            height = Integer.valueOf(etHeight.getText().toString());
            weight = Integer.valueOf(etWeight.getText().toString());
            age = Integer.valueOf(etAge.getText().toString());
            RadioButton selectGender = (RadioButton)findViewById(Gender.getCheckedRadioButtonId());
            gender = selectGender.getText().toString();
            //將個人資料儲存成檔案方便模式讀取
            try{
                bw = CsvWriter.createBW("User.csv");
            }catch (IOException e){
                e.printStackTrace();
            }
            try{
                bw.write("team,name,height,weight,age,gender");
                bw.newLine();
                bw.write(team+","+name+","+height+","+weight+","+age+","+gender);
                bw.newLine();
            }catch (IOException e){
                e.printStackTrace();
            }
            CsvWriter.saveCsv(bw);
            finish();

        }
    }

    public void getFirestore(){
        CollectionReference collectionRef = db.collection("Teams");

        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        String s = document.getId();
                        Object q = document.getData().get("name");
                        Log.e("check", s+q);
                        Log.d(TAG, document.getId() + " => " + document.getData());

                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
    }


}
