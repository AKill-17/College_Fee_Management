<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.feeapp.model.FeePayment, java.util.List, java.text.SimpleDateFormat" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Fee Payments</title>
    <link rel="stylesheet" href="style.css">
    <style>
        .success-message {
            background-color: #dff0d8;
            color: #3c763d;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #d6e9c6;
            border-radius: 4px;
        }
        .error-message {
            background-color: #f2dede;
            color: #a94442;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ebccd1;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <%-- Display success/error messages --%>
    <% String successMessage = (String) request.getSession().getAttribute("successMessage");
       if (successMessage != null) { %>
        <div class="success-message"><%= successMessage %></div>
        <% request.getSession().removeAttribute("successMessage"); %>
    <% } %>
    <% String errorMessage = (String) request.getSession().getAttribute("errorMessage");
       if (errorMessage != null) { %>
        <div class="error-message"><%= errorMessage %></div>
        <% request.getSession().removeAttribute("errorMessage"); %>
    <% } %>

    <div class="container">
        <h1>Fee Payment Records</h1>
        <div class="actions">
            <a href="feepaymentadd.jsp" class="btn">Add New Payment</a>
            <a href="index.jsp" class="btn">Back to Home</a>
        </div>
        
        <table>
            <thead>
                <tr>
                    <th>Payment ID</th>
                    <th>Student ID</th>
                    <th>Student Name</th>
                    <th>Payment Date</th>
                    <th>Amount</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<FeePayment> feePayments = (List<FeePayment>) request.getAttribute("feePayments");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    
                    if (feePayments != null && !feePayments.isEmpty()) {
                        for (FeePayment payment : feePayments) {
                            String paymentDate = sdf.format(payment.getPaymentDate());
                %>
                <tr>
                    <td><%= payment.getPaymentId() %></td>
                    <td><%= payment.getStudentId() %></td>
                    <td><%= payment.getStudentName() %></td>
                    <td><%= paymentDate %></td>
                    <td><%= payment.getAmount() %></td>
                    <td><%= payment.getStatus() %></td>
                    <td>
                        <a href="displayPayments?action=edit&id=<%= payment.getPaymentId() %>" class="btn">Edit</a>
                        <form action="deletePayment" method="post" style="display: inline;">
                            <input type="hidden" name="paymentId" value="<%= payment.getPaymentId() %>">
                            <button type="submit" class="btn danger" 
                                    onclick="return confirm('Are you sure you want to delete this payment?')">
                                Delete
                            </button>
                        </form>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="7">No fee payment records found</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
        <div class="footer-credit">
 	  	Assembled in JAVA ✨ ~ Akhil
		</div>
    </div>
    <script src="theme.js"></script>
</body>
</html>