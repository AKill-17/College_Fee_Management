package com.feeapp.servlet;

import com.feeapp.dao.FeePaymentDAO;
import com.feeapp.model.FeePayment;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/displayPayments")
public class DisplayFeePaymentsServlet extends HttpServlet {
    private FeePaymentDAO feePaymentDAO;

    public void init() {
        feePaymentDAO = new FeePaymentDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        int paymentId = 0;
        
        try {
            paymentId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            // ID parameter not provided or invalid
        }

        try {
            if ("edit".equals(action)) {
                showEditForm(request, response, paymentId);
            } else if ("delete".equals(action)) {
                deletePayment(request, response, paymentId);
            } else {
                listPayments(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void listPayments(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<FeePayment> feePayments = feePaymentDAO.selectAllPayments();
        request.setAttribute("feePayments", feePayments);
        RequestDispatcher dispatcher = request.getRequestDispatcher("feepaymentdisplay.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response, int paymentId)
            throws ServletException, IOException {
        FeePayment existingPayment = feePaymentDAO.selectPayment(paymentId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("feepaymentupdate.jsp");
        request.setAttribute("payment", existingPayment);
        dispatcher.forward(request, response);
    }

    private void deletePayment(HttpServletRequest request, HttpServletResponse response, int paymentId) 
            throws ServletException, IOException {
        
        // Debug logging
        System.out.println("Attempting to delete payment ID: " + paymentId);
        
        try {
            boolean deletionSuccessful = feePaymentDAO.deletePayment(paymentId);
            
            if (deletionSuccessful) {
                // Set success message in session to survive redirect
                request.getSession().setAttribute("successMessage", 
                    "Payment ID " + paymentId + " was successfully deleted");
            } else {
                request.getSession().setAttribute("errorMessage",
                    "Failed to delete payment ID " + paymentId + " (no records affected)");
            }
            
        } catch (Exception e) { // Catch any unexpected exceptions
            System.err.println("Unexpected error during deletion:");
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage",
                "System error while deleting payment: " + e.getMessage());
        }
        
        // Always redirect back to display page
        response.sendRedirect("displayPayments");
    }
}