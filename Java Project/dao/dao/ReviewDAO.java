package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewDAO {

    // ✅ Add a review
    public void addReview(int hotelId, String reviewerName, String reviewText, int stars) {
        String query = "INSERT INTO reviews (hotel_id, reviewer_name, review_text, stars) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, hotelId);
            stmt.setString(2, reviewerName);
            stmt.setString(3, reviewText);
            stmt.setInt(4, stars);
            stmt.executeUpdate();
            System.out.println("✅ Review added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Get all reviews for a specific hotel
    public String getReviewsByHotel(int hotelId) {
        StringBuilder reviews = new StringBuilder();
        String query = "SELECT reviewer_name, review_text, stars, review_date FROM reviews WHERE hotel_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, hotelId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reviews.append("👤 Reviewer: ").append(rs.getString("reviewer_name")).append("\n");
                reviews.append("⭐ Stars: ").append(rs.getInt("stars")).append("\n");
                reviews.append("💬 Review: ").append(rs.getString("review_text")).append("\n");
                reviews.append("🕒 Date: ").append(rs.getTimestamp("review_date")).append("\n");
                reviews.append("---------------------------\n");
            }

            if (reviews.length() == 0) {
                reviews.append("❌ No reviews found for this hotel.\n");
            }

        } catch (SQLException e) {
            reviews.append("Error fetching reviews: ").append(e.getMessage());
        }

        return reviews.toString();
    }
}
