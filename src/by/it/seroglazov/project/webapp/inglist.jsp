<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>Cocktails list</title>
	<style>
           #ingredients {
            color: #fff;
           }
        </style>
	<%@ include file="include/head.jsp" %>
</head>
<body>
	<%@ include file="include/menu.jsp" %>
	<div class="container">
        <c:if test="${error_message!=null}">
            <br>
            <div class="alert alert-danger" role="alert">${error_message}</div>
            <c:remove var="error_message" scope="session" />
        </c:if>

        <div class="container mb-3">
            <h3>Ingredients in your bar:</h3>
        <c:forEach var="user_ingredient" items="${user_ingredients}">
		    <form action="do?command=RemIngFromBar" method="POST">
		        <input type="hidden" name="ingredient_id" value="${user_ingredient.id}" >
		        <input type="submit" class="btn btn-success btn-lg btn-block text-left my-2" name="ing" value="${user_ingredient.name}" />
		    </form>
		</c:forEach>
		</div>

        <div class="container">
        <h3>Choose ingredients you already have:</h3>
		<c:forEach var="other_ingredient" items="${other_ingredients}">
            <form action="do?command=AddIngToBar" method="POST">
		        <input type="hidden" name="ingredient_id" value="${other_ingredient.id}" >
		        <input type="submit" class="btn btn-info btn-lg btn-block text-left my-2" name="ing" value="${other_ingredient.name}" />
		    </form>
		</c:forEach>
		</div>

	</div>
</body>
</html>