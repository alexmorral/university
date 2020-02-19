package com.ryblade.quizd.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ryblade.quizd.R;

/**
 * Created by alexmorral on 13/5/15.
 */
public class HelpFragment extends Fragment {
    OnClickListener mCallback;

    // Container Activity must implement this interface
    public interface OnClickListener {
        public void onPlaySelected();
        public void onModifySelected();

    }
    public HelpFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.help_layout_general, container, false);

        Button playBtn = (Button) rootView.findViewById(R.id.playInstrBtn);
        Button modBtn = (Button) rootView.findViewById(R.id.modIntrBtn);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onPlaySelected();
            }
        });
        modBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onModifySelected();
            }
        });


        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

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
}
