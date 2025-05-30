<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.feeapp.model.FeePayment, java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Fee Payment</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container">
        <h1>Update Fee Payment</h1>
        <%
            FeePayment payment = (FeePayment) request.getAttribute("payment");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String paymentDate = sdf.format(payment.getPaymentDate());
        %>
        <form action="updatePayment" method="post">
            <input type="hidden" name="paymentId" value="<%= payment.getPaymentId() %>">
            <div class="form-group">
                <label for="studentId">Student ID:</label>
                <input type="number" id="studentId" name="studentId" value="<%= payment.getStudentId() %>" required>
            </div>
            <div class="form-group">
                <label for="studentName">Student Name:</label>
                <input type="text" id="studentName" name="studentName" value="<%= payment.getStudentName() %>" required>
            </div>
            <div class="form-group">
                <label for="paymentDate">Payment Date:</label>
                <input type="date" id="paymentDate" name="paymentDate" value="<%= paymentDate %>" required>
            </div>
            <div class="form-group">
                <label for="amount">Amount:</label>
                <input type="number" step="0.01" id="amount" name="amount" value="<%= payment.getAmount() %>" required>
            </div>
            <div class="form-group">
                <label for="status">Status:</label>
                <select id="status" name="status" required>
                    <option value="Paid" <%= payment.getStatus().equals("Paid") ? "selected" : "" %>>Paid</option>
                    <option value="Partial" <%= payment.getStatus().equals("Partial") ? "selected" : "" %>>Partial</option>
                    <option value="Unpaid" <%= payment.getStatus().equals("Unpaid") ? "selected" : "" %>>Unpaid</option>
                </select>
            </div>
            <div class="form-group">
                <input type="submit" value="Update" class="btn">
                <a href="displayPayments" class="btn cancel">Cancel</a>
            </div>
        </form>
        <div class="footer-credit">
 	  	Assembled in JAVA ✨ ~ Akhil
		</div>
    </div>
    <script src="theme.js"></script>
</body>
</html>