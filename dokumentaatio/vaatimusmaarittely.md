
# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen tarkoituksena on toimia varastonhallintajärjestelmänä tietokoneiden komponenteille. Sovelluksessa voi lisätä, muokata sekä poistaa komponentteja tietokannasta. Sovellusta voi käyttää vain rekisteröidyt käyttäjät.

## Käyttäjät

Alussa sovelluksessa on vain yksi käyttäjärooli “normal”, joka mahdollistaa komponenttien lisäämisen, muokkaamisen sekä poistamisen. 
Jatkossa mahdollisesti kolme eri käyttäjäroolia, jotka kuvattu tarkemmin jatkokehitysideoissa.

## Perusversion tarjoama toiminnallisuus

### Ennen kirjautumista

- käyttäjä voi luoda järjestelmään käyttäjätunnuksen - **Tehty**  
  - käyttäjäunnuksen tulee olla uniikki ja pituudeltaan vähintään 4 merkkiä sekä salasanan tulee olla vähintään 6 merkkiä pitkä - **Tehty**  

- käyttäjä voi kirjautua järjestelmään - **Tehty**  
  - kirjautuminen onnistuu syötettäessä olemassaoleva käyttäjätunnus kirjautumislomakkeelle - **Tehty**
  - jos käyttäjää ei ole olemassa, antaa järjestelmä ilmoituksen - **Tehty**  


### Kirjautumisen jälkeen

- käyttäjä voi tarkastella tietokannan sisältöä, eli listaa komponenteista - **Tehty**  

- käyttäjä voi lisätä komponentteja tietokantaan lomakkeeseen annettujen tietojen perusteella - **Tehty**  
  - lomakkeessa on aluksi kentät ainakin seuraaville tiedoille: Type, Model, Manufacturer ja Serial number - **Tehty**  

- käyttäjä voi muokata tietokannassa olevien komponenttien tietoja

- käyttäjä voi poistaa komponentteja tietokannasta

- käyttäjä voi tarkastella muita tietokantaan rekisteröityneitä käyttäjiä

- käyttäjä voi kirjautua ulos järjestelmästä - **Tehty**  

## Jatkokehitysideoita

Perusversion jälkeen järjestelmää täydennetään ajan salliessa esim. seuraavilla toiminnallisuuksilla

- käyttäjä voi luoda paikallisen tietokannan lisäksi verkossa toimivan tietokannan

- käyttäjä voi hakea komponentteja tietokannasta annettujen tietojen perusteella

- kolme eri käyttäjäroolia, jotka ovat owner, normal sekä read
  - roolilla owner on oikeudet kaikkiin toimintoihin, kuten muokata tietokannan käyttäjien rooleja sekä lisätä, muokata ja poistaa tietokannassa olevaa tietoa
  - roolilla normal on oikeudet tietokannan sisällön muokkaamiseen, lisäämiseen ja poistamiseen
  - roolilla read on oikeudet vain tietokannan tarkasteluun
  
- komponentteja on mahdollista järjestää tyypin, mallin tai valmistajan perusteella - **Tehty**  
