package br.com.OrderManagementAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "tb_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean complete;

    @JsonIgnore
    @CreatedDate
    @Column(updatable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime creationDate;

}