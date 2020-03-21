package com.mtem.asset_management_app;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.StringTokenizer;

public class NumberTextWatcherWithSeperator implements TextWatcher {

    private EditText editText;
    public NumberTextWatcherWithSeperator(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        try {
            editText.removeTextChangedListener(this);
            String value = editText.getText().toString();

            if (!value.equals("")) {

                if (value.startsWith(".")) {
                    editText.setText("0.");
                }
                if (value.startsWith("0") && !value.startsWith("0.")) {
                    editText.setText("");

                }

                String str = editText.getText().toString().replaceAll(",", "");
                if (!value.equals(""))
                    editText.setText(getDecimalFormattedString(str));
                editText.setSelection(editText.getText().toString().length());
            }
            editText.addTextChangedListener(this);
        } catch (Exception ex) {
            ex.printStackTrace();
            editText.addTextChangedListener(this);
        }
    }

    private static String getDecimalFormattedString(String value) {
        StringTokenizer lst = new StringTokenizer(value, ".");
        String str1 = value;
        String str2 = "";
        if (lst.countTokens() > 1) {
            str1 = lst.nextToken();
            str2 = lst.nextToken();
        }
        String str3 = "";
        int first = 0;
        int second = -1 + str1.length();
        if (str1.charAt(-1 + str1.length()) == '.') {
            second--;
            str3 = ".";
        }
        for (int third = second; ; third--) {
            if (third < 0) {
                if (str2.length() > 0)
                    str3 = str3 + "." + str2;
                return str3;
            }
            if (first == 3) {
                str3 = "," + str3;
                first = 0;
            }
            str3 = str1.charAt(third) + str3;
            first++;
        }

    }

}