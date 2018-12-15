/**
 * 
 */
package cn.whj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.domain.base.SubArea;
import cn.whj.dao.SubAreaRespository;
import cn.whj.service.SubAreaService;

/**
 * @author wuhuijie
 *2018年12月15日
 * 
 */
@Service
@Transactional
public class SubAreaServiceImpl implements SubAreaService {

	@Autowired
	private SubAreaRespository subAreaRespository;

	@Override
	public void add(SubArea model) {
		subAreaRespository.save(model);
	}

	@Override
	public Page<SubArea> pageQuerySubA(Specification<SubArea> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return subAreaRespository.findAll(spec,pageable);
	}


}
