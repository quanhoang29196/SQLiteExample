package com.e_leisure.quanhoang.duocfinal.LOGIC.DATABASE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.e_leisure.quanhoang.duocfinal.LOGIC.POJO.TIMKIEM.Med;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final String TAG = Database.class.getSimpleName();


    public static final String DB_NAME = "Results.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "results";
    public static final String ID = "id";
    public static final String TENTHUOC = "tenThuoc";
    public static final String SODANGKY = "soDangKy";
    public static final String DUONGDANANH = "duongDanAnh";
    public static final String HOATCHATCHINH = "hoatChatChinh";
    public static final String HAMLUONG = "hamLuong";
    public static final String CHIDINH = "chiDinh";
    public static final String HANGSANXUAT = "hangSanXuat";
    public static final String NUOCSANXUAT = "nuocSanXuat";


    String createTable = "CREATE TABLE " + TABLE_NAME + "" +
            " (" + ID + " TEXT , " +
            TENTHUOC +" TEXT , " +
            SODANGKY + " TEXT , " +
            DUONGDANANH + " TEXT , " +
            HOATCHATCHINH +" TEXT , " +
            HAMLUONG +" TEXT , " +
            CHIDINH +" TEXT , " +
            HANGSANXUAT +" TEXT , " +
            NUOCSANXUAT + " TEXT )";


    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public void addMed (Med med) {

        Log.d(TAG, "Database Got " + med.getTenThuoc());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, med.getId());
        contentValues.put(TENTHUOC, med.getTenThuoc());
        contentValues.put(SODANGKY, med.getSoDangKy());
        contentValues.put(DUONGDANANH, med.getDuongDanAnh());
        contentValues.put(HOATCHATCHINH, med.getHoatChatChinh());
        contentValues.put(HAMLUONG, med.getHamLuong());
        contentValues.put(CHIDINH, med.getChiDinh());
        contentValues.put(HANGSANXUAT, med.getHangSanXuat());
        contentValues.put(NUOCSANXUAT, med.getNuocSanXuat());

        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public Cursor getMedById (int Id, SQLiteDatabase db) {

        String[] med = {ID, TENTHUOC, SODANGKY, DUONGDANANH, HOATCHATCHINH, HAMLUONG, CHIDINH, HANGSANXUAT, NUOCSANXUAT};
        String selection = ID + " LIKE ?";
        String[] selection_args = {Integer.toString(Id)};

        Cursor cursor = db.query(TABLE_NAME, med, selection, selection_args, null, null, null);
        return cursor;
    }

    public List<Med> getAllMed() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY "  + ID +  " LIMIT " + 5 , null);

        List<Med> medList = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                Med med = new Med();
                med.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID))));
                med.setTenThuoc(cursor.getString(cursor.getColumnIndex(TENTHUOC)));
                med.setChiDinh(cursor.getString(cursor.getColumnIndex(CHIDINH)));
                med.setDuongDanAnh(cursor.getString(cursor.getColumnIndex(DUONGDANANH)));
                med.setHamLuong(cursor.getString(cursor.getColumnIndex(HAMLUONG)));
                med.setHangSanXuat(cursor.getString(cursor.getColumnIndex(HANGSANXUAT)));
                med.setNuocSanXuat(cursor.getString(cursor.getColumnIndex(NUOCSANXUAT)));
                med.setHoatChatChinh(cursor.getString(cursor.getColumnIndex(HOATCHATCHINH)));
                med.setSoDangKy(cursor.getString(cursor.getColumnIndex(SODANGKY)));

                medList.add(med);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return medList;
    }
}
