package ru.volga_it.simbir_go.features.transport.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.volga_it.simbir_go.common.exceptions.BadRequestException;
import ru.volga_it.simbir_go.common.exceptions.ResourceNotFoundException;
import ru.volga_it.simbir_go.features.account.entities.UserEntity;
import ru.volga_it.simbir_go.features.account.services.UserService;
import ru.volga_it.simbir_go.features.transport.dto.params.GetTransportsRequestParams;
import ru.volga_it.simbir_go.features.transport.dto.params.TransportTypeRequestParam;
import ru.volga_it.simbir_go.features.transport.dto.params.UpdateTransportParams;
import ru.volga_it.simbir_go.features.transport.dto.params.admin.AdminCreateOrUpdateTransportParams;
import ru.volga_it.simbir_go.features.transport.entities.TransportEntity;
import ru.volga_it.simbir_go.features.transport.entities.TransportTypeEntity;
import ru.volga_it.simbir_go.features.transport.repositories.TransportRepository;
import ru.volga_it.simbir_go.features.transport.services.type.TransportTypeService;

import java.util.List;
import java.util.Objects;

import static ru.volga_it.simbir_go.common.earthCalc.EarthCalc.distanceInKilometers;
import static ru.volga_it.simbir_go.features.transport.dto.params.TransportTypeRequestParam.parseTransportType;

@Service
@RequiredArgsConstructor
public class TransportServiceImpl implements TransportService {

    private final TransportRepository transportRepository;
    private final TransportTypeService transportTypeService;
    private final UserService userService;

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

            if(params.getType() != null && isValid)
                isValid = transport.getTypeEntity().getType() == params.getType();

            if(params.getLatitude() != null && params.getLongitude() != null && params.getRadius() != null && isValid) {
                double distance = distanceInKilometers(transport. getLatitude(), transport.getLongitude(),
                        params.getLatitude(), params.getLongitude());

                isValid = distance <= params.getRadius();
            }

            return isValid;
        }).toList();
    }

    @Override
    @SneakyThrows
    @Transactional(readOnly = true)
    public Page<TransportEntity> getAll(Integer offset, Integer limit, TransportTypeRequestParam type) {
        if(type == TransportTypeRequestParam.All)
            return transportRepository.findAll(PageRequest.of(offset, limit));
        else
            return transportRepository.findAllByType(parseTransportType(type), PageRequest.of(offset, limit));
    }

    @Override
    @Transactional
    public TransportEntity add(Long ownerId, TransportEntity transport) {
        TransportTypeEntity transportType = transportTypeService.getByType(transport.getTypeEntity().getType());
        UserEntity owner = userService.getById(ownerId);

        transport.setTypeEntity(transportType);
        transport.setOwner(owner);

        return transportRepository.save(transport);
    }

    @Override
    @Transactional
    public void update(Long id, UpdateTransportParams params) {
        update(id, params.toAdminParams());
    }

    @Override
    @Transactional
    public void update(Long id, AdminCreateOrUpdateTransportParams params) {
        TransportEntity oldTransport = getById(id);
        TransportEntity updatedTransport = params.toEntity();
        updatedTransport.setId(id);

        if(params.getType() != null &&oldTransport.getTypeEntity().getType() != params.getType()) {
            TransportTypeEntity transportType = transportTypeService.getByType(params.getType());
            updatedTransport.setTypeEntity(transportType);
        } {
            updatedTransport.setTypeEntity(oldTransport.getTypeEntity());
        }

        if(params.getOwnerId() != null && !Objects.equals(oldTransport.getOwner().getId(), params.getOwnerId())) {
            UserEntity owner = userService.getById(params.getOwnerId());
            updatedTransport.setOwner(owner);
        }else {
            updatedTransport.setOwner(oldTransport.getOwner());
        }

        transportRepository.save(updatedTransport);
    }

    @Override
    @Transactional
    public void deletedById(Long id) {
        transportRepository.deleteById(id);
    }
}
