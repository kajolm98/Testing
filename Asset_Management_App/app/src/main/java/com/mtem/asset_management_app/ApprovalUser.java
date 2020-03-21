package com.mtem.asset_management_app;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ApprovalUser extends BaseActivity {
    LinearLayout usersList,scrollOneView;
    View customLayout;
    AlertDialog dialog;
    boolean wantToCloseDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_user);
        usersList = findViewById(R.id.userListView);
             ResultSet response = cc.sqlModule(selectOperation, userDetailsH, allColumns, "Status='Pending'", genericDatabase);
            if (!response.next()) {
                Toast.makeText(ApprovalUser.this, "New Users Not Yet Registered", Toast.LENGTH_LONG).show();
            } else {
                while (response.next()) {
                    onAddField(response);
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void onAddField(final ResultSet response) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.user_approval_form, null);
            TextView username = rowView.findViewById(R.id.user);
            final TextView email = rowView.findViewById(R.id.email);
            TextView status = rowView.findViewById(R.id.status);
            usersList.addView(rowView, usersList.getChildCount());
            username.setText(response.getString(2)+" "+response.getString(3));
            email.setText(response.getString(5));
            status.setText(response.getString(11));
             rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAlertDialogForAddTravelDetail("User Details",email.getText().toString());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showAlertDialogForAddTravelDetail(String title, final String email) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.yourDialog);
            builder.setTitle(title);
            wantToCloseDialog=false;
            customLayout = getLayoutInflater().inflate(R.layout.appro_user_one, null);
             final TextView userView = customLayout.findViewById(R.id.userNameView);
            final TextView emailView = customLayout.findViewById(R.id.emailView);
            final TextView addressView = customLayout.findViewById(R.id.addressView);
            final TextView phoneView = customLayout.findViewById(R.id.phonrNoView);
            final TextView dobView = customLayout.findViewById(R.id.dobView);
            final TextView passwordView = customLayout.findViewById(R.id.passwordView);
            final TextView roleView = customLayout.findViewById(R.id.roleView);
            final TextView countryView = customLayout.findViewById(R.id.countryView);
            final TextView idView = customLayout.findViewById(R.id.countryView);

            final TextView userEdit = customLayout.findViewById(R.id.userNameEdit);
            final TextView emailEdit = customLayout.findViewById(R.id.emailEdit);
            final TextView addressEdit = customLayout.findViewById(R.id.addressEdit);
            final TextView phoneEdit = customLayout.findViewById(R.id.phoneNoEdit);
            final TextView dobEdit = customLayout.findViewById(R.id.dobEdit);
            final TextView passwordEdit = customLayout.findViewById(R.id.passwordEdit);
            final Spinner roleEdit = customLayout.findViewById(R.id.roleEdit);
            final Spinner countryEdit = customLayout.findViewById(R.id.countryEdit);
            final TextView idEdit = customLayout.findViewById(R.id.idEdit);

            scrollOneView=customLayout.findViewById(R.id.approvalOneViewScroll);
            scrollOneView.setOnTouchListener(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View view, MotionEvent ev)
                {
                    hideKeyboard(view);
                    return false;
                }
            });

            ArrayList<String> location = retriveDropDown("Location", "DropDown_IMP", inventoryDatabase);
            ArrayAdapter<String> locationL = new ArrayAdapter<>(this, R.layout.spinner_style, R.id.textview, location);
            countryEdit.setPrompt(locationH);
            countryEdit.setAdapter(locationL);
            ArrayList<String> role = retriveDropDown("Role", "Role_Table",genericDatabase);
            ArrayAdapter<String> roleL = new ArrayAdapter<>(this, R.layout.spinner_style, R.id.textview, role);
            roleEdit.setPrompt(roleH);
            roleEdit.setAdapter(roleL);

            ResultSet result = cc.sqlModule(selectOperation, userDetailsH, allColumns, "EmailID='" + email + "'",
                    genericDatabase);
            while (result.next()) {
                userEdit.setText(result.getString("FirstName")+" "+result.getString("LastName"));
                emailEdit.setText(result.getString("EmailID"));
                addressEdit.setText(result.getString("Address"));
                phoneEdit.setText(result.getString("PhoneNo"));
                dobEdit.setText(result.getString("DateOfBirth"));
                passwordEdit.setText(result.getString("Password"));
            }
            builder.setView(customLayout).setCancelable(false)
                    .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            dialog = builder.create();
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (idEdit.getText().toString().isEmpty() || roleEdit.getSelectedItem().toString().equals("-select-")
                                || countryEdit.getSelectedItem().toString().equals("-select-")){
                             String warning = "";
                            if (idEdit.getText().toString().isEmpty()) {
                                warning = "Enter Employee ID";
                            }else if (roleEdit.getSelectedItem().toString().equals("-select-")) {
                                warning = "Choose Employee Role";
                            } else if (countryEdit.getSelectedItem().toString().equals("-select-")) {
                                warning = "Choose Country";
                            }
                            Toast.makeText(ApprovalUser.this, warning, Toast.LENGTH_SHORT).show();
                        } else {
                            String[] empolyeeDetailsColumns = {"EmployeeID", "FirstName", "LastName", "Address","EmailID","PhoneNo", "DateOfBirth","Country"};
                            String[] empolyeeDetailsValues = {idEdit.getText().toString(),
                                    firstWord(userEdit.getText().toString()),  lastWord(userEdit.getText().toString()),
                                    addressEdit.getText().toString(), emailEdit.getText().toString(),phoneEdit.getText().toString(),dobEdit.getText().toString(),
                                    countryEdit.getSelectedItem().toString()};
                            int result = cc.sqlModule(insertOperation,empoyee_DetailsH,empolyeeDetailsColumns,null,empolyeeDetailsValues,genericDatabase);
                            if (result >= 1) {
                                Toast.makeText(ApprovalUser.this, "New User Approved", Toast.LENGTH_LONG).show();
                                String[] userDetailsCol={"Status"};
                                String[] userDetailsValues={"Approved"};
                                String[] loginCol={"EmployeeID","Employee_name","Password","Role"};
                                String[] loginValues={idEdit.getText().toString(),userEdit.getText().toString(),passwordEdit.getText().toString(),
                                        roleEdit.getSelectedItem().toString()};
                                cc.sqlModule(updateOperation,userDetailsH,userDetailsCol,"EmailID='"+email+"'",userDetailsValues,genericDatabase);
                                cc.sqlModule(insertOperation,loginTableH,loginCol,null,loginValues,genericDatabase);
                                 recreateActivity();
                            } else {
                                Toast.makeText(ApprovalUser.this, "New User Not Approved", Toast.LENGTH_LONG).show();
                            }
                            wantToCloseDialog = true;
                        }
                        if (wantToCloseDialog)
                            dialog.dismiss();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean wantToCloseDialog(String user_details, String s) {
        return true;
    }
}
