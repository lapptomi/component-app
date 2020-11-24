# Ohjelmistotekniikka, harjoitustyö

## Dokumentaatio
[Vaatimusmäärittely](https://github.com/lapptomi/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)  
[Työkirjanpito](https://github.com/lapptomi/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)

## Komentorivitoiminnot
###### Huom. Komennot tulee suorittaa projektin juuressa   

### Ohjelman suoritus
Ohjelma voidaan suorittaa komennolla
```
mvn compile exec:java -Dexec.mainClass=Main
```

Suorittaessa ohjelmaa tulee käyttäjän luoda uusi tili, jolla kirjautua sisään.


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


