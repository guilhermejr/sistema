<?php

use Illuminate\Support\Facades\Route;
use BuscaNFEBa\Buscador;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/{nfe}', function ($nfe) {
    
    $retorno = new Buscador("http://nfe.sefaz.ba.gov.br/servicos/nfce/qrcode.aspx?p=" . $nfe);
    return $retorno->getArray();

});
