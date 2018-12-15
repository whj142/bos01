/**
 * 
 */
package cn.whj.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.itcast.bos.domain.base.Area;

/**
 * @author wuhuijie 2018年12月11日
 * 
 */
public interface AreaRespository extends JpaRepository<Area, Integer>, JpaSpecificationExecutor<Area> {

}
