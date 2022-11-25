package com.netflixwish.demo.converter;

import com.netflixwish.demo.entity.Certification;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class CertificationAttributeConverter implements AttributeConverter<Certification, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Certification attribute) {
        return attribute != null ? attribute.getKey() : null;
    }

    @Override
    public Certification convertToEntityAttribute(Integer integer) {
        return Stream.of(Certification.values()).filter(certif -> certif.getKey().equals(integer)).findFirst().orElse(null);
    }
}
