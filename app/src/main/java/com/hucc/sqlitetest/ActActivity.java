package com.hucc.sqlitetest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hucc.sqlitetest.db.SQLiteDB;
import com.hucc.sqlitetest.model.Contact;

/**
 * Created by chunchun.hu on 2018/2/26.
 */

public class ActActivity extends BaseActivity implements View.OnClickListener {

    private EditText  nameEdit,phoneEdit;
    private Button   BtnAdd, BtnEdit, BtnDelete;

    private SQLiteDB mSqliteDB;
    private Contact contactBean;

    public static void start(Context context) {
        Intent intent = new Intent(context, ActActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, Contact item) {
        Intent intent = new Intent(context, ActActivity.class);
        intent.putExtra(ActActivity.class.getSimpleName(),item);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act);

        nameEdit = findViewById(R.id.personText);
        phoneEdit = findViewById(R.id.phoneText);
        BtnAdd = findViewById(R.id.btnAdd);
        BtnEdit = findViewById(R.id.btnEdit);
        BtnDelete = findViewById(R.id.btnDelete);

        BtnAdd.setOnClickListener(this);
        BtnEdit.setOnClickListener(this);
        BtnDelete.setOnClickListener(this);

        contactBean = getIntent().getParcelableExtra(ActActivity.class.getSimpleName());
        if (contactBean != null){
            BtnAdd.setVisibility(View.INVISIBLE);

            nameEdit.setText(contactBean.getName());
            phoneEdit.setText(contactBean.getPhone());
        }else{
            BtnEdit.setVisibility(View.INVISIBLE);
            BtnDelete.setVisibility(View.INVISIBLE);
        }
        mSqliteDB = new SQLiteDB(ActActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
         if (v == BtnAdd){
            contactBean = new Contact();
            contactBean.setName(nameEdit.getText().toString());
            contactBean.setPhone(phoneEdit.getText().toString());
            mSqliteDB.add(contactBean);

             Toast.makeText(ActActivity.this,"Inserted!",Toast.LENGTH_SHORT).show();
             finish();
         }else if (v == BtnEdit){
            contactBean.setName(nameEdit.getText().toString());
            contactBean.setPhone(phoneEdit.getText().toString());
            mSqliteDB.update(contactBean);

            Toast.makeText(ActActivity.this, "updated!", Toast.LENGTH_SHORT).show();
            finish();
         }else if (v == BtnDelete){
             mSqliteDB.delete(contactBean.getId());

             Toast.makeText(ActActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();
             finish();
         }
    }
}
