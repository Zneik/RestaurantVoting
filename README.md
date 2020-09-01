Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

---
### API methods
#### For regular user
#### User
##### Get current user info
`curl http://localhost:8080/rest/users --user user@yandex.ru:password`
##### Register new user
`curl -H "Content-Type:application/json" -X POST -d "{\"name\":\"UserNew\",\"email\":\"user_new@yandex.ru\",\"password\":\"newPass123\"}" http://localhost:8080/rest/users/register`

#### Restaurant
##### Get menu restaurants today
`curl http://localhost:8080/rest/restaurants --user user@yandex.ru:password`
##### Get menu restaurant today(id = 100005)
`curl http://localhost:8080/rest/restaurants/100005 --user user@yandex.ru:password`

#### Vote
##### Get user history votes
`curl http://localhost:8080/rest/restaurants/votes --user user@yandex.ru:password`
##### Create vote(restaurantId = 100004)
`curl -H "Content-Type:application/json" -X POST http://localhost:8080/rest/restaurants/100004/votes --user test_user@mail.ru:password`
##### Update vote(restaurantId = 100004)
`curl -H "Content-Type:application/json" -X PUT http://localhost:8080/rest/restaurants/100004/votes --user test_user@mail.ru:password`
##### Delete vote(restaurantId = 100004)
`curl -H "Content-Type:application/json" -X DELETE http://localhost:8080/rest/restaurants/100004/votes --user test_user@mail.ru:password`

#### For admin user
#### Restaurant
##### Get all restaurants
`curl http://localhost:8080/rest/admin/restaurants --user admin@gmail.com:admin`
##### Get restaurant(id = 100004)
`curl http://localhost:8080/rest/admin/restaurants/100004 --user admin@gmail.com:admin`
##### Create new restaurant
`curl -H "Content-Type:application/json" -X POST -d "{\"name\":\"Restaurant 11\"}" http://localhost:8080/rest/admin/restaurants --user admin@gmail.com:admin`
##### Update restaurant(id = 100016)
`curl -H "Content-Type:application/json" -X PUT -d "{\"id\":\"100016\",\"name\":\"Restaurant 1\"}" http://localhost:8080/rest/admin/restaurants/100016 --user admin@gmail.com:admin`
##### Delete restaurant
`curl -H "Content-Type:application/json" -X DELETE http://localhost:8080/rest/admin/restaurants/100016 --user admin@gmail.com:admin`
#### Dish
##### Get dishes by restaurant(restaurantId = 100004)(with history)
`curl http://localhost:8080/rest/admin/restaurants/100004/dishes --user admin@gmail.com:admin`
##### Get dishes by id
`curl http://localhost:8080/rest/admin/dishes/100007 --user admin@gmail.com:admin`
##### Create dish
`curl -H "Content-Type:application/json" -X POST -d "{\"name\":\"Лазанья\", \"price\":\"150\"}" http://localhost:8080/rest/admin/restaurants/100004/dishes --user admin@gmail.com:admin`
##### Update dish
`curl -H "Content-Type:application/json" -X PUT -d "{\"id\":\"100017\",\"name\":\"Лазанья\", \"price\":\"300\"}" http://localhost:8080/rest/admin/restaurants/100004/dishes/100017 --user admin@gmail.com:admin`
##### Delete dish
`curl -H "Content-Type:application/json" -X DELETE http://localhost:8080/rest/admin/dishes/100017 --user admin@gmail.com:admin`
#### Vote
##### Get today votes
`curl http://localhost:8080/rest/admin/votes --user admin@gmail.com:admin`
##### Get vote history
###### all
`curl http://localhost:8080/rest/admin/votes/history --user admin@gmail.com:admin`
###### filtered by date
`curl -X GET -d "startDate=2020-08-23" -d "endDate=2020-08-23" -G http://localhost:8080/rest/admin/votes/history --user admin@gmail.com:admin`
