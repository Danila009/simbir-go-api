package ru.volga_it.simbir_go.features.rent.services.type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.volga_it.simbir_go.common.exceptions.ResourceNotFoundException;
import ru.volga_it.simbir_go.features.rent.entities.RentTransportType;
import ru.volga_it.simbir_go.features.rent.entities.RentTransportTypeEntity;
import ru.volga_it.simbir_go.features.rent.repositories.RentTransportTypeRepository;

@Service
@RequiredArgsConstructor
public class RentTransportTypeServiceImpl implements RentTransportTypeService {

    private final RentTransportTypeRepository rentTransportTypeRepository;

    @Override
    public RentTransportTypeEntity getByType(RentTransportType type) {
        return rentTransportTypeRepository.findByType(type)
                .orElseThrow(() -> new ResourceNotFoundException("rent transport type not found"));
    }
}
