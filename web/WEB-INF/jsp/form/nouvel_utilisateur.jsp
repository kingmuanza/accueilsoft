<%@page import="java.util.Date"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
    </head>
    <body >
        <div class="grid">
            <div class="row cells4">
                <div class="cell colspan4">
                    <h1 class="">Nouvel utilisateur</h1>
                    <small class="">Les champs avec <span class="fg-red">*</span> sont obligatoires</small>
                </div>
            </div>
        </div>


        <form id="form1" runat="server" class="grid" method="post" action="FormNouveauUtilisateurServlet" style="padding-top: 20px ;">
            <div class="row cells6">
                <div class="cell colspan6">
                    <label>Login <span class="fg-red">*</span></label>
                    <div class="input-control text full-size" data-role="input">
                        <input name="login" type="text" value="" required="true">
                    </div>
                </div>
            </div>
            <div class="row cells6">
                <div class="cell colspan3">
                    <label>Mot de passe <span class="fg-red">*</span></label>
                    <div class="input-control text full-size" data-role="input">
                        <input name="passe" type="password" value="" required="true">
                    </div>
                </div>
                <div class="cell colspan3">
                    <label>Confirmation <span class="fg-red">*</span></label>
                    <div class="input-control text full-size" data-role="input">
                        <input name="confirmation" type="password" value="" required="true">
                    </div>
                </div>
            </div>
            <div class="row cells12">
            <div class="cell colspan6">
                <label>Employé <span class="fg-red">*</span></label>
                <div class="input-control select full-size" data-role="select" data-placeholder="Entrer le nom de la personne à voir">
                    <select name="employe" class="js-select" required="true">
                        <option></option>
                        <c:forEach items="${employes}" var="e">
                            <option value="${e.idemploye}">
                                ${e.noms} ${e.prenoms}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="cell colspan6">
                <label>Profil utilisateur <span class="fg-red">*</span></label>
                <div class="input-control select full-size" data-role="select" data-placeholder="Entrer le nom du profil utilisateur">
                    <select name="profil" class="js-select" required="true">
                        <option></option>
                        <c:forEach items="${utilisateurProfils}" var="up">
                            <option value="${up.idutilisateurProfil}">
                                ${up.nom}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            </div>
                <div class="row cells6">
                <div class="cell colspan6">
                    <br>
                    <input type="submit" value="Enregistrement" class="button bg-darkOrange fg-white">
                </div>
            </div>
        </form>
    </body>
</html>
