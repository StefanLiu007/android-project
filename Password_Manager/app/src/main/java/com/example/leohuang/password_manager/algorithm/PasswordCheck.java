package com.example.leohuang.password_manager.algorithm;

import java.util.regex.Pattern;

public class PasswordCheck {
    private String password = null;
    private Pattern patternChars = Pattern.compile("[a-zA-Z]{6,32}");
    private Pattern patternNum = Pattern.compile("^\\d{6,32}$");
    private Pattern patternNumChars = Pattern.compile("[a-z0-9A-Z]{6,32}");

    public PasswordCheck(String paramString) {
        this.password = paramString;
    }

    public int check() {
        if (this.password == null)
            return -1;
        if (this.password.length() <= 0)
            return -1;
        if (this.password.length() < 6)
            return 1;
        if (this.patternNum.matcher(this.password).matches())
            return 1;
        if (this.patternChars.matcher(this.password).matches())
            return 1;
        if (this.patternNumChars.matcher(this.password).matches())
            return 2;
        return 3;
    }

    public String getPassword() {
        return this.password;
    }
}
