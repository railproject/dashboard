/**
 * Created by star on 4/26/14.
 */
$(function(){
    $("#date_selector").datepicker({format: "yyyy-mm-dd"});
    var date = $.url().param("date");
    if (date) {
        $("#date_selector").val(date);
    } else {
        $("#date_selector").datepicker('setValue', new Date());
    }
    var viewModel = new pageViewModel();
    $("#search").click(viewModel.loadPage);
    ko.applyBindings(viewModel);
    $("#search")[0].click();
//    $("#date_selector").val($.url().param("date"));

});

function pageViewModel() {
    var self = this;
    self.rows = ko.observableArray();

    self.sum = 0;

    self.cleanData = function() {
        self.rows.removeAll();
        $("#sum_row").remove();
        self.sum = 0;
    }

    self.count = function(row) {
        self. sum += row.kc_sum + row.ly_sum + row.hc_all_sum;
    }

    self.loadPage = function() {
        if ($("#date_selector").val().length <= 0) {
            return false;
        }
        $("#search").addClass("disabled");
        $("#myModal").modal('show').css({
            "margin-top": "200px"
        });
        $.ajax({
            url: "/dashboard/calendar/sf?date=" + $("#date_selector").val(),
//            async: false,
            type: "GET",
            success: function(grids) {
                self.cleanData();
                for(var i = 0; i< grids.length; i++) {
                    var row = {
                        index: i+1,
                        id: grids[i].id,
                        code: grids[i].code,
                        name: grids[i].name,
                        short_name: grids[i].shortname,
                        td_jc: grids[i].td_jc,
                        td_zd: grids[i].td_zd,
                        td_sum: grids[i].td_jc + grids[i].td_zd, //图客始发小计
                        lk_jc: grids[i].lk_jc,
                        lk_zd: grids[i].lk_zd,
                        lk_sum: grids[i].lk_jc + grids[i].lk_zd, //临客始发小计
                        hc_zd: grids[i].hc_zd,
                        hc_jc: grids[i].hc_jc,
                        hc_sum: grids[i].hc_jc + grids[i].hc_zd, //货车始发小计
                        xywd_jc: grids[i].xywd_jc,
                        xywd_zd: grids[i].xywd_zd,
                        xywd_sum: grids[i].xywd_jc + grids[i].xywd_zd, //行邮五定始发小计
                        ly_jc: grids[i].ly_jc,
                        ly_zd: grids[i].ly_zd,
                        ly_sum: grids[i].ly_jc + grids[i].ly_zd, //路用始发小计*
                        kc_sum: grids[i].td_jc + grids[i].td_zd + grids[i].lk_jc + grids[i].lk_zd, //客车合计*
                        hc_all_sum: grids[i].hc_jc + grids[i].hc_zd + grids[i].xywd_jc + grids[i].xywd_zd //货车合计*
                    }
                    self.count(row);
                    self.rows.push(row);
                }
                $("#content").append("<tr id=\"sum_row\"><td colspan=\"2\" class=\"text-center\">总合计</td><td colspan=\"17\" class=\"text-center\">" + self.sum + "</td></tr>")
            },
            complete: function() {
                $("#search").removeClass("disabled");
                $("#myModal").modal('hide');
            }
        });
    }

}