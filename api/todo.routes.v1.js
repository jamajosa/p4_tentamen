//
// ./api/todo.routes.v1.js
//
var express = require('express');
var routes = express.Router();
var db = require('../config/db');


// retourneert een lijst met fims vanaf start en dan alle films die eronder staan met een counter dat het number is van wanneer hij stopt
routes.get('/films', function(req, res) {

    var filme = req.body;
    var query = {
        sql: 'SELECT * FROM `film` WHERE film_id BETWEEN ? AND ?',
        values: [filme.offset,filme.count],
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

//get all movies with :filmid
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

//haal wat film gegevens op op basisch van gebruikersgegevens
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
//een post om een rental aan te maken
routes.post('/rentals/:userid/:inventoryid', function(req, res) {
    var userid = req.params.userid;
    var inventoryid = req.params.inventoryid;
    var dateNow = new Date().toISOString().slice(0, 19).replace('T', ' ');
    console.log(dateNow);

    var query = {
        sql: 'INSERT INTO `rental`(`rental_date`, `inventory_id`, `customer_id`, `staff_id`) VALUES (?,?,?,?);',
        values: ["'2015-15-14'", 1, 1,1],
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




////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////

//
// Voeg een todo toe. De nieuwe info wordt gestuurd via de body van de request message.
// Vorm van de URL: POST http://hostname:3000/api/v1/todos
//
routes.post('/todos', function(req, res) {

    var todos = req.body;
    var query = {
        sql: 'INSERT INTO `todos`(`Titel`, `Beschrijving`) VALUES (?, ?)',
        values: [todos.Titel, todos.Beschrijving],
        timeout: 2000 // 2secs
    };

    console.dir(todos);
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

//
// Wijzig een bestaande todo. De nieuwe info wordt gestuurd via de body van de request message.
// Er zijn twee manieren om de id van de todos mee te geven: via de request parameters (doen we hier)
// of als property in de request body.
// 
// Vorm van de URL: PUT http://hostname:3000/api/v1/todos/23
//
routes.put('/todos/:id', function(req, res) {

    var todos = req.body;
    var ID = req.params.id;
    var query = {
        sql: 'UPDATE `todos` SET Title=? , Beschrijving=? WHERE ID=?',
        values: [todos.Title, todos.Beschrijving, ID],
        timeout: 2000 // 2secs
    };

    console.dir(todos);
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

//
// Verwijder een bestaande todo.
// Er zijn twee manieren om de id van de todos mee te geven: via de request parameters (doen we hier)
// of als property in de request body.
// 
// Vorm van de URL: DELETE http://hostname:3000/api/v1/todos/23
//
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