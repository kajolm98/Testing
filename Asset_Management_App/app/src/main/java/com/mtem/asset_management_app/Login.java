package com.mtem.asset_management_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;

public class Login extends BaseActivity {

    Button loginButton;
    EditText usernameEdit,passwordEdit;
     TextView forgotPass,signUp;
     CheckBox showPass,rememberMe;
     LinearLayout loginView,loginView1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.loginButton);
        usernameEdit = findViewById(R.id.usernameEdit);
        passwordEdit = findViewById(R.id.passwordEdit);
        loginView = findViewById(R.id.loginView);
        loginView1 = findViewById(R.id.loginView1);
        showPass = findViewById(R.id.showPassword);
        forgotPass = findViewById(R.id.forgotPassword);
        signUp = findViewById(R.id.signUp);
        rememberMe = findViewById(R.id.rememberMe);
        preLogin();
        setLang();
        showPassword();
        forgotPassword();
        signUp();
        loginView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                return false;
            }
        });loginView1.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                return false;
            }
        });
        loginButtonClick();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.loginOption1) {
            return true;
        } else if (id == R.id.loginOption2) {
            return true;
        } else if (id == R.id.loginOption3 ) {
            showDialogForExit(Login.this, exitAppH, wantToExitAppM);
            return true;
        } else if (id == R.id.violet) {
            Utility.setTheme(getApplicationContext(), THEME_ONE);
            recreateActivity();
            return true;
        } else if (id == R.id.english) {
            Utility.setLanguage(getApplicationContext(),english);
            updateLanguage();
            setLang();
            return true;
        } else if (id == R.id.french) {
            Utility.setLanguage(getApplicationContext(),french);
            updateLanguage();
            setLang();
            return true;
        } else if (id == R.id.spanish) {
            Utility.setLanguage(getApplicationContext(),spanish);
            updateLanguage();
            setLang();
            return  true;
        } else if (id == R.id.german) {
            Utility.setLanguage(getApplicationContext(),german);
            updateLanguage();
            setLang();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        showDialogForExit(Login.this, exitAppH, wantToExitAppM);
    }
    public void setLang(){
        try {
            usernameEdit.setHint(employeeidH);
            passwordEdit.setHint(passwordH);
            loginButton.setText(loginB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loginButtonClick(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(usernameEdit.getText().toString(),passwordEdit.getText().toString());
            }
        });
    }
    public void preLogin(){
        try{
            ResultSet resultSet = cc.sqlModule(selectOperation, "Login_Table", allColumns, "EmployeeID='" +
                    firstWord(Utility.getUserName(getApplicationContext()))+ "' AND " + "Password='" +
                    lastWord(Utility.getPassword(getApplicationContext())) + "'", genericDatabase);
            if (resultSet.next()) {
                do {
                    userId = resultSet.getString("EmployeeID").trim();
                    userName = resultSet.getString("Employee_name").trim();
                    userPassword = resultSet.getString("Password").trim();
                    userRole = resultSet.getString("Role").trim();
                } while (resultSet.next());
                     startActivity(new Intent(Login.this, Home.class).putExtra(selectedLanguageKey, selectedLanguage));
                    Toast.makeText(Login.this, loggedInM, Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void login(String username,String password){
        boolean isValid = checkValidation(username,password );
        if (isValid == true) {
            try {
                ResultSet resultSet = cc.sqlModule(selectOperation, "Login_Table", allColumns, "EmployeeID='" +  username+ "' AND " +
                        "Password='" +password + "'", genericDatabase);
                if (resultSet.next()) {
                    do {
                        userId = resultSet.getString(THEME_ONE).trim();
                        userName = resultSet.getString(2).trim();
                        userPassword = resultSet.getString(3).trim();
                        userRole = resultSet.getString(4).trim();
                    } while (resultSet.next());
                    if (rememberMe.isChecked()) {
                        Utility.setlogin(getApplicationContext(), userId, userPassword);
                    }
                    startActivity(new Intent(Login.this, Home.class).putExtra(selectedLanguageKey, selectedLanguage));
                    Toast.makeText(Login.this, loggedInM, Toast.LENGTH_LONG).show();
                } else {
                    showMessage(warningH, invalidUPM);
                }
            } catch (Exception e) {
                Toast.makeText(Login.this, somethingWentWrongM, Toast.LENGTH_LONG).show();
            }
        }
    }
    public void showPassword() {
        showPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                if (isChecked) {
                    showPass.setText("hide_password");// change
                    passwordEdit.setInputType(InputType.TYPE_CLASS_TEXT);
                    passwordEdit.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());// show password
                } else {
                    showPass.setText("show_password");// change
                    passwordEdit.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordEdit.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());// hide password
                }
            }
        });
    }
    public void forgotPassword(){
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,ForgotPassword.class));
            }
        });
    }
    public void signUp(){
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,SignUp.class));
            }
        });
    }
    public boolean checkValidation(String username,String password) {
        // Get email id and password
        boolean isCredentialsCurrect = false;
        // Check for both field is empty or not
        if (username.equals("") || username.length() == zero
                || password.equals("") || password.length() == zero) {
            showMessage(warningH, "Please enter your Id and Password");
            isCredentialsCurrect = false;
            // Check if email id is valid or not
        } else {
            isCredentialsCurrect = true;
        }
        return isCredentialsCurrect;
    }

}
