<%@page import="java.util.Date"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1 class="">${entree.nomComplet}</h1>
        <form class="grid" method="post" action="EntreeServlet" style="padding-top: 20px ;">
            <div class="row">
                <div class="cell">
                    <label style="font-size : 1.5em">
                        Confirmez-vous la sortie du visiteur de votre bureau ?
                    </label>
                    <input type="hidden" name="action" value="confirmation_sortie">
                    <input type="hidden" name="id" value="${entree.identree}">
                </div>
            </div>
            <div class="row">
            </div>
            <div class="row cells6" style="padding-top: 20px;">
                <div class="cell colspan6">
                
                    <input type="submit" value="Oui" class="button bg-orange fg-white">
                    <a href="start" class="button bg-dark fg-white">Non</a>
                </div>
            </div>
        </form>

    </body>
</html>
