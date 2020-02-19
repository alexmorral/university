package com.ryblade.quizd;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ryblade.quizd.domain.DomainController;
import com.ryblade.quizd.fragments.AddLanguageFragment;
import com.ryblade.quizd.fragments.AssignTranslationsFragment;
import com.ryblade.quizd.fragments.DetailLanguageFragment;
import com.ryblade.quizd.fragments.DetailWordFragment;
import com.ryblade.quizd.fragments.LanguageListFragment;


public class ModifyActivity extends ActionBarActivity implements LanguageListFragment.OnClickListener, DetailLanguageFragment.OnClickListener,
DetailWordFragment.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        DomainController domainController = DomainController.getInstance();
        domainController.startDatabase(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new LanguageListFragment())
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_modify, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_new:
                onCreateLangSelected();
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);

    }


    public void onLanguageSelected(String lang) {
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article
        // Create fragment and give it an argument for the selected article
        DetailLanguageFragment newFragment = new DetailLanguageFragment();
        Bundle args = new Bundle();
        args.putString("lang", lang);

        newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

        newFragment.updateDetailView();
    }

    private void onCreateLangSelected() {
        AddLanguageFragment newFragment = new AddLanguageFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }


    @Override
    public void onWordSelected(String lang, String word) {
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article
        // Create fragment and give it an argument for the selected article
        DetailWordFragment newFragment = new DetailWordFragment();
        Bundle args = new Bundle();
        args.putString("lang", lang);
        args.putString("word", word);

        newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
        newFragment.updateInfo();
    }

    @Override
    public void onAddTranslationSelected(String lang, String word) {
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article
        // Create fragment and give it an argument for the selected article
        AssignTranslationsFragment newFragment = new AssignTranslationsFragment();
        Bundle args = new Bundle();
        args.putString("lang", lang);
        args.putString("word", word);

        newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
        newFragment.updateInfo();
    }


}
