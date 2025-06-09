package com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosRespuestaGutendex(
        @JsonAlias("results") List<DatosLibro> resultados,
        @JsonAlias("count") Integer count,
        @JsonAlias("next") String siguiente,
        @JsonAlias("previous") String anterior
) {
}
