# Ohjelmistotekniikka, harjoitustyö

## Dokumentaatio
[Vaatimusmäärittely](https://github.com/lapptomi/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)  
[Työkirjanpito](https://github.com/lapptomi/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)

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
Tiedosto löytyy luomisen jälkeen polusta target/Harjoitustyo-1.0-SNAPSHOT.jar


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


