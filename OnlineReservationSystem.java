import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class OnlineReservationSystem extends JFrame {

    private ArrayList<Reservation> reservations = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private User loggedInUser;

    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    private JTextField loginUsernameField = new JTextField(15);
    private JPasswordField loginPasswordField = new JPasswordField(15);

    private JTextField createUsernameField = new JTextField(15);
    private JPasswordField createPasswordField = new JPasswordField(15);

    private JTextField trainNumberField = new JTextField(15);
    private JTextField trainNameField = new JTextField(15);
    private JTextField classTypeField = new JTextField(15);
    private JTextField dateField = new JTextField(15);
    private JTextField fromField = new JTextField(15);
    private JTextField toField = new JTextField(15);
    private JTextField passengerCountField = new JTextField(15);

    private JTextField cancelPnrField = new JTextField(15);
    private JTextField viewPnrField = new JTextField(15);

    private JTextArea outputArea = new JTextArea(10, 30);

    public OnlineReservationSystem() {
        setTitle("Online Reservation System");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel homePanel = new JPanel(new GridLayout(2, 1, 10, 10));
        JButton createAccountHomeButton = new JButton("Create Account");
        JButton loginHomeButton = new JButton("Login");
        homePanel.add(createAccountHomeButton);
        homePanel.add(loginHomeButton);
        createAccountHomeButton.addActionListener(e -> cardLayout.show(mainPanel, "CreateAccount"));
        loginHomeButton.addActionListener(e -> cardLayout.show(mainPanel, "LoginScreen"));

        JPanel createAccountPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        createAccountPanel.add(new JLabel("Create Username:"));
        createAccountPanel.add(createUsernameField);
        createAccountPanel.add(new JLabel("Create Password:"));
        createAccountPanel.add(createPasswordField);
        JButton createAccountButton = new JButton("Create Account");
        createAccountPanel.add(createAccountButton);
        createAccountButton.addActionListener(e -> handleCreateAccount());

        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(loginUsernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(loginPasswordField);
        JButton loginButton = new JButton("Login");
        loginPanel.add(loginButton);
        loginButton.addActionListener(e -> handleLogin());

        JPanel menuPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton makeReservationButton = new JButton("Make a Reservation");
        JButton cancelReservationButton = new JButton("Cancel Reservation");
        JButton viewReservationButton = new JButton("View Reservation");
        menuPanel.add(makeReservationButton);
        menuPanel.add(cancelReservationButton);
        menuPanel.add(viewReservationButton);
        makeReservationButton.addActionListener(e -> cardLayout.show(mainPanel, "Reservation"));
        cancelReservationButton.addActionListener(e -> cardLayout.show(mainPanel, "Cancel"));
        viewReservationButton.addActionListener(e -> cardLayout.show(mainPanel, "View"));

        JPanel reservationPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        reservationPanel.add(new JLabel("Train Number:"));
        reservationPanel.add(trainNumberField);
        reservationPanel.add(new JLabel("Train Name:"));
        reservationPanel.add(trainNameField);
        reservationPanel.add(new JLabel("Class Type:"));
        reservationPanel.add(classTypeField);
        reservationPanel.add(new JLabel("Date of Journey:"));
        reservationPanel.add(dateField);
        reservationPanel.add(new JLabel("From:"));
        reservationPanel.add(fromField);
        reservationPanel.add(new JLabel("To:"));
        reservationPanel.add(toField);
        reservationPanel.add(new JLabel("Passenger Count:"));
        reservationPanel.add(passengerCountField);
        JButton insertButton = new JButton("Insert");
        reservationPanel.add(insertButton);
        insertButton.addActionListener(e -> handleInsertReservation());

        JPanel cancelPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        cancelPanel.add(new JLabel("Enter PNR Number:"));
        cancelPanel.add(cancelPnrField);
        JButton cancelReservationButtonActual = new JButton("Cancel Reservation");
        cancelPanel.add(cancelReservationButtonActual);
        cancelReservationButtonActual.addActionListener(e -> handleCancelReservation());

        JPanel viewPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        viewPanel.add(new JLabel("Enter PNR Number:"));
        viewPanel.add(viewPnrField);
        JButton viewReservationButtonActual = new JButton("View Reservation");
        viewPanel.add(viewReservationButtonActual);
        viewPanel.add(outputArea);
        outputArea.setEditable(false);
        viewReservationButtonActual.addActionListener(e -> handleViewReservation());

        mainPanel.add(homePanel, "Home");
        mainPanel.add(createAccountPanel, "CreateAccount");
        mainPanel.add(loginPanel, "LoginScreen");
        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(reservationPanel, "Reservation");
        mainPanel.add(cancelPanel, "Cancel");
        mainPanel.add(viewPanel, "View");

        add(mainPanel);
        cardLayout.show(mainPanel, "Home");
    }

    private void handleCreateAccount() {
        String username = createUsernameField.getText().trim();
        String password = new String(createPasswordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters long.");
            return;
        }
        users.add(new User(username, password));
        JOptionPane.showMessageDialog(this, "Account created successfully! Please log in.");
        cardLayout.show(mainPanel, "Home");
    }

    private void handleLogin() {
        String username = loginUsernameField.getText().trim();
        String password = new String(loginPasswordField.getPassword());

        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                loggedInUser = user;
                JOptionPane.showMessageDialog(this, "Login successful!");
                cardLayout.show(mainPanel, "Menu");
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Invalid username or password.");
    }

    private void handleInsertReservation() {
        String trainNumber = trainNumberField.getText().trim();
        String trainName = trainNameField.getText().trim();
        String classType = classTypeField.getText().trim();
        String dateOfJourney = dateField.getText().trim();
        String from = fromField.getText().trim();
        String to = toField.getText().trim();
        int passengerCount = Integer.parseInt(passengerCountField.getText().trim());

        ArrayList<Passenger> passengers = new ArrayList<>();
        for (int i = 1; i <= passengerCount; i++) {
            String passengerName = JOptionPane.showInputDialog("Enter Name of Passenger " + i);
            int passengerAge = Integer.parseInt(JOptionPane.showInputDialog("Enter Age of Passenger " + i));
            passengers.add(new Passenger(passengerName, passengerAge));
        }

        Random rand = new Random();
        int pnrNumber = rand.nextInt(900) + 100;

        Reservation reservation = new Reservation(pnrNumber, trainNumber, trainName, classType, dateOfJourney, from, to, passengers);
        reservations.add(reservation);

        JOptionPane.showMessageDialog(this, "Reservation successful! Your PNR Number is " + pnrNumber);
        clearReservationFields();
        cardLayout.show(mainPanel, "Menu");
    }

    private void handleCancelReservation() {
        try {
            int pnrNumber = Integer.parseInt(cancelPnrField.getText().trim());
            boolean found = false;

            for (Reservation reservation : reservations) {
                if (reservation.pnrNumber == pnrNumber) {
                    reservations.remove(reservation);
                    JOptionPane.showMessageDialog(this, "Reservation cancelled successfully!");
                    found = true;
                    break;
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "No reservation found with PNR Number " + pnrNumber);
            }

            cardLayout.show(mainPanel, "Menu");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid PNR Number.");
        }
    }

    private void handleViewReservation() {
        try {
            int pnrNumber = Integer.parseInt(viewPnrField.getText().trim());

            for (Reservation reservation : reservations) {
                if (reservation.pnrNumber == pnrNumber) {
                    outputArea.setText(reservation.toString());
                    JOptionPane.showMessageDialog(this, "Reservation details displayed.");
                    cardLayout.show(mainPanel, "Menu");
                    return;
                }
            }

            outputArea.setText("No reservation found with PNR Number " + pnrNumber);
        } catch (NumberFormatException ex) {
            outputArea.setText("Invalid PNR Number.");
        }
    }

    private void clearReservationFields() {
        trainNumberField.setText("");
        trainNameField.setText("");
        classTypeField.setText("");
        dateField.setText("");
        fromField.setText("");
        toField.setText("");
        passengerCountField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OnlineReservationSystem().setVisible(true));
    }

    private class User {
        String username;
        String password;

        User(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    private class Reservation {
        int pnrNumber;
        String trainNumber;
        String trainName;
        String classType;
        String dateOfJourney;
        String from;
        String to;
        ArrayList<Passenger> passengers;

        Reservation(int pnrNumber, String trainNumber, String trainName, String classType,
                    String dateOfJourney, String from, String to, ArrayList<Passenger> passengers) {
            this.pnrNumber = pnrNumber;
            this.trainNumber = trainNumber;
            this.trainName = trainName;
            this.classType = classType;
            this.dateOfJourney = dateOfJourney;
            this.from = from;
            this.to = to;
            this.passengers = passengers;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("PNR Number: ").append(pnrNumber).append("\n")
              .append("Train Number: ").append(trainNumber).append("\n")
              .append("Train Name: ").append(trainName).append("\n")
              .append("Class Type: ").append(classType).append("\n")
              .append("Date of Journey: ").append(dateOfJourney).append("\n")
              .append("From: ").append(from).append("\n")
              .append("To: ").append(to).append("\n")
              .append("Passengers: \n");
            for (Passenger p : passengers) {
                sb.append("Name: ").append(p.name).append(", Age: ").append(p.age).append("\n");
            }
            return sb.toString();
        }
    }

    private class Passenger {
        String name;
        int age;

        Passenger(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}
