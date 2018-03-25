function start() {
    $.ajax({
        method:"POST",
        url: "/setup"
    });
}

function data() {
    $.ajax({
        method:"GET",
        url: "/currentplayer",
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
}