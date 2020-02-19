package com.ryblade.quizd.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ryblade.quizd.R;
import com.ryblade.quizd.domain.DomainController;

import java.util.ArrayList;


/**
 * Created by alexmorral on 4/3/15.
 */
public class AssignTranslationsFragment extends Fragment {

    private DomainController domainController;

    private String language;
    private String word;
    private String language2;
    private String word2;

    private Spinner langSelect;
    private Spinner wordSelect;

    public AssignTranslationsFragment() {
        domainController = DomainController.getInstance();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (menu != null) {
            menu.findItem(R.id.action_new).setVisible(false);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.assign_translation, container, false);

        addItemsToLangSelect(inflater, rootView);
        addItemsToWordSelect(inflater, rootView);

        addListenerOnSpinnerItemSelection(inflater, rootView);
        addListenerOnButton(rootView);

        TextView titleView = (TextView) rootView.findViewById(R.id.titleView);
        titleView.setText("Adding translation for " + word);

        return rootView;
    }

    public void addItemsToLangSelect(LayoutInflater inflater, View view) {

        langSelect = (Spinner) view.findViewById(R.id.langSelect);
        ArrayList<String> langs = new ArrayList<>(domainController.getLanguages());
        langs.remove(language);
        ArrayAdapter<String> langAdapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_spinner_item, langs);
        langAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        langSelect.setAdapter(langAdapter);
    }

    public void addItemsToWordSelect(LayoutInflater inflater, View view) {

        wordSelect = (Spinner) view.findViewById(R.id.wordSelect);
        ArrayList<String> words = domainController.getWords(langSelect.getSelectedItem().toString());
        ArrayAdapter<String> wordAdapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_spinner_item, words);
        wordAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wordSelect.setAdapter(wordAdapter);
    }

    public void addListenerOnSpinnerItemSelection(final LayoutInflater inflater, final View view2) {
        langSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addItemsToWordSelect(inflater, view2);
                language2 = langSelect.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        wordSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                word2 = wordSelect.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // get the selected dropdown list value
    public void addListenerOnButton(View view) {

        langSelect = (Spinner) view.findViewById(R.id.langSelect);
        wordSelect = (Spinner) view.findViewById(R.id.wordSelect);
        Button addTranslationBtn = (Button) view.findViewById(R.id.addTranslationBtn);

        addTranslationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTranslation();
            }
        });
    }

    public void addTranslation() {
        try {
            domainController.setTranslation(language, word, language2, word2);
            //Toast.makeText(this.getActivity(), "Translation assigned!", Toast.LENGTH_SHORT).show();
            getFragmentManager().popBackStackImmediate();
        } catch (Exception e) {
            Toast.makeText(this.getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void updateInfo() {
        Bundle b = this.getArguments();
        language = b.getString("lang");
        word = b.getString("word");
    }
}
