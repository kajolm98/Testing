package com.mtem.asset_management_app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.sql.ResultSet;


public class CommunicationScrap extends BaseActivity {

    Button scrapButton;
     TextView  serialView,makeView,modelView,boughtView,typeView,assignView,statusView,locationView,imeiView,supplierView,prizeView,currencyView;
    TextView  serialNo,make,model,boughtOn,equipmentType,assignTo,status,location,imeiNumber,supplier,prize,labelView,currencyList;
    String barcode,whichRead,selectedLang;
    Context context;
    ImageView barcodeImage;
    Resources resources;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_communication_scrap);

            scrapButton = findViewById(R.id.scrapButton);
            serialNo = findViewById(R.id.serialEdit);
            make = findViewById(R.id.makeEdit);
            model = findViewById(R.id.modelEdit);
            boughtOn = findViewById(R.id.boughtEdit);
            equipmentType = findViewById(R.id.typeList);
            assignTo = findViewById(R.id.assignList);
            status = findViewById(R.id.statusList);
            location = findViewById(R.id.locationList);
            imeiNumber = findViewById(R.id.imeiEdit);
             supplier = findViewById(R.id.supplierEdit);
            prize = findViewById(R.id.prizeEdit);
            currencyList = findViewById(R.id.currencyList);

            labelView = findViewById(R.id.label);
            serialView = findViewById(R.id.serialView);
            makeView = findViewById(R.id.makeView);
            modelView = findViewById(R.id.modelView);
            boughtView = findViewById(R.id.boughtView);
            typeView = findViewById(R.id.typeView);
            assignView = findViewById(R.id.assignView);
            statusView = findViewById(R.id.statusView);
            locationView = findViewById(R.id.locationView);
            imeiView = findViewById(R.id.imeiView);
            supplierView = findViewById(R.id.supplierView);
            prizeView = findViewById(R.id.prizeView);
            currencyView = findViewById(R.id.currencyView);

            barcodeImage = findViewById(R.id.barcodeImage);

            Intent intent = getIntent();
            barcode = intent.getStringExtra(barcodeValueKey);
            selectedLang = intent.getStringExtra(selectedLanguageKey);


            setLang();
            try {
                ResultSet result = cc.sqlModule(selectOperation, overViewTable, allColumns, "PrimaryKey='" + barcode+"'", inventoryDatabase);
                while (result.next()) {
                    labelView.setText("Scrap " + result.getString("AssetType") + " Form");
                    whichRead = result.getString("AssetType");
                    location.setText(result.getString("Location"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (whichRead.equals("Communication Devices")) {
                ResultSet result = cc.sqlModule(selectOperation, communicationDevicesTable, allColumns, "PrimaryKey='" + barcode+"'", inventoryDatabase);
                try {
                    while (result.next()) {
                        serialNo.setText(result.getString("SerialNumber"));
                        make.setText(result.getString("Make"));
                        model.setText(result.getString("Model"));
                        supplier.setText(result.getString("Supplier"));
                        prize.setText(result.getString("Price"));
                        currencyList.setText(result.getString("Currency"));
                        boughtOn.setText(result.getString("BoughtOn"));
                        assignTo.setText(result.getString("AssignedTo"));
                        status.setText(result.getString("Status"));
                        equipmentType.setText(result.getString("EquipmentType"));
                        imeiNumber.setText(result.getString("IMEINumber"));
                    }
                    scrapCommunicationDevices();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else
                Toast.makeText(this, invaliQRM, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void setLang(){
            try {
                serialView.setHint(serialNumberH);
                makeView.setHint(makeH);
                modelView.setHint(modelH);
                boughtView.setHint(boughtOnH);
                typeView.setText(equipmentTypeH);
                assignView.setText(assignToH);
                statusView.setText(statusH);
                locationView.setText(locationH);
                imeiView.setHint(imeiH);
                supplierView.setHint(supplierH);
                prizeView.setHint(prizeH);
                scrapButton.setText(scrapB);
             } catch (Exception e) {
                e.printStackTrace();
        }
    }
    public void showDialog(){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CommunicationScrap.this,R.style.yourDialog);// set title
        alertDialogBuilder.setTitle(warningH);// set dialog message
        alertDialogBuilder.setMessage(scrapW).setCancelable(false)
                .setPositiveButton(yesB,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        if(whichRead.equals("Communication Devices")){
                            String[] communicationCol={"Status"};
                            String[] communicationValue={scrappedStatus};
                            int result=cc.sqlModule(updateOperation,communicationDevicesTable,communicationCol,"PrimaryKey='"+barcode+"'",communicationValue,inventoryDatabase);
                            if(result<=0){
                                Toast.makeText(CommunicationScrap.this,scrapNotSuccessfullM,Toast.LENGTH_LONG).show();
                            }else {
                                String[] overViewCol={"Status"};
                                String[] overViewValue={scrappedStatus};
                                cc.sqlModule(updateOperation,overViewTable,overViewCol,"PrimaryKey='"+barcode+"'",overViewValue,inventoryDatabase);
                                 Toast.makeText(CommunicationScrap.this, scrapSuccessfullM, Toast.LENGTH_LONG).show();
                            }
                            startActivity(new Intent(CommunicationScrap.this,Home.class).putExtra(selectedLanguageKey,selectedLang));
                        }
                    }
                }).setNegativeButton(noB,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void scrapCommunicationDevices(){
        scrapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }
}
