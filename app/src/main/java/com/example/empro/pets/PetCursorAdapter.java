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

public class PetCursorAdapter extends CursorAdapter {
    public PetCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(list_item,parent,false);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.summary);

        // Find the columns of pet attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(COLUMN_PET_NAME);
        int breedColumnIndex = cursor.getColumnIndex(COLUMN_PET_BREED);

        // Read the pet attributes from the Cursor for the current pet
        String petName = cursor.getString(nameColumnIndex);
        String petBreed = cursor.getString(breedColumnIndex);

        // Update the TextViews with the attributes for the current pet
        nameTextView.setText(petName);
        summaryTextView.setText(petBreed);
    }
    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
}
