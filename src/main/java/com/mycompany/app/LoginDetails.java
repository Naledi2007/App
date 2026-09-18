package com.mycompany.app;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Student
 */
public class LoginDetails {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellPhoneNumber;

    public LoginDetails(String firstName, String lastName, String username,String password, String cellPhoneNumber) {
                       
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public boolean checkUserName() {

        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity() {

        boolean hasCapitalLetter = false;
        boolean hasNumber = false;
        boolean hasSpecialCharacter = false;

        if (password.length() < 8) {
            return false;
        }

        for (int i = 0; i < password.length(); i++) {

            char character = password.charAt(i);

            if (Character.isUpperCase(character)) {
                hasCapitalLetter = true;
            }

            if (Character.isDigit(character)) {
                hasNumber = true;
            }

            if (!Character.isLetterOrDigit(character)) {
                hasSpecialCharacter = true;
            }
        }

        return hasCapitalLetter && hasNumber && hasSpecialCharacter;
    }

    public boolean checkCellPhoneNumber() {

        String cellPhoneRegex = "^\\+27[6-8][0-9]{8}$";

        return cellPhoneNumber.matches(cellPhoneRegex);
    }

    public String registerUser() {

        if (!checkUserName()) {

            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";

        } else if (!checkPasswordComplexity()) {

            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";

        } else if (!checkCellPhoneNumber()) {

            return "Cell phone number is incorrectly formatted or does not contain an international code.";

        } else {

            return "User successfully registered.";
        }
    }

    public boolean loginUser(String enteredUsername, String enteredPassword) {

        return enteredUsername.equals(username)
                && enteredPassword.equals(password);
    }

    public String returnLoginStatus(String enteredUsername, String enteredPassword) {

        if (loginUser(enteredUsername, enteredPassword)) {

            return "Welcome " + firstName + " " + lastName
                    + ", it is great to see you again.";

        } else {

            return "Username or password incorrect, please try again.";
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}