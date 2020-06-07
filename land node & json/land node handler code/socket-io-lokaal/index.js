//ook belangrijk hiermee maak je de objecten aan voor al je functies enzo
var express = require('express');
var socket = require('socket.io');

//account informatie om te checken
var pin= '1';
var account = '1'


//Response json objects
var withdrawResponse = {
  'body': {
    'code': 200,
    'message': 'Success'
  },
  'header': {
    'originCountry':'DE',
    'originBank':'DE',
    'receiveCountry':'DE',
    'receiveBank':'DE'
  }
}

//deze als het helemaal fout gaat
var withdrawResponseError = {
  'body': {
    'code': 404,
    'message': 'je hebt error man'
  },
  'header': {
    'originCountry':'DE',
    'originBank':'DE',
    'receiveCountry':'DE',
    'receiveBank':'DE'
  }
}

// App setup ik weet niet wat dit doet maar zonder deze gaat die ding niet werken
var app = express();
var server = app.listen(4000, function(){
    console.log('listening for requests on port 4000,');
});

// hiermee gaat de website werken dat is alles wat ik ervan weet man
app.use(express.static('public'));

// Socket setup & pass server // ja dit moet je ook gewoon doen maar waarom idfk
var io = socket(server);
io.on('connection', (socket) => {

    console.log('made socket connection', socket.id);

    // Handle chat event
    socket.on('withdraw', function(data){
        // console.log(data);
        //check of de pincode en account klopt en of het binnen duitsland moet komen
        if(data.body.pin == pin && data.body.account == account){
          //io emit te sturen
        io.sockets.emit('withdraw', withdrawResponse);
        //last resort error code sturen als je er niks mee kan
      }else {io.sockets.emit('withdraw', withdrawResponseError);}
    });



});
