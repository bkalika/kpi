package com.kpi;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

public class Client {
    public DbxClientV2 dbxClient(){
        final String ACCESS_TOKEN = "N_mbTDVsmTgAAAAAAAAAAfkPrdPSm7OOV3EikxKxI8_TUYDhB811qKRhcyN9zZyY";

        // Create Dropbox client
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
        return client;
    }
}
