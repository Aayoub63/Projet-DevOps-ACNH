# ACNHCollector-Front

Bienvenue sur le frontend de **ACNHCollector**, un outil de gestion de collections pour **Animal Crossing : New Horizons**. Ce site vous permet de suivre votre progression dans l'obtention des différents objets du jeu.

## 🚀 Fonctionnalités

Le site propose les outils suivants pour accompagner votre expérience de jeu :

- **Gestion de collections** : Suivez vos musiques, œuvres d'art et fossiles.
- **Calculateur de fleurs** : Un outil dédié pour vous aider dans vos hybridations florales.
- **Profil utilisateur** : Gérez votre compte, visualisez votre profil public et partagez votre progression.
- **Intégration** : Conçu comme un complément à d'autres outils communautaires (comme animalcrossing-online.com).

## 🛠️ Prérequis

Avant de commencer, assurez-vous d'avoir installé :

- **Node.js** (version recommandée : LTS)
- **npm** (inclus avec Node.js)

## 📦 Installation

1. Clonez le dépôt ou téléchargez les sources.
2. Installez les dépendances du projet :

```bash
npm install
```

## ⚙️ Configuration

Le projet utilise des variables d'environnement pour communiquer avec l'API backend. Créez un fichier `.env` à la racine du projet (ou modifiez les variables d'environnement de votre système) :

```env
VUE_APP_API=https://votre-api-url.com/api/
```

*Note : Assurez-vous que l'URL se termine par un slash `/`.*

## 🚀 Lancement et Développement

### Lancer le serveur de développement
Pour tester le projet localement avec rechargement automatique :

```bash
npm run serve
```
Le projet sera accessible par défaut sur `http://localhost:6969`.

### Compiler pour la production
Pour générer les fichiers optimisés pour le déploiement :

```bash
npm run build
```
Les fichiers seront générés dans le dossier `dist/`.


---
*Ce contenu a été généré par IA.*
