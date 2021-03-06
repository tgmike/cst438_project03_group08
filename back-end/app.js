const mysql = require('mysql');
require('dotenv').config();
const express = require("express");
const cors = require('cors');
var moment = require('moment');
const port = process.env.PORT || 3000;
const pool = dbConnection();
const app = express();

app.use(cors());
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

app.post("/book", async (req, res) => {
  let title = req.query.title;
  let author = req.query.author;
  let bookInsertSql = "INSERT INTO Books (title, author) VALUES (?, ?)";
  let params = [title, author];
  let insertBook = await executeSQL(bookInsertSql, params)[0];
  res.json(insertBook);
});


app.get("/users", async (req, res) => {
  let usersSql = "SELECT * FROM Users";
  let users = await executeSQL(usersSql);
  res.json(users);
});

app.get("/user", async (req, res) => {
  let userId = req.query.userId;
  let userSql = "SELECT * FROM Users where userId = ?";
  let param = [userId]
  let user = await executeSQL(userSql, param);
  if(user.length == 0){
    return res.status(404).send({
      message: 'Error user not found'
    });
  }
  res.json(user[0]);
});

app.delete("/user", async (req, res) => {
  let userId = req.query.userId;
  let userSql = "DELETE FROM Users where userId = ?";
  let param = [userId]
  let user = await executeSQL(userSql, param);
  if(user.affectedRows == 0){
    return res.status(200).send({
      message: 'Error user not found'
    });
  }else{
    return res.status(404).send({
      message: 'User successfully deleted'
    });
  }
});

//creates users
app.post("/createAccount", async (req, res) => {
  let username = req.query.username;
  let password = req.query.password;
  let createSql = "INSERT INTO Users (username, password) VALUES (?, ?)";
  let params = [username, password];
  let insertUser = await executeSQL(createSql, params);
  res.json(insertUser);
});
 
//gets all reservations from all users
app.get("/reservationsAll", async (req, res) => {
  let reservationsSql = "SELECT * FROM Reservations";
  let reservations = await executeSQL(reservationsSql);
  res.json(reservations);
});

//Returns all of a specified user's reservations
app.get("/reservationsUser", async (req, res) => {
  let userId = req.query.userId;
  let reservationsSql = "SELECT * FROM Reservations WHERE userId = ?";
  let param = [userId];
  let reservations = await executeSQL(reservationsSql, param);
  if(reservations.length == 0){
    return res.status(404).send({
      message: 'User doesn\'t exist or has no reservations'
    });
  }
  res.json(reservations);
});

//Returns a specified reservation
app.get("/reservation", async (req, res) => {
  let reservation = req.query.reservationId;
  let reservationsSql = "SELECT * FROM Reservations WHERE reservationId = ?";
  let param = [reservation];
  let reservations = await executeSQL(reservationsSql, param);
  if(reservations.length == 0){
    return res.status(404).send({
      message: 'Error reservation not found'
    });
  }
  res.json(reservations);
});

app.delete("/reservation", async (req, res) => {
  let reservationId = req.query.reservationId;
  let reservationSql = "DELETE FROM Reservations WHERE reservationId = ?";
  let param = [reservationId];
  let reservation = await executeSQL(reservationSql, param);
  if(reservation.affectedRows == 0){
    return res.status(200).send({
      message: 'Reservation not found'
    });
  }
  else{
    return res.status(200).send({
      message: 'Reservation deleted'
    });
  }
});

app.post("/reservation", async (req, res) => {
  let date1 = new Date();
  const format1 = "YYYY-MM-DD HH:mm:ss";
  let reservationDateTime = moment(date1).format(format1);
  let userId = req.query.userId;
  let bookId = req.query.bookId;

  //Check if user exists
  let userSql = "SELECT * FROM Users where userId = ?";
  let param = [userId]
  let user = await executeSQL(userSql, param);
  if(user.length == 0){
    return res.status(404).send({
      message: 'Error user not found'
    });
  }

  //Check if book exists
  let bookSql = "SELECT * FROM Books where bookId = ?";
  param = [bookId]
  let book = await executeSQL(bookSql, param);
  if(book.length == 0){
    return res.status(404).send({
      message: 'Error book not found'
    });
  }

  //Check to see if book is reserved
  let reservationSql = "SELECT * FROM Reservations where bookId = ?";
  param = [bookId]
  book = await executeSQL(reservationSql, param);
  if(book.length != 0){
    return res.status(404).send({
      message: 'Error book is already reserved'
    });
  }

  let reservationInsertSql = "INSERT INTO Reservations (reservationDateTime, userId, bookId) VALUES (?, ?, ?)";
  let params = [reservationDateTime, userId, bookId];
  let insertReservation = await executeSQL(reservationInsertSql, params)[0];
  res.json(insertReservation);
});

app.get("/availability", async (req, res) => {
  let booksAv = [];
  let bookId;
  let param;
  let book;
  let reservationsSql;
  let bookReserved;
  let booksSql = "SELECT * FROM Books";
  let books = await executeSQL(booksSql);
  for(const index in books) {
    book = books[index];
    bookId = book.bookId;
    reservationsSql = "SELECT * FROM Reservations WHERE bookId = ?";
    param = [bookId];
    bookReserved = await executeSQL(reservationsSql, param);
    if(bookReserved.length == 0){
      booksAv.push(book);
    }
  }
  res.json(booksAv);
});


app.listen(port, () => {
  console.log(`Server running at http://localhost:${port}`);
});

async function executeSQL(sql, params) {
  return new Promise (function (resolve, reject) {
    pool.query(sql, params, function (err, rows, fields) {
    if (err) throw err;
      resolve(rows);
    });
  });
}
