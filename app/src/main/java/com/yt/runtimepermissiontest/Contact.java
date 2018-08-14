package com.yt.runtimepermissiontest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Contact extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ContactAdapter adapter;

    private List<ContactModel> mList = new ArrayList<>();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, Contact.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        readContact();

        recyclerView = findViewById(R.id.recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ContactAdapter(mList);

        recyclerView.setAdapter(adapter);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            getContacts();
        }
    }

    private void readContact() {
        for (int i = 0; i < 5; i ++) {
            ContactModel model = new ContactModel();
            model.setName("name" + i);
            model.setPhone("123" + i + "_323_" + i);
            mList.add(model);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContacts();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private void getContacts() {
        Cursor cursor = null;

        try {

            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    ContactModel model = new ContactModel();
                    model.setName(name);
                    model.setPhone(phone);
                    mList.add(model);
                }
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null){
                cursor.close();
            }
        }
    }
}
