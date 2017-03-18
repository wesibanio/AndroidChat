package com.example.nisan.toychat;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView outputText;
    private EditText inputText;
    private ScrollView outputScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        outputText = (TextView) findViewById(R.id.outputText);
        inputText = (EditText) findViewById(R.id.inputText);
        outputScroll = (ScrollView)findViewById(R.id.outputScroll);
        Button sendButton = (Button) findViewById(R.id.sendButton);

        if (savedInstanceState != null) {
            this.outputText.setText(savedInstanceState.getString(
                    String.valueOf(R.id.outputText)
            ));
            this.outputScroll.scrollTo(0, this.outputScroll.getBottom());
        }
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMessage();
            }
        });

        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener(){

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId != EditorInfo.IME_ACTION_SEND) {
                    return false;
                }
                addMessage();
                return true;
            }
        });

    }

    private void addMessage() {

        if (getString(R.string.initMsg).equals(this.outputText.getText().toString())) {
            this.outputText.setText("");
        }
        this.outputText.append(this.inputText.getText().toString() + "\n");

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);

        this.outputScroll.smoothScrollTo(0, this.outputScroll.getBottom());
        this.inputText.setText("");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(String.valueOf(R.id.outputText), this.outputText.getText().toString());

    }
}
