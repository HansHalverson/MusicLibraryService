package util;

import javax.servlet.http.HttpServletRequest;

public class UrlUtil {

    public static String getPathSegment(HttpServletRequest request, int segmentNumber) {
        String segment = null;

        String pathInfo = request.getPathInfo();
        if (pathInfo != null) {
            String[] pathSections = pathInfo.substring(1).split("/");
            if (pathSections.length >= segmentNumber) {
                segment = pathSections[segmentNumber - 1];
            }
        }

        return segment;
    }
}
