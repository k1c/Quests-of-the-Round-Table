$(document).ready(function() {
    $("s1").on("click", function(){
        $.ajax({
            method: "POST",
            url: "/scenarioOne"
        });
    });

    $("s2").on("click", function(){
        $.ajax({
            method: "POST",
            url: "/scenarioTwo"
        });
    });
});