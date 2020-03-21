package com.mtem.asset_management_app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.sql.ResultSet;

public class itScrap extends BaseActivity {

        Button scrapButton;
          TextView  serialView,makeView,macIdView,modelView,boughtView,typeView,assignView,statusView,locationView,imeiView,supplierView,prizeView;
        TextView serialNo,make,macID,model,boughtOn,equipmentType,assignTo,status,location,imeiNumber,supplier,prize,labelView,currencyList;
        String barcode,whichRead,selectedLang;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            try {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_it_scrap);

                scrapButton = findViewById(R.id.scrapButton);
                serialNo = findViewById(R.id.serialEdit);
                make = findViewById(R.id.makeEdit);
                macID = findViewById(R.id.macIDEdit);
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
                macIdView = findViewById(R.id.macIDView);
                modelView = findViewById(R.id.modelView);
                boughtView = findViewById(R.id.boughtView);
                typeView = findViewById(R.id.typeView);
                assignView = findViewById(R.id.assignView);
                statusView = findViewById(R.id.statusView);
                locationView = findViewById(R.id.locationView);
                imeiView = findViewById(R.id.imeiView);
                supplierView = findViewById(R.id.supplierView);
                prizeView = findViewById(R.id.prizeView);


                Intent intent = getIntent();
                barcode = intent.getStringExtra(barcodeValueKey);
                selectedLang = intent.getStringExtra(selectedLanguageKey);


                setLang();

                ResultSet result = cc.sqlModule(selectOperation, overViewTable, allColumns, "PrimaryKey='" + barcode+"'", inventoryDatabase);
                try {
                    while (result.next()) {
                        labelView.setText("Scrap " + result.getString("AssetType") + " Form");
                        whichRead = result.getString("AssetType");
                        location.setText(result.getString("Location"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (whichRead.equals("IT_Equipment")) {
                    ResultSet result1 = cc.sqlModule(selectOperation, itEquipmentTable, allColumns, "PrimaryKey='" + barcode+"'", inventoryDatabase);
                    try {
                        while (result1.next()) {
                            serialNo.setText(result1.getString("SerialNumber"));
                            make.setText(result1.getString("Make"));
                            model.setText(result1.getString("Model"));
                            supplier.setText(result1.getString("Supplier"));
                            prize.setText(result1.getString("Price"));
                            currencyList.setText(result1.getString("Currency"));
                            boughtOn.setText(result1.getString("BoughtOn"));
                            assignTo.setText(result1.getString("AssignedTo"));
                            status.setText(result1.getString("Status"));
                            equipmentType.setText(result1.getString("EquipmentType"));
                            macID.setText(result1.getString("MACID"));
                        }
                        scrapITEquipment();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    Toast.makeText(this, invaliQRM, Toast.LENGTH_LONG).show();
            }catch (Exception e){
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
                macIdView.setHint(macH);
                supplierView.setHint(supplierH);
                prizeView.setHint(prizeH);
                scrapButton.setText(scrapB);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    public void showDialog(Context context,String title,String msg){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context,R.style.yourDialog);
        // set title
        alertDialogBuilder.setTitle(title);
        // set dialog message
        alertDialogBuilder.setMessage(msg).setCancelable(false)
                .setPositiveButton(yesB,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        String[] communicationCol={"Status"};
                        String[] communicationValue={scrappedKey};
                        int result=cc.sqlModule(updateOperation,itEquipmentTable,communicationCol,"PrimaryKey='"+barcode+"'",communicationValue,inventoryDatabase);
                        if(result<=0){
                            Toast.makeText(itScrap.this,scrapNotSuccessfullM,Toast.LENGTH_LONG).show();
                        }else{
                            String[] overViewCol={"Status"};
                            String[] overViewValue={scrappedKey};
                            cc.sqlModule(updateOperation,overViewTable,overViewCol,"PrimaryKey='"+barcode+"'",overViewValue,inventoryDatabase);
                            Toast.makeText(itScrap.this,scrapSuccessfullM,Toast.LENGTH_LONG).show();
                            startActivity(new Intent(itScrap.this,Home.class).putExtra(selectedLanguageKey,selectedLang));
                        }
                    }
                })
                .setNegativeButton(noB,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
        public void scrapITEquipment(){
            scrapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog(itScrap.this,warningH,scrapW);
                }
            });
        }
    }
