package com.imag.master_groups.utils;

import com.imag.master_groups.exception.UserDefinedException;

import java.util.regex.Pattern;

public class Validation {

    public static final String mobileNumber = "[6-9][0-9]{9}";
    public static final String gmail = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
    public static final String name = "[a-zA-Z ]*";
    public static final String uSMobileNum = "^(?:\\+?1[-.\\s]?)?([0-9]{3})[\\s.-]?([0-9]{3})[\\s.-]?([0-9]{4})$";
    public static final String password = "[a-zA-Z0-9@*!#$]{9,20}";

    public static String mobileValidation(String mobileNumber) {
        if (mobileNumber != null && !mobileNumber.isEmpty()) {
            if (Pattern.matches(Validation.mobileNumber, mobileNumber) || Pattern.matches(Validation.uSMobileNum, mobileNumber))
                return mobileNumber;
            else throw new UserDefinedException("Invalid Phone number....!");
        } else throw new UserDefinedException("Phone number can't be null or empty...!");
    }

    public static String mailValidation(String gmail) {
        if (gmail != null && !gmail.isEmpty()) {
            if (gmail.length() <= 30) {
                if (Pattern.matches(Validation.gmail, gmail)) return gmail;
                else throw new UserDefinedException("Invalid email....!");
            } else throw new UserDefinedException("length of email should be less than or equal to 30");
        } else throw new UserDefinedException("Email can't be null or empty...!");
    }

    public static String nameValidation(String name) {
        if (name != null && !name.isEmpty()) {
            if (name.length() <= 30) {
                if (Pattern.matches(Validation.name, name)) return name;
                else throw new UserDefinedException("Invalid name...!");
            } else throw new UserDefinedException("length of the name should be less than or equal to 30");
        } else throw new UserDefinedException("Name can't be null or empty...!");
    }

    public static String passwordValidation(String password) {
        if (password != null && !password.isEmpty()) {
            if (password.length() > 8 && password.length() < 21) {
                if (Pattern.matches(Validation.password, password)) {
                    return password;
                } else throw new UserDefinedException("Invalid password format...!");
            } else
                throw new UserDefinedException("Password should be min 9 char's and max 20");
        } else throw new UserDefinedException("Password can't be null or empty...!");
    }

    public static String addressValidation(String address) {
        if (address != null && !address.isEmpty()) {
            if (!address.isBlank()) {
                return address;
            } else throw new UserDefinedException("Address can't be blank...!");
        } else throw new UserDefinedException("Address can't be null or empty...!");
    }

    public static String descriptionValidation(String description) {
        if (description != null && !description.isEmpty()) {
            if (!description.isBlank()) {
                if (description.length() < 151)
                    return description;
                else
                    throw new UserDefinedException("length of the description should be less than or equal to 150 char's");
            } else throw new UserDefinedException("Description can't be blank...!");
        } else throw new UserDefinedException("Description can't be null or empty...!");
    }

}
