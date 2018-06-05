<%@page import="java.util.Date"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Statistiques</title>
        <script src="js/jquery-qrcode-0.14.0.js" type="text/javascript"></script>
        <script>
            var titre = "Historique des entrées";
            $("#example_table5").DataTable({
                'searching': true,
                "iDisplayLength": 5,
                "info": true,
                dom: '<"top"fB>rt<"bottom"lp><"clear">',
                "order": [[2, "desc"], [3, 'desc']],
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
        <div class="grid padding20" style="height: 450px; overflow-y: scroll">


            <h1 style=""class="fg-blue">Statistiques des entrées par employé</h1>
            <div class="row cells6" style="padding-right: 40px;">
                <table id="example_table5" class="dataTable border bordered" style="margin-bottom: 10px;">
                    <thead>
                        <tr>
                            <th>Employé</th>
                            <th>Bureau</th>
                            <th>Nombre de visites</th>
                            <th>Durée totale </th>
                        </tr>
                    </thead>

                    <tfoot >
                        <tr>
                            <th>Employé</th>
                            <th>Bureau</th>
                            <th>Nombre de visites</th>
                            <th>Durée totale </th>
                        </tr>
                    </tfoot>
                    <tbody>
                        <c:forEach items="${employes}" var="e">

                            <tr>

                                <td><a href="#historique/${e.idemploye}">${e.noms} ${e.prenoms}</a></td>
                                <td>${e.bureau.libelle}</td>
                                <td>
                                    <c:set var="nbre" value="0"/>
                                    <c:forEach items="${e.entrees}" var="c">
                                        <c:if test="${!empty premierEntree && premierEntree.before(c.heureDentree) && !empty derniereEntree && derniereEntree.after(c.heureDentree)}">
                                            <c:set var="nbre" value="${nbre + 1}"/>
                                        </c:if>
                                    </c:forEach>
                                    ${nbre}
                                </td>
                                <td>
                                    <c:set var="total" value="0"/>
                                    <c:set var="inconnu" value="0"/>
                                    <c:forEach items="${e.entrees}" var="c">
                                        <c:if test="${!empty premierEntree && premierEntree.before(c.heureDentree) && !empty derniereEntree && derniereEntree.after(c.heureDentree)}">
                                            <c:if test="${!empty c.heureSortie}">
                                                <c:set var="diff" value="${((c.heureSortie.getTime()-c.heureDentree.getTime())/1000).intValue()}"/>
                                                <c:set var="total" value="${total + diff}"/>
                                            </c:if>
                                            <c:if test="${empty c.heureSortie}">
                                                <c:set var="inconnu" value="${inconnu+1}"/>
                                            </c:if>
                                        </c:if>
                                    </c:forEach>
                                    <fmt:formatNumber var="heure" value="${total/3600}" maxFractionDigits="0" minIntegerDigits="2" maxIntegerDigits="5"/>
                                    <fmt:formatNumber var="min" value="${(total-heure*3600)/60}" maxFractionDigits="0" minIntegerDigits="2" maxIntegerDigits="2"/>
                                    <c:if test="${min<0}">
                                        <fmt:formatNumber var="heure" value="${heure-1}" maxFractionDigits="0" minIntegerDigits="2" maxIntegerDigits="5"/>
                                        <fmt:formatNumber var="min" value="${(total-heure*3600)/60}" maxFractionDigits="0" minIntegerDigits="2" maxIntegerDigits="2"/>
                                    </c:if>
                                    <fmt:formatNumber var="sec" value="${total-heure*3600-min*60}" maxFractionDigits="0" minIntegerDigits="2" maxIntegerDigits="2"/>
                                    <c:if test="${sec<0}">
                                        <fmt:formatNumber var="min" value="${min-1}" maxFractionDigits="0" minIntegerDigits="2" maxIntegerDigits="2"/>
                                        <fmt:formatNumber var="sec" value="${total-heure*3600-min*60}" maxFractionDigits="0" minIntegerDigits="2" maxIntegerDigits="2"/>
                                    </c:if>
                                    ${heure}:${min}:${sec} 
                                    <c:if test="${inconnu==1}"><span style="cursor: pointer;" class="fg-red place-right" onclick="window.location.href = '#historique/${e.idemploye}'">Une sortie non signalée </span></c:if>
                                    <c:if test="${inconnu>1}"><span style="cursor: pointer;" class="fg-red place-right"  onclick="window.location.href = '#historique/${e.idemploye}'">${inconnu} sorties non signalées </span></c:if>

                                    </td>
                                </tr>
                        </c:forEach>

                    </tbody>
                </table>
            </div>

        </div>
    </body>
</html>
