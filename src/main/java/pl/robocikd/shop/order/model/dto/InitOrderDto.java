package pl.robocikd.shop.order.model.dto;

import lombok.Builder;
import lombok.Getter;
import pl.robocikd.shop.order.model.Shipment;

import java.util.List;

@Getter
@Builder
public class InitOrderDto {
    private List<Shipment> shipments;
}
