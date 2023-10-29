package ru.volga_it.simbir_go.features.transport.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.volga_it.simbir_go.common.exceptions.ResourceNotFoundException;
import ru.volga_it.simbir_go.features.transport.dto.GetTransportsRequestParams;
import ru.volga_it.simbir_go.features.transport.entities.TransportEntity;
import ru.volga_it.simbir_go.features.transport.entities.TransportTypeEntity;
import ru.volga_it.simbir_go.features.transport.repositories.TransportRepository;
import ru.volga_it.simbir_go.features.transport.services.type.TransportTypeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransportServiceImpl implements TransportService {

    private final TransportRepository transportRepository;
    private final TransportTypeService transportTypeService;

    @Override
    @Transactional(readOnly = true)
    public TransportEntity getById(Long id) {
        return transportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transport not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransportEntity> getAll(GetTransportsRequestParams params) {
        List<TransportEntity> transports = transportRepository.findAll();
        if(params == null) return transports;

        return transports.stream().filter(transport -> {
            boolean isValid = true;

            if(params.getCanBeRented() != null)
                isValid = transport.getCanBeRented().equals(params.getCanBeRented());

            if(params.getType() != null)
                isValid = isValid && transport.getTypeEntity().getType() == params.getType();

            return isValid;
        }).toList();
    }

    @Override
    @Transactional
    public Long add(TransportEntity transport) {
        TransportTypeEntity transportType = transportTypeService.getByType(transport.getTypeEntity().getType());
        transport.setTypeEntity(transportType);
        return transportRepository.save(transport).getId();
    }

    @Override
    @Transactional
    public void update(Long id, TransportEntity updatedTransport) {
        TransportEntity oldTransport = getById(id);
        updatedTransport.setTypeEntity(oldTransport.getTypeEntity());
        transportRepository.save(updatedTransport);
    }

    @Override
    @Transactional
    public void deletedById(Long id) {
        transportRepository.deleteById(id);
    }
}
