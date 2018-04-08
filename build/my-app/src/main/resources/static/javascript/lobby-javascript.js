function submit() {
    $.ajax({
        method:"POST",
        url: "/namesubmit",
        data: {name: $('#name').val()},
        success: wait_to_start()
    });
}

function wait_to_start() {

    var intID = setInterval(function () {
        $.ajax({
            method: "GET",
            url: "/checkready",
            success: function(res) {
                if (res) {
                    document.getElementById("ready").style.display = visible;
                    document.getElementById("submit").style.display = hidden;
                    clearInterval(intID);
                    clearInterval(intID2);
                }
            }
        });
    }, 2000);

    var intID2 = setInterval(function () {
        $.ajax({
            method: "GET",
            url: "/waiting",
            success: function(data) {
                var info = $('#information');
                info.clear();
                for(var datum in data){
                    if (data[datum].name)
                        info.append("<li>" + data[datum].name + "</li>")
                }
            }
        });
    }, 1000);
}
