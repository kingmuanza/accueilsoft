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
        <h1 class="fg-white" style="font-weight: 200; font-size: 3.5em">Nouvelle entrée</h1>
        <small class="fg-white">Les champs avec <span class="fg-red">*</span> sont obligatoires</small>
        <form class="grid" method="post" action="FormEntreeServlet" style="padding-top: 20px ;">
            <div class="row cells6">
                <div class="cell colspan4">
                    <label>Nom complet <span class="fg-red">*</span></label>
                    <div class="input-control text full-size" data-role="input">
                        <input name="nom" type="text" value="" required="true">
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
                <div class="cell colspan6">
                    <label>Entreprise</label>
                    <div class="input-control text full-size" data-role="input">
                        <input name="entreprise" type="text" value="">
                    </div>
                </div>
            </div>
            <div class="row cells6">
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
                        <input name="numero" type="text" value="${courrier.idcourrier}" required="true">
                    </div>
                </div>

            </div>
            <div class="row cells6">
                <div class="cell colspan6">
                    <label>Motif</label>
                    <div class="input-control text full-size" data-role="input">
                        <input name="motif" type="text" value="">
                    </div>
                </div>
            </div>
            <div class="row cells12">
                <div class="cell colspan6">
                    <label>Bureau de destination <span class="fg-red">*</span></label>
                    <div class="input-control select full-size" data-role="select" data-placeholder="Entrer le nom du bureau de destination">
                        <select name="bureau" class="js-select full-size" required="true">
                            <option></option>
                            <c:forEach items="${bureaux}" var="b">
                                <option value="${b.idbureau}">${b.libelle}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="cell colspan6">
                    <label>Employé à voir</label>
                    <div class="input-control select full-size" data-role="select" data-placeholder="Entrer le nom de la personne à voir">
                        <select name="employe" class="js-select">
                            <option></option>
                            <c:forEach items="${employes}" var="e">
                                <option id="employe_${e.idemploye}" value="${e.idemploye}" class="test" bureau="${e.bureau.idbureau}">${e.noms} ${e.prenoms}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row cells6">
                <div class="cell colspan6">
                    <br>
                    <input style="font-weight: 200; border: 2px solid " type="submit" value="Enregistrement" class="button bg-blue fg-white bd-white">

                </div>
            </div>
        </form>

    </body>
</html>
