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

public class furnitureScrap extends BaseActivity {

        Button scrapButton;
          TextView  serialView,makeView,modelView,boughtView,typeView,assignView,statusView,locationView,supplierView,prizeView,
                lengthView,widthView,heightView;
        TextView serialNo,make,model,boughtOn,equipmentType,assignTo,status,location,supplier,prize,labelView,
                lengthEdit,widthEdit,heightEdit,lengthListEdit,widthListEdit,heigthListEdit,currencyListEdit;
        String barcode,whichRead,selectedLang;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_furniture_scrap);

            scrapButton=findViewById(R.id.scrapButton);
            serialNo=findViewById(R.id.serialEdit);
            make=findViewById(R.id.makeEdit);
             model=findViewById(R.id.modelEdit);
            boughtOn=findViewById(R.id.boughtEdit);
            equipmentType=findViewById(R.id.typeList);
            assignTo=findViewById(R.id.assignList);
            status=findViewById(R.id.statusList);
            location=findViewById(R.id.locationList);
             supplier=findViewById(R.id.supplierEdit);
                 prize=findViewById(R.id.prizeEdit);
                currencyListEdit=findViewById(R.id.currencyList);
                lengthListEdit=findViewById(R.id.lengthUnitEdit);
                widthListEdit=findViewById(R.id.widthUnitEdit);
                heigthListEdit=findViewById(R.id.heightUnitEdit);

            labelView=findViewById(R.id.label);
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
            lengthEdit=findViewById(R.id.lengthEdit);
            widthEdit=findViewById(R.id.widthEdit);
            heightEdit=findViewById(R.id.heightEdit);
            lengthView=findViewById(R.id.lengthView);
            widthView=findViewById(R.id.widthView);
            heightView=findViewById(R.id.heighView);


            Intent intent=getIntent();
            barcode=intent.getStringExtra(barcodeValueKey);
            selectedLang=intent.getStringExtra(selectedLanguageKey);


            setLang();

             ResultSet result=cc.sqlModule(selectOperation,overViewTable,allColumns,"PrimaryKey='"+barcode+"'",inventoryDatabase);
             try {
                 while (result.next()) {
                     labelView.setText("Scrap " + result.getString("AssetType") + " Form");
                     whichRead = result.getString("AssetType");
                     location.setText(result.getString("Location"));
                 }
             }catch (Exception e) {
                 e.printStackTrace();
             }
            if(whichRead.equals("Furniture")){
                try{
                ResultSet result1=cc.sqlModule(selectOperation,furnitureTable,allColumns,"PrimaryKey='"+barcode+"'",inventoryDatabase);
                while (result1.next()){
                    serialNo.setText(result1.getString("SerialNumber"));
                    make.setText(result1.getString("Make"));
                    model.setText(result1.getString("Model"));
                    supplier.setText(result1.getString("Supplier"));
                    prize.setText(result1.getString("Price") );
                    currencyListEdit.setText(result1.getString("Currency"));
                    boughtOn.setText(result1.getString("BoughtOn"));
                    assignTo.setText(result1.getString("AssignedTo"));
                    status.setText(result1.getString("Status"));
                    equipmentType.setText(result1.getString("EquipmentType"));
                    lengthEdit.setText(result1.getString("Length") );
                    widthEdit.setText(result1.getString("Width") );
                    heightEdit.setText(result1.getString("Height") );
                    lengthListEdit.setText(result1.getString("LengthUnit"));
                    widthListEdit.setText(result1.getString("WidthUnit"));
                    heigthListEdit.setText(result1.getString("HeightUnit"));
                }
                scrapFurniture();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }else
                Toast.makeText(this,invaliQRM,Toast.LENGTH_LONG).show();
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
                lengthView.setHint(lengthH);
                widthView.setHint(widthH);
                heightView.setHint(heightH);
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
                        int result=cc.sqlModule(updateOperation,furnitureTable,communicationCol,"PrimaryKey='"+barcode+"'",communicationValue,inventoryDatabase);
                        if(result<=0){
                            Toast.makeText(furnitureScrap.this, scrapNotSuccessfullM, Toast.LENGTH_LONG).show();
                        } else {
                            String[] overViewCol={"Status"};
                            String[] overViewValue={scrappedKey};
                            cc.sqlModule(updateOperation,overViewTable,overViewCol,"PrimaryKey='"+barcode+"'",overViewValue,inventoryDatabase);
                            Toast.makeText(furnitureScrap.this, scrapSuccessfullM, Toast.LENGTH_LONG).show();
                            startActivity(new Intent(furnitureScrap.this, Home.class).putExtra(selectedLanguageKey, selectedLang));
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
        public void scrapFurniture(){
            scrapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        showDialog(furnitureScrap.this,warningH,scrapW);
                        //db.scrapToOverView( barcode);
                }
            });
        }
    }

