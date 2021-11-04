# Otter Library

Wireframe/Mockup:
![Wireframe](https://i.imgur.com/5pbnA8b.png)

ERD:

![ERD](https://i.imgur.com/ezlvHRB.png)

Contributors: 
* Eric Chavez (http://github.com/ericchavez831)
* Ivan Mendoza (http://github.com/ivan776)
* Michael Velasquez (http://github.com/tgmike)
* Oscar Ramirez (http://github.com/csramirez)

Tech Stack:
* Front-End: Android
* Backend: Node
* Database: SQLite

Tentative API endpoints:
* Create new user account
  * POST: [url]/newuser?username={username}&password={password}
* Log in to account
  * POST: [url]/login?username={username}&password={password}
* Log out of account
  * POST [url]/logout?username={username}
* Delete account
  * DELETE [url]/logout?username={username}
* List all books
  * GET [URL]/books
* Show a specific book
  * GET [URL]/books?id={bookID}
* Show a specific user
  * GET [URL]/user?user={username}
* Add new books
  * POST [URL]/books?title={title}...
* Remove items
  * DELETE [URL]/books?id={bookID}
* Update user information
  * PATCH [URL]/users?username={username}
