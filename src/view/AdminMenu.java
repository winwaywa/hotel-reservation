package view;

import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    static AdminResource adminResource = AdminResource.getInstance();

    public static void display() {
        System.out.println("=== Admin menu ===" + '\n' +
                "1.  See all Customers" + '\n' +
                "2.  See all Rooms" + '\n' +
                "3.  See all Reservations" + '\n' +
                "4.  Add a Room" + '\n' +
                "5.  Back to Main Menu");
    }

    public static void handleChoice(String choice) {
        Scanner scanner = new Scanner(System.in);
        switch (choice) {
            case "1":
                System.out.println("=== List all Customers ===");
                List<Customer> customerList = (List<Customer>) adminResource.getAllCustomers();
                customerList.forEach(System.out::println);
                break;
            case "2":
                System.out.println("=== List all Rooms ===");
                List<IRoom> roomList = (List<IRoom>) adminResource.getAllRooms();
                roomList.forEach(System.out::println);
                break;
            case "3":
                System.out.println("=== List all Reservations ===");
                adminResource.displayAllReservations();
                break;
            case "4":
                try {
                    System.out.println("=== Add a Room ===");
                    System.out.println("Enter room number: ");
                    String roomNumber = scanner.nextLine();
                    System.out.println("Enter price: ");
                    Double price = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println("Enter type: ");
                    System.out.println("1.SINGLE    2.DOUBLE");
                    String type = scanner.nextLine();
                    RoomType typeEnum;
                    if (type.equals("1")) {
                        typeEnum = RoomType.SINGLE;
                    } else if (type.equals("2")) {
                        typeEnum = RoomType.DOUBLE;
                    } else {
                        throw new InputMismatchException("type room invalid !");
                    }
                    IRoom room = new Room(roomNumber, price, typeEnum);
                    List<IRoom> rooms = new ArrayList<>();
                    rooms.add(room);
                    adminResource.addRoom(rooms);
                } catch (Exception e) {
                    System.err.println("Error when add room: " + e.getMessage());
                }
                break;
            case "5":
                System.out.println("Back to Main Menu");
                break;
        }
    }
}
