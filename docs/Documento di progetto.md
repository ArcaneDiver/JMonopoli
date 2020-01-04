---


---

<h1 id="documento-del-progetto-monopoly">Documento del progetto Monopoly</h1>
<h3 id="gruppo-e-obiettivo">Gruppo e obiettivo</h3>
<p>Il gruppo è composto da Victor G. De Matteis, Marian Tataru e Michele Della Mea.</p>
<p>L’obiettivo del gioco è quello di offrire un’esperienza nuova che mantenga però la strategicità e logica di base del Monopoly.</p>
<h3 id="requirements-del-progetto">Requirements del progetto</h3>
<p>La deadline per la consegna del progetto è stata posta alla data del <strong>2 febbraio 2020, entro le 23:59.</strong></p>
<p>Il programma deve rispettare delle caratteristiche ben definite tra cui:</p>
<ul>
<li>Una struttura rigorosa del codice</li>
<li>Le varie operazione devono essere distinte in diversi metodi</li>
<li>Il programma deve compilare ed andare in esecuzione <strong>senza errori o bug.</strong></li>
</ul>
<h3 id="obiettivo-del-gioco"><strong>Obiettivo del gioco</strong></h3>
<p>L’obiettivo del gioco rimane comnque quello di generare il numero maggiore di denaro alla fine della partita.</p>
<p>Il giocatore perde quando chiude senza denaro rimanente, in quanto non è in grado di pagare le spese che ammontano finendo su una casella.</p>
<p>Per generare introiti si possono acquistare delle industrie. Quando un giocatore passa sopra un’industria si vede costretto a pagare lo stato, se nessuno la detiene, o al giocatore possessore dell’azienda e stabili.</p>
<h3 id="requirements-per-giocare"><strong>Requirements per giocare</strong></h3>
<p>Per praticità di realizzazione le partite possono avere luogo solo in presenza di quattro giocatori.</p>
<p>I giocatori dovrebbero avere una buona conoscenza del gioco. Se il development del gioco lo permette, verrà aggiunto un tutorial introduttivo.</p>
<h3 id="start-del-gioco"><strong>Start del gioco</strong></h3>
<p>All’inizio del gioco vengono affidati ad ogni giocatore una quantità di denaro che corrisponde a 250.000 denari e 5 proprietà acquistabili scelte randomicamente.</p>
<p>Il gioco prende luogo in Prussosca.</p>
<h3 id="turni-del-gioco"><strong>Turni del gioco</strong></h3>
<p>Ogni turno presenta un evento che varia i prezzi da pagare in industrie e stabili.</p>
<p>Se non persistono eventi durante il turno, ne verrà generato uno nuovo.</p>
<p>Ogni evento, in termini pratici, viene rappresentato dalla variazione, in percentuale, di prezzi. Alcuni prezzi aumenteranno e altri diminuiranno. I prezzi sono costanti e variano il prezzo per il prossimo evento. In quel momento i prezzi non verranno riportati alla normalità. Il prossimo evento modificherà nuovamente i prezzi, ribaltando o affermando la situazione.</p>
<p>Durante il turno, a tutti i quattro giocatori, viene dato il diritto di avanzare all’interno del campo per una distanza generata randomicamente.</p>
<p>L’unica eccezione avviene quando il giocatore è in prigione. In quel momento, per il giocatore sarà possibile muoversi solo se:</p>
<ul>
<li>
<p>Sono passati tre turni dall’entrata in prigione, pagando 5000 soldi.</p>
</li>
<li>
<p>Due numeri generati randomicamente sono uguali</p>
</li>
<li>
<p>Si dispone di un pass per uscire da prigione, ottenibile dalle carte delle probabilità.</p>
</li>
</ul>
<p>Una volta che viene portata a termine l’attività della casella in cui si è finiti, è possibile entrare in contatto con gli altri giocatori per trattare.</p>
<p>Verrà mostrata una chat in cui verranno dati a disposizione dei comandi particolare per la transazione di proprietà e soldi.</p>
<p>Infinite transazioni possono essere eseguite.</p>
<p>A questa chat, se il development lo consente, saranno presenti tutte le persone all’interno di una partita.</p>
<h3 id="campo"><strong>Campo</strong></h3>
<p>Il campo è un quadrato suddiviso in nove caselle e una casella per angolo.</p>
<h3 id="industrie"><strong>Industrie</strong></h3>
<p>Il campo presenta diversi tipi di identità.</p>
<p>Questo gioco, a differenza del Monopoly originale, al posto delle proprietà presenta le industrie.</p>
<p>Ci sono otto tipi di industrie:</p>
<ul>
<li>Tessile</li>
<li>Sviluppo</li>
<li>Prod. Armi</li>
<li>Cibarie</li>
<li>Alberghiera</li>
<li>Ferroviera</li>
<li>Spaziale</li>
<li>Automobilistica</li>
</ul>
<h3 id="tipologie-di-caselle-all’interno-del-campo"><strong>Tipologie di caselle all’interno del campo</strong></h3>
<p>All’interno del campo vi sono diverse tipologie di caselle:</p>
<ul>
<li>
<p>Probabilità: Il giocatore deve pescare una carta dal mazzo delle probabilità. Gli effetti che la carta implica verranno applicati immediatamente dopo la carta. Le probabilità sono di maggioranza positiva.</p>
</li>
<li>
<p>Imprevisti: Il giocatore deve pescare una carta dal mazzo degli imprevisti. Sono eventi di maggioranza negativa.</p>
</li>
<li>
<p>Industria: vi sono diversi scenari possibili in base allo stato della casella.</p>
</li>
<li>
<p>Se l’industria non è posseduta da nessuno: si può pagare la tassa di transito, che varia da industria ad industria e che andrà alla banca di Stato,  oppure è possibile comprare l’industria dallo Stato. Si paga la tassa corrispondente ad un solo stabile. Tutte le industrie possiedono solo uno stabile</p>
</li>
<li>
<p>L’industria è posseduta da un altro giocatore: sarà necessario pagare la tassa di transito che varia da industria a industria e cambia anche in base al numero di stabili. Questa tassa andrà direttamente al possessore dell’industria come introito.</p>
</li>
<li>
<p>L’industria posseduta dallo stesso giocatore che ci finisce sopra: se il giocatore possiede tutte le industrie dello stesso tipo. La tassa di passaggio aumenta in base al numero di stabili.</p>
</li>
<li>
<p>Industria non acquistabile: i servizi elettrici e idraulici sono industrie dello Stato non in vendita. Sono disperse per il campo.</p>
</li>
<li>
<p>Casella del via (Casella della distribuzione del pane): Il giocatore che ci passa, anche senza avere contatto con la casella, ha il diritto di ritirare 20.000 soldi.</p>
</li>
</ul>
<h3 id="modalità-multigiocatore"><strong>Modalità multigiocatore</strong></h3>
<p>Per la modalità multigiocatore il progetto è quello di realizzare un codice server in una macchina che aspetta le connessioni di quattro persone. Quando queste quattro sono connesse, il gioco inizia.</p>
<p>Utilizzeremo sempre una struttura a socket. Gli host comunicheranno con il server tramite dei messaggi.</p>
<p>Questi conterranno dei dati. I messaggi verranno mandati a tutti gli host, e conterranno dei comandi.</p>
<p>Per vedere uno schema della comunicazione consultare il diagramma</p>
<h3 id="multigiocatore"><strong>MULTIGIOCATORE</strong></h3>
<p><strong>Struttura del codice</strong></p>
<ul>
<li>Server
<ul>
<li>LobbyMode</li>
<li>DescrittoreGioco</li>
<li>Tutti gli elementi di gioco sotto citati attuali con i dati modificati dagli eventi per la partita corrente</li>
<li>Esito</li>
<li>Chiusura</li>
<li>Costanti</li>
<li>Routine</li>
</ul>
</li>
<li>Client
<ul>
<li>GUI</li>
<li>Reciever</li>
</ul>
</li>
<li>Elementi di gioco
<ul>
<li>DescrittoreGioco che rimane costante come punto di partenza</li>
<li>Array con tutti gli elementi sotto citati:
<ul>
<li>Industrie</li>
<li>Costanti</li>
<li>Eventi</li>
<li>Probabilità</li>
<li>Imprevisti</li>
<li>Denaro di tutti gli utenti</li>
<li>Proprietà di tutti gli utenti (campo da riempire randomicamente</li>
</ul>
</li>
</ul>
</li>
<li>Testsuite
<ul>
<li>Client automatizzato</li>
</ul>
</li>
</ul>
<h3 id="sviluppo-del-codice"><strong>Sviluppo del codice</strong></h3>
<p>Lo sviluppo del codice possiederà diversi filoni principali:</p>
<ol>
<li>GUI</li>
<li>Client e testing client</li>
<li>Server</li>
<li>Elementi di gioco</li>
<li>Orchestrazione</li>
<li>Controllo qualità</li>
</ol>
<h3 id="percorso-di-sviluppo-e-ruoli"><strong>Percorso di sviluppo e ruoli</strong></h3>
<ul>
<li>
<p>Fase iniziale</p>
<ul>
<li>[NOME] Sviluppo di tutti i comandi MINIMI per la comunicazione Client Server</li>
<li>[VICTOR] Client su console automatico per il testing</li>
<li>[NOME] Server su console</li>
<li>[NOME] Elementi del gioco MINIMI
<ul>
<li>Industrie</li>
<li>Costanti</li>
<li>Eventi</li>
</ul>
</li>
</ul>
</li>
<li>
<p>Terminato lo sviluppo del client su console avverrà la seconda fase della progettazione</p>
<ul>
<li>[VICTOR] Client a GUI per il giocatore</li>
<li>[NOME] Ampliamento degli elementi di gioco</li>
<li>[NOME] Sviluppo della chat per la trattativa</li>
<li>[NOME] Sviluppo del resto dei comandi sia per chat che per Server Client</li>
</ul>
</li>
<li>
<p>Terminato lo sviluppo di Server, Client, GUI, Elementi di gioco [NOME] procederà con la fase di orchestrazione di tutte le parti in modo tale che tutto funzioni</p>
</li>
<li>
<p>Alla fine, [VICTOR] procederà con la fase di testing e delivering del prodotto</p>
</li>
</ul>
<h3 id="previsione-delle-classi"><strong>Previsione delle classi</strong></h3>
<p>Si tratta di una previsione e ci riserviamo la possibilità di cambiare nome e struttura delle classi in qualsiasi momento del development del prodotto, purché rimanga possibile assicurare, con i cambiamenti da applicare, la consegna entro la deadline.</p>
<p>Il diagramma è presente alla fine del documento.</p>
<h3 id="creazione">Creazione</h3>
<p>Il documento di progetto è stato realizzato da Victor G. De Matteis integralmente, diagrammi compresi.<br>
Le decisioni più importanti sono state prese all’unanimità dopo cinque ore di incontri all’Istituto Salesiano Bearzi.</p>
<h3 id="diagrammi">Diagrammi</h3>
<p><img src="https://github.com/ArcaneDiver/JMonopoli/blob/master/docs/FOTO%20formalizzazione.png?raw=true" alt="enter image description here"></p>
<p><img src="https://github.com/ArcaneDiver/JMonopoli/blob/master/docs/FOTO%20Previsione%20classi.png?raw=true" alt=""></p>

