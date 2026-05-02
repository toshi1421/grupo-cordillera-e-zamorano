package com.cordillera.ventas.controller;

import com.cordillera.ventas.model.Venta;
import com.cordillera.ventas.service.VentaService;
import com.cordillera.ventas.dto.VentaSolicitud;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping("/crear")
    public ResponseEntity<?> realizarCompra(@Valid @RequestBody VentaSolicitud solicitud) {

        Venta nuevaVenta = ventaService.procesarVenta(solicitud);
        return ResponseEntity.ok(nuevaVenta);
    }
}
