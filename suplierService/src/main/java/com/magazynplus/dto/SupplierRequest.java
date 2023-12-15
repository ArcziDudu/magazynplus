package com.magazynplus.dto;

import lombok.Builder;

@Builder
public record SupplierRequest(String name,
                              String phone,
                              String address,
                              String nip) {
}
