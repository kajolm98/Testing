package com.mtem.asset_management_app;

import androidx.appcompat.app.AppCompatActivity;

import org.junit.Test;

import static org.junit.Assert.*;

public class ApprovalUserTest {
ApprovalUser approvalUser = new ApprovalUser();

    @Test
    public void showAlertDialogForAddTravelDetail() {
    boolean isValid = approvalUser.wantToCloseDialog("User Details","info.mtem.co.in");
    assertEquals(true,isValid);
    }
}