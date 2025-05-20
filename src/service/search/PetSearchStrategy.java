package service.search;

import collector.PetCollector;
import entity.enums.PetType;
import repository.FileRepository;

import java.nio.file.Path;
import java.util.List;

public interface PetSearchStrategy {
    void printSearchResult(PetType petType, PetCollector collector, FileRepository fileRepository);
    List<Path> search(PetType petType, PetCollector collector, FileRepository fileRepository);
}
