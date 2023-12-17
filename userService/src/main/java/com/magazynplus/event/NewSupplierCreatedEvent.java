package com.magazynplus.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewSupplierCreatedEvent {
    private String orderNumber;
}
