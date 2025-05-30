package com.feeapp.dao;

import com.feeapp.model.FeePayment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FeePaymentDAO {
    private static final Logger LOGGER = Logger.getLogger(FeePaymentDAO.class.getName());
    
    // Updated JDBC URL with timezone and SSL parameters
    private String jdbcURL = "jdbc:mysql://localhost:3308/CollegeFeeDB?useSSL=false&serverTimezone=UTC";
    private String jdbcUsername = "root";
    private String jdbcPassword = ""; // Change to your MySQL password
    
    // SQL queries
    private static final String INSERT_PAYMENT_SQL = "INSERT INTO FeePayments (StudentID, StudentName, PaymentDate, Amount, Status) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_PAYMENT_BY_ID = "SELECT * FROM FeePayments WHERE PaymentID = ?";
    private static final String SELECT_ALL_PAYMENTS = "SELECT * FROM FeePayments";
    private static final String DELETE_PAYMENT_SQL = "DELETE FROM FeePayments WHERE PaymentID = ?";
    private static final String UPDATE_PAYMENT_SQL = "UPDATE FeePayments SET StudentID=?, StudentName=?, PaymentDate=?, Amount=?, Status=? WHERE PaymentID=?";
    
    private static final String SELECT_UNPAID_STUDENTS = "SELECT * FROM FeePayments WHERE Status = 'Unpaid'";
    private static final String SELECT_OVERDUE_PAYMENTS = "SELECT * FROM FeePayments WHERE Status = 'Partial'";
    private static final String SELECT_TOTAL_COLLECTION = "SELECT SUM(Amount) as total FROM FeePayments WHERE PaymentDate BETWEEN ? AND ?";
    
    /**
     * Establishes a database connection with proper error handling
     * @return Connection object or null if connection fails
     */
    protected Connection getConnection() {
        Connection connection = null;
        try {
            // Explicitly load the driver class
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            LOGGER.log(Level.INFO, "Attempting to connect to database: {0}", jdbcURL);
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            
            if (connection != null) {
                LOGGER.info("Database connection established successfully!");
            } else {
                LOGGER.severe("Failed to establish database connection - returned null");
            }
        } catch (ClassNotFoundException e) {
            LOGGER.severe("MySQL JDBC Driver not found!");
            LOGGER.severe(e.getMessage());
        } catch (SQLException e) {
            LOGGER.severe("Connection failed! Check output console");
            printSQLException(e);
        }
        return connection;
    }
    
    /**
     * Validates if the database connection is available
     * @return true if connection is successful, false otherwise
     */
    public boolean validateConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error validating database connection", e);
            return false;
        }
    }
    
    // Create payment
    public void insertPayment(FeePayment payment) throws SQLException {
        try (Connection connection = getConnection()) {
            if (connection == null) {
                throw new SQLException("Failed to establish database connection");
            }
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PAYMENT_SQL)) {
                preparedStatement.setInt(1, payment.getStudentId());
                preparedStatement.setString(2, payment.getStudentName());
                preparedStatement.setDate(3, new java.sql.Date(payment.getPaymentDate().getTime()));
                preparedStatement.setDouble(4, payment.getAmount());
                preparedStatement.setString(5, payment.getStatus());
                
                int affectedRows = preparedStatement.executeUpdate();
                LOGGER.log(Level.INFO, "Inserted payment, affected rows: {0}", affectedRows);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error inserting payment", e);
            printSQLException(e);
            throw e;
        }
    }
    
    // Update payment
    public boolean updatePayment(FeePayment payment) throws SQLException {
        try (Connection connection = getConnection()) {
            if (connection == null) {
                throw new SQLException("Failed to establish database connection");
            }
            
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_PAYMENT_SQL)) {
                statement.setInt(1, payment.getStudentId());
                statement.setString(2, payment.getStudentName());
                statement.setDate(3, new java.sql.Date(payment.getPaymentDate().getTime()));
                statement.setDouble(4, payment.getAmount());
                statement.setString(5, payment.getStatus());
                statement.setInt(6, payment.getPaymentId());
                
                int affectedRows = statement.executeUpdate();
                LOGGER.log(Level.INFO, "Updated payment, affected rows: {0}", affectedRows);
                return affectedRows > 0;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating payment", e);
            printSQLException(e);
            throw e;
        }
    }
    
    // Select payment by id
    public FeePayment selectPayment(int paymentId) {
        FeePayment payment = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PAYMENT_BY_ID)) {
            preparedStatement.setInt(1, paymentId);
            
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    int studentId = rs.getInt("StudentID");
                    String studentName = rs.getString("StudentName");
                    Date paymentDate = rs.getDate("PaymentDate");
                    double amount = rs.getDouble("Amount");
                    String status = rs.getString("Status");
                    payment = new FeePayment(paymentId, studentId, studentName, paymentDate, amount, status);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error selecting payment by ID", e);
            printSQLException(e);
        }
        return payment;
    }
    
    // Select all payments
    public List<FeePayment> selectAllPayments() {
        List<FeePayment> payments = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PAYMENTS);
             ResultSet rs = preparedStatement.executeQuery()) {
            
            while (rs.next()) {
                int paymentId = rs.getInt("PaymentID");
                int studentId = rs.getInt("StudentID");
                String studentName = rs.getString("StudentName");
                Date paymentDate = rs.getDate("PaymentDate");
                double amount = rs.getDouble("Amount");
                String status = rs.getString("Status");
                payments.add(new FeePayment(paymentId, studentId, studentName, paymentDate, amount, status));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error selecting all payments", e);
            printSQLException(e);
        }
        return payments;
    }
    
    // Delete payment
    public boolean deletePayment(int paymentId) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PAYMENT_SQL)) {
            statement.setInt(1, paymentId);
            return statement.executeUpdate() > 0;
        }
    }
    
    // Report methods
    public List<FeePayment> getUnpaidStudents() {
        return getPaymentsByQuery(SELECT_UNPAID_STUDENTS);
    }
    
    public List<FeePayment> getOverduePayments() {
        return getPaymentsByQuery(SELECT_OVERDUE_PAYMENTS);
    }
    
    public double getTotalCollection(Date startDate, Date endDate) {
        double total = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TOTAL_COLLECTION)) {
            preparedStatement.setDate(1, new java.sql.Date(startDate.getTime()));
            preparedStatement.setDate(2, new java.sql.Date(endDate.getTime()));
            
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    total = rs.getDouble("total");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error calculating total collection", e);
            printSQLException(e);
        }
        return total;
    }
    
    private List<FeePayment> getPaymentsByQuery(String query) {
        List<FeePayment> payments = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {
            
            while (rs.next()) {
                int paymentId = rs.getInt("PaymentID");
                int studentId = rs.getInt("StudentID");
                String studentName = rs.getString("StudentName");
                Date paymentDate = rs.getDate("PaymentDate");
                double amount = rs.getDouble("Amount");
                String status = rs.getString("Status");
                payments.add(new FeePayment(paymentId, studentId, studentName, paymentDate, amount, status));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error executing report query: " + query, e);
            printSQLException(e);
        }
        return payments;
    }
    
    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                LOGGER.severe("SQLState: " + ((SQLException) e).getSQLState());
                LOGGER.severe("Error Code: " + ((SQLException) e).getErrorCode());
                LOGGER.severe("Message: " + e.getMessage());
                
                Throwable t = ex.getCause();
                while (t != null) {
                    LOGGER.severe("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}