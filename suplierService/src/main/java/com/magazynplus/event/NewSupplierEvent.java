package com.magazynplus.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
public class NewSupplierEvent extends ApplicationEvent {
    private String orderNumber;

    public NewSupplierEvent(Object source, String orderNumber) {
        super(source);
        this.orderNumber = orderNumber;
    }

    public NewSupplierEvent(String orderNumber) {
        super(orderNumber);
        this.orderNumber = orderNumber;
    }
}
