/**
 * Created by star on 4/14/14.
 */

$(function() {
    var socket = new SockJS('/dashboard/calendar');
    var stompClient = Stomp.over(socket);

    var appModel = new ApplicationModel(stompClient);
    ko.applyBindings(appModel);

    appModel.connect();
});


function ApplicationModel(stompClient) {
    var self = this;

    self.username = ko.observable();
    self.calendar = ko.observable(new CalendarModel());
    self.dayindex = ko.observable(new IndexModel());

    $.ajax({
        url: "/dashboard/calendar/list",
        async: false,
        type: "GET",
        success: self.calendar().loadCalendar
    });

    $.ajax({
        url: "/dashboard/calendar/days",
        async: false,
        type: "GET",
        success: self.dayindex().loadDays
    });

    self.connect = function() {
        stompClient.connect({}, function(frame) {

            console.log('Connected ' + frame);
            self.username(frame.headers['user-name']);

            /*stompClient.subscribe("/app/calendars", function(message) {
             self.calendar().loadCalendar(JSON.parse(message.body));
             });*/
//            $.get("/dashboard/calendar/list", self.calendar().loadCalendar);

            stompClient.subscribe("/topic/calendar.update", function(message) {
                self.calendar().updateCalendar(JSON.parse(message.body));
            });
//            stompClient.subscribe("/user/queue/position-updates", function(message) {
//                self.pushNotification("Position update " + message.body);
//                self.portfolio().updatePosition(JSON.parse(message.body));
//            });
            stompClient.subscribe("/user/queue/errors", function(message) {
                console.log("Error " + message.body);
            });
        }, function(error) {
            console.log("STOMP protocol error " + error);
        });
    }
}



function CalendarModel() {
    var self = this;

    self.cells = ko.observableArray();

    self.today = ko.observable();

    self.tomorrow = ko.observable();

    self.cellMap = {};

    self.loadCalendar = function(calendars) {
        for ( var i = 0; i < calendars.length; i++) {
            var cell = new Cell(calendars[i]);
            if (i == 0) {
                self.today = cell;
            } else if (i == 1) {
                self.tomorrow = cell;
            } else {
                self.cells.push(cell);
                self.cellMap[cell.date] = cell;
            }
        }
    }

    self.updateCalendar = function(calendars) {
//        self.cleanDate()
//        self.loadCalendar(calendars);
//        if (self.cells().length == 0) {
//            self.loadCalendar(calendars);
//        }
        for ( var i = 0; i < calendars.length; i ++) {

            if ( i ==0 ) {
                self.today.updateCell(calendars[i]);
            } else if(i == 1) {
                self.tomorrow.updateCell(calendars[i]);
            } else {
                var cell0 = new Cell(calendars[i]);
                var cell = self.cellMap[cell0.date];
                if ( cell != null) {
                    cell.updateCell(calendars[i]);
                }
            }
        }
    }

    self.cleanDate = function() {
        self.cells.removeAll();
        self.cellMap = {};
    }
}

function IndexModel() {
    var self = this;

    self.days = ko.observableArray();

    self.loadDays = function(dates) {
        for(var i = 0; i < dates.length; i ++) {
            var day = new Day(i, dates[i]);
            self.days.push(day);
        }
    }
}


function Day(index, date) {
    var self = this;

    self.date = date;
    self.day = ko.observable(moment(date).date());
    self.index = index;
    self.isToday = ko.computed(function() {
        return self.index == 0? "btn btn-default margin-top-5" : "btn btn-default margin-top-5";
    })
}

function Cell(cell) {
    var self = this;

    self.cell = cell;
    self.date = cell.date; // 必须要，上面用date来做来键值
    self.dateStr = ko.observable(cell.date);
//    self.dateStr = ko.observable("<span class=\"badge\">" + cell.dayOfWeek + "</span>" + cell.date);
    self.zysx = ko.observable(cell.zysx);
    self.zysxfa = ko.computed(function() {
        return cell.zysx > 0? "red_number fa-2x": "fa-2x";
    });
    self.td = ko.observable(cell.td);
    self.tdfa = ko.computed(function() {
        return cell.td > 0? "red_number fa-2x": "fa-2x";
    });
    self.lk = ko.observable(cell.lk);
    self.lkfa = ko.computed(function() {
        return cell.lk > 0? "red_number fa-2x": "fa-2x";
    });
    self.sg = ko.observable(cell.sg);
    self.sgfa = ko.computed(function() {
        return cell.sg > 0? "red_number fa-2x": "fa-2x";
    });
    self.hc = ko.observable(cell.hc);
    self.hcfa = ko.computed(function() {
        return cell.hc > 0? "red_number fa-2x": "fa-2x";
    });
    self.qt = ko.observable(cell.qt);
    self.qtfa = ko.computed(function() {
        return cell.qt > 0? "red_number fa-2x": "fa-2x";
    });
    self.td_sj = ko.observable(cell.td_sj);
    self.td_sjfa = ko.computed(function() {
        return cell.td_sj > 0? "red_number fa-2x": "fa-2x"
    });
    self.hc_sj = ko.observable(cell.hc_sj);
    self.hc_sjfa = ko.computed(function() {
        return cell.hc_sj > 0? "red_number fa-2x": "fa-2x";
    })
;
    self.updateCell = function(cell) {
        self.date = cell.date;
        self.dateStr = ko.observable(cell.date);
        self.zysx(cell.zysx);
        self.td(cell.td);
        self.lk(cell.lk);
        self.sg(cell.sg);
        self.hc(cell.hc);
        self.qt(cell.qt);
        self.td_sj(cell.td_sj);
        self.hc_sj(cell.hc_sj);

        if(self.cell.zysx != cell.zysx
            || self.cell.td != cell.td
            || self.cell.lk != cell.lk
            || self.cell.sg != cell.sg
            || self.cell.hc != cell.hc
            || self.cell.qt != cell.qt
            || self.cell.td_sj != cell.td_sj
            || self.cell.hc_sj != cell.hc_sj) {
            show(cell.date);
        }
    }
}