package com.kpi;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.DeleteErrorException;
import com.dropbox.core.v2.files.DeleteResult;
import com.dropbox.core.v2.files.FileMetadata;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class File{

    Client client = new Client();

    public void upload(String path) throws IOException, DbxException {
        // Upload "image_example.jpg" to Dropbox
        try (InputStream in = new FileInputStream(path)) {
            FileMetadata metadata = client.dbxClient().files().uploadBuilder("/" + path)
                    .uploadAndFinish(in);
            System.out.println("The file " + path + " was uploaded.");
        }
    }

    public void delete(String path) {
        try {
            DeleteResult deletedFile = client.dbxClient().files().deleteV2(path);
            System.out.println("The file " + deletedFile.getMetadata().getName() + " was deleted.");
        } catch (DeleteErrorException e) {
            e.printStackTrace();
            System.out.println("File not found");
        } catch (DbxException e) {
            e.printStackTrace();
        }
    }
}
