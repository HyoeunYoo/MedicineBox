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

// 가입
app.post('/signup', function (req, res) {
    console.log("Server: signup  데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved id : " + req.body.id);
	console.log("recieved name : " + req.body.name);
    console.log("recieved pw : " + req.body.password);
    console.log("recieved phone : " + req.body.phone);
    var userId = req.body.id;
    var userName = req.body.name;
    var userPwd = req.body.password;
    var userPhone = req.body.phone;

 
    var sql = 'INSERT INTO user (user_id, user_name, user_pwd, user_phone) VALUES (?, ?, ?, ?)';
    var params = [userId, userName, userPwd, userPhone];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log("Error" + err);
            res.send(false);
        } else if (result.affectedRows > 0) {
            console.log("insert success");
            res.send(true);
        } else {
            res.send(false);
        }
    });
});


// 로그인
app.post('/login', function (req, res) {
    console.log("Server: login  데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved id : " + req.body.id);
    console.log("recieved pw : " + req.body.password);
    var userId = req.body.id;
    var userPwd = req.body.password;

    var sql = 'select user_pwd from user where user_id = ?';
    var params = [userId];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
        } else {
            if(result[0].user_pwd == userPwd) {
                console.log("pwd compare success");
                res.send(true);
            } else {
                res.send(false);
            }
        }
    });
});

// 정보수정 비번 체크
app.post('/accountchk', function (req, res) {
    console.log("Server: post account  데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved id : " + req.body.id);
    console.log("recieved name : " + req.body.name);
    console.log("recieved name : " + req.body.oldpwd);
    console.log("recieved newpwd : " + req.body.newpwd);
    console.log("recieved phone : " + req.body.phone);
    var userId = req.body.id;
    var userOldpwd = req.body.oldpwd;

    var sql = 'select user_pwd from user where user_id = ?';
    var params = [userId];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
        } else {
            if(result[0].user_pwd == userOldpwd) {
                console.log("pwd compare success");
                res.send(true);
            } else {
                res.send(false);
            }
        }
    });
});

// 정보수정
app.put('/account', function (req, res) {
    console.log("Server: put account  데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved id : " + req.body.id);
    console.log("recieved name : " + req.body.name);
    console.log("recieved newpwd : " + req.body.newpwd);
    console.log("recieved phone : " + req.body.phone);
    var userId = req.body.id;
    var userName = req.body.name;
    var userNewpwd = req.body.newpwd;
    var userPhone = req.body.phone;

    var sql = 'update user set user_name = ?, user_pwd = ?, user_phone = ? where user_id = ?';
    var params = [userName, userNewpwd, userPhone, userId];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
        } else {
            if(result.affectedRows > 0) {
                console.log("update success");
                res.send(true);
            } else {
                res.send(false);
            }
        }
    });
});

// 정보 로드
app.post('/accountload', function (req, res) {
    console.log("Server: post account  데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved id : " + req.body.id);
    var userId = req.body.id;

    var sql = 'select user_name, user_phone from user where user_id = ?';
    var params = [userId];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("load success");
            res.send(JSON.stringify(result));
        }
    });
});

// 탈퇴
app.delete('/leave', function (req, res) {
    console.log("Server: delete leave 데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved id : " + req.body.id);
    var userId = req.body.id;
    var userPwd = req.body.password;

    var sql = 'select user_pwd from user where user_id = ?';
    var params = [userId];

    connection.query(sql, params, function (err, result) {
        console.log("leave select result : " + JSON.stringify(result));
        
        if (err){
            console.log(err);
            res.send(false);
        } else if (result[0].user_pwd == userPwd) {
            console.log("pwd compare success");
            
            var sql2 = 'DELETE FROM user WHERE user_id = ?';
            var params2 = [userId];

            connection.query(sql2, params2, function (err, result) {
                console.log("leave delete result : " + JSON.stringify(result));
                
                if (err){
                    console.log(err);
                    res.send(false);
                } else if (result.affectedRows > 0) {
                    console.log("delete success");       
                    res.send(true);
                } else {
                    res.send(false);
                }
            });

        } else {
            res.send(false);
        }
    });
});

// 검색
app.post('/search', function (req, res) {
    console.log("Server: post search  데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved word : " + req.body.search);
    var word = req.body.search;

    var sql = 'select medi_name from medi where medi_name like "%' + word + '%" ' ;
    //var params = [req.body.search];

    connection.query(sql, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("search success");
            res.send(JSON.stringify(result));
        }
    });
});

