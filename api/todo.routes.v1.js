//
// ./api/todo.routes.v1.js
//
var express = require('express');
var routes = express.Router();
var db = require('../config/db');

///////////////////////////////////////////////////////////
//startgetal en counter om aantal films op te halen
///////////////////////////////////////////////////////////
routes.get('/fromCountfilms/:start/:number', function(req, res) {

    var start = parseInt(req.params.start);
    var number = parseInt(req.params.number) + start;
    var query = {
        sql: 'SELECT * FROM `film` ORDER BY film_id ASC LIMIT ? ,?',
        values: [start,number],
        timeout: 2000 // 2secs
    };

    console.log('Nummers ' + start, "    " ,number);

    res.contentType('application/json');
    db.query(query, function(error, rows, fields) {
        if (error) {
            res.status(401).json(error);
        } else {
            res.status(200).json({ result: rows });
        };
    });
});

///////////////////////////////////////////////////////////
//get all movies
///////////////////////////////////////////////////////////
routes.get('/allfilms', function(req, res) {
    var query = {
        sql: 'SELECT * FROM `film`',
        timeout: 2000 // 2secs
    };
    console.log('Onze query: ' + query.sql);
    res.contentType('application/json');
    db.query(query, function(error, rows, fields) {
        if (error) {
            res.status(401).json(error);
        } else {
            res.status(200).json({ result: rows });
        };
    });
});


///////////////////////////////////////////////////////////
//get all movies with :filmid
///////////////////////////////////////////////////////////
routes.get('/films/:filmid', function(req, res) {
    var ID = req.params.filmid;
    var query = {
        sql: 'SELECT * FROM `film` WHERE film_id=?',
        values: [ID],
        timeout: 2000 // 2secs
    };
    console.log('Onze query: ' + query.sql);
    res.contentType('application/json');
    db.query(query, function(error, rows, fields) {
        if (error) {
            res.status(401).json(error);
        } else {
            res.status(200).json({ result: rows });
        };
    });
});

///////////////////////////////////////////////////////////
//rental gegevens getten op basisch van een user
///////////////////////////////////////////////////////////
routes.get('/rentals/:userids', function(req, res) {
    var ID = req.params.userids;
    var query = {
        sql: 'SELECT f.title, f.description,f.rating FROM rental r, inventory i , film f WHERE i.inventory_id=r.inventory_id AND i.film_id=f.film_id AND r.customer_id=?',
        values: [ID],
        timeout: 2000 // 2secs
    };
    console.log('Onze query: ' + query.sql);

    res.contentType('application/json');
    db.query(query, function(error, rows, fields) {
        if (error) {
            res.status(401).json(error);
        } else {
            res.status(200).json({ result: rows });
        };
    });
});

///////////////////////////////////////////////////////////
//een post om een rental aan te maken
///////////////////////////////////////////////////////////
routes.post('/rentals/:userid/:inventoryid', function(req, res) {
    var userid = req.params.userid;
    var inventoryid = req.params.inventoryid;
    res.contentType('application/json');
    db.query('INSERT INTO `rental`(`rental_date`, `inventory_id`, `customer_id`, `staff_id`) VALUES (CURRENT_TIMESTAMP,'+inventoryid+','+userid+',1)', function(error, rows, fields) {
        if (error) {
            console.log(error, rows);
            res.status(401).json(error);
        } else {
            res.status(200).json({ result: rows });
        };
    });
});

///////////////////////////////////////////////////////////
//Een rental Editten
///////////////////////////////////////////////////////////
routes.put('/rentals/:userid/:inventoryid', function(req, res) {
    var userid = parseInt(req.params.userid);
    var inventoryid = parseInt(req.params.inventoryid);
    res.contentType('application/json');
    db.query('UPDATE `rental` SET (`rental_date`, `customer_id`, `staff_id`) VALUES (CURRENT_TIMESTAMP,'+userid+',1) WHERE `inventory_id` = '+inventoryid, function(error, rows, fields) {
        if (error) {
            console.log(error, rows);
            res.status(401).json(error);
        } else {
            res.status(200).json({ result: rows });
        };
    });
});

///////////////////////////////////////////////////////////
//de delete.
///////////////////////////////////////////////////////////
routes.delete('/todos/:id', function(req, res) {
    var ID = req.params.id;
    var query = {
        sql: 'DELETE FROM `todos` WHERE ID=?',
        values: [ID],
        timeout: 2000 // 2secs
    };
    console.log('Onze query: ' + query.sql);
    res.contentType('application/json');
    db.query(query, function(error, rows, fields) {
        if (error) {
            res.status(401).json(error);
        } else {
            res.status(200).json({ result: rows });
        };
    });
});

module.exports = routes;