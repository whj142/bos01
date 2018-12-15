package cn.whj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.domain.base.Standard;
import cn.whj.dao.StandardRepository;
import cn.whj.service.StandardService;
@Service
@Transactional//事务
public class StandardServiceImpl implements StandardService {

	@Autowired
	private StandardRepository standardRepository;
	@Override
	public void add(Standard model) {
		standardRepository.save(model);
	}
	
	public void edit(Standard standard){
		standardRepository.saveAndFlush(standard);
	}

	@Override
	public Page<Standard> findAllPage(Pageable pageable) {
		
		return standardRepository.findAll(pageable);
	}

	@Override
	public List<Standard> findAll() {
		// TODO Auto-generated method stub
		return standardRepository.findAll();
	}

}
