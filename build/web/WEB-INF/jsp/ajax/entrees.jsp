<%@page import="java.util.Date"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="nbr_entree" value="${0}" />
<c:forEach items="${mesEntrees}" var="me">
    <c:if test="${empty me.heureEntreeBureau && empty me.heureSortieBureau}">
        <c:set var="nbr_entree" value="${nbr_entree+1}" />
        <div class="slide grid" onclick="metroDialog.open('#dialog-entree${me.identree}')">
            <div class="row cells3">
                <div class="cell padding10">
                    <span style="font-size: 5em;" class="icon mif-user fg-white"></span>
                </div>
                <div class="cell colspan2 padding10">
                    <h4>${me.nomComplet}</h4>
                    <h6>${me.entreprise}</h6>
                    <span>${me.motif}</span>
                </div>
            </div>
        </div>
    </c:if>
</c:forEach>
<c:if test="${nbr_entree==0}">
    <div class="slide padding20 fg-white half-opacity">
        <h3>Aucun visiteur en route vers votre bureau</h3>
    </div>
</c:if>
