package com.ryblade.quizd.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.ryblade.quizd.R;
import com.ryblade.quizd.domain.DomainController;

import java.util.ArrayList;

/**
 * Created by alexmorral on 4/3/15.
 */
public class DetailWordFragment extends Fragment {

    private DomainController domainController;
    private ArrayList<String> translations;
    OnClickListener mCallback;

    private String language;
    private String word;
    private TextView nTranslationsView;

    private int pos = 0;

    // Container Activity must implement this interface
    public interface OnClickListener {
        public void onAddTranslationSelected(String lang, String word);
    }

    public DetailWordFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void loadTranslations() {
        domainController = DomainController.getInstance();
        translations = domainController.getTranslations(language, word);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        translations = new ArrayList<>();
        loadTranslations();

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_list_item_1, translations);
        View rootView = inflater.inflate(R.layout.detail_word_fragment, container, false);
        //final ListView translationList = (ListView) rootView.findViewById(R.id.translationsListView);
        final SwipeMenuListView translationList = (SwipeMenuListView) rootView.findViewById(R.id.translationsListView);
        translationList.setAdapter(adapter);

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Are you sure you want to delete this translation?");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String row = translationList.getItemAtPosition(pos).toString();
                        String[] parts = row.split(" "); //parts[0] = word
                        String[] parts2 = parts[1].split("[()]"); //parts2[1] = lang
                        adapter.remove(translationList.getItemAtPosition(pos).toString());
                        domainController.removeTranslation(language, word, parts2[1], parts[0]);
                        nTranslationsView.setText(String.valueOf(translations.size()) + " translations");
                        dialog.cancel();
                    }
                });
        builder1.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        final AlertDialog alert11 = builder1.create();


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        translationList.setMenuCreator(creator);

        translationList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        pos = position;
                        alert11.show();
                        break;
                }
                adapter.notifyDataSetChanged();

                return false;
            }
        });

        nTranslationsView = (TextView) rootView.findViewById(R.id.nTranslationsTextView);

        nTranslationsView.setText(String.valueOf(translations.size()) + " translations");

        TextView wordName = (TextView) rootView.findViewById(R.id.wordTextView);

        wordName.setText(word);

        Button addTransBtn = (Button) rootView.findViewById(R.id.addTranslationBtn);

        addTransBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onAddTranslationSelected(language, word);
                nTranslationsView.setText(String.valueOf(translations.size()));
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

    public void updateInfo() {
        Bundle b = this.getArguments();
        language = b.getString("lang");
        word = b.getString("word");
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
