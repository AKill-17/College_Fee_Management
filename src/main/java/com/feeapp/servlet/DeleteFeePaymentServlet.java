package com.feeapp.servlet;

import com.feeapp.dao.FeePaymentDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deletePayment")
public class DeleteFeePaymentServlet extends HttpServlet {
    private FeePaymentDAO feePaymentDAO;

    @Override
    public void init() throws ServletException {
        feePaymentDAO = new FeePaymentDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Get and validate payment ID
        int paymentId;
        try {
            paymentId = Integer.parseInt(request.getParameter("paymentId"));
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Invalid payment ID format");
            response.sendRedirect("displayPayments");
            return;
        }

        // 2. Attempt deletion
        try {
            boolean deleted = feePaymentDAO.deletePayment(paymentId);
            
            if (deleted) {
                request.getSession().setAttribute("successMessage", 
                    "Payment deleted successfully");
            } else {
                request.getSession().setAttribute("errorMessage",
                    "Payment not found or could not be deleted");
            }
            
        } catch (SQLException e) {
            // Print error to server console for debugging
            System.err.println("Error deleting payment:");
            e.printStackTrace();
            
            request.getSession().setAttribute("errorMessage",
                "Database error occurred. Please try again.");
        }

        // 3. Redirect back to display page
        response.sendRedirect("displayPayments");
    }
}