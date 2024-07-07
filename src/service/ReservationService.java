package service;

import model.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ReservationService {

    // singleton
    private static ReservationService reservationService = null;
    private final Set<Reservation> reservationList = new HashSet<Reservation>();
    private final Set<IRoom> roomList = new HashSet<IRoom>();

    private ReservationService() {
//        Customer c1 = new Customer("Nguyen", "Hiep", "hiep@gmail.com");
//        Customer c2 = new Customer("Nguyen", "Tuan", "tuan@gmail.com");
//
//        IRoom r1 = new Room("A1", 1.2, RoomType.SINGLE);
//        IRoom r2 = new Room("A2", 10.45, RoomType.DOUBLE);
//        roomList.add(r1);
//        roomList.add(r2);
//
//        Date dateCheckin = null;
//        Date dateCheckout = null;
//        Date dateCheckin1 = null;
//        Date dateCheckout1 = null;
//        try {
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            dateCheckin = formatter.parse("2024-07-01");
//            dateCheckout = formatter.parse("2024-07-05");
//            dateCheckin1 = formatter.parse("2024-07-07");
//            dateCheckout1 = formatter.parse("2024-07-10");
//        } catch (Exception e) {
//            System.out.println("Have error when mock data !");
//        }
//        Reservation re1 = new Reservation(c1, r1, dateCheckin, dateCheckout);
//        Reservation re2 = new Reservation(c2, r2, dateCheckin1, dateCheckout1);
//        reservationList.add(re1);
//        reservationList.add(re2);
    }

    public static ReservationService getInstance() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    public void addRoom(IRoom room) throws Exception {
        IRoom roomFind = roomList.stream().filter(r->r.getRoomNumber().equals(room.getRoomNumber())).findFirst().orElse(null);
        if (roomFind != null){
            throw new Exception("This room exists !");
        }
        roomList.add(room);
    }

    public IRoom getARoom(String roomId) {
        return roomList.stream()
                .filter(item -> item.getRoomNumber().equals(roomId))
                .findFirst()
                .orElse(null);
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservationList.add(reservation);
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        return roomList.stream()
                .filter(room -> isRoomAvailable(room, checkInDate, checkOutDate))
                .collect(Collectors.toSet());
    }
    private boolean isRoomAvailable(IRoom room, Date checkInDate, Date checkOutDate) {
        boolean isAvailable = true;
        for(Reservation reservation: reservationList){
            if (reservation.getRoom().equals(room)){
                if((checkInDate.before(reservation.getCheckOutDate()) || checkInDate.equals(reservation.getCheckOutDate()))
                        && (checkOutDate.after(reservation.getCheckInDate()) || checkOutDate.equals(reservation.getCheckInDate()))){
                    isAvailable = false;
                }
            }
        }

        return isAvailable;
//        return reservationList.stream()
//                .filter(reservation -> reservation.getRoom().equals(room))
//                .noneMatch(reservation ->
//                        checkInDate.before(reservation.getCheckOutDate()) && checkOutDate.after(reservation.getCheckInDate()));
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        return reservationList.stream()
                .filter(item -> item.getCustomer().getEmail().equals(customer.getEmail()))
                .collect(Collectors.toSet());
    }

    public void printAllReservation() {
        reservationList.forEach(System.out::println);
    }

    public Collection<IRoom> getAllRooms() {
        return roomList;
    }
}
