/*
On game load, display:
1) hand
2) others
3) decks
4) console
 */
$(document).ready(function() {
    $.ajax({
        method:"GET",
        url: "/currentplayer",
        data: {id: parseInt(document.cookie.split("=")[1])},
        success: function(player) {
            $("tr", '#decks').append('<td><img src="images/A King Arthur.jpg"/></td>')
            $("tr", '#decks').append('<td><img src="images/S Back.jpg"/></td>')
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
               cell.append('<img data-type="hand" data-id="' + i + '" onmouseover="focusCard(this)" onmouseleave="unfocusCard(this)" style="transform: translateX(' + -i*50 + 'px);" src="images/' + hand[i].res +'" />');
            }

            waiting_players();
        }
    });
});

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

}
/* ---------- */
/* COMPONENTS */
/* ---------- */

/* Current Player */

/* Other Players */
function waiting_players() {
    console.log("worked");
    $.ajax({
        method:"GET",
        url: "/waitingplayers",
        success: function(players) {
            console.log(players);
            var numCol = 1 + 1 + 1 + 1 + 1;

            for (var j = 0; j < players.length; j++) {
                $('#waiting-players').append('<tr data-id="' + j + '"></tr>')
            }
            for (var i = numCol - 1; i >= 0; i--) {
                $("tr", "#waiting-players").append("<td data-id='" + i +"'></td>");
            }

            /* ranks */
            for (i = 0; i < numCol; i++) {

            }
        }
    });
}
/* Decks */

/* Tournament */

/* Quest */

/* Console */