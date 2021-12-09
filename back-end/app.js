const mysql = require('mysql');
const express = require("express");
require('dotenv').config();
const cors = require('cors');
const port = process.env.PORT || 3000;
const pool = dbConnection();
const app = express();

app.use(cors())
app.use(express.json());
app.use(express.urlencoded({extended: true}));

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

app.get("/", (req, res) => {
  res.send("Hello World!");
});

app.get("/books", async (req, res) => {
  let booksSql = "SELECT * FROM Books";
  let books = await executeSQL(booksSql);
  res.json(books);
});

app.get("/book", async (req, res) => {
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

app.delete("/book", async (req, res) => {
  let bookId = req.query.bookId;
  let bookSql = "DELETE FROM Books where bookId = ?";
  let param = [bookId]
  let book = await executeSQL(bookSql, param);
  if(book.affectedRows == 0){
    return res.status(200).send({
      message: 'Error book not found'
    });
  }else{
    return res.status(404).send({
      message: 'Book successfully deleted'
    });
  }
});
//creates users
app.post("/createAccount", async (req, res) => {
  let username = req.body.username;
  let password = req.body.password;
  let createSql = "INSERT INTO Users (username, password) VALUES (?, ?)";
  let params = [username, password];
  let insertUser = await executeSQL(createSql, params);
  res.json(insertUser);
});
app.post("/book", async (req, res) => {
  let title = req.body.title;
  let author = req.body.author;
  let bookInsertSql = "INSERT INTO Books (title, author) VALUES (?, ?)";
  let params = [title, author];
  let insertBook = await executeSQL(bookInsertSql, params)[0];
  res.json(insertBook);
});
//gets all reservations from all users
app.get("/reservations", async (req, res) => {
  let reservationsSql = "SELECT * FROM Reservations";
  let reservations = await executeSQL(reservationsSql);
  res.json(reservations);
})
//Returns user's current reservation
app.get("/reservation", async (req, res) => {
let user = req.query.userId;
let reservationsSql = "SELECT * FROM Reservations WHERE userId = ?";
let param = [user];
let reservations = await executeSQL(reservationsSql, param);
res.json(reservations);
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
