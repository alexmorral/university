package com.ryblade.quizd.fragments;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ryblade.quizd.R;

/**
 * Created by alexmorral on 13/5/15.
 */
public class HelpModifyFragment extends Fragment implements Html.ImageGetter{

    public HelpModifyFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.help_modify_layout, container, false);

        TextView firstLabel = (TextView) rootView.findViewById(R.id.firstLabel);
        TextView secondLabel = (TextView) rootView.findViewById(R.id.secondLabel);
        TextView thirdLabel = (TextView) rootView.findViewById(R.id.thirdLabel);
        TextView fourthLabel = (TextView) rootView.findViewById(R.id.fourthLabel);
        TextView fifthLabel = (TextView) rootView.findViewById(R.id.fifthLabel);

        firstLabel.setText(Html.fromHtml("You can add <b>LANGUAGES</b> to the application by clicking on <img src='addbutton.png'/> at the top bar", this, null));
        secondLabel.setText(Html.fromHtml("You can see the words of a <b>LANGUAGE</b> by clicking on any language.", this, null));
        thirdLabel.setText(Html.fromHtml("You can add a <b>WORD</b> to a language by filling the text field and clicking <img src='addword.png'/>", this, null));
        fourthLabel.setText(Html.fromHtml("You can see the <b>TRANSLATIONS</b> of a word by clicking on that word.", this, null));
        fifthLabel.setText(Html.fromHtml("You can add a translation by clicking on <img src='addtranslation.png'/> and selecting word you want to associate the word with.", this, null));

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public Drawable getDrawable(String arg0) {
        int id = 0;

        if(arg0.equals("addbutton.png")){
            id = R.drawable.addbutton;
        }

        if(arg0.equals("addtranslation.png")){
            id = R.drawable.addtranslation;
        }

        if(arg0.equals("addword.png")){
            id = R.drawable.addword;
        }



        LevelListDrawable d = new LevelListDrawable();
        Drawable empty = getResources().getDrawable(id);
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

        return d;
    }
}
