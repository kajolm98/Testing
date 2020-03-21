package com.mtem.asset_management_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewBarcode1 extends BaseActivity {

    Button printAndNextButton;
    TextView uniqueBarcodeGenerated,qrCodeValue;
     SimpleDateFormat dateFormat;
     String onlyDate,selectedLang,space="",spaced="",stringInt,zeros="000",lastRow="",barcodeFormat,
            eu="EU",dot=".",hyphen="-",nullZero="",oneZero="0",twoZero="00",threeZero="000",fourZero="0000",fiveZero="00000";
     String adminLocation,adminCompany;
    int index,intFormat, hundred =100,ten=10,thousand=1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_new_barcode1);
            printAndNextButton = findViewById(R.id.printAndNextButton);
            qrCodeValue = findViewById(R.id.barcodeView);
            uniqueBarcodeGenerated = findViewById(R.id.uniqueBarcodeGenerated);
            ResultSet resultSet = cc.sqlModule(selectOperation,"Employee_Details",topOneColumn,"EmployeeID='"+userId+"' and FirstName='"+firstWord(userName)+"'",genericDatabase);
            while (resultSet.next()) {
                adminCompany = resultSet.getString("CompanyName").toUpperCase();
                adminLocation = resultSet.getString("Location").toUpperCase();
            }
            makeBarcodeFormat();
            barcodeFormat = adminCompany.toUpperCase() + dot +adminLocation.toUpperCase()+dot+ onlyDate + dot + zeros + lastRow;
            Intent intent = getIntent();
            selectedLang = intent.getStringExtra(selectedLanguageKey);
            setLanguage();
            makeBarcodeText();
            printAndNext();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void makeBarcodeFormat(){
        try {
            if(adminCompany.length()<six||adminLocation.length()<three) {
                if (adminCompany.length() == five) {
                    adminCompany = adminCompany.concat(oneZero);
                } else if (adminCompany.length() == four) {
                    adminCompany = adminCompany.concat(twoZero);
                } else if (adminCompany.length() == three) {
                    adminCompany = adminCompany.concat(threeZero);
                } else if (adminCompany.length() == two) {
                    adminCompany = adminCompany.concat(fourZero);
                } else if (adminCompany.length() == one) {
                    adminCompany = adminCompany.concat(fiveZero);
                }
                if (adminLocation.length() == two) {
                    adminLocation = adminLocation.concat(oneZero);
                } else if (adminLocation.length() == one) {
                    adminLocation = adminLocation.concat(twoZero);
                }
            }
             if(adminCompany.length()>six||adminLocation.length()>three){
                if(adminCompany.length()>six){
                adminCompany=adminCompany.substring(zero,six);}
                if(adminLocation.length()>three){
                adminLocation=adminLocation.substring(zero,three);}
            }
            dateFormat = new SimpleDateFormat( datePattern);
            onlyDate = dateFormat.format(new Date()).replace(hyphen, dot);
            ResultSet resultDate = cc.sqlModule(selectOperation,overViewTable,topOneColumn,"PrimaryKey LIKE '%"+adminCompany+"."+adminLocation+
                    "%' order by PrimaryKey desc",inventoryDatabase);
            while (resultDate.next()) {
                stringInt = resultDate.getString(one).substring(11, 21);
            }
            ResultSet resultRow = cc.sqlModule(selectOperation,overViewTable,topOneColumn,"PrimaryKey LIKE '%"+adminCompany+"."+adminLocation+
                    "%' order by PrimaryKey desc",inventoryDatabase);
            if (resultRow.getFetchSize() == zero || !onlyDate.equals(stringInt)) {
                lastRow = String.valueOf(one);
            } else
                while (resultRow.next()) {
                    lastRow = resultRow.getString(one).substring(22, 26);
                    intFormat = Integer.parseInt(lastRow);
                    intFormat++;
                    lastRow = String.valueOf(intFormat);
                }
            if (intFormat >= ten) {
                zeros = twoZero;
            } else if (intFormat >= hundred) {
                zeros = oneZero;
            } else if (intFormat >= thousand) {
                zeros = nullZero;
            }
        }
        catch (Exception e){
            Toast.makeText(this, somethingWentWrongM, Toast.LENGTH_SHORT).show();
        }
    }
    public void printAndNext(){
        printAndNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String[] overViewCol = {"PrimaryKey", "QRCodeImage"};
                    String[] overViewValues = {barcodeFormat, barcodeFormat};
                    int result = cc.sqlModule(insertOperation, "OverView", overViewCol, null, overViewValues, inventoryDatabase);
                    if (result > zero) {
                        Toast.makeText(NewBarcode1.this, newQRCodeCreatedM, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(NewBarcode1.this, NewBarcode2.class).putExtra(barcodeValueKey, barcodeFormat)
                                .putExtra(selectedLanguageKey, selectedLang));
                    } else
                        Toast.makeText(NewBarcode1.this, newQRCodeNotCreatedM, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void setLanguage(){
            try {
                uniqueBarcodeGenerated.setText(newQRCodeGeneratedH);
                printAndNextButton.setText(printQRCodeAndProceedB);
            } catch (Exception e) {
                e.printStackTrace();
        }
    }
    public void makeBarcodeText(){
        char[] ctr=barcodeFormat.toCharArray();
        for(index=zero;index<barcodeFormat.length();index++){
            if(index==barcodeFormat.length()-one){
                spaced+=ctr[index];
            }else
                spaced+=ctr[index]+space;
        }
        qrCodeValue.setText(spaced);
    }
}
