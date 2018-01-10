**UMLParser**

jest to program pozwalający na translację klas zapisanych za pomocą programu Papyrus na język programu CPN Tools. Projekt został zrealizowany w ramach przedmiotu Studio Projektowe.

**Opis programu**

UML Parser to aplikacja desktopowa, napisana w języku Java. Używa między innymi bibliotek Encore od Eclipse. Po uruchomieniu widoczne są trzy przyciski. Przycisk Choose UML służy do wyboru pliku w formacie UML, który będzie przetwarzany (plik wyjściowy z programu Papyrus). Przycisk Choose Output Folder pozwala na wybranie lokalizacji, w której zapisany zostanie plik wynikowy programu, czyli projekt możliwy do otworzenia w programie CPN Tools. Trzeci, dolny przycisk Convert UML to CPN Tools rozpoczyna translację pliku UML na projekt kompatybilny z CPN Tools. 

**Działanie programu**

Po uruchomieniu programu użykownik powinien nacisnąć przycisk Choose UML, i wybrać plik uml programu Papyrus, który ma zostać przetworzony. Wczytanie pliku do programu odbywa się za pomocą bibliotek Encore od Eclipse’a oraz przetworzenia ich hierarchi typów z Encore, na hierachie własne programu, które zapisane są w katalogu dir entities. Następnie za pomocą przycisku Choose Output Folder, powinien wybrać folder na dysku, w którym zapisana zostanie aplikacja wyjściowa. Naciśnięcie przycisku Convert UML to CPN Tools spowoduje uruchomienie procesu translacji. Utworzony zostaje nowy dokument, który uzupełniony zostaje danymi z pustego szablonu strony programu CPN Tools. Ze wczytanych wcześniej danych z pliku UML tworzona jest odpowiadająca struktura. Klasy zamienione zostają na miejsca oraz colsety zawierające dpowiednie zmienne zawarte w klasach. Operacje (metody) tłumaczone zostają na tranzycje. Tranzycje połączone zostają łukami z odpowiadającymi im miejscami. Tak powstały plik jest w pełni działającą aplikacją, która może zostać wczytana do programu CPN Tools.

**Artefakty - Runnable .jar files**

W katalogu out/artifacts/umlParser znajduje się plik wykonywalny aplikacji umlParser.jar.


----------


 Aby pobrać działającą aplikacje proszę wykorzystać ten link:
[https://bitbucket.org/Srock987/umlparser/downloads/umlParser.zip](https://bitbucket.org/Srock987/umlparser/downloads/umlParser.zip)


