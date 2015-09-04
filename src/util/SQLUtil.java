package util;

import database.Column;
import repository.Repository;

import javax.json.JsonArray;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SQLUtil {

    @SuppressWarnings("unchecked")
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

    public static String createColumnList(ArrayList<String> keys, Map<String, Column> keysToColumns) {
        StringBuilder builder = new StringBuilder("(");
        for (int i = 0; i < keys.size() - 1; i++) {
            builder.append(keysToColumns.get(keys.get(i)).getColumnName());
            builder.append(", ");
        }
        builder.append(keysToColumns.get(keys.get(keys.size() - 1)).getColumnName());
        builder.append(")");
        return builder.toString();
    }

    public static String createInsertionTemplate(int numColumns, int numEntries) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < numEntries - 1; i++) {
            builder.append("(");
            for (int j = 0; j < numColumns - 1; j++) {
                builder.append("?, ");
            }
            builder.append("?), ");
        }

        builder.append("(");
        for (int i = 0; i < numColumns - 1; i++) {
            builder.append("?, ");
        }
        builder.append("?)");

        return builder.toString();
    }
}
