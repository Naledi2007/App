package com.mycompany.app;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author Student
 */
public class LoginTest {
    @Test
    public void testCorrectUsername() {

        LoginDetails user = new LoginDetails("Kyle", "Smith","kyl_1","Ch&&sec@ke99!","+27838968976" );
        assertTrue(user.checkUserName());
    }

    @Test
    public void testIncorrectUsername() {

        LoginDetails user = new LoginDetails("Kyle", "Smith","kyle!!!!!!!","Ch&&sec@ke99!","+27838968976" );                     
        assertFalse(user.checkUserName());
    }

    @Test
    public void testCorrectPassword() {

        LoginDetails user = new LoginDetails("Kyle", "Smith","kyl_1","Ch&&sec@ke99!","+27838968976" );
        assertTrue(user.checkPasswordComplexity());
    }

    @Test
    public void testIncorrectPassword() {

        LoginDetails user = new LoginDetails("Kyle", "Smith","kyl_1","password","+27838968976" );
                assertFalse(user.checkPasswordComplexity());
    }

    @Test
    public void testCorrectCellPhoneNumber() {

        LoginDetails user = new LoginDetails("Kyle", "Smith","kyl_1","Ch&&sec@ke99!","+27838968976" );               
        assertTrue(user.checkCellPhoneNumber());
    }

    @Test
    public void testIncorrectCellPhoneNumber() {

        LoginDetails user = new LoginDetails("Kyle", "Smith","kyl_1","Ch&&sec@ke99!", "08966553");               
        assertFalse(user.checkCellPhoneNumber());
    }

    @Test
    public void testSuccessfulLogin() {

        LoginDetails user = new LoginDetails("Kyle", "Smith","kyl_1","Ch&&sec@ke99!","+27838968976" );                
        assertTrue(user.loginUser("kyl_1", "Ch&&sec@ke99!"));
                    
    }

    @Test
    public void testUnsuccessfulLogin() {

        LoginDetails user = new LoginDetails("Kyle", "Smith","kyl_1","Ch&&sec@ke99!","+27838968976" );                     
        assertFalse(user.loginUser("wrong", "wrong"));
               
    }

    @Test
    public void testSuccessfulLoginMessage() {

        LoginDetails user = new LoginDetails("Kyle", "Smith","kyl_1","Ch&&sec@ke99!","+27838968976" );               
        assertEquals("Welcome Kyle Smith, it is great to see you again.",user.returnLoginStatus( "kyl_1","Ch&&sec@ke99!"));              
                  
    }

    @Test
    public void testUnsuccessfulLoginMessage() {

        LoginDetails user = new LoginDetails("Kyle", "Smith","kyl_1","Ch&&sec@ke99!","+27838968976" );               
        assertEquals("Username or password incorrect, please try again.",user.returnLoginStatus("wrong","wrong"));
                       
    }
}