package org.example.constants;

public class ResourcesConstants {
    private static final String BASE_RESOURCES_URL = "http://192.168.175.100/";
    public static String getResourcesUrl(String fileId) {
        return BASE_RESOURCES_URL.concat(fileId);
    }
}
