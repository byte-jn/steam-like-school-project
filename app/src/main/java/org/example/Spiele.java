package org.example;

import java.util.ArrayList;
import java.util.List;

public class Spiele {

    private String titel;
    private List<String> dlcListe;

    public Spiele(String titel) {
        this.titel = titel;
        this.dlcListe = new ArrayList<>();
    }

    public void addDlc(String dlcName) {
        this.dlcListe.add(dlcName);
    }
}
