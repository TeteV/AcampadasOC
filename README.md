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


### Usability

When we design multiplatform applications, the interface that we propose will be essential for the user to feel comfortable working with it. Bad design can cause customers to leave our application.

Within the context of computer applications and software development, usability is defined as the discipline that studies how to design websites and applications so that users can interact with them in the easiest, most comfortable and most intuitive way possible.

Among the elements that I have taken into account when trying to create a design as legible as possible and that does not provide any kind of difficulty or deficiency when interpreting its content, they have been:
  
* **Colors:** Among the colors that I have used in this design we find:
  * **#F5F6FC:** I have used this color mainly for the use of small details, such as some buttons, or in case you want to highlight small elements or icons.
  * **#00BCAA:** I have used it for the subject of titles, subtitles and some other elements in which I used it to create contrast. 
  * **#024341:** I use them mainly to create a gradient in the background of many of the windows of my interface or prototype.
  * **#F4765B:** I use it to create a little contrast between the text in that window, when the background is starring an image.
  
* **Typography:** The fonts I have used are:
  * **Ubuntu:** I use it for the titles of most items.
  * **Quicksand:** I use this typeface mainly for the description inside the rooms or for elements such as reviews. Also, he used different weights within it from "Light", "Regular" and "Medium".
  
  * **Interactive elements:** In the prototype you will find the buttons as interactive elements, in each of the majority of windows, in some, they will be a button to complete the reservas search, in others they will be arrows to move between elements of a slideshow, or in another to display the menu.

* **Data presentation:** In this case, when talking about the presentation of data, I think I should emphasize precisely the last window, which is where I specify information about the reservations, there what I do is precisely divide the content into different sections, from the location of said reservation, a brief description of how many people are going, how many cars are going to be used, etc.

# Aditional content
once the app is running you must to create an account and login to enjoy it

## Made with
[Php Storm](https://www.jetbrains.com/es-es/phpstorm/) Website.\
[Android Studio](https://developer.android.com/studio) Website.

Readme.md template by [Villanuevand](https://gist.github.com/Villanuevand/6386899f70346d4580c723232524d35a)

---
💻 with 💜 by [TeteV](https://github.com/TeteV) 🐦 & [Javi Medina](https://github.com/JavierMedina11)
