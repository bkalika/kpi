package com.google.api.services.samples.drive.cmdline.queries;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class DriveFile {
    protected Drive service;

    public DriveFile(Drive service) {
        this.service = service;
    }

    public void uploadFile(String pathName) throws IOException {
        File fileMetadata = new File();

        fileMetadata.setName(pathName);
        java.io.File filePath = new java.io.File(pathName);
        FileContent mediaContent = new FileContent("image/jpeg", filePath);
        File file = service.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();
        System.out.println("File ID: " + file.getId());
    }

    public void deleteFileOrFolder(String name) {
        try {
            String fileId = searchFile(name);
            service.files().delete(fileId).execute();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
    }

    public void createFileInFolder(String folderName, String pathName) throws IOException{

        String folderId = searchFile(folderName);
        System.out.println("FolderId: " + folderId);
        File fileMetadata = new File();
        fileMetadata.setName(pathName);
        fileMetadata.setParents(Collections.singletonList(folderId));
        System.out.println("fileMetadata: " + fileMetadata);
        java.io.File filePath = new java.io.File(pathName);
        FileContent mediaContent = new FileContent("image/jpeg", filePath);
        File file = service.files().create(fileMetadata, mediaContent)
                .setFields("id, parents")
                .execute();
        System.out.println("File ID: " + file.getId());
    }

    public void moveFileBetweenFolders(String targetFolder, String pathName) throws IOException{
        String fileId = searchFile(pathName);
        String folderId = searchFile(targetFolder); // folderId for moving the file

        // Retrieve the existing parents to remove
        File file = service.files().get(fileId)
                .setFields("parents")
                .execute();
        StringBuilder previousParents = new StringBuilder();
        for (String parent : file.getParents()) {
            previousParents.append(parent);
            previousParents.append(',');
        }
        // Move the file to the new folder
        file = service.files().update(fileId, null)
                .setAddParents(folderId)
                .setRemoveParents(previousParents.toString())
                .setFields("id, parents")
                .execute();
    }

    private String searchFile(String searchFile) throws IOException {
        String pageToken = null;
        String fileId = null;
        DriveBasicReadQueries query = new DriveBasicReadQueries(service);

        do {
            FileList result = service.files().list()
                    .setQ("mimeType='image/jpeg'")
                    .setSpaces("drive")
                    .setFields("nextPageToken, files(id, name)")
                    .setPageToken(pageToken)
                    .execute();

            for(File fileInRoot : query.listFoldersInRoot()){
                if(fileInRoot.getName().equals(searchFile)){
                    fileId = fileInRoot.getId();
                }
            }
            if (fileId == null){
                for (File file : result.getFiles()) {
                    if(file.getName().equals(searchFile)){
                        System.out.printf("Found: %s (%s)\n",
                                file.getName(), file.getId());
                        fileId = file.getId();
                    }
                }
            }




            pageToken = result.getNextPageToken();
            return fileId;

        } while (pageToken != null);

    }

}
