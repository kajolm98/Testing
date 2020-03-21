package com.mtem.asset_management_app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;

public class QueryInventory extends BaseActivity {

    Button submitButton;
    Spinner assignedList;
    TextView inventoryManagementText,userAssigned,serialNo,barcodeValue,barcodeImage,assignTo,assetType,equipmentType,status,location;
    String selectedLang,row1,row2,row3,row4,row5,row6,row7;
    TableLayout table;
    TableRow tableRow;
    Context context;
    Bitmap bitmap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_inventory);

        submitButton=findViewById(R.id.submitButton);
        inventoryManagementText=findViewById(R.id.inventoryManagementText);
        userAssigned=findViewById(R.id.userAssigned);
        assignedList=findViewById(R.id.userAssignedList);
        table=findViewById(R.id.tableView);
        tableRow=findViewById(R.id.row0);
        serialNo=findViewById(R.id.rowNumber);
        barcodeValue=findViewById(R.id.row0Header);
        barcodeImage=findViewById(R.id.row1Header);
        assignTo=findViewById(R.id.row2Header);
        assetType=findViewById(R.id.row3Header);
        equipmentType=findViewById(R.id.row4Header);
        status=findViewById(R.id.row5Header);
        location=findViewById(R.id.row6Header);
        //table.setStretchAllColumns(true);
        serialNo.setGravity(Gravity.CENTER);
        barcodeValue.setGravity(Gravity.CENTER);
        barcodeImage.setGravity(Gravity.CENTER);
        assignTo.setGravity(Gravity.CENTER);
        assetType.setGravity(Gravity.CENTER);
        equipmentType.setGravity(Gravity.CENTER);
        status.setGravity(Gravity.CENTER);
        location.setGravity(Gravity.CENTER);
        context=QueryInventory.this;
         Intent intent=getIntent();
        selectedLang = intent.getStringExtra(selectedLanguageKey);

            ArrayList<String> userAssignedList =retriveDropDown("Employee_name","Login_Table",genericDatabase);
            ArrayAdapter<String> assignedUserList = new ArrayAdapter<>(this,R.layout.spinner_style,R.id.textview, userAssignedList);
            assignedList.setPrompt(assignToH);
            assignedList.setAdapter(assignedUserList);

         setLang();
        submit();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void setLang() {
        try {
            inventoryManagementText.setText(inventoryManagmentH);
            userAssigned.setText(assignToH);
            submitButton.setText(submitB);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void submit(){
         submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (assignedList.getSelectedItem().toString().equals("-select-")) {
                    Toast.makeText(context, "Please Select Any User", Toast.LENGTH_SHORT).show();
                } else {
                    ResultSet response = cc.sqlModule(selectOperation, overViewTable, allColumns, "AssignedTo='" + assignedList.getSelectedItem().toString() + "'", inventoryDatabase);
                    try {
                        if (!response.next()) {
                            for (int row = 1; row < table.getChildCount(); row++) {
                                View child = table.getChildAt(row);
                                if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
                            }
                            Toast.makeText(QueryInventory.this, getString(R.string.userNotAssigned), Toast.LENGTH_LONG).show();
                        } else {
                            for (int row = one; row < table.getChildCount(); row++) {
                                View child = table.getChildAt(row);
                                if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
                            }
                            int count = one;
                            row1 = response.getString("PrimaryKey");
                            row2 = response.getString("QRCodeImage");
                            row3 = response.getString("AssignedTo");
                            row4 = response.getString("AssetType");
                            row5 = response.getString("EquipmentType");
                            row6 = response.getString("Status");
                            row7 = response.getString("Location");
                             createRows(count + "", row1, "  " + row2, row3, row4, row5, row6, row7);
                            count++;
                            while (response.next()) {
                                row1 = response.getString("PrimaryKey");
                                row2 = response.getString("QRCodeImage");
                                row3 = response.getString("AssignedTo");
                                row4 = response.getString("AssetType");
                                row5 = response.getString("EquipmentType");
                                row6 = response.getString("Status");
                                row7 = response.getString("Location");
                                 createRows(count + "", row1, "  " + row2, row3, row4, row5, row6, row7);
                                count++;
                                //showMassage("Assigned List",allData.toString());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    public void getQRcode(String barcodeFormat) throws WriterException {
        String productId = barcodeFormat;
         Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        Writer codeWriter;
        codeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = codeWriter.encode(productId, BarcodeFormat.QR_CODE,100, 100, hintMap);
        int width = byteMatrix.getWidth();
        int height = byteMatrix.getHeight();
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        for (int biteRow = zero; biteRow < width; biteRow++) {
            for (int biteColumn = zero; biteColumn < height; biteColumn++) {
                bitmap.setPixel(biteRow, biteColumn, byteMatrix.get(biteRow, biteColumn) ? Color.BLACK : Color.WHITE);
            }
        }
     }
    public void createRows(String slNo,String barcode,String image,String assign,String asset,String equip,String state,String local) throws WriterException {
        TableRow tr = new TableRow(this);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(20, 20, 20, 20);
        TextView serialNo = new TextView(this);
        TextView barcodeValue = new TextView(this);
        TextView barcodeImage = new TextView(this);
        TextView assignTo = new TextView(this);
        TextView assetType = new TextView(this);
        TextView equipmentType = new TextView(this);
        TextView status = new TextView(this);
        TextView location = new TextView(this);

        serialNo.setText(slNo);
        barcodeValue.setText(barcode);
        barcodeImage.setText(image);
        assignTo.setText(assign);
        assetType.setText(asset);
        equipmentType.setText(equip);
        status.setText(state);
        location.setText(local);

        serialNo.setGravity(Gravity.CENTER);
        barcodeValue.setGravity(Gravity.CENTER);
        barcodeImage.setGravity(Gravity.CENTER);
        assignTo.setGravity(Gravity.CENTER);
        assetType.setGravity(Gravity.CENTER);
        equipmentType.setGravity(Gravity.CENTER);
        status.setGravity(Gravity.CENTER);
        location.setGravity(Gravity.CENTER);

        tr.addView(serialNo);
        tr.addView(barcodeValue);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            ImageView view = new ImageView(this);
            getQRcode(barcodeValue.getText().toString());
            view.setImageBitmap(bitmap);
            tr.addView(view);
            tr.addView(assignTo);
        tr.addView(assetType);
        tr.addView(equipmentType);
        tr.addView(status);
        tr.addView(location);

        table.addView(tr);
    }
}
