package pl.robocikd.shop.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.robocikd.shop.cart.controller.dto.CartSummaryDto;
import pl.robocikd.shop.cart.controller.mapper.CartMapper;
import pl.robocikd.shop.cart.model.dto.CartProductMinDto;
import pl.robocikd.shop.cart.service.CartService;

import java.util.List;

import static pl.robocikd.shop.cart.controller.mapper.CartMapper.mapToCartSummaryDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @GetMapping("/{id}")
    public CartSummaryDto getCart(@PathVariable Long id) {
        return mapToCartSummaryDto(cartService.getCart(id));
    }

    @PutMapping("/{id}")
    public CartSummaryDto addProductToCart(@PathVariable Long id, @RequestBody CartProductMinDto cartProductMinDto) {
        return mapToCartSummaryDto(cartService.addProductToCart(id, cartProductMinDto));
    }

    @PutMapping("/{id}/update")
    public CartSummaryDto updateCart(@PathVariable Long id, @RequestBody List<CartProductMinDto> cartProductMinDtoList) {
        return CartMapper.mapToCartSummaryDto(cartService.updateCart(id, cartProductMinDtoList));
    }
}
