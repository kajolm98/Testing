package com.mtem.asset_management_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPassword extends BaseActivity {
    TextView loginTextView;
    EditText emailEdit;
    Button sendLink;
    LinearLayout forgotView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        loginTextView=findViewById(R.id.login);
        emailEdit=findViewById(R.id.email);
        sendLink=findViewById(R.id.sendMail);
        forgotView=findViewById(R.id.forgotPassView);
        forgotView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                return false;
            }
        });
        wantToLogin();
        sendReSetLink();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.loginOption1) {
            return true;
        } else if (id == R.id.loginOption2) {
            return true;
        } else if (id == R.id.loginOption3 ) {
            showDialogForExit(ForgotPassword.this, exitAppH, wantToExitAppM);
            return true;
        } else if (id == R.id.violet) {
            Utility.setTheme(getApplicationContext(), THEME_ONE);
            recreateActivity();
            return true;
        } else if (id == R.id.english) {
            Utility.setLanguage(getApplicationContext(),english);
            updateLanguage();
             return true;
        } else if (id == R.id.french) {
            Utility.setLanguage(getApplicationContext(),french);
            updateLanguage();
             return true;
        } else if (id == R.id.spanish) {
            Utility.setLanguage(getApplicationContext(),spanish);
            updateLanguage();
             return  true;
        } else if (id == R.id.german) {
            Utility.setLanguage(getApplicationContext(),german);
            updateLanguage();
             return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void wantToLogin(){
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPassword.this,Login.class));
            }
        });
    }
    public  void sendReSetLink(){
        sendLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isLoggedIn = checkValidation();
                 if (isLoggedIn == true) {
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private boolean checkValidation() {
        // Get email id and password
        String getEmailId = emailEdit.getText().toString();
        boolean isCredentialsCurrect = false;

        // Check patter for email id
        Pattern p = Pattern.compile(Utility.mailAddressFormat);
        Matcher m = p.matcher(getEmailId);

        // Check for both field is empty or not
        if (getEmailId.equals("") || getEmailId.length() == 0){
            Toast.makeText(this, "Please enter your Email Id", Toast.LENGTH_SHORT).show();
             isCredentialsCurrect = false;
        }
        // Check if email id is valid or not
        else if (!m.find()) {
            Toast.makeText(this, "Your Email Id is Invalid", Toast.LENGTH_SHORT).show();
             isCredentialsCurrect = false;
            // Else do login and do your stuff
        } else {
            Toast.makeText(ForgotPassword.this, "Re-set Password Link Sent", Toast.LENGTH_SHORT)
                    .show();
            isCredentialsCurrect = true;
        }
        return isCredentialsCurrect;
    }
    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ForgotPassword.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}