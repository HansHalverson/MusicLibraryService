package util;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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

    public static Map<String, String> getQueryParameters(HttpServletRequest request) {
        Map<String, String> queryParams = new HashMap<>();

        Map<String, String[]> params = request.getParameterMap();
        for (String key : params.keySet()) {
            if (params.get(key).length == 1) {
                queryParams.put(key, params.get(key)[0]);
            }
        }

        return queryParams;
    }
}
