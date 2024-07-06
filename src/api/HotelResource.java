package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    ReservationService reservationService = ReservationService.getInstance();
    CustomerService customerService = CustomerService.getInstance();

    //singleton
    private static HotelResource hotelResource = null;
    private HotelResource(){}
    public static HotelResource getInstance(){
        if(hotelResource==null){
            hotelResource = new HotelResource();
        }
        return hotelResource;
    }

    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }
    public void createACustomer(String  email,  String firstName, String lastName){
        customerService.addCustomer(email,firstName,lastName);
    }

    public IRoom getRoom(String roomNumber){
        return reservationService.getARoom(roomNumber);
    }
    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date CheckOutDate){
        Customer customer = getCustomer(customerEmail);
        return reservationService.reserveARoom(customer, room, checkInDate, CheckOutDate);
    }
    public Collection<Reservation> getCustomersReservations(String customerEmail){
        Customer customer = getCustomer(customerEmail);
        return reservationService.getCustomersReservation(customer);
    }
    public Collection<IRoom> findARoom(Date checkIn, Date checkOut){
        return reservationService.findRooms(checkIn,checkOut);
    }
}
