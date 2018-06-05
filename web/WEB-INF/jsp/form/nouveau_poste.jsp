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
            ${empty poste ? "Nouveau poste": poste.libelle}
        </h1>
        <small class="fg-white">Les champs avec <span class="fg-red">*</span> sont obligatoires</small>
        <form class="grid" method="post" action="FormPosteServlet" style="padding-top: 20px ;">
            <div class="row cells3">
                <div class="cell ">
                    <label>Code <span class="fg-red">*</span></label>
                    <div class="input-control text full-size" data-role="input">
                        <input name="id" type="hidden" value="${poste.idposte}" required="true">
                        <input name="code" type="text" value="${poste.code}" required="true">
                    </div>
                </div>
                <div class="cell colspan2">
                    <label>Libellé <span class="fg-red">*</span></label>
                    <div class="input-control text full-size" data-role="input">
                        <input name="libelle" type="text" value="${poste.libelle}" required="true">
                    </div>
                </div>
            </div>
            <div class="row cells3">
                <div class="cell colspan3">
                    <label>Poste parent</label>
                    <div class="input-control select full-size" data-role="select" data-placeholder="Entrer le poste parent">
                        <select name="parent" class="js-select full-size">
                            <option></option>
                            <c:forEach items="${postes}" var="p">
                                <option ${poste.poste.idposte==p.idposte ? "selected":""} value="${p.idposte}">${p.libelle}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row cells6">
                <div class="cell colspan6">
                    <br>
                    <input style="background: none !important" type="submit" value="Enregistrement" class="button  fg-white bd-white">
                    <button style="" type="submit" name="action" value="supprimer" class="button bg-dark fg-white bd-white"> 
                        Supprimer
                    </button>
                </div>
            </div>
        </form>

    </body>
</html>
