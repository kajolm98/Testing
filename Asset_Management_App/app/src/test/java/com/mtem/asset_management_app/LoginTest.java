package com.mtem.asset_management_app;

import android.widget.CheckBox;
import android.widget.CompoundButton;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginTest {
    Login login = new Login();

    @Before
    public void setUp() throws Exception {
        System.out.println("Ready for testing");
    }

    @Test
    public void checkValidation() {
        boolean isValid = login.checkValidation("MTEM_01", "1234");
        assertEquals(true, isValid);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Done with testing ");
    }
}