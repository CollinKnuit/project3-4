const io = require("socket.io-client");
const socket = io.connect("http://145.24.222.151:8085");

var bankCode = "FLFL";

balance = {
    'body': {
        'pin': '0000',
        'account': '01234567',
    },
    'header': {
        'originCountry': 'DE',
        'originBank': 'FLFL',
        'receiveCountry': 'DE',
        'receiveBank': 'DASB'
    }
}

socket.on("connect", function() {
    console.log("connected to server");
    socket.emit("verbonden", bankCode);
    socket.emit("balance", balance);

    socket.on("balance", function(balanceBody){
        console.log(balanceBody);
    })

    socket.on("response", function(responseBody){
        console.log("Response ontvangen: ");
        console.log(responseBody);
    })


});
