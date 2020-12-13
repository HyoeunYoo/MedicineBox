var wpi = require('../../wiring-pi');

wpi.wiringPiSetupGpio();

var rpiInfoArr = wpi.piBoardId();

console.log(rpiInfoArr);
