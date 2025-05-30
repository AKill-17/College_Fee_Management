<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Generate Reports</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container">
        <h1>Generate Reports</h1>
        <div class="menu">
            <a href="reportCriteria?reportType=unpaid" class="btn">Unpaid Students</a>
            <a href="reportCriteria?reportType=overdue" class="btn">Overdue Payments</a>
            <a href="reportCriteria?reportType=collection" class="btn">Total Collection</a>
            <a href="index.jsp" class="btn">Back to Home</a>
        </div>
        <div class="footer-credit">
 	  	Assembled in JAVA ✨ ~ Akhil
		</div>
    </div>
    <script src="theme.js"></script>
</body>
</html>