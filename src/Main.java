import java.io.*;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader("c:\\Users\\yanus\\AndroidStudioProjects\\LozovaBusiness\\app\\src\\main\\res\\xml\\fop.xml"));
//            String line;
//            while ( (line = reader.readLine()) !=null) {
//                System.out.println(line);
//            }
//        } catch (IOException e) {
//            System.out.println("Error");
//            e.printStackTrace();
//        }

        try {
            // Создаем экземпляр по работе с БД
            DBHelper dbHelper = DBHelper.getInstance();

            // Добавляем запись
            dbHelper.addBusines(new Business(1, "name", "Kiiv", "20.0.7", "working"));

            // Получаем все записи и выводим их на консоль
            List<Business> businesses = dbHelper.getAllProducts();
            for (Business business : businesses) {
                System.out.println(business.toString());
            }

            // Удаление записи с id = 8
            //dbHelper.deleteProduct(8);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}