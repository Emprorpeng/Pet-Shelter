package com.example.empro.pets;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.empro.pets.data.PetContract;

import static com.example.empro.pets.R.layout.list_item;
import static com.example.empro.pets.data.PetContract.PetEntry.COLUMN_PET_BREED;
import static com.example.empro.pets.data.PetContract.PetEntry.COLUMN_PET_NAME;
import static com.example.empro.pets.data.PetContract.PetEntry.GENDER_FEMALE;
import static com.example.empro.pets.data.PetContract.PetEntry.GENDER_MALE;
import static com.example.empro.pets.data.PetContract.PetEntry.GENDER_UNKNOWN;
//This method binds the pet data (in the current row pointed to by cursor) to the given list item layout.
public class PetCursorAdapter extends CursorAdapter {
    public PetCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(list_item,parent,false);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.summary);

        int nameColumnIndex = cursor.getColumnIndex(COLUMN_PET_NAME);
        int breedColumnIndex = cursor.getColumnIndex(COLUMN_PET_BREED);

        String petName = cursor.getString(nameColumnIndex);
        String petBreed = cursor.getString(breedColumnIndex);

        nameTextView.setText(petName);
        summaryTextView.setText(petBreed);
    }

}
