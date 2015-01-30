package com.example.babathurpe.tipcalculator;

import java.text.NumberFormat;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


public class MainActivity extends Activity {

    public static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    public static final NumberFormat percentFormat = NumberFormat.getCurrencyInstance();

    private double billAmount = 0.0;
    private int customPercent = 18;
    protected int count = 0;
    private TextView amountDisplayTextView;
    private TextView percentCustomTextView;
    private TextView tip15TextView;
    private TextView total15TextView;
    private TextView tipCustomTextView;
    private TextView totalCustomTextView;
    private TextView afterTax15View;
    private TextView afterTaxCustomView;
    private TextView eachPays15View;
    private TextView eachPaysCustomView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get references to the textviews
        amountDisplayTextView = (TextView) findViewById(R.id.amountEditText);
        percentCustomTextView = (TextView) findViewById(R.id.percentCustomTextView);
        tip15TextView = (TextView) findViewById(R.id.tip15BeforeTaxTextView);
        total15TextView = (TextView) findViewById(R.id.total15TextView);
        tipCustomTextView = (TextView) findViewById(R.id.tipCustomBeforeTaxTextView);
        totalCustomTextView = (TextView) findViewById(R.id.totalCustomTextView);
        //beforeTax15View = (TextView) findViewById(R.id.tip15BeforeTaxTextView);
        //beforeTaxCustomView = (TextView) findViewById(R.id.tipCustomBeforeTaxTextView);
        afterTax15View = (TextView) findViewById(R.id.tip15AfterTaxTextView);
        afterTaxCustomView = (TextView) findViewById(R.id.tipCustomAfterTaxTextView);
        eachPays15View = (TextView) findViewById(R.id.total15EachTextView);
        eachPaysCustomView = (TextView) findViewById(R.id.totalCustomEachTextView);
        //update GUI based on amount of Bill and percentage
        //amountDisplayTextView.setText(currencyFormat.format(billAmount));
        updateStandard(); //udate all 15% textview
        updateCustom(); //udate all custom textview


        EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        //set customTipSeekBar's OnChangeListener
        SeekBar customTipSeekBar = (SeekBar) findViewById(R.id.customTipSeekBar);
        customTipSeekBar.setOnSeekBarChangeListener(customSeekBarListener);

        EditText peopleCount = (EditText) findViewById(R.id.peopleCountEditText);
        peopleCount.addTextChangedListener(peopleCountTextWatcher);
    }

    public void updateStandard() {

        //calculate the 15% tip and total
        double fifteenPercentTip = billAmount * 0.15;
        double fifteenPercentTotal = billAmount + fifteenPercentTip;

        //tip before tax
        double tip15AfterTax = billAmount * 0.15 * 0.13;

        //how much every one at the table pays
        double eachPays = billAmount / count;

        //display the calculated amount to their respective textviews
        tip15TextView.setText(currencyFormat.format(fifteenPercentTip));
        total15TextView.setText(currencyFormat.format(fifteenPercentTotal));
        afterTax15View.setText(currencyFormat.format(tip15AfterTax));
        eachPays15View.setText(currencyFormat.format(eachPays));
    }

    public void updateCustom() {

        //show custom percent in percentCustomView formatted as %
        percentCustomTextView.setText(percentFormat.format(customPercent));

        //calculate the custom tip and total
        double customTip = billAmount * (customPercent/100);
        double customTotal = billAmount + customTip;
        double tipCustomAfterTax = billAmount * (customPercent /100) * 0.13;
        double eachPays = billAmount / count;

        //display the calculated custom tip to their respective textviews
        //formatted as currency
        tipCustomTextView.setText(currencyFormat.format(customTip));
        totalCustomTextView.setText(currencyFormat.format(customTotal));
        afterTaxCustomView.setText(currencyFormat.format(tipCustomAfterTax));
        eachPaysCustomView.setText(currencyFormat.format(eachPays));
    }


    private OnSeekBarChangeListener customSeekBarListener = new OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            //update the custom percent value according to the slider value
            customPercent = progress;
            updateCustom();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    TextWatcher peopleCountTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try{
                //get number of people from the peopleCountEditText
                count = Integer.parseInt(s.toString());
            } catch(NumberFormatException e){
                count = 0;
            }
            updateStandard();
            updateCustom();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };



    private TextWatcher amountEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        //whilst user changes the amount in the text field
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try{
                //convert billAmount from the amountEditText to a double
                billAmount = Double.parseDouble(s.toString());
            } catch(NumberFormatException e){
                billAmount = 0.0;
            }
            //amountDisplayTextView.setText(currencyFormat.format(billAmount));
            updateStandard();
            updateCustom();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

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
