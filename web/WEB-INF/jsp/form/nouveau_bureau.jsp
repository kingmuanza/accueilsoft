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
            ${empty bureau ? "Nouveau bureau": bureau.libelle}
        </h1>
        <small class="fg-white">Les champs avec <span class="fg-red">*</span> sont obligatoires</small>
        <form class="grid" method="post" action="FormBureauServlet" style="padding-top: 20px ;">
            <div class="row cells3">
                <div class="cell ">
                    <label>Code <span class="fg-red">*</span></label>
                    <div class="input-control text full-size" data-role="input">
                        <input name="id" type="hidden" value="${bureau.idbureau}" required="true">
                        <input name="code" type="text" value="${bureau.code}" required="true">
                    </div>
                </div>
                <div class="cell colspan2">
                    <label>Libellé <span class="fg-red">*</span></label>
                    <div class="input-control text full-size" data-role="input">
                        <input name="libelle" type="text" value="${bureau.libelle}" required="true">
                    </div>
                </div>
            </div>
            <div class="row cells3 ">
                <div class="cell ">
                    <label>Etage <span class="fg-red">*</span></label>
                    <div class="input-control text full-size" data-role="input">
                        <input name="etage" type="text" value="${bureau.etage}" required="true">
                    </div>
                </div>
                <div class="cell colspan2">
                    <label>Numéro de porte <span class="fg-red">*</span></label>
                    <div class="input-control text full-size" data-role="input">
                        <input name="numeroPorte" type="text" value="${bureau.numeroPorte}" required="true">
                    </div>
                </div>
            </div>
            <div class="row cells6">
                <div class="cell colspan6">
                    <br>
                    <input style="background: none !important" type="submit" value="Enregistrer" class="button  fg-white bd-white">
                    <button style="" type="submit" name="action" value="supprimer" class="button bg-dark fg-white bd-white"> 
                        Supprimer
                    </button>

                </div>
            </div>
        </form>

    </body>
</html>
