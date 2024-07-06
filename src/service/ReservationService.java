package service;

import model.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationService {

    // singleton
    private static ReservationService reservationService = null;
    private final List<Reservation> reservationList = new ArrayList<Reservation>();
    private final List<IRoom> roomList = new ArrayList<IRoom>();

    private ReservationService() {
        Customer c1 = new Customer("Nguyen", "Hiep", "hiep@gmail.com");
        Customer c2 = new Customer("Nguyen", "Tuan", "tuan@gmail.com");

        IRoom r1 = new Room("A1", 1.2, RoomType.SINGLE);
        IRoom r2 = new Room("A2", 10.45, RoomType.DOUBLE);
        roomList.add(r1);
        roomList.add(r2);

        Date dateCheckin = null;
        Date dateCheckout = null;
        Date dateCheckin1 = null;
        Date dateCheckout1 = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            dateCheckin = formatter.parse("2024-07-01");
            dateCheckout = formatter.parse("2024-07-05");
            dateCheckin1 = formatter.parse("2024-07-07");
            dateCheckout1 = formatter.parse("2024-07-10");
        } catch (Exception e) {
            System.out.println("Have error when mock data !");
        }
        Reservation re1 = new Reservation(c1, r1, dateCheckin, dateCheckout);
        Reservation re2 = new Reservation(c2, r2, dateCheckin1, dateCheckout1);
        reservationList.add(re1);
        reservationList.add(re2);
    }

    public static ReservationService getInstance() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    public void addRoom(IRoom room) {
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
                .collect(Collectors.toList());
    }
    private boolean isRoomAvailable(IRoom room, Date checkInDate, Date checkOutDate) {
        return reservationList.stream()
                .filter(reservation -> reservation.getRoom().equals(room))
                .noneMatch(reservation ->
                        checkInDate.before(reservation.getCheckOutDate()) && checkOutDate.after(reservation.getCheckInDate()));
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        return reservationList.stream()
                .filter(item -> item.getCustomer().getEmail().equals(customer.getEmail()))
                .collect(Collectors.toList());
    }

    public void printAllReservation() {
        reservationList.forEach(System.out::println);
    }

    public Collection<IRoom> getAllRooms() {
        return roomList;
    }
}
