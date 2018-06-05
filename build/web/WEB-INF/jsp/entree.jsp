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
        <script>
            function sortie(id) {

                $.confirm({
                    title: 'Confirmation',
                    boxWidth: '30%',
                    useBootstrap: false,
                    content: 'Avez-vous remis la pièce d\'identité du visiteur et récupéré le badge ?\n',
                    buttons: {
                        Oui: {
                            text: 'Oui, je l\'ai fait',
                            btnClass: 'button bg-orange fg-white',
                            action: function () {
                                $.post(
                                        "EntreeServlet",
                                        {
                                            action: "sortie",
                                            id: id
                                        }
                                );
                                history.go(0);
                                window.location.href = window.location.href;
                            }
                        },
                        Annuler: {
                            text: 'Annuler',
                            btnClass: 'button ribbed-gray fg-white',
                            action: function () {

                            }
                        }
                    }
                });

            }
        </script>
    </head>
    <body >
        <h1 class="">
            Entrée à <fmt:formatDate type = "time" value = "${entree.heureDentree}" />

        </h1>
            <form class="grid" id="entree" method="post" action="EntreeServlet" style="padding-top: 20px ;">
            <div class="row cells6">
                <div class="cell colspan4">
                    <label>Nom complet <span class="fg-red">*</span></label>
                    <div class="input-control text full-size" data-role="input">
                        <input name="nom" type="text" value="${entree.nomComplet}" required="true">
                        
                        <input name="id" type="hidden" value="${entree.identree}" required="true">
                        
                    </div>
                </div>
                <div class="cell colspan2">
                    <label>Badge attribué</label>
                    <div class="input-control select full-size" data-role="select" data-placeholder="">
                        <select name="badge" class="js-select full-size">
                            <option></option>
                            <c:forEach items="${entreeBadgesAll}" var="eb">
                                <option value="${eb.identreeBadge}" ${eb.identreeBadge==entree.entreeBadge.identreeBadge ? "selected":""}>
                                    ${eb.numero} ${eb.statut=="Utilisé" ? "*":""}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row cells6">
                <div class="cell colspan6">
                    <label>Entreprise</label>
                    <div class="input-control text full-size" data-role="input">
                        <input name="entreprise" type="text" value="${entree.entreprise}">
                    </div>
                </div>
            </div>
            <div class="row cells6">
                <div class="cell colspan3">
                    <label>Pièce</label>
                    <div class="input-control select full-size">
                        <select name="piece" value="${entree.piece}">
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
                        <input name="numero" type="text" value="${entree.numeroDePiece}" required="true">
                    </div>
                </div>

            </div>
            <div class="row cells6">
                <div class="cell colspan6">
                    <label>Motif</label>
                    <div class="input-control text full-size" data-role="input">
                        <input name="motif" type="text" value="${entree.motif}">
                    </div>
                </div>
            </div>
            <div class="row cells12">
                <div class="cell colspan6">
                    <label>Bureau de destination <span class="fg-red">*</span></label>
                    <div class="input-control select full-size" data-role="select" data-placeholder="Entrer le nom du bureau de destination">
                        <select name="bureau" class="full-size" required="true">
                            <c:forEach items="${bureaux}" var="b">
                                <option value="${b.idbureau}" ${b.idbureau==entree.bureau.idbureau ? "selected":""}>
                                    ${b.libelle}
                                </option>
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
                                <option ${entree.employe.idemploye==e.idemploye ? "selected":""} id="employeMod_${e.idemploye}" value="${e.idemploye}">
                                    ${e.noms} ${e.prenoms}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row cells6">
                <div class="cell colspan6">
                    <br>
                    <button  type="submit" value="sortie" class="button bg-orange fg-white" name="action">
                        La personne est sortie
                    </button>
                    <button type="submit" value="modifier" class="button bg-green fg-white" name="action">
                        Modifier
                    </button>
                </div>
            </div>
        </form>

    </body>
</html>
