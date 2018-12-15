/**
 * 
 */
package cn.itcast.web.action;

import java.io.IOException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.SubArea;
import cn.whj.service.SubAreaService;

/**
 * @author wuhuijie 2018年12月15日
 * 
 */
@ParentPackage("struts-default")
@Namespace("/")
@Actions
@Controller
@Scope("prototype")
public class SubAreaAction extends BaseAction<SubArea> {

	@Autowired
	private SubAreaService subAreaService;

	/**
	 * 保存分区
	 * @return
	 */
	@Action(value = "subareaAction_save", results = {
			@Result(name = "success", location = "/pages/base/sub_area.jsp") })
	public String save() {
		subAreaService.add(model);
		return "success";

	}
	@Action(value = "subareaAction_pageQuerySubA")
	public String pageQuerySubA() throws IOException {
		Pageable pageable = new PageRequest(page-1, rows);
		
		Specification<SubArea> spec = new Specification<SubArea>() {

			@Override
			public Predicate toPredicate(Root<SubArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		Page<SubArea> page = subAreaService.pageQuerySubA(spec,pageable);
//		"subareas",
		String[] exclude = {"subareas","fixedArea"};
		this.write2JsonObject(page, exclude);
		return NONE;
		
	}
}
