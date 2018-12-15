/**
 * 
 */
package cn.whj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.domain.base.Area;
import cn.whj.dao.AreaRespository;
import cn.whj.service.AreaService;

/**
 * @author wuhuijie
 *2018年12月11日
 * 
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaRespository areaRespository;
	@Override
	public void batchSave(List<Area> list) {
		for (Area area : list) {
			areaRespository.save(area);
		}
		
	}
	@Override
	public Page<Area> pageQuery(Specification<Area> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return areaRespository.findAll(spec, pageable);
	}
	@Override
	public List<Area> findAll() {
		// TODO Auto-generated method stub
		return areaRespository.findAll();
	}

}
