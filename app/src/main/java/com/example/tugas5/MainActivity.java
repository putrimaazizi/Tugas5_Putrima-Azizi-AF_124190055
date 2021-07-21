package com.example.tugas5;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugas5.entity.AppDatabase;
import com.example.tugas5.entity.DataBuku;
import com.example.tugas5.view.MainAdapter;
import com.example.tugas5.view.MainContact;
import com.example.tugas5.view.MainPresenter;



import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContact.view{
    private AppDatabase appDatabase;
    private MainPresenter mainPresenter;
    private MainAdapter mainAdapter;

    private Button btnOK;
    private RecyclerView recyclerView;
    private EditText etNama, etAlamat, etJumlah;

    private int id = 0;
    boolean edit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOK = findViewById(R.id.btn_submit);
        btnOK.setOnClickListener(this);
        etNama = findViewById(R.id.et_nama);
        etAlamat = findViewById(R.id.et_alamat);
        etJumlah = findViewById(R.id.et_pemain);
        recyclerView = findViewById(R.id.rc_main);
        appDatabase = appDatabase.inidb(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mainPresenter = new MainPresenter(this);
        mainPresenter.readData(appDatabase);
    }

    @Override
    public void successAdd() {
        Toast.makeText(this,"Input data berhasil",Toast.LENGTH_SHORT).show();
        mainPresenter.readData(appDatabase);
    }

    @Override
    public void successDelete() {
        Toast.makeText(this,"Berhasil Menghapus Data",Toast.LENGTH_SHORT).show();
        mainPresenter.readData(appDatabase);
    }

    @Override
    public void resetForm() {
        etNama.setText("");
        etAlamat.setText("");
        etJumlah.setText("");
        btnOK.setText("Submit");
    }

    @Override
    public void getData(List<DataBuku> list) {
        mainAdapter = new MainAdapter(this,list, this);
        recyclerView.setAdapter(mainAdapter);
    }

    @Override
    public void editData(DataBuku item) {
        etNama.setText(item.getName());
        etAlamat.setText(item.getAddress());
        etJumlah.setText(item.getPemain());
        id = item.getId();
        edit = true;
        btnOK.setText("EDIT DATA");
    }

    @Override
    public void deleteData(DataBuku item) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        }else{
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Menghapus Data");
        builder.setMessage(("Apakah anda yakin?"));
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                resetForm();
                ;
                mainPresenter.deleteData(item, appDatabase);
            }
        });
        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        })
                .setIcon(android.R.drawable.ic_dialog_dialer).show();
    }


    @Override
    public void onClick(View view) {
        if(view==btnOK){
            if (etNama.getText().toString().equals("")||
                    etAlamat.getText().toString().equals("")||
                    etJumlah.getText().toString().equals("")) {
                Toast.makeText(this,"Anda belum melengkapi semua data yang ada", Toast.LENGTH_SHORT).show();
            }else {
                if(edit){
                    mainPresenter.insertData(etNama.getText().toString(),etAlamat.getText().toString(),
                            Integer.parseInt(etJumlah.getText().toString()),appDatabase);
                }else{
                    mainPresenter.editData(etNama.getText().toString(),etAlamat.getText().toString(),
                            Integer.parseInt(etJumlah.getText().toString()),id,appDatabase);
                    edit = false;
                }
                resetForm();
            }
        }
    }
}