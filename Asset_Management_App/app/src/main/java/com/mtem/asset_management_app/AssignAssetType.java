package com.mtem.asset_management_app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class AssignAssetType extends BaseActivity {

    ImageButton communicationDevices,finishedGoods,furniture,itEquipment,technicalEquipment,vehicle,others;
     String task,barcode,selectedLang,space=" ",spaced="";
    TextView barcodeView,communicationDevicesText,finishedGoodsText,furnitureText,itEquipmentText,technicalEquipmentText,vehicleText,othersText,selectAssetType;
    int index;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_asset_type);

        communicationDevices=findViewById(R.id.communicationDevices);
        finishedGoods=findViewById(R.id.finishedGoods);
        furniture=findViewById(R.id. furniture);
        itEquipment=findViewById(R.id. itEquipment);
        technicalEquipment=findViewById(R.id. technicalEquipment);
        vehicle=findViewById(R.id. vehicle);
        others=findViewById(R.id. others);
        barcodeView=findViewById(R.id.barcodeView);
         communicationDevicesText=findViewById(R.id.communicationDevicesText);
        finishedGoodsText=findViewById(R.id.finishedGoodsText);
        furnitureText=findViewById(R.id.furnitureText);
        itEquipmentText=findViewById(R.id.itEquipmentText);
        technicalEquipmentText=findViewById(R.id.technicalEquipmentText);
        vehicleText=findViewById(R.id.vehicleText);
        othersText=findViewById(R.id.othersText);
        selectAssetType=findViewById(R.id.assetManagement);

        Intent intent=getIntent();
        barcode=intent.getStringExtra(barcodeValueKey);
        selectedLang=intent.getStringExtra(selectedLanguageKey);

        changeImage();
        selectLang();
        makeBarcodeText();


        communicationDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task=communicationDevicesH;
                startActivity(new Intent(AssignAssetType.this, CommunicationAssign.class)
                        .putExtra(tasKey,task).putExtra(barcodeValueKey,barcode)
                        .putExtra(selectedLanguageKey,selectedLang));
            }
        });
        finishedGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task=finishedGoodsH;
                startActivity(new Intent(AssignAssetType.this, FgOtTeVeAssign.class)
                        .putExtra(tasKey,task).putExtra(barcodeValueKey,barcode)
                        .putExtra(selectedLanguageKey,selectedLang));
             }
        });
        furniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task=furnitureH;
                startActivity(new Intent(AssignAssetType.this, furnitureAssign.class)
                        .putExtra(tasKey,task).putExtra(barcodeValueKey,barcode)
                .putExtra(selectedLanguageKey,selectedLang));
            }
        });
        itEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task=itEquipmentH;
                startActivity(new Intent(AssignAssetType.this, itAssign.class)
                        .putExtra(tasKey,task).putExtra(barcodeValueKey,barcode)
                        .putExtra(selectedLanguageKey,selectedLang));
            }
        });
        technicalEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task=technicalEquipmentH;
                startActivity(new Intent(AssignAssetType.this, FgOtTeVeAssign.class)
                        .putExtra(tasKey,task).putExtra(barcodeValueKey,barcode)
                        .putExtra(selectedLanguageKey,selectedLang));
             }
        });
        vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task=vehicleH;
                startActivity(new Intent(AssignAssetType.this, FgOtTeVeAssign.class)
                        .putExtra(tasKey,task).putExtra(barcodeValueKey,barcode)
                        .putExtra(selectedLanguageKey,selectedLang));
            }
        });
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task=othersH;
                startActivity(new Intent(AssignAssetType.this, FgOtTeVeAssign.class)
                        .putExtra(tasKey,task).putExtra(barcodeValueKey,barcode)
                        .putExtra(selectedLanguageKey,selectedLang));
            }
        });
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }
    public void changeImage(){
        if(language==1){
            communicationDevices.setImageResource(R.drawable.blue_cd);
            finishedGoods.setImageResource(R.drawable.blue_fg);
            furniture.setImageResource(R.drawable.blue_fur);
            itEquipment.setImageResource(R.drawable.blue_ite);
            technicalEquipment.setImageResource(R.drawable.blue_te);
            vehicle.setImageResource(R.drawable.blue_vehicles);
            others.setImageResource(R.drawable.blue_others);
        }
    }
    public void selectLang(){
        try {
            selectAssetType.setText(selectAssetTypeH);
            communicationDevicesText.setText(cdH);
            finishedGoodsText.setText(fgH);
            furnitureText.setText(fuH);
            itEquipmentText.setText(iteH);
            technicalEquipmentText.setText(teH);
            vehicleText.setText(vH);
            othersText.setText(OH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void makeBarcodeText(){
        char[] ctr=barcode.toCharArray();
        for(index=zero;index<barcode.length();index++){
            if(index==barcode.length()-1){
                spaced+=ctr[index];
            }else
                spaced+=ctr[index]+space;
        }
        barcodeView.setText(spaced);
    }
}
