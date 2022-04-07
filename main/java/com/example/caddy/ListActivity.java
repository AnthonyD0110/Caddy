package com.example.caddy;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {

    private Database mDbHelper;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_list);

        listView = (ListView) findViewById(R.id.listCourse);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView text = (TextView) view;
                text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
        });

        mDbHelper = new Database(this);
        mDbHelper.open();
        fillData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void deleteAllList(){
        mDbHelper.deleteAllList();
        fillData();
    }

    public void fillData() {
        Cursor c = mDbHelper.fetchAllListProducts();
        startManagingCursor(c);

        String[] from = new String[] { Database.KEY_NAME};
        int[] to = new int[] { R.id.text2 };

        SimpleCursorAdapter products =
                new SimpleCursorAdapter(this, R.layout.list_row, c, from, to,0);
        listView.setAdapter(products);
    }

    public void fillDataCategory(View view) {
        Cursor c = mDbHelper.fetchAllListProductsCategory();
        startManagingCursor(c);

        String[] from = new String[] { Database.KEY_NAME };
        int[] to = new int[] { R.id.text2 };

        SimpleCursorAdapter products =
                new SimpleCursorAdapter(this, R.layout.list_row, c, from, to,0);
        listView.setAdapter(products);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.actionRemove) {
            showAlertDialogDeleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showAlertDialogDeleteAll(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Caddy");
        alert.setMessage("Souhaitez-vous vraiment effacer la liste de course ? ");
        alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteAllList();
            }
        });
        alert.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alert.create().show();
    };

    public void previousActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
