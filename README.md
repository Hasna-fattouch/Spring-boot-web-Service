Dans le cadre d'un projet d'application basée sur des micro-services, vous devez mettre en place
un webservice REST/JSON permettant de gérer des personnes dans une base de données. Une
personne est définie par les propriétés suivantes :
a. Id: Guid
b. Prénom: string
c. Nom: string

Pour qu'une personne soit considérée comme valide, son nom et son prénom doivent être non-
vides.

Le webservice doit présenter les endpoints CRUD classiques :
a. Récupération d'une liste de personnes, avec filtres sur le nom et le prénom. Les filtres
doivent être optionnels. Les filtres doivent être insensibles à la casse, et il doit être possible
de faire une recherche sur le début ou la fin du nom/prénom (par exemple pour
"Sébastien", "Séb" ou "tien" doit matcher).
b. Récuperation d'une personne , sur base de son id.
c. Ajouter une personne
d. Mis à jour une personne 
Suppression d'une personne
