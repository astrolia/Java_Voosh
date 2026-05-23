package com.voosh.vooshv1.util; // Use o pacote correto do seu projeto

import com.voosh.vooshv1.model.Modelo;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;


@FacesConverter(value = "modeloConverter", managed = true)
public class ModeloConverter implements Converter<Modelo> {

    //pega o id ddo modelo escolhido e o coloca em um objeto vazio
    @Override
    public Modelo getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank() || value.contains("@")) {
            return null;
        }

        // cria um objeto modelo apenas com id de um modelo existente
        Modelo modelo = new Modelo();
        modelo.setId(Integer.parseInt(value.trim()));
        return modelo;
    }

    //guarda o id do modelo escolhido
    @Override
    public String getAsString(FacesContext context, UIComponent component, Modelo value) {
        if (value == null || value.getId() == 0) {
            return "";
        }
        // Transforma o ID numérico em String para enviar à tela
        return String.valueOf(value.getId());
    }
}