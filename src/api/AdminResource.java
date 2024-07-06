package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    ReservationService reservationService = ReservationService.getInstance();
    CustomerService customerService = CustomerService.getInstance();

    //singleton
    private static AdminResource adminResource = null;
    private AdminResource(){}
    public static AdminResource getInstance(){
        if(adminResource==null){
            adminResource = new AdminResource();
        }
        return adminResource;
    }

    public Customer getCustomer(String email){
        return null;
    }
    public void addRoom(List<IRoom> rooms){}

    public Collection<IRoom> getAllRooms(){
        return null;
    }

    public Collection<Customer> getAllCustomers(){
        return null;
    }

    public void displayAllReservations(){}
}
