import dao.HotelDAO;
import dao.ReviewDAO;
import model.Hotel;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        HotelDAO hotelDAO = new HotelDAO();
        ReviewDAO reviewDAO = new ReviewDAO();

        while (true) {
            System.out.println("\n=== HOTEL REVIEW SYSTEM ===");
            System.out.println("1. Add Hotel");
            System.out.println("2. View All Hotels");
            System.out.println("3. Add Review for Hotel");
            System.out.println("4. Show Reviews of a Hotel");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                // ✅ 1. Add Hotel
                case 1 -> {
                    System.out.print("Enter hotel name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter location: ");
                    String location = sc.nextLine();
                    System.out.print("Enter rating (0–5): ");
                    double rating = sc.nextDouble();
                    sc.nextLine();

                    Hotel hotel = new Hotel(name, location, rating);
                    hotelDAO.addHotel(hotel);
                }

                // ✅ 2. View all hotels
                case 2 -> {
                    System.out.println("\n--- List of Hotels ---");
                    hotelDAO.getAllHotels().forEach(h -> {
                        System.out.println("ID: " + h.getId());
                        System.out.println("Name: " + h.getName());
                        System.out.println("Location: " + h.getLocation());
                        System.out.println("Rating: " + h.getRating());
                        System.out.println("Reviews: " + h.getReviewCount());
                        System.out.println("-----------------------");
                    });
                }

                // ✅ 3. Add Review for a Hotel
                case 3 -> {
                    System.out.println("\n--- Add Review ---");
                    System.out.print("Enter hotel ID: ");
                    int hotelId = sc.nextInt();
                    sc.nextLine(); // clear buffer

                    // Check if hotel exists before inserting review
                    Hotel hotel = hotelDAO.getHotelById(hotelId);
                    if (hotel == null) {
                        System.out.println("❌ Hotel not found! Please check the ID.");
                        break;
                    }

                    System.out.print("Enter your name: ");
                    String reviewer = sc.nextLine();
                    System.out.print("Enter your review: ");
                    String reviewText = sc.nextLine();
                    System.out.print("Enter stars (1–5): ");
                    int stars = sc.nextInt();
                    sc.nextLine();

                    reviewDAO.addReview(hotelId, reviewer, reviewText, stars);
                }

                // ✅ 4. Show all reviews of a hotel
                case 4 -> {
                    System.out.print("Enter hotel ID: ");
                    int hotelId = sc.nextInt();
                    sc.nextLine();

                    // Check if hotel exists before showing reviews
                    Hotel hotel = hotelDAO.getHotelById(hotelId);
                    if (hotel == null) {
                        System.out.println("❌ Hotel not found! Please check the ID.");
                        break;
                    }

                    System.out.println("\n--- Reviews for " + hotel.getName() + " ---");
                    System.out.println(reviewDAO.getReviewsByHotel(hotelId));

                }

                // ✅ 5. Exit
                case 5 -> {
                    System.out.println("👋 Exiting system...");
                    sc.close();
                    System.exit(0);
                }

                // ⚠️ Default option
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }
}


