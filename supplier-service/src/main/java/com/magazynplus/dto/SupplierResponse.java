package com.magazynplus.dto;

import lombok.Builder;

@Builder
public record SupplierResponse(Integer id,
                               String name,
                               String phoneNumber,
                               String address,
                               String nip,
                               String email,
                               String postalCode) {
}
