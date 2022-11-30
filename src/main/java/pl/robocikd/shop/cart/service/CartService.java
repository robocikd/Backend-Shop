package pl.robocikd.shop.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.robocikd.shop.cart.model.Cart;
import pl.robocikd.shop.cart.model.CartItem;
import pl.robocikd.shop.cart.model.dto.CartProductMinDto;
import pl.robocikd.shop.cart.respository.CartRepository;
import pl.robocikd.shop.common.model.Product;
import pl.robocikd.shop.common.repository.ProductRepository;

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
            return cartRepository.save(Cart.builder()
                    .created(now())
                    .build());
        }
        return cartRepository.findById(id).orElseThrow();
    }
}
