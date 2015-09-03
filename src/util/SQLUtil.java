package util;

import database.Column;
import repository.Repository;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SQLUtil {

    public static Map<Column, String> formatQueryParams(Repository repository, Map<String, String> queryParams) throws MusicLibraryRequestException {
        Map<Column, String> sqlParams = new HashMap<>();
        Map<String, Column> queryParamsToColumns = repository.getParamsToColumns();

        for (String key : queryParams.keySet()) {
            if (queryParamsToColumns.containsKey(key)) {
                sqlParams.put(queryParamsToColumns.get(key), queryParams.get(key));
            } else {
                throw new MusicLibraryRequestException(HttpServletResponse.SC_BAD_REQUEST, "Unknown query parameter \"" + key + "\".");
            }
        }

        return sqlParams;
    }

    public static String createQueryTemplate(ArrayList<Column> columns) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < columns.size() - 1; i++) {
            builder.append(columns.get(i).getColumnName());
            builder.append(" = ? AND ");
        }
        builder.append(columns.get(columns.size() - 1).getColumnName());
        builder.append(" = ?");
        return builder.toString();
    }
}
