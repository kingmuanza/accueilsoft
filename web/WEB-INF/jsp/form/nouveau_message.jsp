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
        <h1 class="">Nouveau message</h1>
        <c:if test="${ true }">
            <form class="grid" method="post" action="FormNouveauMessageServlet" style="padding-top: 20px ;">
                <div class="row">
                    <div class="cell">
                        <label>Destinataire</label>
                        <div class="input-control select full-size" data-role="select" data-placeholder="Entrer le nom de la personne à voir">
                            <select name="employe" class="js-select">
                                <option></option>
                                <c:forEach items="${employes}" var="e">
                                    <option id="employe_${e.idemploye}" value="${e.idemploye}" ${employe.idemploye==e.idemploye? "selected":""} class="test">
                                        ${e.noms} ${e.prenoms} 
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row cells6">
                    <div class="cell colspan6">
                        <label>Contenu</label>
                        <div class="input-control textarea full-size" data-role="input">
                            <textarea name="contenu" rows="2"></textarea>
                        </div>
                    </div>
                </div>
                <div class="row cells6">
                    <div class="cell colspan6">
                        <input type="submit" value="Envoyer" class="button bg-darkIndigo fg-white">
                    </div>
                </div>
            </form>
        </c:if>


    </body>
</html>
