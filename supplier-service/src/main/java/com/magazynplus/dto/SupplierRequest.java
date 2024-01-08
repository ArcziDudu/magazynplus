package com.magazynplus.dto;

import lombok.Builder;

@Builder
public record SupplierRequest(Integer id,
                              String name,
                              String phoneNumber,
                              String address,
                              String nip,
                              String email,
                              String postalCode) {
}
