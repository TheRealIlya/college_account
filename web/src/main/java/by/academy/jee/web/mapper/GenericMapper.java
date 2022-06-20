package by.academy.jee.web.mapper;

import java.util.List;

public interface GenericMapper<R, T, Y> {

    T mapModelToDto(Y model);

    List<T> mapModelListToDtoList(List<Y> models);

    Y mapDtoToModel(R dtoRequest);
}