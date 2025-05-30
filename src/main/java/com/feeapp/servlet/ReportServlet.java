package com.feeapp.servlet;

import com.feeapp.dao.FeePaymentDAO;
import com.feeapp.model.FeePayment;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/report")
public class ReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FeePaymentDAO feePaymentDAO;

    public void init() {
        feePaymentDAO = new FeePaymentDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String reportType = request.getParameter("reportType");

        try {
            if ("unpaid".equals(reportType)) {
                List<FeePayment> unpaidStudents = feePaymentDAO.getUnpaidStudents();
                request.setAttribute("reportData", unpaidStudents);
                request.setAttribute("reportTitle", "Unpaid Students Report");

            } else if ("overdue".equals(reportType)) {
                List<FeePayment> overduePayments = feePaymentDAO.getOverduePayments();
                request.setAttribute("reportData", overduePayments);
                request.setAttribute("reportTitle", "Overdue Payments Report");

            } else if ("collection".equals(reportType)) {
                String startDateStr = request.getParameter("startDate");
                String endDateStr = request.getParameter("endDate");

                if (startDateStr == null || endDateStr == null) {
                    response.sendRedirect("error.jsp");
                    return;
                }

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = format.parse(startDateStr);
                Date endDate = format.parse(endDateStr);

                // ✅ Convert util.Date to sql.Date
                java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
                java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

                double totalCollection = feePaymentDAO.getTotalCollection(sqlStartDate, sqlEndDate);
                request.setAttribute("totalCollection", totalCollection);
                request.setAttribute("startDate", startDateStr);
                request.setAttribute("endDate", endDateStr);
                request.setAttribute("reportTitle", "Total Collection Report");
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("report_result.jsp");
            dispatcher.forward(request, response);

        } catch (ParseException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
