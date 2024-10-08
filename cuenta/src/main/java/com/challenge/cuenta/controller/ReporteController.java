package com.challenge.cuenta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import com.challenge.cuenta.service.IMovimiento;

import reactor.core.publisher.Mono;

import com.challenge.cuenta.error.LocalNotFoundException;
import com.challenge.cuenta.model.entity.Cuenta;
import com.challenge.cuenta.model.entity.Movimiento;
import com.challenge.cuenta.service.ICuenta;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1")
public class ReporteController {

    @Autowired
    private ICuenta cuentaService;

    @Autowired
    private IMovimiento movimientoService;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/reportes")
    public Mono<ResponseEntity<?>> generarReporte(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
                                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin,
                                            @RequestParam Long id) throws LocalNotFoundException {
            
        try {
            Mono<Map<String, Object>> clienteNombreMono = webClientBuilder.build()
            .get()
            .uri("http://localhost:8092/api/v1/clientes/{id}", id)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
                    
            return clienteNombreMono.flatMap(cliente -> {

                String clienteNombre = (String) cliente.get("nombre");

                List<Cuenta> cuentas = cuentaService.findByIdCliente(id);
                if (cuentas.isEmpty()) {
                    throw new LocalNotFoundException("El cliente no existe o no tiene cuentas asignadas");
                }

                List<Map<String, Object>> reportes = new ArrayList<>();

                for (Cuenta cuenta : cuentas) {
                    List<Movimiento> movimientos = movimientoService.obtenerMovimientosPorFecha(cuenta, fechaInicio, fechaFin);

                    double saldoInicial = cuenta.getSaldoInicial();
                    double saldoDisponible = saldoInicial;
                    double totalMovimientos = 0;

                    for (Movimiento movimiento : movimientos) {
                        if ("Credito".equalsIgnoreCase(movimiento.getTipoMovimiento())) {
                            saldoDisponible += movimiento.getValor();
                            totalMovimientos += movimiento.getValor();
                        } else if ("Debito".equalsIgnoreCase(movimiento.getTipoMovimiento())) {
                            saldoDisponible -= movimiento.getValor();
                            totalMovimientos -= movimiento.getValor();
                        }
                    }

                    Map<String, Object> reporte = new HashMap<>();
                    reporte.put("Fecha", new Date());
                    reporte.put("Cliente", clienteNombre);
                    reporte.put("Numero Cuenta", cuenta.getNumeroCuenta());
                    reporte.put("Tipo", cuenta.getTipoCuenta());
                    reporte.put("Saldo Inicial", saldoInicial);
                    reporte.put("Estado", cuenta.getEstado());
                    reporte.put("Movimiento", totalMovimientos);
                    reporte.put("Saldo Disponible", saldoDisponible);

                    // Agregar el reporte a la lista de reportes
                    reportes.add(reporte);
                }

                // Retornar todos los reportes como respuesta JSON
                return Mono.just(ResponseEntity.ok(reportes));
            });
        } catch (Exception e) {
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al generar el reporte"));
        }
    }
}
