/**
 * Created by star on 4/14/14.
 */

$(function() {
    $('.carousel').carousel({
        interval: false
    })

    var socket = new SockJS('/dashboard/calendar');
    var stompClient = Stomp.over(socket);

//    ko.applyBindings(new ViewModel());

    var appModel = new PanelApplicationModel(stompClient);
    ko.applyBindings(appModel);

    appModel.connect();
});

function PanelApplicationModel(stompClient) {
    var self = this;

    self.username = ko.observable();
    self.panel = ko.observable(new PanelModel());

    self.connect = function() {
        stompClient.connect({}, function(frame) {

            console.log('Connected ' + frame);
            self.username(frame.headers['user-name']);

            stompClient.subscribe("/app/calendars", function(message) {
                self.panel().loadCalendar(JSON.parse(message.body));
            });
            stompClient.subscribe("/topic/calendar.update", function(message) {
                self.panel().updateCalendar(JSON.parse(message.body));
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



function PanelModel() {
    var self = this;

    self.panels = ko.observableArray();

    self.panelMap = {};

    self.loadCalendar = function(calendars) {
        for ( var i = 0; i < calendars.length; i++) {
            var panel = new Panel(i, calendars[i]);
            self.panels.push(panel);
            self.panelMap[panel.date] = panel;
        }
    }

    self.updateCalendar = function(calendars) {
//        self.cleanDate()
//        self.loadCalendar(calendars);
        for ( var i = 0; i < calendars.length; i++) {
            var panel = new Panel(i, calendars[i]);
            var panel = self.panelMap[panel.date];
            if (panel != null) {
                panel.updatePanel(calendars[i])
            }
        }
    }

    self.cleanDate = function() {
        self.panels.removeAll();
        self.panelMap = {};
    }
}

function Panel(num, panel) {
    var self = this;

    self.date = panel.date;
    self.dateStr = ko.observable("<span class=\"badge\">" + panel.dayOfWeek + "</span>" + self.date);
    self.num = num;
    self.index = ko.observable(num);
    self.isActive = ko.computed(function() {
        return self.num == 0? "active" : "";
    })
    self.zysx = ko.observable(panel.zysx);
    self.td = ko.observable(panel.td);
    self.lk = ko.observable(panel.lk);
    self.sg = ko.observable(panel.sg);
    self.hc = ko.observable(panel.hc);
    self.qt = ko.observable(panel.qt);

    self.updatePanel = function(panel) {
        self.zysx(panel.zysx);
        self.td(panel.td);
        self.lk(panel.lk);
        self.sg(panel.sg);
        self.hc(panel.hc);
        self.qt(panel.qt);
    }
}