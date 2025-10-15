import java.util.*;

// Car class to store car details
class Car {
    private int id;
    private String brand;
    private String model;
    private double pricePerDay;
    private boolean isAvailable;

    public Car(int id, String brand, String model, double pricePerDay) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.pricePerDay = pricePerDay;
        this.isAvailable = true;
    }

    public int getId() { return id; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public double getPricePerDay() { return pricePerDay; }
    public boolean isAvailable() { return isAvailable; }
    public void rent() { isAvailable = false; }
    public void makeAvailable() { isAvailable = true; }

    @Override
    public String toString() {
        return id + " | " + brand + " " + model + " | ₹" + pricePerDay + "/day | " +
               (isAvailable ? "Available" : "Rented");
    }
}

// Customer class
class Customer {
    private int id;
    private String name;
    private String contact;

    public Customer(int id, String name, String contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getContact() { return contact; }
}

// Rental Transaction class
class Rental {
    private Customer customer;
    private Car car;
    private int days;
    private double totalCost;

    public Rental(Customer customer, Car car, int days) {
        this.customer = customer;
        this.car = car;
        this.days = days;
        this.totalCost = car.getPricePerDay() * days;
        car.rent();
    }

    public void endRental() {
        car.makeAvailable();
    }

    @Override
    public String toString() {
        return "Customer: " + customer.getName() + " | Car: " + car.getBrand() + " " + car.getModel() +
               " | Days: " + days + " | Total: ₹" + totalCost;
    }
}

// Main system class
public class RentalCarSystem {
    private static List<Car> cars = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    private static List<Rental> rentals = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        seedData();
        int choice;

        do {
            System.out.println("\n=== RENTAL CAR MANAGEMENT SYSTEM ===");
            System.out.println("1. View Available Cars");
            System.out.println("2. Add New Car");
            System.out.println("3. Register Customer");
            System.out.println("4. Rent a Car");
            System.out.println("5. Return a Car");
            System.out.println("6. View All Rentals");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1: viewCars(); break;
                case 2: addCar(); break;
                case 3: registerCustomer(); break;
                case 4: rentCar(); break;
                case 5: returnCar(); break;
                case 6: viewRentals(); break;
                case 0: System.out.println("Thank you for using the system!"); break;
                default: System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 0);
    }

    // Preload sample data
    private static void seedData() {
        cars.add(new Car(1, "Toyota", "Innova", 2500));
        cars.add(new Car(2, "Honda", "City", 2000));
        cars.add(new Car(3, "Hyundai", "Creta", 2200));
        customers.add(new Customer(1, "Neha", "9876543210"));
        customers.add(new Customer(2, "Ravi", "8765432109"));
    }

    private static void viewCars() {
        System.out.println("\n--- Available Cars ---");
        for (Car c : cars) {
            System.out.println(c);
        }
    }

    private static void addCar() {
        System.out.print("Enter Car ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Brand: ");
        String brand = sc.nextLine();
        System.out.print("Enter Model: ");
        String model = sc.nextLine();
        System.out.print("Enter Price per day: ");
        double price = sc.nextDouble();

        cars.add(new Car(id, brand, model, price));
        System.out.println("✅ Car added successfully!");
    }

    private static void registerCustomer() {
        System.out.print("Enter Customer ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Contact: ");
        String contact = sc.nextLine();

        customers.add(new Customer(id, name, contact));
        System.out.println("✅ Customer registered successfully!");
    }

    private static void rentCar() {
        System.out.print("Enter Customer ID: ");
        int custId = sc.nextInt();
        System.out.print("Enter Car ID: ");
        int carId = sc.nextInt();
        System.out.print("Enter number of rental days: ");
        int days = sc.nextInt();

        Customer customer = customers.stream().filter(c -> c.getId() == custId).findFirst().orElse(null);
        Car car = cars.stream().filter(c -> c.getId() == carId && c.isAvailable()).findFirst().orElse(null);

        if (customer != null && car != null) {
            Rental rental = new Rental(customer, car, days);
            rentals.add(rental);
            System.out.println("✅ Rental created successfully!");
            System.out.println(rental);
        } else {
            System.out.println("❌ Invalid customer or car unavailable.");
        }
    }

    private static void returnCar() {
        System.out.print("Enter Car ID to return: ");
        int carId = sc.nextInt();

        Rental rental = rentals.stream().filter(r -> r.toString().contains("Car:") && 
            r.toString().contains(String.valueOf(carId))).findFirst().orElse(null);

        if (rental != null) {
            rental.endRental();
            System.out.println("✅ Car returned successfully!");
        } else {
            System.out.println("❌ Rental not found!");
        }
    }

    private static void viewRentals() {
        System.out.println("\n--- Active Rentals ---");
        for (Rental r : rentals) {
            System.out.println(r);
        }
    }
}