// 검색 수 조회
app.post('/searchload', function (req, res) {
    console.log("Server: post search load 데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved name : " + req.body.name);
    var mediName = req.body.name;

    var sql = 'select medi_search from medi where medi_name = ?'
    var params = [mediName];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("search load success");
            res.send(JSON.stringify(result));
        }
    });
});

// 검색 수 증가
app.post('/searchadd', function (req, res) {
    console.log("Server: post search add 데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved num : " + req.body.num);
    console.log("recieved name : " + req.body.name);
    var searchNum = req.body.num;
    var mediName = req.body.name;

    var sql = 'update medi set medi_search = ? where medi_name= ?'
    var params = [searchNum, mediName];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            if(result.affectedRows > 0) {
                console.log("search add success");
                res.send(true);
            } else {
                res.send(false);
            }
        }
    });
});

// 보관의약품 추가 시 none 조회
app.post('/noneload', function (req, res) {
    console.log("Server: post none load 데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved name : " + req.body.name);
    var noneName = req.body.name;

    var sql = 'select * from none where none_name = ?'
    var params = [noneName];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("none load success");
            res.send(JSON.stringify(result));
        }
    });
});

// 사용자 검색어 추가 - 검색 시
app.post('/nonesearchadd', function (req, res) {
    console.log("Server: post none search add 데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved name : " + req.body.name);
    var noneName = req.body.name;

    var sql = 'insert into none(none_name, none_search) values(?, ?)';
    var params = [noneName, 1];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
        } else {
            if(result.affectedRows > 0) {
                console.log("none search add success");
                res.send(true);

            } else {
                res.send(false);
            }
        }
    });
});

// 사용자 검색어의 검색 수 증가
app.post('/nonesearchup', function (req, res) {
    console.log("Server: post none search up 데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved num : " + req.body.num);
    console.log("recieved name : " + req.body.name);
    var searchNum = req.body.num;
    var noneName = req.body.name;

    var sql = 'update none set none_search = ? where none_name = ?';
    var params = [searchNum, noneName];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
        } else {
            if(result.affectedRows > 0) {
                console.log("none search up success");
                res.send(true);

            } else {
                res.send(false);
            }
        }
    });
});

// 검색결과, 보관의약품 상세조회
app.post('/mediname', function (req, res) {
    console.log("Server: post mediname  데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved name : " + req.body.name);
    var mediName = req.body.name;

    var sql = 'select * from medi where medi_name = ?';
    var params = [mediName];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("medi name success");
            res.send(JSON.stringify(result));
        }
    });
});

// 보관의약품 상세조회 - 복용타입, 복용횟수
app.post('/takedetail', function (req, res) {
    console.log("Server: post takedatail 데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved id : " + req.body.id);
    console.log("recieved num : " + req.body.num);
    var userId = req.body.id;
    var mediNum = req.body.num;

    var sql = 'select * from take where user_id=? && medi_num=?';
    var params = [userId, mediNum];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("take detail success");
            res.send(JSON.stringify(result));
        }
    });
});

// 보관의약품 상세조회 - 복용 요일
app.post('/takeday', function (req, res) {
    console.log("Server: post takeday 데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved id : " + req.body.id);
    console.log("recieved num : " + req.body.num);
    var userId = req.body.id;
    var mediNum = req.body.num;

    var sql = 'select distinct(take_day) from take where user_id=? && medi_num=?';
    var params = [userId, mediNum];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("take day success");
            res.send(JSON.stringify(result));
        }
    });
});

// 보관의약품 상세조회 - 복용 시작날짜, 주기
app.post('/takecycle', function (req, res) {
    console.log("Server: post takecycle 데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved id : " + req.body.id);
    console.log("recieved num : " + req.body.num);
    var userId = req.body.id;
    var mediNum = req.body.num;

    var sql = 'select take_start, take_cycle from take where user_id=? && medi_num=?';
    var params = [userId, mediNum];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("take cycle success");
            res.send(JSON.stringify(result));
        }
    });
});

