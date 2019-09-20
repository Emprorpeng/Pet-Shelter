package com.example.empro.pets;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.empro.pets.data.PetContract;
import com.example.empro.pets.data.PetDbHelper;

import static com.example.empro.pets.data.PetContract.*;
import static com.example.empro.pets.data.PetContract.PetEntry.CONTENT_URI;
import static com.example.empro.pets.data.PetContract.PetEntry.TABLE_NAME;
import static com.example.empro.pets.data.PetContract.PetEntry._ID;

public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public final int PET_LOADER = 0;
    public static Uri new_uri = null;
    PetCursorAdapter mCursorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        mCursorAdapter = new PetCursorAdapter(this,null);
        ListView petListView =findViewById(R.id.listview);
        View empty = findViewById(R.id.empty_view);
        petListView.setEmptyView(empty);
        petListView.setAdapter(mCursorAdapter);
        getLoaderManager().initLoader(PET_LOADER,null,this);
        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new_uri = ContentUris.withAppendedId(CONTENT_URI,id);
                Intent intent = new Intent(CatalogActivity.this,EditorActivity.class).setData(new_uri);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    private void insertPets(){
        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_BREED,"Tabby");
        values.put(PetEntry.COLUMN_PET_NAME,"Garfield");
        values.put(PetEntry.COLUMN_PET_GENDER, PetEntry.GENDER_MALE);
        values.put(PetEntry.COLUMN_PET_WEIGHT,7);
        Uri uri = getContentResolver().insert(CONTENT_URI,values);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertPets();
                return true;
            case R.id.action_delete_all_entries:
                deleteAllPets();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                PetEntry._ID,
                PetEntry.COLUMN_PET_NAME,
                PetEntry.COLUMN_PET_BREED,
        };
        switch (id){
            case PET_LOADER: return new CursorLoader(this,CONTENT_URI,projection,null,null,null);
            default: return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    mCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    mCursorAdapter.swapCursor(null);
    }
    private void deleteAllPets() {
        int rowsDeleted = getContentResolver().delete(PetEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from pet database");
    }
}
