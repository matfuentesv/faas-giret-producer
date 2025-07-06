package com.giret.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {


    private Long idPrestamo;
    private Long recursoId;
    private String fechaPrestamo;
    private String fechaDevolucion;
    private String solicitante;
    private String estado;


}
