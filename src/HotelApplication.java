import java.time.LocalDate;
import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import view.AdminMenu;
import view.MainMenu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class HotelApplication {
    public static void main(String[] args) {
        HotelResource hotelResource = HotelResource.getInstance();
        Customer userLogin = null;

        do{
            if(userLogin != null){
                System.out.println("~~~~~~~~~~~~~~~~");
                System.out.println("Wellcome " + userLogin.getEmail());
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

                        System.out.println("=== List rooms found: ===");
                        roomList.forEach(System.out::println);

                        if(roomList.size() == 0){
                            System.out.println("No rooms found from "+ dateCheckinStr + " to " + dateCheckoutStr +" !");

                            dateCheckinStr = dateAddDays(dateCheckinStr, 7);
                            dateCheckoutStr = dateAddDays(dateCheckoutStr, 7);
                            try {
                                dateCheckin = formatter.parse(dateCheckinStr);
                                dateCheckout = formatter.parse(dateCheckoutStr);
                            } catch (ParseException e) {
                                throw new Exception("invalid input date !");
                            }

                            roomList = hotelResource.findARoom(dateCheckin, dateCheckout);
                            if (roomList.size()>0){
                                System.out.println("Recommended room list from "+ dateCheckinStr + " to " + dateCheckoutStr +":");
                                roomList.forEach(System.out::println);
                            }else{
                                System.out.println("No recommended rooms found "+ dateCheckinStr + " to " + dateCheckoutStr +" !");
                                break;
                            }
                        }


                        System.out.println("=== Reserve a room ===");
                        System.out.println("Enter room number in above list: ");
                        String roomNumber = scanner.nextLine();
                        IRoom room = roomList.stream().filter(r->r.getRoomNumber().equals(roomNumber)).findFirst().orElse(null);
                        if(room == null){
                            System.out.println("room number invalid !");
                            break;
                        }

                        System.out.println("=== Your information reservation ===");
                        System.out.println(userLogin);
                        System.out.println(room);
                        System.out.println("Date checkin: " + dateCheckin);
                        System.out.println("Date checkout: " + dateCheckout);
                        System.out.println("Are you sure reservation (Yes/No):");
                        String confirm = scanner.nextLine();
                        if(confirm.equalsIgnoreCase("Yes")){
                            Reservation reservation = hotelResource.bookARoom(userLogin.getEmail(),room,dateCheckin, dateCheckout);
                            if(reservation != null){
                                System.out.println("Reserve room success !");
                            }
                        }else{
                            System.out.println("Canceled the reservation !");
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
                    boolean admin = true;
                    do{
                        AdminMenu.display();
                        System.out.println("Enter your choice:");
                        String ad_choice = scanner.nextLine();
                        if(ad_choice.equals("5")){
                            admin = false;
                        }
                        AdminMenu.handleChoice(ad_choice);
                    }while(admin);
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

    public static String dateAddDays(String provDate, int dayToAdd) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendarIns = Calendar.getInstance();

        try{
            calendarIns.setTime(dateFormat.parse(provDate));
        }catch(ParseException e){
            e.printStackTrace();
        }

        calendarIns.add(Calendar.DAY_OF_MONTH, dayToAdd);
        String dateAfter = dateFormat.format(calendarIns.getTime());
        return dateAfter;
    }
}