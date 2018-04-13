/*
On game load, display:
1) hand
2) others
3) decks
4) console
 */

var WIDTH = 200;
var X_OFFSET = WIDTH/3;
var questStages = -1;
var isQuest = false;
var isSponsor = false;

$(document).ready(function() {
    // TODO: Periodically check for changes (flicker?).
    // ^ update everyone instead of just one
    // WebSockets/SSE could solve this?
    $("#current-player").attr("data-playerid", document.cookie.split("=")[1]);

    var previous = -1;
    setInterval(function() {
        $.ajax({
            method: "GET",
            url: "/checkUpdate",
            success: function(counter) {
                if (counter !== previous) {
                    previous = counter;
                    story_card();
                    current_player();
                    waiting_players();
                    if (isQuest) {
                        if (!isSponsor) {
                            $("#quest-weapons").empty();
                            $("#quest-weapons").append("<td></td>");
                            $("#quest-foes").empty();

                            displayOthersQuest();
                        }
                    } else {
                        $("#quest").css("visibility", "hidden");
                        $("#quest-weapons").empty();
                        $("#quest-weapons").append("<td></td>");
                        $("#quest-foes").empty();
                        questStages = -1;
                    }
                    getState();
                }
            }
        });
    }, 1500);
});

function selectCard(c) {
    c = $(c);
    var old = c.css("transform");
    if (c.attr("data-selected") === "0") {
        c.css("transform", old + ' translateY(-100px)');
        c.attr("data-selected", "1");
    } else {
        c.css("transform", old + ' translateY(100px)');
        c.attr("data-selected", "0");
    }
}

var x = 99;

function focusCard(c) {
    $(c).css('z-index', x++);

    var parent = $(c).parent().children();
    var index = parent.index(c);

    for(var i = 0; i <= index; i++){
		$(parent[i]).css('z-index',x++);
    }
    for(i = parent.length;i>=index;i--){
		$(parent[i]).css('z-index',x++);
    }
    $(parent[index]).css('z-index',x++);
    $(c).css('border', 'solid 2px gold');
}

function unfocusCard(c) {
    $(c).css('border', "none");
}

function getState() {
    var data = {};
    $.ajax({
        method: "GET",
        url: "/state",
        success: function (state) {
            data['state'] = state;
            $.ajax({
                method: "GET",
                url: "/currentturn",
                success: function (turn) {
                    data['turn'] = turn;
                    if (parseInt($("#current-player").attr('data-playerid')) === turn)
                        executeState(data);
                    else
                        updateState(data);
                }
            });
        }
    });
}


