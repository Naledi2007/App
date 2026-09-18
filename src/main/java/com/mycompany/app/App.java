/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.app;
import java.util.Scanner;

/**
 *
 * @author Student
 */
public class App {

   public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("===== Registration =====");

        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter a username: ");
        String username = scanner.nextLine();

        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        System.out.print("Enter your cell phone number:");
        String cellPhoneNumber = scanner.nextLine();

        LoginDetails user = new LoginDetails(firstName,lastName,username,password,cellPhoneNumber);

        System.out.println();

        if (user.checkUserName()) {
            System.out.println("Username successfully captured.");
        } else {
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
        }

        if (user.checkPasswordComplexity()) {
            System.out.println("Password successfully captured.");
        } else {
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
        }

        if (user.checkCellPhoneNumber()) {
            System.out.println("Cell phone number successfully added.");
        } else {
            System.out.println("Cell phone number is incorrectly formatted or does not contain an international code.");
        }

        System.out.println();

        if (user.checkUserName()
                && user.checkPasswordComplexity()
                && user.checkCellPhoneNumber()) {

            System.out.println("User successfully registered.");

            System.out.println();
            System.out.println("===== Login =====");

            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine();

            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();

            System.out.println(
                    user.returnLoginStatus(loginUsername, loginPassword)
            );

        } else {

            System.out.println("Registration unsuccessful.");
        }

        scanner.close();
    }
}
