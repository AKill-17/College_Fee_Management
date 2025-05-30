package com.feeapp.servlet;

import com.feeapp.dao.FeePaymentDAO;
import com.feeapp.model.FeePayment;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updatePayment")
public class UpdateFeePaymentServlet extends HttpServlet {
    private FeePaymentDAO feePaymentDAO;

    public void init() {
        feePaymentDAO = new FeePaymentDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int paymentId = Integer.parseInt(request.getParameter("paymentId"));
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            String studentName = request.getParameter("studentName");
            String paymentDateStr = request.getParameter("paymentDate");
            double amount = Double.parseDouble (request.getParameter("amount"));
            String status = request.getParameter("status");
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date paymentDate = format.parse(paymentDateStr);
            
            FeePayment payment = new FeePayment(paymentId, studentId, studentName, paymentDate, amount, status);
            feePaymentDAO.updatePayment(payment);
            response.sendRedirect("displayPayments");
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}