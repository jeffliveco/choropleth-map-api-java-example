package co.jeffersonortiz.choroplethapi.business.gis.service;

import java.util.List;

import javax.persistence.EntityManager;

import co.jeffersonortiz.choroplethapi.dao.ShapeDao;
import co.jeffersonortiz.choroplethapi.dto.ShapeDto;
import co.jeffersonortiz.choroplethapi.entity.Shape;
import co.jeffersonortiz.choroplethapi.exception.business.BusinessException;
import co.jeffersonortiz.choroplethapi.exception.data.DataAccessException;

/**
 * 
 * @author <a href="mailto:me@jeffersonortiz.com">Jefferson Ortiz Quiroga</a>
 * @version 1.0
 */
public class GisService implements IGisService {

	/**
	 * 
	 */
	private ShapeDao shapeDao;
	
	/**
	 * 
	 */
	private static GisService instance;
	
	/**
	 * 
	 * @param em
	 * @return
	 */
	public static GisService getInstance(EntityManager em) {
		if(instance == null) {
			instance = new GisService(em);
		}
		return instance;
	}
	
	public GisService(EntityManager em) {
		this.shapeDao = ShapeDao.getInstance(em);
	}
	
	@Override
	public List<ShapeDto> getAllWord() throws BusinessException {
		try {
			List<Shape> resultData = shapeDao.getAll();
			resultData = shapeDao.getGeometries(resultData);
			
			return new ShapeDto().mapperListEntityToListDto(resultData);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage());
		}
	}
}
