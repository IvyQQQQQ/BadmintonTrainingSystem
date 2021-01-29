package com.example.badmintontrainingsystem;

import android.app.Application;

public class GlobalVariable extends Application {
    private String UTeam,UName;     //User 隊名 名字


    //修改 變數値
    public void setUTeam(String team){
        this.UTeam = team;
    }
    public void setUName(String name){
        this.UName = name;
    }

    //取得 變數值
    public String getUTeam() {
        return UTeam;
    }
    public String getUName(){
        return UName;
    }

}
