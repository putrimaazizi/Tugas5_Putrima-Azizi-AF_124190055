package com.example.tugas5.view;

import android.view.View;

import com.example.tugas5.entity.AppDatabase;
import com.example.tugas5.entity.DataBuku;

import java.util.List;

public interface MainContact {
    interface view extends View.OnClickListener{
        void successAdd();
        void successDelete();
        void resetForm();
        void getData(List<DataBuku> list);
        void editData(DataBuku item);
        void deleteData(DataBuku item);
    }

    interface presenter{
        void insertData(String nama, String alamat, int jumlah, AppDatabase database);
        void readData(AppDatabase database);
        void editData(String nama, String alamat, int jumlah, int id, AppDatabase database);
        void deleteData(DataBuku dataBuku, AppDatabase database);
    }
}
