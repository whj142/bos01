/**
 * 
 */
package cn.whj.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.SubArea;

/**
 * @author wuhuijie
 *2018年12月15日
 * 
 */
public interface SubAreaService {

	/**
	 * @param model
	 */
	void add(SubArea model);

	Page<SubArea> pageQuerySubA(Specification<SubArea> spec, Pageable pageable);

}
