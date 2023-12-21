package com.magazynplus.dto;

import lombok.Builder;
import lombok.ToString;

@Builder
public record SupplierRequest(Integer id,
                              String name,
                              String phoneNumber,
                              String address,
                              String nip,
                              String email,
                              String postalCode) {
}
