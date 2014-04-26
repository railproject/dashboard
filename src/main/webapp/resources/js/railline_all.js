/**
 * Created by star on 4/26/14.
 */
$(function(){
    $("#date_selector").datepicker({format: "yyyy-mm-dd"});
    $("#date_selector").datepicker('setValue', new Date());
    var viewModel = new pageViewModel();
    $("#search").click(viewModel.loadPage);
    ko.applyBindings(viewModel);
    $("#search")[0].click();
//    $("#date_selector").val($.url().param("date"));

});

function pageViewModel() {
    var self = this;
    self.rows = ko.observableArray();

    self.td_sf_zd = 0;
    self.td_sf_jc = 0;
    self.td_sf_sum = 0;
    self.td_jr_zd = 0;
    self.td_jr_jc = 0;
    self.td_jr_sum = 0;
    self.lk_sf_zd = 0;
    self.lk_sf_jc = 0;
    self.lk_sf_sum = 0;
    self.lk_jr_zd = 0;
    self.lk_jr_jc = 0;
    self.lk_jr_sum = 0;
    self.sum = 0;

    self.cleanData = function() {
        self.rows.removeAll();
        $("#sum_row").remove();
        self.td_sf_zd = 0;
        self.td_sf_jc = 0;
        self.td_sf_sum = 0;
        self.td_jr_zd = 0;
        self.td_jr_jc = 0;
        self.td_jr_sum = 0;
        self.lk_sf_zd = 0;
        self.lk_sf_jc = 0;
        self.lk_sf_sum = 0;
        self.lk_jr_zd = 0;
        self.lk_jr_jc = 0;
        self.lk_jr_sum = 0;
        self.sum = 0;
    }

    self.count = function(row) {
        self.td_sf_zd += row.td_sf_zd;
        self.td_sf_jc += row.td_sf_jc;
        self.td_sf_sum += row.td_sf_sum;
        self.td_jr_zd += row.td_jr_zd;
        self.td_jr_jc += row.td_jr_jc;
        self.td_jr_sum += row.td_jr_sum;
        self.lk_sf_zd += row.lk_sf_zd;
        self.lk_sf_jc += row.lk_sf_jc;
        self.lk_sf_sum += row.lk_sf_sum;
        self.lk_jr_zd += row.lk_jr_zd;
        self.lk_jr_jc += row.lk_jr_jc;
        self.lk_jr_sum += row.lk_jr_sum;
        self.sum += row.sum;
    }

    self.loadPage = function() {
        if ($("#date_selector").val().length <= 0) {
            return false;
        }
        $("#search").addClass("disabled");
        $.ajax({
            url: "/dashboard/calendar/grid?date=" + $("#date_selector").val(),
//            async: false,
            type: "GET",
            success: function(grids) {
                self.cleanData();
                for(var i = 0; i< grids.length; i++) {
                    var row = {
                        index: i+1,
                        lj_id: grids[i].id,
                        lj_name: grids[i].name,
                        td_sf_zd: grids[i].td_sf_zd,
                        td_sf_jc: grids[i].td_sf_jc,
                        td_sf_sum: grids[i].td_sf_zd + grids[i].td_sf_jc, //图定始发小计
                        td_jr_zd: grids[i].td_jr_zd,
                        td_jr_jc: grids[i].td_jr_jc,
                        td_jr_sum: grids[i].td_jr_zd + grids[i].td_jr_jc, //图定接入小计
                        lk_sf_zd: grids[i].lk_sf_zd,
                        lk_sf_jc: grids[i].lk_sf_jc,
                        lk_sf_sum: grids[i].lk_sf_zd + grids[i].lk_sf_jc, //临客始发小计
                        lk_jr_zd: grids[i].lk_jr_zd,
                        lk_jr_jc: grids[i].lk_jr_jc,
                        lk_jr_sum: grids[i].lk_jr_zd + grids[i].lk_jr_jc, //临客接入小计
                        sum: grids[i].td_sf_zd + grids[i].td_sf_jc + grids[i].td_jr_zd + grids[i].td_jr_jc + grids[i].lk_sf_zd + grids[i].lk_sf_jc + grids[i].lk_jr_zd + grids[i].lk_jr_jc
                    }
                    self.count(row);
                    self.rows.push(row);
                }
                $("#content").append("<tr id=\"sum_row\"><td colspan=\"2\"><div class=\"text-center\">总合计</div></td><td>" + self.sum + "</td><td>" + self.td_sf_sum +
                    "</td><td>" + self.td_sf_jc + "</td><td>" + self.td_sf_zd + "</td><td>" + self.td_jr_sum + "</td><td>" + self.td_jr_jc + "</td><td>" +
                    self.td_jr_zd + "</td><td>" + self.lk_sf_sum + "</td><td>" + self.lk_sf_jc + "</td><td>" + self.lk_sf_zd + "</td><td>" + self.lk_jr_sum +
                    "</td><td>" + self.lk_jr_jc + "</td><td>" + self.lk_jr_zd + "</td></tr>");
            },
            complete: function() {
                $("#search").removeClass("disabled");
            }
        });
    }

}

function Grid(index, grid) {
    var self = this;
    self.index = ko.observable(index);
    self.lj_id = ko.observable(grid.id);
    self.lj_name = ko.observable(grid.name);
    self.td_sf_zd = ko.observable(grid.td_sf_zd);
    self.td_sf_jc = ko.observable(grid.td_sf_jc);
    self.td_sf_sum = ko.observable(grid.td_sf_zd + grid.td_sf_jc); //图定始发小计
    self.td_jr_zd = ko.observable(grid.td_jr_zd);
    self.td_jr_jc = ko.observable(grid.td_jr_jc);
    self.td_jr_sum = ko.observable(grid.td_jr_zd + grid.td_jr_jc); //图定接入小计
    self.lk_sf_zd = ko.observable(grid.lk_sf_zd);
    self.lk_sf_jc = ko.observable(grid.lk_sf_jc);
    self.lk_sf_sum = ko.observable(grid.lk_sf_zd + grid.lk_sf_jc); //临客始发小计
    self.lk_jr_zd = ko.observable(grid.lk_jr_zd);
    self.lk_jr_jc = ko.observable(grid.lk_jr_jc);
    self.lk_jr_sum = ko.observable(grid.lk_jr_zd + grid.lk_jr_jc); //临客接入小计
    self.sum = ko.observable(grid.td_sf_zd + grid.td_sf_jc + grid.td_jr_zd + grid.td_jr_jc + grid.lk_sf_zd + grid.lk_sf_jc + grid.lk_jr_zd + grid.lk_jr_jc);
}



