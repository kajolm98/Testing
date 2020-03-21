package com.mtem.asset_management_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;

public class FgOtTeVeRead extends BaseActivity {
    TextView serialNo,make,model,boughtOn,equipmentType,assignTo,status,location,supplier,prize,labelView,currencyView;
    TextView   serialView,makeView,modelView,boughtView,typeView,assignView,statusView,locationView,supplierView,prizeView,currencyEdit;
    String barcode,whichRead,selectedLang;
    ImageView barcodeImage;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fg_ot_te_ve_read);

        serialNo = findViewById(R.id.serialEdit);
        make = findViewById(R.id.makeEdit);
        model = findViewById(R.id.modelEdit);
        boughtOn = findViewById(R.id.boughtEdit);
        status = findViewById(R.id.statusList);
        assignTo = findViewById(R.id.assignList);
        location = findViewById(R.id.locationList);
        equipmentType = findViewById(R.id.typeList);
        supplier = findViewById(R.id.supplierEdit);
             prize = findViewById(R.id.prizeEdit);
             currencyEdit=findViewById(R.id.currencyList);

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


            barcodeImage=findViewById(R.id.barcodeImage);

        Intent intent=getIntent();
        barcode=intent.getStringExtra(barcodeValueKey);
        selectedLang=intent.getStringExtra(selectedLanguageKey);


        setLang();

        ResultSet result=cc.sqlModule(selectOperation,overViewTable,allColumns,"PrimaryKey='"+barcode+"'",inventoryDatabase);
        try {
            while (result.next()) {
                labelView.setText("Read " + result.getString("AssetType") + " Form");
                whichRead = result.getString("AssetType").trim();
                location.setText(result.getString("Location"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(whichRead.equals("Finished Goods")){
            readFinishedGoods();
        }else if(whichRead.equals("Others")){
            readOthers();
        }else if(whichRead.equals("Technical Equipment")) {
            readTechnicalEquipment();
        }else  if(whichRead.equals("Vehicle")){
            readVehicle();
        }else
            Toast.makeText(this,invaliQRM,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void setLang(){
       try {
           serialView.setText(serialNumberH);
           makeView.setText(makeH);
           modelView.setText(modelH);
           boughtView.setText(boughtOnH);
           typeView.setText(equipmentTypeH);
           assignView.setText(assignToH);
           statusView.setText(statusH);
           locationView.setText(locationH);
           supplierView.setText(supplierH);
           prizeView.setText(prizeH);
       }catch (Exception e){
           e.printStackTrace();
        }
    }public void  readFinishedGoods(){
        ResultSet result=cc.sqlModule(selectOperation,finishedGoodsTable,allColumns,"PrimaryKey='"+barcode+"'",inventoryDatabase);
        try{
        while (result.next()){
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }public void readOthers(){
        ResultSet result=cc.sqlModule(selectOperation,othersTable,allColumns,"PrimaryKey='"+barcode+"'",inventoryDatabase);
        try{
        while (result.next()){
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void  readTechnicalEquipment(){
        ResultSet result=cc.sqlModule(selectOperation,technicalEquipmentTable,allColumns,"PrimaryKey='"+barcode+"'",inventoryDatabase);
        try{
        while (result.next()) {
            serialNo.setText(result.getString("SerialNumber"));
            make.setText(result.getString("Make"));
            model.setText(result.getString("Model"));
            supplier.setText(result.getString("Supplier"));
            prize.setText(result.getString("Price") );
            currencyEdit.setText(result.getString("Currency"));
            boughtOn.setText(result.getString("BoughtOn"));
            assignTo.setText(result.getString("AssignedTo"));
            status.setText(result.getString("Status"));
            equipmentType.setText(result.getString("EquipmentType"));
        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void  readVehicle(){
        ResultSet result=cc.sqlModule(selectOperation,vehicleTable,allColumns,"PrimaryKey='"+barcode+"'",inventoryDatabase);
        try{
        while (result.next()){
            serialNo.setText(result.getString("SerialNumber"));
            make.setText(result.getString("Make"));
            model.setText(result.getString("Model"));
            supplier.setText(result.getString("Supplier"));
            prize.setText(result.getString("Price") );
            currencyEdit.setText(result.getString("Currency"));
            boughtOn.setText(result.getString("BoughtOn"));
            assignTo.setText(result.getString("AssignedTo"));
            status.setText(result.getString("Status"));
            equipmentType.setText(result.getString("EquipmentType"));
        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

