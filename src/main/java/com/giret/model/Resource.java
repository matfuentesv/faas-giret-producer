package com.giret.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public Long getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(Long idRecurso) {
        this.idRecurso = idRecurso;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getFechaVencimientoGarantia() {
        return fechaVencimientoGarantia;
    }

    public void setFechaVencimientoGarantia(String fechaVencimientoGarantia) {
        this.fechaVencimientoGarantia = fechaVencimientoGarantia;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "idRecurso=" + idRecurso +
                ", modelo='" + modelo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", numeroSerie='" + numeroSerie + '\'' +
                ", fechaCompra='" + fechaCompra + '\'' +
                ", fechaVencimientoGarantia='" + fechaVencimientoGarantia + '\'' +
                ", emailUsuario='" + emailUsuario + '\'' +
                ", estado='" + estado + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
