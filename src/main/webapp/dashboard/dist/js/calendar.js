/**
 * Created by star on 4/14/14.
 */

function ApplicationModel(stompClient) {
    var self = this;

    self.username = ko.observable();
    self.calendar = ko.observable(new CalendarModel());

    self.connect = function() {
        stompClient.connect({}, function(frame) {

            console.log('Connected ' + frame);
            self.username(frame.headers['user-name']);

            stompClient.subscribe("/app/calendars", function(message) {
                self.calendar().loadCalendar(JSON.parse(message.body));
            });
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
        self.cellMap = {};
        self.loadCalendar(calendars);
    }
}

function Cell(cell) {
    var self = this;

    self.date = ko.observable(cell.date);
    self.zysx = ko.observable(cell.zysx);
    self.td = ko.observable(cell.td);
    self.lk = ko.observable(cell.lk);
    self.sg = ko.observable(cell.sg);
    self.hc = ko.observable(cell.hc);
    self.qt = ko.observable(cell.qt);

    self.updateCell = function(cell) {
        self.date(cell.date);
        self.zysx(cell.zysx);
        self.td(cell.td);
        self.lk(cell.lk);
        self.sg(cell.sg);
        self.hc(cell.hc);
        self.qt(cell.qt);
    }
}