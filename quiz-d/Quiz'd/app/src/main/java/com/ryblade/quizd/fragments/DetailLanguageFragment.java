package com.ryblade.quizd.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
public class DetailLanguageFragment extends Fragment {

    private String languageView;
    private ArrayList<String> wordArray;
    private DomainController domainController;
    private ArrayAdapter<String> adapter;
    private int nWords = 0, pos = 0;
    private TextView nWordsTextView;
    OnClickListener mCallback;


    // Container Activity must implement this interface
    public interface OnClickListener {
        public void onWordSelected(String lang, String word);

    }

    public DetailLanguageFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void loadWords() {
        if (domainController==null) domainController = DomainController.getInstance();
        wordArray = domainController.getWords(languageView);
        nWords = wordArray.size();
        /*if(wordArray.isEmpty()) {
            wordArray.add("There are no words");
            nWords = 0;
        }*/
        nWordsTextView.setText(String.valueOf(nWords)+ " Words");

    }

    public void loadView(final View view, LayoutInflater inflater) {
        TextView langTextView = (TextView) view.findViewById(R.id.langTextView);

        langTextView.setText(languageView);
        nWordsTextView = (TextView) view.findViewById(R.id.nWordsTextView);
        String itemSel = "You selected " + languageView;
        //Toast.makeText(getActivity(), itemSel, Toast.LENGTH_SHORT).show();

        loadWords();

        adapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_list_item_1, wordArray);


        final SwipeMenuListView wordListView = (SwipeMenuListView) view.findViewById(R.id.wordsListView);
        wordListView.setAdapter(adapter);


        Button addWordBtn = (Button) view.findViewById(R.id.addWordBtn);
        addWordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWord(view);
            }
        });


        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Are you sure you want to delete this word?");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteWord(wordListView.getItemAtPosition(pos).toString());
                        adapter.notifyDataSetChanged();
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

        wordListView.setMenuCreator(creator);

        wordListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        pos = position;
                        alert11.show();
                        break;
                }

                return false;
            }
        });
        wordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.onWordSelected(languageView, wordListView.getItemAtPosition(position).toString());
            }
        });

    }


    public void addWord(View view) {
        EditText wordField = (EditText) view.findViewById(R.id.newWordText);
        try {
            String newWord = wordField.getText().toString();
            if (!newWord.equals("")) {
                domainController.addWordToLanguage(languageView, wordField.getText().toString());
                //Toast.makeText(getActivity(), "Word added succesfully!", Toast.LENGTH_SHORT).show();
                if (wordArray.contains("There are no words"))
                    wordArray.remove("There are no words");
                nWords = wordArray.size();
                adapter.notifyDataSetChanged();
                nWordsTextView.setText(String.valueOf(nWords) + " Words");
                wordField.setText("");
            } else Toast.makeText(getActivity(), "Please fill in the word field", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void deleteWord(String word) {
        domainController.deleteWordFromLanguage(languageView, word);
        --nWords;
        //Toast.makeText(getActivity(), "You deleted " + word, Toast.LENGTH_SHORT).show();
        nWordsTextView.setText(String.valueOf(nWords)+ " Words");
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_language_fragment, container, false);

        loadView(rootView, inflater);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (menu != null) {
            menu.findItem(R.id.action_new).setVisible(false);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void updateDetailView(){
        Bundle b = this.getArguments();
        languageView = b.getString("lang");
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


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

}
