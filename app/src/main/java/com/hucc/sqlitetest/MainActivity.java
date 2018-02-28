package com.hucc.sqlitetest;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.hucc.sqlitetest.db.SQLiteDB;
import com.hucc.sqlitetest.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements RecyclerOnItemClickListener {

    private RecyclerView mRecyclerView = null;
    private TextView  mEmptyView = null;
    private FloatingActionButton fab = null;
    private ContactListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SQLiteDB  sqLiteDB;
    private List<Contact> mContactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.listView);
        mEmptyView = findViewById(android.R.id.empty);
        fab = findViewById(R.id.fab);

        if (mContactList == null){
            mEmptyView.setVisibility(View.VISIBLE);
        }

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new ContactListAdapter(this);
        mAdapter.setOnItemClickListener(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActActivity.start(MainActivity.this);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    private void loadData() {
        sqLiteDB = new SQLiteDB(MainActivity.this);
        mContactList = new ArrayList<>();

        Cursor cursor = sqLiteDB.query();
        Contact contactBean;

        if (cursor!= null&&cursor.moveToFirst()) {
            mEmptyView.setVisibility(View.GONE);
            do {
             contactBean = new Contact();

             contactBean.setId(cursor.getInt(0));
             contactBean.setName(cursor.getString(1));
             contactBean.setPhone(cursor.getString(2));

             mContactList.add(contactBean);
            } while (cursor.moveToNext());
          }

        mAdapter.clear();
        mAdapter.addAll(mContactList);
    }

    @Override
    public void onItemClick(int position, View view) {
        ActActivity.start(MainActivity.this, mAdapter.getItem(position));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
