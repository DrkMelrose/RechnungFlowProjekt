package de.rechnungflow.persistance;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JsonStorage {
    private final ObjectMapper mapper = new ObjectMapper();

    public <T> List<T> readList(Path path, TypeReference<List<T>> type){
        if (!Files.exists(path)){
            return List.of();
        }
        try{
            return mapper.readValue(path.toFile(), type);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read: " + path, e);
        }
    }

    public <T> void writeList(Path path, List<T> data){
        try{
            Path parent = path.getParent();
            if (parent != null) Files.createDirectories(parent);

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(path.toFile(), data);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write: " + path, e);
        }
    }
}
