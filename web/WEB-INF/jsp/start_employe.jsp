<%@page import="java.util.Date"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head lang="en">
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <meta name="description" content="Metro, a sleek, intuitive, and powerful framework for faster and easier web development for Windows Metro Style.">
        <meta name="keywords" content="HTML, CSS, JS, JavaScript, framework, metro, front-end, frontend, web development">
        <meta name="author" content="Sergey Pimenov and Metro UI CSS contributors">

        <link rel='shortcut icon' type='image/x-icon' href='../favicon.ico' />
        <title>Accueil SOFT</title>

        <link href="css/metro.css" rel="stylesheet">
        <link href="css/select2.css" rel="stylesheet" type="text/css"/>
        <link href="css/metro-icons.css" rel="stylesheet">
        <link href="css/jquery.dataTables.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/buttons.dataTables.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/metro-colors.css" rel="stylesheet" type="text/css"/>
        <link href="css/jquery-confirm.css" rel="stylesheet" type="text/css"/>
        <link href="css/start.css" rel="stylesheet" type="text/css"/>

        <script src="js/jquery-2.1.3.min.js"></script>
        <script src="js/metro.js"></script>
        <script src="js/select2.js" type="text/javascript"></script>
        <script src="js/jquery.dataTables.min.js" type="text/javascript"></script>
        <script src="js/buttons.flash.min.js" type="text/javascript"></script>
        <script src="js/buttons.html5.min.js" type="text/javascript"></script>
        <script src="js/dataTables.buttons.min.js" type="text/javascript"></script>
        <script src="js/processing-api.min.js.js" type="text/javascript"></script>
        <script src="js/buttons.print.min.js" type="text/javascript"></script>
        <script src="js/pdf.js.js" type="text/javascript"></script>
        <script src="js/pdfmake.min.js" type="text/javascript"></script>
        <script src="js/vfs_fonts.js" type="text/javascript"></script>
        <script src="js/Chart.min.js" type="text/javascript"></script>
        <script src="js/Chart.bundle.min.js" type="text/javascript"></script>
        <script src="js/easytimer.min.js" type="text/javascript"></script>
        <script src="js/jquery-confirm.js"></script>
        <script src="js/start.js" type="text/javascript"></script>

        <script>
            function urgence() {
                var filterVal = 'blur(5px)';
                var filterVal2 = 'grayscale(100%)';
                $('#area').css('filter', filterVal);
            }
            function messageLu(id) {
                $.post(
                        "FormNouveauMessageServlet",
                        {
                            action: "message_lu",
                            id: id
                        }
                );
            }
            function colisRecu(id) {
                $.confirm({
                    title: "Colis",
                    boxWidth: '20%',
                    useBootstrap: false,
                    typeAnimated: true,
                    content: "Marquer le colis comme recu ?\n",
                    buttons: {
                        Oui: {
                            text: 'Oui',
                            btnClass: 'button bg-green fg-white',
                            action: function () {
                                $.post(
                                        "FormNouveauColisServlet",
                                        {
                                            action: "colis_recu",
                                            id: id
                                        }

                                );
                                window.location.href = window.location.href;
                            }
                        },
                        Annuler: {
                            text: 'Annuler',
                            btnClass: 'button bg-gray fg-white',
                            action: function () {
                                
                            }
                        }
                    }
                });

            }

            function confirmationEntreeBureau(id, nom) {
                $.confirm({
                    title: "<h2 class='fg-blue'>" + nom + "</h2>",
                    boxWidth: '40%',
                    type: 'blue',
                    useBootstrap: false,
                    typeAnimated: true,
                    content: "<h4 class='fg-gray'>Confirmer la présence du visiteur dans votre bureau<br><br></h4> ",
                    buttons: {
                        Oui: {
                            text: 'Je confirme',
                            btnClass: 'button bg-blue fg-white',
                            action: function () {
                                $.post(
                                        "EntreeServlet",
                                        {
                                            action: "confirmation_entree",
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
            function confirmationSortieBureau(id, nom) {
                $.confirm({
                    title: "<h2 class='fg-orange'>" + nom + "</h2>",
                    boxWidth: '40%',
                    type: 'red',
                    useBootstrap: false,
                    typeAnimated: true,
                    content: "<h4 class='fg-gray'>Confirmer la sortie du visiteur de votre bureau<br><br></h4> ",
                    buttons: {
                        Oui: {
                            text: 'Je confirme',
                            btnClass: 'button bg-orange fg-white',
                            action: function () {
                                $.post(
                                        "EntreeServlet",
                                        {
                                            action: "confirmation_sortie",
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
            var indexNouveauxMessages = 0;
            function nouveauxMessages() {

                var newDate = new Date().getTime() + "";
                $("#messages_non_lus").load("MessageAjaxServlet?date=" + newDate, function (response, status, xhr) {
                    console.log("Recherche de nouveaux messages");
                    var slides = $("#messages_non_lus").find('.list');
                    console.log(slides.length);
                    if (slides.length > 0 && slides.length > indexNouveauxMessages) {
                        $.Notify({
                            icon: "<span class='mif-bell'></span>",
                            content: "<h3 style='font-weight:200; padding-left:15px;'>Vous avez reçu un nouveau message !</h3>",
                            type: 'success',
                            keepOpen: true
                        });
                        indexNouveauxMessages = slides.length ;
                    }
                    $("#messages_non_lusNombre").html("" + slides.length);

                });
            }

            function personnesEnrouteVersMonBureau() {
                var options = {
                    controls: false,
                    markers: false
                };
                if ($("#enrouteversmonbureau").data("carousel").options._interval) {
                    clearInterval($("#enrouteversmonbureau").data("carousel").options._interval);

                }
                $("#enrouteversmonbureau").removeData("carousel");
                $("#enrouteversmonbureau").removeData("metro-carousel");


                var newDate = new Date().getTime() + "";
                $("#enrouteversmonbureau").load("EntreeAjaxServlet?date=" + newDate, function (response, status, xhr) {
                    $("#enrouteversmonbureau").carousel(options);
                    console.log("Muanza");
                    console.log($("#enrouteversmonbureau").data("carousel"));
                    console.log($("#enrouteversmonbureau").data("metro-carousel"));
                    var slides = $("#enrouteversmonbureau").find('.slide');
                    console.log(slides.length);
                    $("#enrouteversmonbureauNombre").html("" + slides.length);

                });
            }

            $(function () {

                var options = {
                    controls: false,
                    markers: false
                }
                $("#enrouteversmonbureau").carousel(options);
                console.log($("#enrouteversmonbureau").data());
                var muanza = setInterval(personnesEnrouteVersMonBureau, 120000);
                var muanzaMessages = setInterval(nouveauxMessages, 30000);
                console.log(muanza);
                console.log(muanzaMessages);




            <c:if test="${!empty notification}">
                $.Notify({
                    icon: "<span class='mif-bell'></span>",
                    content: "<h3 style='font-weight:200; padding-left:15px;'>${notification}</h3>",
                    type: 'success',
                    timeout: 5000
                });
            </c:if>
            <c:if test="${!empty error}">
                $.Notify({
                    caption: 'Erreur',
                    content: "${error}",
                    type: 'alert',
                    keepOpen: true
                });
            </c:if>


            });


        </script>

        <script>

        </script>
    </head>
    <body style="background-size: 100% auto;overflow-y: hidden; background-image: url('img/meeting.jpg'); background-attachment: fixed;">
        <div id="right-charm" class="bg-darkIndigo" style="" data-role="charm" data-position="right" >
            <div class="grid padding20" >
                <div class="fg-dark" style="font-size: 3.0em ; font-weight: 500">
                    <span class="fg-white">Messages</span>
                </div>
                <p style="font-size : 1.2em" class="fg-grayLighter">
                    Réception
                </p>
                <div  class="" style="padding-left: 0px !important; padding-right: 20px; width: 380px; height: 65vh; overflow-y: auto">
                    <div id="messages_non_lus">

                    </div>
                    <c:forEach items="${messages}" var="m">
                        <div class="list" onclick="messageLu('${m.idmessage}');
                                metroDialog.open('#dialog-form-nouveau-message');
                                toggleMetroCharm('#right-charm')" style="border-bottom: 1px solid #fff; padding-bottom: 10px; padding-top: 10px">
                            <div class="list-content fg-white">
                                <b class="list-title" style="font-size:1.2em;">
                                    ${m.employeByIdemetteur.noms} ${m.employeByIdemetteur.prenoms}
                                </b>
                                <div class="list-subtitle fg-white" style="font-size:1.2em;">
                                    <q> ${m.contenu} </q>
                                </div>
                                <span class="list-remark">
                                    <fmt:setLocale value="fr" />
                                    <fmt:formatDate value="${m.dateEnvoi}" type="both" var="dateEnvoi" />
                                    ${dateEnvoi}
                                </span>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div id="colis-charm" class="bg-darkCrimson" style="" data-role="charm" data-position="right" >
            <div class="grid padding20" >
                <div  style="font-size: 3.0em ; font-weight: 500">
                    <span class="fg-white">Colis</span>
                </div>
                <p class="fg-white">Colis déposés à l'accueil</p>
                <div class="" style="padding-left: 0px !important; padding-right: 20px; width: 380px; height: 65vh; overflow-y: auto">
                    <c:forEach items="${coliss}" var="c">
                        <div class="list" onclick="colisRecu('${c.idcolis}');
                                toggleMetroCharm('#colis-charm')" style="border-bottom: 1px solid #fff; padding-bottom: 10px; padding-top: 10px">
                            <div class="list-content fg-white">
                                <b class="list-title" style="font-size:1.2em;">
                                    ${c.employe.noms} ${c.employe.prenoms}, ${c.bureau.libelle}
                                </b>
                                <div class="list-subtitle fg-white" style="font-size:1.2em;">
                                    ${empty c.description ? "Aucune description": c.description} 
                                </div>
                                <span class="list-remark">
                                    Déposé par ${c.noms} ${c.prenoms}
                                </span>
                                <div>
                                    <fmt:setLocale value="fr" />
                                    <fmt:formatDate value="${c.dateDepot}" type="both" var="dateDepot" />
                                    ${dateDepot}
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

        <div id="area"
             class="tile-area tile-area-scheme-dark fg-white" 
             style="opacity: 0.91; height: 100%; max-height: 100% !important; background-color: #000005; background-attachment: fixed;">

            <div class="tile-area-title" style="font-weight: 400; padding-top: 10px">
                Accueil <span class="fg-blue">SOFT</span>
            </div>
            <div class="tile-area-controls">
                <button onclick="metroDialog.open('#dialog-form-utilisateur${sessionScope.utilisateur.idutilisateur}')" class="image-button icon-right bg-blue fg-white bg-hover-dark no-border">
                    <span class="sub-header no-margin text-light">
                        ${sessionScope.utilisateur.employe.noms}
                    </span> 
                    <span class="icon mif-user"></span>
                </button>
                <a href="DeconnexionServlet" class="square-button bg-red fg-white bg-hover-dark no-border"><span class="mif-switch"></span></a>
            </div>

            <c:if test="${true}">
                <div class="tile-group one">
                    <span class="tile-group-title" style="font-weight: 300">Actions</span>
                    <div class="tile-small bg-darkIndigo" data-role="tile" onclick="metroDialog.open('#dialog-form-nouveau-message')">
                        <div class="tile-content iconic">
                            <span class="icon mif-mail"></span>
                        </div>
                    </div>
                    <div class="tile-small bg-red" data-role="tile" onclick="metroDialog.open('#dialog-form-urgence');
                            urgence()">
                        <div class="tile-content iconic">
                            <span class="icon mif-bell"></span>
                        </div>
                    </div>
                    <div class="tile-small bg-darkCrimson" data-role="tile" onclick="metroDialog.open('#dialog-annuaire')">
                        <div class="tile-content iconic">
                            <span class="icon mif-phone-in-talk "></span>
                        </div>
                    </div>
                    <div class="tile-small bg-darkGreen" data-role="tile" onclick="metroDialog.open('#dialog-form-nouvelle-visite')">
                        <div class="tile-content iconic">
                            <span class="icon mif-calendar "></span>
                        </div>
                    </div>
                </div>
            </c:if>

            <c:if test="${ upfDAO.hasAccess((sessionScope.utilisateur), '002') }">
                <div class="tile-group double">
                    <span class="tile-group-title" style="font-weight: 300">Entrées</span>
                    <div class="tile-container">
                        <div class="tile tile-wide bg-blue fg-white" data-role="tile">
                            <div class="tile-content" >
                                <div id="enrouteversmonbureau" class="carousel" >
                                    <c:set var="nbr_entree" value="${0}" />
                                    <c:forEach items="${mesEntrees}" var="me">
                                        <c:if test="${empty me.heureEntreeBureau && empty me.heureSortieBureau}">
                                            <c:set var="nbr_entree" value="${nbr_entree+1}" />
                                            <div class="slide grid" onclick="metroDialog.open('#dialog-entree${me.identree}')">
                                                <div class="row cells3">
                                                    <div class="cell padding10">
                                                        <span style="font-size: 5em;" class="icon mif-user fg-white"></span>
                                                    </div>
                                                    <div class="cell colspan2 padding10">
                                                        <h4>${me.nomComplet}</h4>
                                                        <h6>${me.entreprise}</h6>
                                                        <span>${me.motif}</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${nbr_entree==0}">
                                        <div class="slide padding20 fg-white half-opacity">
                                            <h3>Aucun visiteur en route vers votre bureau</h3>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                            <span class="tile-label">En route vers votre bureau</span>
                            <span id="enrouteversmonbureauNombre" class="tile-badge bg-orange">${nbr_entree}</span>
                        </div>
                        <div class="tile tile-wide bg-orange fg-white" data-role="tile">
                            <div class="tile-content">
                                <div class="carousel" data-role="carousel" data-controls="false" data-markers="false">
                                    <c:set var="t" value="${1}" />
                                    <c:set var="nbr" value="${0}" />
                                    <c:forEach items="${mesEntrees}" var="me">
                                        <c:if test="${!empty me.heureEntreeBureau  && empty me.heureSortieBureau}">
                                            <c:set var="t" value="${t*0}" />
                                            <c:set var="nbr" value="${nbr+1}" />
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${t==1}">
                                        <div class="slide padding20 fg-white half-opacity">
                                            <h3>Aucun visiteur dans votre bureau</h3>
                                        </div>
                                    </c:if>

                                    <c:forEach items="${mesEntrees}" var="me">
                                        <c:if test="${!empty me.heureEntreeBureau  && empty me.heureSortieBureau}">
                                            <div class="slide grid"  onclick="metroDialog.open('#dialog-sortie${me.identree}')">
                                                <div class="row cells3">
                                                    <div class="cell padding10">
                                                        <span style="font-size: 5em;" class="icon mif-user fg-white"></span>
                                                    </div>
                                                    <div class="cell colspan2 padding10">
                                                        <h4>${me.nomComplet}</h4>
                                                        <span>${me.motif}</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:if>

                                    </c:forEach>
                                </div>
                            </div>
                            <span class="tile-label">Actuellement dans votre bureau</span>
                            <span class="tile-badge bg-darkGreen">${nbr}</span>
                        </div>
                    </div>
                </div>
            </c:if>

            <c:if test="${ upfDAO.hasAccess((sessionScope.utilisateur), '002') }">
                <div class="tile-group triple">
                    <span class="tile-group-title" style="font-weight: 300">Espace personnel</span>
                    <div class="tile-container">
                        <div class="tile tile-wide bg-darkGreen fg-white" data-role="tile">
                            <div class="tile-content">
                                <div class="carousel" data-role="carousel" data-controls="false" data-markers="false">
                                    <c:if test="${mesVisites.size()==0}">
                                        <div class="slide padding20 fg-white half-opacity">
                                            <h3>Aucune personne attendue aujourd'hui</h3>
                                        </div>
                                    </c:if>
                                    <c:forEach items="${mesVisites}" var="mv">
                                        <div class="slide grid" onclick="metroDialog.open('#dialog-visite${mv.idvisite}')">
                                            <div class="row cells4">
                                                <div class="cell padding10">
                                                    <span style="font-size: 5em;" class="icon mif-user fg-white"></span>
                                                </div>
                                                <div class="cell colspan2 padding10">
                                                    <h4>${mv.nomPersonneAttendue}</h4>
                                                    <span>${mv.motif}</span>

                                                </div>
                                                <div class="cell">
                                                    <h3 class="align-center">
                                                        <span class="icon mif-alarm fg-white"></span>
                                                        <br>
                                                        <fmt:formatDate value="${mv.heureAttendue}" type="time" timeStyle="short" var="arrive" />
                                                        <b>${arrive}</b>
                                                    </h3>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                            <span class="tile-label">Personnes attendues aujourd'hui</span>
                            <span class="tile-badge bg-orange">${mesVisites.size()}</span>
                        </div>
                        <div class="tile bg-orange fg-white" data-role="tile" onclick="metroDialog.open('#dialog-profil')">
                            <div class="tile-content bg-dark">
                                <img src="${empty profil ? 'img/user.ico': profil}" data-role="fitImage" data-format="square">
                            </div>
                            <span class="tile-label fg-dark">Profil</span>
                        </div>
                        <div class="tile bg-gray fg-white" data-role="tile" onclick="metroDialog.open('#dialog-historique')">
                            <div class="tile-content iconic">
                                <span class="icon mif-history"></span>
                            </div>
                            <div class="tile-label">Historique</div>
                        </div>
                        <div class="tile bg-darkCrimson fg-white" data-role="tile" onclick="toggleMetroCharm('#colis-charm')">
                            <div class="tile-content iconic">
                                <span class="icon mif-gift"></span>
                            </div>
                            <span class="tile-label">Colis</span>
                            <span class="tile-badge bg-orange">${coliss_nonrecu.size()}</span>
                        </div>
                        <div class="tile bg-darkIndigo" data-role="tile" onclick="toggleMetroCharm('#right-charm')">
                            <div class="tile-content iconic">
                                <span class="icon mif-mail"></span>
                            </div>
                            <span class="tile-label">Messages</span>
                            <span id="messages_non_lusNombre" class="tile-badge bg-orange">${messages_nonlus.size()}</span>
                        </div>
                    </div>
                </div>
            </c:if>

        </div>

        <div class="fg-white padding30" style="position: fixed;bottom: 0;width: 100%; ">
            <p style="float: right;">&copy; Powered by SIA TECHNOLOGY GROUP 2018</p>
        </div>

        <div data-role="dialog" id="dialog-form-entree" class="padding30 bd-blue"
             data-close-button="true"
             data-windows-style="false"
             data-href="FormEntreeServlet?temps=${temps}"
             data-overlay="true"
             data-width="600"
             data-background="bg-blue"
             data-color="fg-white"
             style="border : 2px solid #60a917">
        </div>
        <div data-role="dialog" id="dialog-form-nouvelle-visite" class="padding20 bg-darkGreen"
             data-close-button="true"
             data-href="FormNouvelleVisiteServlet?temps=${temps}"
             data-overlay="true"
             data-color="fg-white"
             data-width="600">
        </div>
        <c:forEach items="${mesVisites}" var="mvx">
            <div data-role="dialog" id="dialog-visite${mvx.idvisite}" class="padding20 bg-darkGreen"
                 data-close-button="true"
                 data-href="VisiteServlet?id=${mvx.idvisite}&temps=${temps}"
                 data-overlay="true"
                 data-color="fg-white"
                 data-width="600">
            </div>
        </c:forEach>
        <c:forEach items="${mesEntrees}" var="mes">
            <div data-role="dialog" id="dialog-entree${mes.identree}" class="padding20 bg-blue"
                 data-close-button="true"
                 data-href="ConfirmationPresenceServlet?id=${mes.identree}&temps=${temps}"
                 data-overlay="true"
                 data-color="fg-white"
                 data-width="600">
            </div>
        </c:forEach>
        <c:forEach items="${mesEntrees}" var="mes">
            <div data-role="dialog" id="dialog-sortie${mes.identree}" class="padding20 bg-orange"
                 data-close-button="true"
                 data-href="ConfirmationSortieServlet?id=${mes.identree}&temps=${temps}"
                 data-overlay="true"
                 data-color="fg-white"
                 data-width="600">
            </div>
        </c:forEach>

        <div data-role="dialog" id="dialog-form-utilisateur${sessionScope.utilisateur.idutilisateur}" class="padding20 bg-blue"
             data-close-button="true"
             data-href="FormUtilisateurServlet?id=${sessionScope.utilisateur.idutilisateur}&temps=${temps}"
             data-overlay="true"
             data-color="fg-white"
             data-width="600">
        </div>

        <div data-role="dialog" id="dialog-form-nouveau-message" class="padding20 bg-darkIndigo"
             data-close-button="true"
             data-href="FormNouveauMessageServlet?temps=${temps}"
             data-overlay="true"
             data-color="fg-white"
             data-width="600">
        </div>
        <div data-role="dialog" id="dialog-form-urgence" class="padding20 bg-red"
             data-close-button="false"
             data-href="FormUrgenceServlet?temps=${temps}"
             data-overlay="true"
             data-color="fg-white"
             data-width="400">
        </div>
        <div data-role="dialog" id="dialog-annuaire" class=""
             data-close-button="true"
             data-windows-style="true"
             data-href="AnnuaireServlet?temps=${temps}"
             data-overlay="true"
             data-width="1000"
             data-height="500">
        </div>
        <div data-role="dialog" id="dialog-historique" class="bg-grayLighter"
             data-close-button="true"
             data-windows-style="true"
             data-href="HistoriqueServlet?id=${sessionScope.utilisateur.employe.idemploye}&temps=${temps}"
             data-overlay="true"
             data-width="1000"
             data-height="500">
        </div>
        <div data-role="dialog" id="dialog-profil" class="bg-dark padding20"
             data-close-button="true"
             data-windows-style="false"
             data-href="EmployeServlet?id=${sessionScope.utilisateur.employe.idemploye}&temps=${temps}"
             data-overlay="true"
             data-color="fg-white"
             data-width="600">
        </div>

    </body>
</html>