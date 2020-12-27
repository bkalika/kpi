package com.javacourse.pdb.laba2;

import java.util.ArrayList;

/**
 * Created by bkalika on 28.09.2020.
 */

public class SmartPhone extends Phone {

    public String phoneClass;
    private double price;
    public String type;

//    private void cleanSmartPhone() {
//        System.out.println("successfully clean phone");
//    }

    @Override
    public void call(Phone incomingCall) {
        super.call(incomingCall);
    }

    //    public String getPhoneClass() {
//        return phoneClass;
//    }
    public void setPhoneClass(String phoneClass) {
        this.phoneClass = phoneClass;
    }
    //    public double getPrice() {
//        return price;
//    }
    public void setPrice(double price) {
        this.price = price;
    }
    //    public String getType() {
//        return type;
//    }
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SmartPhone {" +
                "phoneClass='" + phoneClass + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", price=" + price +
                ", operator='" + operator + '\'' +
                ", type='" + type + '\'' +
                ", status=" + status +
                ", balance=" + balance +
                '}';
    }

//    @Override
//    public int hashCode() {
//        return super.hashCode();
//    }

//    @Override
//    public boolean equals(Object obj) {
//        return super.equals(obj);
//    }
//
//    @Override
//    protected Object clone() throws CloneNotSupportedException {
//        return super.clone();
//    }
//
//    @Override
//    protected void finalize() throws Throwable {
//        super.finalize();
//    }

    public SmartPhone(
            String phoneNumber,
            String operator,
            String phoneClass,
            double price,
            String type) {
        super(phoneNumber, operator);
        this.phoneClass = phoneClass;
        this.price = price;
        this.type = type;
    }

    public SmartPhone(
            String phoneNumber,
            String operator,
            int status,
            double balance,
            String incomingCall,
            String lastMessage,
            ArrayList messages,
            String phoneClass,
            double price,
            String type) {
        super(phoneNumber, operator, status, balance, incomingCall, lastMessage, messages);
        this.phoneClass = phoneClass;
        this.price = price;
        this.type = type;
    }
    public SmartPhone() {
        super();
    }
}
