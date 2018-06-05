<%@page import="java.util.Date"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Annuaire</title>
        <script>
            var titre = "Annuaire";
            $("#example_table").DataTable({
                'searching': true,
                "iDisplayLength": 5,
                "info": true,
                dom: '<"top"fB>rt<"bottom"lp><"clear">',
                "order": [[0, "asc"], [1, 'asc']],
                buttons: [
                    {
                        extend: 'pdfHtml5',
                        text: "Exporter en PDF",
                        title: titre,
                        message: '',
                        className: 'impressionPDF'
                    },
                    {
                        extend: 'print',
                        text: "Imprimer",
                        title: titre,
                        message: '',
                        className: 'impression'
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
        <div class="padding10" style="height: 450px; overflow-y: scroll">
            <h1 class="" style="cursor: pointer; padding-left : 30px; ">Annuaire </h1>  
            <div class="row cells6" style="padding-left: 30px; padding-top: 20px;">
                <table id="example_table" class="dataTable border bordered" style="margin-bottom: 10px;">
                    <thead>
                        <tr>
                            <th>Noms Prenoms</th>

                            <th>Poste</th>
                            <th>Bureau</th>
                            <th>Porte</th>
                            <th>Etage</th>
                            <th>Bâtiment</th>
                            <th>Télephone</th>
                        </tr>
                    </thead>

                    <tfoot >
                        <tr>
                            <th>Noms Prenoms</th>

                            <th>Poste</th>
                            <th>Bureau</th>
                            <th>Porte</th>
                            <th>Etage</th>
                            <th>Bâtiment</th>
                            <th>Télephone</th>
                        </tr>
                    </tfoot>
                    <tbody>
                        <c:forEach items="${employes}" var="e">
                            <c:if test="${true}">
                                <tr class="bg-grayLighter">
                                    <td class="no-wrap">
                                        <b>${e.noms} ${e.prenoms}</b>
                                    </td>

                                    <td>${e.poste.code}</td>
                                    <td>${e.bureau.code}</td>
                                    <td>${e.bureau.numeroPorte}</td>
                                    <td>${e.bureau.etage} </td>
                                    <td>${e.bureau.batiment.code}</td>
                                    <td><b style="float: right">${e.telEntreprise}</b></td>
                                </tr>
                            </c:if>
                        </c:forEach>

                    </tbody>
                </table>
            </div>
        </div>
        <!--div data-role="dialog, draggable" id="employe_dialog" class="grid padding20 bg-grayLighter" data-close-button="true" data-show="${!empty employe}" data-width="800" data-overlay="true" data-overlay-color="op-dark">
            <h1 class="fg-blue">${empty employe ? "Nouvel":employe.noms} ${empty employe ? "employé":employe.prenoms}</h1>
            <small class="">Les champs avec <span class="fg-red">*</span> sont obligatoires</small>
            <form method="post" action="EmployeServlet" style="padding-top: 40px ;">
                <div class="row cells6">
                    <div class="cell colspan3">
                        <label>Noms<span class="fg-red">*</span></label>
                        <div class="input-control text full-size" data-role="input">
                            <input name="noms" type="text" value="${employe.noms}" required="true">
                            <input name="id" type="hidden" value="${employe.idemploye}" >
                        </div>
                    </div>
                    <div class="cell colspan3">
                        <label>Prénoms</label>
                        <div class="input-control text full-size" data-role="input">
                            <input name="prenoms" type="text" value="${employe.prenoms}" required="true">
                        </div>
                    </div>
                </div>
                <div class="row cells3">
                    <div class="cell colspan3">
                        <label>Poste<span class="fg-red">*</span></label>
                        <div class="input-control select full-size" data-role="select" data-placeholder="Entrer le nom du bureau de destination">
                            <select name="poste" class="js-select full-size" required="true">
                                <option></option>
        <c:forEach items="${postes}" var="p">
            <option ${employe.poste.idposte==p.idposte ? "selected":""} value="${p.idposte}">${p.libelle}</option>
        </c:forEach>
    </select>
</div>
</div>
</div>
<div class="row cells3">
<div class="cell colspan3">
<label>Bureau <span class="fg-red">*</span></label>
<div class="input-control select full-size" data-role="select" data-placeholder="Entrer le nom du bureau de destination">
    <select name="bureau" class="js-select full-size" required="true">
        <option></option>
        <c:forEach items="${bureaux}" var="b">
            <option ${employe.bureau.idbureau==b.idbureau ? "selected":""} value="${b.idbureau}">
            ${b.code} ${b.libelle}, Bâtiment : ${b.batiment.code}, Porte : ${b.numeroPorte}, Etage : ${b.etage}
        </option>
        </c:forEach>
    </select>
</div>
</div>
</div>
<div class="row cells12">
<div class="cell colspan6">
<label>Télephone<span class="fg-red">*</span></label>
<div class="input-control text full-size" data-role="input">
    <input name="tel" type="text" value="${employe.telEntreprise}" required="true">
</div>
</div>
<div class="cell colspan6">
<label>Autre contact</label>
<div class="input-control text full-size" data-role="input">
    <input name="autre" type="text" value="${employe.autreContact}">
</div>
</div>
</div>
<div class="row cells6">
<div class="cell colspan6">
<br>
<input type="submit" value="Enregistrement" class="button ribbed-blue fg-white">
</div>
</div>
</form>
</div-->
    </body>
</html>