// 보관의약품 상세조회 - 복용 시간
app.post('/taketime', function (req, res) {
    console.log("Server: post taketime 데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved id : " + req.body.id);
    console.log("recieved num : " + req.body.num);
    var userId = req.body.id;
    var mediNum = req.body.num;

    var sql = 'select distinct(take_time) from take where user_id=? && medi_num=?';
    var params = [userId, mediNum];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("take time success");
            res.send(JSON.stringify(result));
        }
    });
});

// 보관의약품 상세조회 - 복용정보 - 사용기한
app.post('/expireload', function (req, res) {
    console.log("Server: post expireload  데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved slot : " + req.body.slot);
    var mediSlot = req.body.slot;

    var sql = 'select storage_num, storage_expire from storage where storage_slot = ?';
    var params = [mediSlot];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("expire load success");
            res.send(JSON.stringify(result));
        }
    });
});

// 보관의약품 로드1
app.post('/takeload', function (req, res) {
    console.log("Server: post takeload  데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved id : " + req.body.id);
    console.log("recieved day : " + req.body.day);
    var userId = req.body.id;
    var takeDay = req.body.day;

    var sql = 'select medi_num from take where user_id = ? && take_day= ?';
    var params = [userId, takeDay];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("take load success");
            res.send(JSON.stringify(result));
        }
    });
});

// 보관의약품 로드2 - 요일별 시간
app.post('/timeload', function (req, res) {
    console.log("Server: post timeload  데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved id : " + req.body.id);
    console.log("recieved day : " + req.body.day);
    var userId = req.body.id;
    var takeDay = req.body.day;

    var sql = 'select take_time from take where user_id = ? && take_day= ?';
    var params = [userId, takeDay];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("time load success");
            res.send(JSON.stringify(result));
        }
    });
});

// 보관의약품 로드3 - 주기별 시간
app.post('/cycleload', function (req, res) {
    console.log("Server: post cycle load  데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved id : " + req.body.id);
    var userId = req.body.id;

    var sql = 'select medi_num, take_start, take_cycle, take_time from take where user_id= ? & take_start is not null;';
    var params = [userId];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("cycle load success");
            res.send(JSON.stringify(result));
        }
    });
});

// 보관의약품 로드4
app.post('/storeload', function (req, res) {
    console.log("Server: post storeload  데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved id : " + req.body.id);
    var userId = req.body.id;

    var sql = 'select * from storage where user_id = ?';
    var params = [userId];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("store load success");
            res.send(JSON.stringify(result));
        }
    });
});

// 보관의약품 로드5
app.post('/mediload', function (req, res) {
    console.log("Server: post mediload  데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved num : " + req.body.num);
    var mediNum = req.body.num;

    var sql = 'select medi_name, medi_photo from medi where medi_num = ?';
    var params = [mediNum];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("medi load success");
            res.send(JSON.stringify(result));
        }
    });
});

// 보관의약품 조회 시 none 조회
app.post('/nonenameload', function (req, res) {
    console.log("Server: post none name load 데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved num : " + req.body.num);
    var noneNUm = req.body.num;

    var sql = 'select * from none where none_num = ?'
    var params = [noneNUm];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("none name load success");
            res.send(JSON.stringify(result));
        }
    });
});

// 보관의약품 오늘 시간
app.post('/todaytake', function (req, res) {
    console.log("Server: post todaytake 데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved id : " + req.body.id);
    console.log("recieved num : " + req.body.num);
    var userId = req.body.id;
    var mediNum = req.body.num;

    var sql = 'select distinct(take_time) from take where user_id = ? && medi_num = ?';
    var params = [userId, mediNum];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("today take1 success");
            res.send(JSON.stringify(result));
        }
    });
});

// 보관의약품 수정 - take num
app.post('/takenumload', function (req, res) {
    console.log("Server: post takenumload  데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved id : " + req.body.id);
    console.log("recieved num : " + req.body.num);
    var userId = req.body.id;
    var mediNum = req.body.num;

    var sql = 'select take_num from take where user_id = ? && medi_num = ?';
    var params = [userId, mediNum];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("take num load success");
            res.send(JSON.stringify(result));
        }
    });
});

// 보관의약품 수정 - 삭제 먼저
app.delete('/takeeditdelete', function (req, res) {
    console.log("Server: take edit delete  데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved take_num : " + req.body.num);
    var takeNum = req.body.num;

    var sql = 'delete from take where take_num = ?';
    var params = [takeNum];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + result);

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("take edit delete success");
            res.send(true);
        }
    });
});

