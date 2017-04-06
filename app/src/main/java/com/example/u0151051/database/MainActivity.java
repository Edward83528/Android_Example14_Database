package com.example.u0151051.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//1.在專案的java檔->com.底下新增名字為:MYDBHelper的class檔->讓MYDBHelper繼承SQLiteOpenHelper(Android提供實作好資料庫的類別)
//再來將MYDBHelper實作SQLiteOpenHelper抽象類別的兩個抽象方法(onCreate/onUpgrade)和建立建構子(因為SQLiteOpenHelper有有參數的建構子,卻沒有無參數的建構子)
//2.SQLiteOpenHelper類別中可呼叫以下方法得到SQLiteDatabase物件：
//2.1:getReadableDatabase()方法(讀取資料庫的SQLiteDatabase物件，可用在查詢)
//2.2:getWritableDatabase()方法(擁有更新能力的SQLiteDatabase物件，用途為新增、修改或刪除)
public class MainActivity extends AppCompatActivity {
    EditText et1, et2, et3;
    TextView tv4;
    Button btn1, btn2, btn3, btn4;
    private SQLiteDatabase db;
    private MyDBHelp dbHelpe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findview();
        //建立資料庫
        dbHelpe = new MyDBHelp(this);
        db = dbHelpe.getWritableDatabase();
    }

    void findview() {
        et1 = (EditText) findViewById(R.id.editText);
        et2 = (EditText) findViewById(R.id.editText2);
        et3 = (EditText) findViewById(R.id.editText3);
        tv4 = (TextView) findViewById(R.id.textView4);
        btn1 = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btn1.setOnClickListener(c);
        btn2.setOnClickListener(c);
        btn3.setOnClickListener(c);
        btn4.setOnClickListener(c);

    }

    View.OnClickListener c = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button:
                    long id;
                    // //使用ContentValues類別，就像是Java的Map集合類別，專門儲存Key-Value的一組對應資料組，其中Key鍵值使用欄位的名稱，Value則是該欄位的值
                    ContentValues cv = new ContentValues();
                    cv.put("_id", Integer.parseInt(et1.getText().toString()));
                    cv.put("name", et2.getText().toString());
                    cv.put("score", Double.parseDouble(et3.getText().toString()));
                    //使用SQLiteDatabase的insert方法新增記錄至表格，第一個參數為表格名稱，第三個則是「資料包」
                    //取得資料庫物件後呼叫insert方法，傳入表格名稱與values集合物件以新增這筆記錄，若成功會回傳新增記錄的id值
                    //insert方法中的第二個參數可填入一個欄位名稱，如果設null,當第三個參數values內無任何資料時，會在該欄位上給予空值
                    id = db.insert("todos", null, cv);
                    tv4.setText("新增記錄成功" + id);
                    break;
                case R.id.button2:
                    int count;
                    int id1 = Integer.parseInt(et1.getText().toString());
                    ContentValues cv1 = new ContentValues();
                    cv1.put("score", Double.parseDouble(et3.getText().toString()));
                    count = db.update("todos", cv1, "_id=" + id1, null);
                    tv4.setText("更新記錄成功" + count);
                    break;
                case R.id.button3:
                    int count2;
                    int id2 = Integer.parseInt(et1.getText().toString());
                    count2 = db.delete("todos" +
                            "", "_id=" + id2, null);
                    tv4.setText("刪除記錄成功" + count2);
                    break;
                case R.id.button4:
                    SqlQuery("SELECT * FROM " + "todos");
                    break;
            }
        }
    };

    public void SqlQuery(String sql) {
        String[] colNames;
        String str = "";
        Cursor c = db.rawQuery(sql, null);
        colNames = c.getColumnNames();
        for (int i = 0; i < colNames.length; i++) {
            str += colNames[i] + "\t\t";
        }
        str += "\n";
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            str += c.getString(0) + "\t";
            str += c.getString(1) + "\t";
            str += c.getString(2) + "\n";
            c.moveToNext();
        }
        tv4.setText(str.toString());
    }
}
