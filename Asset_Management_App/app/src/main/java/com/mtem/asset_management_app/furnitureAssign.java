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

public class furnitureAssign extends BaseActivity {

        Button saveButton;
     Spinner statusList, assignToList, locationList, equipmentTypeList,currencyList,lengthList,widthList,heightList;
    LinearLayout assignAssetView, assignAssetView1, assignAssetView2;
        TextView  assetLabel,typeView, assignView, statusView, locationView;
        TextView   serialView,makeView,modelView, boughtView, supplierView, prizeView, lengthView, widthView, heightView;
        EditText  serialNumberEdit,makeEdit,modelEdit, boughtOn,supplierEdit, prizeEdit, lengthEdit, widthEdit, heightEdit;
        String barcode, task, selectedLang;
        Context context;
        DatePickerDialog picker;


        @Override
        public void onCreate(Bundle savedInstanceState) {
            try{
            super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_furniture_assign);

            serialNumberEdit = findViewById(R.id.serialEdit);
            makeEdit = findViewById(R.id.makeEdit);
            modelEdit = findViewById(R.id.modelEdit);
            boughtOn = findViewById(R.id.boughtEdit);
            saveButton = findViewById(R.id.saveButton);
            statusList = findViewById(R.id.statusList);
            assignToList = findViewById(R.id.assignList);
            locationList = findViewById(R.id.locationList);
            equipmentTypeList = findViewById(R.id.typeList);
            currencyList=findViewById(R.id.currencyList);
            lengthList=findViewById(R.id.lengthUnitEdit);
            widthList=findViewById(R.id.widthUnitEdit);
            heightList=findViewById(R.id.heightUnitEdit);
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
              context=furnitureAssign.this;

            assetLabel = findViewById(R.id.label);
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
            lengthEdit = findViewById(R.id.lengthEdit);
            widthEdit = findViewById(R.id.widthEdit);
            heightEdit = findViewById(R.id.heightEdit);
            lengthView = findViewById(R.id.lengthView);
            widthView = findViewById(R.id.widthView);
            heightView = findViewById(R.id.heighView);

                prizeEdit.addTextChangedListener(new NumberTextWatcherWithSeperator(prizeEdit));

                boughtOn.setInputType(InputType.TYPE_NULL);
            boughtOn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar cal = Calendar.getInstance();
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int month = cal.get(Calendar.MONTH);
                    int year = cal.get(Calendar.YEAR);
                    // date picker dialog
                    picker = new DatePickerDialog(furnitureAssign.this,
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
            barcode = intent.getStringExtra(barcodeValueKey);
            task = intent.getStringExtra(taskKey);
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
            ArrayList<String> length = retriveDropDown("LengthUnit","DropDown_IMP",inventoryDatabase);
             ArrayAdapter<String> lengthL = new ArrayAdapter<>(this,R.layout.spinner_style,R.id.textview, length);
                lengthList.setPrompt(lenghtUnitH);
                lengthList.setAdapter(lengthL);
            ArrayList<String> width = retriveDropDown("HeightUnit","DropDown_IMP",inventoryDatabase);
             ArrayAdapter<String> widthL = new ArrayAdapter<>(this,R.layout.spinner_style,R.id.textview, width);
                widthList.setPrompt(widthUnitH);
                widthList.setAdapter(widthL);
            ArrayList<String> height = retriveDropDown("WidthUnit","DropDown_IMP",inventoryDatabase);
            ArrayAdapter<String> heightL = new ArrayAdapter<>(this,R.layout.spinner_style,R.id.textview, height);
                heightList.setPrompt(heightUnitH);
                heightList.setAdapter(heightL);

            setLang();

            if (task.equals("furniture")) {
                assetLabel.setText("Furniture Assign Form");
                ArrayList<String> equipmentTypes = retriveDropDown("EquipmentTypeFur","DropDown_IMP",inventoryDatabase);
                ArrayAdapter<String> equipTypes = new ArrayAdapter<>(this,R.layout.spinner_style,R.id.textview, equipmentTypes);
                equipmentTypeList.setPrompt(equipmentTypeH);
                equipmentTypeList.setAdapter(equipTypes);
                saveFurnitureAsset();
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
            showDialogToHome(furnitureAssign.this,warningH,saveW);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        showDialog(furnitureAssign.this,warningH,saveW);
    }
    public void showDialogToHome(Context context,String title,String msg){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context,R.style.yourDialog);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg).setCancelable(false)
                .setPositiveButton(yesB,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        startActivity(new Intent(furnitureAssign.this,Home.class).putExtra(selectedLanguageKey,selectedLanguage));
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
        // set title
        alertDialogBuilder.setTitle(title);
        // set dialog message
        alertDialogBuilder.setMessage(msg).setCancelable(false)
                .setPositiveButton(yesB,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        furnitureAssign.this.finish();
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
                supplierView.setHint(supplierH);
                prizeView.setHint(prizeH);
                saveButton.setText(saveB);
                lengthView.setHint(lengthH);
                widthView.setHint(widthH);
                heightView.setHint(heightH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void saveFurnitureAsset() {
            try {
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (boughtOn.getText().toString().isEmpty() || prizeEdit.getText().toString().isEmpty()
                                ||equipmentTypeList.getSelectedItem().toString().equals("-select-") || assignToList.getSelectedItem().toString().equals("-select-")
                                || statusList.getSelectedItem().toString().equals("-select-") || locationList.getSelectedItem().toString().equals("-select-")
                                || currencyList.getSelectedItem().toString().equals("-select-")) {
                            Toast.makeText(furnitureAssign.this, mandatoryFieldM, Toast.LENGTH_SHORT).show();
                        } else {
                            String[] furnitureCol={"PrimaryKey","SerialNumber","Make","Model","Supplier","Price","Currency","BoughtOn","AssignedTo",
                                    "Status","EquipmentType","Length","LengthUnit","Width","WidthUnit","Height","HeightUnit"};
                            String[] furnitureValues={barcode, serialNumberEdit.getText().toString(), makeEdit.getText().toString(),
                                    modelEdit.getText().toString(), supplierEdit.getText().toString(), prizeEdit.getText().toString(),
                                    currencyList.getSelectedItem().toString(), boughtOn.getText().toString(), assignToList.getSelectedItem().toString(),
                                    statusList.getSelectedItem().toString(),equipmentTypeList.getSelectedItem().toString(), lengthEdit.getText().toString(),
                                    lengthList.getSelectedItem().toString(),widthEdit.getText().toString(),widthList.getSelectedItem().toString(),
                                    heightEdit.getText().toString(),heightList.getSelectedItem().toString()};
                            int result = cc.sqlModule(insertOperation,furnitureTable,furnitureCol,null,furnitureValues,inventoryDatabase);
                            if (result >0) {
                                String[] overViewCol = {"AssignedTo", "AssetType", "EquipmentType", "Status", "Location"};
                                String[] overViewValues = {assignToList.getSelectedItem().toString(), assetLabel.getText().toString().replace(" Assign Form", ""), equipmentTypeList.getSelectedItem().toString(),
                                        statusList.getSelectedItem().toString(), locationList.getSelectedItem().toString()};
                                cc.sqlModule(updateOperation,overViewTable,overViewCol,"PrimaryKey='"+barcode+"'",overViewValues,inventoryDatabase);
                                Toast.makeText(furnitureAssign.this, assignSuccessfullM, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(furnitureAssign.this, Home.class).putExtra(selectedLanguageKey, selectedLang));
                            } else
                                Toast.makeText(furnitureAssign.this, assignnNotSuccessfullM, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (Exception e) {
                Toast.makeText(furnitureAssign.this, somethingWentWrongM, Toast.LENGTH_SHORT).show();
            }
        }
    }
