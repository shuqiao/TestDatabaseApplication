package com.main.testdatabaseapplication;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    private EditText etId;
    private EditText etName;
    private EditText etAge;
    private EditText etSex;
    private EditText etScore;
    private Button btnAdd;
    private Button btnAll;
    private Button btnUpdate;
    private Button btnDelete;

    private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etId = (EditText) findViewById(R.id.et_id);
        etName = (EditText) findViewById(R.id.et_name);
        etAge = (EditText) findViewById(R.id.et_age);
        etSex = (EditText) findViewById(R.id.et_sex);
        etScore = (EditText) findViewById(R.id.et_score);
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnAll = (Button) findViewById(R.id.btn_all);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnDelete = (Button) findViewById(R.id.btn_delete);

        myDb = new DatabaseHelper(this);

        Log.i(TAG, myDb.getPath());

        initListeners();
    }

    private void initListeners() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean suc = myDb.insert(getEtString(etName), getEtString(etAge), getEtString(etSex), getEtString(etScore));

                Toast.makeText(MainActivity.this, suc ? "Add data Success!" : "Add data Error!", Toast.LENGTH_SHORT).show();
            }
        });
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("SQLiteDatabase data");
                builder.setMessage(myDb.getAll());
                builder.setCancelable(true);
                builder.show();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.update(getEtString(etId), getEtString(etName), getEtString(etAge), getEtString(etSex), getEtString(etScore));
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = myDb.delete(getEtString(etName));
                Toast.makeText(MainActivity.this, "Delete data num = " + num, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getEtString(EditText et) {
        return et.getText().toString();
    }
}
