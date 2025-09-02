package util;

import annotation.CsvColumn;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class GenericCsvExporter {

    public <T> String exportToCsv(Collection<T> data, String... fieldNames) {
    if (data == null || data.isEmpty()) {
        return "Nenhum dado para exportar";
    }

    // garante que seja uma List (mesmo que venha Set, etc.)
    List<T> list = new ArrayList<>(data);

    Class<?> clazz = list.get(0).getClass();
    List<Field> allExportable = getExportableFields(clazz); // todos os @CsvColumn

    // Filtra apenas os campos escolhidos
    List<Field> selectedFields = allExportable.stream()
            .filter(field -> Arrays.asList(fieldNames).contains(field.getName()))
            .collect(Collectors.toList());

    if (selectedFields.isEmpty()) {
        return "Nenhum campo válido selecionado";
    }

    // Cabeçalho com os headers das anotações
    String header = selectedFields.stream()
            .map(field -> {
                CsvColumn ann = field.getAnnotation(CsvColumn.class);
                return ann.header().isEmpty() ? field.getName() : ann.header();
            })
            .collect(Collectors.joining(";"));

    // Dados
    List<String> rows = list.stream()
            .map(obj -> createCsvRow(obj, selectedFields))
            .collect(Collectors.toList());

    List<String> lines = new ArrayList<>();
    lines.add(header);
    lines.addAll(rows);

    return String.join("\n", lines);
}


    private List<Field> getExportableFields(Class<?> clazz) {
    List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(CsvColumn.class))
            .peek(field -> field.setAccessible(true))
            .collect(Collectors.toList());

    System.out.println("[DEBUG] Campos com @CsvColumn encontrados: " +
        fields.stream().map(Field::getName).toList());

    return fields;
}

    private <T> String createCsvRow(T obj, List<Field> fields) {
        return fields.stream()
                .map(field -> {
                    try {
                        Object value = field.get(obj);
                        return value != null ? value.toString() : "";
                    } catch (IllegalAccessException e) {
                        return "ERROR";
                    }
                })
                .map(this::escapeCsvField)
                .collect(Collectors.joining(";"));
    }

    private String escapeCsvField(String field) {
        if (field.contains(";") || field.contains("\"") || field.contains("\n")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }
}