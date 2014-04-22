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

    self.cellMap = {};

    self.loadCalendar = function(calendars) {
        for ( var i = 0; i < calendars.length; i++) {
            var cell = new Cell(calendars[i]);
            self.cells.push(cell);
            self.cellMap[cell.date] = cell;
        }
    }

    self.updateCalendar = function(calendars) {
//        self.cleanDate()
//        self.loadCalendar(calendars);
//        if (self.cells().length == 0) {
//            self.loadCalendar(calendars);
//        }
        for ( var i = 0; i < calendars.length; i ++) {
            var cell0 = new Cell(calendars[i]);
            var cell = self.cellMap[cell0.date];
            if ( cell != null) {
                cell.updateCell(calendars[i]);
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

    self.date = cell.date; // 必须要，上面用date来做来键值
    self.dateStr = ko.observable(cell.date);
//    self.dateStr = ko.observable("<span class=\"badge\">" + cell.dayOfWeek + "</span>" + cell.date);
//    self.zysx = ko.observable(cell.zysx);
    self.td = ko.observable(cell.td);
//    self.lk = ko.observable(cell.lk);
    self.sg = ko.observable(cell.sg);
//    self.hc = ko.observable(cell.hc);
//    self.qt = ko.observable(cell.qt);

    self.updateCell = function(cell) {
//        self.zysx(cell.zysx);
        self.td(cell.td);
//        self.lk(cell.lk);
        self.sg(cell.sg);
//        self.hc(cell.hc);
//        self.qt(cell.qt);
    }
}