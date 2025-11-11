package ui;

import dao.HotelDAO;
import dao.ReviewDAO;
import model.Hotel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HotelReviewApp extends JFrame {
    private final HotelDAO hotelDAO = new HotelDAO();
    private final ReviewDAO reviewDAO = new ReviewDAO();

    private final JTextArea displayArea;

    public HotelReviewApp() {
        setTitle("🏨 Hotel Review System");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Buttons Panel ---
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5, 10, 10));

        JButton addHotelBtn = new JButton("Add Hotel");
        JButton viewHotelsBtn = new JButton("View Hotels");
        JButton addReviewBtn = new JButton("Add Review");
        JButton showReviewsBtn = new JButton("Show Reviews");
        JButton exitBtn = new JButton("Exit");

        buttonPanel.add(addHotelBtn);
        buttonPanel.add(viewHotelsBtn);
        buttonPanel.add(addReviewBtn);
        buttonPanel.add(showReviewsBtn);
        buttonPanel.add(exitBtn);

        // --- Display Area ---
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // --- Button Actions ---
        addHotelBtn.addActionListener(this::addHotel);
        viewHotelsBtn.addActionListener(this::viewHotels);
        addReviewBtn.addActionListener(this::addReview);
        showReviewsBtn.addActionListener(this::showReviews);
        exitBtn.addActionListener(e -> System.exit(0));
    }

    // ✅ Add a new hotel
    private void addHotel(ActionEvent e) {
        String name = JOptionPane.showInputDialog(this, "Enter hotel name:");
        String location = JOptionPane.showInputDialog(this, "Enter hotel location:");
        String ratingStr = JOptionPane.showInputDialog(this, "Enter rating (0-5):");

        if (name == null || location == null || ratingStr == null) return;

        try {
            double rating = Double.parseDouble(ratingStr);
            Hotel hotel = new Hotel(name, location, rating);
            hotelDAO.addHotel(hotel);
            displayArea.append("✅ Hotel added: " + name + "\n");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    // ✅ View all hotels
    private void viewHotels(ActionEvent e) {
        displayArea.setText("=== 🏨 List of Hotels ===\n\n");
        for (Hotel h : hotelDAO.getAllHotels()) {
            displayArea.append(
                    "ID: " + h.getId() +
                            "\nName: " + h.getName() +
                            "\nLocation: " + h.getLocation() +
                            "\nRating: " + h.getRating() +
                            "\nReviews: " + h.getReviewCount() +
                            "\n----------------------\n"
            );
        }
    }

    // ✅ Add a review
    private void addReview(ActionEvent e) {
        try {
            int hotelId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter hotel ID:"));
            String reviewer = JOptionPane.showInputDialog(this, "Enter your name:");
            String reviewText = JOptionPane.showInputDialog(this, "Enter your review:");
            int stars = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter stars (1-5):"));

            reviewDAO.addReview(hotelId, reviewer, reviewText, stars);
            displayArea.append("⭐ Review added for hotel ID " + hotelId + "\n");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    // ✅ Show reviews for a hotel
    private void showReviews(ActionEvent e) {
        try {
            int hotelId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter hotel ID:"));

            // Fetch hotel info
            Hotel hotel = hotelDAO.getHotelById(hotelId);

            if (hotel == null) {
                displayArea.setText("❌ No hotel found with ID " + hotelId);
                return;
            }

            displayArea.setText("=== 🏨 Reviews for " + hotel.getName() + " ===\n");
            displayArea.append("📍 Location: " + hotel.getLocation() + "\n");
            displayArea.append("⭐ Rating: " + hotel.getRating() + "\n\n");

            // Fetch and display reviews
            String reviews = reviewDAO.getReviewsByHotel(hotelId);
            displayArea.append(reviews);

            displayArea.setCaretPosition(0); // Scroll to top
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HotelReviewApp().setVisible(true));
    }
}
