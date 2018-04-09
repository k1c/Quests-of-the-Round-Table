var intID2;
$(document).ready(function() {
    $('#submit').attr('onclick', 'submit()');
    intID2 = setInterval(function () {
        $.ajax({
            method: "GET",
            url: "/waiting",
            success: function(data) {
                var info = $('#information');
                info.empty();
                for(var datum = 0; datum < data.length; datum++){
                    if (data[datum].name)
                        info.append("<li>" + data[datum].name + "</li>")
                }
            }
        });
    }, 500);
});
function submit() {
    $.ajax({
        method:"POST",
        url: "/namesubmit",
        data: {name: $('#name').val()},
        success: wait_to_start()
    });
}

function wait_to_start() {
    $('#submit').hide();
    $('#waiting-text').show();
    var intID = setInterval(function () {
        $.ajax({
            method: "GET",
            url: "/checkready",
            success: function(res) {
                console.log("interval running");
                if (res) {
                    $('#waiting-text').hide();
                    $('#submit').removeAttr('onclick');
                    $('#submit-href').attr('href', 'game');
                    $('#submit').html("Ready!");
                    $('#submit').show();
                    clearInterval(intID);
                    clearInterval(intID2);
                }
            }
        });
    }, 500);
}
