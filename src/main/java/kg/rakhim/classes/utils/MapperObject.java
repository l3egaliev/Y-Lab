package kg.rakhim.classes.utils;

import kg.rakhim.classes.context.ApplicationContext;
import org.modelmapper.ModelMapper;

public class MapperObject {
    private final static ModelMapper mapper = (ModelMapper) ApplicationContext.getContext("modelMapper");

    public static <T> Object map(Object o1, Class<T> type){
        return mapper.map(o1, type);
    }
}
