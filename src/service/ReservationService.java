package service;

import model.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationService {

    private List<Reservation> reservationList = new ArrayList<Reservation>();
    private List<IRoom> roomList = new ArrayList<IRoom>();

    // singleton
    private static ReservationService reservationService = null;
    private ReservationService() {
        Customer c1 = new Customer("Nguyen", "Hiep", "hiep@gmail.com");
        Customer c2 = new Customer("Nguyen", "Tuan", "tuan@gmail.com");

        IRoom r1 = new Room("A1", 1.2, RoomType.SINGLE);
        IRoom r2 = new Room("A2", 10.45, RoomType.DOUBLE);
        roomList.add(r1);
        roomList.add(r2);

        Date dateCheckin = null;
        Date dateCheckout = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            dateCheckin = formatter.parse("2024-06-30");
            dateCheckout = formatter.parse("2024-06-31");
        } catch (Exception e) {

        }
        Reservation re1 = new Reservation(c1, r1, dateCheckin, dateCheckout);
        reservationList.add(re1);
    }
    public static ReservationService getInstance(){
        if(reservationService == null){
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    public void addRoom(IRoom room){
    }
    public IRoom getARoom(String roomId){
        return roomList.stream()
                .filter(item->item.getRoomNumber().equals(roomId))
                .findFirst()
                .orElse(null);
    }
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservationList.add(reservation);
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        return reservationList.stream()
                .filter(item->item.getCheckOutDate().before(checkInDate))
                .map(Reservation::getRoom)
                .collect(Collectors.toSet());
    }

    public Collection<Reservation> getCustomersReservation(Customer customer){
        return reservationList.stream()
                .filter(item->item.getCustomer().getEmail().equals(customer.getEmail()))
                .collect(Collectors.toList());
    }

    public void printAllReservation(){

    }
}
