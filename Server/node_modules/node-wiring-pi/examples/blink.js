var wpi = require('../../wiring-pi');

wpi.wiringPiSetupGpio();

console.log(process.env.WIRINGPI_GPIOMEM);

var pin = 17;

wpi.pinMode(pin, wpi.OUTPUT);

var value = 1;

setInterval(function() {
  wpi.digitalWrite(pin, value);
  value = +!value;
}, 50);
