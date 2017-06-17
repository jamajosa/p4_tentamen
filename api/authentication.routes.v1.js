//
// ./api/authentication.routes.v1.js
//
var express = require('express');
var router = express.Router();
var db = require('../config/db');
var auth = require('../auth/authentication');

//
// Hier gaat de gebruiker inloggen.
// Input: username en wachtwoord
// ToDo: 
//	 - zoek de username in de database, en vind het password dat opgeslagen is
// 	 - als user gevonden en password matcht, dan return valide token
//   - anders is de inlogpoging gefaald - geef foutmelding terug.
//
router.post('/login', function(req, res) {

    // Even kijken wat de inhoud is
    console.dir(req.body);

    // De username en pwd worden meegestuurd in de request body
    var username = req.body.username;
    var password = req.body.password;

    var query = {
        sql: 'SELECT * FROM customer where first_name=? and password=?',
        values: [username,password],
        timeout: 2000 // 2secs
    };

    db.query(query, function(error, rows, fields) {
        if (error) {
            res.status(401).json(error);
        } else {
            res.status(200).json({ result: rows });
        };
    });

});

// Hiermee maken we onze router zichtbaar voor andere bestanden. 
module.exports = router;