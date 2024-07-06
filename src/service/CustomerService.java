package service;

import model.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerService {
    private List<Customer> customerList = new ArrayList<Customer>();

    // singleton
    private static CustomerService customerService = null;
    private CustomerService(){}
    public static CustomerService getInstance(){
        if(customerService == null){
            customerService = new CustomerService();
        }
        return customerService;
    }

    public void addCustomer(String email, String firstName, String lastName){
        Customer customer = new Customer(firstName,lastName,email);
        customerList.add(customer);
    }

    public Customer getCustomer(String customerEmail){
        return customerList.stream().filter(item->item.getEmail().equals(customerEmail)).findFirst().orElse(null);
    }

    public Collection<Customer> getAllCustomers(){
        return null;
    }
}
