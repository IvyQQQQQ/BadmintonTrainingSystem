package com.example.badmintontrainingsystem;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class CsvWriter {



    /*
        檔案儲存路徑
     */


    //PATH : /storage/emulated/0/Android/data/com.bestlab.weighttraining/files/Download/

    private static final String CSV_FILE_PATH =  MainActivity.CSV_FILE_PATH;

    /*
        檔案副檔名
     */
    private static final String CSV_FILE__NAME_EXTENSION = ".csv";

    /*
        檔案編碼格式

        若包含中文內容建議使用 UTF-8，其他編碼類型建議參考官方文件

        https://developer.android.com/reference/java/nio/charset/Charset.html
      */
    private static final String CSV_FILE_CHARSET = "UTF-8";

    /*
        檔案編碼標頭

        採用 UFT-8 編碼文件，使用 Excel 軟體開啟時，內容會顯示異常出現亂碼。為解決此問題，需要在檔案
        開頭以位元流寫入 BOM(Byte Order Mark) 標籤

        http://jeiworld.blogspot.tw/2009/09/phpexcelutf-8csv.html

        https://en.wikipedia.org/wiki/Byte_order_mark
     */
    //private static final byte[] CSV_FILE_BOM = new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};

    private static final String CSV_SEPARATOR = ",";


    public static void saveCsv(BufferedWriter bw){

        Log.e("SAVEE","OK");
        try
        {
            bw.flush();
            bw.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    //創建bw
    public static BufferedWriter createBW(String fileName) throws IOException {

        File file = new File(CSV_FILE_PATH + fileName);
        if (!file.exists()){
            file.getParentFile().mkdirs();
            try{
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }

        }


        FileOutputStream fos = new FileOutputStream(file);
        //fos.write(CSV_FILE_BOM);

        OutputStreamWriter osw = new OutputStreamWriter(fos, CSV_FILE_CHARSET);
        BufferedWriter bw = new BufferedWriter(osw);

        return bw;
    }

    //刪除檔案
    public static void deleteFile (String fileName) {
        File file = new File(CSV_FILE_PATH + fileName);

        file.delete();
    }




}