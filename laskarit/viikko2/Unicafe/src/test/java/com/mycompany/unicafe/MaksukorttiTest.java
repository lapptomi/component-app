package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti != null);
    }

    @Test
    public void kortinSaldoOnAlussaOikein() {
        assertEquals(10, kortti.saldo());
    }

    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(7);
        assertEquals(17, kortti.saldo());
    }

    @Test
    public void rahaaOttaessaSaldoVaheneeOikein() {
        kortti.otaRahaa(10);
        assertEquals(0, kortti.saldo());
    }

    @Test
    public void rahaaOttaessaSaldoEiMuutuJosRahaaEiOleTarpeeksi() {
        kortti.otaRahaa(12);
        assertEquals(10, kortti.saldo());
    }

    @Test
    public void otaRahaaMetodiPalauttaaTrueJosRahatRiittavatMuutenFalse() {
        boolean rahatEivatRiita = kortti.otaRahaa(kortti.saldo() + 1); //false
        boolean rahatRiittavat = kortti.otaRahaa(kortti.saldo()); //true
        assertFalse(rahatEivatRiita);
        assertTrue(rahatRiittavat);
    }

    @Test
    public void maksuKortinToStringMetodiToimiiOikein() {
        int euroa = kortti.saldo() / 100;
        int senttia = kortti.saldo() % 100;
        assertEquals(String.format("saldo: %d.%d", euroa, senttia), kortti.toString());
    }
}
