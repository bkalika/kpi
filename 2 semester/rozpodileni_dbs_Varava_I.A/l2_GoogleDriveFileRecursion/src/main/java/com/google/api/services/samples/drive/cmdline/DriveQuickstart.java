package com.google.api.services.samples.drive.cmdline;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.samples.drive.cmdline.queries.DriveBasicReadQueries;
import com.google.api.services.samples.drive.cmdline.queries.DriveFile;
import com.google.api.services.samples.drive.cmdline.queries.DriveFolder;
import com.google.api.services.samples.drive.cmdline.queries.TargetFilePathsDriveQuery;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.*;

public class DriveQuickstart {
	private static Logger logger = LoggerFactory.getLogger(DriveQuickstart.class);

    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".credentials/2/drive-java-quickstart.json");

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = DriveQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static void main(String... args) throws IOException, GeneralSecurityException {

        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        DriveBasicReadQueries query = new DriveBasicReadQueries(service);
        DriveFolder driveFolder = new DriveFolder(service);
        DriveFile driveFile = new DriveFile(service);


        System.out.println("====Hello. This is a program to work in GoogleDrive. In this program, you can create," +
                "delete files and folders.====");

        Scanner sc = new Scanner(System.in);
        String action;

        boolean working = true;

        while(working){
            System.out.println("Enter a key:\n" +
                    "'C' - for creating a folder,\n" +
                    "'D' - for deleting the folder or file,\n" +
                    "'U' - for uploading a file,\n" +
                    "'S' - for creating a file in specific folder,\n" +
//                    "'R' - for removing the file,\n" +
                    "'G' - for getting all folders in root directory,\n" +
                    "'L' - for getting all files in specific directory,\n" +
                    "'M' - for moving the file to the target folder,\n" +
                    "'H' - for sanity check,\n" +
                    "'Q' - exit from the program:\n");

            action = sc.nextLine();

            switch (action.toLowerCase()){
                case "c":
                    System.out.println("Please, enter a folder name:");
                    action = sc.nextLine();
                    logger.info("////// ATTEMPT TO CREATE A FOLDER //////");
                    driveFolder.createFolder(action);
                    break;
                case "d":
                    System.out.println("Please, enter the folder of file name for deleting:");
                    action = sc.nextLine();
                    driveFile.deleteFileOrFolder(action);
                    break;
                case "u":
                    System.out.println("Please, enter a path with name to the file:");
                    action = sc.nextLine();
                    logger.info("////// ATTEMPT TO UPLOAD THE FILE ///////");
                    driveFile.uploadFile(action);
                    break;
                case "s":
                    System.out.println("Please, enter the target folder:");
                    action = sc.nextLine();
                    String folderName = action;
                    System.out.println("Please, enter a path with name to the file:");
                    action = sc.nextLine();
                    String pathName = action;
                    driveFile.createFileInFolder(folderName, pathName);
                    break;
                case "m":
                    System.out.println("Enter the file name for moving:");
                    action = sc.nextLine();
                    String fileName = action;
                    System.out.println("Enter the target folder:");
                    action = sc.nextLine();
                    String targetFolder = action;
                    driveFile.moveFileBetweenFolders(targetFolder, fileName);
                    break;
                case "h":
                    logger.info("////////// SANITY CHECK //////////");
                    List<File> sanityCheckResults = sanityCheck(service);
                    outputListOfFileResults(sanityCheckResults);
                    break;
                case "q":
                    System.out.println("===Exit. Good luck.===");
                    working = false;
                    break;
                case "l":
                    System.out.println("Please, enter the folder name:");
                    action = sc.nextLine();
                        Map<File, List<File>> listChildItemsOfFolderResults = query.listChildItemsOfFolder(action);
                        logger.info("////////// LIST ALL CHILD ITEMS OF FOLDERS MATCHING NAME //////////");
                        listChildItemsOfFolderResults.forEach((parentFolder, childItems) -> {
                            logger.info(String.format("listing child items of folderName: %s folderId: %s", parentFolder.getName(), parentFolder.getId()));
                            outputListOfFileResults(childItems);
                        });
                        break;
                case "g":
                    List<File> listFoldersInRootResults = query.listFoldersInRoot();
                    logger.info("////////// LIST ALL FOLDERS IN ROOT DIRECTORY //////////");
                    outputListOfFileResults(listFoldersInRootResults);
                    break;
                default:
                    System.out.println("Wrong key. Please, repeat:");
                    break;
            }

//            driveFile.searchFileNew("aaa");

        }



//		TargetFilePathsDriveQuery recursiveSearch = new TargetFilePathsDriveQuery(service, "RyanAir");
		
//		checkFilePath_1(recursiveSearch);
//		checkFilePath_2(recursiveSearch);
//		checkFilePath_3(recursiveSearch);
//		checkFilePath_4(recursiveSearch);
//		checkFilePath_5_illegalArg(recursiveSearch);
	}
    
	private static List<File> sanityCheck(Drive service) throws IOException {
		// Print the names and IDs for up to 10 files.
        FileList result = service.files().list()
                .setPageSize(10)
                .setFields("nextPageToken, files(id, name)")
                .execute();
        List<File> files = result.getFiles();
        return files;
	}

    private static void outputListOfFileResults(List<File> files) {
        if (files == null || files.isEmpty()) {
        	logger.info("No results.");
        } else {
            for (File file : files) {
            	logger.info(String.format("file.getName: %s getId: %s", file.getName(), file.getId()));
            }
		}
    }
    
