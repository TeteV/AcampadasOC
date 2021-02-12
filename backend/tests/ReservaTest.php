<?php

use App\Models\Reserva;
use \App\Http\Controllers\ReservaController;

class ReservaTest extends TestCase {

    public function testShowReserv(){
        $bookings = new ReservaController();

        $this->assertEquals(
            Reserva::all()->where('id_persona', 6), $bookings->showReserv(6)
        );
    }

}
