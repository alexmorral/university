package com.ryblade.quizd.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.ryblade.quizd.R;
import com.ryblade.quizd.domain.DomainController;

import java.util.ArrayList;

/**
 * Created by alexmorral on 11/5/15.
 */
public class NewMatchFragment extends Fragment {

    private DomainController domainController;
    OnClickListener mCallback;
    String lang1, lang2, level;
    private ArrayAdapter<String> lang1Adapter, lang2Adapter;

    // Container Activity must implement this interface
    public interface OnClickListener {
        public void onPlaySelected(String lang1, String lang2, String level);
    }

    public NewMatchFragment() {
        domainController = DomainController.getInstance();
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.start_match, container, false);
        RadioButton easyBtn = (RadioButton) rootView.findViewById(R.id.easyBtn);
        RadioButton mediumBtn = (RadioButton) rootView.findViewById(R.id.mediumBtn);
        RadioButton hardBtn = (RadioButton) rootView.findViewById(R.id.hardBtn);
        final Spinner lang1Select = (Spinner) rootView.findViewById(R.id.lang1Spin);
        final Spinner lang2Select = (Spinner) rootView.findViewById(R.id.lang2Spin);
        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lang1Select.setSelection(0);
                lang1 = lang1Select.getItemAtPosition(0).toString();
                ArrayList<String> langs = getLanguages(lang1Select.getSelectedItem().toString(), rootView);
                lang2Adapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_spinner_item, langs);
                lang2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                lang2Select.setAdapter(lang2Adapter);
            }
        });
        mediumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lang1Select.setSelection(0);
                lang1 = lang1Select.getItemAtPosition(0).toString();
                ArrayList<String> langs = getLanguages(lang1Select.getSelectedItem().toString(), rootView);
                lang2Adapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_spinner_item, langs);
                lang2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                lang2Select.setAdapter(lang2Adapter);
            }
        });
        hardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lang1Select.setSelection(0);
                lang1 = lang1Select.getItemAtPosition(0).toString();
                ArrayList<String> langs = getLanguages(lang1Select.getSelectedItem().toString(), rootView);
                lang2Adapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_spinner_item, langs);
                lang2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                lang2Select.setAdapter(lang2Adapter);
            }
        });
        easyBtn.setChecked(true);
        addItemsToLang1Select(inflater, rootView);
        addItemsToLang2Select(inflater, rootView);


        Button playBtn = (Button) rootView.findViewById(R.id.playBtn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RadioButton easyBtn = (RadioButton) rootView.findViewById(R.id.easyBtn);
                RadioButton mediumBtn = (RadioButton) rootView.findViewById(R.id.mediumBtn);
                if (easyBtn.isChecked()) level = "easy";
                else if (mediumBtn.isChecked()) level = "medium";
                else level = "hard";

                if (lang2Adapter.isEmpty() || lang1Adapter.isEmpty()) {
                    Toast.makeText(getActivity(), "You must select two languages", Toast.LENGTH_SHORT).show();
                } else {
                    mCallback.onPlaySelected(lang1, lang2, level);
                    Log.v("TAG", "Starting match with: Lang1:" + lang1 + " Lang2:" + lang2 + " Level:" + level);
                }

            }
        });

        lang1Select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lang1 = lang1Select.getItemAtPosition(position).toString();
                ArrayList<String> langs = getLanguages(lang1Select.getSelectedItem().toString(), rootView);
                lang2Adapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_spinner_item, langs);
                lang2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                lang2Select.setAdapter(lang2Adapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        lang2Select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lang2 = lang2Select.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        return rootView;
    }

    public ArrayList<String> getLanguages(String lang, View view) {
        if (lang.equals("null")){
            ArrayList<String> langs = new ArrayList<>(domainController.getLanguages());
            return langs;
        }else {
            int num;
            RadioButton easyBtn = (RadioButton) view.findViewById(R.id.easyBtn);
            RadioButton mediumBtn = (RadioButton) view.findViewById(R.id.mediumBtn);
            if (easyBtn.isChecked()) num = 5;
            else if (mediumBtn.isChecked()) num = 10;
            else num = 15;
            ArrayList<String> langs = new ArrayList<>(domainController.getPlayingLanguages(lang, num));
            return langs;
        }
    }

    public void addItemsToLang1Select(LayoutInflater inflater, View view) {

        Spinner lang1Select = (Spinner) view.findViewById(R.id.lang1Spin);
        ArrayList<String> langs = getLanguages("null", view);
        lang1Adapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_spinner_item, langs);
        lang1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lang1Select.setAdapter(lang1Adapter);
    }

    public void addItemsToLang2Select(LayoutInflater inflater, View view) {

        Spinner lang1Select = (Spinner) view.findViewById(R.id.lang1Spin);
        Spinner lang2Select = (Spinner) view.findViewById(R.id.lang2Spin);
        ArrayList<String> langs = getLanguages(lang1Select.getSelectedItem().toString(), view);
        lang2Adapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_spinner_item, langs);

        lang2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lang2Select.setAdapter(lang2Adapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnClickListener");
        }
    }


}
