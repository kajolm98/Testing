package com.mtem.asset_management_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;


public class NewBarcode2 extends BaseActivity {

    Button downloadButton, assignAssetButton;
    TextView barcodeView;
    Bitmap bitmap;
    ImageView barcodeImage;
    String barcode, selectedLang;
    View view;
    private static final int STORAGE_PERMISSION_CODE = 101;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_barcode2);

        downloadButton = findViewById(R.id.downloadButton);
        assignAssetButton = findViewById(R.id.assignAssetButton);
        barcodeView = findViewById(R.id.barcodeView);
         barcodeImage = findViewById(R.id.barcodeImage);

        Intent intent = getIntent();
        barcode = intent.getStringExtra(barcodeValueKey);
        selectedLang = intent.getStringExtra(selectedLanguageKey);
        barcodeView.setText(barcode);

        setLanguage();
        assignAssetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewBarcode2.this, AssignAssetType.class).putExtra(barcodeValueKey, barcode)
                        .putExtra(selectedLanguageKey, selectedLang));
            }
        });

        buttonGenerate_onClick();
       downloadBarcode();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void setLanguage() {
            try {
                downloadButton.setText(downloadQRCodeB);
                assignAssetButton.setText(assignAssetB);
            } catch (Exception e) {
                e.printStackTrace();
        }
    }
    private void buttonGenerate_onClick() {
        try {
            String productId = barcode;
            Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            Writer codeWriter;
            codeWriter = new QRCodeWriter();
            BitMatrix byteMatrix = codeWriter.encode(productId, BarcodeFormat.QR_CODE, 500, 500, hintMap);
            int width = byteMatrix.getWidth();
            int height = byteMatrix.getHeight();
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            for (int biteRow = 0; biteRow < width; biteRow++) {
                for (int biteColumn = 0; biteColumn < height; biteColumn++) {
                    bitmap.setPixel(biteRow, biteColumn, byteMatrix.get(biteRow, biteColumn) ? Color.BLACK : Color.WHITE);
                }
                barcodeImage.setImageBitmap(bitmap);
            }
         } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    private void saveImage(Bitmap finalBarcode){
        PdfDocument pd = new PdfDocument();
        PdfDocument.PageInfo pi = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.Page p = pd.startPage(pi);
        Canvas c = p.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(7);
        c.drawText(barcode, 40, 40, paint);
        c.drawBitmap(bitmap.createScaledBitmap(finalBarcode,100,100,true), 28, 41, new Paint());
        pd.finishPage(p);
        try {
            //make sure you have asked for storage permission before this
            String root = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES).toString();
            File myDir = new File(root + "/_I_M_/QR_codes");
            myDir.mkdirs();
            String fileName = barcode + ".pdf";
            File file = new File(myDir, fileName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            pd.writeTo(new FileOutputStream(file));
            Toast.makeText(NewBarcode2.this, "QR_code Saved In : " +
                    file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
         } catch (IOException ioe) {
            ioe.printStackTrace();
        }catch (Exception e){
            Toast.makeText(this, somethingWentWrongM, Toast.LENGTH_SHORT).show();
        }
        pd.close();
    }

    public void downloadBarcode(){
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,STORAGE_PERMISSION_CODE);
             }
        });
    }
    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(NewBarcode2.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(NewBarcode2.this,
                    new String[] { permission },
                    requestCode);
        }
        else {
                     saveImage(bitmap);
         }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > zero
                    && grantResults[zero] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(NewBarcode2.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(NewBarcode2.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
