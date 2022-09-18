import java.sql.SQLException;

public abstract class Manager {
    public abstract boolean check(String ID) throws SQLException;
    public abstract void displayAll() throws SQLException;
    public abstract void search() throws SQLException;
    public abstract void add() throws SQLException;
    public abstract void delete() throws SQLException;
    public abstract void update() throws SQLException;
}
