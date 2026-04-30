package com.cordillera.ventas.service;

import com.cordillera.ventas.clients.InventarioClient;
import com.cordillera.ventas.clients.UsuarioClient;
import com.cordillera.ventas.model.Venta;
import com.cordillera.ventas.repository.VentaRepository;
import com.cordillera.ventas.dto.VentaSolicitud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private InventarioClient inventarioClient;

    public Venta procesarVenta(VentaSolicitud solicitud) {
      
        usuarioClient.obtenerUsuarioPorId(solicitud.getIdUsuario());

        
        inventarioClient.descontarStock(solicitud.getIdProducto(), solicitud.getCantidad());

        
        Venta nuevaVenta = new Venta();
        nuevaVenta.setIdUsuario(solicitud.getIdUsuario());
        nuevaVenta.setIdProducto(solicitud.getIdProducto());
        nuevaVenta.setCantidad(solicitud.getCantidad());
        nuevaVenta.setTotal(solicitud.getTotal());

        return ventaRepository.save(nuevaVenta);
    }
}