package com.alura.literalura.model;

public enum Idioma {
    ESPAÑOL("es"),
    INGLES("en"),
    FRANCES("fr"),
    PORTUGUES("pt"),
    OTROS("otros");

    private final String codigo;

    Idioma(String codigo) { this.codigo = codigo; }

    public String getCodigo() { return codigo; }

    public static Idioma fromString(String value) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.codigo.equalsIgnoreCase(value) || idioma.name().equalsIgnoreCase(value)) {
                return idioma;
            }
        }
        return OTROS;
    }

    public String getNombreIdioma() {
        switch (this) {
            case ESPAÑOL: return "Español";
            case INGLES: return "Inglés";
            case FRANCES: return "Francés";
            case PORTUGUES: return "Portugués";
            default: return "Otros";
        }
    }
}
