package com.cordillera.ventas.service;

import com.cordillera.ventas.clients.InventarioClient;
import com.cordillera.ventas.clients.UsuarioClient;
import com.cordillera.ventas.config.RabbitMQConfig;
import com.cordillera.ventas.model.Venta;
import com.cordillera.ventas.repository.VentaRepository;
import com.cordillera.ventas.dto.VentaSolicitud;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    @Value("${spring.rabbitmq.listener.simple.auto-startup:false}")
    private boolean rabbitEnabled;

    public Venta procesarVenta(VentaSolicitud solicitud) {
        
        usuarioClient.obtenerUsuarioPorId(solicitud.getIdUsuario());

        Venta nuevaVenta = new Venta();
        nuevaVenta.setIdUsuario(solicitud.getIdUsuario());
        nuevaVenta.setIdProducto(solicitud.getIdProducto());
        nuevaVenta.setCantidad(solicitud.getCantidad());
        nuevaVenta.setTotal(solicitud.getTotal());

        Venta ventaGuardada = ventaRepository.save(nuevaVenta);


        if (rabbitEnabled) {
            String mensaje = solicitud.getIdProducto() + ":" + solicitud.getCantidad();
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, mensaje);
        }

        return ventaGuardada;
    }

    public List<Venta> listarTodasLasVentas() {
        return ventaRepository.findAll();
    }

    public Optional<Venta> obtenerVentaPorId(Long id) {
        return ventaRepository.findById(id);
    }

    public List<Venta> obtenerVentasPorUsuario(Long idUsuario) {
        return ventaRepository.findByIdUsuario(idUsuario);
    }
}