var deffered = {};

function show(id) {
    var timer = deffered[id];
    if (!timer) {
        $("#" + id).children("div.spinner").show();
        deffered[id] = $.timer(function() {
            $("#" + id).children("div.spinner").hide();
            deffered[id].stop();
            deffered[id] = null;
        }, 5000, true);
    } else if (!timer.isActive) {
        timer.play();
    } else if (timer.isActive) {
        timer.reset();
    }


}

function doIt(ele) {
    return false;
   // window.open("railline_all.html?date=" + $(ele).find("div.padding-left-0").get(0).id, "_blank");
}
