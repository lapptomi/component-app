# Testausdokumentti

Ohjelmaa on testattu sekä automatisoiduin yksikkö- ja integraatiotestein JUnitilla sekä manuaalisesti tapahtunein järjestelmätason testein.

## Yksikkö- ja integraatiotestaus

### sovelluslogiikka

Automatisoitujen testien ytimen moudostavat sovelluslogiikkaa, eli pakkauksen fi.project.domain luokkia testaavat integraatiotestit ComponentServiceTest ja UserServiceTest.

Integraatiotestit käyttävät datan pysyväistallennukseen erillistä testaukseen tarkoitettua SQLite-tietokantaa, joka alustetaan ennen jokaista testiä.

Sovelluslogiikkakerroksen luokille User ja Component ei ole tehty yksikkötestejä.

### Testauskattavuus

Käyttöliittymäkerrosta lukuunottamatta sovelluksen testauksen rivikattavuus on 86% ja haarautumakattavuus 92%

<img src="https://github.com/lapptomi/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/jacoco.png" width="800">

## Järjestelmätestaus

Sovelluksen järjestelmätestaus on suoritettu manuaalisesti.

### Toiminnallisuudet

Kaikki [määrittelydokumentin](https://github.com/lapptomi/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md) ja käyttöohjeen listaamat toiminnallisuudet on käyty läpi. Kaikkien toiminnallisuuksien yhteydessä on syötekentät yritetty täyttää myös virheellisillä arvoilla kuten tyhjillä.

## Sovellukseen jääneet laatuongelmat

Avaamalla sovelluksen tuplaklikkaamalla kuvaketta saattaa sovellus ottaa testaamiseen tarkoitetun tietokannan käyttöön normaalin sijasta.
