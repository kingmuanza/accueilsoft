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
        <h1 class="fg-white" style="font-weight: 200; font-size: 3.5em">
            Nouveau colis
        </h1>
        <small class="fg-white">Les champs avec <span class="fg-red">*</span> sont obligatoires</small>
        <form class="grid" method="post" action="FormNouveauColisServlet" style="padding-top: 20px ;">
            <div class="row cells6">
                <div class="cell colspan6">
                    <label>Nom complet du déposant <span class="fg-red">*</span></label>
                    <div class="input-control text full-size" data-role="input">
                        <input name="nom" type="text" value="" required="true">
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
                        <input name="numeropiece" type="text" value="${courrier.idcourrier}" required="true">
                    </div>
                </div>

            </div>
            
            <div class="row cells6">
                <div class="cell colspan3">
                    <label>Numéro colis <span class="fg-red">*</span></label>
                    <div class="input-control text full-size" data-role="input">
                        <input name="numerocolis" type="text" value="" >
                    </div>
                </div>
                <div class="cell colspan3">
                    <label>Employé destinataire</label>
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
                    <label>Description</label>
                    <div class="input-control textarea full-size" data-role="input">
                        <textarea name="description" rows="2"></textarea>
                    </div>
                </div>
            </div>
            <div class="row cells6">
                <div class="cell colspan6">
                    <br>
                    <input style="font-weight: 200; border: 2px solid " type="submit" value="Enregistrement" class="button bg-green fg-white bd-white">

                </div>
            </div>
        </form>

    </body>
</html>
