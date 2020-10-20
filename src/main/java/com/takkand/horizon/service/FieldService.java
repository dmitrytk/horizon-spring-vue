package com.takkand.horizon.service;

import com.takkand.horizon.domain.Field;

public interface FieldService extends BaseOperations<Field> {
    void deleteWells(Long id);
}
