package com.mtem.asset_management_app;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends BaseActivity {
    TextView loginTextView;
    Button signUp;
    DatePickerDialog picker;
    CheckBox terms;
    LinearLayout signUpView;
    EditText firstName,lastName,address,email,phone,dateOfBirth,setPass,conPass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        loginTextView=findViewById(R.id.login);
        signUp=findViewById(R.id.signUp);
        firstName=findViewById(R.id.firstName);
        lastName=findViewById(R.id.lastName);
        address=findViewById(R.id.address);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phonrNo);
        dateOfBirth=findViewById(R.id.dob);
        setPass=findViewById(R.id.setPass);
        conPass=findViewById(R.id.conPass);
        terms=findViewById(R.id.agree);
        signUpView=findViewById(R.id.signUpView);
        wantToLogin();
        signUp();
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);
                picker = new DatePickerDialog(SignUp.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateOfBirth.setText(year + "-" + (monthOfYear + one) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
                picker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                picker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
            }
        });
        signUpView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                return false;
            }
        });
        }catch (Exception e){
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
            showDialogForExit(SignUp.this, exitAppH, wantToExitAppM);
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
                startActivity(new Intent(SignUp.this,Login.class));
            }
        });
    }
    public void signUp(){
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = checkValidation();
                 if (isValid == true) {
                     try {
                         String[] signDeCol = {"FirstName", "LastName", "Address", "EmailID", "PhoneNo", "DateOfBirth", "Password", "Status"};
                         String[] signDeValues = {firstName.getText().toString(), lastName.getText().toString(), address.getText().toString(),
                                 email.getText().toString(), phone.getText().toString(), dateOfBirth.getText().toString(),
                                 setPass.getText().toString(),"Pending"};
                         int result = cc.sqlModule(insertOperation, userDetailsH, signDeCol, null, signDeValues, genericDatabase);
                         if (result > 0) {
                             Toast.makeText(SignUp.this, "Signed Up", Toast.LENGTH_SHORT).show();
                         } else
                             Toast.makeText(SignUp.this, somethingWentWrongM, Toast.LENGTH_SHORT).show();
                     } catch (Exception e) {
                         Toast.makeText(SignUp.this, somethingWentWrongM+e.getMessage(), Toast.LENGTH_SHORT).show();
                         e.printStackTrace();
                     }
                 }
            }
        });
    }
    public boolean checkValidation() {
        // Get email id and password
        String first=firstName.getText().toString();
        String last=lastName.getText().toString();
        String addr=address.getText().toString();
        String getEmailId = email.getText().toString();
        String phoneNo=phone.getText().toString();
        String dateOfB=dateOfBirth.getText().toString();
        String setPassword = setPass.getText().toString();
        String conPassword = conPass.getText().toString();

        boolean isCredentialsCurrect = false;

        // Check patter for email id
        Pattern p = Pattern.compile(Utility.mailAddressFormat);
        Matcher m = p.matcher(getEmailId);

        // Check for both field is empty or not
        if (first.equals("") || first.length() == 0
                ||last.equals("") || last.length() == 0
                ||addr.equals("") || addr.length() == 0
                ||getEmailId.equals("") || getEmailId.length() == 0
                ||phoneNo.equals("") || phoneNo.length() == 0
                ||dateOfB.equals("") || dateOfBirth.length() == 0
                ||setPassword.equals("") || setPassword.length() == 0
                || conPassword.equals("") || conPassword.length() == 0) {
            Toast.makeText(SignUp.this, "Enter All Credentials", Toast.LENGTH_SHORT).show();
            isCredentialsCurrect = false;
            // Check if email id is valid or not
        }else if (!m.find()) {
            Toast.makeText(SignUp.this, "Your Email Id is Invalid", Toast.LENGTH_SHORT).show();
            isCredentialsCurrect = false;
            // Else do login and do your stuff
        }else if (phoneNo.length()>10){
            Toast.makeText(SignUp.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
        }else if(setPassword.length()<4){
            Toast.makeText(SignUp.this, "Password min length should be 4", Toast.LENGTH_SHORT).show();
        }else if(!setPassword.equals(conPassword)){
            Toast.makeText(SignUp.this, "Both password doesn't match", Toast.LENGTH_SHORT).show();
        }else if (!terms.isChecked()){
            Toast.makeText(SignUp.this, "Please select Terms and Conditions", Toast.LENGTH_SHORT).show();
    } else {
            isCredentialsCurrect = true;
        }
        return isCredentialsCurrect;
    }
}

