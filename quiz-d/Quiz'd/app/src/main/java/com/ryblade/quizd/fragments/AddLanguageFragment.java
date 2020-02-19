package com.ryblade.quizd.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ryblade.quizd.R;
import com.ryblade.quizd.domain.DomainController;

/**
 * Created by alexmorral on 26/3/15.
 */
public class AddLanguageFragment extends Fragment{


    private DomainController domainController = null;
    private View rootView;

    public AddLanguageFragment() {
        if (domainController==null) domainController = DomainController.getInstance();
        setHasOptionsMenu(true);
    }


    public void createLanguage() {
        EditText langName = (EditText) rootView.findViewById(R.id.langField);

        try {
            String newLang = langName.getText().toString();
            if (!newLang.equals("")) {
                domainController.createLanguage(langName.getText().toString());
                //Toast.makeText(this.getActivity(), "Language Created!", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(this.getActivity(), "Please fill in the language field", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this.getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        getFragmentManager().popBackStackImmediate();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.add_lang_fragment, container, false);
        Button createBtn = (Button) rootView.findViewById(R.id.createBtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createLanguage();
            }
        });
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (menu != null) {
            menu.findItem(R.id.action_new).setVisible(false);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }
}
