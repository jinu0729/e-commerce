package com.jinu.commerce.domain.cart.service;

import com.jinu.commerce.domain.cart.dto.request.CartRequestDto;
import com.jinu.commerce.domain.cart.dto.request.CartItemEditRequestDto;
import com.jinu.commerce.domain.cart.entity.Cart;
import com.jinu.commerce.domain.cart.entity.CartItem;
import com.jinu.commerce.domain.cart.repository.CartDetailRepository;
import com.jinu.commerce.global.exception.CustomException;
import com.jinu.commerce.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j(topic = "CartDetailServiceImpl")
@Service
@RequiredArgsConstructor
public class CartDetailServiceImpl implements CartDetailService {
    private final CartDetailRepository repository;

    @Override
    @Transactional
    public void addCartItems(Cart cart, List<CartRequestDto> requestDtos) {
        log.info("장바구니 아이템 생성");

        List<CartItem> cartItems = new ArrayList<>();

        for (CartRequestDto requestDto : requestDtos) {
            CartItem cartItem = CartItem.builder()
                    .cart(cart)
                    .product(requestDto.getProduct())
                    .qty(requestDto.getQty())
                    .build();

            cartItems.add(cartItem);
        }

        this.repository.saveAll(cartItems);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItem> getAllCartItemsByCart(Cart cart) {
        log.info("장바구니 조회");

        return this.repository.findAllByCart(cart);
    }

    @Override
    @Transactional
    public void editCartItemQty(CartItemEditRequestDto requestDto) {
        log.info("장바구니 아이템 수량 변경");

        CartItem cartItem = this.getCartItemById(requestDto.getCartItemId());
        cartItem.editQty(requestDto.getQty());
    }


    @Override
    @Transactional(readOnly = true)
    public CartItem getCartItemById(Long cartItemId) {
        log.info("아이디로 장바구나 이이템 조회");

        return this.repository.findById(cartItemId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ORDER));
    }
}