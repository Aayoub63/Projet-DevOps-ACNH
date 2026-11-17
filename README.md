# ACNHCollector - Plateforme de Déploiement Continu

Ce dépôt contient le code source de l'application ACNHCollector (Frontend Vue.js et Backend Spring Boot Java 8), ainsi que tout le nécessaire pour automatiser son déploiement.

L'objectif du projet est de proposer une plateforme capable d'être déployée facilement, de manière reproductible, sans manipulation manuelle complexe.

---

## Architecture Technique

Le projet repose sur une architecture moderne, mais volontairement simple à comprendre :

- **Infrastructure as Code (IaC)** : OpenTofu permet de créer la machine virtuelle et de configurer automatiquement les enregistrements DNS (OVH).
- **Configuration as Code** : Ansible installe Docker, transfère les fichiers nécessaires et déploie les conteneurs de manière idempotente.
- **CI/CD** : GitLab CI gère la compilation (via des builds Docker multi-stage) et orchestre le déploiement.
- **Réseau & Sécurité** : Traefik agit comme reverse proxy, génère automatiquement les certificats HTTPS avec Let's Encrypt et gère les règles CORS.
- **Données** : La base de données MariaDB est externalisée sur Clever Cloud.

Cette organisation permet de séparer clairement les responsabilités et de simplifier le déploiement global.

---

## Architecture des Secrets

Aucun secret (mot de passe, clé SSH, token API) n'est stocké en clair dans le dépôt.

L'architecture est organisée de la manière suivante :

- **Environnement Local** : uniquement les clés OVH sont nécessaires pour le provisionnement via OpenTofu.
- **Coffre-fort OpenBao** : centralise tous les secrets sensibles (base de données, clé SSH, token GitLab).
- **Pipeline GitLab CI** : récupère les secrets dynamiquement depuis OpenBao au moment du build et du déploiement.

Cette approche permet de sécuriser le projet tout en gardant un fonctionnement fluide.

---

## Guide de Déploiement de A à Z

Les étapes ci-dessous permettent de déployer entièrement la plateforme.

### 1. Prérequis Locaux

Assurez-vous d'avoir les outils suivants installés :

- `git`
- `opentofu`
- `ansible`
- `jq` (nécessaire pour le script de gestion des secrets)

---

### 2. Configuration de l'Environnement

#### Mise en place du dépôt Git

Option A : Cloner un dépôt existant
```bash
git clone <URL_DU_DEPOT_GITLAB>
cd <NOM_DU_DOSSIER>
```

Option B : Pousser un projet local vers GitLab
```bash
git remote add origin <URL_DU_NOUVEAU_DEPOT_GITLAB>
git branch -M main
git push -u origin main
```

---

#### Configuration du Projet GitLab

Dans **Settings > CI/CD > Variables**, ajoutez :

* `USER_ID_UCA` : votre identifiant
* `ACME_EMAIL` : votre adresse email

Ces variables sont nécessaires pour configurer correctement les noms de domaine.

---

#### Configuration des variables d'environnement

Pour éviter de saisir les variables à chaque fois, créez un fichier `env.sh` à la racine du projet (déjà ignoré par Git).

Faites `cat ~/.ssh/id_ed25519.pub` pour récupérer votre clé publique.

Exemple de fichier `env.sh` :

```bash
#!/bin/bash

# Provider OVH
export OVH_ENDPOINT="ovh-eu"
export OVH_APPLICATION_KEY="..."
export OVH_APPLICATION_SECRET="..."
export OVH_CONSUMER_KEY="..."

# Provider UCA
export TF_VAR_UCA_TOKEN="..."
export TF_VAR_ssh_public_key="..." 

# Provider Clever Cloud
export TF_VAR_CC_ORGANISATION="..."
export TF_VAR_CC_TOKEN="..."
export TF_VAR_CC_SECRET="..."

# Backend OpenTofu
export TF_HTTP_USERNAME="..."
export TF_HTTP_PASSWORD="..."
```

Puis chargez les variables :

```bash
source env.sh
```

---

### 3. Provisionnement de l'Infrastructure

Placez-vous dans le dossier `tofu/` :

```bash
tofu init
tofu apply
```

À la fin de cette étape, un fichier `mariadb-Database.sh` est généré. Il contient les informations de connexion à la base de données. Conservez-les pour la suite.

---

### 4. Gestion des Secrets (OpenBao)

Le pipeline nécessite que certains secrets soient stockés dans votre coffre-fort OpenBao. 
**Attention :** La clé `SSH_KEY` doit impérativement être **encodée en Base64** pour être lue correctement par la CI. De plus, la clé `REGISTRY_TOKEN` correspond à votre **Personal Access Token GitLab**.

*(Astuce : vous pouvez obtenir votre clé encodée avec la commande : `cat ~/.ssh/id_ed25519 | base64`)*

Pour vous simplifier la configuration et éviter les erreurs de formatage, un script automatisé est fourni. 
**Avant de l'exécuter, ouvrez et modifiez le fichier `update_openbao.sh` pour y renseigner votre propre nom d'utilisateur GitLab.** Ensuite, rendez-le exécutable :

```bash
chmod +x update_openbao.sh
```

Puis exécutez-le :

```bash
./update_openbao.sh
```

Les informations suivantes seront demandées :

* clé SSH privée
* token GitLab (registry)
* identifiants de la base de données

Le script stocke ensuite ces données dans OpenBao.

---