function updateState(data) {
    var state = data['state'];
    var turn = data['turn'];

    highlight_player(turn);

    var msg = $("#message");
    var btn = $("#buttons");
    msg.empty();
    btn.empty();
    switch (state) {
        case "BEGIN_TURN":
            msg.append("<h2>Waiting for: " + $("[data-playerid='" + turn + "']").attr("data-playername") + "</h2>");
            break;
        case "EVENT_LOGIC":
            msg.append("<h2>Event: " + $("[data-cardname]").attr("data-cardname") + "</h2>");
            break;
        case "END_TURN":
            msg.append("<h2>Waiting for: " + $("[data-playerid='" + turn + "']").attr("data-playername") + "</h2>");
            break;
        case "SPONSOR_QUEST":
            msg.append("<h2>Waiting for: " + $("[data-playerid='" + turn + "']").attr("data-playername") + "</h2>");
            break;
        case "SPONSOR_SUBMIT":
            isQuest = true;
            $.ajax({
                method: "GET",
                url: "/questInfo",
                success: function(info) {
                    questStages = info;
                }
            });
            msg.append("<h2>Currently Sponsoring Quest: " + $("[data-playerid='" + turn + "']").attr("data-playername") + "</h2>");
            break;
        case "TOURNAMENT_STAGE":
            msg.append("<h2>Waiting for: " + $("[data-playerid='" + turn + "']").attr("data-playername") + "</h2>");
            break;
        case "PARTICIPATE_QUEST":
            msg.append("<h2>Asking " +  $("[data-playerid='" + turn + "']").attr("data-playername") + " for participation.</h2>");
            break;
        case "PARTICIPATE_TOURNAMENT":
            msg.append("<h2>Waiting for: " + $("[data-playerid='" + turn + "']").attr("data-playername") + "</h2>");
            break;
        case "QUEST_HANDLER":
            msg.append("<h2>Waiting for: " + $("[data-playerid='" + turn + "']").attr("data-playername") + "</h2>");
            break;
        case "TOURNAMENT_HANDLER":
            msg.append("<h2>Waiting for: " + $("[data-playerid='" + turn + "']").attr("data-playername") + "</h2>");
            break;
        case "DISCARD":
            msg.append("<h2>Someone needs to discard!</h2>");
            msg.append("<h2>Waiting for: " + $("[data-playerid='" + turn + "']").attr("data-playername") + "</h2>");
            break;
        case "STAGE_FOE":
            msg.append("<h2>Waiting for: " + $("[data-playerid='" + turn + "']").attr("data-playername") + "</h2>");
            break;
        case "STAGE_TEST":
            msg.append("<h2>Waiting for: " + $("[data-playerid='" + turn + "']").attr("data-playername") + "</h2>");
            break;
        case "STAGE_END":
            msg.append("<h2>Waiting for: " + $("[data-playerid='" + turn + "']").attr("data-playername") + "</h2>");
            break;
        case "QUEST_END":
            isQuest = false;
            msg.append("<h2>Waiting for: " + $("[data-playerid='" + turn + "']").attr("data-playername") + "</h2>");
            break;
        case "TOURNAMENT_STAGE_END":
            msg.append("<h2>Waiting for: " + $("[data-playerid='" + turn + "']").attr("data-playername") + "</h2>");
            break;
        case "TOURNAMENT_END":
            msg.append("<h2>Waiting for: " + $("[data-playerid='" + turn + "']").attr("data-playername") + "</h2>");
            break;

    }
    /* Update console - like ConsoleView */
    /* if-else: if relevant to you, update personal, else update general */
    /* very very very not secure at all. RIP js h4x0rs */
}

function executeState(data) {
    var state = data['state'];
    var turn = data['turn'];

    highlight_player(turn);

    var msg = $("#message");
    var btn = $("#buttons");
    msg.empty();
    btn.empty();
    switch (state) {
        case "BEGIN_TURN":
            msg.append("<h2>" + state + "</h2>");
            btn.append("<button onclick='nextState()'>Start Turn</button>");
            break;
        case "EVENT_LOGIC":
            msg.append("<h2>Event: " + $("[data-cardname]").attr("data-cardname") + "</h2>");
            btn.append("<button onclick='nextState()'>Run Event</button>");
            break;
        case "END_TURN":
            msg.append("<h2>End Turn</h2>");
            btn.append("<button onclick='nextState()'>End Turn</button>");
            break;
        case "SPONSOR_QUEST":
            msg.append("<h2>Quest: " + $("[data-cardname]").attr("data-cardname") + "</h2>");
            btn.append("<button onclick='decide(true)'>Sponsor</button>");
            btn.append("<button onclick='decide(false)'>Decline</button>");
            break;
        case "SPONSOR_SUBMIT":
            isQuest = true;
            isSponsor = true;
            msg.append("<h2>Successfully Sponsored Quest! Click below to begin setting up.</h2>");
            btn.append("<button onclick='buildQuest()'>Setup Quest</button>")
            break;
        case "TOURNAMENT_STAGE":
            msg.append("<h2>Select your cards!</h2>");
            btn.append("<button onclick='playSelected()'>Play!</button>");
            break;
        case "PARTICIPATE_QUEST":
            msg.append("<h2>Participate in quest?</h2>");
            btn.append("<button onclick='decide(true)'>Participate</button>");
            btn.append("<button onclick='decide(false)'>Decline</button>");
            break;
        case "PARTICIPATE_TOURNAMENT":
            msg.append("<h2>Participate in Tournament?</h2>");
            btn.append("<button onclick='decide(true)'>Participate</button>");
            btn.append("<button onclick='decide(false)'>Decline</button>");
            break;
        case "QUEST_HANDLER":
            msg.append("<h2>Begin/Continue on Quest (next stage)?</h2>");
            btn.append("<button onclick='nextState()'>Begin</button>");
            break;
        case "TOURNAMENT_HANDLER":
            msg.append("<h2>Begin Tournament?</h2>");
            btn.append("<button onclick='nextState()'>Begin</button>");
            break;
        case "DISCARD":
            msg.append("<h2>Please select cards to discard!</h2>");
            btn.append("<button onclick='discardSelected()'>Discard!</button>");
            break;
        case "STAGE_FOE":
            msg.append("<h2>Next stage is a foe! Choose your cards.</h2>");
            btn.append("<button onclick='playSelected()'>Play!</button>");
            break;
        case "STAGE_TEST":
            msg.append("<h2>Next stage is a test! Choose your cards.</h2>");
            btn.append("<button onclick='playSelected()'>Bid!</button>");
            btn.append("<button onclick='decide(true)'>Give up!</button>");
            break;
        case "STAGE_END":
            msg.append("<h2>Run the stage?</h2>");
            btn.append("<button onclick='nextState()'>End Stage</button>");
            break;
        case "QUEST_END":
            isQuest = false;
            isSponsor = false;
            questStages = -1;
            msg.append("<h2>End the quest?</h2>");
            btn.append("<button onclick='nextState()'>End Quest</button>");
            break;
        case "TOURNAMENT_STAGE_END":
            msg.append("<h2>End the tournament round?");
            btn.append("<button onclick='nextState()'>End round</button>");
            break;
        case "TOURNAMENT_END":
            msg.append("<h2>End the tournament?");
            btn.append("<button onclick='nextState()'>End tournament</button>");
            break;
    }
    /* Update console - like ConsoleView */
    /* if-else: if relevant to you, update personal, else update general */
    /* very very very not secure at all. RIP js h4x0rs */
}

