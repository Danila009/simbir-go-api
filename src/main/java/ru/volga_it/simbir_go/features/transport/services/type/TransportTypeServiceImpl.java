package ru.volga_it.simbir_go.features.transport.services.type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.volga_it.simbir_go.common.exceptions.ResourceNotFoundException;
import ru.volga_it.simbir_go.features.transport.entities.TransportType;
import ru.volga_it.simbir_go.features.transport.entities.TransportTypeEntity;
import ru.volga_it.simbir_go.features.transport.repositories.TransportTypeRepository;

@Service
@RequiredArgsConstructor
public class TransportTypeServiceImpl implements TransportTypeService {

    private final TransportTypeRepository transportTypeRepository;

    @Override
    public TransportTypeEntity getByType(TransportType type) {
        return transportTypeRepository.findByType(type)
                .orElseThrow(() -> new ResourceNotFoundException("Transport type not found"));
    }
}
