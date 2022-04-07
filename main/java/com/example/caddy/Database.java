package com.example.caddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class Database {

    //Attributs

    public static final String KEY_NAME = "productName";
    public static final String KEY_CATEGORY = "productCategory";
    public static final String KEY_ID = "_id";
    private static final String TAG = "DBCaddy";
    private DatabaseHelper mDbHelper;
    private static SQLiteDatabase mDb;
    private static ArrayList<ArrayList<String>> ArrayListProduits;

    //Création de la liste prédéfinie

    private static final String PRODUITS_CREATE =
            "create table produits ("+KEY_ID+" integer primary key autoincrement,"+KEY_NAME+" text not null," +
                    ""+KEY_CATEGORY+" integer);";

    //Créations de la liste de course
    private static final String LISTEPRODUITS_CREATE =
            "create table listeProduits ("+KEY_ID+" integer primary key autoincrement,"+KEY_NAME+" text not null," +
                    ""+KEY_CATEGORY+" integer);";

    //Informations sur la base de données

    private static final String DATABASE_NAME = "caddy";
    private static final String TABLE_PRODUCTS = "produits"; //Liste prédéfinie
    private static final String TABLE_LISTPRODUCTS = "listeProduits"; //Liste de course
    private static final int DATABASE_VERSION = 2;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        //Méthode onCreate de la base de données
        @Override
        public void onCreate(SQLiteDatabase db) {

            //Requête SQL de créations de table
            db.execSQL(PRODUITS_CREATE);
            db.execSQL(LISTEPRODUITS_CREATE);

            // Fruits

            ArrayList<String> ArrayListFruits = new ArrayList<String>();

            String[] listFruits = { "Orange", "Pomme", "Banane", "Kiwi", "Pêche", "Abricot", "Fraise", "Raisin" };

            for (String fruit : listFruits) {
                ArrayListFruits.add(fruit);
            }

            // Legumes

            ArrayList<String> ArrayListLegumes = new ArrayList<String>();

            String[] listLegumes = { "Tomate", "Cornichon", "Oignon", "Ail", "Salade", "Carotte",
                    "Pomme de terre" };

            for (String legume : listLegumes) {
                ArrayListLegumes.add(legume);
            }

            // Epices

            ArrayList<String> ArrayListEpices = new ArrayList<String>();

            String[] listEpices = new String[] { "Origan", "Poivre", "Sel", "Paprika", "Curry", "Cannelle",
                    "Herbes de provence",
                    "Persil", "Thym" };

            for (String epice : listEpices) {
                ArrayListEpices.add(epice);
            }

            // Feculents

            ArrayList<String> ArrayListFeculents = new ArrayList<String>();

            String[] listFeculents = new String[] { "Riz", "Pâtes", "Pain blanc", "Pain de mie", "Pain complet",
                    "Céréales" };

            for (String feculent : listFeculents) {
                ArrayListFeculents.add(feculent);
            }

            // Viande Poisson

            ArrayList<String> ArrayListViandePoisson = new ArrayList<String>();

            String[] listViandePoisson = new String[] { "Boeuf", "Steak Hachés", "Jambon", "Poulet", "Saumon", "Oeuf",
                    "Boîte de thon" };

            for (String viande : listViandePoisson) {
                ArrayListViandePoisson.add(viande);
            }

            // Produits Laitiers

            ArrayList<String> ArrayListProduitsLaitiers = new ArrayList<String>();

            String[] listProduitsLaitiers = new String[] { "Fromage", "Lait", "Glace", "Yaourt" };

            for (String laitier : listProduitsLaitiers) {
                ArrayListProduitsLaitiers.add(laitier);
            }

            // Plats préparés

            ArrayList<String> ArrayListPlatsPrepares = new ArrayList<String>();

            String[] listPlatsPrepares = new String[] { "Sushi", "Couscous", "Cassoulet", "Choucroute", "Plateau Raclette",
                    "Raviolis" };

            for (String plat : listPlatsPrepares) {
                ArrayListPlatsPrepares.add(plat);
            }

            // Desserts

            ArrayList<String> ArrayListDesserts = new ArrayList<String>();

            String[] listDesserts = new String[] { "Sorbet", "Mousses dessert", "Gâteau", "Pâtisserie", "Strudel" };

            for (String dessert : listDesserts) {
                ArrayListDesserts.add(dessert);
            }

            // Boissons

            ArrayList<String> ArrayListBoissons = new ArrayList<String>();

            String[] listBoissons = new String[] { "Eau minérale", "Jus d''orange", "Jus de raisin", "Jus de pomme",
                    "Boisson gazeuse" };

            for (String boisson : listBoissons) {
                ArrayListBoissons.add(boisson);
            }

            // Alcools

            ArrayList<String> ArrayListAlcools = new ArrayList<String>();

            String[] listAlcools = new String[] { "Vin rouge", "Vin blanc", "Porto", "Champagne", "Bière", "Rhum" };

            for (String alcool : listAlcools) {
                ArrayListAlcools.add(alcool);
            }

            // Produits Ménagers

            ArrayList<String> ArrayListProduitsMenagers = new ArrayList<String>();

            String[] listProduitsMenagers = new String[] { "Torchon", "Sacs poubelle", "Sacs congélation", "Lessive",
                    "Produit Lave Vaiselle", "Sacs aspirateur" };

            for (String menager : listProduitsMenagers) {
                ArrayListProduitsMenagers.add(menager);
            }

            // Produits

            ArrayListProduits = new ArrayList<ArrayList<String>>();

            ArrayListProduits.add(ArrayListFruits);
            ArrayListProduits.add(ArrayListLegumes);
            ArrayListProduits.add(ArrayListEpices);
            ArrayListProduits.add(ArrayListFeculents);
            ArrayListProduits.add(ArrayListViandePoisson);
            ArrayListProduits.add(ArrayListProduitsLaitiers);
            ArrayListProduits.add(ArrayListPlatsPrepares);
            ArrayListProduits.add(ArrayListDesserts);
            ArrayListProduits.add(ArrayListBoissons);
            ArrayListProduits.add(ArrayListAlcools);
            ArrayListProduits.add(ArrayListProduitsMenagers);

            int nbCategory = 0; //Numéro de la catégorie

            for (ArrayList<String> row : ArrayListProduits) {
                nbCategory++;
                for (String column : row) {
                    //Requête SQL d'insertion de données dans la liste prédéfinie
                    db.execSQL("insert into "+TABLE_PRODUCTS+" ("+KEY_NAME+","+KEY_CATEGORY+") values ('"+column+"','"+nbCategory+"');");
                }
            }
        }

        //Méthode de mis à jour de la base de données
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS produits");
            onCreate(db);
        }
    }

    //Constructeur de la base de données
    public Database(Context ctx) {
        this.mCtx = ctx;
    }

    //Méthode d'ouverte de la base de données
    public Database open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    //Méthode de fermeture de la base de données
    public void close() {
        mDbHelper.close();
    }

    //Méthode d'ajout de produit dans la liste prédéfinie
    public long createProduct(String productName) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, productName);

        return mDb.insert(TABLE_PRODUCTS, null, initialValues);
    }

    //Méthode de supression de produit dans la liste prédéfinie
    public boolean deleteProduct(long productId) {

        return mDb.delete(TABLE_PRODUCTS, KEY_ID + "=" + productId, null) > 0;
    }

    //Méthode de supression de tous les produits de la liste prédéfinie
    public boolean deleteAll(){

        return mDb.delete(TABLE_PRODUCTS, KEY_ID + ">= 0", null) > 0;
    }

    //Méthode de sélection de tous les produits de la liste prédéfinie
    public Cursor fetchAllProducts() {

        return mDb.query(TABLE_PRODUCTS, new String[] {KEY_ID, KEY_NAME, KEY_CATEGORY}, null, null, null, null, null);
    }

    //Méthode de sélection de tous les produits de la liste prédéfinie par catégorie
    public Cursor fetchAllProductsCategory() {

        return mDb.query(TABLE_PRODUCTS, new String[] {KEY_ID, KEY_NAME, KEY_CATEGORY}, null, null, null, null, KEY_CATEGORY);
    }

    //Méthode de sélection de tous les produits de la liste de course par ordre alphabétique
    public Cursor fetchAllListProducts() {

        return mDb.query(TABLE_LISTPRODUCTS, new String[] {KEY_ID, KEY_NAME, KEY_CATEGORY}, null, null, null, null, KEY_NAME);
    }

    //Méthode de sélection de tous les produits de la liste de course par catégorie
    public Cursor fetchAllListProductsCategory() {

        return mDb.query(TABLE_LISTPRODUCTS, new String[] {KEY_ID, KEY_NAME, KEY_CATEGORY}, null, null, null, null, KEY_CATEGORY);
    }

    //Méthode d'insertion dans la liste de course
    public long insertListProduct(String productName, int productCategory) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, productName);
        initialValues.put(KEY_CATEGORY, productCategory);

        return mDb.insert(TABLE_LISTPRODUCTS, null, initialValues);
    }

    //Méthode de supression de tous les produits dans la liste de course
    public boolean deleteAllList(){
        return mDb.delete(TABLE_LISTPRODUCTS, KEY_ID + ">= 0", null) > 0;
    }

    //Méthode de récupération du nom de produit dans la liste prédéfinie
    public String searchProductName(int i){
        String result = "";
        int position = i + 1;
        Cursor mCursor = mDb.query(TABLE_PRODUCTS, new String[] {KEY_NAME}, KEY_ID+"="+position, null, null, null, null);

        if (mCursor.moveToFirst()){
            result = mCursor.getString(mCursor.getColumnIndex(KEY_NAME));
        }
        mCursor.close();
        return result;
    }

    //Méthode de récupération de la catégorie de produit dans la liste prédéfinie
    public int searchProductCategory(int i){
        int result = 0;
        int position = i + 1;
        Cursor mCursor = mDb.query(TABLE_PRODUCTS, new String[] {KEY_CATEGORY}, KEY_ID+"="+position, null, null, null, null);

        if (mCursor.moveToFirst()){
            result = mCursor.getInt(mCursor.getColumnIndex(KEY_CATEGORY));
        }
        mCursor.close();
        return result;
    }


}
