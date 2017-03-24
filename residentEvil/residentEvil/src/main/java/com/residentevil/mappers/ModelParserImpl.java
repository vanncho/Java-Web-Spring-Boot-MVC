package com.residentevil.mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class ModelParserImpl implements ModelParser {

    private ModelMapper modelMapper;

    public ModelParserImpl() {
        this.modelMapper = new ModelMapper();
    }

    @Override
    public <S, D> D convert(S source, Class<D> destination) {
        D convertedObject = this.modelMapper.map(source, destination);
        return convertedObject;
    }

    @Override
    public <S, D> D convert(S source, Class<D> destination, PropertyMap<S, D> propertyMap) {
        this.modelMapper.addMappings(propertyMap);
        D convertedObject = this.modelMapper.map(source, destination);
        return convertedObject;
    }
}
