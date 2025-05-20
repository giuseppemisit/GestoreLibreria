package base.libreria;

import salvataggio.JsonStorageService;

public class LibreriaJson extends AbstractLibreria{

    public LibreriaJson(String utenteCorrente){
        super(new JsonStorageService(utenteCorrente), utenteCorrente);
    }

}