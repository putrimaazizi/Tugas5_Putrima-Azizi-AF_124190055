package com.example.tugas5.view;

import android.os.AsyncTask;
import android.util.Log;

import com.example.tugas5.entity.AppDatabase;
import com.example.tugas5.entity.DataBuku;

import java.util.List;

public class MainPresenter implements  MainContact.presenter {
    private MainContact.view view;

    public MainPresenter(MainContact.view view) {
        this.view = view;
    }

    class InsertData extends AsyncTask<Void,Void,Long>{
        private AppDatabase appDatabase;
        private DataBuku dataBuku;

        public InsertData(AppDatabase appDatabase,DataBuku dataBuku){
            this.appDatabase = appDatabase;
            this.dataBuku =  dataBuku;
        }

        @Override
        protected Long doInBackground(Void... voids) {
            return appDatabase.dao().insertData(dataBuku);
        }

        protected void onPostExecute(Long along) {
            super.onPostExecute(along);
            view.successAdd();
        }
    }

    @Override
    public void insertData(String nama, String alamat, int jumlah, AppDatabase database) {
        final DataBuku dataBuku = new DataBuku();
        dataBuku.setName(nama);
        dataBuku.setAddress(alamat);
        dataBuku.setPemain(jumlah);
        new InsertData(database,dataBuku).execute();
    }

    @Override
    public void readData(AppDatabase database) {
        List<DataBuku>list;
        list = database.dao().getData();
        view.getData(list);
    }


    class EditData extends AsyncTask<Void, Void, Integer> {
        private AppDatabase appDatabase;
        private DataBuku dataBuku;

        public EditData(AppDatabase appDatabase, DataBuku dataBuku) {
            this.appDatabase = appDatabase;
            this.dataBuku = dataBuku;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return appDatabase.dao().updateData(dataBuku);
        }

        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            Log.d("integer db","onPostExecute :"+integer);
            view.successAdd();
        }
    }

    @Override
    public void editData(String nama, String alamat, int jumlah, int id, AppDatabase database) {
        final DataBuku dataBuku = new DataBuku();
        dataBuku.setName(nama);
        dataBuku.setAddress(alamat);
        dataBuku.setPemain(jumlah);
        dataBuku.setId(id);
        new EditData(database,dataBuku).execute();
    }

    class DeleteData extends AsyncTask<Void,Void,Long>{
        private AppDatabase appDatabase;
        private DataBuku dataBuku;

        public DeleteData(AppDatabase appDatabase, DataBuku dataBuku) {
            this.appDatabase = appDatabase;
            this.dataBuku = dataBuku;
        }

        @Override
        protected Long doInBackground(Void... voids) {
            appDatabase.dao().deleteData(dataBuku);
            return null;
        }

        protected void onPostExecute(Long along) {
            super.onPostExecute(along);
            view.successDelete();
        }
    }

    @Override
    public void deleteData(DataBuku dataBuku, AppDatabase database) {
        new DeleteData(database, dataBuku).execute();
    }
}

