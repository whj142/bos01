/**
 * 
 */
package cn.whj.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.itcast.bos.domain.base.SubArea;

/**
 * @author wuhuijie
 *2018年12月15日
 * 
 */
public interface SubAreaRespository extends JpaRepository<SubArea, Integer>, JpaSpecificationExecutor<SubArea> {

}
