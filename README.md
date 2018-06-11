# news-analyzer

Projekt na przedmiot: Zaawansowane Techniki Integracji Systemów, 2018.

## Aplikacja do pobierania feedów

Aplikacja stworzona jest w Javie w oparciu o framework Spring. 
Aby go uruchomić należy zainstalować wymagane przez Mavena biblioteki.
Uruchomienie następuje przez klasę NewsAnalyzerApplication, gdzie można dokładnie skonfigurować wykorzystywane kanały RSS. W application.properties należy podać dane dostępowe do bazy MongoDB.

## Moduł do analizy

Został zrealizowany w oparciu o Jupyter Notebook z wykorzystaniem biblioteki Pandas i PyMongo. Zależności Pythona (wykorzystano Python 3.6) można zainstalować korzystając z polecenia `pip install -r scripts/requirements.txt`. Następnie poleceniem `jupyter notebook` uruchomić notebook `analysis-v1.pynb`
Scripts zawiera wszystkie pliki Pythona konieczne do analizy, ale korzysta również ze słowników w katalogu `resources`.