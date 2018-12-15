package cn.whj.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.Courier;
//JpaSpecificationExecutor<Courier>
public interface CourierRepository extends JpaRepository<Courier, Integer>, JpaSpecificationExecutor<Courier> {

	/**
	 * @param id
	 */
	@Query("update Courier set deltag = '1' where id = ?1")
	@Modifying
	void updateDeltag(Integer id);

}
