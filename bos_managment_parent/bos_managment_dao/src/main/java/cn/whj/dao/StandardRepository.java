package cn.whj.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.Standard;

public interface StandardRepository extends JpaRepository<Standard, Integer> {

	public Standard findById(Integer id);
	
	public List<Standard> findByNameLike(String name);
	
	@Query("from Standard where name like ?1")
	public List<Standard> getStandardByName(String name);
	
}
