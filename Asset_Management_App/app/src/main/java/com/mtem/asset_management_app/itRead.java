package com.mtem.asset_management_app;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;

public class itRead extends BaseActivity {

        TextView  serialNo,make,macID,model,boughtOn,equipmentType,assignTo,status,location,supplier,prize,labelView,currencyList;
        TextView  serialView,makeView,macIdView,modelView,boughtView,typeView,assignView,statusView,locationView,supplierView,prizeView;
        String barcode,whichRead,selectedLang;
     ImageView barcodeImage;



        @Override
        public void onCreate(Bundle savedInstanceState) {
            try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_it_read);

            serialNo=findViewById(R.id.serialEdit);
            make=findViewById(R.id.makeEdit);
            macID=findViewById(R.id.macIDEdit);
            model=findViewById(R.id.modelEdit);
            boughtOn=findViewById(R.id.boughtEdit);
            equipmentType=findViewById(R.id.typeList);
            assignTo=findViewById(R.id.assignList);
            status=findViewById(R.id.statusList);
            location=findViewById(R.id.locationList);
            supplier=findViewById(R.id. supplierEdit);
                 prize=findViewById(R.id.prizeEdit);
                currencyList=findViewById(R.id.currencyList);

                labelView=findViewById(R.id.label);
            serialView = findViewById(R.id.serialView);
            makeView = findViewById(R.id.makeView);
            macIdView = findViewById(R.id.macIDView);
            modelView = findViewById(R.id.modelView);
            boughtView = findViewById(R.id.boughtView);
            typeView = findViewById(R.id.typeView);
            assignView = findViewById(R.id.assignView);
            statusView = findViewById(R.id.statusView);
            locationView = findViewById(R.id.locationView);
            supplierView = findViewById(R.id.supplierView);
            prizeView = findViewById(R.id.prizeView);

            barcodeImage=findViewById(R.id.barcodeImage);

            Intent intent=getIntent();
            barcode=intent.getStringExtra(barcodeValueKey);
            selectedLang=intent.getStringExtra(selectedLanguageKey);

            setLang();

             ResultSet result=cc.sqlModule(selectOperation,overViewTable,allColumns,"PrimaryKey='"+barcode+"'",inventoryDatabase);
             try {
                 while (result.next()) {
                     labelView.setText("Read " + result.getString("AssetType") + " Form");
                     whichRead = result.getString("AssetType");
                     location.setText(result.getString("Location"));
                 }
             }catch (Exception e){
                 e.printStackTrace();
             }
            if(whichRead.equals("IT_Equipment")){
                readITEquipment();
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
                macIdView.setHint(macH);
                supplierView.setHint(supplierH);
                prizeView.setHint(prizeH);
             } catch (Exception e) {
                e.printStackTrace();
            }
        }public void  readITEquipment() {
        ResultSet result = cc.sqlModule(selectOperation,itEquipmentTable,allColumns,"PrimaryKey='"+barcode+"'",inventoryDatabase);
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
                macID.setText(result.getString("MACID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }
