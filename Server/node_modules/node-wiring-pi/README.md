##Node.js bindings to [wiringPi](http://www.wiringpi.com)

node-wiring-pi is based on https://github.com/WiringPi/WiringPi-Node

It differs from the original project by:

* Using a vanilla WiringPi.com install, rather than a customised build.  
This version of the node bindings will be missing features that have been added to https://github.com/WiringPi/WiringPi-Node.

* Using [NAN](https://github.com/nodejs/nan) to provide Node version abstraction
Hopefully this makes it more resilient against Node API changes

This should mean that installation is simpler, and supports npm install -g (global mode).

## Install

Make sure you have [WiringPi](http://wiringpi.com/download-and-install/) installed.  On Raspbian Jessie, this should be as easy as:

```
sudo apt-get install wiringpi
```

See the [WiringPi](http://wiringpi.com/download-and-install/) link for more options and details.

To install this node module:

```
npm install node-wiring-pi
```

## Usage

```javascript
var wpi = require('node-wiring-pi');
```

## TODO

* Documentation!
* Review export constants
* Add NAN_MODULE_INIT signatures to .h files
* Update build script - rebuild or configure build?
* Add devlib / extensions
* wiringPiISR vs epoll documentation (#3)
* Add util to replace gpio (read config and set pins as root)


