package cn.whj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.domain.base.Courier;
import cn.whj.dao.CourierRepository;
import cn.whj.service.CourierService;

@Service
@Transactional
public class CourierServiceImpl implements CourierService {

	@Autowired
	private CourierRepository courierRepository;

	@Override
	public void addCourier(Courier model) {
		courierRepository.save(model);

	}

	@Override
	public Page<Courier> findAll(Specification<Courier> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return courierRepository.findAll(spec, pageable);
	}

	/* (non-Javadoc)
	 * @see cn.whj.service.CourierService#delete(java.lang.String)
	 */
	@Override
	public void delete(String ids) {
		String[] idarr = ids.split(",");
		for (String id : idarr) {
			courierRepository.updateDeltag(Integer.parseInt(id));
		}
		
	}

}
