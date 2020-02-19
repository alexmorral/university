package com.ryblade.quizd;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ryblade.quizd.domain.DomainController;

import java.util.ArrayList;

/**
 * Created by alexmorral on 12/5/15.
 */
public class RankActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rank_layout);

        initializeComponents();
    }

    public void initializeComponents() {
        Button exitBtn = (Button) findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DomainController domainController = DomainController.getInstance();
        domainController.startDatabase(this);
        ArrayList<String> ranks = domainController.getAllRanks();
        TextView name1Label = (TextView) findViewById(R.id.name1Label);
        TextView name2Label = (TextView) findViewById(R.id.name2Label);
        TextView name3Label = (TextView) findViewById(R.id.name3Label);
        TextView name4Label = (TextView) findViewById(R.id.name4Label);
        TextView name5Label = (TextView) findViewById(R.id.name5Label);
        ArrayList<TextView> textViews = new ArrayList<>();
        textViews.add(name1Label);
        textViews.add(name2Label);
        textViews.add(name3Label);
        textViews.add(name4Label);
        textViews.add(name5Label);

        int i = 0;
        while (i < ranks.size()) {
            TextView tView = textViews.get(i);
            tView.setText(ranks.get(i));
            ++i;
        }
        while (i < 5) {
            TextView tView = textViews.get(i);
            tView.setText("... - ...");
            ++i;
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