function nextState() {
    $.ajax({
        method: "GET",
        url: "/nextState",
        success: update_game
    });
}

function playSelected() {
    var selected = getSelected();
    $.ajax({
        method: "POST",
        contentType: "application/json",
        url: "/playCards?id=" + parseInt(document.cookie.split("=")[1]),
        data: JSON.stringify(selected),
        success: update_game
    });
}

function discardSelected() {
    var selected = getSelected();
    $.ajax({
        method: "POST",
        contentType: "application/json",
        url: "/discardCards?id=" + parseInt(document.cookie.split("=")[1]),
        data: JSON.stringify(selected),
        success: update_game
    });
}

function decide(choice) {
    $.ajax({
        method:"POST",
        url:"/decide",
        data: {id: parseInt(document.cookie.split("=")[1]), choice: choice},
        success: update_game
    });
}

function getSelected() {
    return $('img[data-selected="1"]').map(function(){
        return parseInt($(this).attr("data-cardid"));
    }).get();
}

function highlight_player(id) {
    $("body").find("[data-playerid]").css('border', 'none');
    $("body").find("[data-playerid=" + id +"]").css('border', 'solid 5px orange');
}

function update_game() {
    story_card();
    current_player();
    waiting_players();
    getState();
}
/* ---------- */
/* COMPONENTS */
/* ---------- */

/* Current Story Card */
function story_card() {
    $("tr", '#decks').empty();
    $("tr", '#decks').append('<td><img src="images/A Back.jpg"/></td>');
    $.ajax({
        method: "GET",
        url: "/storyCard",
        success: function(card) {
            if (card !== "") {
                $("tr", '#decks').append('<td><img data-cardtype="current" data-cardname="' + card.name + '" src="images/' + card.res +'"/></td>');
            } else {
                $("tr", '#decks').append('<td><img src="images/S Back.jpg"/></td>');
            }
        }
    });
}

