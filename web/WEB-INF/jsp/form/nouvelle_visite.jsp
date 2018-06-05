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
        <h1 class="">Personne attendue</h1>
        <small class="">Les champs avec <span class="fg-red">*</span> sont obligatoires</small>
        <form class="grid" method="post" action="FormNouvelleVisiteServlet" style="padding-top: 20px ;">
            <div class="row cells2">
                <div class="cell colspan">
                    <label>Nom complet <span class="fg-red">*</span></label>
                    <div class="input-control text full-size" data-role="input">
                        <input name="nom" type="text" value="" required="true">
                    </div>
                </div>
                <div class="cell colspan">
                    <label>Entreprise</label>
                    <div class="input-control text full-size" data-role="input">
                        <input name="entreprise" type="text" value="">
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
                <div class="cell colspan3">
                    <label>Date <span class="fg-red">*</span></label>
                    <c:set var="now" value="<%=new java.util.Date()%>" />
                    <fmt:formatDate pattern="yyyy-MM-dd" value="${now}" var="dateEmission"/>
                    <div class="input-control text full-size" data-role="datepicker" data-preset="${dateEmission}">
                        <input type="text" name="date_arrivee">
                        <button class="button"><span class="mif-calendar"></span></button>
                    </div>
                </div>
                <div class="cell colspan3">
                    <label>Heure </label>
                    <div class="input-control text full-size" data-role="input">
                        <input name="heure" type="time" value="08:00" >
                    </div>
                </div>
            </div>
            <div class="row cells6">
                <div class="cell colspan6">
                    <label>Commentaire</label>
                    <div class="input-control textarea full-size" data-role="input">
                        <textarea name="commentaire" rows="2"></textarea>
                    </div>
                </div>
            </div>
            <div class="row cells6">
                <div class="cell colspan6">
                    <br>
                    <input type="submit" value="Enregistrement" class="button bg-darkGreen fg-white">
                </div>
            </div>
        </form>
    </body>
</html>
