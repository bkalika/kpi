package com.kpi;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.*;
import com.dropbox.core.v2.users.FullAccount;

public class Folder {

    Client client = new Client();

    public void create(String name) {

        try {
//            CreateFolderResult folder = client.dbxClient().files().createFolderV2("/createFolder4");
            CreateFolderResult folder = client.dbxClient().files().createFolderV2(name);
            System.out.println("Folder was created: " + folder.getMetadata().getName());
        } catch (
                CreateFolderErrorException err) {
            if (err.errorValue.isPath() && err.errorValue.getPathValue().isConflict()) {
                System.out.println("Something already exists at the path.");
            } else {
                System.out.print("Some other CreateFolderErrorException occurred...");
                System.out.print(err.toString());
            }
        } catch (Exception err) {
            System.out.print("Some other Exception occurred...");
            System.out.print(err.toString());
        }
    }

    public void delete(String name) {

        try {
            DeleteResult folder = client.dbxClient().files().deleteV2(name);
            System.out.println("Folder " + folder.getMetadata().getName() + " was deleted!");
        } catch (
                DeleteErrorException err) {
            System.out.println("Folder does not exist..");
        } catch (Exception err) {
            System.out.print("Some other Exception occurred...");
            System.out.print(err.toString());
        }
    }

    public void getAccountInfo() throws DbxException {
        // Get current account info
        FullAccount account = client.dbxClient().users().getCurrentAccount();
        System.out.println(account.getName().getDisplayName());

    }

    public void getContent() throws DbxException {

        // Get files and folder metadata from Dropbox root directory
        ListFolderResult result = client.dbxClient().files().listFolder("");
        while (true) {
            for (Metadata metadata : result.getEntries()) {
                System.out.println(metadata.getPathLower());
            }

            if (!result.getHasMore()) {
                break;
            }

            result = client.dbxClient().files().listFolderContinue(result.getCursor());
        }
    }

}