// 보관의약품 추가
app.post('/storageadd', function (req, res) {
    console.log("Server: post storage add 데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved id : " + req.body.id);
    console.log("recieved mediNum : " + req.body.mediNum);
    console.log("recieved slotNum : " + req.body.slotNum);
    console.log("recieved expire : " + req.body.expire);
    var userId = req.body.id;
    var mediNum = req.body.mediNum;
    var slotNum = req.body.slotNum;
    var expire = req.body.expire;

    var sql = 'insert into storage(user_id, medi_num, storage_slot, storage_expire) values(?, ?, ?, ?)';
    var params = [userId, mediNum, slotNum, expire];

    var sql2 = 'select storage_num from storage where user_id = ? && storage_slot = ?';
    var params2 = [userId, slotNum];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
        } else {
            if(result.affectedRows > 0) {
                console.log("storage insert success");

                connection.query(sql2, params2, function (err, result) {
                    console.log("results : " + JSON.stringify(result));
            
                    if (err) {
                        console.log(err);
                        res.send(false);
                    } else {
                        console.log("storage num load success");
                        res.send(JSON.stringify(result));
                    }
                });
            } else {
                res.send(false);
            }
        }
    });
});

// 보관의약품 추가 - 요일별
app.post('/takedayadd', function (req, res) {
    console.log("Server: post take day add 데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved id : " + req.body.id);
    console.log("recieved mediNum : " + req.body.mediNum);
    console.log("recieved storageNum : " + req.body.storageNum);
    console.log("recieved type : " + req.body.type);
    console.log("recieved day : " + req.body.day);
    console.log("recieved fre : " + req.body.fre);
    console.log("recieved time : " + req.body.time);
    var userId = req.body.id;
    var mediNum = req.body.mediNum;
    var storageNum = req.body.storageNum;
    var takeType = req.body.type;
    var takeDay = req.body.day;
    var takeFre = req.body.fre;
    var takeTime = req.body.time;

    var sql = 'insert into take(user_id, medi_num, storage_num, take_type, take_day, take_fre, take_time) values(?, ?, ?, ?, ?, ?, ?)';
    var params = [userId, mediNum, storageNum, takeType, takeDay, takeFre, takeTime];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
        } else {
            if(result.affectedRows > 0) {
                console.log("day insert success");
                res.send(true);
            } else {
                res.send(false);
            }
        }
    });
});

// 보관의약품 추가 - 주기별
app.post('/takecycleadd', function (req, res) {
    console.log("Server: post take cycle add 데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved id : " + req.body.id);
    console.log("recieved mediNum : " + req.body.mediNum);
    console.log("recieved storageNum : " + req.body.storageNum);
    console.log("recieved type : " + req.body.type);
    console.log("recieved start : " + req.body.start);
    console.log("recieved cycle : " + req.body.cycle);
    console.log("recieved fre : " + req.body.fre);
    console.log("recieved time : " + req.body.time);
    var userId = req.body.id;
    var mediNum = req.body.mediNum;
    var storageNum = req.body.storageNum;
    var takeType = req.body.type;
    var takeStart = req.body.start;
    var takeCycle = req.body.cycle;
    var takeFre = req.body.fre;
    var takeTime = req.body.time;

    var sql = 'insert into take(user_id, medi_num, storage_num, take_type, take_start, take_cycle, take_fre, take_time) values(?, ?, ?, ?, ?, ?, ?, ?)';
    var params = [userId, mediNum, storageNum, takeType, takeStart, takeCycle, takeFre, takeTime];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
        } else {
            if(result.affectedRows > 0) {
                console.log("cycle update success");
                res.send(true);
            } else {
                res.send(false);
            }
        }
    });
});

// 의약품 보관 수 증가
app.post('/medistoreup', function (req, res) {
    console.log("Server: post medi store up 데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved num : " + req.body.num);
    var mediNum = req.body.num;

    var sql = 'update medi set medi_store = medi_store + 1 where medi_num = ?';
    var params = [mediNum];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
        } else {
            if(result.affectedRows > 0) {
                console.log("medi store up success");
                res.send(true);

            } else {
                res.send(false);
            }
        }
    });
});

