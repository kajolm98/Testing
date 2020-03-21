package com.mtem.asset_management_app;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
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

import java.util.ArrayList;
import java.util.Calendar;

public class CommunicationAssign extends BaseActivity {

    Button saveButton;
    Spinner statusList, assignToList, locationList, equipmentTypeList, currencyList;
    TextView serialView, makeView, modelView, boughtView, imeiView, supplierView, prizeView;
    TextView assetLabel, typeView, assignView, statusView, locationView;
    EditText serialNumberEdit, makeEdit, modelEdit, boughtOn, imeiEdit, supplierEdit, prizeEdit;
    String barcode, task, selectedLang;
    Context context;
    DatePickerDialog picker;
     LinearLayout assignAssetView, assignAssetView1, assignAssetView2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_communication_assign);

            serialNumberEdit = findViewById(R.id.serialEdit);
            makeEdit = findViewById(R.id.makeEdit);
            modelEdit = findViewById(R.id.modelEdit);
            boughtOn = findViewById(R.id.boughtEdit);
            saveButton = findViewById(R.id.saveButton);
            statusList = findViewById(R.id.statusList);
            assignToList = findViewById(R.id.assignList);
            locationList = findViewById(R.id.locationList);
            equipmentTypeList = findViewById(R.id.typeList);
            currencyList = findViewById(R.id.currencyList);
            imeiEdit = findViewById(R.id.imeiEdit);
            supplierEdit = findViewById(R.id.supplierEdit);
            prizeEdit = findViewById(R.id.prizeEdit);
             context = CommunicationAssign.this;

            assignAssetView = findViewById(R.id.assignAssetView);
            assignAssetView1 = findViewById(R.id.assignAssetView1);
            assignAssetView2 = findViewById(R.id.assignAssetView2);

            assetLabel = findViewById(R.id.label);
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
            prizeEdit.addTextChangedListener(new NumberTextWatcherWithSeperator(prizeEdit));

            Intent intent = getIntent();
             barcode = intent.getStringExtra(barcodeValueKey);
            task = intent.getStringExtra(taskKey);
            selectedLang = intent.getStringExtra(selectedLanguageKey);

            boughtOn.setInputType(InputType.TYPE_NULL);
            boughtOn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar cal = Calendar.getInstance();
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int month = cal.get(Calendar.MONTH);
                    int year = cal.get(Calendar.YEAR);
                    picker = new DatePickerDialog(CommunicationAssign.this,
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

            ArrayList<String> assignTo = retriveDropDown("Employee_name", "Login_Table", genericDatabase);
            ArrayAdapter<String> assign = new ArrayAdapter<>(this, R.layout.spinner_style, R.id.textview, assignTo);
            assignToList.setPrompt(assignToH);
            assignToList.setAdapter(assign);

            ArrayList<String> status = retriveDropDown("Status", "DropDown_IMP", inventoryDatabase);
            ArrayAdapter<String> statusL = new ArrayAdapter<>(this, R.layout.spinner_style, R.id.textview, status);
            statusList.setPrompt(statusH);
            statusList.setAdapter(statusL);

            ArrayList<String> location = retriveDropDown("Location", "DropDown_IMP", inventoryDatabase);
            ArrayAdapter<String> locationL = new ArrayAdapter<>(this, R.layout.spinner_style, R.id.textview, location);
            locationList.setPrompt(locationH);
            locationList.setAdapter(locationL);

            ArrayList<String> currency = retriveDropDown("Currency", "DropDown_IMP", inventoryDatabase);
            ArrayAdapter<String> currencyL = new ArrayAdapter<>(this, R.layout.spinner_style, R.id.textview, currency);
            currencyList.setPrompt(currencyH);
            currencyList.setAdapter(currencyL);

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


            setLang();

            if (task.equals(communicationDevicesH)) {
                assetLabel.setText("Communication Devices Assign Form");
                ArrayList<String> equipmentTypes = retriveDropDown("EquipmentTypeCD", "DropDown_IMP", inventoryDatabase);
                ArrayAdapter<String> equipTypes = new ArrayAdapter<>(this, R.layout.spinner_style, R.id.textview, equipmentTypes);
                equipmentTypeList.setPrompt(equipmentTypeH);
                equipmentTypeList.setAdapter(equipTypes);
                saveCommunicationDeviceAsset();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_all, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.allOption2) {
            showDialogToHome(CommunicationAssign.this, Login.warningH, saveW);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showDialog(CommunicationAssign.this, Login.warningH, saveW);
    }

    public void showDialogToHome(Context context, String title, String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.yourDialog);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg).setCancelable(false)
                .setPositiveButton(Login.yesB, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(CommunicationAssign.this, Home.class).putExtra(selectedLanguageKey, selectedLanguage));
                    }
                })
                .setNegativeButton(Login.noB, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void showDialog(Context context, String title, String msg) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.yourDialog);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg).setCancelable(false)
                .setPositiveButton(Login.yesB, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CommunicationAssign.this.finish();
                    }
                })
                .setNegativeButton(Login.noB, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void setLang() {
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
            saveButton.setText(saveB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveCommunicationDeviceAsset() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (serialNumberEdit.getText().toString().isEmpty() || boughtOn.getText().toString().isEmpty() || imeiEdit.getText().toString().isEmpty() || prizeEdit.getText().toString().isEmpty()
                            || equipmentTypeList.getSelectedItem().toString().equals("-select-") || assignToList.getSelectedItem().toString().equals("-select-")
                            || statusList.getSelectedItem().toString().equals("-select-") || locationList.getSelectedItem().toString().equals("-select-")
                            || currencyList.getSelectedItem().toString().equals("-select-")) {
                        Toast.makeText(CommunicationAssign.this, mandatoryFieldM, Toast.LENGTH_SHORT).show();
                    } else {
                        String[] communicationCol = {"PrimaryKey", "SerialNumber", "Make", "Model", "Supplier", "Price", "Currency", "BoughtOn", "AssignedTo",
                                "Status", "EquipmentType", "IMEINumber"};
                        String[] communicationValues = {barcode, serialNumberEdit.getText().toString(), makeEdit.getText().toString(),
                                modelEdit.getText().toString(), supplierEdit.getText().toString(), prizeEdit.getText().toString(),
                                currencyList.getSelectedItem().toString(), boughtOn.getText().toString(), assignToList.getSelectedItem().toString(),
                                statusList.getSelectedItem().toString(), equipmentTypeList.getSelectedItem().toString(), imeiEdit.getText().toString()};
                        int result = cc.sqlModule(insertOperation, communicationDevicesTable, communicationCol, null, communicationValues, inventoryDatabase);
                        if (result > 0) {
                            String[] overViewCol = {"AssignedTo", "AssetType", "EquipmentType", "Status", "Location"};
                            String[] overViewValues = {assignToList.getSelectedItem().toString(), assetLabel.getText().toString().replace(" Assign Form", ""), equipmentTypeList.getSelectedItem().toString(),
                                    statusList.getSelectedItem().toString(), locationList.getSelectedItem().toString()};
                            cc.sqlModule(updateOperation, overViewTable, overViewCol, "PrimaryKey='" + barcode + "'", overViewValues, inventoryDatabase);
                            Toast.makeText(CommunicationAssign.this, assignSuccessfullM, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(CommunicationAssign.this, Home.class).putExtra(selectedLanguageKey, selectedLang));
                        } else
                            Toast.makeText(CommunicationAssign.this, assignnNotSuccessfullM, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(CommunicationAssign.this, somethingWentWrongM, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
