import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import view.AdminMenu;
import view.MainMenu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class HotelApplication {
    public static void main(String[] args) {
        HotelResource hotelResource = HotelResource.getInstance();
        AdminResource adminResource = AdminResource.getInstance();
        Customer userLogin = null;

        do{
            if(userLogin != null){
                System.out.println("Wellcome " + userLogin.getFirstName());
            }
            MainMenu.display();
            System.out.println("Enter your choice:");
            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();

            switch (choice){
                case "1":
                    try {
                        if (userLogin==null){
                            System.out.println("Let create account before reserve room !");
                            break;
                        }
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        System.out.println("=== Find a room ===");
                        System.out.println("Enter date checkin(yyyy-MM-dd):");
                        String dateCheckinStr = scanner.nextLine();
                        System.out.println("Enter date checkout(yyyy-MM-dd):");
                        String dateCheckoutStr = scanner.nextLine();
                        Date dateCheckin;
                        Date dateCheckout;
                        try {
                            dateCheckin = formatter.parse(dateCheckinStr);
                            dateCheckout = formatter.parse(dateCheckoutStr);
                        } catch (ParseException e) {
                            throw new Exception("invalid input date !");
                        }
                        Collection<IRoom> roomList = hotelResource.findARoom(dateCheckin, dateCheckout);
                        roomList.forEach(System.out::println);

                        System.out.println("=== Reserve a room ===");
                        System.out.println("Enter room number");
                        String roomNumber = scanner.nextLine();
                        Reservation reservation = hotelResource.bookARoom(userLogin.getEmail(),hotelResource.getRoom(roomNumber),dateCheckin, dateCheckout);
                        if(reservation != null){
                            System.out.println("Reserve room success !");
                        }
                    }catch(Exception e){
                        System.err.println("Error when reserve room: "+ e.getMessage());
                    }
                    break;
                case "2":
                    if (userLogin==null){
                        System.out.println("Let create account before see your reservations !");
                        break;
                    }
                    System.out.println("=== See my reservations ===");
                    Collection<Reservation> reservationList = hotelResource.getCustomersReservations(userLogin.getEmail());
                    if(reservationList.size() == 0){
                        System.out.println("No room reserved");
                    }
                    reservationList.forEach(System.out::println);
                    break;
                case "3":
                    try {
                        System.out.println("=== Create an account ===");
                        System.out.println("Enter your email:");
                        String email = scanner.nextLine();
                        System.out.println("Enter first name:");
                        String firstName = scanner.nextLine();
                        System.out.println("Enter last name:");
                        String lastName = scanner.nextLine();
                        hotelResource.createACustomer(email, firstName, lastName);
                        System.out.println("Create success !");

                        userLogin = hotelResource.getCustomer(email);

                    }catch(Exception e){
                        System.err.println("Error when create account: "+ e.getMessage());
                    }
                    break;
                case "4":
                    AdminMenu.display();
                    break;
                case "5":
                    System.out.println("=== Exit App ===");
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }while(true);
    }
}