package com.mtem.asset_management_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ScanBarcode extends BaseActivity  {

    Button submitButton,getBarcodeButton;
     EditText finalBarcodeValue;
     String getStringFromBarcode,task,selectedLang,subTask;
    TextView scanAssetBarcode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_scan_barcode);

            submitButton = findViewById(R.id.submitButton);
            getBarcodeButton = findViewById(R.id.getBarcodeButton);
            finalBarcodeValue = findViewById(R.id.finalBarcodeValue);
             scanAssetBarcode = findViewById(R.id.scanAsset);

            Intent i = getIntent();
            task = i.getStringExtra(tasKey);
            selectedLang = i.getStringExtra(selectedLanguageKey);
            finalBarcodeValue.setText(getStringFromBarcode);
            try {
                if (task.equals(assignTask)) {
                    assignSubmit();
                } else if (task.equals(updateTask)) {
                    updateSubmit();
                } else if (task.equals(readTask)) {
                    readSubmit();
                } else if (task.equals(scrapTask)) {
                    scrapSubmit();
                }
            } catch (Exception e) {
                Toast.makeText(ScanBarcode.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            setLanguage();
            setGetBarcode();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void setLanguage(){
            try {
                scanAssetBarcode.setText(scanAssetRQCodeM);
                submitButton.setText(submitB);
                getBarcodeButton.setText(scanQRCodeB);
            } catch (Exception e) {
                e.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            String productId = intentResult.getContents();
            finalBarcodeValue.setText(productId);
            ResultSet result = cc.sqlModule(selectOperation, overViewTable, allColumns, "PrimaryKey='" + finalBarcodeValue.getText().toString() + "'", inventoryDatabase);
            try {
                if (task.equals(assignTask)) {
                    if (!result.next()) {
                        Toast.makeText(ScanBarcode.this, invaliQRM, Toast.LENGTH_LONG).show();
                    } else {
                        if (result.getString("AssetType") == null) {
                            startActivity(new Intent(ScanBarcode.this, AssignAssetType.class).putExtra("barcodeValue",
                                    finalBarcodeValue.getText().toString())
                                    .putExtra("selectedLang", selectedLang));
                        } else if (result.getString("Status").equalsIgnoreCase(scrappedStatus)) {
                            Toast.makeText(ScanBarcode.this, alreadyScrapM, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ScanBarcode.this, alreadyAssignM, Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if (task.equals(updateTask)) {
                    if (!result.next()) {
                        Toast.makeText(ScanBarcode.this, invaliQRM, Toast.LENGTH_LONG).show();
                    } else {
                        if (result.getString("AssetType") == null) {
                            Toast.makeText(ScanBarcode.this, "Asset Not Yet Assigned", Toast.LENGTH_SHORT).show();
                        } else {
                            subTask = result.getString("AssetType");
                            if (result.getString("Status").equalsIgnoreCase(scrappedStatus)) {
                                Toast.makeText(ScanBarcode.this, alreadyScrapM, Toast.LENGTH_SHORT).show();
                            } else {
                                if (subTask.contains("Communication")) {
                                    startActivity(new Intent(ScanBarcode.this, CommunicationUpdate.class).putExtra("barcodeValue",
                                            finalBarcodeValue.getText().toString())
                                            .putExtra("selectedLang", selectedLang));
                                } else if (subTask.contains("Technical")) {
                                    startActivity(new Intent(ScanBarcode.this, FgOtTeVeUpdate.class).putExtra("barcodeValue",
                                            finalBarcodeValue.getText().toString())
                                            .putExtra("selectedLang", selectedLang));
                                } else if (subTask.contains("Furniture")) {
                                    startActivity(new Intent(ScanBarcode.this, furnitureUpdate.class).putExtra("barcodeValue",
                                            finalBarcodeValue.getText().toString())
                                            .putExtra("selectedLang", selectedLang));
                                } else if (subTask.contains("IT_Equipment")) {
                                    startActivity(new Intent(ScanBarcode.this, itUpdate.class).putExtra("barcodeValue",
                                            finalBarcodeValue.getText().toString())
                                            .putExtra("selectedLang", selectedLang));
                                } else if (subTask.contains("Finished") || subTask.contains("Others")) {
                                    startActivity(new Intent(ScanBarcode.this, FgOtTeVeUpdate.class).putExtra("barcodeValue",
                                            finalBarcodeValue.getText().toString())
                                            .putExtra("selectedLang", selectedLang));
                                } else if (subTask.contains("Vehicle")) {
                                    startActivity(new Intent(ScanBarcode.this, FgOtTeVeUpdate.class).putExtra("barcodeValue",
                                            finalBarcodeValue.getText().toString())
                                            .putExtra("selectedLang", selectedLang));
                                }
                            }
                        }
                    }
                } else if (task.equals(readTask)) {
                    if (!result.next()) {
                        Toast.makeText(ScanBarcode.this, invaliQRM, Toast.LENGTH_LONG).show();
                    } else {
                        if (result.getString("AssetType") == null) {
                            Toast.makeText(ScanBarcode.this, "Asset Not Yet Assigned", Toast.LENGTH_SHORT).show();
                        } else {
                            subTask = result.getString("AssetType");
                            if (result.getString("Status").equalsIgnoreCase(scrappedStatus)) {
                                Toast.makeText(ScanBarcode.this, alreadyScrapM, Toast.LENGTH_SHORT).show();
                            } else {
                                subTask = result.getString("AssetType");
                                if (subTask.contains("Communication")) {
                                    startActivity(new Intent(ScanBarcode.this, CommunicationRead.class).putExtra("barcodeValue",
                                            finalBarcodeValue.getText().toString())
                                            .putExtra("selectedLang", selectedLang));
                                } else if (subTask.contains("Technical")) {
                                    startActivity(new Intent(ScanBarcode.this, FgOtTeVeRead.class).putExtra("barcodeValue",
                                            finalBarcodeValue.getText().toString())
                                            .putExtra("selectedLang", selectedLang));
                                } else if (subTask.contains("Furniture")) {
                                    startActivity(new Intent(ScanBarcode.this, furnitureRead.class).putExtra("barcodeValue",
                                            finalBarcodeValue.getText().toString())
                                            .putExtra("selectedLang", selectedLang));
                                } else if (subTask.contains("IT_Equipment")) {
                                    startActivity(new Intent(ScanBarcode.this, itRead.class).putExtra("barcodeValue",
                                            finalBarcodeValue.getText().toString())
                                            .putExtra("selectedLang", selectedLang));
                                } else if (subTask.contains("Vehicle")) {
                                    startActivity(new Intent(ScanBarcode.this, FgOtTeVeRead.class).putExtra("barcodeValue",
                                            finalBarcodeValue.getText().toString())
                                            .putExtra("selectedLang", selectedLang));
                                } else if (subTask.contains("Finished") || subTask.contains("Others")) {
                                    startActivity(new Intent(ScanBarcode.this, FgOtTeVeRead.class).putExtra("barcodeValue",
                                            finalBarcodeValue.getText().toString())
                                            .putExtra("selectedLang", selectedLang));
                                }
                            }
                        }
                    }
                } else if (task.equals(scrapTask)) {
                    if (!result.next()) {
                        Toast.makeText(ScanBarcode.this, invaliQRM, Toast.LENGTH_LONG).show();
                    } else {
                        if (result.getString("AssetType") == null) {
                            Toast.makeText(ScanBarcode.this, "Asset Not Yet Assigned", Toast.LENGTH_SHORT).show();
                        } else {
                            subTask = result.getString("AssetType");
                            if (result.getString("Status").equalsIgnoreCase(scrappedStatus)) {
                                Toast.makeText(ScanBarcode.this, alreadyScrapM, Toast.LENGTH_SHORT).show();
                            } else {
                                subTask = result.getString("AssetType");
                                if (subTask.contains("Communication")) {
                                    startActivity(new Intent(ScanBarcode.this, CommunicationScrap.class).putExtra("barcodeValue",
                                            finalBarcodeValue.getText().toString())
                                            .putExtra("selectedLang", selectedLang));
                                } else if (subTask.contains("Technical")) {
                                    startActivity(new Intent(ScanBarcode.this, FgOtTeVeScrap.class).putExtra("barcodeValue",
                                            finalBarcodeValue.getText().toString())
                                            .putExtra("selectedLang", selectedLang));
                                } else if (subTask.contains("Furniture")) {
                                    startActivity(new Intent(ScanBarcode.this, furnitureScrap.class).putExtra("barcodeValue",
                                            finalBarcodeValue.getText().toString())
                                            .putExtra("selectedLang", selectedLang));
                                } else if (subTask.contains("IT_Equipment")) {
                                    startActivity(new Intent(ScanBarcode.this, itScrap.class).putExtra("barcodeValue",
                                            finalBarcodeValue.getText().toString())
                                            .putExtra("selectedLang", selectedLang));
                                } else if (subTask.contains("Vehicle")) {
                                    startActivity(new Intent(ScanBarcode.this, FgOtTeVeScrap.class).putExtra("barcodeValue",
                                            finalBarcodeValue.getText().toString())
                                            .putExtra("selectedLang", selectedLang));
                                } else if (subTask.contains("Finished") || subTask.contains("Others")) {
                                    startActivity(new Intent(ScanBarcode.this, FgOtTeVeScrap.class).putExtra("barcodeValue",
                                            finalBarcodeValue.getText().toString())
                                            .putExtra("selectedLang", selectedLang));
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Toast.makeText(ScanBarcode.this, somethingWentWrongM, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setGetBarcode(){
        getBarcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    IntentIntegrator intentIntegrator = new IntentIntegrator(ScanBarcode.this);
                    intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                    intentIntegrator.setCameraId(zero);  // Use a specific camera of the device
                    intentIntegrator.setOrientationLocked(true);
                    intentIntegrator.setBeepEnabled(true);
                    intentIntegrator.setCaptureActivity(CaptureActivityPortrait.class);
                    intentIntegrator.initiateScan();
                }
            }
        });
    }
    public void assignSubmit(){
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (finalBarcodeValue.length() > zero) {
                        ResultSet result = cc.sqlModule(selectOperation, overViewTable, allColumns, "PrimaryKey='" + finalBarcodeValue.getText().toString() + "'", inventoryDatabase);
                        try {
                            if (!result.next()) {
                                Toast.makeText(ScanBarcode.this, invaliQRM, Toast.LENGTH_LONG).show();
                            } else {
                                if (result.getString("AssetType") == null) {
                                    startActivity(new Intent(ScanBarcode.this, AssignAssetType.class).putExtra("barcodeValue",
                                            finalBarcodeValue.getText().toString())
                                            .putExtra("selectedLang", selectedLang));
                                } else if (result.getString("Status").equalsIgnoreCase(scrappedStatus)) {
                                    Toast.makeText(ScanBarcode.this, alreadyScrapM, Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(ScanBarcode.this, alreadyAssignM, Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else
                        Toast.makeText(ScanBarcode.this, scanAssetRQCodeM, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    } public void updateSubmit(){
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finalBarcodeValue.length() > zero) {
                        ResultSet result = cc.sqlModule(selectOperation, overViewTable, allColumns, "PrimaryKey='" + finalBarcodeValue.getText().toString() + "'", inventoryDatabase);
                        try {
                            if (!result.next()) {
                                Toast.makeText(ScanBarcode.this, invaliQRM, Toast.LENGTH_LONG).show();
                            } else {
                                if (result.getString("AssetType") == null) {
                                    Toast.makeText(ScanBarcode.this,"Asset Not Yet Assigned", Toast.LENGTH_SHORT).show();
                                }else {
                                    subTask = result.getString("AssetType");
                                    if (result.getString("Status").equalsIgnoreCase(scrappedStatus)) {
                                        Toast.makeText(ScanBarcode.this, alreadyScrapM, Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (subTask.contains("Communication")) {
                                            startActivity(new Intent(ScanBarcode.this, CommunicationUpdate.class).putExtra("barcodeValue",
                                                    finalBarcodeValue.getText().toString())
                                                    .putExtra("selectedLang", selectedLang));
                                        } else if (subTask.contains("Technical")) {
                                            startActivity(new Intent(ScanBarcode.this, FgOtTeVeUpdate.class).putExtra("barcodeValue",
                                                    finalBarcodeValue.getText().toString())
                                                    .putExtra("selectedLang", selectedLang));
                                        } else if (subTask.contains("Furniture")) {
                                            startActivity(new Intent(ScanBarcode.this, furnitureUpdate.class).putExtra("barcodeValue",
                                                    finalBarcodeValue.getText().toString())
                                                    .putExtra("selectedLang", selectedLang));
                                        } else if (subTask.contains("IT_Equipment")) {
                                            startActivity(new Intent(ScanBarcode.this, itUpdate.class).putExtra("barcodeValue",
                                                    finalBarcodeValue.getText().toString())
                                                    .putExtra("selectedLang", selectedLang));
                                        } else if (subTask.contains("Finished") || subTask.contains("Others")) {
                                            startActivity(new Intent(ScanBarcode.this, FgOtTeVeUpdate.class).putExtra("barcodeValue",
                                                    finalBarcodeValue.getText().toString())
                                                    .putExtra("selectedLang", selectedLang));
                                        } else if (subTask.contains("Vehicle")) {
                                            startActivity(new Intent(ScanBarcode.this, FgOtTeVeUpdate.class).putExtra("barcodeValue",
                                                    finalBarcodeValue.getText().toString())
                                                    .putExtra("selectedLang", selectedLang));
                                        }
                                    }
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    } else
                        Toast.makeText(ScanBarcode.this, scanAssetRQCodeM, Toast.LENGTH_LONG).show();
                }
            });
    } public void  readSubmit(){
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(finalBarcodeValue.length()>zero) {
                    ResultSet result = cc.sqlModule(selectOperation, overViewTable, allColumns, "PrimaryKey='" + finalBarcodeValue.getText().toString() + "'", inventoryDatabase);
                    try {
                        if(!result.next()){
                            Toast.makeText(ScanBarcode.this,invaliQRM,Toast.LENGTH_LONG).show();
                        } else {
                            if (result.getString("AssetType") == null) {
                                Toast.makeText(ScanBarcode.this,"Asset Not Yet Assigned", Toast.LENGTH_SHORT).show();
                            }else {
                                subTask = result.getString("AssetType");
                                if (result.getString("Status").equalsIgnoreCase(scrappedStatus)) {
                                    Toast.makeText(ScanBarcode.this, alreadyScrapM, Toast.LENGTH_SHORT).show();
                                } else {
                                    if (subTask.contains("Communication")) {
                                        startActivity(new Intent(ScanBarcode.this, CommunicationRead.class).putExtra("barcodeValue",
                                                finalBarcodeValue.getText().toString())
                                                .putExtra("selectedLang", selectedLang));
                                    } else if (subTask.contains("Technical")) {
                                        startActivity(new Intent(ScanBarcode.this, FgOtTeVeRead.class).putExtra("barcodeValue",
                                                finalBarcodeValue.getText().toString())
                                                .putExtra("selectedLang", selectedLang));
                                    } else if (subTask.contains("Furniture")) {
                                        startActivity(new Intent(ScanBarcode.this, furnitureRead.class).putExtra("barcodeValue",
                                                finalBarcodeValue.getText().toString())
                                                .putExtra("selectedLang", selectedLang));
                                    } else if (subTask.contains("IT_Equipment")) {
                                        startActivity(new Intent(ScanBarcode.this, itRead.class).putExtra("barcodeValue",
                                                finalBarcodeValue.getText().toString())
                                                .putExtra("selectedLang", selectedLang));
                                    } else if (subTask.contains("Vehicle")) {
                                        startActivity(new Intent(ScanBarcode.this, FgOtTeVeRead.class).putExtra("barcodeValue",
                                                finalBarcodeValue.getText().toString())
                                                .putExtra("selectedLang", selectedLang));
                                    } else if (subTask.contains("Finished") || subTask.contains("Others")) {
                                        startActivity(new Intent(ScanBarcode.this, FgOtTeVeRead.class).putExtra("barcodeValue",
                                                finalBarcodeValue.getText().toString())
                                                .putExtra("selectedLang", selectedLang));
                                    }
                                }
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }else
                    Toast.makeText(ScanBarcode.this,scanAssetRQCodeM,Toast.LENGTH_LONG).show();
            }
        });
    } public void  scrapSubmit(){
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(finalBarcodeValue.length()>zero) {
                    ResultSet result = cc.sqlModule(selectOperation, overViewTable, allColumns, "PrimaryKey='" + finalBarcodeValue.getText().toString() + "'", inventoryDatabase);
                    try {
                        if(!result.next()){
                            Toast.makeText(ScanBarcode.this,invaliQRM,Toast.LENGTH_LONG).show();
                        } else {
                            if (result.getString("AssetType") == null) {
                                Toast.makeText(ScanBarcode.this,"Asset Not Yet Assigned", Toast.LENGTH_SHORT).show();
                            }else {
                                subTask = result.getString("AssetType");
                                if (result.getString("Status").equalsIgnoreCase(scrappedStatus)) {
                                    Toast.makeText(ScanBarcode.this, alreadyScrapM, Toast.LENGTH_SHORT).show();
                                } else {
                                    if (subTask.contains("Communication")) {
                                        startActivity(new Intent(ScanBarcode.this, CommunicationScrap.class).putExtra("barcodeValue",
                                                finalBarcodeValue.getText().toString())
                                                .putExtra("selectedLang", selectedLang));
                                    } else if (subTask.contains("Technical")) {
                                        startActivity(new Intent(ScanBarcode.this, FgOtTeVeScrap.class).putExtra("barcodeValue",
                                                finalBarcodeValue.getText().toString())
                                                .putExtra("selectedLang", selectedLang));
                                    } else if (subTask.contains("Furniture")) {
                                        startActivity(new Intent(ScanBarcode.this, furnitureScrap.class).putExtra("barcodeValue",
                                                finalBarcodeValue.getText().toString())
                                                .putExtra("selectedLang", selectedLang));
                                    } else if (subTask.contains("IT_Equipment")) {
                                        startActivity(new Intent(ScanBarcode.this, itScrap.class).putExtra("barcodeValue",
                                                finalBarcodeValue.getText().toString())
                                                .putExtra("selectedLang", selectedLang));
                                    } else if (subTask.contains("Vehicle")) {
                                        startActivity(new Intent(ScanBarcode.this, FgOtTeVeScrap.class).putExtra("barcodeValue",
                                                finalBarcodeValue.getText().toString())
                                                .putExtra("selectedLang", selectedLang));
                                    } else if (subTask.contains("Finished") || subTask.contains("Others")) {
                                        startActivity(new Intent(ScanBarcode.this, FgOtTeVeScrap.class).putExtra("barcodeValue",
                                                finalBarcodeValue.getText().toString())
                                                .putExtra("selectedLang", selectedLang));
                                    }
                                }
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }else
                    Toast.makeText(ScanBarcode.this,scanAssetRQCodeM,Toast.LENGTH_LONG).show();
            }
        });
    }
}
