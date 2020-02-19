package com.ryblade.quizd;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ryblade.quizd.domain.DomainController;
import com.ryblade.quizd.fragments.LanguageListFragment;
import com.ryblade.quizd.fragments.MatchFragment;
import com.ryblade.quizd.fragments.NewMatchFragment;

import java.util.ArrayList;


public class PlayActivity extends ActionBarActivity implements NewMatchFragment.OnClickListener, MatchFragment.OnClickListener{

    private DomainController domainController;
    private String language1, language2;
    private int maxCount, currentCount, score = 0, hits, fails;
    private ArrayList<String> playableWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        domainController = DomainController.getInstance();
        domainController.startDatabase(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new NewMatchFragment())
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onPlaySelected(String lang1, String lang2, String level) {
        MatchFragment newFragment = new MatchFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putString("lang1", lang1);
        args.putString("lang2", lang2);
        args.putInt("currentCount", 0);
        score = 0;
        args.putInt("score", score);
        args.putBoolean("finish", false);
        args.putBoolean("finished", false);
        hits = fails = 0;
        args.putInt("hits", hits);
        args.putInt("fails", fails);
        int max = 5;
        switch (level) {
            case "easy":
                max = 5;
                break;
            case "medium":
                max = 10;
                break;
            case "hard":
                max = 15;
                break;
        }
        args.putInt("maxCount", max);
        language1 = lang1;
        language2 = lang2;
        maxCount = max;
        currentCount = 0;

        domainController.generatePlayableWords(lang1, lang2, max);
        playableWords = domainController.getPlayableWords();
        args.putString("firstWord", playableWords.get(0));

        newFragment.setArguments(args);
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

        newFragment.setFirstInfo();
    }

    @Override
    public void onNextSelected(int current, String wordIntroduced) {



        MatchFragment newFragment = new MatchFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        boolean correct = domainController.checkWordCorrect(language1, playableWords.get(current), language2, wordIntroduced);

        if (correct) {
            score += 10;
            hits++;
        }
        else {
            score -= 5;
            fails++;
        }


        currentCount = current+1;
        Bundle args = new Bundle();
        args.putInt("score", score);
        args.putInt("currentCount", currentCount);
        args.putString("lang1", language1);
        args.putString("lang2", language2);
        args.putInt("hits", hits);
        args.putInt("fails", fails);
        if (current == maxCount-1) {
            args.putBoolean("finished", true);
            Log.v("FINISHING", "You have finished the match");
        } else args.putBoolean("finished", false);
        if (current == maxCount-2) args.putBoolean("finish", true);

        playableWords = domainController.getPlayableWords();
        args.putString("nextWord", playableWords.get(currentCount));

        newFragment.setArguments(args);
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

        newFragment.updateInfo();


    }
}
