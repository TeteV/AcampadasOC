# Canarias Camping

[Lumen](https://lumen.laravel.com/) Laravel Website. \
[Kotlin](https://kotlinlang.org/) Kotlin Website.

## First of all
Download this repository, has a frontend and backend folder

## Backend-Laravel
Go to the backend folder with your favourite ide and type in terminal
```
composer install
```

In your DB gestor create a new database for this project.
```
create database DBNAME;
```

Rename ".env.example" to .env and change some things in archive like you have in your DB
```
DB_CONNECTION=mysql
DB_HOST=127.0.0.1
DB_PORT=3306
DB_DATABASE=DBNAME
DB_USERNAME=USERNAME
DB_PASSWORD=USERPSWD
```
### To migrate Database
```
php artisan migrate
```

This is for start the local server in your PC
```
php -S yourIp:8000 -t ./public
```

If you want to use reports must do this changes, go to routes/api
and  in aprox line 80
```
'db_connection' => [
            'driver' => 'mysql', //mysql, ....
            'username' => 'YOURDBUSERNAME',
            'password' => 'YOURDBPASSWORD',
            'host' => '127.0.0.1',
            'database' => 'YOURDBNAME',
            'port' => '3306'
        ]
```

## Frontend

Go to the frontend folder and open it with your IDE, must to go to the following route\
and make some changes.
```
app/java/com.example.frontend.controller 
io.ServiceSingleton
```
The change must to change is baseUrl with youp IpAdress:8000/api/

Another change is in route
```
app/java/com.example.frontend.controller 
ui.CreateReservaActivity
```
Near Line 33 in getZone() change url with youp IpAdress:8000/img/

Another change is in route
```
app/java/com.example.frontend.controller 
ui.WebView
```
In line 14, change the url with youp IpAdress:8000/help

### Final Steps
Once in the backend folder your server is ON, you can press the play ▶️ button (AndroidStudio)
and the emulator starts running

# Aditional content
once the app is running you must to create an account and login to enjoy it

## Made with
[Php Storm](https://www.jetbrains.com/es-es/phpstorm/) Website.\
[Android Studio](https://developer.android.com/studio) Website.

Readme.md template by [Villanuevand](https://gist.github.com/Villanuevand/6386899f70346d4580c723232524d35a)

---
💻 with 💜 by [TeteV](https://github.com/TeteV) 🐦 & [Javi Medina](https://github.com/JavierMedina11)
