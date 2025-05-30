<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Total Collection Report</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container">
        <h1>Total Collection Report</h1>
        <form action="report" method="get">
            <input type="hidden" name="reportType" value="collection">
            <div class="form-group">
                <label for="startDate">Start Date:</label>
                <input type="date" id="startDate" name="startDate" required>
            </div>
            <div class="form-group">
                <label for="endDate">End Date:</label>
                <input type="date" id="endDate" name="endDate" required>
            </div>
            <div class="form-group">
                <input type="submit" value="Generate Report" class="btn">
                <a href="reports.jsp" class="btn cancel">Cancel</a>
            </div>
        </form>
    </div>
    <div class="footer-credit">
 	  	Assembled in JAVA ✨ ~ Akhil
		</div>
    <script src="theme.js"></script>
</body>
</html>