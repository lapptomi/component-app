# Arkkitehtuurikuvaus

## Rakenne
Ohjelman rakenne noudattelee kolmitasoista kerrosarkkitehtuuria, ja koodin pakkausrakenne on seuraava:

<img src="https://github.com/lapptomi/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/pic1.png" width="750">

Pakkaus _fi.project.ui_ sisältää JavaFX:llä toteutetun käyttöliittymän _fi.project.domain_ sovelluslogiikan ja tietojen tallennuksen sekä _fi.project.dao_ dao rajapinnat.


## Käyttöliittymä
Käyttöliittymä sisältää viisi erillistä näkymää
- kirjautuminen
- uuden käyttäjän luominen
- lista komponenteista
- lomake jossa mahdollista lisätä uusi komponentti
- lista käyttäjistä

jokainen näistä on toteutettu omana Scene-oliona. Näkymistä yksi kerrallaan on näkyvänä eli sijoitettuna sovelluksen stageen. Käyttöliittymät ovat valmiiksi toteutettu erillisiin FXML-tiedostoihin ja niitä voi kontrolloida niihin kytkettyjen controller-luokkien avulla.

Käyttöliittymä on eristetty sovelluslogiikasta, eli käyttöliittymää kontrolloivat controller-luokat vain kutsuu sopivin parametrein sovelluslogiikasta vastuussa olevia luokkia UserService sekä ComponentService.


## Sovelluslogiikka
Sovelluksen loogisen datamallin muodostavat luokat User ja Component, jotka kuvaavat käyttäjiä ja komponentteja.

<img src="https://github.com/lapptomi/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/pic2.png" width="750">

Luokkien toiminnallisuudesta vastaavat luokat UserService sekä ComponentService. Luokat tarjoavat toiminnot esimerkiksi käyttäjien ja komponenttien lisäämiseen, poistamiseen, muokkaamiseen, sekä hakemiseen.

Ohjelman osien suhdetta kuvaava luokka/pakkauskaavio:

<img src="https://github.com/lapptomi/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/pic3.png" width="750">

Pakkauksen fi.project.domain.services luokat UserService ja ComponentService huolehtivat tietojen lisäämisestä, muokkaamisesta, poistamisesta ja hakemisesta tietokannasta. Kyseisiä toimintoja varten toteuttaa luokat rajapinnat UserDao sekä ComponentDao.

Sovellus tallettaa käyttäjät ja komponentit SQLite-tietokantaan. Tietokantatiedostojen nimet voi määritellä luokassa Database. Kyseinen luokka on myös vastuussa tietokannan yhteydestä, jota muut luokat voivat kutsua toteuttaessaan Database-olion.


# Päätoiminnallisuudet
Kuvataan seuraavaksi sovelluksen toimintalogiikka muutaman päätoiminnallisuuden osalta sekvenssikaaviona.


#### käyttäjän kirjautuminen
Kun kirjautumisnäkymässä syötekenttään on kirjoitettu käyttäjätunnus ja klikataan painiketta log in etenee sovelluksen kontrolli seuraavasti:
<img src="https://github.com/lapptomi/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/login.png" width="750">

Painikkeen painamiseen reagoiva tapahtumankäsittelijä kutsuu sovelluslogiikan metodia validCredentials antaen parametriksi kirjautuneen käyttäjätunnuksen. Seuraavaksi sovelluslogiikka selvittää UserService:n avulla onko käyttäjätunnus olemassa. Jos on, eli kirjautuminen onnistuu, vaihtuu käyttöliittymän näkymä items.fxml-tiedostosta luotuun sceneen. Kyseinen scene sisältää listan tietokannassa olevista komponenteista.

#### uuden käyttäjän luominen
<img src="https://github.com/lapptomi/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/create-user.png" width="750">

Käyttäjän syöttäessä tiedot lomakkeeseen ja painamalla sign up painiketta kutsutaan sovelluslogiikan metodia getUser, joka varmistaa että käyttäjänimeä ei ole varattu. Jos käyttäjänimeä ei ole varattu luodaan uusi käyttäjä kutsumalla metodia createUser, jolloin käyttäjä tallentuu tietokantaan.

# Ohjelman rakenteeseen jääneet heikkoudet
#### käyttöliittymä
Käyttöliittymän toiminnallisuudesta huolehtivat controller-luokat sisältävät paljon toistuvaa koodia, jonka voisi korjata luomalla yhteisiä metodeja näille luokille.

#### database-luokka
Toistaiseksi testejä suorittaessa ei ole järjevää ratkaisua tietokannan suhteen. Tarkoituksena olisi luoda kaksi erillistä tietokantaa, joista toisessa säilyisi tieto, sekä toinen olisi testaamista varten, jossa tieto alustetaan joka kerta ennen testin suorittamista.
