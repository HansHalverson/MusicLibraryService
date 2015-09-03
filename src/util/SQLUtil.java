package util;

import repository.Repository;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class SQLUtil {

    public static Map<String, String> formatQueryParams(Repository repository, Map<String, String> queryParams) throws MusicLibraryRequestException {
        Map<String, String> sqlParams = new HashMap<>();
        Map<String, String> queryParamsToColumnNames = repository.getParamsToColumnNames();

        for (String key : queryParams.keySet()) {
            if (queryParamsToColumnNames.containsKey(key)) {
                sqlParams.put(queryParamsToColumnNames.get(key), queryParams.get(key));
            } else {
                throw new MusicLibraryRequestException(HttpServletResponse.SC_BAD_REQUEST, "Unknown query parameter \"" + key + "\".");
            }
        }

        return sqlParams;
    }

    public static String createQueryTemplate(int numParameters) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numParameters - 1; i++) {
            builder.append("? = ? AND ");
        }
        builder.append("? = ?");
        return builder.toString();
    }
}
