package com.example.caddy;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.caddy.databinding.ActivityMainBinding;

import android.view.Menu;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

//Classe qui s'occupe de la liste prédéfinie
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private EditText editText;
    private ListView listView;
    private Database mDbHelper;

    //Méthode onCreate de MainActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        editText = (EditText) findViewById(R.id.editTextProduct);
        listView = (ListView) findViewById(R.id.listProduct);

        //Méthode d'appui long dans la liste prédéfinie
        registerForContextMenu(listView);

        //Méthode de d'appui court dans la lsite prédéfinie
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = searchProductName(i);
                int category = searchProductCategory(i);
                insertProduct(name,category);
            }
        });

        mDbHelper = new Database(this); //Initialisation de l'objet Database
        mDbHelper.open(); //Ouverture de la base de données
        fillData();
    }

    //Méthode d'ajout de produit dans la base de données à partir du bouton
    public void sentMessage(View view){
        createProduct(editText.getText().toString());
        editText.setText("");
    };

    //Méthode de création du menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Initialisation de la méthode d'ajout de produit de la base de données
    public void createProduct(String productName) {
        mDbHelper.createProduct(productName);
        fillData();
    }

    //Initialisation de la méthode de suppresion de produit de la base de données
    public void deleteProduct(long productId) {
        mDbHelper.deleteProduct(productId);
        fillData();
    }

    //Initialisation de la méthode de supression de tous les produits de la base de données
    public void deleteAll(){
        mDbHelper.deleteAll();
        fillData();
    }

    //Initialisation de la méthode d'insertion de la liste prédifinie à la liste de course
    public void insertProduct(String productName, int productCategory) {
        mDbHelper.insertListProduct(productName, productCategory);
    }

    //Initialisation de la méthode de recherche de nom produit de la base de données
    public String searchProductName(int i) {
        return mDbHelper.searchProductName(i);
    }

    //Initialisation de la méthode de recherche de catégorie de produit de la base de données
    public int searchProductCategory(int i) {
        return mDbHelper.searchProductCategory(i);
    }

    //Méthode de remplissage de la la listview à partir de la méthode de sélection des produits de la base de données
    public void fillData() {
        Cursor c = mDbHelper.fetchAllProducts();
        startManagingCursor(c);

        String[] from = new String[] { Database.KEY_NAME };
        int[] to = new int[] { R.id.text };

        SimpleCursorAdapter products =
                new SimpleCursorAdapter(this, R.layout.product_row, c, from, to,0);
        listView.setAdapter(products);
    }

    //Méthode de remplissage de la la listview à partir de la méthode de sélection des produits par catégorie de la base de données
    public void fillDataCategory(View view) {
        Cursor c = mDbHelper.fetchAllProductsCategory();
        startManagingCursor(c);

        String[] from = new String[] { Database.KEY_NAME };
        int[] to = new int[] { R.id.text };

        SimpleCursorAdapter products =
                new SimpleCursorAdapter(this, R.layout.product_row, c, from, to,0);
        listView.setAdapter(products);
    }

    //Méthode de sélection des items du menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.actionRemove) {
            showAlertDialogDeleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Méthode de menu contextuel
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    //Méthode de sélection des items du menu contextuel
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)
                item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.effacerItem:
                showAlertDialogDeleteItem(info.id);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    //Méthode de la boîte de dialogue de supression de tous les produits
    public void showAlertDialogDeleteAll(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Caddy");
        alert.setMessage("Souhaitez-vous vraiment effacer la liste prédéfinie ? ");
        alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteAll();
            }
        });
        alert.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alert.create().show();
    };

    //Méthode de la boîte de dialogue de supression d'un produit
    public void showAlertDialogDeleteItem(long l){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Caddy");
        alert.setMessage("Souhaitez-vous vraiment effacer cet item ? ");
        alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteProduct(l);
            }
        });
        alert.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alert.create().show();
    };

    //Méthode de basculement à l'activité de la liste de course à partir d'un bouton
    public void nextActivity(View view){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}