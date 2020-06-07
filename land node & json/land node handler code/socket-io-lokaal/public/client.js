// hiermee kies je waar je socket gaat verbinden
var socket = io.connect('http://localhost:4000');

// Query DOM // aka alle variabelen die je op je html gebruikt waarmee je kan communiceren
var      pin = document.getElementById('pin');
var      account = document.getElementById('account');
var      amount = document.getElementById('amount');
var      btn = document.getElementById('send');
var      output = document.getElementById('output');

//withdraw objects
var withdraw ={
  'body':
  {
    'account': account.value,
    'amount': amount.value,
    'pin': pin.value
  },
  'header':
  {
    'originCountry': 'DE',
    'receiveCountry': 'DE',
    'originBank': 'DE',
    'receiveBank': 'DE'
  }
}

// Emit events
btn.addEventListener('click', function(){
    socket.emit('withdraw',withdraw);
    account.value = "";
    amount.value = "";
    pin.value = "";
});


// Listen for events
socket.on('withdraw', function(data){
//  if(typeof data === 'object')
  //  {
      if(data.body.code == 200)
        {//het is gelukt
          output.innerHTML += '<p>' + data.body.message + '</p>'
        }
      if(data.body.code == 404)
        {//het is gelukt maar andersom
            output.innerHTML += '<p>' + data.body.message + '</p>'
        }
    } else {output.innerHTML += '<p>wtf hoe dan</p>'}
});
