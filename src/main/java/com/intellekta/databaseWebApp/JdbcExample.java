package com.intellekta.databaseWebApp;

import java.sql.*;

public class JdbcExample {
    private final static String url = "jdbc:mysql://localhost:3306/databasewebapp";
    private final static String username = "root";
    private final static String password = "password";
    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {

            ResultSet countResultSet = stmt.executeQuery("SELECT COUNT(*) FROM sales");
            countResultSet.next();
            int rowCount = countResultSet.getInt(1);
            System.out.println("Количество записей: " + rowCount);

            int saleId = 1;
            ResultSet saleResultSet = stmt.executeQuery("SELECT * FROM sales WHERE id = " + saleId);
            saleResultSet.next();
            int id = saleResultSet.getInt("id");
            double price = saleResultSet.getDouble("price");
            String purchaseDate = saleResultSet.getString("purchase_date");
            String saleDate = saleResultSet.getString("sale_date");
            int productId = saleResultSet.getInt("product_id");

            System.out.println("Информация о продаже с id " + id);
            System.out.println("Сумма: " + price);
            System.out.println("Дата поступления товара: " + purchaseDate);
            System.out.println("Дата продажи: " + saleDate);
            System.out.println("Идентификатор товара: " + productId);

            addSale(1999.99, "2023-09-04", "2023-09-04", 3);

            getSalesWithPriceGreaterThan100();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addSale(double price, String purchaseDate, String saleDate, int productId) {
        String sql = "INSERT INTO sales (price, purchase_date, sale_date, product_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, price);
            stmt.setString(2, purchaseDate);
            stmt.setString(3, saleDate);
            stmt.setInt(4, productId);

            int rowCount = stmt.executeUpdate();

            if (rowCount > 0) {
                System.out.println("New sale record added successfully.");
            } else {
                System.out.println("Failed to add new sale record.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getSalesWithPriceGreaterThan100() {
        String sql = "SELECT * FROM sales WHERE price > 100";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {

            ResultSet resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                double price = resultSet.getDouble("price");
                String purchaseDate = resultSet.getString("purchase_date");
                String saleDate = resultSet.getString("sale_date");
                int productId = resultSet.getInt("product_id");
                String saleInfo = "ID: " + id + ", Price: " + price + ", Purchase Date: " + purchaseDate + ", Sale Date: " + saleDate + ", Product ID: " + productId;

                System.out.println(saleInfo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
