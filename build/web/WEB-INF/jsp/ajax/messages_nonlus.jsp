<%@page import="java.util.Date"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="nbr_entree" value="${0}" />
<c:forEach items="${messages_nonlus}" var="m">
    <div class="list" onclick="messageLu('${m.idmessage}');
            metroDialog.open('#dialog-form-nouveau-message');
            toggleMetroCharm('#right-charm')" style="border-bottom: 1px solid #fff; padding-bottom: 10px; padding-top: 10px">
        <div class="list-content fg-white">
            <b class="list-title" style="font-size:1.2em;">
                ${m.employeByIdemetteur.noms} ${m.employeByIdemetteur.prenoms}
            </b>
            <div class="list-subtitle fg-white" style="font-size:1.2em;">
                <q> ${m.contenu} </q>
            </div>
            <span class="list-remark">
                <fmt:setLocale value="fr" />
                <fmt:formatDate value="${m.dateEnvoi}" type="both" var="dateEnvoi" />
                ${dateEnvoi}
            </span>
        </div>
    </div>
</c:forEach>
