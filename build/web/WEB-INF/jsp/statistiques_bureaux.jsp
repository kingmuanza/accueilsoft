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
            $("#example_table").DataTable({
                'searching': true,
                "iDisplayLength": 50,
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
                    "infoFiltered": "(sur les _MAX_ disponibles)"
                }
            });

            function showDialog(id, identree, nom, badge, piece, numero, motif, bureau, employe, heure) {
                $("#heureArrivee").html(heure);
                $("#entree input[name=nom]").val(nom);
                $("#entree input[name=identree]").val(identree);
                $("#entree select[name=badge]").val(badge);
                $(function () {
                    $("#entree select[name=badge]").select2();
                });
                $("#entree select[name=piece]").val(piece);
                $("#entree input[name=numero]").val(numero);
                $("#entree input[name=motif]").val(motif);
                $("#entree select[name=bureau] option[value='" + bureau + "']").attr('selected', 'selected');
                $(function () {
                    $("#entree select[name=bureau]").select2();
                });
                $("#entree select[name=employe] option[value='" + employe + "']").attr('selected', 'selected');
                $(function () {
                    $("#entree select[name=employe]").select2();
                });
                var dialog = $(id).data('dialog');
                dialog.open();
            }

            function showVisite(id, idvisite, nom, bureau, heure, motif, commentaire, bureau2, employe, piece) {
                $("#heureArrivee").html(heure);
                $("#personne_attendue").html(nom);
                $("#bureau_destination_final").html(bureau);
                $("#heure_attendue").html(heure);
                $("#visite input[name=idvisite]").val(idvisite);
                $("#entree select[name=badge]").val(1);
                $(function () {
                    $("#entree select[name=badge]").select2();
                });
                $("#entree select[name=piece]").val(piece);
                $("#visite textarea[name=commentaire]").val(commentaire);
                $("#visite input[name=motif]").val(motif);
                $("#entree select[name=bureau] option[value='" + bureau + "']").attr('selected', 'selected');
                $(function () {
                    $("#entree select[name=bureau]").select2();
                });
                $("#entree select[name=employe] option[value='" + employe + "']").attr('selected', 'selected');
                $(function () {
                    $("#entree select[name=employe]").select2();
                });
                var dialog = $(id).data('dialog');
                dialog.open();
            }

            function sortie(id) {
                var identree = $("#entree input[name=identree]").val();
                $.confirm({
                    title: 'Confirmation',
                    boxWidth: '30%',
                    useBootstrap: false,
                    content: 'Avez-vous remis la pièce d\'identité du visiteur et récupéré le badge ?\n',
                    buttons: {
                        Oui: {
                            text: 'Oui, je l\'ai fait',
                            btnClass: 'button ribbed-green fg-white',
                            action: function () {
                                $.post(
                                        "start",
                                        {
                                            action: "sortie",
                                            id: identree
                                        }
                                );
                                $("#list_" + identree + "").hide();
                                location.reload(true);
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


            //Nouvelle entrée
//            $("#nouveau_entree select[name=bureau]").on("change", function () {
//                console.log("Le bureau a changé !! : " + $("#nouveau_entree select[name=bureau]").val());
//            });

            $("#nouveau_entree select[name=employe]").on("change", function () {

                var employe = $("#nouveau_entree select[name=employe]").val();
                console.log("L'employé a changé !! : " + employe);

                var bureau = $("#employe_" + employe + "").attr("bureau").toString();
                console.log("Son bureau c'est le : " + bureau);

                $("#nouveau_entree select[name=bureau] option[value='" + bureau + "']").attr('selected', 'selected');
                $("#nouveau_entree select[name=bureau]").select2("val", bureau);

                console.log("L'ordi a selectionné le bureau : " + $("#nouveau_entree select[name=bureau]").val());

                $("#nouveau_entree select[name=bureau]").select2();

            });

            //Modifier une entrée
//            $("#entree select[name=bureau]").on("change", function () {
//                console.log("Le bureau a changé !! : " + $("#entree select[name=bureau]").val());
//            });

            $("#entree select[name=employe]").on("change", function () {
                var employe = $("#entree select[name=employe]").val();
                console.log("L'employé a changé !! : " + employe);

                var bureau = $("#employeMod_" + employe + "").attr("bureau").toString();
                console.log("Son bureau c'est le : " + bureau);

                $("#entree select[name=bureau] option[value='" + bureau + "']").attr('selected', 'selected');
                $("#entree select[name=bureau]").select2("val", bureau);

                console.log("L'ordi a selectionné le bureau : " + $("#entree select[name=bureau]").val());

                $("#entree select[name=bureau]").select2();
            });

            function test() {
                alert("C bon !!");
            }
            //Création des élements pour les graphes

            var nombre = function () {
                return Math.round(Math.random() * 205) + 50;
            };
            var liste_bureaux = [];
            var data = [];
            var backgroundColor = [];

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
                <c:forEach items="${b.entrees}" var="e">
                    <c:if test="${!empty premierEntree && premierEntree.before(e.heureDentree) && !empty derniereEntree && derniereEntree.after(e.heureDentree)}">
            i++;
                    </c:if>
                </c:forEach>
            data.push(i);
            backgroundColor.push('rgba(' + nombre() + ',' + nombre() + ',' + nombre() + ', 0.5)')
            </c:forEach>



            console.log(liste_bureaux);
            console.log(data);
            console.log(backgroundColor);

            var ctx = document.getElementById("myChart").getContext('2d');
            var ctx2 = document.getElementById("myChart2").getContext('2d');
            var myChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: liste_bureaux,
                    datasets: [{
                            label: 'Nombre de visites',
                            data: data,
                            backgroundColor: backgroundColor,
                            borderColor: backgroundColor,
                            borderWidth: 1
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
                    }
                }
            });
            var myChart2 = new Chart(ctx2, {
                type: 'pie',
                data: {
                    labels: liste_bureaux,
                    datasets: [{
                            label: 'Nombre de visites',
                            data: data,
                            backgroundColor: backgroundColor,
                            borderColor: backgroundColor,
                            borderWidth: 1
                        }]
                },
                options: {
                }
            });

            function filtrer() {
                var debut = $("input[name=debut]").val();
                var fin = $("input[name=fin]").val();
                //alert("#/stats/"+debut+"/"+fin);
                window.location.href = "#/stats/" + debut + "/" + fin;
            }
        </script>
    </head>
    <body >
        <h3 class="">Statistiques des entrées par bureau</h3>
        <div class="row cells10" style="padding-right: 40px; padding-top: 0px;">
            <div class="cell colspan4">
                <canvas id="myChart2"></canvas>
            </div>
        </div>

    </body>
</html>
