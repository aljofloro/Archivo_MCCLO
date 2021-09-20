package com.solmov.archivomcclo.data;

public class Expediente {
  private String numeroUnico;
  private String numero;
  private String anio;
  private String cuaderno;
  private String ubigeo;
  private String tipo;
  private String especialidad;
  private String juzgado;

  public Expediente(String numeroUnico) {
    this.numeroUnico = numeroUnico;
    this.anio = numeroUnico.substring(1,5);
    this.numero = numeroUnico.substring(5,10);
    this.cuaderno = String.valueOf(Integer.valueOf(numeroUnico.substring(17,20)));
    this.ubigeo = numeroUnico.substring(10,14);
    setTipo(numeroUnico.substring(15,16));
    setEspecialidad(numeroUnico.substring(16,17));
    setJuzgado(numeroUnico.substring(21,23));
  }

  private void setJuzgado(String codJuzgado){
    switch (codJuzgado){
      case "201":
      case "S02":
      case "505":
      case "102":
      case "103":
      case "104":
      case "001":
      case "002":
        this.juzgado = "01";
        break;
      case "202":
      case "034":
      case "004":
        this.juzgado = "02";
        break;
      case "203":
      case "029":
        this.juzgado = "03";
        break;
      case "030":
        this.juzgado = "04";
        break;
      default:
        this.juzgado = codJuzgado;
        break;
    }
  }

  private void setEspecialidad(String codEspecialidad){
    switch (codEspecialidad){
      case "2":
        this.especialidad = "CI";
        break;
      case "4":
        this.especialidad = "LA";
        break;
      case "9":
        this.especialidad = "CO";
        break;
      default:
        this.especialidad = codEspecialidad;
        break;
    }
  }

  private void setTipo(String codTipo){
    switch (codTipo){
      case "2":
        this.tipo = "SP";
        break;
      case "3":
        this.tipo = "JR";
        break;
      case "4":
        this.tipo = "JM";
        break;
      case "5":
        this.tipo = "JP";
        break;
      default:
        this.tipo = "ERROR"+codTipo;
        break;
    }
  }

  public String getNumeroExpediente(){
    return getNumero()+"-"+getAnio()+"-"
        +getCuaderno()+"-"+getUbigeo()+"-"+getTipo()+"-"
        +getEspecialidad()+"-"+getJuzgado();
  }

  public String getEspecialidad() {
    return especialidad;
  }

  public String getJuzgado() {
    return juzgado;
  }

  public String getNumero() {
    return numero;
  }

  public String getAnio() {
    return anio;
  }

  public String getCuaderno() {
    return cuaderno;
  }

  public String getUbigeo() {
    return ubigeo;
  }

  public String getTipo() {
    return tipo;
  }
}
