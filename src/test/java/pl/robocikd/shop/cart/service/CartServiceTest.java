package pl.robocikd.shop.cart.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.robocikd.shop.cart.model.Cart;
import pl.robocikd.shop.cart.model.dto.CartProductMinDto;
import pl.robocikd.shop.cart.respository.CartRepository;
import pl.robocikd.shop.common.model.Product;
import pl.robocikd.shop.common.repository.ProductRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private CartService cartService;

    @Test
    void shouldAddProductToCartWhenCartIdNotExist() {
        // given
        Long cartId = 0L;
        CartProductMinDto cartProductMinDto = new CartProductMinDto(1L, 1);
        when(productRepository.findById(1L)).thenReturn(Optional.of(Product.builder().id(1L).build()));
        when(cartRepository.save(any())).thenReturn(Cart.builder().id(1L).build());
        // when
        Cart result = cartService.addProductToCart(cartId, cartProductMinDto);
        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldAddProductToCartWhenCartIdExists() {
        // given
        Long cartId = 1L;
        CartProductMinDto cartProductMinDto = new CartProductMinDto(1L, 1);
        when(productRepository.findById(1L)).thenReturn(Optional.of(Product.builder().id(1L).build()));
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(Cart.builder().id(1L).build()));
        // when
        Cart result = cartService.addProductToCart(cartId, cartProductMinDto);
        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }
}