package com.utn.TP1_JPA.entidades;

import com.utn.TP1_JPA.enumeraciones.FormaPago;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Factura extends BaseEntidad{

    private Integer numero;
    private Date fecha;
    private double dto;
    private Integer total;
    private FormaPago formaPago;
}