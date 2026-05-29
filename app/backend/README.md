# ACNHCollector API

Cette API REST, développée avec **Spring Boot (Java 8)**, permet de gérer une collection d'objets pour le jeu **Animal Crossing: New Horizons** (ACNH). Elle offre des fonctionnalités de gestion pour les arts, les fleurs, les fossiles, les musiques et les utilisateurs.

## Prérequis

Pour lancer ce projet, vous devez disposer des éléments suivants :
- **Java 8** (JDK 1.8)
- **Maven 3.x** (ou utiliser le wrapper `mvnw` inclus)
- **MariaDB** (ou une base de données compatible MySQL)

## Configuration

### Base de données

Par défaut, le projet est configuré pour se connecter à une base de données MariaDB locale.
- **URL** : `jdbc:mariadb://localhost:3306/acnh`
- **Utilisateur** : `root`
- **Mot de passe** : *(vide)*

Vous pouvez modifier ces paramètres dans le fichier `src/main/resources/application.properties` ou en utilisant des variables d'environnement.

### Variables d'environnement / Propriétés Spring

Voici les principales propriétés que vous pouvez configurer (via `application.properties` ou en les passant en arguments de la ligne de commande) :

| Propriété | Description | Valeur par défaut |
|-----------|-------------|-------------------|
| `spring.datasource.url` | URL de connexion JDBC | `jdbc:mariadb://localhost:3306/acnh` |
| `spring.datasource.username` | Nom d'utilisateur DB | `root` |
| `spring.datasource.password` | Mot de passe DB | *(vide)* |
| `spring.jpa.hibernate.ddl-auto` | Stratégie Hibernate (ex: `update`, `create`) | `update` |

## Installation et Lancement

1.  **Cloner le projet**
2.  **Configurer la base de données** :
    - Créez une base de données nommée `acnh` dans votre instance MariaDB.
    - Importez le fichier `dump.sql` fourni à la racine du projet pour initialiser les tables et les données :
      ```bash
      mariadb -u root acnh < dump.sql
      ```
      *(Remarque : Ajustez la commande avec `-p` si vous avez un mot de passe)*.
3.  **Compiler le projet** :
    ```bash
    ./mvnw clean install
    ```
4.  **Lancer l'application** :
    ```bash
    ./mvnw spring-boot:run
    ```

L'application sera accessible par défaut sur `http://localhost:8080`.

## Usage Fonctionnel

L'API expose plusieurs points de terminaison pour gérer les collections :

### Authentification
- `POST /login` : Authentification utilisateur pour obtenir un jeton JWT.

### Collections (Public en lecture / Admin en écriture)
- `GET /arts` : Liste tous les objets d'art.
- `GET /flowers` : Liste toutes les fleurs.
- `GET /fossils` : Liste tous les fossiles.
- `GET /musics` : Liste toutes les musiques.

Chaque ressource supporte également les opérations `GET /{id}`, `POST`, `PATCH /{id}` et `DELETE /{id}` (ces dernières nécessitent des privilèges administrateur).

### Utilisateurs et Collections Personnelles
- `GET /users` : Liste les utilisateurs.
- `GET /users/{id}/arts` : Liste la collection d'art d'un utilisateur spécifique.
- `POST /users/{userId}/arts/{artId}` : Ajoute un objet d'art à la collection d'un utilisateur.
- Des points de terminaison similaires existent pour les fleurs, les fossiles et les musiques.

### Ressources Statiques
Le projet sert également des images statiques pour les différents objets de collection via `/static/static/...` (arts, fleurs, fossiles, musiques).

---
*Ce contenu a été généré par IA.*
