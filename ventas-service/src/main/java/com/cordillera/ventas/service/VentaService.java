package com.cordillera.ventas.service;

import com.cordillera.ventas.clients.InventarioClient;
import com.cordillera.ventas.clients.UsuarioClient;
import com.cordillera.ventas.config.RabbitMQConfig;
import com.cordillera.ventas.model.Venta;
import com.cordillera.ventas.repository.VentaRepository;
import com.cordillera.ventas.dto.VentaSolicitud;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Venta procesarVenta(VentaSolicitud solicitud) {
        
        usuarioClient.obtenerUsuarioPorId(solicitud.getIdUsuario());

        Venta nuevaVenta = new Venta();
        nuevaVenta.setIdUsuario(solicitud.getIdUsuario());
        nuevaVenta.setIdProducto(solicitud.getIdProducto());
        nuevaVenta.setCantidad(solicitud.getCantidad());
        nuevaVenta.setTotal(solicitud.getTotal());

        Venta ventaGuardada = ventaRepository.save(nuevaVenta);


        String mensaje = solicitud.getIdProducto() + ":" + solicitud.getCantidad();
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, mensaje);

        return ventaGuardada;
    }
}