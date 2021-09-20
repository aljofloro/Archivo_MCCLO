package com.solmov.archivomcclo;

import com.solmov.archivomcclo.data.Expediente;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
  Expediente expediente = new Expediente("22021000842301132016201");
  @Test
  public void numeroAnio() {
    assertEquals("2021", expediente.getNumeroExpediente());
  }

}