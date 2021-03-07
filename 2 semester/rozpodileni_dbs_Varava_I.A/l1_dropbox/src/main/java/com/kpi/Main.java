package com.kpi;

import com.dropbox.core.DbxException;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws DbxException, IOException {

        File file = new File();
        Folder folder = new Folder();

        System.out.println("====Hello. This is a program to work in Dropbox. In this program, you can create," +
                "delete files and folders.====");

        Scanner sc = new Scanner(System.in);
        String action;

        boolean working = true;

        while(working){
            System.out.println("Enter a key:\n" +
                    "'C' - for creating a folder,\n" +
                    "'D' - for deleting the folder,\n" +
                    "'U' - for uploading a file,\n" +
                    "'R' - for removing the file,\n" +
                    "'G' - for getting all content,\n" +
                    "'I' - for getting user surname,\n" +
                    "'Q' - exit from the program:\n");

            action = sc.nextLine();

            switch (action.toLowerCase()){
                case "c":
                    System.out.println("Please, enter a folder name:");
                    action = "/" + sc.nextLine();
                    folder.create(action);
                    break;
                case "d":
                    System.out.println("Please, enter the folder name for deleting:");
                    action = "/" + sc.nextLine();
                    folder.delete(action);
                    break;
                case "u":
                    System.out.println("Please, enter a path with name to the file:");
                    action = sc.nextLine();
                    file.upload(action);
                    break;
                case "r":
                    System.out.println("Enter the file name for deleting:");
                    action = "/" + sc.nextLine();
                    file.delete(action);
                    break;
                case "q":
                    System.out.println("===Exit. Good luck.===");
                    working = false;
                    break;
                case "g":
                    folder.getContent();
                    System.out.println("");
                    break;
                case "i":
                    folder.getAccountInfo();
                    System.out.println("");
                    break;
                default:
                    System.out.println("Wrong key. Please, repeat:");
                    break;
            }

        }

    }
}
