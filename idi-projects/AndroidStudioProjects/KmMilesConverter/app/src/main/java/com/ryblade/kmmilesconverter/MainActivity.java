package com.ryblade.kmmilesconverter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {


    public void conversion(View view) {
        switch(view.getId()) {
            case R.id.convertBtn :
                EditText text = (EditText) findViewById(R.id.textField);
                TextView result = (TextView) findViewById(R.id.resultTextView);
                RadioButton buttonKm = (RadioButton) findViewById(R.id.kmRadio);
                float inputValue = Float.parseFloat(text.getText().toString());
                double resultValue;
                if (buttonKm.isChecked()) resultValue = inputValue / 1.609344;
                else resultValue = inputValue * 1.609344;
                result.setText(String.format("%.03f", resultValue));
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
        return true;
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
