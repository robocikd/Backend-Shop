package pl.robocikd.shop.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.robocikd.shop.cart.model.dto.CartProductMinDto;
import pl.robocikd.shop.common.model.Cart;
import pl.robocikd.shop.common.model.CartItem;
import pl.robocikd.shop.common.model.Product;
import pl.robocikd.shop.common.repository.CartRepository;
import pl.robocikd.shop.common.repository.ProductRepository;

import java.util.List;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public Cart getCart(Long id) {
        return cartRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Cart addProductToCart(Long id, CartProductMinDto cartProductMinDto) {
        Cart cart = getInitializedCart(id);
        cart.addProduct(CartItem.builder()
                .quantity(cartProductMinDto.quantity())
                .product(getProduct(cartProductMinDto.productId()))
                .cartId(cart.getId())
                .build());
        return cart;
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }

    private Cart getInitializedCart(Long id) {
        if (id == null || id <= 0) {
            return saveNewCart();
        }
        return cartRepository.findById(id)
                .orElseGet(this::saveNewCart);
    }

    private Cart saveNewCart() {
        return cartRepository.save(Cart.builder()
                .created(now())
                .build());
    }

    @Transactional
    public Cart updateCart(Long id, List<CartProductMinDto> cartProductMinDtos) {
        Cart cart = cartRepository.findById(id).orElseThrow();
        cart.getItems().forEach(cartItem -> {
            cartProductMinDtos.stream()
                    .filter(cartProductMinDto -> cartItem.getProduct().getId().equals(cartProductMinDto.productId()))
                    .findFirst().ifPresent(cartProductMinDto -> cartItem.setQuantity(cartProductMinDto.quantity()));
        });
        return cart;
    }
}
