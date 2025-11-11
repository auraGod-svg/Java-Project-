package dao;

import model.Hotel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelDAO {

    // Insert a new hotel
    public void addHotel(Hotel hotel) {
        String query = "INSERT INTO hotels (name, location, rating) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, hotel.getName());
            stmt.setString(2, hotel.getLocation());
            stmt.setDouble(3, hotel.getRating());
            stmt.executeUpdate();

            System.out.println("🏨 Hotel added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all hotels
    public List<Hotel> getAllHotels() {
        List<Hotel> hotels = new ArrayList<>();
        String query = "SELECT * FROM hotels";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Hotel hotel = new Hotel();
                hotel.setId(rs.getInt("id"));
                hotel.setName(rs.getString("name"));
                hotel.setLocation(rs.getString("location"));
                hotel.setRating(rs.getDouble("rating"));
                hotel.setReviewCount(rs.getInt("review_count"));
                hotels.add(hotel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }

    // ✅ Get a hotel by its ID (moved inside the class)
    public Hotel getHotelById(int id) {
        Hotel hotel = null;
        String sql = "SELECT * FROM hotels WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                hotel = new Hotel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getDouble("rating"),
                        rs.getInt("review_count")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hotel;
    }
}
