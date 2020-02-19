package com.ryblade.quizd.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ryblade.quizd.R;
import com.ryblade.quizd.domain.DomainController;

/**
 * Created by alexmorral on 11/5/15.
 */
public class MatchFragment extends Fragment {

    OnClickListener mCallback;
    String lang1, lang2, word;
    int current, maxCount, score, hits, fails;
    boolean finish, finished;

    // Container Activity must implement this interface
    public interface OnClickListener {
        public void onNextSelected(int current, String wordIntroduced);
    }

    public MatchFragment() {
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView;
        if (!finished) {
            rootView = inflater.inflate(R.layout.match, container, false);

            TextView wordNumLabel = (TextView) rootView.findViewById(R.id.wordNumLabel);
            TextView scoreLabel = (TextView) rootView.findViewById(R.id.scoreLabel);

            TextView lang1Label = (TextView) rootView.findViewById(R.id.lang1Label);
            TextView lang2Label = (TextView) rootView.findViewById(R.id.lang2Label);
            TextView wordLabel = (TextView) rootView.findViewById(R.id.wordLabel);
            TextView hitsLabel = (TextView) rootView.findViewById(R.id.hitsLabel);
            TextView failsLabel = (TextView) rootView.findViewById(R.id.failsLabel);

            wordNumLabel.setText("Word " + String.valueOf(current + 1));
            scoreLabel.setText("Score: " + score);

            lang1Label.setText(lang1);
            lang2Label.setText(lang2);

            hitsLabel.setText("Hits: " + String.valueOf(hits));
            failsLabel.setText("Fails: " + String.valueOf(fails));

            wordLabel.setText(word);

            Button nextBtn = (Button) rootView.findViewById(R.id.nextBtn);

            if (finish) nextBtn.setText("Finish");
            else nextBtn.setText("Next");

            final EditText translatedWord = (EditText) rootView.findViewById(R.id.wordField);

            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newWord = translatedWord.getText().toString();
                    if (!newWord.equals("")) {
                        mCallback.onNextSelected(current, newWord);
                    } else {
                        Toast.makeText(getActivity(), "Please fill in the word field", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            rootView = inflater.inflate(R.layout.finisished_match, container, false);
            TextView scoreLabel = (TextView) rootView.findViewById(R.id.scoreLabel);
            Button exitBtn = (Button) rootView.findViewById(R.id.exitBtn);
            Button saveRankBtn = (Button) rootView.findViewById(R.id.saveBtn);
            final EditText nameField = (EditText) rootView.findViewById(R.id.nameField);
            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
            saveRankBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DomainController domainController = DomainController.getInstance();
                    String nameString = nameField.getText().toString();
                    if (!nameString.equals("")) {
                        domainController.saveRank(nameString, score);
                        getActivity().finish();
                    } else Toast.makeText(getActivity(), "Please introduce a name", Toast.LENGTH_SHORT).show();

                }
            });
            scoreLabel.setText(String.valueOf(score));

        }
        return rootView;
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

    public void setFirstInfo(){
        Bundle b = this.getArguments();
        lang1 = b.getString("lang1");
        lang2 = b.getString("lang2");
        current = b.getInt("currentCount");
        maxCount = b.getInt("maxCount");
        word = b.getString("firstWord");
        score = b.getInt("score");
        finish = b.getBoolean("finish");
        finished = b.getBoolean("finished");
        hits = b.getInt("hits");
        fails = b.getInt("fails");

    }

    public void updateInfo(){
        Bundle b = this.getArguments();
        lang1 = b.getString("lang1");
        lang2 = b.getString("lang2");
        current = b.getInt("currentCount");
        word = b.getString("nextWord");
        finish = b.getBoolean("finish");
        score = b.getInt("score");
        finished = b.getBoolean("finished");
        hits = b.getInt("hits");
        fails = b.getInt("fails");
    }


}
