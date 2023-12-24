package com.magazynplus.events;

import com.magazynplus.entity.SupplierEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
public class NewSupplierEvent extends ApplicationEvent {
    private SupplierEntity supplier;

    public NewSupplierEvent(Object source, SupplierEntity supplier) {
        super(source);
        this.supplier = supplier;
    }

    public NewSupplierEvent(SupplierEntity supplier) {
        super(supplier);
        this.supplier = supplier;
    }
}
