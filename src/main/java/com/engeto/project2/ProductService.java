package com.engeto.project2;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    Connection connection;

    public ProductService() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/eshop",
                "eshop_crud",
                "Fotbalek123*");
    }

    public List <Product> getAllProducts() throws SQLException {
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM product");

        List < Product > resultList = new ArrayList < > ();

        while (resultSet.next()) {
            Product product = extractProductData(resultSet);

            resultList.add(product);
        }
        return resultList;
    }

    private Product extractProductData(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getLong("id"),
                resultSet.getInt("partNo"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getBoolean("isForSale"),
                resultSet.getBigDecimal("price"));
    }
    public Product getProductById(Long productId) throws SQLException {
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM product WHERE id = " + productId);

        if (resultSet.next()) {
            return extractProductData(resultSet);
        }
        return null;
    }

    public Long saveNewProduct(Product newProduct) throws SQLException {
        Statement statement = connection.createStatement();

        statement.executeUpdate("INSERT INTO product(partNo, name, description, price, isForSale) Values ('" + newProduct.getPartNo() + "', '" + newProduct.getName() + "', '" + newProduct.getDescription() + "', '" + newProduct.getPrice() + "', " + newProduct.getIsForSale() + ")", Statement.RETURN_GENERATED_KEYS);

        ResultSet generatedKeys = statement.getGeneratedKeys();
        generatedKeys.next();
        return generatedKeys.getLong(1);
    }

    public String  updatePriceById(Long productId, BigDecimal newPrice) throws SQLException {
        Statement statement = connection.createStatement();

       int result = statement.executeUpdate("UPDATE product SET price = " + newPrice + " WHERE id = " + productId);

       if (result !=1){
           return "Update failed";
       }
       return "Update successful";
    }

    public String deleteOutOfSaleProducts() throws SQLException {
        Statement statement = connection.createStatement();

       int result = statement.executeUpdate("DELETE FROM product WHERE isForSale = 0");

       if (result ==0){
           return "0 products deleted";
       }
       return "Successfully deleted products: " + result;
    }
}