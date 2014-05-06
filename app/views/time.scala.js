$(function() {
    var WS = window['MozWebSocket'] ? MozWebSocket : WebSocket
    var dateSocket = new WS("@routes.ApplicationController.timeWs().webSocketURL(request)")

    var receiveEvent = function(event) {
        $("#time").html("Täna on: "+event.data);
    }

    dateSocket.onmessage = receiveEvent
})
