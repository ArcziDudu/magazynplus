package com.magazynplus.event;

import com.magazynplus.entity.SupplierEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewSupplierCreatedEvent {
    private SupplierEntity supplier;
}
