package com.bourymbodj.autocomplete2;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;

/**
 * Created by bourymbodj on 16-07-01.
 */
public class CustomAutoCompleteTextChangedListener implements TextWatcher {

    public static final String TAG = "CustomAutoCompleteTextChangedListener.java";
    Context context;

    public CustomAutoCompleteTextChangedListener(Context context){
        this.context = context;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence userInput, int start, int before, int count) {

        Log.e(TAG, "User input " + userInput);
        MainActivity mainActivity= ((MainActivity)context);

        // query the databae based on the user input
        mainActivity.item= mainActivity.getItemsFromDb(userInput.toString());
        // update the arrayAdapter
        mainActivity.myAdapter.notifyDataSetChanged();
        mainActivity.myAdapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_dropdown_item_1line, mainActivity.item);
        mainActivity.myAutoComplete.setAdapter(mainActivity.myAdapter);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
