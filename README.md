# Ohjelmistotekniikka, harjoitustyö


## Sovelluksen tarkoitus

Sovelluksen tarkoituksena on toimia varastonhallintajärjestelmänä tietokoneiden komponenteille. Sovelluksessa voi lisätä, muokata sekä poistaa komponentteja tietokannasta. Sovellusta voi käyttää vain rekisteröidyt käyttäjät. 


## Dokumentaatio
[Vaatimusmäärittely](https://github.com/lapptomi/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)  
[Työkirjanpito](https://github.com/lapptomi/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)  
[Arkkitehtuuri](https://github.com/lapptomi/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)  
[Testausdokumentti](https://github.com/lapptomi/ot-harjoitustyo/blob/master/dokumentaatio/testaus.md)  
[Käyttöohje](https://github.com/lapptomi/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)
[Kuvia sovelluksen käyttöliittymästä](https://github.com/lapptomi/ot-harjoitustyo/blob/master/dokumentaatio/ui.md)


## Komentorivitoiminnot
###### Huom. Komennot tulee suorittaa projektin juuressa   

### Ohjelman suoritus
Ohjelma voidaan suorittaa komennolla
```
mvn compile exec:java -Dexec.mainClass=fi.project.Main
```

Suorittaessa ohjelmaa tulee käyttäjän luoda uusi tili, jolla kirjautua sisään.


### Suoritettavan jarin generointi  
Suoritettava jar-tiedosto voidaan luoda komennolla  
```
mvn package
```
Tiedosto löytyy luomisen jälkeen polusta target/component-app.jar


## Releaset
[loppupalautus](https://github.com/lapptomi/ot-harjoitustyo/releases/tag/loppupalautus)


### Testaus
Testit voidaan suorittaa komennolla
```
mvn test
```


Testikattavuusraportti voidaan luoda komennolla  
```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_  


### Checkstyle
Checkstyle voidaan ajaa komennolla 
```
mvn jxr:jxr checkstyle:checkstyle
```

Raportti tyylivirheistä löytyy polusta target/site/checkstyle.html


### JavaDoc
JavaDocista saa luotua HTML-version suorittamalla komennon

```
mvn javadoc:javadoc
```
Generoitu JavaDoc löytyy hakemistosta target/site/apidocs/

