const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const dbconn = require('./dbconn');
// app.use 
app.use(bodyParser.json());


var insertData = { 
	id: "",
	password: ""
}

var sql = "SELECT `user_id`, `user_pwd` FROM `user` WHERE `user_id` = ";

var flag = "";
console.log(typeof(results));
// var i = 0;


const getmember = (req, res) => {
    console.log("Server: POST  데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
	console.log("recieved id : " + req.body.id);
    console.log("recieved pw : " + req.body.password);
	insertData.id = req.body.id;
	insertData.password = req.body.password;

    if(insertData.id != "") {

        query = sql + "\"" + insertData.id + "\";";
        dbconn.SELECT(query, function(results) {
            console.log("results : " + JSON.stringify(results));
            // console.log(i);
            if(results != "") {
                // i++
                // console.log(i);
                console.log("result not null");
                flag = compare(results[0].user_id, results[0].user_pwd);
                res.send(flag);
                console.log("login : " + flag);
            } else {
                res.send(false);
                // i--;
                // console.log(i);
            }
        });
    } else {
        res.send("input ID is null");
    }
}




const compare = (id, pw) => {
	if(id == insertData.id && pw == insertData.password) {
        return true;
        console.log("TRUE");
	} else {
        return false;
        console.log("FALSE");
	}

}




app.post('/login', getmember);

app.listen(65001, () => {
	console.log('login app listening on port 65001');
});


