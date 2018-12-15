package cn.whj.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.Courier;

public interface CourierService {

	public void addCourier(Courier model);

	public Page<Courier> findAll(Specification<Courier> spec, Pageable pageable);

	/**
	 * @param ids
	 */
	public void delete(String ids);
}
