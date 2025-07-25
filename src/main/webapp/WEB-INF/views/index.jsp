<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>JWT 인증 시스템</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 50px; }
        .container { max-width: 400px; margin: 0 auto; text-align: center; }
        .btn { padding: 10px 20px; margin: 10px; background: #007bff; color: white; text-decoration: none; border-radius: 5px; }
        .btn:hover { background: #0056b3; }
    </style>
</head>
<body>
<div class="container">
    <h1>JWT 인증 시스템</h1>
    <p>Spring Boot + JSP + JWT 로그인 시스템</p>
    <a href="${pageContext.request.contextPath}/login" class="btn">로그인</a>
    <a href="${pageContext.request.contextPath}/register" class="btn">회원가입</a>
    <a href="${pageContext.request.contextPath}/dashboard" class="btn">대시보드</a>
</div>
</body>
</html>