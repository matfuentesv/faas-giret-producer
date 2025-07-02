package com.giret.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Loan {

    private Long idPrestamo;
    private Long recursoId;
    private String fechaPrestamo;
    private String fechaDevolucion;
    private String solicitante;
    private String estado;
    private Resource resource;

}
