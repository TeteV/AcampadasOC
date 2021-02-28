<?php

/** @var \Laravel\Lumen\Routing\Router $router */

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It is a breeze. Simply tell Lumen the URIs it should respond to
| and give it the Closure to call when that URI is requested.
|
*/

/*$router->get('/', function () use ($router) {
    return $router->app->version();
});*/

//php -S 192.168.1.129:8000 -t ./public

$router->get('/help', function ()  {
    return view('help');
});


$router->group(['prefix' => 'api'], function () use ($router) {
    $router->post('signin', ['as' => 'operario.store', 'uses' => 'OperarioController@signIn']);
    $router->post('login', ['as' => 'operario.logIn', 'uses' => 'OperarioController@logIn']);
    //LogOut funciona mal , recoje la table users en vez de la de operarios
    $router->post('logout', ['as' => 'operario.logout', 'uses' =>  'OperarioController@logOut']);

    $router->get('operario-id/{id}', ['as' => 'operario.show', 'uses' => 'OperarioController@getById']);
    $router->delete('delete-operario/{id}', ['as' => 'operario.delete', 'uses' => 'OperarioController@delete']);
    $router->put('update-operario/{id}', ['as' => 'operario.update', 'uses' => 'OperarioController@update']);
    $router->post('uploadfoto/{file}', ['as' => 'operario.upload', 'uses' => 'OperarioController@loadImage']);

// API route group
    // Matches "/api/zona"
    $router->get('zona', ['as' => 'zona', 'uses' => 'ZonaController@index']);
    // Matches "/api/zona/{id}"
    $router->get('zona/{id}', ['as' => 'zona.show', 'uses' => 'ZonaController@show']);
    // Matches "/api/zona/{id}"
    $router->delete('delete-zona/{id}', ['as' => 'zona.delete', 'uses' => 'ZonaController@delete']);
    // Matches "/api/zona/{id}"
    $router->put('update-zona/{id}', ['as' => 'zona.updatePost', 'uses' => 'ZonaController@updatePost']);
    // Matches "/api/zona"
    $router->post('add-zona', ['as' => 'zona.createPost', 'uses' => 'ZonaController@createPost']);

    // Matches "/api/persona"
    $router->get('persona', ['as' => 'persona', 'uses' => 'PersonaController@index']);
    // Matches "/api/persona/{id}"
    $router->get('persona/{id}', ['as' => 'persona.show', 'uses' => 'PersonaController@show']);
    // Matches "/api/persona/{id}"
    $router->delete('persona/{id}', ['as' => 'persona.delete', 'uses' => 'PersonaController@delete']);
    // Matches "/api/persona/{id}"
    $router->put('persona/{id}', ['as' => 'persona.updatePost', 'uses' => 'PersonaController@updatePost']);
    // Matches "/api/persona"
    $router->post('persona', ['as' => 'persona.createPost', 'uses' => 'PersonaController@createPost']);

    // Matches "/api/reserva"
    $router->get('reserva', ['as' => 'reserva', 'uses' => 'ReservaController@index']);
    // Matches "/api/reserva/{id}"
    $router->get('reserva/{id}', ['as' => 'reserva.show', 'uses' => 'ReservaController@show']);

    // Matches "/api/reserva_zone/{id_zona}"
    //$router->get('reserva_zone/{id_zona}', ['as' => 'reserva.showReservZone', 'uses' => 'ReservaController@showReservZone']);
    $router->get('reserva_local/{localizador}', ['as' => 'reserva.showReservLocalizador', 'uses' => 'ReservaController@showReservLocalizador']);
    // Matches "/api/reserva_zone/{id_zona}/{data_picker}"
    $router->get('reserva_zone/{id_zona}/{date_picker}', ['as' => 'reserva.showReservZoneDate', 'uses' => 'ReservaController@showReservZoneDate']);

    // Matches "/api/zona/{id}"
    $router->delete('reserva/{id}', ['as' => 'reserva.delete', 'uses' => 'ReservaController@delete']);
    // Matches "/api/zona/{id}"
    $router->put('reservaUpdate/{id}', ['as' => 'reserva.updatePost', 'uses' => 'ReservaController@updatePost']);
    // Matches "/api/zona"
    $router->post('reservaCreate', ['as' => 'reserva.createPost', 'uses' => 'ReservaController@createPost']);
});
