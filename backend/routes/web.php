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

$router->get('/', function () use ($router) {
    return $router->app->version();
});

$router->group(['prefix' => 'api'], function () use ($router) {
    $router->post('signin', ['as' => 'operario.store', 'uses' => 'OperarioController@signIn']);
    $router->post('login', ['as' => 'operario.logIn', 'uses' => 'OperarioController@logIn']);

// API route group
    // Matches "/api/zona"
    $router->get('zona', ['as' => 'zona', 'uses' => 'ZonaController@index']);
    // Matches "/api/zona/{id}"
    $router->get('zona/{id}', ['as' => 'zona.show', 'uses' => 'ZonaController@show']);
    // Matches "/api/zona/{id}"
    $router->delete('zona/{id}', ['as' => 'zona.delete', 'uses' => 'ZonaController@delete']);
    // Matches "/api/zona/{id}"
    $router->put('zona/{id}', ['as' => 'zona.updatePost', 'uses' => 'ZonaController@updatePost']);
    // Matches "/api/zona"
    $router->post('zona', ['as' => 'zona.createPost', 'uses' => 'ZonaController@createPost']);

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
});
