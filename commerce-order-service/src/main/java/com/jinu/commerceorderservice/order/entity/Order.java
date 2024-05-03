package com.jinu.commerceorderservice.order.entity;

import com.jinu.commercecommon.util.Timestamped;
import com.jinu.commerceuserservice.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Order  extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;


    @Builder
    public Order(User user, Status status) {
        this.user = user;
        this.status = status;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
}