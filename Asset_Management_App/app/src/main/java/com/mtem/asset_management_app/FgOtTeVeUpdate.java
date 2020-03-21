package com.mtem.asset_management_app;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;

public class FgOtTeVeUpdate extends BaseActivity {
    Button updateButton;
     Spinner statusList,assignToList,locationList,equipmentTypeList,currencyList;
    TextView  typeView,assignView,statusView,locationView,labelView,currencyView;
    TextView serialView,makeView,modelView,boughtView,supplierView,prizeView;
    EditText serialNumberEdit,makeEdit,modelEdit,boughtOn,supplierEdit,prizeEdit;
    String barcode,whichUpdate,selectedLang;
    DatePickerDialog picker;
    LinearLayout assignAssetView, assignAssetView1, assignAssetView2;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fg_ot_te_ve_update);

        serialNumberEdit = findViewById(R.id.serialEdit);
        makeEdit = findViewById(R.id.makeEdit);
        modelEdit = findViewById(R.id.modelEdit);
        boughtOn = findViewById(R.id.boughtEdit);
        updateButton = findViewById(R.id.updateButton);
        statusList = findViewById(R.id.statusList);
        assignToList = findViewById(R.id.assignList);
        locationList = findViewById(R.id.locationList);
        equipmentTypeList = findViewById(R.id.typeList);
        supplierEdit = findViewById(R.id.supplierEdit);
             prizeEdit = findViewById(R.id.prizeEdit);

            assignAssetView = findViewById(R.id.assignAssetView);
            assignAssetView1 = findViewById(R.id.assignAssetView1);
            assignAssetView2 = findViewById(R.id.assignAssetView2);
            assignAssetView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent ev) {
                    hideKeyboard(view);
                    return false;
                }
            });
            assignAssetView1.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent ev) {
                    hideKeyboard(view);
                    return false;
                }
            });
            assignAssetView2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent ev) {
                    hideKeyboard(view);
                    return false;
                }
            });
         currencyList = findViewById(R.id.currencyList);

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

            context=FgOtTeVeUpdate.this;
        prizeEdit.addTextChangedListener(new NumberTextWatcherWithSeperator(prizeEdit));

        labelView=findViewById(R.id. label);
         updateButton=findViewById(R.id.updateButton);


            boughtOn.setInputType(InputType.TYPE_NULL);
        boughtOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);
                picker = new DatePickerDialog(FgOtTeVeUpdate.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                boughtOn.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
                picker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                picker. getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
            }
        });
        Intent intent = getIntent();
        barcode = intent.getStringExtra("barcodeValue");
        selectedLang = intent.getStringExtra("selectedLang");

            ArrayList<String> assignTo =retriveDropDown("Employee_name","Login_Table",genericDatabase);
            ArrayAdapter<String> assign = new ArrayAdapter<>(this,R.layout.spinner_style,R.id.textview, assignTo);
            assignToList.setPrompt(assignToH);
            assignToList.setAdapter(assign);

            ArrayList<String> status =retriveDropDown("Status","DropDown_IMP",inventoryDatabase);
            ArrayAdapter<String> statusL = new ArrayAdapter<>(this,R.layout.spinner_style,R.id.textview, status);
            statusList.setPrompt(statusH);
            statusList.setAdapter(statusL);

            ArrayList<String> location =  retriveDropDown("Location","DropDown_IMP",inventoryDatabase);
            ArrayAdapter<String> locationL = new ArrayAdapter<>(this,R.layout.spinner_style,R.id.textview, location);
            locationList.setPrompt(locationH);
            locationList.setAdapter(locationL);

            ArrayList<String> currency = retriveDropDown("Currency","DropDown_IMP",inventoryDatabase);
            ArrayAdapter<String> currencyL = new ArrayAdapter<>(this,R.layout.spinner_style,R.id.textview, currency);
            currencyList.setPrompt(currencyH);
            currencyList.setAdapter(currencyL);

        setLang();
        try{
        ResultSet result=cc.sqlModule(selectOperation,overViewTable,allColumns,"PrimaryKey='"+barcode+"'",inventoryDatabase);
        while (result.next()){
            labelView.setText("Update "+result.getString("AssetType")+" Form");
            whichUpdate=result.getString("AssetType");
            location.set(0,"-"+result.getString("Location")+"-");
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        if( whichUpdate.equals("Finished Goods")){
            ArrayList<String> equipmentTypes = retriveDropDown("EquipmentTypeFG","DropDown_IMP",inventoryDatabase);
            ArrayAdapter<String> equipTypes=new ArrayAdapter<>(this,R.layout.spinner_style,R.id.textview,equipmentTypes);
            equipmentTypeList.setPrompt(equipmentTypeH);
            equipmentTypeList.setAdapter(equipTypes);
            ResultSet result1=cc.sqlModule(selectOperation,finishedGoodsTable,allColumns,"PrimaryKey='"+barcode+"'",inventoryDatabase);
            try{
            while (result1.next()) {
                serialNumberEdit.setText(result1.getString("SerialNumber"));
                makeEdit.setText(result1.getString("Make"));
                modelEdit.setText(result1.getString("Model"));
                supplierEdit.setText(result1.getString("Supplier"));
                prizeEdit.setText(result1.getString("Price"));
                boughtOn.setText(result1.getString("BoughtOn"));
                currency.set(0,"-"+result1.getString("Currency")+"-");
                assignTo.set(0,"-"+result1.getString("AssignedTo")+"-");
                status.set(0,"-"+result1.getString("Status")+"-");
                equipmentTypes.set(0,"-"+result1.getString("EquipmentType")+"-");
            }
            updateFinishedGoodsAsset();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(whichUpdate.equals("Others")){
            ArrayList<String> equipmentTypes = retriveDropDown("EquipmentTypeOthers","DropDown_IMP",inventoryDatabase);
            ArrayAdapter<String> equipTypes=new ArrayAdapter<>(this,R.layout.spinner_style,R.id.textview,equipmentTypes);
            equipmentTypeList.setPrompt(equipmentTypeH);
            equipmentTypeList.setAdapter(equipTypes);
            ResultSet result1=cc.sqlModule(selectOperation,othersTable,allColumns,"PrimaryKey='"+barcode+"'",inventoryDatabase);
            try{
            while (result1.next()) {
                serialNumberEdit.setText(result1.getString("SerialNumber"));
                makeEdit.setText(result1.getString("Make"));
                modelEdit.setText(result1.getString("Model"));
                supplierEdit.setText(result1.getString("Supplier"));
                prizeEdit.setText(result1.getString("Price"));
                boughtOn.setText(result1.getString("BoughtOn"));
                currency.set(0,"-"+result1.getString("Currency")+"-");
                assignTo.set(0,"-"+result1.getString("AssignedTo")+"-");
                status.set(0,"-"+result1.getString("Status")+"-");
                equipmentTypes.set(0,"-"+result1.getString("EquipmentType")+"-");
            }
            updateOthersAsset();
            }catch (Exception e){
                e.printStackTrace();
            }
        } else if(whichUpdate.equals("Technical Equipment")){
            ArrayList<String> equipmentTypes = retriveDropDown("EquipmentTypeTE","DropDown_IMP",inventoryDatabase);
            ArrayAdapter<String> equipTypes=new ArrayAdapter<>(this,R.layout.spinner_style,R.id.textview,equipmentTypes);
            equipmentTypeList.setPrompt(equipmentTypeH);
            equipmentTypeList.setAdapter(equipTypes);
            ResultSet result1=cc.sqlModule(selectOperation,technicalEquipmentTable,allColumns,"PrimaryKey='"+barcode+"'",inventoryDatabase);
            try{
            while (result1.next()) {
                serialNumberEdit.setText(result1.getString("SerialNumber"));
                makeEdit.setText(result1.getString("Make"));
                modelEdit.setText(result1.getString("Model"));
                supplierEdit.setText(result1.getString("Supplier"));
                prizeEdit.setText(result1.getString("Price"));
                boughtOn.setText(result1.getString("BoughtOn"));
                currency.set(zero,"-"+result1.getString("Currency")+"-");
                assignTo.set(zero,"-"+result1.getString("AssignedTo")+"-");
                status.set(zero,"-"+result1.getString("Status")+"-");
                equipmentTypes.set(zero,"-"+result1.getString("EquipmentType")+"-");
            }
            updateTechnicalEquipmentAsset();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else  if(whichUpdate.equals("Vehicle")) {
            ArrayList<String> equipmentTypes = retriveDropDown("EquipmentTypeVehicles","DropDown_IMP",inventoryDatabase);
            ArrayAdapter<String> equipTypes = new ArrayAdapter<>(this, R.layout.spinner_style, R.id.textview, equipmentTypes);
            equipmentTypeList.setPrompt(equipmentTypeH);
            equipmentTypeList.setAdapter(equipTypes);
            ResultSet result1 = cc.sqlModule(selectOperation,vehicleTable,allColumns,"PrimaryKey='"+barcode+"'",inventoryDatabase);
            try{
            while (result1.next()) {
                serialNumberEdit.setText(result1.getString("SerialNumber"));
                makeEdit.setText(result1.getString("Make"));
                modelEdit.setText(result1.getString("Model"));
                supplierEdit.setText(result1.getString("Supplier"));
                prizeEdit.setText(result1.getString("Price"));
                currency.set(0,"-"+result1.getString("Currency")+"-");
                boughtOn.setText(result1.getString("BoughtOn"));
                assignTo.set(0,"-"+result1.getString("AssignedTo")+"-");
                status.set(0,"-"+result1.getString("Status")+"-");
                equipmentTypes.set(0,"-"+result1.getString("EquipmentType")+"-");
            }
            updateVehicleAsset();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else
            Toast.makeText(this,invaliQRM,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.allOption2) {
            showDialogToHome(FgOtTeVeUpdate.this,warningH,saveW);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        showDialog(FgOtTeVeUpdate.this,warningH,saveW);
    }
    public void showDialogToHome(Context context,String title,String msg){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context,R.style.yourDialog);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg).setCancelable(false)
                .setPositiveButton(yesB,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        startActivity(new Intent(FgOtTeVeUpdate.this,Home.class).putExtra(selectedLanguageKey,selectedLanguage));
                    }
                })
                .setNegativeButton(noB,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void showDialog(Context context,String title,String msg){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context,R.style.yourDialog);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg).setCancelable(false)
                .setPositiveButton(yesB,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        FgOtTeVeUpdate.this.finish();
                    }
                })
                .setNegativeButton(noB,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
            updateButton.setText(updateB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateFinishedGoodsAsset(){
            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                    if (boughtOn.getText().toString().isEmpty() || prizeEdit.getText().toString().isEmpty()
                            || equipmentTypeList.getSelectedItem().toString().equals("-select-") || assignToList.getSelectedItem().toString().equals("-select-")
                            || statusList.getSelectedItem().toString().equals("-select-") || locationList.getSelectedItem().toString().equals("-select-")
                            || currencyList.getSelectedItem().toString().equals("-select-")) {
                        Toast.makeText(FgOtTeVeUpdate.this, mandatoryFieldM, Toast.LENGTH_SHORT).show();
                    } else {
                        String[] fgOtTeCol={"SerialNumber","Make","Model","Supplier","Price","Currency","BoughtOn","AssignedTo",
                                "Status","EquipmentType"};
                        String[] fgOtTeValues={serialNumberEdit.getText().toString(),
                                makeEdit.getText().toString(),
                                modelEdit.getText().toString(),
                                supplierEdit.getText().toString(),
                                prizeEdit.getText().toString(),
                                currencyList.getSelectedItem().toString().replace("-","").trim(),
                                boughtOn.getText().toString(),
                                assignToList.getSelectedItem().toString().replace("-","").trim(),
                                statusList.getSelectedItem().toString().replace("-","").trim(),
                                equipmentTypeList.getSelectedItem().toString().replace("-","").trim()};
                        int result = cc.sqlModule(updateOperation,finishedGoodsTable,fgOtTeCol,"PrimaryKey='"+barcode+"'",fgOtTeValues,inventoryDatabase);
                        if (result >0) {
                            String[] overViewCol = {"AssignedTo", "EquipmentType", "Status", "Location"};
                            String[] overViewValues = {assignToList.getSelectedItem().toString().replace("-","").trim(),
                                    equipmentTypeList.getSelectedItem().toString().replace("-","").trim(),
                                    statusList.getSelectedItem().toString().replace("-","").trim(),
                                    locationList.getSelectedItem().toString().replace("-","").trim()};
                            cc.sqlModule(updateOperation,overViewTable,overViewCol,"PrimaryKey='"+barcode+"'",overViewValues,inventoryDatabase);
                            Toast.makeText(FgOtTeVeUpdate.this, updateSuccessfullM, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(FgOtTeVeUpdate.this, Home.class).putExtra(selectedLanguageKey, selectedLang));
                        } else
                            Toast.makeText(FgOtTeVeUpdate.this, updateNotSuccessfullM, Toast.LENGTH_SHORT).show();
                    }
                    }
                    catch (Exception e){
                        Toast.makeText( FgOtTeVeUpdate.this, somethingWentWrongM, Toast.LENGTH_SHORT).show();
                    } }
            });
    }
    public void updateOthersAsset(){
             updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                    if (boughtOn.getText().toString().isEmpty() || prizeEdit.getText().toString().isEmpty()
                            || equipmentTypeList.getSelectedItem().toString().equals("-select-") || assignToList.getSelectedItem().toString().equals("-select-")
                            || statusList.getSelectedItem().toString().equals("-select-") || locationList.getSelectedItem().toString().equals("-select-")
                            || currencyList.getSelectedItem().toString().equals("-select-")) {
                        Toast.makeText(FgOtTeVeUpdate.this, mandatoryFieldM, Toast.LENGTH_SHORT).show();
                    } else {
                        String[] fgOtTeCol={"SerialNumber","Make","Model","Supplier","Price","Currency","BoughtOn","AssignedTo",
                                "Status","EquipmentType"};
                        String[] fgOtTeValues={serialNumberEdit.getText().toString(), makeEdit.getText().toString(),
                                modelEdit.getText().toString(), supplierEdit.getText().toString(),
                                prizeEdit.getText().toString(),
                                currencyList.getSelectedItem().toString().replace("-","").trim(),
                                boughtOn.getText().toString(),
                                assignToList.getSelectedItem().toString().replace("-","").trim(),
                                statusList.getSelectedItem().toString().replace("-","").trim(),
                                equipmentTypeList.getSelectedItem().toString().replace("-","").trim()};
                        int result = cc.sqlModule(updateOperation,othersTable,fgOtTeCol,"PrimaryKey='"+barcode+"'",fgOtTeValues,inventoryDatabase);
                        if (result >zero) {
                            String[] overViewCol = {"AssignedTo", "EquipmentType", "Status", "Location"};
                            String[] overViewValues = {assignToList.getSelectedItem().toString().replace("-","").trim(),
                                    equipmentTypeList.getSelectedItem().toString().replace("-","").trim(),
                                    statusList.getSelectedItem().toString().replace("-","").trim(),
                                    locationList.getSelectedItem().toString().replace("-","").trim()};
                            cc.sqlModule(updateOperation,overViewTable,overViewCol,"PrimaryKey='"+barcode+"'",overViewValues,inventoryDatabase);
                            Toast.makeText(FgOtTeVeUpdate.this, updateSuccessfullM, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(FgOtTeVeUpdate.this, Home.class).putExtra(selectedLanguageKey, selectedLang));
                        } else
                            Toast.makeText(FgOtTeVeUpdate.this, updateNotSuccessfullM, Toast.LENGTH_SHORT).show();
                    }
                    }
                    catch (Exception e){
                        Toast.makeText( FgOtTeVeUpdate.this, somethingWentWrongM, Toast.LENGTH_SHORT).show();
                    } }
             });
    }
    public void updateTechnicalEquipmentAsset(){
             updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                    if (boughtOn.getText().toString().isEmpty() || prizeEdit.getText().toString().isEmpty()
                            || equipmentTypeList.getSelectedItem().toString().equals("-select-") || assignToList.getSelectedItem().toString().equals("-select-")
                            || statusList.getSelectedItem().toString().equals("-select-") || locationList.getSelectedItem().toString().equals("-select-")
                            || currencyList.getSelectedItem().toString().equals("-select-")) {
                        Toast.makeText(FgOtTeVeUpdate.this, "Red Underlined Fields Are Mandatory", Toast.LENGTH_SHORT).show();
                    } else {
                        String[] fgOtTeCol={"SerialNumber","Make","Model","Supplier","Price","Currency","BoughtOn","AssignedTo",
                                "Status","EquipmentType"};
                        String[] fgOtTeValues={serialNumberEdit.getText().toString(), makeEdit.getText().toString(),
                                modelEdit.getText().toString(), supplierEdit.getText().toString(),
                                prizeEdit.getText().toString(),
                                currencyList.getSelectedItem().toString().replace("-","").trim(),
                                boughtOn.getText().toString(),
                                assignToList.getSelectedItem().toString().replace("-","").trim(),
                                statusList.getSelectedItem().toString().replace("-","").trim(),
                                equipmentTypeList.getSelectedItem().toString().replace("-","").trim()};
                        int result = cc.sqlModule(updateOperation,technicalEquipmentTable,fgOtTeCol,"PrimaryKey='"+barcode+"'",fgOtTeValues,inventoryDatabase);
                        if (result >0) {
                            String[] overViewCol = {"AssignedTo", "EquipmentType", "Status", "Location"};
                            String[] overViewValues = {assignToList.getSelectedItem().toString().replace("-","").trim(),
                                    equipmentTypeList.getSelectedItem().toString().replace("-","").trim(),
                                    statusList.getSelectedItem().toString().replace("-","").trim(),
                                    locationList.getSelectedItem().toString().replace("-","").trim()};
                            cc.sqlModule(updateOperation,overViewTable,overViewCol,"PrimaryKey='"+barcode+"'",overViewValues,inventoryDatabase);
                            Toast.makeText(FgOtTeVeUpdate.this, updateSuccessfullM, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(FgOtTeVeUpdate.this, Home.class).putExtra(selectedLanguageKey, selectedLang));
                        } else
                            Toast.makeText(FgOtTeVeUpdate.this, updateNotSuccessfullM, Toast.LENGTH_SHORT).show();
                    }
                    }
                    catch (Exception e){
                        Toast.makeText( FgOtTeVeUpdate.this, somethingWentWrongM, Toast.LENGTH_SHORT).show();
                    } }
             });
    }
    public void updateVehicleAsset(){
             updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                    if (boughtOn.getText().toString().isEmpty() || prizeEdit.getText().toString().isEmpty()
                            || equipmentTypeList.getSelectedItem().toString().equals("-select-") || assignToList.getSelectedItem().toString().equals("-select-")
                            || statusList.getSelectedItem().toString().equals("-select-") || locationList.getSelectedItem().toString().equals("-select-")
                            || currencyList.getSelectedItem().toString().equals("-select-")) {
                        Toast.makeText(FgOtTeVeUpdate.this, mandatoryFieldM, Toast.LENGTH_SHORT).show();
                    } else {
                        String[] vehicleCol={"SerialNumber","Make","Model","Supplier","Price","BoughtOn","AssignedTo",
                                "Status","EquipmentType","Currency"};
                        String[] vehicleValues={serialNumberEdit.getText().toString(), makeEdit.getText().toString(),
                                modelEdit.getText().toString(), supplierEdit.getText().toString(), prizeEdit.getText().toString(),
                                boughtOn.getText().toString(),
                                assignToList.getSelectedItem().toString().replace("-","").trim(),
                                statusList.getSelectedItem().toString().replace("-","").trim(),
                                equipmentTypeList.getSelectedItem().toString().replace("-","").trim(),
                                currencyList.getSelectedItem().toString().replace("-","").trim(),};
                        int result = cc.sqlModule(updateOperation,vehicleTable,vehicleCol,"PrimaryKey='"+barcode+"'",vehicleValues,inventoryDatabase);
                        if (result >0) {
                            String[] overViewCol = {"AssignedTo", "EquipmentType", "Status", "Location"};
                            String[] overViewValues = {assignToList.getSelectedItem().toString().replace("-","").trim(),
                                    equipmentTypeList.getSelectedItem().toString().replace("-","").trim(),
                                    statusList.getSelectedItem().toString().replace("-","").trim(),
                                    locationList.getSelectedItem().toString().replace("-","").trim()};
                            cc.sqlModule(updateOperation,overViewTable,overViewCol,"PrimaryKey='"+barcode+"'",overViewValues,inventoryDatabase);
                            Toast.makeText(FgOtTeVeUpdate.this, updateSuccessfullM, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(FgOtTeVeUpdate.this, Home.class).putExtra(selectedLanguageKey, selectedLang));
                        } else
                            Toast.makeText(FgOtTeVeUpdate.this, updateNotSuccessfullM, Toast.LENGTH_SHORT).show();
                    }

        }
        catch (Exception e){
            Toast.makeText( FgOtTeVeUpdate.this, somethingWentWrongM, Toast.LENGTH_SHORT).show();
        } }
             });
    }
}

