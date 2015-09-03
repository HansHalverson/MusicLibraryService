package database;

public class Column {

    private String columnName;
    private ColumnType columnType;

    public Column(String columnName, ColumnType columnType) {
        this.columnName = columnName;
        this.columnType = columnType;
    }

    public String getColumnName() {
        return columnName;
    }

    public ColumnType getColumnType() {
        return columnType;
    }

    public enum ColumnType {
        INT,
        STRING
    }
}
