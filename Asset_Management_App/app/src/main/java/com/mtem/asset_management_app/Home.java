package com.mtem.asset_management_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.sql.ResultSet;


public class Home extends BaseActivity {

    Button newAssetButton, assignAssetButton, updateAssetButton, readAssetButton, scrapAssetButton, queryInventoryButton,addNewUser;
    String task, selectedLang;
    TextView inventoryManagement;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        newAssetButton = findViewById(R.id.newAssetButton);
        assignAssetButton = findViewById(R.id.assignAssetButton);
        updateAssetButton = findViewById(R.id.updateAssetButton);
        readAssetButton = findViewById(R.id.readAssetButton);
        scrapAssetButton = findViewById(R.id.scrapAssetButton);
        queryInventoryButton = findViewById(R.id.queryInventoryButton);
         inventoryManagement = findViewById(R.id.inventoryManagement);
         addNewUser=findViewById(R.id.signedUserBtn);

        Intent intent = getIntent();
        selectedLang = intent.getStringExtra(selectedLanguageKey);
        setLanguage();
        try {
            if (userRole.equalsIgnoreCase(employeeH)) {
                View newAssetBtn = findViewById(R.id.newAssetButton);
                View assignAssetBtn = findViewById(R.id.assignAssetButton);
                View updateAssetBtn = findViewById(R.id.updateAssetButton);
                View scrapAssetBtn = findViewById(R.id.scrapAssetButton);
                View signedUserBtn = findViewById(R.id.signedUserBtn);

                ViewGroup parent = (ViewGroup) newAssetBtn.getParent();
                ViewGroup parent1 = (ViewGroup) assignAssetBtn.getParent();
                ViewGroup parent2 = (ViewGroup) updateAssetBtn.getParent();
                ViewGroup parent3 = (ViewGroup) scrapAssetBtn.getParent();
                ViewGroup parent4 = (ViewGroup) signedUserBtn.getParent();

                parent.removeView(newAssetBtn);
                parent1.removeView(assignAssetBtn);
                parent2.removeView(updateAssetBtn);
                parent3.removeView(scrapAssetBtn);
                parent4.removeView(signedUserBtn);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        newAssetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, NewBarcode1.class).putExtra(selectedLanguageKey, selectedLang));
            }
        });
        assignAssetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task = assignTask;
                startActivity(new Intent(Home.this, ScanBarcode.class).putExtra(taskKey, task).putExtra(selectedLanguageKey, selectedLang));
            }
        });
        updateAssetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task = updateTask;
                startActivity(new Intent(Home.this, ScanBarcode.class).putExtra(taskKey, task).putExtra(selectedLanguageKey, selectedLang));
            }
        });
        readAssetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task = readTask;
                startActivity(new Intent(Home.this, ScanBarcode.class).putExtra(taskKey, task).putExtra(selectedLanguageKey, selectedLang));
            }
        });
        scrapAssetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task = scrapTask;
                startActivity(new Intent(Home.this, ScanBarcode.class).putExtra(taskKey, task).putExtra(selectedLanguageKey, selectedLang));
            }
        });
        queryInventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent asset_query = new Intent(getApplicationContext(), QueryInventory.class).putExtra(selectedLanguageKey, selectedLang);
                startActivity(asset_query);
            }
        });
        addNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showAlertDialogForAddUser(addNewUserH);
                startActivity(new Intent(Home.this,ApprovalUser.class));
            }
        });
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.homeOption1) {
            showUserInformationDialog(userInfoH,userName,userRole,userId,userPassword);
             return true;
        }else if (id == R.id.homeOption2) {
            showDialogForLogout(Home.this,logoutH,wantToLogoutM);
            return true;
        }else if (id == R.id.homeOption3) {
            showDialogForExit(Home.this,exitAppH,wantToExitAppM);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        showDialogForLogout(Home.this,logoutH,wantToLogoutM);
    }
    public void setLanguage(){
            try{
                inventoryManagement.setText(inventoryManagmentH);
                newAssetButton.setText(craeteNewAssetB);
                assignAssetButton.setText(assignAssetB);
                updateAssetButton.setText(updateAssetB);
                readAssetButton.setText(readAssetB);
                scrapAssetButton.setText(scrapAssetB);
                queryInventoryButton.setText(queryInventoryB);
                addNewUser.setText(addNewUserH);
            } catch (Exception e) {
                e.printStackTrace();
        }
    }
}