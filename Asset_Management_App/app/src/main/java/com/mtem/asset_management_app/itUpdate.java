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

public class itUpdate extends BaseActivity {

        Button updateButton;
     Spinner statusList,assignToList,locationList,equipmentTypeList,currencyList;
      TextView  labelView, typeView, assignView, statusView, locationView;
    TextView  serialView,makeView, macIdView, modelView, boughtView,supplierView, prizeView;
    EditText serialNumberEdit,makeEdit,macIdEdit,modelEdit,boughtOn,supplierEdit,prizeEdit;
        String barcode,whichUpdate,selectedLang;
    LinearLayout assignAssetView, assignAssetView1, assignAssetView2;
        Context context;
        DatePickerDialog picker;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            try {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_it_update);

                serialNumberEdit = findViewById(R.id.serialEdit);
                makeEdit = findViewById(R.id.makeEdit);
                macIdEdit = findViewById(R.id.macIDEdit);
                modelEdit = findViewById(R.id.modelEdit);
                boughtOn = findViewById(R.id.boughtEdit);
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
                supplierView = findViewById(R.id.supplierView);
                prizeView = findViewById(R.id.prizeView);
                prizeEdit.addTextChangedListener(new NumberTextWatcherWithSeperator(prizeEdit));
                context=itUpdate.this;

                updateButton = findViewById(R.id.updateButton);
                 boughtOn.setInputType(InputType.TYPE_NULL);
                boughtOn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar cal = Calendar.getInstance();
                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        int month = cal.get(Calendar.MONTH);
                        int year = cal.get(Calendar.YEAR);
                        picker = new DatePickerDialog(itUpdate.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        boughtOn.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                    }
                                }, year, month, day);
                        picker.show();
                        picker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                        picker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
                    }
                });

                Intent intent = getIntent();
                barcode = intent.getStringExtra(barcodeValueKey);
                selectedLang = intent.getStringExtra(selectedLanguageKey);

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

                ResultSet result1 = cc.sqlModule(selectOperation, overViewTable, allColumns, "PrimaryKey='" + barcode+"'", inventoryDatabase);
                try {
                    while (result1.next()) {
                        labelView.setText("Update " + result1.getString("AssetType") + " Form");
                        whichUpdate = result1.getString("AssetType");
                        location.set(0, "-" + result1.getString("Location") + "-");
                    }
                }catch (Exception e){
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                if (whichUpdate.equals("IT_Equipment")) {
                    ArrayList<String> equipmentTypes = retriveDropDown("EquipmentTypeITE","DropDown_IMP",inventoryDatabase);
                    ArrayAdapter<String> equipTypes = new ArrayAdapter<>(this, R.layout.spinner_style, R.id.textview, equipmentTypes);
                    equipmentTypeList.setPrompt(equipmentTypeH);
                    equipmentTypeList.setAdapter(equipTypes);
                    ResultSet result = cc.sqlModule(selectOperation, itEquipmentTable, allColumns, "PrimaryKey='" + barcode+"'", inventoryDatabase);
                    try {
                        while (result.next()) {
                            serialNumberEdit.setText(result.getString("SerialNumber"));
                            makeEdit.setText(result.getString("Make"));
                            modelEdit.setText(result.getString("Model"));
                            supplierEdit.setText(result.getString("Supplier"));
                            prizeEdit.setText(result.getString("Price"));
                            currency.set(zero, "-" + result.getString("Currency") + "-");
                            boughtOn.setText(result.getString("BoughtOn"));
                            assignTo.set(zero, "-" + result.getString("AssignedTo") + "-");
                            status.set(zero, "-" + result.getString("Status") + "-");
                            equipmentTypes.set(zero, "-" + result.getString("EquipmentType") + "-");
                            macIdEdit.setText(result.getString("MACID"));
                        }
                        updateITEquipmentAsset();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.allOption2) {
            showDialogToHome(itUpdate.this,warningH,saveW);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        showDialog(itUpdate.this,warningH,saveW);
    }
    public void showDialogToHome(Context context,String title,String msg){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context,R.style.yourDialog);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg).setCancelable(false)
                .setPositiveButton(yesB,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        startActivity(new Intent(itUpdate.this,Home.class).putExtra(selectedLanguageKey,selectedLanguage));
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
                        itUpdate.this.finish();
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
                macIdView.setHint(macH);
                supplierView.setHint(supplierH);
                prizeView.setHint(prizeH);
                updateButton.setText(updateB);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void updateITEquipmentAsset(){
            try {
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (boughtOn.getText().toString().isEmpty() || macIdEdit.getText().toString().isEmpty() || prizeEdit.getText().toString().isEmpty()
                                || equipmentTypeList.getSelectedItem().toString().equals("-select-") || assignToList.getSelectedItem().toString().equals("-select-")
                                || statusList.getSelectedItem().toString().equals("-select-") || locationList.getSelectedItem().toString().equals("-select-")
                                || currencyList.getSelectedItem().toString().equals("--")) {
                            Toast.makeText(itUpdate.this, mandatoryFieldM, Toast.LENGTH_SHORT).show();
                        } else {
                            String[] itEquipmentCol={"SerialNumber","Make","Model","Supplier","Price","Currency","BoughtOn","AssignedTo",
                                    "Status","EquipmentType","MACID"};
                            String[] itEquipmentValues={serialNumberEdit.getText().toString(), makeEdit.getText().toString(),
                                    modelEdit.getText().toString(), supplierEdit.getText().toString(), prizeEdit.getText().toString(),
                                    currencyList.getSelectedItem().toString().replace("-","").trim(),
                                    boughtOn.getText().toString(), assignToList.getSelectedItem().toString().replace("-","").trim(),
                                    statusList.getSelectedItem().toString().replace("-","").trim(),
                                    equipmentTypeList.getSelectedItem().toString().replace("-","").trim(), macIdEdit.getText().toString()};
                            int result = cc.sqlModule(updateOperation,itEquipmentTable,itEquipmentCol,"PrimaryKey='"+barcode+"'",itEquipmentValues,inventoryDatabase);
                            if (result >0) {
                                String[] overViewCol = {"AssignedTo", "EquipmentType", "Status", "Location"};
                                String[] overViewValues = {assignToList.getSelectedItem().toString().replace("-","").trim(),
                                        equipmentTypeList.getSelectedItem().toString().replace("-","").trim(),
                                        statusList.getSelectedItem().toString().replace("-","").trim(),
                                        locationList.getSelectedItem().toString().replace("-","").trim()};
                                cc.sqlModule(updateOperation,overViewTable,overViewCol,"PrimaryKey='"+barcode+"'",overViewValues,inventoryDatabase);
                                Toast.makeText(itUpdate.this, updateSuccessfullM, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(itUpdate.this, Home.class).putExtra(selectedLanguageKey, selectedLang));
                            } else
                                Toast.makeText(itUpdate.this, updateNotSuccessfullM, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            catch (Exception e){
                Toast.makeText(itUpdate.this,somethingWentWrongM, Toast.LENGTH_SHORT).show();
            }
        }
    }
