package com.utn.TP1_JPA.entidades;

import com.utn.TP1_JPA.enumeraciones.Tipo;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto extends BaseEntidad{

    private Integer tiempoEstimadoCocina;
    private String denominacion;
    private double precioVta;
    private double precioCompra;
    private Integer stockActual;
    private Integer stockMin;
    private String unidadMedida;
    private String receta;
    private Tipo tipo;

    @ManyToMany(mappedBy = "productos")
    private Set<Pedido> pedidos;

    public double getPrecio() {
        return 0;
    }
}