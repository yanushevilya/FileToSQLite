import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class DBHelper {
    // Константа, в которой хранится адрес подключения
    private static final String CON_STR = "jdbc:sqlite:C:\\Users\\yanus\\IdeaProjects\\FileToSQLite\\out\\production\\FileToSQLite\\business.s3db";

    // Используем шаблон одиночка, чтобы не плодить множество
    // экземпляров класса DbHelper
    private static DBHelper instance = null;

    public static synchronized DBHelper getInstance() throws SQLException {
        if (instance == null)
            instance = new DBHelper();
        return instance;
    }

    // Объект, в котором будет храниться соединение с БД
    private Connection connection;

    private DBHelper() throws SQLException {
        // Регистрируем драйвер, с которым будем работать
        // в нашем случае Sqlite
        DriverManager.registerDriver(new JDBC());
        // Выполняем подключение к базе данных
        this.connection = DriverManager.getConnection(CON_STR);
    }

    public List<Business> getAllProducts() {

        // Statement используется для того, чтобы выполнить sql-запрос
        try (Statement statement = this.connection.createStatement()) {
            // В данный список будем загружать наши продукты, полученные из БД
            List<Business> businesses = new ArrayList<Business>();
            // В resultSet будет храниться результат нашего запроса,
            // который выполняется командой statement.executeQuery()
            ResultSet resultSet = statement.executeQuery("SELECT _id, name, address, kved, stan FROM fop");
            // Проходимся по нашему resultSet и заносим данные в products
            while (resultSet.next()) {
                businesses.add(new Business(resultSet.getInt("_id"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("kved"),
                        resultSet.getString("stan")));
            }
            // Возвращаем наш список
            return businesses;

        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустую коллекцию
            return Collections.emptyList();
        }
    }

    // Добавление продукта в БД
    public void addBusines(Business business) {
        // Создадим подготовленное выражение, чтобы избежать SQL-инъекций
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO fop(`name`, `address`, `kved`, `stan`) " +
                        "VALUES(?, ?, ?, ?)")) {
            statement.setObject(1, business.name);
            statement.setObject(2, business.address);
            statement.setObject(3, business.kved);
            statement.setObject(4, business.stan);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Удаление продукта по id
    public void deleteProduct(int id) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM Products WHERE _id = ?")) {
            statement.setObject(1, id);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
