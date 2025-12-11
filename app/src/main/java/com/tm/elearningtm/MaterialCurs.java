package com.tm.elearningtm;

public class MaterialCurs extends Postare {
    private TipMaterial tipMaterial;
    public MaterialCurs(int id, String titlu, TipMaterial tipMaterial) {
        super(id, titlu);
        this.tipMaterial = tipMaterial;
    }
}
