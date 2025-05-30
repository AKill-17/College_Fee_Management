<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Fee Payment</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container">
        <h1>Add Fee Payment</h1>
        <form action="addPayment" method="post">
            <div class="form-group">
                <label for="studentId">Student ID:</label>
                <input type="number" id="studentId" name="studentId" required>
            </div>
            <div class="form-group">
                <label for="studentName">Student Name:</label>
                <input type="text" id="studentName" name="studentName" required>
            </div>
            <div class="form-group">
                <label for="paymentDate">Payment Date:</label>
                <input type="date" id="paymentDate" name="paymentDate" required>
            </div>
            <div class="form-group">
                <label for="amount">Amount:</label>
                <input type="number" step="0.01" id="amount" name="amount" required>
            </div>
            <div class="form-group">
                <label for="status">Status:</label>
                <select id="status" name="status" required>
                    <option value="Paid">Paid</option>
                    <option value="Partial">Partial</option>
                    <option value="Unpaid">Unpaid</option>
                </select>
            </div>
            <div class="form-group">
                <input type="submit" value="Add Payment" class="btn">
                <a href="index.jsp" class="btn cancel">Cancel</a>
            </div>
        </form>
        <div class="footer-credit">
 	  	Assembled in JAVA ✨ ~ Akhil
		</div>
        
    </div>
    <script src="theme.js"></script>
</body>
</html>