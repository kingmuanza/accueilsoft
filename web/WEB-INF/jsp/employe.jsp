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
            $("#blah").click(function () {
                $("#imgInp").click();
            });

            function readURL(input) {

                if (input.files && input.files[0]) {
                    var reader = new FileReader();

                    reader.onload = function (e) {
                        $('#blah').attr('src', e.target.result);
                        console.log(e.target.result);
                        $("input[name='imgblob']").val(e.target.result)
                    }

                    reader.readAsDataURL(input.files[0]);
                }
            }

            $("#imgInp").change(function () {
                readURL(this);
            });
        </script>
    </head>
    <body >
        <div class="grid">
            <div class="row cells4">
                <div class="cell padding10">
                    <img  id="blah" src="${empty profil ? 'img/user.ico': profil}" class="bd-white" style="border: 2px solid">
                </div>
                <div class="cell colspan3">
                    <h1 class="">${empty employe ? "Nouvel":employe.noms} ${empty employe ? "employé":employe.prenoms}</h1>
                    <small class="">Les champs avec <span class="fg-red">*</span> sont obligatoires</small>
                </div>
            </div>
        </div>


        <form id="form1" runat="server" class="grid" method="post" action="EmployeServlet" style="padding-top: 40px ;">
            <input type='file' name="imageenvoyee" id="imgInp" style="display: none"/>
            <div class="row cells6">
                <div class="cell colspan3">
                    <label>Noms<span class="fg-red">*</span></label>
                    <div class="input-control text full-size" data-role="input">
                        <input name="noms" type="text" value="${employe.noms}" required="true">
                        <input id="imgblob" type="hidden" name="imgblob" value="">
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
                    <input type="submit" value="Enregistrement" class="button fg-white" style="background : none ! important">
                    <c:if test="${sessionScope.utilisateur.utilisateurProfil.code=='ADMIN'}">
                        <button style="" type="submit" name="action" value="supprimer" class="button bg-dark fg-white bd-white"> 
                            Supprimer
                        </button>
                    </c:if>
                </div>
            </div>
        </form>
    </body>
</html>
