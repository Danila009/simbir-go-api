package ru.volga_it.simbir_go.features.rent.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.volga_it.simbir_go.common.exceptions.BadRequestException;
import ru.volga_it.simbir_go.common.exceptions.ResourceNotFoundException;
import ru.volga_it.simbir_go.features.account.entities.UserEntity;
import ru.volga_it.simbir_go.features.account.services.UserService;
import ru.volga_it.simbir_go.features.rent.dto.params.CreateOrUpdateRentTransportParams;
import ru.volga_it.simbir_go.features.rent.entities.RentTransportEntity;
import ru.volga_it.simbir_go.features.rent.entities.RentTransportType;
import ru.volga_it.simbir_go.features.rent.entities.RentTransportTypeEntity;
import ru.volga_it.simbir_go.features.rent.repositories.RentTransportRepository;
import ru.volga_it.simbir_go.features.rent.services.type.RentTransportTypeService;
import ru.volga_it.simbir_go.features.transport.entities.TransportEntity;
import ru.volga_it.simbir_go.features.transport.services.TransportService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static ru.volga_it.simbir_go.common.extensions.LocalDateTimeExtension.daysBetweenInclusive;
import static ru.volga_it.simbir_go.common.extensions.LocalDateTimeExtension.minutesBetweenInclusive;

@Service
@RequiredArgsConstructor
public class RentTransportServiceImpl implements RentTransportService {

    private final RentTransportRepository rentTransportRepository;
    private final UserService userService;
    private final TransportService transportService;
    private final RentTransportTypeService rentTransportTypeService;

    @Override
    @Transactional(readOnly = true)
    public List<RentTransportEntity> getAll(
            Long transportId,
            Long userId
    ) {
        List<RentTransportEntity> rents = rentTransportRepository.findAll();

        return rents.stream().filter(rent -> {
            boolean isValid = rent.getTransport().getCanBeRented();

            if(transportId != null)
                isValid = Objects.equals(rent.getTransport().getId(), transportId);

            if(userId != null)
                isValid = isValid && Objects.equals(rent.getUser().getId(), userId);

            return isValid;
        }).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RentTransportEntity getById(Long id) {
        return rentTransportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rent transport not found"));
    }

    @Override
    public RentTransportEntity add(CreateOrUpdateRentTransportParams params) {
        RentTransportEntity rent = new RentTransportEntity();
        TransportEntity transport = transportService.getById(params.transportId());
        UserEntity user = userService.getById(params.userId());
        RentTransportTypeEntity type = rentTransportTypeService.getByType(params.type());

        rent.setTransport(transport);
        rent.setUser(user);
        rent.setTimeStart(params.timeStart());
        rent.setTimeEnd(params.timeEnd());
        rent.setPriceOfUnit(params.priceOfUnit());
        rent.setTransportType(type);
        rent.setFinalPrice(params.finalPrice());

        return rentTransportRepository.save(rent);
    }

    @Override
    @Transactional
    public RentTransportEntity userNew(RentTransportType type, Long userId, Long transportId) {
        TransportEntity transport = transportService.getById(transportId);
        if(!transport.getCanBeRented()) throw new BadRequestException("Cannot be rented out");

        UserEntity user = userService.getById(userId);

        RentTransportTypeEntity rentTransportType = rentTransportTypeService.getByType(type);
        RentTransportEntity rent = new RentTransportEntity();
        if(user.getBalance() <= 0) throw new BadRequestException("User balance is less than zero");
        rent.setTransport(transport);
        rent.setUser(user);
        rent.setTransportType(rentTransportType);
        rent.setTimeStart(LocalDateTime.now());
        rent.getTransport().setCanBeRented(false);

        Double priceOfUnit = type == RentTransportType.Days ? transport.getDayPrice() : transport.getMinutePrice();
        if(priceOfUnit == null) throw new BadRequestException("Cannot be rented out by type " + type.name());
        rent.setPriceOfUnit(priceOfUnit);

        return rentTransportRepository.save(rent);
    }

    @Override
    @Transactional
    public RentTransportEntity userEnd(Double latitude, Double longitude, Long rentId) {
        RentTransportEntity rent = getById(rentId);
        if(rent.getTransport().getCanBeRented()) throw new BadRequestException("Rental of transport is completed");

        rent.setTimeEnd(LocalDateTime.now());

        Double finalPrice = rent.getTransportType().getType() == RentTransportType.Days ?
                rent.getTransport().getDayPrice() * daysBetweenInclusive(rent.getTimeStart(), rent.getTimeEnd()) :
                rent.getTransport().getMinutePrice() * minutesBetweenInclusive(rent.getTimeStart(), rent.getTimeEnd());

        rent.setFinalPrice(finalPrice);
        rent.getUser().setBalance(rent.getUser().getBalance() - rent.getFinalPrice());
        rent.getTransport().setLatitude(latitude);
        rent.getTransport().setLongitude(longitude);

        rent.getTransport().setCanBeRented(true);

        return rentTransportRepository.save(rent);
    }

    @Override
    @Transactional
    public void update(Long id, CreateOrUpdateRentTransportParams params) {
        RentTransportEntity rent = getById(id);
        TransportEntity transport = transportService.getById(params.transportId());
        UserEntity user = userService.getById(params.userId());
        RentTransportTypeEntity type = rentTransportTypeService.getByType(params.type());

        rent.setTransport(transport);
        rent.setUser(user);
        rent.setTimeStart(params.timeStart());
        rent.setTimeEnd(params.timeEnd());
        rent.setPriceOfUnit(params.priceOfUnit());
        rent.setTransportType(type);
        rent.setFinalPrice(params.finalPrice());

        rentTransportRepository.save(rent);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        rentTransportRepository.deleteById(id);
    }
}
