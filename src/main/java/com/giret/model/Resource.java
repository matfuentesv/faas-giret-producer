package com.giret.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Resource {

    private Long idRecurso;
    private String modelo;
    private String descripcion;
    private String numeroSerie;
    private String fechaCompra;
    private String fechaVencimientoGarantia;
    private String emailUsuario;
    private String estado;
    private String categoria;
}
