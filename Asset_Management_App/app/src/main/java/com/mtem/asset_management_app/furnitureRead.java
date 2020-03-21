package com.mtem.asset_management_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;

public class furnitureRead extends BaseActivity {

        TextView serialNo,make,model,boughtOn,equipmentType,assignTo,status,location,supplier,prize,labelView,length,width,height;
        TextView   serialView,makeView,modelView,boughtView,typeView,assignView,statusView,locationView,supplierView,
                prizeView,lengthView,widthView,heightView,currencyEdit,lengthEdit,widthEdit,heigthEdit;
         String barcode,whichRead,selectedLang;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_furniture_read);

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
                currencyEdit=findViewById(R.id.currencyList);
                lengthEdit=findViewById(R.id.lengthUnitEdit);
                widthEdit=findViewById(R.id.widthUnitEdit);
                heigthEdit=findViewById(R.id.heightUnitEdit);


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
            length=findViewById(R.id.lengthEdit);
            width=findViewById(R.id.widthEdit);
            height=findViewById(R.id.heightEdit);
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
                     labelView.setText("Read " + result.getString("AssetType") + " Form");
                     whichRead = result.getString("AssetType");
                     location.setText(result.getString("Location"));
                 }
             }catch (Exception e){
                 e.printStackTrace();
             }
            if(whichRead.equals("Furniture")){
                readFurniture();
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
                lengthView.setHint(lengthH);
                widthView.setHint(widthH);
                heightView.setHint(heightH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void  readFurniture(){
            ResultSet result=cc.sqlModule(selectOperation,furnitureTable,allColumns,"PrimaryKey='"+barcode+"'",inventoryDatabase);
            try {
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
                    length.setText(result.getString("Length"));
                    lengthEdit.setText(result.getString("LengthUnit"));
                    width.setText(result.getString("Width"));
                    widthEdit.setText(result.getString("WidthUnit"));
                    height.setText(result.getString("Height"));
                    heigthEdit.setText(result.getString("HeightUnit"));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
