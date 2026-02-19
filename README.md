# JavaImageManager - Gestionnaire d'Images Sécurisé

Projet réalisé dans le cadre de la licence **L2 Informatique** à l'Université de Limoges (2024-2025). 
Cet outil permet de gérer, transformer et sécuriser une bibliothèque d'images via une interface graphique moderne.

## Objectifs du Projet
Développer une application robuste respectant le paradigme **MVC** (Modèle-Vue-Contrôleur) pour manipuler des fichiers images et leurs métadonnées.

## Fonctionnalités

### Traitement & Filtres
- **Transformations de base** : Rotation et symétrie.
- **Filtres colorimétriques** :
  - Échange de composantes RGB (ex: RGB -> BRG).
  - Passage en Noir & Blanc et Sépia.
- **Analyse d'image** : Détection de contours via le filtre de **Sobel**.

### Gestion des Métadonnées (Tags)
- Système de **Tags** pour identifier et rechercher des images.
- **Persistance des données** : Sauvegarde de l'historique des transformations et des tags dans des fichiers **JSON** (via la bibliothèque GSon).

### Sécurité & Chiffrement
- **Chiffrement d'image** : Mélange prédictif des pixels basé sur un mot de passe utilisateur.
- Utilisation de `SecureRandom` avec hachage **SHA-256** pour garantir l'intégrité du processus.

## Architecture Technique
- **Langage** : Java 
- **Interface Graphique** : JavaFX avec fichiers **FXML** pour une séparation nette de la vue.
- **Conception logicielle** : Utilisation de l'héritage et des interfaces pour éviter la redondance de code.
  
##  Installation & Utilisation
1. **Prérequis** : Assurez-vous d'avoir JavaFX configuré dans votre IDE (IntelliJ).
2. **Exécution** : Lancez la classe principale pour ouvrir l'interface.
3. **Importation** : Utilisez le `FileChooser` pour charger une image depuis votre disque
4. **Ressources** : Placez vos images de test dans le répertoire `/resources`.
