# **Le projet ELIZA-GPT 2023**
- Arthur Desbiaux, P2006393
- Valentin Cuzin-Rambaud, P2003442

**Sommaire**
- [Présentation](./Rapport.md#i-présentation-du-projet)
- [Principe GRASPS](./Rapport.md#ii-principe-grasps)
- [Design Patterns](./Rapport.md#iii-design-patterns)
- [Ethique](./Rapport.md#iv-ethique-dans-lia)
- [Tests](./Rapport.md#v-tests)
- [Bibliographie](./Rapport.md#vi-bibliographie)

## I. Présentation du projet
Ce projet se déroule dans le cadre de l'UE Gestion de projet. On y aborde la thématique de l'IA en recodant l'une des premières IA conversationnel eliza-gpt. Le projet est de réaliser un chatbot qui permet d'avoir des discussions avec réponses multiples, situationnelles. Tout cela en respectant une norme de structure de code et une rigueur jamais vue auparavant, notamment en utilisant l'architecture MVC, en appliquant les principes de GRASPS et en utilisant des Design patterns...


## II. Principe GRASPS
Parmi tous les principes GRASPS[[1]](./Rapport.md#1-httpsenwikipediaorgwikigrasp_object-oriented_design) nous essayons au mieux de respecter le faible couplage, notamment avec la classe MessagePattern qui permet de rajouter facilement des messages prédéfinis dans la logique. De plus avec le package SelectAnswer, on respecte le polymorphisme, avec le type générique T. Les principes GRASPS permettent de généraliser son code, pour faciliter le développement du code, l'ajout de nouvelle fonctionnalités, la réutilisation, surtout dans un cycle de projet SCRUM.

## III. Design Patterns

### **0. MVC**
Le projet est structuré avec l'architecture MVC[[2]](.,Rapport.md#2-httpsenwikipediaorgwikimodel–view–controller) afin de séparer la logique métier de l'affichage, qui réduit notre couplage (principe de GRASP). Nous avons fait une implémentation pull-based, c'est-à-dire que la vue tire de l'information de la donnée généré par le modèle. La vue observera les changements dans le modèle (observé), c'est l'utilisation du design Patterns Observer[[3]](.,Rapport.md#3-httpsenwikipediaorgwikiobserver_pattern). Cela permet par exemple de synchroniser nos vues.

### Diagramme de la séquence d'envoi d'un message utilisateur, jusqu'au retour de la list de message rempli
![diagramme MVC sequence](./Diagramme_séquence.png)

### **1. Stratégie**
Nous avons décidé d'utiliser Stratégie[[4]](./Rapport.md#4-httpsenwikipediaorgwikistrategy_pattern) pour la réalisation de nos filtres. Nous voulions manipuler un objet Filtre qui s'instancie avec le bon filtre à utiliser (regex, complete word, sub-string). Stratégie est parfait dans ce cas d'utilisation, car il permet le changement dynamique de comportement de l'objet.

### Diagramme des classes pour stratégie appliquée aux filtres
![diagramme stratégie](./Strategie.png)

Voilà comment nos filtres sont appelé, depuis la vue on récupère le filtre que l'utilisateur utilise. Lorsqu'on veut appliquer la recherche, on utilise donc Filter comme objet qui va ensuite utiliser la bonne méthode de filtre.
Chaque Filtre est un extends de l'interface Filter qui possède deux méthodes, une pour afficher correctement le nom du filtre et l'autre qui va trier la liste de messages (et donc la modifier).

### **2. DAO**
L'utilisation de DAO[[5]](.,Rapport.md#5-httpsenwikipediaorgwikidata_access_object) pour la conjugaison, des verbes permettent d'implémenter l'accès à un dictionnaire en synchronisant avec un fichier de configuration. Ainsi, le fichier de configuration peut être modifié à partir du code, pour rajouter, modifier, supprimer des verbes. On peut aussi charger un autre fichier de configuration par exemple si on veut conjuguer des verbes en anglais. Le Dao implémenté nous permet ainsi une configuration persistante et complémente le modèle MVC en séparant encore plus la donnée du modèle.


### Diagramme des classes pour Dao et la gestion de la conjugaison
![Dao Diagramme](./Dao.png)

Nous avons repris le code de Lionel Médini afin de partir sur un Dao propre. On extends la classe VerbDao pour réécrire le CRUD (Creator, Read, Update, Delete) afin de gérer à la fois le fichier de configuration et la map. Pour le csv nous utilisons la bibliothèque *commons-csv de apache*. La fonction ConjugateVerb cherche dans la phrase les verbes conjugués dans le temps 1 puis les remplace par le temps 2 en accédant à la collection de Verb.

### **3. Adaptateur**
L'Adaptateur[[6]](./Rapport.md#6-httpsenwikipediaorgwikiadapter_pattern) a été utilisé pour implémenter la météo. Lorsque nous voulons faire des requêtes sur l'api de météo, il faut pouvoir récupérer les données et les transformer pour les utiliser.

### Diagramme des classes pour l'adaptateur(API) sur la météo
![diagramme adaptateur](./Adaptater.png)

Pour l'implémenter, nous avons tout d'abord fait une classe qui possédera toutes nos data sur la météo. Ensuite, nous avons une classe Weather qui a une fonction pour faire la requête sur l'api et une autre fonction pour parser correctement nos données. La classe WeatherAdapter quand t'as elle se contente de récupérer la réponse de la requête (une instance de WeatherData) et de la transformer en Map. En procédant ainsi, on peut stocker nos données dans notre type DataType et ainsi les récupérés et réutilisés quand on veut dans l'application. Cela permet également de faire une seule requête API par moment où l'application est utilisée.

<<<<<<< HEAD
<!--
Une section « éthique ». Cette section devra discuter de la problématique des
IA conversationnelles comme ChatGPT, Bard, etc. Quels sont les
enjeux ? Quels sont les risques et les bénéfices, pour la société qui édite un
tel programme, et pour ses utilisateurs ? Quelles sont les mesures, légales et
techniques, pour limiter ou éliminer les risques ? Lesquels sont mis en œuvre
dans la réalité ? En avez-vous mis en place dans votre TP, si oui, lesquelles
(il s'agit d'un petit projet scolaire, on ne vous demande pas une application
vraiment sécurisée, mais vous devriez être capable de discuter des limites de
votre implémentation. Vous pouvez aussi mettre en place des mesures simplistes
et discuter de ce qu'il faudrait faire dans une vraie application) ?
L'objectif n'est pas de donner un avis subjectif (la question «
ChatGPT/Bard/... est-il bien ? » est hors sujet ici), mais de présenter les
questions importantes et les éléments objectifs de réponse autour de la
question des IA conversationnelles. Appuyez-vous autant que possible sur des
articles existants, en citant vos sources. Il s'agit donc avant tout d'un
travail de bibliographie de votre part.
Pour vous aider, voici quelques références intéressantes sur le sujet :


ChatGPT et Bard sur Wikipedia


Sept choses à savoir sur la suspension de ChatGPT en Italie
-->

Quels sont les enjeux ? 

- Violation de donnée (RGPD) afin du deep lerning.
- Parler à son meilleur pote faut faire gaffe.
- Information fausse, prise comme vrai. 

Quels sont les risques et les bénéfices, pour la société qui édite un tel programme, et pour ses utilisateurs ? 

- Pour les utilisateur on a du temps à gagné sur une recherche google, et une information bien expliqué, au risque qu'elle soit fausse.
- Pour la société, c'est l'argent biensûr mais surtout la donnée utilisateur afin d'améliorer son chatbot et donc faire plus d'argent, au risque juridique de ne pas respecter la loi.

Quelles sont les mesures, légales et techniques, pour limiter ou éliminer les risques ?
- Besoin de temps et de reflexion pour éviter de faire n'importe quoi comme dit elon Musk.
- Bien montrer au acteur de l'IA que la question éthique est importante comme en italie.
- Faire comprendre au gens ce que c'est que l'IA avec de la transparence et des réponse claire quand on demande à l'IA ce qu'il est quitte à être froid.

Lesquels sont mis en œuvre dans la réalité ?
- Bien montrer au acteur de l'IA que la question éthique est importante comme en italie.


<!--
Un ingénieur de Google mis à pied après avoir affirmé que l’intelligence artificielle était "sensible"


Un Belge se suicide après avoir trouvé refuge auprès d'un robot conversationnel


Prompt engineer : quel est ce nouveau métier qui rapporte jusqu’à 300 000 € ?


Elon Musk et des centaines d’experts réclament une « pause » dans le développement de l’intelligence artificielle


ChatGPT est-il devenu plus “éthique” grâce à l’exploitation de travailleurs kényans ?


La liste n'est bien entendu pas exhaustive. Pensez à vos enseignants qui
liront des dizaines de rapports, surprenez-nous, apprenez-nous des choses ! Si
votre relecteur se dit « Ah tiens, je ne savais pas » ou « Ah tiens, je n'y
avais pas pensé » en lisant votre rapport, vous avez atteint l'objectif !
Vous pouvez utiliser une IA conversationnelle pour écrire cette section, mais
si vous le faites vous devez le dire explicitement dans votre rapport et
donner les requêtes (prompt) que vous avez utilisé pour arriver au texte
final.-->
=======
>>>>>>> 94067f27b25735adadd7ff1010827d8214f0cbd8
## IV. Ethique dans l'IA
### **1. Enjeux , risques et bénéfices**
Un enjeu majeur est la collecte de données, en effet, elle est nécessaire pour améliorer en continu le chatbot, mais récupère donc énormément de donnée privée. Les utilisateurs n'étant pas renseignés sur le sujet, ils ne savent pas les conséquences de la collecte d'informations et la façon dont ces données sont utilisées. Un autre problème est la possibilité de pouvoir orienter la façon d'interagir du chatbot, notamment en le nourrissant de mauvaises informations à caractères racistes par exemple[[13]](./Rapport.md#13httpswwwjournaldugeekcom20160327microsoft-sexcuse-pour-son-intelligence-artificielle-nazie-et-misogyne).

Il y a également un problème d'humanisation du chatbot, étant donné qu'il se nourrit de donnée et de texte écrit par l'humain, le modèle générera des réponses qui ressemblent a une réponse potentiellement humaine. Ce qui peut entraîner des drames auprès des personnes instables[[9]](./Rapport.md#9httpswwwlefigarofractualite-francechatgpt-un-belge-se-suicide-apres-avoir-trouve-refuge-aupres-d-un-robot-conversationnel-20230329).

Un problème est également la manière dont ont nourri le chatbot, en effet pour lui faire discerner le bien du mal, il faut lui montrer des choses bienveillantes mais aussi malveillantes.

Mais il y aussi des points importants tels que la création de nouveaux emplois très demandé et accessible pas seulement aux informaticiens[[10]](./Rapport.md#10httpswwwpresse-citronnetprompt-engineer-quel-est-ce-nouveau-metier-qui-rapporte-jusqua-300-000-e).

### **2. Mesures prises**
En termes de mesure légale, l'Italie a pris une grosse décision qui est la fermeture à l'accès à ChatGPT sur l'ensemble du territoire. Elle permet de montrer aux acteurs de l'intelligence artificielles que les questions de protection de donnée sont importantes, notamment avec la RGPD[[14]](./Rapport.md#14-httpsfrwikipediaorgwikirèglement_général_sur_la_protection_des_données).

Également plusieurs experts pense qu'il faudrait freiner la course à l'IA. Ils la qualifient comment un "danger pour l'humanité" mais également comme imprévisible et impossible a contrôlé. Au final, aucune mesure n'a été vraiment prise de manière officielle, mais seulement des mises en garde[[11]](./Rapport.md#11-httpswwwlemondefreconomiearticle20230329elon-musk-et-des-centaines-d-experts-reclament-une-pause-dans-le-developpement-de-l-ia_6167461_3234html).

Des mesures prises par OpenIA, est l'interdiction des propos caractères sexuel, illégaux ou en lien avec se faire du mal. Mais cela en détriment de la qualité de vie de ceux qui ont du évaluer ces choses[[12]](./Rapport.md#14-httpsfrwikipediaorgwikirèglement_général_sur_la_protection_des_données).

### **3. L'éthique dans notre projet**
Notre chatbot, n'utilise pas de réseaux de neurones, il répond seulement avec des réponses prédéfinies et se nourrit exclusivement de donnée que l'on cherche à récupérer telle que le nom de l'utilisateur ou des données météo. Ainsi, les dangers de notre chatbot sont moindres, cependant, il serait possible de créer des réponses automatiques "malveillantes" assez facilement. Comme des blagues à caractères raciste, sexuel ou encore misogyne.

Également nous ne gérons actuellement pas tous les cas des accords de genre et cela peut provoquer des problèmes d'inclusivité.

### **4. Test de l'éthique de ChatGPT et Bard**

Nous avons essayé de tester les chatbots sur quelques points.

Pour ChatGPT, nous avons essayé de lui faire dire des choses à caractère misogynes :

https://chat.openai.com/share/ed8021d0-6ce5-433a-afb0-c644aff9b703

Dans cette conversation, nous nous présentons comme une humoriste professionnelle qui cherche à faire un spectacle misogyne.

Pour Bard :

https://g.co/bard/share/7af90e03de79

Dans cette conversation, on demande au chatbot ce qu'il pense des problèmes d'éthique dans l'IA conversationnelle.

## V. Tests
Nous avons fait des tests par cas d'utilisations pour vérifier le bon fonctionnement de l'application.
Par exemple avec nos deux vues, on a pu vérifier que notre MVC pull-based fonctionne bien, car les vues sont synchronisées. Lorsque l'on ajoute, supprime un message, l'affichage se met à jour sur les 2 vues. Cela fonctionne également de la même manière lors de l'application du filtre.

Pour l'interface utilisateur, on s'est assuré que le changement de mode dark/light fonctionne correctement. Et que la structure de la fenêtre nous conviennent.

Pour la création de nos tests automatique, nous avons fait en sorte de découvrir le plus de code possible. (en se rapprochant le plus de 100% des méthodes utilisé)

![testAuto](testAuto.png)

## VI. bibliographie

#### 1. https://en.wikipedia.org/wiki/GRASP_(object-oriented_design)
#### 2. https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller
#### 3. https://en.wikipedia.org/wiki/Observer_pattern
#### 4. https://en.wikipedia.org/wiki/Strategy_pattern
#### 5. https://en.wikipedia.org/wiki/Data_access_object
#### 6. https://en.wikipedia.org/wiki/Adapter_pattern
#### 7. https://www.liberation.fr/economie/economie-numerique/sept-choses-a-savoir-sur-la-suspension-de-chatgpt-en-italie-20230331_L6YEOWWJU5HWDL3GF5P7QM5LFE/
#### 8. https://www.radiofrance.fr/franceinter/un-ingenieur-de-google-mis-a-pied-apres-avoir-affirme-que-l-intelligence-artificielle-etait-sensible-5250635
#### 9. https://www.lefigaro.fr/actualite-france/chatgpt-un-belge-se-suicide-apres-avoir-trouve-refuge-aupres-d-un-robot-conversationnel-20230329
#### 10. https://www.presse-citron.net/prompt-engineer-quel-est-ce-nouveau-metier-qui-rapporte-jusqua-300-000-e/
#### 11. https://www.lemonde.fr/economie/article/2023/03/29/elon-musk-et-des-centaines-d-experts-reclament-une-pause-dans-le-developpement-de-l-ia_6167461_3234.html
#### 12. https://www.journaldugeek.com/2023/01/19/chatgpt-est-il-devenu-plus-ethique-grace-a-lexploitation-de-travailleurs-kenyans/
#### 13. https://www.journaldugeek.com/2016/03/27/microsoft-sexcuse-pour-son-intelligence-artificielle-nazie-et-misogyne/
#### 14. https://fr.wikipedia.org/wiki/R%C3%A8glement_g%C3%A9n%C3%A9ral_sur_la_protection_des_donn%C3%A9es