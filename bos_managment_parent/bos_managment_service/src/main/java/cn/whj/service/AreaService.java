/**
 * 
 */
package cn.whj.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.Area;

/**
 * @author wuhuijie
 *2018年12月11日
 * 
 */
public interface AreaService {

	/**
	 * @param list
	 */
	void batchSave(List<Area> list);

	/**
	 * @param spec
	 * @param pageable
	 * @return
	 */
	Page<Area> pageQuery(Specification<Area> spec, Pageable pageable);

	/**
	 * @return
	 */
	List<Area> findAll();

}
