package com.psl.entity;

// Define a generic User class.
public class User implements java.io.Serializable {

    private static final long serialVersionUID = -295422703255886286L;

    private String name;

    public User() {    }

    public User(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}