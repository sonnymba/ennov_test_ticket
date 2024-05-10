# ennov_test_ticket
Système de gestion de tickets simple
Vous êtes chargé de développer un système de gestion de tickets simple utilisant Java et Spring Framework. Le système doit permettre à un utilisateur de créer, lire, mettre à jour, affecter et supprimer des tickets. Chaque ticket doit avoir un titre, une description, un utilisateur assigné et un statut (en cours, terminé, annulé).
Pour l'utilisateur, on a juste besoin d'un username et d'un email. Un utilisateur peut avoir plusieurs tickets et un ticket ne peut être affecté qu'à un utilisateur

+ Partie 1 : Configuration du projet
- Créez un nouveau projet Maven ou Gradle.
- Configurez le projet avec Spring Boot.
- Configurez la base de données H2 pour stocker les tickets.

Partie 2 : Modélisation des données
- Créez les entités Java représentant les modèles d'objet :
        - Ticket (avec ses attributs)
        - User (avec ses attributs)

+ Partie 3 : Couches DAO et Service
- Créez les interfaces TicketRepository et UserRepository pour gérer les opérations CRUD.
- Implémentez cette interface en utilisant Spring Data JPA.
- Implémenter les services nécessaires hors CRUD (voir partie 4)

+ Partie 4 : Contrôleurs REST
- Créez un contrôleur REST UserController avec les points de terminaison suivants :
        - GET /users: Récupérer tous les utilisateurs
        - GET /users/{id}/ticket: Récupérer les tickets assignés à l'utilisateur
        - POST /users: Créer un utilisateur
        - PUT /users/{id}: Modifier un utilisateur
        -
- Créez un contrôleur REST TicketController avec les points de terminaison suivants :
        - GET /tickets: Récupérer tous les tickets.
        - GET / tickets /{id}: Récupérer un ticket par son ID.
        - POST / tickets: Créer un nouveau ticket.
        - PUT / tickets /{id}: Mettre à jour un ticket existant.
        - PUT / tickets /{id}/assign/{useId}: Assigner un ticket à un utilisateur.
        - DELETE / tickets /{id}: Supprimer un ticket par son ID.

+ Partie 4 : Contrôle d'accès
- Ajoutez une fonctionnalité pour restreindre l'accès aux tickets en fonction de l'utilisateur connecté

+ Partie 5 : Tests
- Écrivez des tests unitaires pour tester les fonctionnalités des services et des contrôleurs.
- Utilisez JUnit et Mockito pour les tests.

Partie 6 : Documentation
- Documentez vos APIs REST à l'aide de Swagger ou OpenAPI.
- Assurez-vous de gérer les cas d'erreur appropriés, comme la gestion des identifiants inexistants ou d'autres erreurs de validation, en renvoyant les réponses HTTP appropriées.
- L'utilisation du "Generic" et les "Design patterns" serait un plus dans votre implémentation