// 사용자 검색어 추가 - 보관의약품 추가 시
app.post('/nonestoreadd', function (req, res) {
    console.log("Server: post none store add 데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved num : " + req.body.num);
    console.log("recieved name : " + req.body.name);
    var noneNum = req.body.num;
    var noneName = req.body.name;

    var sql = 'insert into none(none_num, none_name, none_store) values(?, ?, ?)';
    var params = [noneNum, noneName, 1];

    var sql2 = 'select * from none where none_name = ?';
    var params2 = [noneName];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
        } else {
            if(result.affectedRows > 0) {
                console.log("none store add success");

                connection.query(sql2, params2, function (err, result) {
                    console.log("results : " + JSON.stringify(result));
            
                    if (err) {
                        console.log(err);
                    } else {
                        console.log("none store select success");
                        res.send(JSON.stringify(result));
                    }
                });

            } else {
                res.send(false);
            }
        }
    });
});

// 사용자 검색어의 보관 수 증가
app.post('/nonestoreup', function (req, res) {
    console.log("Server: post none store up 데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved num : " + req.body.num);
    console.log("recieved name : " + req.body.name);
    var storeNum = req.body.num;
    var noneName = req.body.name;

    var sql = 'update none set none_store = ? where none_name = ?';
    var params = [storeNum, noneName];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
        } else {
            if(result.affectedRows > 0) {
                console.log("none search up success");
                res.send(true);

            } else {
                res.send(false);
            }
        }
    });
});

// 보관의약품 수정 - 사용기한
app.put('/storageedit', function (req, res) {
    console.log("Server: put storage  데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved id : " + req.body.id);
    console.log("recieved slot : " + req.body.slot);
    console.log("recieved expire : " + req.body.expire);
    var userId = req.body.id;
    var mediSlot = req.body.slot;
    var mediExpire = req.body.expire;

    var sql = 'update storage set storage_expire = ? where user_id = ? && storage_slot = ?';
    var params = [mediExpire, userId, mediSlot];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
        } else {
            if(result.affectedRows > 0) {
                console.log("expire update success");
                res.send(true);
            } else {
                res.send(false);
            }
        }
    });
});

// 보관 의약품 추가 시 의약품명 자동 검색
app.get('/listload', function (req, res) {
    console.log("Server: get list load 데이터 받음!");

    var sql = 'select * from medi';

    connection.query(sql, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("list load success");
            res.send(JSON.stringify(result));
        }
    });
});

// 보관의약품 삭제 - take
app.delete('/takedelete', function (req, res) {
    console.log("Server: take delete  데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved medi_num : " + req.body.num);
    var mediNum = req.body.num;

    var sql = 'delete from take where medi_num = ?';
    var params = [mediNum];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + result);

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("take delete success");
            res.send(true);
        }
    });
});

// 보관의약품 삭제 - storage
app.delete('/storagedelete', function (req, res) {
    console.log("Server: storage delete  데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved medi_num : " + req.body.num);
    var mediNum = req.body.num;

    var sql = 'delete from storage where medi_num = ?';
    var params = [mediNum];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + result);

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("storage delete success");
            res.send(true);
        }
    });
});

// 알람 로드
app.post('/alarmload', function (req, res) {
    console.log("Server: post alarmload  데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved id : " + req.body.id);
    var userId = req.body.id;

    var sql = 'select user_alarm from user where user_id = ?';
    var params = [userId];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
            res.send(false);
        } else {
            console.log("alarm load success");
            res.send(JSON.stringify(result));
        }
    });
});


//알람 수정
app.put('/alarmedit', function (req, res) {
    console.log("Server: put alarm  데이터 받음!");
    console.log("recieved data : " + JSON.stringify(req.body));
    console.log("recieved id : " + req.body.id);
    console.log("recieved alarm : " + req.body.alarm);
    var userId = req.body.id;
    var userAlarm = req.body.alarm;

    var sql = 'update user set user_alarm = ? where user_id = ?';
    var params = [userAlarm, userId];

    connection.query(sql, params, function (err, result) {
        console.log("results : " + JSON.stringify(result));

        if (err) {
            console.log(err);
        } else {
            if(result.affectedRows > 0) {
                console.log("update success");
                res.send(true);
            } else {
                res.send(false);
            }
        }
    });
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

app.listen(65004, () => {
	console.log('main listening on port 65004');
});
