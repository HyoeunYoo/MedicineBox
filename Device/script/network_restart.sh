

loopNum=0
while [ true ]; do
    command=$(iwconfig wlan0 | grep ESSID)
    n_state=$(echo "${command#*:}" | tr -d '[:space:]')

    if [ $n_state = "off/any" ]; then
        if [ $loopNum -gt 10 ]; then
            echo "ERROR : TIMEOUT!"
            break
        fi
        loopNum=`expr $loopNum + 1`
        echo "TRYING : $loopNum"
        echo "무선 네트워크 재시작"
        sudo ifconfig wlan0 down
        echo "무선 네트워크 멈춤"
        sleep 1
        sudo ifconfig wlan0 up 
        echo "무선 네트워크 시작"
        sleep 10
    else
        echo "무선 네트워크 연결됨"
        break
    fi
done


