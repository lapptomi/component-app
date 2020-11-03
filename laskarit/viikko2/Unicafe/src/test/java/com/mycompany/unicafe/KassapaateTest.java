package com.mycompany.unicafe;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class KassapaateTest {

    Kassapaate kassapaate;
    Maksukortti maksukortti;

    @Before
    public void setUp() {
        kassapaate = new Kassapaate(); //100 000
        maksukortti = new Maksukortti(20000);
    }

    @Test
    public void kassapaatteenRahamaaraOnAlussaOikea() {
        assertEquals(100000, kassapaate.kassassaRahaa());
    }

    @Test
    public void kassapaatteenMyytyjenLounaidenMaaraOnAlussaOikea() {
        int edullisiaMyyty = kassapaate.edullisiaLounaitaMyyty();
        int maukkaitaMyyty = kassapaate.maukkaitaLounaitaMyyty();
        assertEquals(0, edullisiaMyyty + maukkaitaMyyty);
    }

    @Test
    public void syoEdullisestiKasvattaaKassanSaldoaOikein() {
        int saldoEnnenMyyntia = kassapaate.kassassaRahaa();
        kassapaate.syoEdullisesti(10*100);
        assertEquals(saldoEnnenMyyntia+240, kassapaate.kassassaRahaa());
    }

    @Test
    public void syoEdullisestiPalauttaaOikeanMaaranVaihtorahaa() {
        int kateinen = 10*100;
        int vaihtoraha = kassapaate.syoEdullisesti(kateinen);
        assertEquals(kateinen-240, vaihtoraha);
    }

    @Test
    public void syoMaukkaastiKasvattaaKassanSaldoaOikein() {
        int saldoEnnenMyyntia = kassapaate.kassassaRahaa();
        kassapaate.syoMaukkaasti(10*100);
        assertEquals(saldoEnnenMyyntia+400, kassapaate.kassassaRahaa());
    }

    @Test
    public void syoMaukkaastiPalauttaaOikeanMaaranVaihtorahaa() {
        int kateinen = 10*100;
        int vaihtoraha = kassapaate.syoMaukkaasti(kateinen);
        assertEquals(kateinen-400, vaihtoraha);
    }

    @Test
    public void myytyjenEdullistenLounaidenMaaraKasvaaOikein() {
        int kateinen = 10*100;
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
        kassapaate.syoEdullisesti(kateinen);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
        kassapaate.syoEdullisesti(kateinen);
        kassapaate.syoEdullisesti(kateinen);
        assertEquals(3, kassapaate.edullisiaLounaitaMyyty());
    }

    @Test
    public void myytyjenMaukkeidenLounaidenMaaraKasvaaOikein() {
        int kateinen = 20*100;
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
        kassapaate.syoMaukkaasti(kateinen);
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
        kassapaate.syoMaukkaasti(kateinen);
        kassapaate.syoMaukkaasti(kateinen);
        assertEquals(3, kassapaate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void kassanSaldoEiMuutuJosMaksuEiOleRiittava() {
        int kateinen = 230;
        int kassanSaldo = kassapaate.kassassaRahaa();
        kassapaate.syoEdullisesti(kateinen);
        assertEquals(kassanSaldo, kassapaate.kassassaRahaa());
        kassapaate.syoMaukkaasti(kateinen);
        assertEquals(kassanSaldo, kassapaate.kassassaRahaa());
    }

    @Test
    public void rahatPalautetaanTakaisinJosMaksuEiRiitaOstoon() {
        int kateinen = 200;
        assertEquals(kateinen, kassapaate.syoEdullisesti(kateinen));
        assertEquals(kateinen, kassapaate.syoMaukkaasti(kateinen));
    }

    @Test
    public void lounaidenMaaraEiKasvaJosMaksuEiRiitaOstoon() {
        int kateinen = 200;
        kassapaate.syoEdullisesti(kateinen);
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
        kassapaate.syoMaukkaasti(kateinen);
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }

    // Korttiosto toimii sekä edullisten että maukkaiden lounaiden osalta

    @Test
    public void syoEdullisestiVeloittaaOikeanMaaranRahaaKortilta() {
        int kortillaRahaaEnnenOstoa = maksukortti.saldo();
        boolean kortillaOnTarpeeksiRahaa = kassapaate.syoEdullisesti(maksukortti);
        assertTrue(kortillaOnTarpeeksiRahaa);
        assertEquals(kortillaRahaaEnnenOstoa-240, maksukortti.saldo());
    }

    @Test
    public void syoMaukkaastiVeloittaaOikeanMaaranRahaaKortilta() {
        int kortillaRahaaEnnenOstoa = maksukortti.saldo();
        boolean kortillaOnTarpeeksiRahaa = kassapaate.syoMaukkaasti(maksukortti);
        assertTrue(kortillaOnTarpeeksiRahaa);
        assertEquals(kortillaRahaaEnnenOstoa-400, maksukortti.saldo());
    }

    @Test
    public void myytyjenEdullistenLounaidenMaaraKasvaaKortillaOstaessa() {
        int myydytLounaatEnnen = kassapaate.edullisiaLounaitaMyyty();
        kassapaate.syoEdullisesti(maksukortti);
        kassapaate.syoMaukkaasti(maksukortti);
        kassapaate.syoEdullisesti(maksukortti);
        assertEquals(myydytLounaatEnnen+2, kassapaate.edullisiaLounaitaMyyty());
    }

    @Test
    public void myytyjenMaukkaidenLounaidenMaaraKasvaaKortillaOstaessa() {
        int myydytLounaatEnnen = kassapaate.edullisiaLounaitaMyyty();
        kassapaate.syoMaukkaasti(maksukortti);
        kassapaate.syoEdullisesti(maksukortti);
        kassapaate.syoMaukkaasti(maksukortti);
        assertEquals(myydytLounaatEnnen+2, kassapaate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void myytyjenEdullistenLounaidenMaaraEiKasvaJosKortillaEiTarpeeksiRahaa() {
        kassapaate.syoEdullisesti(new Maksukortti(200));
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
    }

    @Test
    public void kortiltaEiVeloitetaRahaaJosSeEiOleEdulliseenLounaaseenRiittava() {
        int kortinSaldoAlussa = 200;
        Maksukortti vajaasaldoinenKortti = new Maksukortti(kortinSaldoAlussa);
        kassapaate.syoEdullisesti(vajaasaldoinenKortti);
        assertEquals(kortinSaldoAlussa, vajaasaldoinenKortti.saldo());
        assertFalse(kassapaate.syoEdullisesti(vajaasaldoinenKortti));
    }

    @Test
    public void myytyjenMaukkaidenLounaidenMaaraEiKasvaJosKortillaEiTarpeeksiRahaa() {
        kassapaate.syoMaukkaasti(new Maksukortti(380));
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void kortiltaEiVeloitetaRahaaJosSeEiOleMaukkaaseenLounaaseenRiittava() {
        int kortinSaldoAlussa = 380;
        Maksukortti vajaasaldoinenKortti = new Maksukortti(kortinSaldoAlussa);
        kassapaate.syoMaukkaasti(vajaasaldoinenKortti);
        assertEquals(kortinSaldoAlussa, vajaasaldoinenKortti.saldo());
        assertFalse(kassapaate.syoMaukkaasti(vajaasaldoinenKortti));
    }

    @Test
    public void kassanSaldoEiMuutuJosKortillaEiTarpeeksiRahaaOstaessa() {
        int kassanSaldoEnnenOstoa = kassapaate.kassassaRahaa();
        kassapaate.syoEdullisesti(new Maksukortti(200));
        assertEquals(kassanSaldoEnnenOstoa, kassapaate.kassassaRahaa());
        kassapaate.syoMaukkaasti(new Maksukortti(380));
        assertEquals(kassanSaldoEnnenOstoa, kassapaate.kassassaRahaa());
    }

    @Test
    public void kortilleRahaaLadattaessaKortinJaKassanSaldoMuuttuu() {
        int kortinSaldoEnnenLatausta = maksukortti.saldo();
        int kassanSaldoEnnenLatausta = kassapaate.kassassaRahaa();
        int ladattavaSumma = 10000;
        kassapaate.lataaRahaaKortille(maksukortti, ladattavaSumma);
        assertEquals(kortinSaldoEnnenLatausta+ladattavaSumma, maksukortti.saldo());
        assertEquals(kassanSaldoEnnenLatausta+ladattavaSumma, kassapaate.kassassaRahaa());
    }

    @Test
    public void kortilleNegatiivisenMaaranRahaaLadattaessaKortinJaKassanSaldoEiMuutu() {
        int kortinSaldoEnnenLatausta = maksukortti.saldo();
        int kassanSaldoEnnenLatausta = kassapaate.kassassaRahaa();
        int ladattavaSumma = -10000;
        kassapaate.lataaRahaaKortille(maksukortti, ladattavaSumma);
        assertEquals(kortinSaldoEnnenLatausta, maksukortti.saldo());
        assertEquals(kassanSaldoEnnenLatausta, kassapaate.kassassaRahaa());
    }
}