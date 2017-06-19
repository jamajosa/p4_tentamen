//
// ./api/authentication.routes.v1.js
//
var express = require('express');
var router = express.Router();

var auth = require('../auth/authentication');

///////////////////////////////////////////////////////////
//Login
///////////////////////////////////////////////////////////
router.post('/login', function(req, res) {
    console.dir(req.body);

    var username = req.body.username;
    var password = req.body.password;

    db.query('SELECT `email`, `password` FROM `customer` WHERE `email` = "'+username+'" AND password = "'+password+'";', function(error, rows, fields) {
        if (error) {
           console.dir(error);
        } else {
            console.log(rows);
            try{
                var dbUsername = rows[0].email;
                var dbPassword = rows[0].password

            if (username == dbUsername && password == dbPassword) {
                var token = auth.encodeToken(username);
                res.status(200).json({"token": token,});
            } else {
            console.log('Input: username = ' + username + ', password = ' + password);
            res.status(401).json({ "error": "Invalid credentials, bye" })
            }
            } catch(e){
                res.status(401).json({ "error": "Invalid credentials, bye" })
            }
        };
    });



});

///////////////////////////////////////////////////////////
//registreren
///////////////////////////////////////////////////////////
router.post('/register', function(request, response){
console.dir(request.body);

    var customer = request.body;
    var query = {
        sql: 'INSERT INTO `customer`(`email`, `password`) VALUES (?, ?)',
        values: [customer.email, customer.password],
        timeout: 2000 
    };

    console.dir(customer);
    console.log('Onze query: ' + query.sql);

    response.contentType('application/json');
    db.query(query, function(error, rows, fields) {
        if (error) {
            response.status(401).json(error);
        } else {
            response.status(200).json({ result: rows });
        };
    });
});
// Hiermee maken we onze router zichtbaar voor andere bestanden. 
module.exports = router;