/* Current Player */
function current_player() {
    $("tr", "#current-player").empty();
    $.ajax({
        method:"GET",
        url: "/currentplayer",
        data: {id: parseInt(document.cookie.split("=")[1])},
        success: function(player) {
            console.log(player);
            $("#current-player").attr("data-playername", player.name);

            var hand = player.hand;
            var inplay = player.inPlay;
            var cardsPerCol = WIDTH / X_OFFSET;
            var handSpan = Math.floor(hand.length/cardsPerCol + 1);
            var inplaySpan = Math.ceil(inplay.length/cardsPerCol + 1);

            var numCol = 1 + 1 + handSpan + 1 + inplaySpan + 1 + 1 + 1;

            for (var i = numCol - 1; i >= 0; i--) {
                $("tr", "#current-player").append("<td data-id='" + i +"'></td>");
            }

            $('[data-id="0"]').append('<img src="images/' + player.rank.path +'" />');
            $('[data-id="1"]').append('<img style="height: 80%; top:0;" src="images/' + player.shieldImage +'" />');
            $('[data-id="1"]').append('<h2 style="color: white;">Shields: ' + player.rank.shields + '</h2>')
            var cell = $('[data-id="2"]');

            cell.attr("colspan", handSpan);

            for (i = 0; i < hand.length; i++) {
                cell.append('<img ' +
                    'data-type="hand" ' +
                    'data-id="' + i + '" ' +
                    'data-cardid="' + hand[i].id + '" ' +
                    'data-selected="0" ' +
                    'onmouseover="focusCard(this)" ' +
                    'onmouseleave="unfocusCard(this)" ' +
                    'onclick="selectCard(this)" ' +
                    'style="transform: translateX(' + -i*X_OFFSET + 'px);" ' +
                    'src="images/' + hand[i].res +'" />');
            }

            cell = $('[data-id=' + (4+handSpan) + ']');

            cell.attr("colspan", inplaySpan);

            for (i = 0; i < inplay.length; i++) {
                cell.append('<img ' +
                    'data-type="inplay" ' +
                    'data-id="' + i + '" ' +
                    'data-cardid="' + inplay[i].id + '" ' +
                    'onmouseover="focusCard(this)" ' +
                    'onmouseleave="unfocusCard(this)" ' +
                    'style="transform: translateX(' + -i*X_OFFSET + 'px);" ' +
                    'src="images/' + inplay[i].res +'" />');
            }

            cell = $('[data-id=' + (numCol - 1) + ']');
            cell.append("<h2 style='color: white'>Battle Points: " + player.totalBattlePoints + "</h2>");

        }
    });
}

/* Other Players */
function waiting_players() {
    $('#waiting-players').empty();
    $.ajax({
        method:"GET",
        data: {id: parseInt(document.cookie.split("=")[1])},
        url: "/waitingplayers",
        success: function(players) {
            var max = 0;

            for (var j = 0; j < players.length; j++) {

                $('#waiting-players').append('<tr data-playername="' + players[j].name +'"data-playerid="' + players[j].id + '"></tr>')
                if (players[j].inPlay.length >= max) {
                    max = players[j].inPlay.length;
                }
            }

            var cardsPerCol = WIDTH / X_OFFSET;
            var inplaySpan = Math.ceil(max/cardsPerCol + 1);

            var numCol = 1 + 1 + 1 + inplaySpan + 1;

            for (var i = numCol - 1; i >= 0; i--) {
                $("tr", "#waiting-players").append("<td data-id='" + i +"'></td>");
            }

            /* ranks */
            var playerRow;
            for (i = 0; i < players.length; i++) {
                playerRow = $('[data-id="0"]', '[data-playerid=' + players[i].id + ']', "#waiting-players");
                playerRow.append('<img src="images/' + players[i].rank.path + '"/>');
            }

            for (i = 0; i < players.length; i++) {
                playerRow = $('[data-id="1"]', '[data-playerid=' + players[i].id + ']', "#waiting-players");
                playerRow.append('<img style="height: 80%;" src="images/' + players[i].shieldImage + '"/>');
                playerRow.append('<h2 style="color: white">Shields: ' + players[i].rank.shields + '</h2>')
            }

            for (i = 0; i < players.length; i++) {
                playerRow = $('[data-id="2"]', '[data-playerid=' + players[i].id + ']', "#waiting-players");
                playerRow.append('<img src="images/A Back.jpg"/>');
                playerRow.append('<h2 style="color: white">Count: ' + players[i].hand.length + '</h2>');
            }

            if(max > 0) {
                for (i = 0; i < players.length; i++) {
                    playerRow = $('[data-id="3"]', '[data-playerid=' + players[i].id + ']', "#waiting-players");
                    playerRow.attr("colspan", inplaySpan);
                    var inplay = players[i].inPlay;
                    for (var j = 0; j < inplay.length; j++) {
                        playerRow.append('<img ' +
                            'data-type="inplay" ' +
                            'data-id="' + j + '" ' +
                            'data-cardid="' + inplay[j].id + '" ' +
                            'onmouseover="focusCard(this)" ' +
                            'onmouseleave="unfocusCard(this)" ' +
                            'style="transform: translateX(' + -j*X_OFFSET + 'px);" ' +
                            'src="images/' + inplay[j].res +'" />');
                    }
                }
            }

            for (i = 0; i < players.length; i++) {
                playerRow = $('[data-id="' + (numCol - 1) + '"]', '[data-playerid=' + players[i].id + ']', "#waiting-players");
                playerRow.append('<h2 style="color: white">Battle Points: ' + players[i].totalBattlePoints + '</h2>');
            }
        }
    });
}

