var mysql = require('mysql');
var express = require('express');
var bodyParser = require('body-parser');
var app = express();

app.use(bodyParser.json());
// DB에 storage row 생성
app.post('init_quantity', function(req, res) {
    console.log("Server: post init_quantity  데이터 받음!");

})

// 의약품 잔여량 rpi로 받아서 db에 저장
app.post('/quantity', function(req, res) {
    console.log("Server: post quantity  데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved storage : " + req.body.storage);
    console.log("recieved user_device : " + req.body.user_device);

    // var storage = req.body.slot;
    // var storage1 = req.body.slot1;
    // var storage2 = req.body.slot2;
    // var storage3 = req.body.slot3;
    // var storage4 = req.body.slot4;
    // var storage5 = req.body.slot5;
    // var storage6 = req.body.slot6;
    var storage = new Array(req.body.slot1, req.body.slot2, req.body.slot3, req.body.slot4, req.body.slot5, req.body.slot6);

    var user_device = req.body.user_device;

    console.log(typeof(storage));

    for (i = 0 ; i < 6 ; i++) {
        var sql = 'update storage set medi_num = ? where user_id = (select * from user where user_device = ?) and storage_slot = ?';
        var params = [storage[i], user_device, i+1];

        console.log("storage[" + i + "] : " + storage[i])

        connection.query(sql, params, function (err, result) {
            console.log("results : " + result);
    
            if (err) {
                console.log(err);
                res.send(false);
            } else {
                console.log("storage info update success");
                res.send(true);
            }
        });
    }
})


// 와이파이 정보 가져옴
app.get('/quantity', function (req, res) {
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



app.listen(65005, () => {
	console.log('main listening on port 65005');
});