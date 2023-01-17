# Matador

Hej til den nysgerrige seer, her findes informationer angående vores implementering og udvikling af Matador projekt.

- GUI: Som her benyttes som vores visuelle interface som har samme funktion som det originale brætspil. 
  Herinde findes en visuel repræsentation af brikkerne , terningerne, felter samt feltteksten.
  
- Inde i vores Matador mappe i IntelliJ findes alt kodeudviklingen der er blevet lavet under projektet. 

- Indenunder Main, kan alle klasser og objekter findes.

- Controllers: Her er klasserne Guicontroller og Gamecontroller - Der hver især kontrollerer Gui brættet og selve spillet.

- Gamecomponents: Indenunder Board mappen, står alle feltklasserne og deres funktion, ellers finder man Player klasse, Account klasse, Die klasse, Cup klasse, Cup stub klasse og Cardholder klassen samt deres funktioner.  

- Translators: Her finder man alle spillets beskrivelser, alt fra feltbeskrivelser, spille navne og terningekast. 
  Her finder man Dansk tekst og TextLang med ovenstående indhold, og Text klassen samt dets funktion. 
  Der gøres ikke brug af Engelsk tekst, da dette ikke er et krav. 
  
- Til sidst ses vores Game klasse, der primært sætter gang i spillet via GUI'en
- Indenunder Test kan man finde udførte accepttests (til vores valgte user stories) som er udarbejdet via J-unit test.

Kør og test programmet

- Naviguér til den mappe, I ønsker at lægge programmet i i jeres command line (fx Terminal til mac)
- Til at få hele projektet på jeres computer, skriv i terminalen:

$ git clone https://github.com/hellomelissaw/Matador

- Åbn projektet i IntelliJ.

Til at kører programmet skal man tryk 'run' fra Game klassen.

Til at teste programmet: 

- Alle de nuværende unit tests findes i mappen /src/main/test

- Hvis man vil få et bestemt sum af terninger bruger man LoadedCup, når man kalder på metoden run() fra GameController. I LoadedCup kan man vælge antal øjnene på hver af de to terninger i lige så mange kast som man vil.
- Det kan bruges fx til at lande på et specifikt felt.
- GameController findes i /src/main/java/Controllers/GameController
- LoadedCup findes i /src/test/java/GameComponents/LoadedCup

- Hvis man ikke ønsker at sætte alle spillere op manuelt, kan man sætte testInit til true i GameController. 
- Man kan kalde på metoden setGuiIsOn(boolean guiIsOn) og give false som argument, for at 'slukke' for gui'en, når man lave unit tests på de forskellige klasser, som benytter gui'en. Variablen guiIsOn er ellers true per default. 