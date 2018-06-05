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
            function messageLu(id, idemploye) {
                $.post(
                        "FormNouveauMessageServlet",
                        {
                            action: "message_lu",
                            id: id,
                            idemploye: idemploye
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
                        indexNouveauxMessages = slides.length;
                    }
                    $("#messages_non_lusNombre").html("" + slides.length);

                });
            }

            $(function () {
                var muanzaMessages = setInterval(nouveauxMessages, 30000);
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
                var filterVal2 = 'grayscale(100%)';
                var filterVal = 'blur(0px)';
                function sortie(id, nom, bureau) {

                    $.confirm({
                        title: "<h3 class='fg-red'>Urgence</h3>",
                        icon: 'mif mif-spinner mif-ani-spin fg-red',
                        boxWidth: '40%',
                        type: 'red',
                        useBootstrap: false,
                        typeAnimated: true,
                        content: "<div style='font-size:1.2em'>\n\
            Une urgence a été signalée par un employé. Veuillez vous rendre dans son bureau<br><br><b class='fg-red'>Nom : </b> " + nom + "<br><b class='fg-red'>Bureau : </b> " + bureau + "<br><br></div>",
                        buttons: {
                            Oui: {
                                text: 'OK Bien reçu',
                                btnClass: 'button bg-red fg-white',
                                action: function () {
                                    $.post(
                                            "FormUrgenceServlet",
                                            {
                                                action: "sortie",
                                                id: id
                                            }
                                    );
                                    $('#area').css('filter', filterVal);
                                }
                            },
                            Annuler: {
                                text: 'Annuler',
                                btnClass: 'button ribbed-gray fg-white',
                                action: function () {
                                    $('#area').css('filter', filterVal);
                                }
                            }
                        }
                    });

                }
            <c:if test="${ upfDAO.hasAccess((sessionScope.utilisateur), '003') }">
                <c:forEach items="${urgences}" var="urgence">
                    <c:if test="${!urgence.resolue}">
                $('#area').css('filter', filterVal2);
                sortie('${urgence.idurgence}', '${urgence.employe.noms} ${urgence.employe.prenoms}', '${urgence.employe.bureau.libelle} ${urgence.employe.bureau.code}');

                    </c:if>
                </c:forEach>
            </c:if>

                        var nombre = function () {
                            return Math.round(Math.random() * 205) + 50;
                        };
                        var liste_bureaux = [];
                        var data = [];
                        var data2 = [];
                        var backgroundColor = [];
                        var backgroundColor2 = [];

                        var borderColor = [
                            'rgba(255,99,132,1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)'
                        ];

            <c:forEach items="${bureaux}" var="b">
                        liste_bureaux.push("${b.code}");
                        var i = 0;
                        var j = 0;
                <c:forEach items="${b.entrees}" var="e">
                    <c:if test="${!empty premierEntree && premierEntree.before(e.heureDentree) && !empty derniereEntree && derniereEntree.after(e.heureDentree)}">
                        i++;
                        <c:if test="${empty e.heureSortie}">
                        j++;
                        </c:if>
                    </c:if>
                </c:forEach>
                        data.push(i);
                        data2.push(j);
                        backgroundColor.push('rgba(96, 169, 23, 1)')
                        backgroundColor2.push('rgba(250, 104, 0, 1)')
            </c:forEach>



                        console.log(liste_bureaux);
                        console.log(data);
                        console.log(backgroundColor);
                        var ctx = document.getElementById("myChart").getContext('2d');

                        var myChart = new Chart(ctx, {
                            type: 'bar',
                            data: {
                                labels: liste_bureaux,
                                datasets: [{
                                        label: 'Nombre de personnes entrées',
                                        data: data,
                                        backgroundColor: backgroundColor,
                                        borderColor: backgroundColor,
                                        borderWidth: 1
                                    },
                                    {
                                        label: 'Nombre de personnes dans les locaux',
                                        data: data2,
                                        backgroundColor: backgroundColor2,
                                        borderColor: backgroundColor2,
                                        // Changes this dataset to become a line
                                        type: 'bar'
                                    }]
                            },
                            options: {
                                scales: {
                                    yAxes: [{
                                            ticks: {
                                                beginAtZero: true,
                                                min: 0,
                                                stepSize: 1,
                                                maxTicksLimit: 50
                                            }
                                        }]
                                },
                                elements: {
                                    rectangle: {
                                        borderWidth: 2,
                                        borderColor: 'rgb(0, 255, 0)',
                                        borderSkipped: 'bottom'
                                    }
                                },
                                responsive: true,
                                legend: {
                                    position: 'top',
                                }
                            }
                        });

                    });


        </script>

        <script>
            <c:forEach items="${entrees}" var="c">
            var timer${c.identree} = new Timer();
                <c:set var="today" value="<%=new Date()%>"/>
                <c:set var="diff" value="${((today.getTime()-c.heureDentree.getTime())/1000).intValue()}"/>

            //${((today.getTime())/1000).intValue()}
            //${((c.heureDentree.getTime())/1000).intValue()}
            //${((today.getTime()-c.heureDentree.getTime())/1000).intValue()}
            timer${c.identree}.start({precision: 'seconds', startValues: {seconds: ${diff}}});
            timer${c.identree}.addEventListener('secondsUpdated', function (e) {
                $('#${c.identree}').html(timer${c.identree}.getTimeValues().toString());
            });
            </c:forEach>
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
                <div class="" style="padding-left: 0px !important; padding-right: 20px; width: 380px; height: 65vh; overflow-y: auto">
                    <div id="messages_non_lus">

                    </div>
                    <c:forEach items="${messages}" var="m">
                        <div class="list" onclick="messageLu('${m.idmessage}', '${m.employeByIdemetteur.idemploye}');
                                metroDialog.open('#dialog-form-nouveau-message');
                                toggleMetroCharm('#right-charm')" style="cursor : pointer; border-bottom: 1px solid #fff; padding-bottom: 10px; padding-top: 10px">
                            <div class="list-content fg-white">
                                <b class="list-title" style="font-size:1.2em;">
                                    ${m.employeByIdemetteur.noms} ${m.employeByIdemetteur.prenoms}
                                </b>
                                <div class="list-subtitle fg-white" style="font-size:1.2em;">
                                    <q>${m.contenu} </q>
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
        <div id="colis-charm" class="bg-green" style="" data-role="charm" data-position="right" >
            <div class="grid padding20" >
                <div  style="font-size: 3.0em ; font-weight: 500">
                    <span class="fg-white">Colis</span>
                </div>
                <p class="fg-white">Colis déposés à l'accueil</p>
                <div class="" style="padding-left: 0px !important; padding-right: 20px; width: 380px; height: 65vh; overflow-y: auto">
                    <c:forEach items="${coliss}" var="c">
                        <div class="list" onclick="colisRecu('${c.idcolis}');
                                toggleMetroCharm('#colis-charm')" style="cursor: pointer; border-bottom: 1px solid #fff; padding-bottom: 10px; padding-top: 10px">
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
        <div id="left-charm" class="bg-orange" style="" data-role="charm" data-position="left" >
            <div class="grid">
                <div class="row" style="padding-left : 20px; padding-right: 20px; width: 380px">
                    <div class="fg-dark" style="font-size: 3.0em ; font-weight: 500">
                        <span class="fg-white">BEAC</span>
                        <!--span class="fg-white" style="float: right">${fn:length(entrees)}</span-->
                    </div>
                    <span class="fg-white" style="font-weight: 300; font-size: 1.2em" onclick="window.location.href = 'start'">
                        Actuellement dans nos locaux
                    </span>

                    <div style="padding-top: 10px;  padding-bottom: 10px; width: 200px">
                        <span class="fg-blue"></span>
                    </div>

                    <div class="listview-outlook invisible-scrollbar" data-role="listview" style="height: 65vh; overflow-y: auto">
                        <div class="list-group">
                            <div class="list-group-content" id="entrees_actuellement">
                                <c:forEach items="${entrees}" var="c">
                                    <fmt:formatDate value="${c.heureDentree}" type="time" timeStyle="short" var="arrive" />
                                    <div id="list_${c.identree}" style="cursor: pointer;" class="list"  onclick="metroDialog.open('#dialog-form-sortie${c.identree}');
                                            toggleMetroCharm('#left-charm')">
                                        <div class="list-content" style="padding-left: 0px !important">
                                            <span class="list-title fg-white">${c.nomComplet} <b><span class="place-right" id="${c.identree}">00:00</span></b></span>
                                            <span class="list-subtitle fg-lighterGray">
                                                ${c.bureau.libelle}<br> ${!empty c.employe ? c.employe.noms :""} ${!empty c.employe ? c.employe.prenoms :""}
                                            </span>
                                            <span class="list-remark fg-white">${c.piece} ${c.numeroDePiece}<b><span class="place-right">Depuis ${arrive}</span></b></span>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <div class="padding20 fg-grayLighter half-opacity" style="position : absolute; bottom: 0">
                <small>Powered by SIA TECHNOLOGY GROUP</small>
            </div>
        </div>
        <div id="left-charm-visite" class="bg-darkBlue" style="" data-role="charm" data-position="left" >
            <div class="grid">
                <div class="row" style="padding-left : 20px; padding-right: 20px; width: 380px">
                    <div class="fg-dark" style="font-size: 3.0em ; font-weight: 500">
                        <span class="fg-white">BEAC</span>
                        <!--span class="fg-white" style="float: right">${fn:length(entrees)}</span-->
                    </div>
                    <span class="fg-white" style="font-weight: 300; font-size: 1.2em" onclick="window.location.href = 'start'">
                        Attendues dans nos locaux
                    </span>
                    <div style="padding-top: 10px;  padding-bottom: 10px; width: 200px">
                        <span class="fg-blue"></span>
                    </div>

                    <div class="listview-outlook invisible-scrollbar" data-role="listview" style="height: 65vh; overflow-y: auto">
                        <div class="list-group">
                            <div class="list-group-content" id="visites_prevues">
                                <c:set var="now" value="<%=new java.util.Date()%>" />
                                <c:forEach items="${visites}" var="v">
                                    <fmt:formatDate value="${v.heureAttendue}" type="time" timeStyle="short" var="arrive" />

                                    <div id="listv_${v.idvisite}" style="cursor: pointer;" class="list" onclick="metroDialog.open('#dialog-visite-entree${v.idvisite}');
                                            toggleMetroCharm('#left-charm-visite')">
                                        <div class="list-content" style="padding-left: 0px !important">
                                            <span class="list-title fg-white">
                                                ${v.nomPersonneAttendue}
                                                <c:if test="${!now.before(v.heureAttendue)}">
                                                    <span class="mif-bell mif-ani-ring mif-ani-slow place-right fg-white"></span>
                                                </c:if> 
                                            </span>
                                            <span class="list-subtitle fg-grayLighter">
                                                ${!empty v.employe ? v.employe.bureau.libelle :'Indéfini'} 
                                                ${!empty v.employe ? " - " :''} 
                                                ${!empty v.employe ? v.employe.noms :''} 
                                                ${!empty v.employe ? v.employe.prenoms :''} 
                                            </span>
                                            <span class="list-remark ${!now.before(v.heureAttendue) ? 'fg-white':'fg-white'} ">
                                                Attendue à ${!empty v.heureAttendue ? arrive :"tout moment"}
                                            </span>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div> 
                        </div>
                    </div>

                </div>
            </div>
            <div class="padding20 fg-grayLighter half-opacity" style="position : absolute; bottom: 0">
                <small>Powered by SIA TECHNOLOGY GROUP</small>
            </div>
        </div>

        <div id="area"
             class="tile-area tile-area-scheme-dark fg-white" 
             style="opacity: 0.91; height: 100%; max-height: 100% !important; background-color: #000005; background-attachment: fixed;">

            <div class="tile-area-title" style="font-weight: 400; padding-top: 10px">
                Accueil <span class="fg-blue">SOFT</span>
                <!--                <span>BEAC</span>-->
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
                    <c:if test="${ upfDAO.hasAccess((sessionScope.utilisateur), '003') }">
                        <div class="tile-small bg-darkBlue" data-role="tile" onclick="toggleMetroCharm('#left-charm-visite')">
                            <div class="tile-content iconic">
                                <span class="icon mif-user-check "></span>
                            </div>
                        </div>

                        <div class="tile-small bg-blue" data-role="tile" onclick="metroDialog.open('#dialog-form-entree')">
                            <div class="tile-content iconic">
                                <span class="icon mif-user-plus"></span>
                            </div>
                        </div>
                        <div class="tile-small bg-orange" data-role="tile" onclick="toggleMetroCharm('#left-charm')">
                            <div class="tile-content iconic">
                                <span class="icon mif-user-minus"></span>
                            </div>
                        </div>
                        <div class="tile-small bg-green" data-role="tile" onclick="metroDialog.open('#dialog-form-nouveau-colis')">
                            <div class="tile-content iconic">
                                <span class="icon mif-gift"></span>
                            </div>
                        </div>

                    </c:if>
                    <div class="tile-small bg-darkIndigo" data-role="tile" onclick="metroDialog.open('#dialog-form-nouveau-message')">
                        <div class="tile-content iconic">
                            <span class="icon mif-mail"></span>
                        </div>
                    </div>
                    <div class="tile-small bg-darkCrimson" data-role="tile" onclick="metroDialog.open('#dialog-annuaire')">
                        <div class="tile-content iconic">
                            <span class="icon mif-phone-in-talk "></span>
                        </div>
                    </div>
                </div>
            </c:if>

            <c:if test="${ upfDAO.hasAccess((sessionScope.utilisateur), '003') }">
                <div class="tile-group triple">
                    <span class="tile-group-title" style="font-weight: 300">Actuellement dans nos locaux</span>

                    <div class="tile-container">
                        <div class="tile tile-big-x tile-wide-y bg-steel fg-white" data-role="tile" style="background: none !important">
                            <div class="tile-content ">
                                <div class="">
                                    <p style="height: 1px;"></p>
                                    <canvas id="myChart" height="50" width="100"></canvas>
                                </div>

                            </div>
                            <span class="tile-label">Graphe par bureau</span>
                        </div>
                    </div>

                </div>
            </c:if>

            <c:if test="${ upfDAO.hasAccess((sessionScope.utilisateur), '003') }">
                <div class="tile-group double">
                    <span class="tile-group-title" style="font-weight: 300">Autres</span>
                    <div class="tile-container">
                        <div class="tile bg-orange fg-white" data-role="tile" onclick="metroDialog.open('#dialog-profil')">
                            <div class="tile-content">
                                <img src="${empty profil ? 'img/user.ico': profil}" data-role="fitImage" data-format="square">
                            </div>
                            <span class="tile-label fg-dark">Profil</span>
                        </div>
                        <div class="tile bg-darkIndigo" data-role="tile" onclick="toggleMetroCharm('#right-charm')">
                            <div class="tile-content iconic">
                                <span class="icon mif-mail"></span>
                            </div>
                            <span class="tile-label">Messages</span>
                            <span id="messages_non_lusNombre" class="tile-badge bg-orange">${messages_nonlus.size()}</span>
                        </div>
                        <div class="tile bg-green fg-white" data-role="tile" onclick="toggleMetroCharm('#colis-charm')">
                            <div class="tile-content iconic">
                                <span class="icon mif-gift"></span>
                            </div>
                            <span class="tile-label">Colis</span>
                            <span class="tile-badge bg-orange">${coliss.size()}</span>
                        </div>
                        <div class="tile bg-blue fg-white" data-role="tile" onclick="metroDialog.open('#dialog-historique')">
                            <div class="tile-content iconic">
                                <span class="icon mif-history"></span>
                            </div>
                            <div class="tile-label">Historique</div>
                        </div>
                    </div>
                </div>
            </c:if>


        </div>

        <div class="fg-white padding30" style="position: fixed;bottom: 0;width: 100%; ">
            <p style="float: right; opacity: 0.8">&copy; Powered by SIA TECHNOLOGY GROUP 2018</p>
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
        <c:forEach items="${entrees}" var="c">
            <div data-role="dialog" id="dialog-form-sortie${c.identree}" class="padding20 bg-orange"
                 data-close-button="true"
                 data-windows-style="false"
                 data-href="EntreeServlet?id=${c.identree}&temps=${temps}"
                 data-overlay="true"
                 data-color="fg-white"
                 data-width="600">
            </div>
        </c:forEach>

        <div data-role="dialog" id="dialog-form-nouvelle-visite" class="padding20 bg-darkGreen"
             data-close-button="true"
             data-href="FormNouvelleVisiteServlet?temps=${temps}"
             data-overlay="true"
             data-color="fg-white"
             data-width="600">
        </div>
        <c:forEach items="${visites}" var="mvx">
            <div data-role="dialog" id="dialog-visite-entree${mvx.idvisite}" class="padding20 bg-darkBlue"
                 data-close-button="true"
                 data-href="VisiteToEntreeServlet?id=${mvx.idvisite}&temps=${temps}"
                 data-overlay="true"
                 data-color="fg-white"
                 data-width="600">
            </div>
        </c:forEach>
        <div data-role="dialog" id="dialog-form-nouveau-colis" class="padding20 bg-green"
             data-close-button="true"
             data-href="FormNouveauColisServlet?temps=${temps}"
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
        <div data-role="dialog" id="dialog-annuaire" class="bg-grayLighter"
             data-close-button="true"
             data-windows-style="true"
             data-href="AnnuaireServlet?temps=${temps}"
             data-overlay="true"
             data-color="fg-dark"
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
        <div data-role="dialog" id="dialog-form-utilisateur${sessionScope.utilisateur.idutilisateur}" class="padding20 bg-blue"
             data-close-button="true"
             data-href="FormUtilisateurServlet?id=${sessionScope.utilisateur.idutilisateur}&temps=${temps}"
             data-overlay="true"
             data-color="fg-white"
             data-width="600">
        </div>
        <div data-role="dialog" id="dialog-historique-global" class=""
             data-close-button="true"
             data-windows-style="true"
             data-href="HistoriqueServlet?temps=${temps}"
             data-overlay="true"
             data-width="1000"
             data-height="500">
        </div>
        <div data-role="dialog" id="dialog-historique" class=""
             data-close-button="true"
             data-windows-style="true"
             data-href="HistoriqueServlet?temps=${temps}"
             data-overlay="true"
             data-width="1000"
             data-height="500">
        </div>

    </body>
</html>