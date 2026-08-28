# SME Ticket API - Installatiehandleiding

## Inhoudsopgave
1. [Inleiding](#inleiding)
2. [Benodigdheden (Prerequisites)](#benodigdheden)
3. [Gebruikte Technieken & Frameworks](#gebruikte-technieken--frameworks)
4. [Projectstructuur](#projectstructuur)
5. [Lokaal Opzetten en Draaien](#lokaal-opzetten-en-draaien)
6. [Standaard Gebruikers & Autorisatieniveaus](#standaard-gebruikers--autorisatieniveaus)
7. [Tests Uitvoeren](#tests-uitvoeren)

---

## Inleiding
Welkom bij de SME Ticket API. Deze RESTful web-API is ontwikkeld als helpdesksysteem voor studenten. Het stelt studenten in staat om gestructureerd vragen (tickets) in te schieten, die vervolgens door Subject Matter Experts (SME's) geclaimd en behandeld kunnen worden.

**Belangrijkste functionaliteiten:**
*   **Ticketbeheer:** Aanmaken, inzien, updaten, en verwijderen van tickets.
*   **Role-Based Access Control (RBAC):** Veilige afscherming van endpoints op basis van rollen (Admin, SME, Student) via JWT-tokens.
*   **Bestandsbeheer:** Uploaden en downloaden van bijlagen (stateless opgeslagen in de database).
*   **Interne Notities:** SME's kunnen onzichtbaar voor studenten overleggen via notities op tickets.
*   **Categorisatie:** Tickets worden verrijkt met categorieën, cursussen en kleurgecodeerde tags.

---

## Benodigdheden
Om deze applicatie lokaal te kunnen draaien, dienen de volgende applicaties en runtime environments geïnstalleerd te zijn:
*   **Java Development Kit (JDK):** Versie 21
*   **Maven:** Versie 3.6+ (voor dependency management en build proces)
*   **PostgreSQL:** Relationele database (versie 14 of hoger) en een tool zoals pgAdmin of DBeaver.
*   **Keycloak:** Versie 20+ (voor Identity & Access Management), geconfigureerd om te draaien op poort `9090`
*   **Postman:** Voor het testen van de API via de bijgeleverde JSON-collectie

---

## Gebruikte Technieken & Frameworks
De applicatie is gebouwd in **Java** en leunt zwaar op het **Spring Boot** (v3.3.0) ecosysteem.
*   **Spring Boot Web:** Voor het bouwen van de REST controllers en globale exception handling.
*   **Spring Boot Data JPA & Hibernate:** Voor de Object-Relational Mapping (ORM) en database-interacties.
*   **Spring Security & OAuth2 Resource Server:** Voor de beveiliging van de endpoints en het valideren van JWT-tokens (uitgegeven door Keycloak).
*   **PostgreSQL Driver:** Voor de connectie met de productie-database.
*   **H2 Database:** Een in-memory database specifiek voor razendsnelle, geïsoleerde integratietesten.

---

## Projectstructuur
Het project is opgebouwd volgens een gelaagde (layered) architectuur met een strikte scheiding van verantwoordelijkheden (SOLID):
*   `config/` : Bevat de globale configuratieklassen, zoals de `SecurityConfig` voor endpoint-beveiliging en JWT-validatie.
*   `controllers/`: Handelen HTTP-requests af en sturen responses terug.
*   `dtos/`: Data Transfer Objects om in- en output af te schermen.
*   `entities/`: De Java-representatie van de database-tabellen.
*   `enums/` : Bevat vaste enumeraties, zoals `TicketStatus`.
*   `exceptions/`: Bevat custom exceptions en de `GlobalExceptionHandler`.
*   `mappers/`: Verantwoordelijk voor de conversie tussen Entities en DTO's.
*   `repositories/`: Interfaces voor database-operaties.
*   `services/`: Bevatten de bedrijfslogica.

---

## Lokaal Opzetten en Draaien

### Stap 1: Database Configureren
Start PostgreSQL (bijvoorbeeld via pgAdmin) en maak een nieuwe, lege database aan, bijvoorbeeld genaamd `smeticketdb`. De applicatie zal later zelf de tabellen aanmaken, dus je hoeft verder geen SQL uit te voeren.

### Stap 2: Keycloak Configureren
Voor de authenticatie maakt deze API gebruik van Keycloak. Voer de volgende stappen exact uit om dit lokaal werkend te krijgen:

1. **Start Keycloak:** Draai Keycloak lokaal (of via Docker) in development mode op poort **9090**.
   *Commando-voorbeeld (Windows):* `kc.bat start-dev --http-port=9090`
   *Commando-voorbeeld (Mac/Linux):* `./kc.sh start-dev --http-port=9090`
2. **Importeer de Realm:**
    * Log in op de Keycloak Admin Console (standaard `http://localhost:9090/admin`).
    * Klik linksboven in het uitklapmenu en klik op **Create Realm**. Geef deze een naam (bijv. `sme-ticket-realm`).
    * Ga in het linkermenu naar **Realm settings**, klik rechtsboven op de knop **Action** en kies **Partial import**.
    * Selecteer het bijgeleverde `realm-export.json` bestand.
    * Vink *Clients*, *Realm roles* en *Client roles* aan. Kies bij acties voor **Overwrite** en klik op **Import**.
3. **Gebruikers Aanmaken:** De rollen en API-instellingen zijn nu ingeladen. Nu maken we de testgebruikers aan:
    * Klik in het linkermenu op **Users** en vervolgens op **Add user**. Vul een Username in (bijv. `test_student1`) en klik op **Create**.
    * Ga naar het tabblad **Credentials** van deze nieuwe gebruiker. Klik op **Set password**. Vul een wachtwoord in, zet de schakelaar bij **Temporary** op **OFF** (belangrijk!) en klik op Save.
    * Ga naar het tabblad **Role mapping**. Klik op **Assign role**, zoek de juiste rol (bijv. `ROLE_STUDENT`), selecteer deze en klik op Assign.
    * *Herhaal dit voor een admin (`ROLE_ADMIN`) en een SME (`ROLE_SME`).*
4. **Client Secret ophalen (Voor Postman):**
    * Klik in het linkermenu op **Clients** en klik op de API-client uit de lijst (bijv. `sme-api-client`).
    * Ga naar het tabblad **Credentials** en kopieer de code die bij **Client secret** staat. Sla deze tijdelijk ergens op; je hebt deze straks nodig in Postman.

### Stap 3: Applicatie Configureren (Omgevingsvariabelen)
Open het project in je IDE (bijv. IntelliJ IDEA). Navigeer naar `src/main/resources/application.properties` en pas de gegevens aan naar jouw lokale instellingen:

```properties
# Database connectie (pas username en password aan naar jouw PostgreSQL gegevens)
spring.datasource.url=jdbc:postgresql://localhost:5432/smeticketdb
spring.datasource.username=JOUW_DB_USERNAME
spring.datasource.password=JOUW_DB_PASSWORD

# Hibernate DDL (Zet op 'create' voor de eerste run om de data.sql te laden, daarna op 'update')
spring.jpa.hibernate.ddl-auto=create
spring.sql.init.mode=always

# Keycloak JWT validatie (pas JOUW_REALM_NAAM aan naar de realm die je in Stap 2.2 hebt gemaakt)
spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:9090/realms/JOUW_REALM_NAAM
```

### Stap 4: Applicatie Starten
Zorg ervoor dat in je `application.properties` de optie `ddl-auto` op `create` staat voor de eerste run. Hierdoor genereert Hibernate de tabellen en voert het automatisch het `data.sql` script uit om de database te vullen met dummy-tickets en categorieën.

**Via IntelliJ IDEA (Aanbevolen):**
1. Open het project en wacht tot Maven alle *dependencies* heeft gedownload (dit kan even duren).
2. Klik op het groene play icoontje rechtsboven naast SmeTicketApiApplication.

**Via Terminal (Maven):**
Open een terminal in de root van het project en run:

**Voor Windows:**
```bash
.\mvnw spring-boot:run
```

**Voor Mac/Linux:**
```bash
./mvnw spring-boot:run
```


### Stap 5: Token configureren in Postman
Om de beveiligde endpoints te testen, moet je per request (of op de hoofdmap van de collectie) een token ophalen:
1. Klik in Postman linksboven op het menu en ga naar **file** en dan **import** of druk op `ctrl + o` en sleep de bijgeleverde Postman-collectie naar het veld.
2. Klik op de naam van de nieuwe collectie (aan de linkerkant).
2. Klik op het tabblad **Authorization**.
2. Selecteer bij Type de optie **OAuth 2.0**.
3. Scroll naar beneden naar **Configure New Token** en stel dit als volgt in:
    * **Grant Type:** `Password Credentials` (of `Authorization Code` afhankelijk van de Keycloak client-instellingen).
    * **Access Token URL:** `http://localhost:9090/realms/JOUW_REALM_NAAM/protocol/openid-connect/token`
    * **Client ID:** Naam van je client (bijv. `sme-api-client`)
    * **Client Secret:** Plak hier de secret die je in Stap 2.4 hebt gekopieerd.
    * **Username / Password:** Vul hier de in Stap 2.3 aangemaakte gebruiker in (bijv. `test_admin1`).
4. Klik op **Get New Access Token** en vervolgens op **Use Token**.

---

## Standaard Gebruikers & Autorisatieniveaus
De API hanteert drie strikte autorisatieniveaus. Zorg dat de *usernames* die je in Keycloak hebt aangemaakt exact overeenkomen met de relaties in de API-database:

| Gebruikersnaam in Keycloak | Rol in Keycloak | Toegang (Korte omschrijving) |
| :--- | :--- | :--- |
| `test_admin1` | `ROLE_ADMIN` | Volledige toegang. Kan categorieën/cursussen toevoegen, notities verwijderen en alle tickets inzien. |
| `test_sme1` / `test_sme2` | `ROLE_SME` | Kan alle tickets inzien, tickets claimen (`PATCH`), tags wijzigen, en interne notities toevoegen/lezen. |
| `test_student1` / `test_student2` / `test_student3` | `ROLE_STUDENT` | Kan alleen **eigen** tickets inzien, nieuwe tickets aanmaken, en bijlagen uploaden naar eigen tickets. |

*Let op: Wachtwoorden voor het daadwerkelijke inloggen worden beheerd in Keycloak. Eventuele wachtwoord-velden in de database van de API (`data.sql`) zijn onbruikbare dummy-wachtwoorden.*

---

## Tests Uitvoeren
Het project bevat Unit Tests en self-contained Integratietesten. Voor het schrijven en uitvoeren van deze tests is gebruikgemaakt van **JUnit 5**, **Mockito** en **Spring MockMvc**. De integratietesten maken gebruik van een geïsoleerde H2 in-memory database (geconfigureerd via `application-test.properties`).

**Via IntelliJ IDEA:**
1. Navigeer in de Project view naar de map `src/test/java`.
2. Klik met je rechtermuisknop op de package `nl.novi.smeticketapi` en selecteer **Run 'Tests in 'smeticketapi''**.

**Via Terminal (Maven):**
Voer het volgende commando uit in de root van het project:

**Voor Windows:**
```bash
.\mvnw test
```

**Voor Mac/Linux:**
```bash
./mvnw test
```
