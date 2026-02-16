package com.example.deliveryapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class InsertPartners {
    public static void main(String[] args) {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/delivery_db"; 
        String user = "root";      // your MySQL username
        String password = "Geethu@6144"; // your MySQL password

        // SQL insert statement
        String sql = "INSERT INTO delivery_partners (available, current_lat, current_lng, name, phone) VALUES (?, ?, ?, ?, ?)";

        // Sample data for 10 delivery partners
        Object[][] partners = {
            {1, 17.5966, 78.4867, "Ramesh", "9998887771"},
            {1, 12.9716, 77.5987, "Ravi Kumar", "9999900001"},
            {1, 12.9860, 77.5940, "Suresh", "9999990002"},
            {1, 12.9600, 77.8080, "Anitha", "9999990003"},
            {1, 13.0827, 80.2707, "Priya", "9999990004"},
            {1, 17.3850, 78.4867, "Rajesh", "9999990005"},
            {1, 19.0760, 72.8777, "Sunil", "9999990006"},
            {1, 28.7041, 77.1025, "Kiran", "9999990007"},
            {1, 22.5726, 88.3639, "Deepa", "9999990008"},
            {1, 11.0168, 76.9558, "Vikram", "9999990009"}
        };

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Loop over partners and insert each one
            for (Object[] p : partners) {
                stmt.setInt(1, (int) p[0]);        // available
                stmt.setDouble(2, (double) p[1]);  // current_lat
                stmt.setDouble(3, (double) p[2]);  // current_lng
                stmt.setString(4, (String) p[3]);  // name
                stmt.setString(5, (String) p[4]);  // phone
                stmt.executeUpdate();
            }

            System.out.println("✅ 10 delivery partners inserted successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
