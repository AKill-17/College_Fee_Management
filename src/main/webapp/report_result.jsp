<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.feeapp.model.FeePayment, java.util.List, java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Report Results</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container">
        <h1><%= request.getAttribute("reportTitle") %></h1>
        
        <% if ("collection".equals(request.getParameter("reportType"))) { %>
            <div class="report-summary">
                <p>Total Collection from <%= request.getAttribute("startDate") %> to <%= request.getAttribute("endDate") %>:</p>
                <h2>$<%= request.getAttribute("totalCollection") %></h2>
            </div>
        <% } else { 
            List<FeePayment> reportData = (List<FeePayment>) request.getAttribute("reportData");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        %>
            <table>
                <thead>
                    <tr>
                        <th>Payment ID</th>
                        <th>Student ID</th>
                        <th>Student Name</th>
                        <th>Payment Date</th>
                        <th>Amount</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <% if (reportData != null && !reportData.isEmpty()) { 
                        for (FeePayment payment : reportData) { 
                            String paymentDate = sdf.format(payment.getPaymentDate());
                    %>
                    <tr>
                        <td><%= payment.getPaymentId() %></td>
                        <td><%= payment.getStudentId() %></td>
                        <td><%= payment.getStudentName() %></td>
                        <td><%= paymentDate %></td>
                        <td><%= payment.getAmount() %></td>
                        <td><%= payment.getStatus() %></td>
                    </tr>
                    <% } 
                    } else { %>
                    <tr>
                        <td colspan="6">No records found</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        <% } %>
        
        <div class="actions">
            <a href="reports.jsp" class="btn">Back to Reports</a>
            <a href="index.jsp" class="btn">Back to Home</a>
        </div>
        <div class="footer-credit">
 	  	Assembled in JAVA ✨ ~ Akhil
		</div>
    </div>
    <script src="theme.js"></script>
</body>
</html>