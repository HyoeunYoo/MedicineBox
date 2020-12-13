var mysql = require('mysql');
var express = require('express');
var bodyParser = require('body-parser');
var app = express();

app.use(bodyParser.json());

var connection = mysql.createConnection({
    host: "mariadb1.cvs2wwdrdvzr.ap-northeast-2.rds.amazonaws.com",
    user: "rdsuser",
    database: "medicinebox",
    password: "medicinebox1",
    port: 3306
});

// 와이파이 정보 입력
app.post('/wifi', function (req, res) {
    console.log("Server: post wifi  데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved device_id : " + req.body.device_id);
    console.log("recieved wifi_id : " + req.body.wifi_id);
    console.log("recieved wifi_pw : " + req.body.wifi_pw);
    var device_id = req.body.device_id;
    var wifi_id = req.body.wifi_id;
    var wifi_pw = req.body.wifi_pw;

    var sql = 'insert into wifi(device_id, wifi_id, wifi_pw) values(?, ?, ?)';
    var params = [device_id, wifi_id, wifi_pw];

    connection.query(sql, params, function (err, result) {
        // console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("wifi data create success");
            res.send(JSON.stringify(true));
        }
    });
});

// 와이파이 정보 가져옴
app.get('/wifi', function (req, res) {
    console.log("Server: get wifi  데이터 받음!");
    console.log("recieved data : " + req.query);
    console.log("recieved device_id : " + req.query.device_id);
    var device_id = req.query.device_id;

    var sql = 'select wifi_id, wifi_pw from wifi where device_id = ?';
    var params = [device_id];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("wifi data read success");
            res.send(JSON.stringify(result));
        }
    });
});


// 와이파이 정보 삭제
app.delete('/wifi', function (req, res) {
    console.log("Server: delete wifi  데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved device_id : " + req.body.device_id);
    var device_id = req.body.device_id;

    var sql = 'delete from wifi where device_id = ?';
    var params = [device_id];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + result);

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("wifi data delete success");
            res.send(true);
        }
    });
});

// 디바이스 IP UPDATE
app.put('/wifi', function (req, res) {
    console.log("Server: update wifi  데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved device_id : " + req.body.device_id);
    var device_id = req.body.device_id;
    var device_ip = req.body.device_ip;

    var sql = 'update wifi set device_ip = ? where device_id = ?';
    var params = [device_ip, device_id];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + result);

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("device_ip in wifi update success");
            res.send(true);
        }
    });
});


//사용자 정보 db에 디바이스 정보 등록
app.post('/account_addDevice', function(req, res) {
    console.log("Server: post user_device  데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved user_id : " + req.body.user_id);
    console.log("recieved user_device : " + req.body.user_device);
    var user_id = req.body.user_id;
    var user_device = req.body.user_device;

    var sql = 'update user set user_device = ? where user_id = ?';
    var params = [user_device, user_id];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + result);
 
        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("user device info update success");
            res.send(true);
        }
    });
})

// 디바이스 IP 
app.get('/deviceIp', function(req, res) {
    console.log("Server: get deviceIp  데이터 받음!");
    console.log("recieved data : " + req.query);
    console.log("recieved device_id : " + req.query.device_id);
    var device_id = req.query.device_id;

    var sql = 'select device_ip from wifi where device_id = ?';
    var params = [device_id];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("wifi data read success");
            res.send(JSON.stringify(result));
        }
    });
})

app.listen(65005, () => {
	console.log('main listening on port 65005');
});