### 5. Déclenchement du Pipeline CI/CD

Un simple push déclenche le pipeline :

```bash
git add .
git commit -m "Initial deployment"
git push origin main
```

Le pipeline construit les images et déploie l'application automatiquement.

---

### 6. Initialisation de la Base de Données

Une fois le pipeline terminé :

1. Aller dans **Build > Pipelines**
2. Ouvrir le dernier pipeline
3. Lancer manuellement le job `init_database`

Cette étape est volontairement séparée pour éviter toute suppression de données en production.

---

### 7. Accès aux services

Une fois le déploiement terminé :

* Application : `https://<VOTRE_IDENTIFIANT_UCA>.uca-devops.ovh`
* API : `https://api.<VOTRE_IDENTIFIANT_UCA>.uca-devops.ovh/arts`
* Monitoring : `https://grafana.<VOTRE_IDENTIFIANT_UCA>.uca-devops.ovh`

---



## Tests et vérifications

### Tests du backend

Le stage `test` lance maintenant les tests du backend (`test_backend`), en plus de la validation OpenTofu et Ansible :

- **Tests unitaires** (`APIArtServiceTest`) : la logique du service est testée avec un repository simulé (Mockito).
- **Tests d'intégration** (`APIArtControllerTest`) : l'application démarre avec une base H2 en mémoire et on appelle l'API (codes 200, 404, 401).

Si un test échoue, aucune image n'est construite ni déployée. Les résultats apparaissent dans l'onglet **Tests** du pipeline (rapport JUnit) et la couverture JaCoCo est affichée sur le job.

En local, sans Java installé :

```bash
cd app/backend
docker run --rm -v "$PWD":/app -w /app maven:3.8.6-openjdk-8-slim mvn -B test
```

### Vérification du déploiement

Le déploiement est vérifié à deux niveaux :

1. **Ansible** attend que les healthchecks Docker du backend et du frontend passent à `healthy`. Sinon, le job `deploy_app` échoue.
2. **Le job `verify_deploy`** appelle les URLs publiques (application, API, Grafana) depuis l'extérieur. Il vérifie le DNS, le certificat HTTPS, le routage Traefik et la réponse de l'application.

Le healthcheck du backend utilise `/actuator/health`, qui vérifie aussi la connexion à la base.

### Monitoring

- **Prometheus** collecte les métriques de la VM (node-exporter) **et de l'application** (Spring Boot Actuator / Micrometer).
- **Grafana** charge automatiquement le dashboard `ACNHCollector` : disponibilité du backend, erreurs 5xx, requêtes par seconde, temps de réponse, pool de connexions Hikari, mémoire JVM, CPU, RAM et disque de la VM.
- L'actuator écoute sur le port **8081**, que Traefik ne route pas : les métriques ne sont pas accessibles depuis internet.
- Le mot de passe admin de Grafana est lu dans OpenBao (clé `GRAFANA_ADMIN_PASSWORD`, à ajouter au secret).

### Ansible idempotent

Relancer le playbook sans changement ne modifie rien :

- les conteneurs ne sont plus tous supprimés à chaque déploiement : `docker-compose up -d` ne recrée que ce qui a changé ;
- si un fichier de configuration change, seul le service concerné est redémarré (handlers) ;
- les secrets sont écrits dans un fichier `.env` (droits 600) au lieu d'être en clair dans `docker-compose.yml`.

### Tester toute la stack en local

Le dossier `local/` permet de lancer l'application avec une base MariaDB locale, Prometheus et Grafana, en réutilisant les mêmes fichiers de configuration que la production :

```bash
cd local
cp .env.example .env      # puis choisir les mots de passe (fichier non versionné)
docker compose up -d --build
./verify_local.sh
```

- Application : http://localhost:8000
- API : http://localhost:8080/arts
- Prometheus : http://localhost:9090
- Grafana : http://localhost:3000 (utilisateur `admin`, mot de passe choisi dans `local/.env`)

Pour tout arrêter : `docker compose down -v`.

---

### Destruction de l'infrastructure

```bash
cd tofu/
tofu destroy
```

## Références et Sources

Afin de construire une plateforme robuste et de mieux comprendre (et éviter) certains anti-patterns, ce projet s’appuie sur les ressources suivantes :

- **Enseignements et Travaux Pratiques (ISIMA)**  
  [Cours et TP de DevOps](https://edu.forestier.re/m1-devops#cours) dispensés par M. Forestier.  
  *(Ces travaux pratiques ont fourni le socle technique et méthodologique sur lequel cette infrastructure automatisée a été construite et étendue).*

- **Architecture & Bonnes Pratiques DevOps**  
  Blog de Stéphane Robert – https://blog.stephane-robert.info/docs/  
  (A servi de référence principale pour l'architecture globale du projet, la conception du pipeline CI/CD, la configuration de Traefik, la gestion des conteneurs Docker et l'application des bonnes pratiques DevOps.)

- **Optimisation Base de Données**
  Article : Pool de connexion Hikari avec Spring Boot - https://medium.com/@faroukymedia/pool-de-connexion-hikari-avec-spring-boot-c8b6bb645691
  (Cet article a été crucial pour résoudre un problème d'épuisement des connexions sur la base de données Clever Cloud. La solution a été de limiter la taille du pool via la variable d'environnement `SPRING_DATASOURCE_HIKARI_MAXIMUM_POOL_SIZE=2` dans la configuration du conteneur backend.)

---
