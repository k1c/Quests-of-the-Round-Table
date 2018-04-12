/*
On game load, display:
1) hand
2) others
3) decks
4) console
 */
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
    // Get cards
    var cards = $('[data-type="hand"]');

    // Rearrage
    var index = parseInt($(c).attr('data-id'));
    var right = 0;
    var left = cards.length - 1;

    while (right !== left) {
        if($(cards[right]).attr('data-id') !== $(c).attr('data-id')) {
            $(cards[right]).css('z-index', x++);
            right += 1;
        }

        if($(cards[left]).attr('data-id') !== $(c).attr('data-id')) {
            $(cards[left]).css('z-index', x++);
            left -= 1;
        }
    }

    $(c).css('z-index', x++);
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
    console.log("update state");
    console.log(data);


    var state = data['state'];
    var turn = data['turn'];

    console.log();
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
            msg.append("<h2>" + state + "</h2>");
            break;
        case "SPONSOR_QUEST":
            msg.append("<h2>" + state + "</h2>");
            break;
        case "SPONSOR_SUBMIT":
            msg.append("<h2>" + state + "</h2>");
            break;
        case "TOURNAMENT_STAGE":
            msg.append("<h2>" + state + "</h2>");
            break;
        case "PARTICIPATE_QUEST":
            msg.append("<h2>" + state + "</h2>");
            break;
        case "PARTICIPATE_TOURNAMENT":
            msg.append("<h2>" + state + "</h2>");
            break;
        case "QUEST_HANDLER":
            msg.append("<h2>" + state + "</h2>");
            break;
        case "TOURNAMENT_HANDLER":
            msg.append("<h2>" + state + "</h2>");
            break;
        case "DISCARD":
            msg.append("<h2>Someone needs to discard!</h2>");
            break;
        case "STAGE_FOE":
            msg.append("<h2>" + state + "</h2>");
            break;
        case "STAGE_TEST":
            msg.append("<h2>" + state + "</h2>");
            break;
        case "STAGE_END":
            msg.append("<h2>" + state + "</h2>");
            break;

    }
    /* Update console - like ConsoleView */
    /* if-else: if relevant to you, update personal, else update general */
    /* very very very not secure at all. RIP js h4x0rs */
}

function executeState(data) {
    console.log(data);

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
            msg.append("<h2>" + state + "</h2>");
            break;
        case "SPONSOR_SUBMIT":
            msg.append("<h2>" + state + "</h2>");
            break;
        case "TOURNAMENT_STAGE":
            msg.append("<h2>" + state + "</h2>");
            break;
        case "PARTICIPATE_QUEST":
            msg.append("<h2>" + state + "</h2>");
            break;
        case "PARTICIPATE_TOURNAMENT":
            msg.append("<h2>" + state + "</h2>");
            break;
        case "QUEST_HANDLER":
            msg.append("<h2>" + state + "</h2>");
            break;
        case "TOURNAMENT_HANDLER":
            msg.append("<h2>" + state + "</h2>");
            break;
        case "DISCARD":
            msg.append("<h2>Please select cards to discard!</h2>");
            btn.append("<button onclick='discardSelected()'>Discard!</button>");
            break;
        case "STAGE_FOE":
            msg.append("<h2>" + state + "</h2>");
            break;
        case "STAGE_TEST":
            msg.append("<h2>" + state + "</h2>");
            break;
        case "STAGE_END":
            msg.append("<h2>" + state + "</h2>");
            break;
        default:
            msg.append("<h2> HOW DID YOU GET HERE? </h2>");

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

function discardSelected() {
    var selected = getSelected();
    console.log(selected);
    $.ajax({
        method: "POST",
        contentType: "application/json",
        url: "/discardCards?id=" + parseInt(document.cookie.split("=")[1]),
        data: JSON.stringify(selected),
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
                $("tr", '#decks').append('<td><img data-cardname="' + card.name + '" src="images/' + card.res +'"/></td>');
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
            $("#current-player").attr("data-playername", player.name);
            var WIDTH = 200;
            var X_OFFSET = WIDTH/3;

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
                    'style="transform: translateX(' + -i*50 + 'px);" ' +
                    'src="images/' + hand[i].res +'" />');
            }
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
            console.log(players);

            var WIDTH = 200;
            var X_OFFSET = WIDTH/3;


            var max = 0;

            for (var j = 0; j < players.length; j++) {

                $('#waiting-players').append('<tr class="others-table" data-playername="' + players[j].name +'"data-playerid="' + players[j].id + '"></tr>')
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
                playerRow.append('<img class="other-players" src="images/' + players[i].rank.path + '"/>');
            }

            for (i = 0; i < players.length; i++) {
                playerRow = $('[data-id="1"]', '[data-playerid=' + players[i].id + ']', "#waiting-players");
                playerRow.append('<img class="other-players" style="height: 80%;" src="images/' + players[i].shieldImage + '"/>');
            }

            for (i = 0; i < players.length; i++) {
                playerRow = $('[data-id="2"]', '[data-playerid=' + players[i].id + ']', "#waiting-players");
                playerRow.append('<img class="other-players" src="images/A Back.jpg"/>');
            }

            if(max > 0) {
                // do inPlay
            }

            for (i = 0; i < players.length; i++) {
                playerRow = $('[data-id="' + (max + 1 + 3) + '"]', '[data-playerid=' + players[i].id + ']', "#waiting-players");
                playerRow.append('<img class="other-players" style="height: 80%;" src="images/Battle_Points.png"/>');
            }
        }
    });
}

/* Decks */

/* Tournament */

/* Quest */

/* Console */