package com.mtem.asset_management_app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSet;
import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {
    public final static int THEME_ONE = 1;
    public static final String mailAddressFormat = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";
    public static final int one=1,two=2,three=3,four=4,five=5,six=6,seven=7,eight=8,nine=9,zero=0;
    public static final String backSlash="/",asterix="*",hyphen="-",dot=".";
    public static String selectedLanguage,selectedLanguageKey="selectedLang",selectOperation="select",insertOperation="insert",deleteOperation="delete",
             updateOperation="update";
    public static String userId,userName,email,userPassword,userRole,inventoryDatabase="InventoryManagementDataBase",genericDatabase="GenericDataBase";
    public static String[] allColumns={"*"},topOneColumn={"top(1)*"};
     public static String datePattern="yyyy-MM-dd",barcodeValueKey="barcodeValue",tasKey="task",scrappedKey="scrapped",scrappedStatus="Scrapped";
    public static String user="user",taskKey="task",assignTask="assign",readTask="read",updateTask="update",scrapTask="scrap";
    public static String communicationDevicesH="communicationDevices",finishedGoodsH="finishedGoods",furnitureH="furniture",itEquipmentH="itEquipment",
            technicalEquipmentH="technicalEquipment",userDetailsH="UserDetails",empoyee_DetailsH="Employee_Details",
            vehicleH="vehicle",othersH="others",employeeH="employee",loginTableH="Login_Table",roleH="Role";
    public static String communicationDevicesTable="CommunicationDevices",finishedGoodsTable="FinishedGoods",furnitureTable="Furniture",itEquipmentTable="ITEquipment",
            technicalEquipmentTable="TechnicalEquipment",vehicleTable="Vehicles",othersTable="Others",overViewTable="OverView";
    public static String english="english",french="french",spanish="spanish",german="german";

    public static String usernameH, passwordH, loginB, warningH, themesH, languagesH, exitAppH, yesB, noB, invalidUPM, wantToExitAppM, loggedInM,
            inventoryManagmentH, userInfoH, logoutH, userLogoutH, wantToLogoutM, craeteNewAssetB, assignAssetB, updateAssetB, readAssetB, scrapAssetB,
            queryInventoryB, idH, nameH, adminH, homeH, newQRCodeGeneratedH, printQRCodeAndProceedB, newQRCodeCreatedM, newQRCodeNotCreatedM,
            somethingWentWrongM, downloadQRCodeB, assignNewAssetB, selectAssetTypeH, cdH, fgH, fuH, iteH, teH, vH, OH, scanAssetRQCodeM, scanQRCodeB,
            submitB, alreadyAssignM, alreadyScrapM, invaliQRM, serialNumberH, makeH, modelH, boughtOnH, equipmentTypeH, assignToH, statusH, locationH,
            imeiH, supplierH, macH, prizeH, lengthH, widthH, heightH, saveW, assignSuccessfullM, assignnNotSuccessfullM, updateSuccessfullM, updateNotSuccessfullM,
            scrapSuccessfullM, scrapNotSuccessfullM,saveB,updateB,scrapB,readB,mandatoryFieldM,scrapW,currencyH,lenghtUnitH,widthUnitH,heightUnitH,employeeidH,
            addNewUserH;


    public static int language = 1;
    ConnectionClass cc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cc = new ConnectionClass();
        updateTheme();
        updateLanguage();
       }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_all, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.allOption1) {
            showUserInformationDialog(userInfoH,userName,userRole,userId,userPassword);
            return true;
        } else if (id == R.id.allOption2) {
            startActivity(new Intent(BaseActivity.this, Home.class).putExtra(selectedLanguageKey, Login.selectedLanguage));
            return true;
        } else if (id == R.id.allOption3) {
            showDialogForLogout(BaseActivity.this, userLogoutH, wantToLogoutM);
            return true;
        } else if (id == R.id.allOption4) {
            showDialogForExit(BaseActivity.this, exitAppH, wantToExitAppM);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public ArrayList retriveDropDown(String colName,String tableName,String dbName ){
            ArrayList allList= new ArrayList<String>();
         try {
            String[] columns = {colName};
            ResultSet result = cc.sqlModule(selectOperation, tableName, columns,colName+" IS NOT NULL", dbName);
            allList.add("-select-");
            while (result.next()) {
                 allList.add(result.getString(colName).trim());
             }
        }catch (Exception e){
            e.printStackTrace();
        }
        return allList;
    }
    public void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    public void recreateActivity() {
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(zero, zero);
        startActivity(intent);
        overridePendingTransition(zero, zero);
    }
    public static String firstWord(String input) {
        return input.split(" ")[0]; // Create array of words and return the 0th word
    }
    public static String lastWord(String input) {
        return input.substring(input.lastIndexOf(" ")+1);
     }
    public void showDialogForExit(Context context, String title, String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.yourDialog);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg).setCancelable(false)
                .setPositiveButton(yesB, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                    }
                })
                .setNegativeButton(noB, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void showDialogForLogout(Context context, String title, String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.yourDialog);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg).setCancelable(false)
                .setPositiveButton(yesB, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Utility.clearlogin(getApplicationContext());
                        Intent logoutToLogin = new Intent(getApplicationContext(), Login.class);
                        startActivity(logoutToLogin);
                    }
                })
                .setNegativeButton(noB, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /*public String setSerialNumber(String tableName){

        ResultSet resultData = cc.sqlModule(selectOperation, tableName + " order by PrimaryKey desc", topOneColumn, null, inventoryDatabase);
        String setSerial=null;
        try {
            if(resultData.next()) {
                String rowData = resultData.getString("SerialNumber");
                int rowNo = Integer.parseInt(rowData);
                rowNo++;
                setSerial=String.valueOf(rowNo);
            }else
            {
                setSerial=String.valueOf(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return setSerial;
    }*/

    public void showMessage(String title, String message) {
        AlertDialog.Builder popUp = new AlertDialog.Builder(this);
        popUp.setCancelable(true);
        popUp.setTitle(title);
        popUp.setMessage(message);
        popUp.show();
    }

    public void showUserInformationDialog(String title,String name,String role,String userId,String userPass) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.yourDialog);
        builder.setTitle(title);
       View userInfoLayout = getLayoutInflater().inflate(R.layout.user_information, null);
        TextView username = userInfoLayout.findViewById(R.id.userName);
        TextView userRole = userInfoLayout.findViewById(R.id.userRole);
        TextView id = userInfoLayout.findViewById(R.id.idEdit);
        TextView pass = userInfoLayout.findViewById(R.id.passwordEdit);
        username.setText(name.toUpperCase());
        userRole.setText(role.toUpperCase());
        id.setText(userId);
        pass.setText(userPass);
        builder.setView(userInfoLayout)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog userDialog = builder.create();
        userDialog.show();
    }
    public void updateTheme() {
        if (Utility.getTheme(getApplicationContext()) == THEME_ONE) {
            setTheme(R.style.AppTheme_violet);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.isHideOnContentScrollEnabled();
              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(getResources().getColor(R.color.black));
                language = THEME_ONE;
            }
        }
    }

    public void updateLanguage() {
        ResultSet resultSet = null;
        int rowNo=0;
        if (Utility.getLanguage(getApplicationContext()).equals(english)) {
            selectedLanguage = Utility.getLanguage(getApplicationContext());
            String chooseLang[]={selectedLanguage};
            resultSet = cc.sqlModule(selectOperation,"Languages",chooseLang,null,inventoryDatabase);
        } else if (Utility.getLanguage(getApplicationContext()).equals(french)) {
            selectedLanguage = Utility.getLanguage(getApplicationContext());
            String chooseLang[]={selectedLanguage};
            resultSet = cc.sqlModule(selectOperation,"Languages",chooseLang,null,inventoryDatabase);
        } else if (Utility.getLanguage(getApplicationContext()).equals(spanish)) {
            selectedLanguage = Utility.getLanguage(getApplicationContext());
            String chooseLang[]={selectedLanguage};
            resultSet = cc.sqlModule(selectOperation,"Languages",chooseLang,null,inventoryDatabase);
        } else if (Utility.getLanguage(getApplicationContext()).equals(german)) {
            selectedLanguage = Utility.getLanguage(getApplicationContext());
            String chooseLang[]={selectedLanguage};
            resultSet = cc.sqlModule(selectOperation,"Languages",chooseLang,null,inventoryDatabase);
        }
        try {
            ArrayList<String> lang = new ArrayList<>();
            while (resultSet.next()) {
                lang.add(resultSet.getString(selectedLanguage));
            }
            usernameH = lang.get(rowNo++);
            passwordH = lang.get(rowNo++);
            loginB = lang.get(rowNo++);
            warningH = lang.get(rowNo++);
            themesH = lang.get(rowNo++);
            languagesH = lang.get(rowNo++);
            exitAppH = lang.get(rowNo++);
            yesB = lang.get(rowNo++);
            noB = lang.get(rowNo++);
            invalidUPM = lang.get(rowNo++);

            wantToExitAppM = lang.get(rowNo++);
            loggedInM = lang.get(rowNo++);
            inventoryManagmentH = lang.get(rowNo++);
            userInfoH = lang.get(rowNo++);
            logoutH = lang.get(rowNo++);
            userLogoutH = lang.get(rowNo++);
            wantToLogoutM = lang.get(rowNo++);
            craeteNewAssetB = lang.get(rowNo++);
            assignAssetB = lang.get(rowNo++);
            updateAssetB = lang.get(rowNo++);

            readAssetB = lang.get(rowNo++);
            scrapAssetB = lang.get(rowNo++);
            queryInventoryB = lang.get(rowNo++);
            idH = lang.get(rowNo++);
            nameH = lang.get(rowNo++);
            adminH = lang.get(rowNo++);
            homeH = lang.get(rowNo++);
            newQRCodeGeneratedH = lang.get(rowNo++);
            printQRCodeAndProceedB = lang.get(rowNo++);
            newQRCodeCreatedM = lang.get(rowNo++);

            newQRCodeNotCreatedM = lang.get(rowNo++);
            somethingWentWrongM = lang.get(rowNo++);
            downloadQRCodeB = lang.get(rowNo++);
            assignNewAssetB = lang.get(rowNo++);
            selectAssetTypeH = lang.get(rowNo++);
            cdH = lang.get(rowNo++);
            fgH = lang.get(rowNo++);
            fuH = lang.get(rowNo++);
            iteH = lang.get(rowNo++);
            teH = lang.get(rowNo++);

            vH = lang.get(rowNo++);
            OH = lang.get(rowNo++);
            scanAssetRQCodeM = lang.get(rowNo++);
            scanQRCodeB = lang.get(rowNo++);
            submitB = lang.get(rowNo++);
            alreadyAssignM = lang.get(rowNo++);
            alreadyScrapM = lang.get(rowNo++);
            invaliQRM = lang.get(rowNo++);
            serialNumberH = lang.get(rowNo++);
            makeH = lang.get(rowNo++);

            modelH = lang.get(rowNo++);
            boughtOnH = lang.get(rowNo++);
            equipmentTypeH = lang.get(rowNo++);
            assignToH = lang.get(rowNo++);
            statusH = lang.get(rowNo++);
            locationH = lang.get(rowNo++);
            imeiH = lang.get(rowNo++);
            supplierH = lang.get(rowNo++);
            macH = lang.get(rowNo++);
            prizeH = lang.get(rowNo++);

            lengthH = lang.get(rowNo++);
            widthH = lang.get(rowNo++);
            heightH = lang.get(rowNo++);
            saveW = lang.get(rowNo++);
            assignSuccessfullM = lang.get(rowNo++);
            assignnNotSuccessfullM = lang.get(rowNo++);
            updateSuccessfullM = lang.get(rowNo++);
            updateNotSuccessfullM = lang.get(rowNo++);
            scrapSuccessfullM = lang.get(rowNo++);
            scrapNotSuccessfullM = lang.get(rowNo++);
            saveB = lang.get(rowNo++);
            updateB = lang.get(rowNo++);
            scrapB = lang.get(rowNo++);
            readB = lang.get(rowNo++);
            mandatoryFieldM=lang.get(rowNo++);
            scrapW=lang.get(rowNo++);

            currencyH=lang.get(rowNo++);
            lenghtUnitH=lang.get(rowNo++);
            widthUnitH=lang.get(rowNo++);
            heightUnitH=lang.get(rowNo++);
            employeeidH=lang.get(rowNo++);
            addNewUserH=lang.get(rowNo++);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}






