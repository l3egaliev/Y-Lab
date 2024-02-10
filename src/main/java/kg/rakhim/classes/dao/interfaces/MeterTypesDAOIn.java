package kg.rakhim.classes.dao.interfaces;

import kg.rakhim.classes.models.MeterType;

public interface MeterTypesDAOIn extends BaseDAO<MeterType, Integer>{
    boolean isExists(String newType);
}
