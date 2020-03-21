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

public class FgOtTeVeScrap extends BaseActivity {
    Button scrapButton;
     TextView  serialView,makeView,modelView,boughtView,typeView,assignView,statusView,locationView,supplierView,prizeView,currencyView;
    TextView serialNo,make,model,boughtOn,equipmentType,assignTo,status,location,supplier,prize,labelView,currencyEdit;
    String barcode,whichRead,selectedLang;
    Context context;
    ImageView barcodeImage;
    Resources resources;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fg_ot_te_ve_scrap);

        serialNo = findViewById(R.id.serialEdit);
        make = findViewById(R.id.makeEdit);
        model = findViewById(R.id.modelEdit);
        boughtOn = findViewById(R.id.boughtEdit);
        scrapButton = findViewById(R.id.scrapButton);
        status = findViewById(R.id.statusList);
        assignTo = findViewById(R.id.assignList);
        location = findViewById(R.id.locationList);
        equipmentType = findViewById(R.id.typeList);
        supplier = findViewById(R.id.supplierEdit);
            prize = findViewById(R.id.prizeEdit);
            currencyEdit = findViewById(R.id.currencyList);

            labelView = findViewById(R.id.label);
        serialView = findViewById(R.id.serialView);
        makeView = findViewById(R.id.makeView);
        modelView = findViewById(R.id.modelView);
        boughtView = findViewById(R.id.boughtView);
        typeView = findViewById(R.id.typeView);
        assignView = findViewById(R.id.assignView);
        statusView = findViewById(R.id.statusView);
        locationView = findViewById(R.id.locationView);
        supplierView = findViewById(R.id.supplierView);
        prizeView = findViewById(R.id.prizeView);
            currencyView = findViewById(R.id.currencyView);


            barcodeImage = findViewById(R.id.barcodeImage);

        Intent intent = getIntent();
        barcode = intent.getStringExtra(barcodeValueKey);
        selectedLang = intent.getStringExtra(selectedLanguageKey);


        setLang();

        ResultSet result1 = cc.sqlModule(selectOperation,overViewTable,allColumns,"PrimaryKey='"+barcode+"'",inventoryDatabase);
        try {
            while (result1.next()) {
                labelView.setText("Scrap " + result1.getString("AssetType") + " Form");
                whichRead = result1.getString("AssetType");
                location.setText(result1.getString("Location"));
            }
            if (whichRead.equals("Finished Goods")) {
                ResultSet result = cc.sqlModule(selectOperation,finishedGoodsTable,allColumns,"PrimaryKey='"+barcode+"'",inventoryDatabase);
                while (result.next()) {
                    serialNo.setText(result.getString("SerialNumber"));
                    make.setText(result.getString("Make"));
                    model.setText(result.getString("Model"));
                    supplier.setText(result.getString("Supplier"));
                    prize.setText(result.getString("Price"));
                    currencyEdit.setText(result.getString("Currency"));
                    boughtOn.setText(result.getString("BoughtOn"));
                    assignTo.setText(result.getString("AssignedTo"));
                    status.setText(result.getString("Status"));
                    equipmentType.setText(result.getString("EquipmentType"));
                }
                scrapFinishedGoods();
            } else if (whichRead.equals("Others")) {
                ResultSet result = cc.sqlModule(selectOperation,othersTable,allColumns,"PrimaryKey='"+barcode+"'",inventoryDatabase);
                while (result.next()) {
                    serialNo.setText(result.getString("SerialNumber"));
                    make.setText(result.getString("Make"));
                    model.setText(result.getString("Model"));
                    supplier.setText(result.getString("Supplier"));
                    prize.setText(result.getString("Price"));
                    currencyEdit.setText(result.getString("Currency"));
                    boughtOn.setText(result.getString("BoughtOn"));
                    assignTo.setText(result.getString("AssignedTo"));
                    status.setText(result.getString("Status"));
                    equipmentType.setText(result.getString("EquipmentType"));
                }
                scrapOthers();
            } else if (whichRead.equals("Technical Equipment")) {
                ResultSet result = cc.sqlModule(selectOperation,technicalEquipmentTable,allColumns,"PrimaryKey='"+barcode+"'",inventoryDatabase);
                while (result.next()) {
                    serialNo.setText(result.getString("SerialNumber"));
                    make.setText(result.getString("Make"));
                    model.setText(result.getString("Model"));
                    supplier.setText(result.getString("Supplier"));
                    prize.setText(result.getString("Price"));
                    currencyEdit.setText(result.getString("Currency"));
                    boughtOn.setText(result.getString("BoughtOn"));
                    assignTo.setText(result.getString("AssignedTo"));
                    status.setText(result.getString("Status"));
                    equipmentType.setText(result.getString("EquipmentType"));
                }
                scrapTechnicalEquipment();
            } else if (whichRead.equals("Vehicle")) {
                ResultSet result =cc.sqlModule(selectOperation,vehicleTable,allColumns,"PrimaryKey='"+barcode+"'",inventoryDatabase);
                while (result.next()) {
                    serialNo.setText(result.getString("SerialNumber"));
                    make.setText(result.getString("Make"));
                    model.setText(result.getString("Model"));
                    supplier.setText(result.getString("Supplier"));
                    prize.setText(result.getString("Price"));
                    currencyEdit.setText(result.getString("Currency"));
                    boughtOn.setText(result.getString("BoughtOn"));
                    assignTo.setText(result.getString("AssignedTo"));
                    status.setText(result.getString("Status"));
                    equipmentType.setText(result.getString("EquipmentType"));
                }
                scrapVehicle();
            }
        else
            Toast.makeText(this,invaliQRM,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            supplierView.setHint(supplierH);
            prizeView.setHint(prizeH);
            scrapButton.setText(scrapB);
         } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showDialog(){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FgOtTeVeScrap.this,R.style.yourDialog);// set title
        alertDialogBuilder.setTitle(warningH);// set dialog message
        alertDialogBuilder.setMessage(scrapW).setCancelable(false)
                .setPositiveButton(yesB,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        if(whichRead.equals("Finished Goods")){
                            String[] communicationCol={"Status"};
                            String[] communicationValue={scrappedKey};
                            int result=cc.sqlModule(updateOperation,finishedGoodsTable,communicationCol,"PrimaryKey='"+barcode+"'",communicationValue,inventoryDatabase);
                            if(result<=0){
                                Toast.makeText(FgOtTeVeScrap.this,scrapNotSuccessfullM,Toast.LENGTH_LONG).show();
                            }else {
                                String[] overViewCol={"Status"};
                                String[] overViewValue={scrappedKey};
                                cc.sqlModule(updateOperation,overViewTable,overViewCol,"PrimaryKey='"+barcode+"'",overViewValue,inventoryDatabase);
                                Toast.makeText(FgOtTeVeScrap.this, scrapSuccessfullM, Toast.LENGTH_LONG).show();
                            }
                            startActivity(new Intent(FgOtTeVeScrap.this,Home.class).putExtra(selectedLanguageKey,selectedLang));
                        } else if(whichRead.equals("Others")){
                            String[] communicationCol={"Status"};
                            String[] communicationValue={scrappedKey};
                            int result=cc.sqlModule(updateOperation,othersTable,communicationCol,"PrimaryKey='"+barcode+"'",communicationValue,inventoryDatabase);
                            if(result<=0){
                                Toast.makeText(FgOtTeVeScrap.this,scrapNotSuccessfullM,Toast.LENGTH_LONG).show();
                            }else {
                                String[] overViewCol={"Status"};
                                String[] overViewValue={scrappedKey};
                                cc.sqlModule(updateOperation,overViewTable,overViewCol,"PrimaryKey='"+barcode+"'",overViewValue,inventoryDatabase);
                                Toast.makeText(FgOtTeVeScrap.this, scrapSuccessfullM, Toast.LENGTH_LONG).show();
                            }
                            startActivity(new Intent(FgOtTeVeScrap.this,Home.class).putExtra(selectedLanguageKey,selectedLang));
                        } else if(whichRead.equals("Technical Equipment")){
                            String[] communicationCol={"Status"};
                            String[] communicationValue={scrappedKey};
                            int result=cc.sqlModule(updateOperation,technicalEquipmentTable,communicationCol,"PrimaryKey='"+barcode+"'",communicationValue,inventoryDatabase);
                            if(result<=0){
                                Toast.makeText(FgOtTeVeScrap.this,scrapNotSuccessfullM,Toast.LENGTH_LONG).show();
                            }else {
                                String[] overViewCol={"Status"};
                                String[] overViewValue={scrappedKey};
                                cc.sqlModule(updateOperation,"OverView",overViewCol,"PrimaryKey='"+barcode+"'",overViewValue,inventoryDatabase);
                                Toast.makeText(FgOtTeVeScrap.this, scrapSuccessfullM, Toast.LENGTH_LONG).show();
                            }
                            startActivity(new Intent(FgOtTeVeScrap.this,Home.class).putExtra(selectedLanguageKey,selectedLang));
                        }
                        else if(whichRead.equals("Vehicle")){
                            String[] communicationCol={"Status"};
                            String[] communicationValue={scrappedKey};
                            int result=cc.sqlModule(updateOperation,vehicleTable,communicationCol,"PrimaryKey='"+barcode+"'",communicationValue,inventoryDatabase);
                            if(result<=0){
                                Toast.makeText(FgOtTeVeScrap.this,scrapNotSuccessfullM,Toast.LENGTH_LONG).show();
                            }else {
                                String[] overViewCol={"Status"};
                                String[] overViewValue={scrappedKey};
                                cc.sqlModule(updateOperation,overViewTable,overViewCol,"PrimaryKey='"+barcode+"'",overViewValue,inventoryDatabase);
                                Toast.makeText(FgOtTeVeScrap.this, scrapSuccessfullM, Toast.LENGTH_LONG).show();
                            }
                            startActivity(new Intent(FgOtTeVeScrap.this,Home.class).putExtra(selectedLanguageKey,selectedLang));
                        }
                    }
                }).setNegativeButton(noB,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // if this button is clicked, just close
                // the dialog box and do nothing
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();// create alert dialog
        alertDialog.show();// show it
    }
    public void scrapFinishedGoods(){
        scrapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }
    public void scrapOthers(){
        scrapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }
    public void scrapTechnicalEquipment() {
        scrapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }
    public void scrapVehicle(){
        scrapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }
}

