{
  'targets': [
    {
      'target_name': 'nodeWiringPi',
      'sources': [
        'src/addon.cc',
        
        'src/wiringPi.cc',
        'src/softPwm.cc',
        'src/softTone.cc',
        'src/wiringPiI2C.cc',
        'src/wiringPiISR.cc',
        'src/wiringPiSPI.cc',
        'src/wiringSerial.cc',
        'src/wiringShift.cc',

        'src/nodeWiringPi.cc'

      ],
      'include_dirs': [
        'wiringPi/wiringPi',
        'wiringPi/devLib',
	'<!(node -e \"require(\'nan\')\")'
      ],
      'libraries': [
        '-lwiringPi',
        '-lwiringPiDev'
      ],
      'cflags': [
        '-Wall',
        '-std=c++11',

      ],
      'conditions': [
        [ 'OS=="mac"', {
          'xcode_settings': {
            'OTHER_CPLUSPLUSFLAGS' : [ '-std=c++11', '-stdlib=libc++'],
            'MACOSX_DEPLOYMENT_TARGET': '10.9'
          }
        }],
      ],
    }
  ]
}
