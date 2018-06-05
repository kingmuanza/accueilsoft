<%@page import="java.util.Date"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Historique</title>
        <script src="js/jquery-qrcode-0.14.0.js" type="text/javascript"></script>
        <script>
            var titre = "Historique des entrées";
            $("#historique").DataTable({
                'searching': true,
                "iDisplayLength": 5,
                "info": true,
                dom: '<"top"fB>rt<"bottom"lp><"clear">',
                "order": [[0, "desc"], [1, 'desc']],
                buttons: [
                    {
                        extend: 'excel',
                        text: "Exporter vers Excel",
                        title: titre,
                        message: '',
                        className: 'impressionExcel ribbed-grayLighter bd-white'
                    },
                    {
                        extend: 'pdfHtml5',
                        text: "Exporter en PDF",
                        title: titre,
                        message: '',
                        className: 'impressionPDF ribbed-grayLighter bd-white'
                    },
                    {
                        extend: 'print',
                        text: "Imprimer",
                        title: titre,
                        message: '',
                        className: 'impression ribbed-grayLighter bd-white'
                    }
                ],
                "language": {
                    "sEmptyTable": "Aucune donnée disponible",
                    "sInfo": "Affiche _START_ à _END_ sur _TOTAL_ entrées",
                    "sLengthMenu": "Afficher _MENU_ lignes par page",
                    "sSearch": "Rechercher : ",
                    "zeroRecords": "Aucun résultat",
                    "info": "Page _PAGE_ sur _PAGES_",
                    "infoEmpty": "Aucun résultat disponible",
                    "sProcessing": "Veuillez patienter...",
                    "infoFiltered": "(sur les _MAX_ disponibles)",
                    "paginate": {
                        "previous": "Précédent",
                        "next": "Suivant"
                    }
                }
            });

           
        </script>
    </head>
    <body >
        <div class="grid padding10" style="height: 450px; overflow-y: scroll">
            <h1 class="fg-blue" style="cursor: pointer; padding-left : 30px; " onclick="window.location.href = '#/historique'">Historique des entrées</h1>
            <h5 class="fg-green" style="padding-left : 30px; ">${!empty employe ? employe.noms : "Tous les employés"} ${!empty employe ? employe.prenoms : ""}</h5>

            <div class="row cells6" style="padding-right: 40px; padding-top: 0px; padding-left : 30px; ">
                <table id="historique" class="dataTable border bordered" style="margin-bottom: 10px;">
                    <thead>
                        <tr>
                            <th>Date</th>
                            <th>Entrée</th>
                            <th>Sortie</th>
                            <th>Nom complet</th>
                            <th>Bureau</th>
                            <th>Employé</th>
                            <th>Motif</th>
                            <th>Badge</th>
                            <th>Visite</th>
                        </tr>
                    </thead>

                    <tfoot >
                        <tr>
                            <th>Date</th>
                            <th>Entrée</th>
                            <th>Sortie</th>
                            <th>Nom complet</th>
                            <th>Bureau</th>
                            <th>Employé</th>
                            <th>Motif</th>
                            <th>Badge</th>
                            <th>Visite</th>
                        </tr>
                    </tfoot>
                    <tbody>
                        <c:forEach items="${entrees}" var="e">
                            <c:if test="${!empty premierEntree && premierEntree.before(e.heureDentree) && !empty derniereEntree && derniereEntree.after(e.heureDentree)}">

                                <c:set var = "maintenant" value = "<%=new Date()%>" />
                                <fmt:formatDate pattern = "yyyy-MM-dd" value = "${maintenant}" var="oday"/>
                                <fmt:formatDate value="${e.heureDentree}" type="time" timeStyle="short" var="arrive" />
                                <fmt:formatDate pattern = "yyyy-MM-dd" value = "${e.heureDentree}" var="jour_arrivee"/>
                                <tr class="${oday!=jour_arrivee && empty e.heureSortie ? 'ribbed-grayLighter fg-hover-red':''}" style="cursor:pointer;" 
                                    ${oday!=jour_arrivee && empty e.heureSortie ? " data-role='hint' data-hint-background='bg-red' data-hint-color='fg-white' data-hint='La sortie n`a pas été enregistrée. Cliquez pour continuer '":''}
                                    <c:if test="${oday!=jour_arrivee && empty e.heureSortie}">
                                        onclick="showDialog('#entree', '${e.identree}', '${e.nomComplet}', '${e.entreeBadge.identreeBadge}', '${e.piece}', '${e.numeroDePiece}', '${e.motif}', '${e.bureau.idbureau}', '${!empty e.employe ? e.employe.idemploye :""}', '${arrive}');"

                                    </c:if>
                                    >
                                    <fmt:setLocale value="fr" />
                                    <fmt:formatDate value="${e.heureDentree}" type="date" dateStyle="long" var="date" />
                                    <fmt:formatDate value="${e.heureDentree}" type="time" timeStyle="short" var="arrivee" />
                                    <fmt:formatDate value="${e.heureSortie}" type="time" timeStyle="short" var="depart" />
                                    <td><span style="display: none;">${e.heureDentree}</span>${date}</td>
                                    <td class="align-center">${arrivee}</td>
                                    <td class="align-center">${depart}</td>
                                    <td><a>${e.nomComplet}</a></td>
                                    <td>${e.bureau.libelle}</td>
                                    <td><a href="#/historique/${e.employe.idemploye}">${e.employe.noms} ${e.employe.prenoms}</a></td>
                                    <td>${e.motif}</td>
                                    <td>${e.entreeBadge.numero}</td>
                                    <td class=" align-center"><div class="fg-green ${!empty e.visite ? 'mif-checkmark':''}"></div></td>
                                </tr>
                            </c:if>
                        </c:forEach>

                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