/* Decks */


/* Tournament */

/* Quest */
function buildQuest() {

    $("#quest-weapons").empty();
    $("#quest-weapons").append("<td></td>");
    $("#quest-foes").empty();

    var msg = $("#message");
    var btn = $("#buttons");

    msg.empty();
    btn.empty();

    msg.append("<h2>Add foes/test to the first row, weapons to the second row (Drag n Drop).");
    btn.append("<button onclick='submitQuest()'>Submit Setup</button>");

    $.ajax({
        method: "GET",
        url: "/questInfo",
        success: function(info) {
            questStages = info;
            for (var i = 0; i < info; i++) {
                $("#quest-foes").append('<td data-id="foe-stage-' + (i+1) + '" class="droppable-foe"></td>');
                $("#quest-weapons").append('<td data-id="weapons-stage-' + (i+1) +'" class="droppable-weapons"></td>');
            }

            $("#quest-foes").prepend('<td><img src="' + $("[data-cardtype='current']").attr("src") +'"/></td>')
            $('#quest').css("visibility", "visible");


            $("img[data-type='hand']").attr("ondrag", "removeStuff(this)");
            $("img[data-type='hand']").prop("onclick", null).draggable({revert: "invalid"});

            $(".droppable-foe").droppable({
                drop: function (event, ui) {
                    $(ui.draggable).detach().prop("onmouseover", null).css({top: 0,left: 0}).appendTo(this);
                }
            });

            $(".droppable-weapons").droppable({
                drop: function (event, ui) {
                    var num = $(this).find("img").length * 60;

                    $(ui.draggable).detach().css({top: 0,left: 0, transform: "translateY("+ num +"px)", "z-index": 0}).appendTo(this);

                }
            });
        }
    });
}

function removeStuff(c) {
    $(c).css("transform", "none");
}

function submitQuest(){

    var selected = [];
    
    for (var i = 1; i < questStages+1; i++) {
        var stagefoes = $("img", "[data-id='foe-stage-" + i + "']").map(function(){
            return parseInt($(this).attr("data-cardid"));
        }).get();

        var stageweapons = $("img", "[data-id='weapons-stage-" + i + "']").map(function(){
            return parseInt($(this).attr("data-cardid"));
        }).get();

        selected.push(stagefoes.concat(stageweapons));
    }

    $.ajax({
        method: "POST",
        contentType: "application/json",
        url: "/submitQuest?id=" + parseInt(document.cookie.split("=")[1]),
        data: JSON.stringify(selected),
        success: function(data) {
            if (data) {
                update_game();
            }
        }
    });
}

function displayQuest() {

}

function displayOthersQuest() {
    for (var i = 0; i < questStages; i++) {
        $("#quest-foes").append('<td data-id="foe-stage-' + (i+1) + '"></td>');
        $("#quest-weapons").append('<td data-id="weapons-stage-' + (i+1) +'"></td>');
    }

    $.ajax({
        method: "GET",
        url: "/state",
        success: function(state) {
            if (state === "STAGE_END") {
                $("#quest-foes").prepend('<td><img src="' + $("[data-cardtype='current']").attr("src") +'"/></td>')
                $('#quest').css("visibility", "visible");
                displayStage();
            }
        }
    });
}

function displayStage() {
    $.ajax({
        method: "GET",
        url: "/getStageIndex",
        success: function(index) {
            $.ajax({
                method: "GET",
                url: "/getStageCards",
                data: {index: index},
                success: function(card) {
                    console.log("Quest Cards for current stage: ");
                    console.log(card);
                    $("[data-id='foe-stage-" + (index+1) + "']").append("<img src='images/" + card[0].res +"' />");
                }
            });
        }
    });
}

/* Console */
