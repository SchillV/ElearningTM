package com.tm.elearningtm.classes;

public class MaterialCurs extends Postare {
    private TipMaterial tipMaterial;

    public MaterialCurs(int id, String titlu, String descriere, TipMaterial tipMaterial) {
        super(id, titlu, descriere);
        this.tipMaterial = tipMaterial;
    }

    public TipMaterial getTipMaterial() {
        return tipMaterial;
    }

    public void setTipMaterial(TipMaterial tipMaterial) {
        this.tipMaterial = tipMaterial;
    }
}
