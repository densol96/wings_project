package lv.wings.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.NonNull;

@Component
public class UrlAssembler {

    private static String frontendDomain;
    private static String backendDomain;


    @Value("${frontend.url}")
    public void setFrontendDomain(String url) {
        UrlAssembler.frontendDomain = url;
    }

    @Value("${backend.url}")
    public void setBackendDomain(String url) {
        UrlAssembler.backendDomain = url;
    }

    public static String getFrontendDomain() {
        return frontendDomain;
    }

    public static String getBackendDomain() {
        return backendDomain;
    }

    /**
     * Returns a full frontend URL by appending the given path to the frontend domain.
     *
     * @param path the relative path that must start with a forward slash (e.g. {@code /login}, {@code /profile})
     * @return full frontend URL as a string
     *
     * @throws IllegalArgumentException if {@code path} does not start with "/"
     * @throws NullPointerException if {@code path} is null "/"
     */
    public static String getFullFrontendPath(@NonNull String path) {
        if (!path.startsWith("/")) {
            throw new IllegalArgumentException("Path must start with '/'");
        }
        return frontendDomain + path;
    }
}
