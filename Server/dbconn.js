
const mysql = require('mysql');


const client = mysql.createConnection({
	host:'mariadb1.cvs2wwdrdvzr.ap-northeast-2.rds.amazonaws.com',
	port:3306,
	user:'rdsuser',
	password:'medicinebox1',
	database:'medicinebox'
});


module.exports = {
    // query : "",
 
	SELECT : function(query, callbackFunc) {
		if(query != "") {
			console.log("query : " + query);
			client.query(query, function(error, results, fields) {
				if(results == null)
					console.log("result is null");
				else{
					console.log(typeof(results));
					// return results;
					callbackFunc(results);
				} 
			})
		} else {
			console.log("Query is EMPTY!");
		}
    },
    
    INSERT : function(query, callbackFunc) {
		if(query != "") {
			console.log("query : " + query);
			client.query(query, function(error, results, fields) {
				if(results != null ){
                    callbackFunc(results);
                }
				else{
                    console.log("insert error");
					console.log(typeof(results));
					// return results;
					//callbackFunc(results);
				} 
			})
		} else {
			console.log("Query is EMPTY!");
		}
    },
    
    UPDATE : function(query, callbackFunc) {
		if(query != "") {
            console.log("query : " + query);
            
			client.query(query, function(error, results, fields) {
				if(results != null ){
                    callbackFunc(results);
                }
				else{
                    console.log("update error");
					console.log(typeof(results));
					// return results;
					//callbackFunc(results);
				} 
			})
		} else {
			console.log("Query is EMPTY!");
		}
	}
}

