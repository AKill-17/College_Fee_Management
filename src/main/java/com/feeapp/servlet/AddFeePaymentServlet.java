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

@WebServlet("/addPayment")
public class AddFeePaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FeePaymentDAO feePaymentDAO;
    
    public void init() {
        feePaymentDAO = new FeePaymentDAO();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            String studentName = request.getParameter("studentName");
            String paymentDateStr = request.getParameter("paymentDate");
            double amount = Double.parseDouble(request.getParameter("amount"));
            String status = request.getParameter("status");
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date paymentDate = format.parse(paymentDateStr);
            
            FeePayment newPayment = new FeePayment();
            newPayment.setStudentId(studentId);
            newPayment.setStudentName(studentName);
            newPayment.setPaymentDate(paymentDate);
            newPayment.setAmount(amount);
            newPayment.setStatus(status);
            
            feePaymentDAO.insertPayment(newPayment);
            response.sendRedirect("feepaymentdisplay.jsp");
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}