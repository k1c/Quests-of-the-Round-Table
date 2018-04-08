$(document).ready(function() {
    console.log(document.cookie.id);
    $.ajax({
        method:"GET",
        url: "/currentplayer",
        data: {id: parseInt(document.cookie.split("=")[1])},
        success: function(player) {
            console.log(player);
            var div = $("#rank");
            div.empty();
            div.prepend('<img class="c-img" src="images' + player.rank.path + '" />');

            div = $("#shield");
            div.empty();
            div.prepend('<img style="height: 80%" src="images/' + player.shieldImage + '" />');

            div = $("#hand");
            div.empty();
            cards = player.hand;

            for(i = 0; i < cards.length - 5; i++){
                div.prepend('<img style="transform: translateX(' + i*100 + 'px)" class="c-img" src="images' + cards[i].res + '" />');
            }
        }
    });
});