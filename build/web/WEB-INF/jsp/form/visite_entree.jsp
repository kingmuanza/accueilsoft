<%@page import="java.util.Date"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            label{
                font-size: 1.2em ;
                font-weight: 300 ;
            }

            textarea:focus, input:focus, select:focus{
                border: 2px solid #444;
            }
        </style>
    </head>
    <body>
        <h1 class="" id="personne_attendue">${visite.nomPersonneAttendue}</h1>
        <span style="font-size: 1.2em ">${visite.entreprise}</span>
        <p class="">
            <fmt:formatDate pattern="HH:mm" value="${visite.heureAttendue}" var="dateEmission"/>
            Attendu au ${visite.employe.bureau.libelle} à ${dateEmission}
        </p>

        <form class="grid" method="post" action="VisiteToEntreeServlet" style="padding-top: 30px ;">
            <input name="idvisite" type="hidden" value="${visite.idvisite}">

            <div class="row cells6">
                <div class="cell colspan6">
                    <label>Motif</label>
                    <div class="input-control text full-size" data-role="input">
                        <input name="motif" type="text" value="${visite.motif}" disabled="true">
                    </div>
                </div>
            </div>
            <div class="row cells6">
                <div class="cell colspan6">
                    <label>Commentaire</label>
                    <div class="input-control textarea full-size" data-role="input">
                        <textarea name="commentaire" rows="2" disabled="true">${visite.commentaire}</textarea>
                    </div>
                </div>
            </div>
            <div class="row cells8">
                <div class="cell colspan3">
                    <label>Pièce</label>
                    <div class="input-control select full-size">
                        <select name="piece">
                            <option value="CNI">Carte Nationale d'Identité</option>
                            <option value="Récépissé CNI">Recépissé de la carte Nationale d'Identité</option>
                            <option value="Permis de conduire">Permis de conduire</option>
                            <option value="Passeport">Passeport</option>
                        </select>
                    </div>
                </div>
                <div class="cell colspan3">
                    <label>Numéro de pièce  <span class="fg-red">*</span></label>
                    <div class="input-control text full-size" data-role="input">
                        <input name="numero" type="text" value="" required="true">
                    </div>
                </div>
                <div class="cell colspan2">
                    <label>Badge attribué</label>
                    <div class="input-control select full-size" data-role="select" data-placeholder="">
                        <select name="badge" class="js-select full-size">
                            <option></option>
                            <c:forEach items="${entreeBadges}" var="eb">
                                <option value="${eb.identreeBadge}">${eb.numero}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row cells6">
                <small class="">Les champs avec <span class="fg-red">*</span> sont obligatoires</small>
            </div>
            <div class="row cells6">
                <div class="cell colspan6">
                    <br>
                    <input type="submit" value="Enregistrement" class="button bg-darkBlue fg-white">
                </div>
            </div>
        </form>
    </body>
</html>