//	public static void checkFilePath_1(TargetFilePathsDriveQuery recursiveSearch) throws IOException {
//		Queue<String> queue = new LinkedList<String>();
//		queue.offer("RyanAir");
//		queue.offer("Docs");
//		queue.offer("CAE Simuflite Beechjet");
//		queue.offer("Beechjet");
//		queue.offer("DDA");
//		queue.offer("CFI");
//		queue.offer("Flying");
//		List<File> pathOfItemsFromTargetFileToRoot = recursiveSearch.validateTargetFilePath(queue);
//        logger.info("////////// CHECKING THAT FILE PATH EXISTS EXAMPLE 1 //////////");
//	    outputListOfFileResults(pathOfItemsFromTargetFileToRoot);
//	    boolean found = CollectionUtils.isNotEmpty(pathOfItemsFromTargetFileToRoot);
//		logger.info("found " + found);
//	}

//	public static void checkFilePath_2(TargetFilePathsDriveQuery recursiveSearch) throws IOException {
//		Queue<String> queue = new LinkedList<String>();
//		queue.offer("RyanAir");
//		queue.offer("Docs");
//		queue.offer("CAE Simuflite Beechjet");
//		queue.offer("Beechjet");
//		queue.offer("Asus stuff");

//		List<File> pathOfItemsFromTargetFileToRoot = recursiveSearch.validateTargetFilePath(queue);
//        logger.info("////////// CHECKING THAT FILE PATH EXISTS EXAMPLE 2 //////////");
//	    outputListOfFileResults(pathOfItemsFromTargetFileToRoot);
//	    boolean found = CollectionUtils.isNotEmpty(pathOfItemsFromTargetFileToRoot);
//		logger.info("found " + found);
//	}
//
//	private static void checkFilePath_3(TargetFilePathsDriveQuery recursiveSearch) throws IOException {
//		Queue<String> queue = new LinkedList<String>();
//		queue.offer("RyanAir");
//		queue.offer("past-safety-messages");
//		queue.offer("Flight club");

//		List<File> pathOfItemsFromTargetFileToRoot = recursiveSearch.validateTargetFilePath(queue);
//        logger.info("////////// CHECKING THAT FILE PATH EXISTS EXAMPLE 3 //////////");
//		outputListOfFileResults(pathOfItemsFromTargetFileToRoot);
//	    boolean found = CollectionUtils.isNotEmpty(pathOfItemsFromTargetFileToRoot);
//		logger.info("found " + found);
//	}
//
//	private static void checkFilePath_4(TargetFilePathsDriveQuery recursiveSearch) throws IOException {
//		Queue<String> queue = new LinkedList<String>();
//		queue.offer("RyanAir");
////		queue.offer("non-existant-folder");
////		queue.offer("Flight club");
//
//		List<File> pathOfItemsFromTargetFileToRoot = recursiveSearch.validateTargetFilePath(queue);
//        logger.info("////////// CHECKING THAT FILE PATH EXISTS EXAMPLE 4 //////////");
//		outputListOfFileResults(pathOfItemsFromTargetFileToRoot);
//	    boolean found = CollectionUtils.isNotEmpty(pathOfItemsFromTargetFileToRoot);
//		logger.info("found " + found);
//	}
//
//	private static void checkFilePath_5_illegalArg(TargetFilePathsDriveQuery recursiveSearch) throws IOException {
//		Queue<String> queue = new LinkedList<String>();
//		queue.offer("RyanAir-wrong-file-should-throw-illegal-argument-exception");
////		queue.offer("past-safety-messages");
////		queue.offer("Flight club");
//
//		List<File> pathOfItemsFromTargetFileToRoot = null;
//
//		try {
//			pathOfItemsFromTargetFileToRoot = recursiveSearch.validateTargetFilePath(queue);
//		} catch (IllegalArgumentException ex) {
//			logger.error(ex.getMessage());
//		}
//
//        logger.info("////////// CHECKING THAT FILE PATH EXISTS EXAMPLE 5 //////////");
//		outputListOfFileResults(pathOfItemsFromTargetFileToRoot);
//	    boolean found = CollectionUtils.isNotEmpty(pathOfItemsFromTargetFileToRoot);
//		logger.info("found " + found);
//	}

}
