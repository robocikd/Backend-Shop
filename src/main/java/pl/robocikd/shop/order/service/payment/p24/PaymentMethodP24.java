package pl.robocikd.shop.order.service.payment.p24;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.robocikd.shop.order.model.Order;
import pl.robocikd.shop.order.model.dto.NotificationReceiveDto;
import reactor.core.publisher.Mono;

import static pl.robocikd.shop.order.service.payment.p24.RequestUtil.createRegisterRequest;
import static pl.robocikd.shop.order.service.payment.p24.RequestUtil.createVerifyRequest;
import static pl.robocikd.shop.order.service.payment.p24.RequestUtil.validate;
import static pl.robocikd.shop.order.service.payment.p24.RequestUtil.validateIpAddress;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentMethodP24 {

    private final PaymentMethodP24Config config;
    private final WebClient p24Client;

    public String initPayment(Order newOrder) {
        log.info("Inicjalizacja płatności");
        ResponseEntity<TransactionRegisterResponse> result = p24Client.post().uri("/transaction/register")
                .bodyValue(createRegisterRequest(config, newOrder))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError
                        , clientResponse -> {
                            log.error("Coś poszło źle: " + clientResponse.statusCode().name());
                            return Mono.empty();
                        })
                .toEntity(TransactionRegisterResponse.class)
                .block();
        if (result != null && result.getBody() != null && result.getBody().getData() != null) {
            return (config.isTestMode() ? config.getTestUrl() : config.getUrl()) + "/trnRequest/"
                    + result.getBody().getData().token();
        }
        return null;
    }

    public String receiveNotification(Order order, NotificationReceiveDto receiveDto, String remoteAddress) {
        log.info(receiveDto.toString());
        validateIpAddress(remoteAddress, config);
        validate(receiveDto, order, config);
        return verifyPayment(receiveDto, order);
    }

    private String verifyPayment(NotificationReceiveDto receiveDto, Order order) {
        ResponseEntity<TransactionVerifyResponse> result = p24Client.put()
                .uri("/transaction/verify")
                .bodyValue(createVerifyRequest(config, order, receiveDto))
                .retrieve()
                .toEntity(TransactionVerifyResponse.class)
                .block();
        log.info("Weryfikacja transakcji satatus: " + result.getBody().getData().status());
        return result.getBody().getData().status();
    }
}
