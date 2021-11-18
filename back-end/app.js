const mysql = require('mysql');
const express = require("express");
require('dotenv').config();

const hostname = '127.0.0.1';
const port = 3000;
const pool = dbConnection();
const app = express();

function dbConnection() {
  const pool = mysql.createPool({
    connectionLimit: 10,
    host: process.env.HOSTNAME,
    user: process.env.UN,
    password: process.env.PASSWORD,
    database: process.env.DB_NAME
  });
  return pool;
}

app.get("/", (req, res, next) => {
  res.send("Hello World!");
 });

app.get("/url", (req, res, next) => {
  res.json({"test": ["Tony","Lisa","Michael","Ginger","Food"]});
 });
 
app.listen(port, hostname, () => {
  console.log(`Server running at http://${hostname}:${port}/`);
});

async function executeSQL(sql, params) {
  return new Promise (function (resolve, reject) {
    pool.query(sql, params, function (err, rows, fields) {
    if (err) throw err;
      resolve(rows);
    });
  });
}