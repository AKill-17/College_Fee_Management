<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.feeapp.model.FeePayment, java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Delete Fee Payment</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container">
        <h1>Delete Fee Payment</h1>
        <%
            FeePayment payment = (FeePayment) request.getAttribute("payment");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String paymentDate = sdf.format(payment.getPaymentDate());
        %>
        <form action="deletePayment" method="post">
            <input type="hidden" name="paymentId" value="<%= payment.getPaymentId() %>">
            <div class="form-group">
                <label>Student ID:</label>
                <span><%= payment.getStudentId() %></span>
            </div>
            <div class="form-group">
                <label>Student Name:</label>
                <span><%= payment.getStudentName() %></span>
            </div>
            <div class="form-group">
                <label>Payment Date:</label>
                <span><%= paymentDate %></span>
            </div>
            <div class="form-group">
                <label>Amount:</label>
                <span><%= payment.getAmount() %></span>
            </div>
            <div class="form-group">
                <label>Status:</label>
                <span><%= payment.getStatus() %></span>
            </div>
            <div class="form-group">
                <p>Are you sure you want to delete this payment record?</p>
                <input type="submit" value="Delete" class="btn danger">
                <a href="feepaymentdisplay.jsp" class="btn">Cancel</a>
            </div>
        </form>
        <div class="footer-credit">
 	  	Assembled in JAVA ✨ ~ Akhil
		</div>
    </div>
    <script src="theme.js"></script>
</body>
</html>
