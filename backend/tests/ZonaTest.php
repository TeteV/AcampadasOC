<?php

use App\Models\Zona;
use \App\Http\Controllers\ZonaController;

class ZonaTest extends TestCase {

    public function testIndex(){
        $zonas = new ZonaController();

        $this->assertEquals(
            Zona::all(), $zonas->index()
        );
    }

    public function testFindById(){
        $zonas = new ZonaController();

        $this->assertEquals(
            Zona::findOrFail(3), $zonas->show(3)
        );
    }

    public function testDestroyBookingById(){
        $zonas = new ZonaController();

        $this->assertEquals(
            response()->json([
                'res' => true,
                'message' => 'Registro ELIMINADO de la vida correctamente'
            ]), $zonas->delete(4)
        );
    }
}
