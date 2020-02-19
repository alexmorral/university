package com.ryblade.quizd;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ryblade.quizd.domain.DomainController;


public class MainActivity extends ActionBarActivity {
    private DomainController domainController;


    public void changeView(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.playBtn:
                intent = new Intent(this, PlayActivity.class);
                startActivity(intent);
                break;
            case R.id.modBtn:
                intent = new Intent(this, ModifyActivity.class);
                startActivity(intent);
                break;
            case R.id.rankBtn:
                intent = new Intent(this, RankActivity.class);
                startActivity(intent);
                break;
            case R.id.helpBtn:
                intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
