#!/bin/bash

rm ./install.log 2>/dev/null 1>&2

if which dpkg-query 2>/dev/null; then
  dpkg-query -s wiringpi >> install.log

  PKG_OK=$(dpkg-query -W --showformat='${Status}\n' wiringpi|grep "install ok installed")
  echo Checking for wiringpi: $PKG_OK
  if [ "" == "$PKG_OK" ]; then
    echo "Could not find wiringpi package - try 'sudo apt-get install wiringpi'"
    exit 1
    #sudo apt-get --force-yes --yes install wiringpi
  fi
else
  echo "Not on debian based system, asuming you have package."
fi

echo "Building node-wiring-pi ... "
node-gyp clean 2>&1 | tee -a ./install.log
node-gyp configure build 2>&1 | tee -a ./install.log
