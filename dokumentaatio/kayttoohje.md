# Käyttöohje

Lataa jar-tiedosto [component-app.jar](https://github.com/lapptomi/ot-harjoitustyo/releases/tag/loppupalautus)


## Ohjelman käynnistäminen

Ohjelma voidaan käynnistää komennolla 

```
java -jar component-app.jar
```
Ohjelman voi myös käynnistää tuplaklikkaamalla kuvaketta.
###### Huom. kuvakkeen avulla käynnistäessä ohjelmaa saattaa sovellus ottaa testaamiseen tarkoitetun tietokannan käyttöön, joten on suositeltavaa käynnistää ohjelma komentoriviltä

## Kirjautuminen


Sovellus käynnistyy kirjautumisnäkymään.

Kirjautuminen onnistuu kirjoittamalla olemassaoleva käyttäjätunnus syötekenttään ja painamalla _log in_.


## Uuden käyttäjän luominen

Kirjautumisnäkymästä on mahdollista siirtyä uuden käyttäjän luomisnäkymään panikkeella _sign up_.

Uusi käyttäjä luodaan syöttämällä tiedot syötekenttiin ja painamalla sign up.  
Luodessa käyttäjää tulee käyttäjänimen olla vähintään 4 merkkiä pitkä, sekä salasanan vähintään 6 merkkiä pitkä.  
Käyttäjänimen tulee olla myös uniikki.

Jos käyttäjän luominen onnistuu, tulee käyttäjälle ilmoitus näytölle.


## Komponenttien muokkaaminen ja poistaminen

Onnistuneen kirjautumisen myötä siirrytään näkymään jossa on lista tietokannassa olevista komponenteista.

Näkymä mahdollistaa komponenttien poistamisen tietokannasta painikkeella _remove_. Taulusta tulee olla komponentti valittuna, joka halutaan poistaa tietokannasta.

Komponentteja voi muokata tuplaklikkaamalla muokattavan komponentin jotakin saraketta, jonka jälkeen käyttäjä voi syöttää uudet tiedot, sekä muutosten jälkeen painamalla näppäintä _ENTER_ tulee muutokset voimaan. Tämän jälkeen kun käyttäjä painaa painiketta _SAVE_, tallentaa sovellus muutokset myös tietokantaan.

## Komponenttien lisääminen tietokantaan
Painamalla painiketta _ADD ITEM_ voi siirtyä näkymään, jossa on mahdollista lisätä komponentti tietokantaan syöttämällä lomakkeeseen lisättävän komponentin tiedot.  
Käyttäjä voi halutessaan alustaa lomakkeen painamalla painiketta _CLEAR_.
