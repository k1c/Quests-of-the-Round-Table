$(document).ready(function() {
    $.ajax({
        method:"GET",
        url: "/currentplayer",
        data: {id: parseInt(document.cookie.split("=")[1])},
        success: function(player) {
            var WIDTH = 200;
            var X_OFFSET = WIDTH/3;

            console.log(player);
            var hand = player.hand;
            var inplay = player.inPlay;
            var cardsPerCol = WIDTH / X_OFFSET;
            var handSpan = Math.floor(hand.length/cardsPerCol + 1);
            var inplaySpan = Math.ceil(inplay.length/cardsPerCol + 1);

            var numCol = 1 + 1 + handSpan + 1 + inplaySpan + 1 + 1 + 1;

            for (var i = numCol - 1; i >= 0; i--) {
                $("tr", "#current-player").append("<td data-id='" + i +"'></td>")
            }

            var cell = $('[data-id="2"]');
            cell.attr("colspan", handSpan);

            for (i = 0; i < hand.length; i++) {
            //    cell.append('<img style="transform: translateX(' + i*50 + 'px);" src="images/R Champion Knight.jpg" />')
            }
        }
    });
});

/*

 */