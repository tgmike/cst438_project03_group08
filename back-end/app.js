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

app.post("/reservation", async (req, res) => {
  let date1 = new Date();
  const format1 = "YYYY-MM-DD HH:mm:ss";
  let reservationDateTime = moment(date1).format(format1);
  let userId = req.body.userId;
  let bookId = req.body.bookId;
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

app.delete("/deleteRes", async (req, res) => {
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

app.get("/availability", async (req, res) => {
let booksAv = [];
let booksSql = "SELECT * FROM Books";
let reservationsSql = "SELECT * FROM Reservations";
let reservations = await executeSQL(reservationsSql);
let books = await executeSQL(booksSql);
let count = 0;
for(let i = 0; i < books.length; i++){
  for(let x = 0; x < reservations.length; x++){
    if(books[i].bookId==reservations[x].bookId){
      break;
    }
    else{
      count+=1;
    }
  }
  if(count!=0){
    booksAv.push(books[i]);
    count = 0;
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
