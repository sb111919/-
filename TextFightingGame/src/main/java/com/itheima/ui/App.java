package com.itheima.ui;

import static com.itheima.ui.Login.getCode;

public class App {
    public static void main(String[] args) {

     Login l = new Login();
        l.start();
        System.out.println(getCode());
    }
}
