package com.javacourse.pdb.laba2;

import java.io.*;
/**
 * Created by bkalika on 28.09.2020.
 */

public class Main {

    public static void main(String[] args) throws IOException {

        InputStream inputStream = System.in;
        Reader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        SmartPhone samsung  = new SmartPhone(
                "0938723960",
                "Vodafone",
                "A",
                2000,
                "Samsung");
        SmartPhone Iphone  = new SmartPhone(
                "0630422778",
                "Lifecell",
                "A",
                2000,
                "IphoneXR");
        samsung.call(Iphone);
        samsung.sendMessage(Iphone," first message from Samsung to iPhone");
        samsung.sendMessage(Iphone," second message from Samsung to iPhone");
        samsung.sendMessage(Iphone," third message from Samsung to iPhone");

        System.out.println("##### ALL MESSAGES #####");
        Iphone.getMessages();

        System.out.println("Last message: " + Iphone.getLastMessage());
        SmartPhone Bohdan = new SmartPhone();
        addNewPhone(Bohdan);
        System.out.println(Bohdan.toString());
        System.out.println("Send message from Bohdan to Samsung and IPHONE. Please enter text Messages:");
        Bohdan.sendMessage(samsung, Iphone, bufferedReader.readLine());

        System.out.println("SAMSUNG MESSAGE " +samsung.getLastMessage()+" IPHONE MESSAGE "+Iphone.getLastMessage());
        System.out.println("Refill account");
        samsung.replenishAccount(Double.parseDouble(bufferedReader.readLine()));
        System.out.println(samsung.toString());
    }


    static void addNewPhone(SmartPhone newSmartPhone) throws IOException
    {
        InputStream inputStream = System.in;
        Reader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        //  SmartPhone newSmartPhone= new SmartPhone("","");

        System.out.println("Enter PhoneNumber: ");
        newSmartPhone.setPhoneNumber(bufferedReader.readLine());
        System.out.println("Enter operator: ");
        newSmartPhone.setOperator(bufferedReader.readLine());
        System.out.println("Enter phoneClass: ");
        newSmartPhone.setPhoneClass(bufferedReader.readLine());
        System.out.println("Enter price: ");
//        double a = Double.parseDouble(bufferedReader.readLine());
//        newSmartPhone.setPrice(a);
        newSmartPhone.setPrice(Double.parseDouble(bufferedReader.readLine()));
        System.out.println("Enter type: ");
        newSmartPhone.setType(bufferedReader.readLine());
    }
}
