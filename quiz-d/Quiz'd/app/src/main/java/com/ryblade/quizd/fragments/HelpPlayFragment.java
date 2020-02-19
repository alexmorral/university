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
public class HelpPlayFragment extends Fragment implements Html.ImageGetter{

    public HelpPlayFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.help_play_layout, container, false);

        TextView firstLabel = (TextView) rootView.findViewById(R.id.firstLabel);
        TextView secondLabel = (TextView) rootView.findViewById(R.id.secondLabel);
        TextView thirdLabel = (TextView) rootView.findViewById(R.id.thirdLabel);

        firstLabel.setText(Html.fromHtml("On the <b>NEW MATCH</b> screen, you have to select the level you want to play. You also have to select the languages you want to play and click on <img src='play.png'/>.", this, null));
        secondLabel.setText(Html.fromHtml("On the <b>MATCH</b> screen, you have to fill the blank text field with the word you guess and click on <img src='next.png' />.", this, null));
        thirdLabel.setText(Html.fromHtml("When you have finished the match, you will see your score. You can either save your score by filling the blank text field with your name and clicking on <img src='save.png'/> or exiting the screen by clicking on <img src='exit.png'/>.", this, null));
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public Drawable getDrawable(String arg0) {
        int id = 0;

        if(arg0.equals("play.png")){
            id = R.drawable.play;
        }

        if(arg0.equals("next.png")){
            id = R.drawable.next;
        }

        if(arg0.equals("save.png")){
            id = R.drawable.save;
        }

        if(arg0.equals("exit.png")){
            id = R.drawable.exit;
        }


        LevelListDrawable d = new LevelListDrawable();
        Drawable empty = getResources().getDrawable(id);
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

        return d;
    }
}
