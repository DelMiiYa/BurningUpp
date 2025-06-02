package com.example.burnoutapp;

import java.util.Map;

public class UserModel {
    private String name;
    private String birthday;
    private String gender;
    private Integer burnoutLevel;
    private Map<String, Object> advancedTest;

    public UserModel(String name, String birthday, String gender, Integer burnoutLevel, Map<String, Object> advancedTest) {
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.burnoutLevel = burnoutLevel;
        this.advancedTest = advancedTest;
    }

    public String getName() { return name; }
    public String getBirthday() { return birthday; }
    public String getGender() { return gender; }
    public Integer getBurnoutLevel() { return burnoutLevel; }
    public Map<String, Object> getAdvancedTest() { return advancedTest; }
}
