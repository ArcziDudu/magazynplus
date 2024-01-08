package com.magazynplus.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "t_product")

public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CsvBindByName(column = "name")
    private String name;
    @CsvBindByName(column = "category")
    private String category;
    @CsvBindByName(column = "producer")
    private String producer;
    @CsvBindByName(column = "price")
    private BigDecimal price;
    @CsvBindByName(column = "quantity")
    private Double quantity;
    @CsvBindByName(column = "description")
    private String description;
    private Boolean availability;
    @CsvBindByName(column = "unit")
    private String unit;
    @CsvBindByName(column = "supplier")
    private String supplier;
    @CsvBindByName(column = "locationInStorage")
    private String locationInStorage;
    @CsvBindByName(column = "bestBeforeDate")
    @CsvDate(value = "yyyy-MM-dd")
    private LocalDate bestBeforeDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductEntity that = (ProductEntity) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(category, that.category) &&
                Objects.equals(producer, that.producer) &&
                Objects.equals(price, that.price) &&
                Objects.equals(description, that.description) &&
                Objects.equals(availability, that.availability) &&
                Objects.equals(unit, that.unit) &&
                Objects.equals(supplier, that.supplier) &&
                Objects.equals(locationInStorage, that.locationInStorage) &&
                Objects.equals(bestBeforeDate, that.bestBeforeDate);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, category, producer, price, description, availability, unit, supplier, locationInStorage, bestBeforeDate);
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", producer='" + producer + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                ", availability=" + availability +
                ", unit='" + unit + '\'' +
                ", supplier='" + supplier + '\'' +
                ", locationInStorage='" + locationInStorage + '\'' +
                ", bestBeforeDate=" + bestBeforeDate +
                '}';
    }
}
