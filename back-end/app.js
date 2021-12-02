const mysql = require('mysql');
const express = require("express");
require('dotenv').config();

const port = process.env.PORT || 3000;
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

 app.get("/books", async (req, res, next) => {
  let booksSql = "SELECT * FROM Books";
  let books = await executeSQL(booksSql);
  res.json(books);
 });

app.get("/book", async (req, res, next) => {
  let bookId = req.query.bookId;
  let bookSql = "SELECT * FROM Books where bookId = ?";
  let param = [bookId]
  let book = await executeSQL(bookSql, param);
  if(book.length == 0){
    return res.status(404).send({
      message: 'Error book not found'
   });
  }
  res.json(book[0]);
 });
 
app.listen(port, () => {
  console.log(`Server running at ${port}`);
});

async function executeSQL(sql, params) {
  return new Promise (function (resolve, reject) {
    pool.query(sql, params, function (err, rows, fields) {
    if (err) throw err;
      resolve(rows);
    });
  });
}
