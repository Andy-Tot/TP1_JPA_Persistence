package com.utn.TP1_JPA.entidades;

import com.utn.TP1_JPA.enumeraciones.Estado;
import com.utn.TP1_JPA.enumeraciones.TipoEnvio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido extends BaseEntidad {

    @Temporal(TemporalType.DATE)
    private Date fecha;
    private double total;
    private TipoEnvio tipoEnvio;
    private Estado estado;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "factura-id")
    private Factura factura;

    @ElementCollection
    @CollectionTable(name = "pedido_producto", joinColumns = @JoinColumn(name = "pedido_id"))
    @MapKeyJoinColumn(name = "producto_id")
    @Column(name = "cantidad")
    private Map<Producto, Integer> productos = new HashMap<>();

    public void agregarProducto(Producto producto, int cantidad) {
        productos.put(producto, cantidad);
    }

    public void calcularTotal() {
        total = 0;
        for (Map.Entry<Producto, Integer> entry : productos.entrySet()) {
            total += entry.getKey().getPrecio() * entry.getValue();
        }
    }
}



